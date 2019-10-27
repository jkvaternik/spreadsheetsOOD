package edu.cs3500.spreadsheets.model.cell.formula;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.value.Value;

import java.util.Hashtable;
import java.util.List;

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
  public Value evaluate(Hashtable<Coord, Cell> spreadsheet) {
    return null;
  }
}
