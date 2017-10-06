# CEMLE - Chess Engine Machine Learning Experiment

Experimenting with machine learning applications on a simple chess engine.

### Overview

The aim of this project is to first create a simple (and probably not very
good) chess engine. The engine will then be experimented on by machine
learning algorithms and a reputable chess engine in the role of an evaluator
and/or opponent. This will hopefully have a positive impact on the performance
of the engine.

### Goals

- [x] Chess engine possible moves search
- [ ] Chess engine best move search with _n_ depth
- [ ] Chess engine UCI protocol game implementation
- [ ] Machine learning experimenting with config parameters
- [ ] Beat amateur player


### TODO

Chess engine:
* _n_ depth branches
* Filter moves that leave king in check
* 3 move draw rule (move history?)
* Pruning search tree
* Unit tests for move methods
* Benchmarking tests
* Save PGN for every game

Data preparation:
* Obtain suitable board position dataset
* Add evaluation parameters _X_ (material balance, available moves count, ...)
* Add engine evaluation _y_

Machine learning:
* Train on data (probably with linear regression)
* Link to chess evaluation config
