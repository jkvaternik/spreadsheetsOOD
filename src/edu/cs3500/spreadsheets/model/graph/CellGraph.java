package edu.cs3500.spreadsheets.model.graph;

import edu.cs3500.spreadsheets.model.cell.Cell;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Represents a graph of cells.
 * Class invariant: for every cell that exists as a node in this graph, it's edges will be non-null.
 * Class invariant: A cell will never have multiple edges to the same cell.
 */
public class CellGraph implements Graph<Cell> {
  Hashtable<Cell, List<Cell>> adjList;

  public CellGraph() {
    this.adjList = new Hashtable<>();
  }

  @Override
  public void addNode(Cell node) throws IllegalArgumentException{
    if (this.adjList.containsKey(node)) {
      throw new IllegalArgumentException("This node is already in the graph.");
    } else {
      this.adjList.put(node, new ArrayList<>());
    }
  }

  @Override
  public void addEdge(Cell from, Cell to) throws IllegalArgumentException{
    if (this.containsNode(from)) {
      List<Cell> edges = this.adjList.get(from);
      if (edges.contains(to)) {
        throw new IllegalArgumentException("This edge already exists");
      } else {
        edges.add(to);
      }
    } else {
      throw new IllegalArgumentException("The from node does not exist.");
    }
  }

  @Override
  public void removeEdge(Cell from, Cell to) throws IllegalArgumentException {
    if (this.containsNode(from)) {
      List<Cell> edges = this.adjList.get(from);
      if (!edges.contains(to)) {
        throw new IllegalArgumentException("This edge does not exist");
      } else {
        edges.remove(to);
      }
    } else {
      throw new IllegalArgumentException("The from node does not exist.");
    }
  }

  @Override
  public void removeAllEdges(Cell node) throws IllegalArgumentException {
    if (this.containsNode(node)) {
      List<Cell> edges = this.adjList.get(node);
      edges.clear();
    } else {
      throw new IllegalArgumentException("The from node does not exist.");
    }
  }

  @Override
  public void removeNode(Cell node) throws IllegalArgumentException {
    if (!this.adjList.containsKey(node)) {
      throw new IllegalArgumentException("This node does not exist in the graph.");
    } else {
      this.adjList.remove(node);
    }
  }

  @Override
  public boolean containsNode(Cell node) {
    return this.adjList.containsKey(node);
  }

  @Override
  public boolean containsEdge(Cell from, Cell to) {
    return this.containsNode(from) && this.adjList.get(from).contains(to);
  }

  @Override
  public boolean containsCycle(Cell node) {
    List<Cell> visited = new ArrayList<>();
    return this.containsCycleAccum(node, visited);
  }

  /**
   * Determines if there is a cycle containing the given cell or any of its references
   * @param node The node of interest
   * @param visited The cells already visited
   * @return Whether or not there is a cycle
   */
  private boolean containsCycleAccum(Cell node, List<Cell> visited) {
    if (visited.contains(node)) {
      return true;
    } else {
      visited.add(node);
      List<Cell> references = this.adjList.get(node);
      for (Cell c : references) {
        if (this.containsCycleAccum(c, visited)) {
          return true;
        }
      }
      return false;
    }
  }
}
