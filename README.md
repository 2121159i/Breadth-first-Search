# Breadth-first-Search
Uses the Breadth-first search and BackTracking to find shortest path between two words from an input dictionary file.

The backtracking algorithm uses the adjacent nodes of every vertex to find the shortest path. The container for this elements is a simple queue. Initially, I put whole wordladders of vertices in this structure, but since that used memory than needed, I changed it to hold simple vertices and setting predecessors which are then used when printing the correct output.

Create a graph using the dictionary file words5.txt and then using that graph and bfs (breadth-first search) algorithm find the shortest path. Print the path between the two words.

If there is no path between the two words, the appropriate message gets printed.

Use the dictionary file words5.txt to test the program.
