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
    PIECES = ("Q", "q", "R", "r", "B", "b", "N", "n", "P", "p")

    def __init__(self, board):
        self.board = board
        self.fen_pieces = self.get_fen_piece_string()

        self.piece_counts = ((piece, self.get_piece_count(piece)) for piece in self.PIECES)

        self.turn = self.board.turn

        self.moves = list(self.board.legal_moves)
        self.moves_total = len(self.moves)
        self.attacks = self.get_attacks_total(self.moves)
        self.castling_rights = self.get_castling_rights_sum()

        self.board.push_uci("0000")  # Null move
        self.opponent_moves = list(self.board.legal_moves)
        self.opponent_moves_total = len(self.opponent_moves)
        self.opponent_attacks = self.get_attacks_total(self.opponent_moves)
        self.opponent_castling_rights = self.get_castling_rights_sum()
        self.board.pop()  # Undo Null move

        self.white_moves_total = self.moves_total if self.turn else self.opponent_moves_total
        self.white_attacks = self.attacks if self.turn else self.opponent_attacks
        self.white_castling_rights = self.castling_rights if self.turn else self.opponent_castling_rights
        self.white_in_check = 1 if self.turn & self.board.is_check() else 0

        self.black_moves_total = self.moves_total if not self.turn else self.opponent_moves_total
        self.black_attacks = self.attacks if not self.turn else self.opponent_attacks
        self.black_castling_rights = self.castling_rights if not self.turn else self.opponent_castling_rights
        self.black_in_check = 1 if not self.turn & self.board.is_check() else 0

    def get_fen_piece_string(self):
        # 0.8 s for 10000
        return self.board.fen().split(" ")[0]

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
            "turn": int(self.turn),
            "white_moves_total": self.white_moves_total,
            "white_attacks": self.white_attacks,
            "white_castling_rights": self.white_castling_rights,
            "white_check": int(self.white_in_check),
            "black_moves_total": self.black_moves_total,
            "black_attacks": self.black_attacks,
            "black_castling_rights": self.black_castling_rights,
            "black_check": int(self.black_in_check),
        }
        for piece_count in self.piece_counts:
            features.update({piece_count[0]: piece_count[1]})
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
