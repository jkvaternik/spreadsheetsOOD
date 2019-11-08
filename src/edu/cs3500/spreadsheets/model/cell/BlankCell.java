package edu.cs3500.spreadsheets.model.cell;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;

/**
 * Represents a blank cell.
 */
public class BlankCell implements Cell {

  @Override
  public void evaluate(Hashtable<Coord, Cell> cells, Hashtable<Formula, Value> values) {
    //Do nothing, since BlankCell's always have a constant value
  }

  @Override
  public String getRawContents() {
    return null;
  }

  @Override
  public boolean containsCyclicalReference(HashSet<Coord> visitedCoords,
                                           Hashtable<Coord, Cell> cells,
                                           HashSet<Coord> coordsNoCycle) {
    return false;
  }

  @Override
  public boolean referencesCell(Coord coord) {
    return false;
  }

  @Override
  public Value getValue() {
    return new StringValue("");
  }

  @Override
  public boolean equals(Object other) {
    return other instanceof BlankCell;
  }

  @Override
  public int hashCode() {
    return Objects.hash("blank cell");
  }

  @Override
  public String toString() {
    return "";
  }
}
