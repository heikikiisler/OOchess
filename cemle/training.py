import pandas as pd
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression, Ridge
from sklearn.model_selection import train_test_split

from cemle import config

lines_range = (0, 100000)

df = pd.read_csv(filepath_or_buffer=config.features_csv_path,
                 skiprows=(range(1, lines_range[0])),
                 nrows=(lines_range[1] - lines_range[0])
                 )

# Skip fen and evaluation
headers = list(df)[1:-1]
X = df[headers]
y = df["evaluation"]

X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=0)

lr = LinearRegression(fit_intercept=True, normalize=True)
lr.fit(X_train, y_train)
coefficients = [round(i, 3) for i in list(lr.coef_)]


def plotting():
    scores = []
    alphas = []
    for i in range(0, 20):
        alpha = 0.5 ** i
        lr = Ridge(fit_intercept=True, normalize=True, tol=0.01, alpha=alpha)
        lr.fit(X_train, y_train)
        score = lr.score(X_test, y_test)
        print("alpha: {}, score: {}".format(alpha, score))
        print(coefficients)
        if score > -1:
            scores.append(score)
            alphas.append(alpha)
    plt.figure()
    plt.xlabel("alpha")
    plt.ylabel("score")
    plt.scatter(alphas, scores)
    plt.xticks(alphas)
    plt.show()


def get_comma_separated_string_from_list(input_list):
    output = ""
    for string in input_list:
        output += str(string) + ","
    return output[:-1]


with open(file=config.linear_regression_coefficients_path, mode="w") as coefficients_file:
    coefficients_file.write(get_comma_separated_string_from_list(headers))
    coefficients_file.write("\n")
    coefficients_file.write(get_comma_separated_string_from_list(coefficients))
