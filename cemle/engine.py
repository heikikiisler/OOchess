import chess

from cemle import config, evaluation


class Engine:
    MAX_VALUE = 2147483647
    MIN_VALUE = -2147483648

    def __init__(self, board, depth):
        self.board = board
        self.depth = depth

    def get_best_move(self):
        if self.board.is_game_over():
            return None
        sorted_moves = self.get_evaluated_moves_alpha_beta()
        print("[cemle] sorted moves: " + str(sorted_moves))
        return sorted_moves[0][0]

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
        return evaluation.get_linear_regression_evaluation(self.board)

    def get_board_evaluation_by_side(self):
        if self.board.turn == chess.WHITE:
            return self.get_board_evaluation()
        return -self.get_board_evaluation()

    def get_fen_piece_string(self):
        return self.board.fen().split(" ")[0]

    def alpha_beta(self, alpha, beta, depth):
        if depth == 0:
            return self.quiescence_search(alpha, beta)
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

    def stop_and_get_best_move(self):
        """Stop thinking and return currently best move.

        TODO: Implement

        """
        return

    def reset(self):
        self.board = chess.Board()


def get_default():
    return Engine(chess.Board(), config.engine_depth)
