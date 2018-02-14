from timeit import default_timer as timer


class Timer:
    def __init__(self, print_message):
        self.print_message = print_message
        self.start = timer()

    def start(self):
        self.start = timer()

    def print_progress(self):
        print("{}{}".format(self.print_message, timer() - self.start))
