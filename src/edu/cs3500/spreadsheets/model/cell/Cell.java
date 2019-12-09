package edu.cs3500.spreadsheets.model.cell;

import java.util.HashSet;
import java.util.Hashtable;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;

/**
 * Represents one cell in a spreadsheet.
 */
public interface Cell {

  /**
   * Evaluates the cell.
   *
   * @param cells  The coordinate to cell mapping of the spreadsheet
   * @param values The formulas which have already been computed (for optimization)
   */
  void evaluate(Hashtable<Coord, Cell> cells, Hashtable<Formula, Value> values);


  /**
   * Gets the string which the user typed into the cell in order to give it it's value or function.
   *
   * @return The string
   */
  String getRawContents();

  /**
   * Determines whether this cell or any cell that it references contains a cyclical reference.
   *
   * @param visitedCoords The coordinates of the cells visited so far. Note: This is local scoping,
   *                      so it is only the cells visited in the chain containing this cell. It is
   *                      to determine if this cell is part of a cyclical reference.
   * @param cells         The coordinate to cell mapping of the spreadsheet
   * @param coordsNoCycle The coordinates of the cells which have already been evaluated and are
   *                      certain to not be part of a cycle (global scoping, not local).
   * @return Whether or not there is a cyclical reference
   */
  boolean containsCyclicalReference(HashSet<Coord> visitedCoords, Hashtable<Coord, Cell> cells,
                                    HashSet<Coord> coordsNoCycle);

  /**
   * Determines if this cell references the cell at the given Coord.
   * @param coord The coord of the reference cell.
   * @return Whether or not this cell references the cell at the Coord.
   */
  boolean referencesCell(Coord coord);

  /**
   * Gets the Value of the given cell.
   * @return The Value
   */
  Value getValue();

  /**
   * Gets the raw contents of the result of pasting this cell's contents at the cell with the given
   * column and row change. (i.e. copying (1, 1) to (2, 2) would have colChange and rowChange be 1).
   * @param colChange The change in column coordinate between the paste cell and this cell.
   *                  Negative value represents the paste cell's column is less than this cell's
   *                  coordinate column.
   * @param rowChange The change in row coordinate between the paste cell and this cell.
   *                  Negative value represents the paste cell's row is less than this cell's
   *                  coordinate row.
   * @return The raw contents string for the paste cell which accounts for any non-absolute cell
   *         references.
   */
  String getPasteString(int colChange, int rowChange);
}
