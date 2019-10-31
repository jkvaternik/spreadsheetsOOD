package edu.cs3500.spreadsheets.model.cell.formula;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.BlankCell;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.formula.function.FormulaVisitor;
import edu.cs3500.spreadsheets.model.cell.formula.value.ErrorValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

/**
 * Represents a reference to one or more spreadsheet cells.
 * TODO: Consider having two subclasses, one for single references and one for multi references.
 */
public class CellReference implements Formula {
  Coord from;
  Coord to;

  /**
   * Creates a cell reference based on the boundary coordinates of the reference region.
   * Note: If from.equals(to), that represents just a single cell reference.
   * @param from The from coord (top left of rectangle)
   * @param to The to coord (bottom right of rectangle)
   */
  public CellReference(Coord from, Coord to) {
    this.from = from;
    this.to = to;
  }

  @Override
  public Value evaluate(Hashtable<Coord, Cell> cells, Hashtable<Formula, Value> values) {
    if (values.containsKey(this)) {
      return values.get(this);
    }
    List<Cell> refs = this.getAllCells(cells);
    if (refs.size() == 1) {
      Value value = refs.get(0).evaluate(cells, values);
      values.put(this, value);
      return value;
    } else {
      values.put(this,
          new ErrorValue(new IllegalArgumentException("Can't evaluate a multi-reference")));
      return new ErrorValue(new IllegalArgumentException("Can't evaluate a multi-reference"));
    }
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitCellReference(this);
  }

  @Override
  public boolean containsCyclicalReference(HashSet<Coord> visitedCoords,
      Hashtable<Coord, Cell> cells, HashSet<Coord> coordsNoCycle) {
    for (Coord coord : this.getAllCoords()) {
      if (cells.containsKey(coord) && !coordsNoCycle.contains(coord)) {
        if (visitedCoords.contains(coord)) {
          return true;
        }
        HashSet<Coord> newVisited = new HashSet<>(visitedCoords);
        newVisited.add(coord);
        if (cells.get(coord).containsCyclicalReference(newVisited, cells, coordsNoCycle)) {
          return true;
        } else {
          coordsNoCycle.add(coord);
        }
      }
    }
    return false;
  }

  private List<Coord> getAllCoords() {
    int fromCol = from.col;
    int fromRow = from.row;
    int toCol = to.col;
    int toRow = to.row;
    Coord current;
    List<Coord> allCoords = new ArrayList<>();

    for (int colIndex = fromCol; colIndex <= toCol; colIndex += 1) {
      for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex += 1) {
        current = new Coord(colIndex, rowIndex);
        allCoords.add(current);
      }
    }
    return allCoords;
  }

  /**
   * Gets all of the cells that are part of this cell reference, based on the given cells in the
   * spreadsheet.
   * @param cells The cells at each coordinate in the spreadsheet
   * @return All of the cells in this reference
   */
  //TODO: How do we not make this public???
  public List<Cell> getAllCells(Hashtable<Coord, Cell> cells) {
    int fromCol = from.col;
    int fromRow = from.row;
    int toCol = to.col;
    int toRow = to.row;
    Coord current;
    List<Cell> allCells = new ArrayList<>();

    for (int colIndex = fromCol; colIndex <= toCol; colIndex += 1) {
      for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex += 1) {
        current = new Coord(colIndex, rowIndex);
        if (cells.containsKey(current)) {
          allCells.add(cells.get(current));
        } else {
          allCells.add(new BlankCell());
        }
      }
    }

    return allCells;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof CellReference) {
      return this.from.equals(((CellReference) other).from)
          && this.to.equals((((CellReference) other).to));
    } else {
      return false;
    }
  }

  public int hashCode() {
    return Objects.hash(this.from, this.to);
  }

  public String toString() {
    return this.from.toString() + ", " + this.to.toString();
  }
}
