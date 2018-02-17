from timeit import default_timer as timer


class Timer:
    def __init__(self):
        self.start = timer()

    def start(self):
        self.start = timer()

    def print_progress(self, print_message):
        print("{}{}".format(print_message, self.get_progress()))

    def get_progress(self):
        return timer() - self.start
