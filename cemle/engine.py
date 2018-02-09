import random


class Engine:
    PIECE_VALUES = {"P": 1, "p": -1, "B": 3, "b": -3, "N": 3, "n": -3, "R": 5, "r": -5, "Q": 9, "q": -9}

    def __init__(self, board):
        self.board = board

    def get_best_move(self):
        return random.choice(list(self.board.legal_moves))

    def move_best_move(self):
        self.board.push(self.get_best_move())

    def get_board_evaluation(self):
        return self.get_material_value()

    def get_material_value(self):
        material_sum = 0
        fen_string = self.get_fen_piece_string()
        for i in range(0, len(fen_string)):
            char = fen_string[i]
            if char in self.PIECE_VALUES.keys():
                material_sum += self.PIECE_VALUES[char]
        return material_sum

    def get_fen_piece_string(self):
        return self.board.fen().split(" ")[0]
