from cemle import data, config


def get_coefficients():
    with open(file=config.linear_regression_coefficients_path, mode="r") as file:
        file.readline()
        return file.readline().split(",")


LINEAR_REGRESSION_COEFFICIENTS = get_coefficients()


def get_linear_regression_evaluation(board):
    features = list(data.BoardFeatureExtractor(board).get_features().values())[1:]
    evaluation = 0
    evaluation_pairs = zip(features, LINEAR_REGRESSION_COEFFICIENTS)
    for evaluation_pair in evaluation_pairs:
        evaluation += int(evaluation_pair[0]) * int(evaluation_pair[1])
    return evaluation
