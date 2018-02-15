import configparser

config_path = "../config.ini"
config = configparser.ConfigParser()
config.read(config_path)

# File paths
evaluations_path = config.get("file_paths", "engine_evaluations")
uci_moves_path = config.get("file_paths", "uci_moves")
fen_evaluations_path = config.get("file_paths", "fen_evaluations")
processed_uci_moves_path = config.get("file_paths", "processed_uci_moves")
features_csv_path = config.get("file_paths", "features_csv")

# Engine parameters
engine_depth = int(config.get("engine", "depth"))
