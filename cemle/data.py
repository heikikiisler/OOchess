import pandas as pd
import chess


class EngineValues:
    def __init__(self, file_name):
        """Example input file:

        Event,MoveScores
        1,18 17 12 8 -5 12 3 -2 22 21 20 13 8 21 11 3 -6 5 1 -10 -21 -1 -26 18 48 48 53 73 46 68 51 60 54 46 70 62 35 54
        2,26 44 26 18 14 34 36 31 37 35 42 52 55
        """
        self.file_name = file_name
        self.csv = pd.read_csv(file_name)
        self.total_games = len(self.csv.index)

    def get_values_for_event(self, game_id):
        """Parameter game_id starts from 0. """
        return self.csv.iloc[game_id]["MoveScores"].split(" ")


class UciMovesReader:
    def __init__(self, file_name):
        self.file_name = file_name
        self.moves_lists = self.read_moves_into_lists()

    def read_moves_into_lists(self):
        """Example input UCI moves file:

        g1f3 g8f6 c2c4 c7c5 b2b3 g7g6 c1b2 f8g7 e2e3 e8g8 f1e2 b7b6 e1g1 c8b7 b1c3 b8c6 d1c2
        e2e4 e7e5 g1f3 g8f6 d2d4 f6e4 f3e5 d7d6 e5f3 d6d5 f1d3 e4d6 e1g1
        ...

        """
        with open(file=self.file_name, mode="r") as file:
            lines = file.read().splitlines()
            return [line.split(" ") for line in lines]

    def get_moves_for_game(self, game_id):
        return self.moves_lists[game_id]


class CsvGenerator:
    def __init__(self, engine_values, pgn_reader):
        self.engine_values = engine_values
        self.uci_moves_reader = pgn_reader

    def generate_position_lines_from_game(self, game_id):
        board = chess.Board()
        evaluations = self.engine_values.get_values_for_event(game_id)
        moves = self.uci_moves_reader.get_moves_for_game(game_id)
        positions = list()
        total_evaluations = len(evaluations)
        total_moves = len(moves)
        if total_evaluations != total_moves:
            print("Skipping game_id {}, #evaluations={}, #moves={}".format(game_id, total_evaluations, total_moves))
            return []
        for i in range(0, total_evaluations):
            board.push_uci(moves[i])
            fen = board.fen()
            evaluation = evaluations[i]
            if evaluation != "NA":
                positions.append((fen, evaluation))
        return positions

    def generate_csv_with_all_games(self, file_path):
        """Example output CSV file:

        fen, evaluation
        rnbqkbnr/pppppppp/8/8/8/5N2/PPPPPPPP/RNBQKB1R b KQkq - 1 1, 18
        rnbqkb1r/pppppppp/5n2/8/8/5N2/PPPPPPPP/RNBQKB1R w KQkq - 2 2, 17
        ...

        """
        total_games = self.engine_values.total_games
        csv_string = "fen, evaluation\n"
        # File directory must exist
        with open(file=file_path, mode="w") as file:
            file.write(csv_string)
            for i in range(0, total_games):
                positions = self.generate_position_lines_from_game(i)
                for position in positions:
                    if position:  # Can't be empty list
                        file.write(("{}, {}\n".format(position[0], position[1])))
                if i % 100 == 0:
                    print("Finished game {}, completion [{:.1f}%]".format(i, float(i / total_games) * 100))


class PgnInfoRemover:
    def __init__(self, input_path, output_path):
        self.input_path = input_path
        self.output_path = output_path

    def run(self):
        line_starts = ("a", "b", "c", "d", "e", "f", "g")
        game_results = (" 1-0", " 1/2-1/2", " 0-1")
        with open(file=self.input_path, mode="r") as input_file:
            with open(file=self.output_path, mode="w") as output_file:
                for line in input_file:
                    if line.startswith(line_starts):
                        for game_result in game_results:
                            line = line.replace(game_result, "")
                        output_file.write(line.lower())


class BoardFeatureExtractor:
    def __init__(self, fen):
        self.board = chess.Board(fen=fen)
        self.turn = self.board.turn
        self.check = self.board.is_check()
        self.moves = self.board.legal_moves
        self.moves_total = len(list(self.moves))
        self.attacks = self.get_attacks_total(self.moves)
        self.castling_rights = self.get_castling_rights_sum()

        self.board.push_uci("0000")  # Null move
        self.opponent_moves = self.board.legal_moves
        self.opponent_moves_total = len(list(self.opponent_moves))
        self.opponent_attacks = self.get_attacks_total(self.opponent_moves)
        self.opponent_castling_rights = self.get_castling_rights_sum()

    def get_attacks_total(self, moves):
        return sum([self.board.is_capture(move) for move in moves])

    def get_castling_rights_sum(self):
        return (self.board.has_kingside_castling_rights(self.turn),
                self.board.has_queenside_castling_rights(self.turn)).count(True)

    def get_features(self):
        """Get features from board and return a dictionary

        TODO: Improve features

        """
        return {
            "fen": self.board.fen(),
            "turn": int(self.turn),
            "check": int(self.check),
            "moves_total": self.moves_total,
            "attacks": self.attacks,
            "castling_rights": self.castling_rights,
            "opponent_moves_total": self.opponent_moves_total,
            "opponent_attacks": self.opponent_attacks,
            "opponent_castling_rights": self.opponent_castling_rights
        }

    @staticmethod
    def get_feature_keys():
        default_extractor = BoardFeatureExtractor(chess.Board().fen())
        return list(default_extractor.get_features().keys())


class FeatureCsvWriter:
    def __init__(self, input_path, output_path):
        self.input_path = input_path
        self.output_path = output_path

    def run(self):
        with open(file=self.output_path, mode="w") as output_file:
            feature_names = BoardFeatureExtractor.get_feature_keys()
            output_file.write("".join([key + "," for key in feature_names]))
            output_file.write("evaluation\n")
            with open(file=self.input_path, mode="r") as input_file:
                next(input_file)
                for line in input_file:
                    values = line.split(",")
                    features = BoardFeatureExtractor(values[0]).get_features()
                    evaluation = values[1]
                    for feature in feature_names:
                        output_file.write(str(features.get(feature)))
                        output_file.write(",")
                    output_file.write(evaluation.strip())
                    output_file.write("\n")
