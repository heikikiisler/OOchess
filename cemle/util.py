from timeit import default_timer as timer
import logging


class Timer:
    def __init__(self):
        self.start = timer()

    def start(self):
        self.start = timer()

    def print_progress(self, print_message):
        print("{}{:.2f} seconds".format(print_message, self.get_progress()))

    def get_progress(self):
        return timer() - self.start

    def get_progress_formatted(self):
        return "{:.2f}".format(self.get_progress())


logger = logging.getLogger("[cemle] ")
logging.basicConfig(level=logging.INFO)


def log(message):
    logger.info(msg=message)
