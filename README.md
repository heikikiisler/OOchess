# CEMLE - Chess Engine Machine Learning Experiment

Experimenting with machine learning applications on a simple chess engine.

### Overview

The aim of this project is to create a simple (and probably not very
good) chess engine with the help of the [python-chess library](https://github.com/niklasf/python-chess). 
The board evaluation function will be created by learning from a data set 
containing board positions with corresponding engine evaluations.

### Goals

Engine:
- [x] Best move search with _n_ depth using negamax
- [x] Search tree alpha-beta pruning
- [x] Quiescence search
- [ ] UCI protocol interface
- [ ] Beat amateur player

Learning, board position evaluation function:
- [x] Chess positions and evaluation data set preparation
- [ ] Board parameters extraction
- [ ] Evaluation function learning from data

### Dependencies

* [python-chess](https://github.com/niklasf/python-chess) by [niklasf](https://github.com/niklasf)
* Python 3.6
* [pandas](https://pandas.pydata.org/) v0.21
* Learning data set from Kaggle: https://www.kaggle.com/c/finding-elo/data

### License

CEMLE is licensed under the GPL 3 as it depends on python-chess. Full information in LICENSE.txt.
