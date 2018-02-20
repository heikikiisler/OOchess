import chess

from cemle import config


def get_coefficients():
    with open(file=config.linear_regression_coefficients_path, mode="r") as file:
        file.readline()
        coefficients = file.readline().split(",")
        return [int(i) for i in coefficients]


LINEAR_REGRESSION_COEFFICIENTS = get_coefficients()
COEFFICIENTS_LENGTH = len(LINEAR_REGRESSION_COEFFICIENTS)


class BoardFeatureExtractor:
    PIECE_VALUES = {"P": 1, "p": -1, "B": 3, "b": -3, "N": 3, "n": -3, "R": 5, "r": -5, "Q": 9, "q": -9}
    PIECES = ("Q", "q", "R", "r", "B", "b", "N", "n", "P", "p")

    def __init__(self, board):
        self.board = board
        self.fen_pieces = self.get_fen_piece_string()

        self.material_value = self.get_material_value()
        self.turn = self.board.turn

        self.moves = list(self.board.legal_moves)
        self.moves_total = len(self.moves)
        self.attacks = self.get_attacks_total(self.moves)

        self.board.push_uci("0000")  # Null move
        self.opponent_moves = list(self.board.legal_moves)
        self.opponent_moves_total = len(self.opponent_moves)
        self.opponent_attacks = self.get_attacks_total(self.opponent_moves)
        self.board.pop()  # Undo Null move

        self.white_moves_total = self.moves_total if self.turn else self.opponent_moves_total
        self.white_attacks = self.attacks if self.turn else self.opponent_attacks

        self.black_moves_total = self.moves_total if not self.turn else self.opponent_moves_total
        self.black_attacks = self.attacks if not self.turn else self.opponent_attacks

    def get_fen_piece_string(self):
        # 0.8 s for 10000
        return self.board.fen().split(" ")[0]

    def get_material_value(self):
        material_sum = 0
        fen_string = self.fen_pieces
        for i in range(0, len(fen_string)):
            char = fen_string[i]
            if char in self.PIECE_VALUES.keys():
                material_sum += self.PIECE_VALUES[char]
        return material_sum

    def get_piece_count(self, piece):
        return self.fen_pieces.count(piece)

    def get_attacks_total(self, moves):
        return sum([self.board.is_capture(move) for move in moves])

    def get_castling_rights_sum(self):
        return (self.board.has_kingside_castling_rights(self.turn),
                self.board.has_queenside_castling_rights(self.turn)).count(True)

    def get_features(self):
        """Get features from board and return a dictionary

        TODO: Improve features

        """
        features = {
            "fen": self.board.fen(),
            "material": self.material_value,
            # "turn": int(self.turn),
            "white_moves_total": self.white_moves_total,
            "white_attacks": self.white_attacks,
            "black_moves_total": self.black_moves_total,
            "black_attacks": self.black_attacks
        }
        return features

    @staticmethod
    def get_feature_keys():
        default_extractor = BoardFeatureExtractor(chess.Board())
        return list(default_extractor.get_features().keys())


def get_linear_regression_evaluation(board):
    extractor = BoardFeatureExtractor(board)
    features = list(extractor.get_features().values())[1:]
    evaluation = 0
    for i in range(0, COEFFICIENTS_LENGTH):
        evaluation += LINEAR_REGRESSION_COEFFICIENTS[i] * int(features[1])
    return evaluation
