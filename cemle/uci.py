import chess

from cemle import engine
from cemle.util import log


class UciEngine:
    def __init__(self):
        self.engine = engine.get_default()

    def process_uci(self):
        """Process UCI commands

        TODO: Add support for commands:

        go
        position
        stop
        ...

        """
        uci_input = input()
        if "position startpos" in uci_input:
            moves_input = uci_input.split(" ")
            self.engine.reset()
            if len(moves_input) > 3:
                for i in range(3, len(moves_input)):
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
            # TODO: Stop and move
            log("Stopping not implemented yet")
        elif "isready" == uci_input:
            print("readyok")
        elif "ucinewgame" in uci_input:
            self.engine.reset()
        else:
            log("Could not process command: " + uci_input)

        self.process_uci()

    def move_best_move(self):
        best_move = self.engine.get_best_move()
        best_move_uci = best_move.uci()
        self.engine.board.push(best_move)
        print("bestmove " + best_move_uci)
