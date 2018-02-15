"""This file is used for linking to UCI GUIs."""

import sys

sys.path.append(".")
sys.path.append("..")

from cemle import uci

uci_engine = uci.UciEngine()
uci_engine.process_uci()
