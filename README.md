# CEMLE - Chess Engine Machine Learning Experiment

Experimenting with machine learning applications on a simple chess engine.

### Overview

The aim of this project is to first create a simple (and probably not very
good) chess engine with the help of the [python-chess library](https://github.com/niklasf/python-chess). The engine will then be experimented on by machine
learning algorithms and a reputable chess engine in the role of an evaluator
and/or opponent. This will hopefully have a positive impact on the performance
of the engine.

### Goals

- [ ] Simple chess engine with UCI protocol
- [ ] Machine learning experimenting with config parameters
- [ ] Beat amateur player

### TODO

Data preparation:
* Obtain suitable board position data set
* Add evaluation parameters _X_ (material balance, available moves count, ...)
* Add engine evaluation _y_

Machine learning:
* Train on data (probably with linear regression)
* Link to chess evaluation config

###License

CEMLE is licensed under the GPL 3 as it depends on python-chess. Full information in LICENSE.txt.
