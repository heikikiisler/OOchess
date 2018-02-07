# CEMLE - Chess Engine Machine Learning Experiment

Experimenting with machine learning applications on a simple chess engine.

### Overview

The aim of this project is to first create a simple (and probably not very
good) chess engine. The engine will then be experimented on by machine
learning algorithms and a reputable chess engine in the role of an evaluator
and/or opponent. This will hopefully have a positive impact on the performance
of the engine.

### Goals

- [ ] Chess engine UCI protocol game implementation
- [ ] Machine learning experimenting with config parameters
- [ ] Beat amateur player


### TODO

Data preparation:
* Obtain suitable board position dataset
* Add evaluation parameters _X_ (material balance, available moves count, ...)
* Add engine evaluation _y_

Machine learning:
* Train on data (probably with linear regression)
* Link to chess evaluation config
