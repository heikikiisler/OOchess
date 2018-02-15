import chess

from cemle import engine


class UciEngine:
    def __init__(self):
        self.engine = engine.get_default()

    def process_uci(self):
        """Process UCI commands

        TODO: Add support for commands:

        uci
        isready
        ucinewgame
        go
        position
        stop
        quit
        ...

        """
        uci_input = input()

        if "moves" in uci_input:
            moves_input = uci_input.split(" ")
            opponent_move = moves_input[len(moves_input) - 1]
            self.engine.board.push(chess.Move.from_uci(opponent_move))
            best_move = self.engine.get_best_move()
            best_move_uci = best_move.uci()
            self.engine.board.push(best_move)
            print("bestmove " + best_move_uci)
        elif "uci" == uci_input:
            print("uciok")
        elif "isready" == uci_input:
            print("readyok")
        elif "ucinewgame" in uci_input:
            self.engine.board = chess.Board()

        self.process_uci()

    def send_uci(self, command):
        """Send UCI commands

        TODO: Add support for commands:

        uciok
        readyok
        ...

        """
        return
