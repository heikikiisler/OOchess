import chess

from cemle import engine
from cemle.util import log


class UciEngine:
    def __init__(self):
        self.engine = engine.get_default()

    def process_uci(self):
        """Process UCI commands."""
        uci_input = input()
        if "position startpos" in uci_input:
            moves_input = uci_input.split(" ")
            self.engine.reset(reset_board=True)
            if len(moves_input) > 3:
                for i in range(3, len(moves_input)):
                    self.engine.board.push(chess.Move.from_uci(moves_input[i]))
            self.move_best_move()
        elif "position fen" in uci_input:
            moves_input = uci_input.split(" ")
            fen = ""
            for i in range(2, 8):
                fen += moves_input[i] + " "
            self.engine.reset(reset_board=True)
            self.engine.board = chess.Board(fen=fen)
            for i in range(9, len(moves_input)):
                self.engine.board.push(chess.Move.from_uci(moves_input[i]))
            self.move_best_move()
        elif "uci" == uci_input:
            print("uciok")
            print("id name cemle")
            print("id author Heiki Rein Kiisler")
        elif "quit" == uci_input:
            log("Quitting, bye!")
            quit()
        elif "stop" == uci_input:
            # TODO: Still must be executed in new thread
            log("Stopping as soon as possible")
            self.engine.stopped = True
        elif "isready" == uci_input:
            print("readyok")
        elif "ucinewgame" in uci_input:
            self.engine.reset(reset_board=True)
        elif "go" in uci_input:
            moves_input = uci_input.split(" ")
            if "wtime" in uci_input and "btime" in uci_input:
                white_time = moves_input[moves_input.index("wtime") + 1]
                black_time = moves_input[moves_input.index("btime") + 1]
                self.engine.set_times_left(white_time=white_time, black_time=black_time)
        else:
            log("Could not process command: " + uci_input)

        self.process_uci()

    def move_best_move(self):
        best_move = self.engine.get_best_move()
        best_move_uci = best_move.uci()
        self.engine.board.push(best_move)
        print("bestmove " + best_move_uci)
