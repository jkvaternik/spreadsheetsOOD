package edu.cs3500.spreadsheets.model.graph;

/**
 * Represents a graph.
 * @param <K> The type of the graph's nodes.
 */
public interface Graph<K> {

  /**
   * Adds a node with no edges to this graph, if it is not already in it.
   * @param node The node to be added
   * @throws IllegalArgumentException if the node already is in the graph
   */
  void addNode(K node) throws IllegalArgumentException;

  /**
   * Adds an edge from one node to another, if the from node exists but not the edge.
   * @param from The from node.
   * @param to The to node.
   * @throws IllegalArgumentException if the edge already exists or if the from node does not exist.
   */
  void addEdge(K from, K to);

  /**
   * Removes the edge if it exists. If it doesn't, throw an exception.
   * @param from the from node
   * @param to the to node
   * @throws IllegalArgumentException if the edge does not exist or if the from node does not exist.
   */
  void removeEdge(K from, K to) throws IllegalArgumentException;

  /**
   * Removes all edges from the given node, if that node exists in the graph.
   * @param node the node to remove all edges
   * @throws IllegalArgumentException if the node does not exist
   */
  void removeAllEdges(K node) throws IllegalArgumentException;

  /**
   * Removes the node from the graph if it exists, along with all of its outward edges.
   * @param node The node to remove
   * @throws IllegalArgumentException if the node does not exist
   */
  void removeNode(K node) throws IllegalArgumentException;

  /**
   * Determines whether or not the given node exists in the graph.
   * @param node The node
   * @return Whether or not it is in the graph
   */
  boolean containsNode(K node);

  /**
   * Determines whether or not an edge exists from one node to another.
   * @param from The from node
   * @param to The to node
   * @return Whether or not the edge exists.
   */
  boolean containsEdge(K from, K to);

  /**
   * Determines if there exists a cycle containing the given node or one of it's references.
   * @param node The node of interest.
   * @return Whether or not there is a cycle.
   */
  boolean containsCycle(K node);
}
