from collections import OrderedDict

import chess
from chess.polyglot import zobrist_hash

from cemle import config, evaluation, util
from cemle.util import log


class Engine:
    """
    Provides best move search and other engine related functionality.

    """

    MAX_VALUE = 2147483647
    MIN_VALUE = -2147483648

    def __init__(self, board, min_depth, max_depth, max_time):
        """
        Creates engine object.

        :param board: python-chess Board object.
        :param min_depth: minimum depth to be searched before returning evaluation.
        :param max_depth: maximum depth to be searched before returning evaluation.
        :param max_time: maximum time spent on a move. Overridden by min_depth.
        """
        self.board = board
        self.side = chess.WHITE if self.board.turn == chess.WHITE else chess.BLACK
        self.min_depth = min_depth
        self.max_depth = max_depth
        self.max_time = max_time
        self.move_timer = util.Timer()
        self.zobrist_table = {} # Python's dict is implemented as a hash table
        self.current_best_move = None
        self.previous_iteration_ordered_moves = {}
        self.achieved_depth = -1
        self.time_left = 300000
        self.opponent_time_left = 300000
        self.stopped = False

    def get_best_move(self):
        """
        Gets best move from current position.

        :return: Best move as a python-chess Move object.
        """
        self.reset(reset_board=False)
        if self.board.is_game_over():
            return None
        sorted_moves = self.get_iterative_best_moves_alpha_beta()
        log(sorted_moves)
        return list(sorted_moves.keys())[0]

    def get_board_evaluation(self):
        """
        Gets the absolute board evaluation.

        :return: Evaluation value, higher better for white, lower better for black.
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
        """
        Gets the relative board evaluation.

        :return: Evaluation value, higher better for current side (white or black).
        """
        return self.get_board_evaluation() if self.board.turn == chess.WHITE else -self.get_board_evaluation()

    def alpha_beta(self, alpha, beta, depth):
        """
        Get board evaluation by using Alpha-Beta.

        :param alpha: initial alpha value
        :param beta: initial beta value
        :param depth: search depth
        :return: relative evaluation
        """
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
        """
        Gets the "quiet" evaluation of the position.

        All captures on the board will be evaluated to overcome the horizon effect.

        :param alpha: initial alpha value
        :param beta: initial beta value
        :return: quiet evaluation of the position
        """
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
        """
        Evaluates all possible moves using Alpha-Beta.

        :param depth: search depth
        :return: dict of moves and corresponding Alpha-Beta evaluations
        """
        moves = {}
        if len(self.previous_iteration_ordered_moves) > 0:
            legal_moves = self.previous_iteration_ordered_moves.keys()
        else:
            legal_moves = self.board.legal_moves
        for move in legal_moves:
            self.board.push(move)
            score = -self.alpha_beta(self.MIN_VALUE, self.MAX_VALUE, depth)
            self.board.pop()
            moves[move] = score
            if self.can_keep_searching():
                log("Exceeded max_time of {}s, current thinking time {}s".format(
                    self.max_time,
                    self.move_timer.get_progress_formatted()))
                break
        # Sort moves with evaluations best to worst
        return OrderedDict(sorted(moves.items(), key=lambda v: v[1], reverse=True))

    def get_iterative_best_moves_alpha_beta(self):
        """
        Gets move evaluations using iterative deepening.

        :return: dict of moves and corresponding Alpha-Beta evaluations
        """
        evaluations = {}
        for depth in range(0, self.max_depth):
            self.previous_iteration_ordered_moves = self.get_evaluated_moves_alpha_beta(depth)
            evaluations.update(self.previous_iteration_ordered_moves)
            evaluations = OrderedDict(sorted(evaluations.items(), key=lambda move: move[1], reverse=True))
            self.achieved_depth = depth
        return evaluations

    def get_captures(self):
        return [move for move in self.board.legal_moves if self.board.is_capture(move)]

    def stop_and_get_best_move(self):
        """Stop thinking and return currently best move.

        TODO: Implement

        """
        return

    def reset(self, reset_board):
        """
        Resets the engine with previously set config.

        :param reset_board: will also reset the board if True.
        """
        if reset_board:
            self.board = chess.Board()
        self.move_timer = util.Timer()
        self.current_best_move = None
        self.previous_iteration_ordered_moves = {}
        self.achieved_depth = -1

    def get_zobrist_hash(self):
        """Gets Zobrist hash of the board."""
        return zobrist_hash(self.board)

    def save_hash_evaluation(self, board_evaluation):
        """
        Saves board evaluation for corresponding Zobrist hash
        :param board_evaluation: absolute evaluation
        :return:
        """
        self.zobrist_table.update({self.get_zobrist_hash(), board_evaluation})

    def has_table_evaluation(self, board_hash):
        """Check if hash table contains evaluation for hash"""
        return board_hash in self.zobrist_table

    def set_times_left(self, white_time, black_time):
        """
        Sets time left for sides (in ms).

        TODO: use times in evaluation depth logic

        """
        if self.side == chess.WHITE:
            self.time_left = white_time
            self.opponent_time_left = black_time
        else:
            self.time_left = black_time
            self.opponent_time_left = white_time

    def can_keep_searching(self):
        """Check if engine should keep searching."""
        return not self.stopped and \
            self.achieved_depth >= self.min_depth and \
            self.max_time < self.move_timer.get_progress()


def get_default():
    """
    Gets default engine with config parameters.

    Board will have the starting position.

    """
    return Engine(chess.Board(), config.engine_min_depth, config.engine_max_depth, config.engine_max_move_time)
