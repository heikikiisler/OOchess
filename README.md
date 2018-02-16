# CEMLE - Chess Engine Machine Learning Experiment

Experimenting with machine learning models on a simple chess engine.

### Overview

The aim of this project is to create a simple (and probably not very
good) chess engine with the help of the [python-chess library](https://github.com/niklasf/python-chess). 
The board evaluation function will then be created by learning from a data set 
containing board positions with corresponding Stockfish engine evaluations.

So far the prototype linear regression model can play decent chess and beat a player with only basic chess knowledge.

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

### Dependencies

* Python 3.6
* [python-chess](https://github.com/niklasf/python-chess) by [niklasf](https://github.com/niklasf)
* [scikit-learn](http://scikit-learn.org) v0.19.1
* [pandas](https://pandas.pydata.org/) v0.21
* [Learning data set](https://www.kaggle.com/c/finding-elo/data) from Kaggle

### License

CEMLE is licensed under the GPL 3 as it depends on python-chess. Full information in LICENSE.txt.
