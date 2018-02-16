import pandas as pd
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split

from cemle import config

df = pd.read_csv(config.features_csv_path)

headers = list(df)[1:-1]

X = df[headers]
y = df["evaluation"]

X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=0)

lr = LinearRegression(fit_intercept=True, normalize=True, n_jobs=-1)
lr.fit(X_train, y_train)

score = lr.score(X_test, y_test)
coefficients = [int(i) for i in list(lr.coef_)]


def get_comma_separated_string_from_list(input_list):
    output = ""
    for string in input_list:
        output += str(string) + ","
    return output[:-1]


with open(file=config.linear_regression_coefficients_path, mode="w") as coefficients_file:
    coefficients_file.write(get_comma_separated_string_from_list(headers))
    coefficients_file.write("\n")
    coefficients_file.write(get_comma_separated_string_from_list(coefficients))
