import random


class Engine:
    def __init__(self, board):
        self.board = board

    def get_best_move(self):
        return random.choice(list(self.board.legal_moves))

    def move_best_move(self):
        self.board.push(self.get_best_move())

    def get_board_evaluation(self):
        return 0
