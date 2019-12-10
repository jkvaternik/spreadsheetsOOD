package edu.cs3500.spreadsheets.model;


import java.util.HashMap;
import java.util.List;

/**
 * Represents the model for a spreadsheet.
 */
public interface SpreadsheetModel {

  /**
   * Clears the cell at the given coordinate, and updates all cells which reference the cell to be
   * cleared.
   *
   * @param coord The coordinate
   */
  void clearCell(Coord coord);

  /**
   * Sets the value of the cell at the given coordinate indices to the specified value. Works for
   * both cells which currently have contents, and those which are blank. Note, the value is just
   * the string representation of the value to set the cell to, which can include doubles, booleans,
   * strings, functions, etc.
   *
   * @param coord The coordinate
   * @param value The string representation of the value
   */
  void setCellValue(Coord coord, String value);

  /**
   * Gets the number of rows of this spreadsheet. The spreadsheet has infinite rows, but this will
   * return the max row that has cells which have been edited.
   *
   * @return The number of rows
   */
  int getNumRows();

  /**
   * Gets the number of columns of this spreadsheet. The spreadsheet has infinite columns, but this
   * will return the max column that has cells which have been edited.
   *
   * @return The number of columns
   */
  int getNumColumns();

  /**
   * Gets the value of the cell at the given coordinate (interpreted as a string).
   *
   * @param coord The coordinate
   * @return The cell's value
   */
  String getValue(Coord coord);

  /**
   * Gets the string that was used as input to create the cell.
   *
   * @param coord The coordinate for the cell
   * @return The string
   */
  String getRawContents(Coord coord);

  /**
   * Gets the list of coordinates at which there is an ErrorValue.
   *
   * @return the list of coordinates
   */

  List<Coord> getErrorCoords();

  /**
   * Gets the height of the given row for the view.
   * @param row The row of interest
   * @return The height of the row
   * @throws IllegalArgumentException If the row is invalid
   */
  int getRowHeight(int row) throws IllegalArgumentException;

  /**
   * Gets the width of the given column for the view.
   * @param col The col of interest
   * @return The width of the column
   * @throws IllegalArgumentException If the column is invalid
   */
  int getColWidth(int col) throws IllegalArgumentException;

  /**
   * Sets the height of the given row of the view to be the given height.
   * @param row The row of interest
   * @param height The height to set
   * @throws IllegalArgumentException If the row or height is invalid
   */
  void setRowHeight(int row, int height) throws IllegalArgumentException;

  /**
   * Sets the width of the given column of the view to be the given width.
   * @param col The col of interest
   * @param width The width to set
   * @throws IllegalArgumentException If the column or width is invalid
   */
  void setColWidth(int col, int width) throws IllegalArgumentException;

  /**
   * Sets the contents of the Cell at pasteCoord based on the contents of the Cell at copyCoord,
   * taking into account absolute and non-absolute references.
   * @param copyCoord The Coord of the Cell which was copied
   * @param pasteCoord The Coord of the cell where the copied cell was pasted
   */
  void copyPasteContents(Coord copyCoord, Coord pasteCoord);

  /**
   * Gets the max row who's height has been changed.
   * @return The max row
   */
  int maxRowChanged();

  /**
   * Gets the max column who's width has been changed.
   * @return The max col
   */
  int maxColChanged();
}
