import operator

import chess
from chess.polyglot import zobrist_hash

from cemle import config, evaluation, util
from cemle.util import log


class Engine:
    MAX_VALUE = 2147483647
    MIN_VALUE = -2147483648

    def __init__(self, board, min_depth, max_depth, max_time):
        self.board = board
        self.min_depth = min_depth
        self.max_depth = max_depth
        self.max_time = max_time
        self.move_timer = util.Timer()
        self.zobrist_table = {}
        self.current_best_move = None
        self.achieved_depth = -1

    def get_best_move(self):
        if self.board.is_game_over():
            return None
        sorted_moves = self.get_iterative_best_moves_alpha_beta()
        return list(sorted_moves.keys())[0]

    def get_board_evaluation(self):
        """Gets board evaluation, will be implemented by learning results.

        Temporary implementation to develop engine.

        """
        current_hash = self.get_zobrist_hash()
        if current_hash in self.zobrist_table:
            return self.zobrist_table.get(current_hash)
        if self.board.is_game_over():
            result = self.board.result()
            if result == "1-0":
                return self.MAX_VALUE
            elif result == "0-1":
                return self.MIN_VALUE
            elif result == "1/2-1/2":
                return 0
        current_evaluation = evaluation.get_linear_regression_evaluation(self.board)
        self.zobrist_table[current_hash] = current_evaluation
        return current_evaluation

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

    def get_evaluated_moves_alpha_beta(self, depth):
        moves = {}
        for move in self.board.legal_moves:
            self.board.push(move)
            score = -self.alpha_beta(self.MIN_VALUE, self.MAX_VALUE, depth)
            self.board.pop()
            moves[move] = score
            if self.achieved_depth >= self.min_depth and self.max_time < self.move_timer.get_progress():
                log("Exceeded max_time of {}s, current thinking time {}s".format(
                    self.max_time,
                    self.move_timer.get_progress()))
                break
        # Sort moves with evaluations best to worst
        return sorted(moves.items(), key=operator.itemgetter(1), reverse=True)

    def get_iterative_best_moves_alpha_beta(self):
        evaluations = {}
        for depth in range(0, self.max_depth):
            moves_evaluations = self.get_evaluated_moves_alpha_beta(depth)
            evaluations.update(moves_evaluations)
            self.achieved_depth = depth
        return evaluations

    def get_captures(self):
        return [move for move in self.board.legal_moves if self.board.is_capture(move)]

    def stop_and_get_best_move(self):
        """Stop thinking and return currently best move.

        TODO: Implement

        """
        return

    def reset(self):
        self.board = chess.Board()
        self.move_timer = util.Timer()
        self.zobrist_table = {}
        self.current_best_move = None
        self.achieved_depth = -1

    def get_zobrist_hash(self):
        return zobrist_hash(self.board)

    def save_hash_evaluation(self, board_evaluation):
        self.zobrist_table.update({self.get_zobrist_hash(), board_evaluation})

    def has_table_evaluation(self, board_hash):
        return board_hash in self.zobrist_table


def get_default():
    return Engine(chess.Board(), config.engine_min_depth, config.engine_max_depth, config.engine_max_move_time)
