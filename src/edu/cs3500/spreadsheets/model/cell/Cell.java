package edu.cs3500.spreadsheets.model.cell;

import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import java.util.HashSet;
import java.util.Hashtable;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;

/**
 * Represents one cell in a spreadsheet.
 * TODO: Consider making this an abstract class, since the only thing that changes between
 *       implementations is the evaluate() method. Both also have value and cellString fields.
 */
public interface Cell {

  /**
   * Evaluates the cell.
   * @param cells The coordinate to cell mapping of the spreadsheet
   * @param values The formulas which have already been computed (for optimization)
   * @return The evaluated value
   */
  Value evaluate(Hashtable<Coord, Cell> cells, Hashtable<Formula, Value> values);


  /**
   * Gets the string which the user typed into the cell in order to give it it's value or function.
   * @return The string
   */
  String getRawContents();

  /**
   * Determines whether this cell or any cell that it references contains a cyclical reference.
   * @param visitedCoords The coordinates of the cells visited so far. Note: This is local scoping,
   *                      so it is only the cells visited in the chain containing this cell. It is
   *                      to determine if this cell is part of a cyclical reference.
   * @param cells The coordinate to cell mapping of the spreadsheet
   * @param coordsNoCycle The coordinates of the cells which have already been evaluated and are
   *                      certain to not be part of a cycle (global scoping, not local).
   * @return Whether or not there is a cyclical reference
   */
  boolean containsCyclicalReference(HashSet<Coord> visitedCoords, Hashtable<Coord, Cell> cells,
      HashSet<Coord> coordsNoCycle);
}
