package edu.cs3500.spreadsheets.model.cell.formula;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.BlankCell;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;

import java.util.ArrayList;
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

  /**
   * Gets all of the cells that are part of this cell reference, based on the given cells in the
   * spreadsheet.
   * @param cells The cells at each coordinate in the spreadsheet
   * @return All of the cells in this reference
   */
  private List<Cell> getAllCells(Hashtable<Coord, Cell> cells) {
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
        }
      }
    }

    return allCells;
  }
}
