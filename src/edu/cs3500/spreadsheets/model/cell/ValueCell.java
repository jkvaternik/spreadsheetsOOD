package edu.cs3500.spreadsheets.model.cell;

import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import java.util.HashSet;
import java.util.Hashtable;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;

/**
 * Represents a cell which contains one of the accepted types of values.
 */
public class ValueCell implements Cell {
  private String rawContents;
  private Value value;

  /**
   * Creates an instance of a value cell from the given cell string and value.
   * @param rawContents The string
   * @param value The value
   */
  public ValueCell(String rawContents, Value value) {
    this.rawContents = rawContents;
    this.value = value;
  }

  @Override
  public Value evaluate(Hashtable<Coord, Cell> cells, Hashtable<Formula, Value> values) {
    return this.value;
  }

  @Override
  public String getRawContents() {
    return this.rawContents;
  }

  @Override
  public boolean containsCyclicalReference(HashSet<Coord> visitedCoords,
      Hashtable<Coord, Cell> cells, HashSet<Coord> coordsNoCycle) {
    return false;
  }
}
