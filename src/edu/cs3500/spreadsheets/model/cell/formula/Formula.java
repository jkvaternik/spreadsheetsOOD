package edu.cs3500.spreadsheets.model.cell.formula;

import java.util.HashSet;
import java.util.Hashtable;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.formula.function.FormulaVisitor;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;

/**
 * Represents a cell formula. A formula can be a value, a function, or a reference to one or more
 * cells.
 */
public interface Formula {
  /**
   * Evaluates the formula.
   *
   * @param spreadsheet The coordinate to cell mapping of the spreadsheet
   * @param values      The formulas which have already been computed (for optimization)
   * @return the evaluated value
   */
  Value evaluate(Hashtable<Coord, Cell> spreadsheet, Hashtable<Formula, Value> values);

  /**
   * Returns the result of applying the given visitor to this Formula.
   *
   * @param visitor the given FormulaVisitor
   * @param <R>     The value type returned by the visitor (not Value)
   * @return the result of applying the given visitor to this Formula
   */
  <R> R accept(FormulaVisitor<R> visitor);

  /**
   * Determines whether this formula or any cell that it references contains a cyclical reference.
   *
   * @param visitedCoords The coordinates of the cells visited so far. Note: This is local scoping,
   *                      so it is only the cells visited in the chain containing this cell. It is
   *                      to determine if this cell is part of a cyclical reference.
   * @param cells         The coordinate to cell mapping of the spreadsheet
   * @param coordsNoCycle The coordinates of the cells which have already been evaluated and are
   *                      certain to not be part of a cycle (global scoping, not local).
   * @return Whether or not there is a cycle.
   */
  boolean containsCyclicalReference(HashSet<Coord> visitedCoords, Hashtable<Coord, Cell> cells,
                                    HashSet<Coord> coordsNoCycle);

  /**
   * Determines if this formula references the cell at the given Coord.
   * @param coord The coord of the reference cell.
   * @return Whether or not this formula references the cell at the Coord.
   */
  boolean referencesCell(Coord coord);
}
