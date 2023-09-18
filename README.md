# TravelingSalesPerson
For CSCI-4463 Artifical Intelligence at Stockton University

  - This repository contains several Java programs that attempt to solve the Traveling Sales Person (TSP) problem.
  - The TSP problem is: Given a list of cities and the distances between each pair of cities, what is the shortest possible tour that visits each city exactly once?
  - The only java class that successfully does this is 'VBSS.java'. VBSS stands for Value Biased Stochastic Sampling (VBSS) and is the only successful attempt at solving the specific TSP instances for this assignment.
  - VBSS uses a heuristic (a bias) assigned to every single city in the problem. This heuristic is based on the distance between the initial city and its Euclidean distance from every other city. Closer cities are more likely to be chosen rather than farther cities. 

