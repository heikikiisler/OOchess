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
        with open(file=self.file_name) as file:
            lines = file.read().splitlines()
            return [line.split(" ") for line in lines]

    def get_moves_for_game(self, game_id):
        return self.moves_lists[game_id]


class CsvGenerator:
    def __init__(self, engine_values, pgn_reader):
        self.engine_values = engine_values
        self.pgn_reader = pgn_reader

    def generate_position_lines_from_game(self, game_id):
        board = chess.Board()
        evaluations = self.engine_values.get_values_for_event(game_id)
        moves = self.pgn_reader.get_moves_for_game(game_id)
        positions = list()
        total_moves = len(evaluations)
        for i in range(0, total_moves):
            board.push_uci(moves[i])
            fen = board.fen()
            evaluation = evaluations[i]
            positions.append((fen, evaluation))
        return positions

    def generate_csv_with_all_games(self):
        """Example output CSV file:

        fen, evaluation
        rnbqkbnr/pppppppp/8/8/8/5N2/PPPPPPPP/RNBQKB1R b KQkq - 1 1, 18
        rnbqkb1r/pppppppp/5n2/8/8/5N2/PPPPPPPP/RNBQKB1R w KQkq - 2 2, 17
        ...

        """
        total_games = self.engine_values.total_games
        csv_string = "fen, evaluation\n"
        for i in range(0, total_games):
            positions = self.generate_position_lines_from_game(i)
            for position in positions:
                csv_string += ("{}, {}\n".format(position[0], position[1]))
        # File directory must exist
        with open(file="../data/small/fen-evaluations.csv", mode="w") as file:
            file.write(csv_string)
