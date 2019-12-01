package edu.cs3500.spreadsheets.model;

import java.util.Hashtable;

/**
 * Interface for any spreadsheet model.
 */
public interface Worksheet {
  /**
   * Creates a new key-value pair and puts it in the hashtable.
   *
   * @param col the x-value of the coord
   * @param row the y-value of the coord
   * @param f   the formula contained in the Cell
   */
  void createCell(int col, int row, Formula f);

  /**
   * Gets the Cell at the specified coord.
   *
   * @param c the coord of the desired cell
   * @return the desired cell
   */
  Cell getCellAt(Coord c);

  /**
   * Deletes the Cell at the specified coord.
   *
   * @param c the coord of the desired cell
   */
  void deleteCellAt(Coord c);

  /**
   * Evaluates all the cells containing formulas.
   *
   * @return Coord of a broken cell if there is an error, or returns null.
   */
  Coord evalAll();

  /**
   * Updates the formula that is currently in the Cell.
   *
   * @param c the coord of the cell to be updated.
   */
  void changeContents(Coord c, Formula f);

  /**
   * Gets a copy of the hashtable.
   *
   * @return a copy of the hash table.
   */
  Hashtable<Coord, Cell> getHashtable();

  /**
   * Finds the cell farthest down and returns its row number. This lets the user know how many rows
   * are in the sheet.
   *
   * @return Number of rows in the spreadsheet
   */
  int getNumRows();

  /**
   * Finds the cell farthest to the right and returns its column number. This lets the user know how
   * many columns are in the sheet.
   *
   * @return Number of rows in the spreadsheet
   */
  int getNumCols();

  /**
   * Reevaluates when a change is made to a spreadsheet.
   *
   * @param c the coord to re evaluate
   * @return Coord of an errant if there is one
   */
  Coord reEval(Coord c);

  /**
   * Sets the cell as an error.
   *
   * @param c coord of cell
   */
  void setErrorAt(Coord c);

  /**
   * Adds columns possible to create cells.
   */
  void addCols();

  /**
   * Adds rows possible to create cells.
   */
  void addRows();
}
