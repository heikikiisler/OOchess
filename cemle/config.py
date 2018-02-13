import configparser


class Config:
    def __init__(self, config_path):
        self.config = configparser.ConfigParser()
        self.config.read(config_path)

        self.evaluations_path = self.config.get("file_paths", "engine_evaluations")
        self.uci_moves_path = self.config.get("file_paths", "uci_moves")
        self.fen_evaluations_path = self.config.get("file_paths", "fen_evaluations")
        self.processed_uci_moves_path = self.config.get("file_paths", "processed_uci_moves")
        self.features_csv_path = self.config.get("file_paths", "features_csv")
