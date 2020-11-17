# cse373bandwidth

The bandwidth problem takes as input a graph G, with n vertices and m edges (ie. pairs of vertices).
The goal is to find a permutation of the vertices on the line which minimizes the maximum length of any
edge. This is better understood through an example. Suppose G consists of 5 vertices and the edges (v1, v2),
(v1, v3), (v1, v4), and (v1, v5). We must map each vertex to a distinct number between 1 and n. Under the
mapping vi → i, the last edge spans a distance of four (ie. 5-1). However, the permutation {2, 3, 1, 4, 5} 
is better, for none of the edges spans a distance of more than two. In fact, it is a permutation which 
minimizes the maximum length of the edges in G, as is any permutation where 1 is in the center.

My approach to the bandwidth problem is to use dynamic programming to look through every permutation, but 
prune(optimize) to efficiently find the correct solution and to avoid permutations that will not yield the solution.

Interesting Optimizations:
       *Throughout the program it keeps track of the max length bandwidth it has encountered so far. If the  max length bandwidth is greater than the current minimum bandwidth then it abandons that run and moves to the next candidate
       *Once the program gets process_solution, it jumps back to the index in which the minimum of the max length bandwidth was found
       *Sorting the candidates by their degrees in ascending order
       *Converted adjacency list to adjacency matrix
       *Removing the last candidate for the first position
