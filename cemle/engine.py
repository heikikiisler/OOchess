import random
import chess


class Engine:
    PIECE_VALUES = {"P": 1, "p": -1, "B": 3, "b": -3, "N": 3, "n": -3, "R": 5, "r": -5, "Q": 9, "q": -9}
    MAX_VALUE = 2147483647
    MIN_VALUE = -2147483648

    def __init__(self, board, depth):
        self.board = board
        self.depth = depth

    def get_best_move(self):
        if self.board.is_game_over():
            return None
        sorted_moves = self.get_evaluated_moves_alpha_beta()
        # if self.board.turn == chess.WHITE:
        print(self.board.turn)
        print("Higher scores are also better for black")
        print(sorted_moves)
        return sorted_moves[0][0]
        # Get lowest value if black:
        # return sorted_moves[len(sorted_moves) - 1][0]

    def get_board_evaluation(self):
        """Gets board evaluation, will be implemented by learning results.

        Temporary implementation to develop engine.

        """
        if self.board.is_game_over():
            result = self.board.result()
            if result == "1-0":
                return self.MAX_VALUE
            elif result == "0-1":
                return self.MIN_VALUE
            elif result == "1/2-1/2":
                return 0
        # random.seed(self.board.fen())
        # random_value = random.uniform(-0.25, 0.25)
        return self.get_material_value()  # + random_value

    def get_board_evaluation_by_side(self):
        if self.board.turn == chess.WHITE:
            return self.get_board_evaluation()
        return -self.get_board_evaluation()

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

    def negamax(self, move, depth):
        self.board.push(move)
        if depth == 0:
            return self.get_board_evaluation_by_side()
        max_value = self.MIN_VALUE
        for move in self.board.legal_moves:
            score = -self.negamax(move, depth - 1)
            self.board.pop()
            if score > max_value:
                max_value = score
        return max_value

    def alpha_beta(self, alpha, beta, depth):
        if depth == 0:
            return self.quiescence_search(alpha, beta)
            # return self.get_board_evaluation_by_side()
        for move in self.board.legal_moves:
            self.board.push(move)
            score = -self.alpha_beta(-beta, -alpha, depth - 1)
            self.board.pop()
            if score >= beta:
                return beta
            if score > alpha:
                alpha = score
        return alpha

    def quiescence_search(self, alpha, beta):
        static_evaluation = self.get_board_evaluation_by_side()
        if static_evaluation >= beta:
            return beta
        if static_evaluation > alpha:
            alpha = static_evaluation
        for move in self.get_captures():
            self.board.push(move)
            score = -self.quiescence_search(-beta, -alpha)
            self.board.pop()
            if score >= beta:
                return beta
            if score > alpha:
                alpha = score
        return alpha

    def get_evaluated_moves(self):
        moves = []
        for move in self.board.legal_moves:
            score = self.negamax(move, self.depth)
            moves.append((move, score))
            self.board.pop()
        # Sort moves with evaluations highest to lowest
        moves.sort(key=lambda move_pairs: move_pairs[1], reverse=True)
        return moves

    def get_evaluated_moves_alpha_beta(self):
        moves = []
        for move in self.board.legal_moves:
            self.board.push(move)
            score = -self.alpha_beta(self.MIN_VALUE, self.MAX_VALUE, self.depth)
            self.board.pop()
            moves.append((move, score))
        # Sort moves with evaluations best to worst
        moves.sort(key=lambda move_pairs: move_pairs[1], reverse=True)
        return moves

    def get_captures(self):
        return [move for move in self.board.legal_moves if self.board.is_capture(move)]
