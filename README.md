# CEMLE - Chess Engine Machine Learning Experiment

Experimenting with machine learning models on a simple chess engine.

### Overview

The aim of this project is to create a simple (and probably not very
good) chess engine with the help of the [python-chess library](https://github.com/niklasf/python-chess). 
The board evaluation function will then be created by learning from a data set 
containing board positions with corresponding Stockfish engine evaluations.

So far the prototype linear regression model can play decent chess and beat a player with only basic chess knowledge.
Example linear regression coefficients are located in _coefficients.csv.sample_.

### Goals

Engine:
- [x] Best move search with _n_ depth using negamax
- [x] Search tree alpha-beta pruning
- [x] Quiescence search
- [x] UCI protocol communication
- [ ] Beat amateur player

Learning, board position evaluation function:
- [x] Chess positions and evaluation data set preparation
- [x] Board parameters extraction
- [x] Linear regression model
- [ ] Explore other models

### How to use

Copy _properties.conf.sample_ into _properties.conf_ and _coefficients.csv.sample_ into _coefficients.csv_.

To play against the UCI engine, only Python 3.6 and python-chess are needed.
Use _cemle/uci.bat_ or _cemle/uci.sh_ to link engine to [Arena](http://www.playwitharena.com/) or other GUIs.
Alternatively you can play on the command line with UCI commands by executing the same scripts.

A sample of the processed data, linking FEN-s to Stockfish evaluations can be found in _data/fen_evaluations.csv.sample_.

### Dependencies

* Python 3.6
* [python-chess](https://github.com/niklasf/python-chess) v0.22.1
* [scikit-learn](http://scikit-learn.org) v0.19.1
* [pandas](https://pandas.pydata.org/) v0.21
* [Learning data set](https://www.kaggle.com/c/finding-elo/data) from Kaggle

### License

CEMLE is licensed under the GPL 3 as it depends on python-chess. Full information in LICENSE.txt.
