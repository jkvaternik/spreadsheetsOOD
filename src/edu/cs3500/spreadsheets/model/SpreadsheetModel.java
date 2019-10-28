package edu.cs3500.spreadsheets.model;


import edu.cs3500.spreadsheets.model.cell.value.Value;

/**
 * Represents the model for a spreadsheet.
 */
public interface SpreadsheetModel {

  /**
   * Clears the cell at the given coordinate, and updates all cells which reference
   * the cell to be cleared.
   *
   * @param coord The coordinate
   */
  void clearCell(Coord coord);

  /**
   * Sets the value of the cell at the given coordinate indices to the specified value, and
   * updates all cells which reference the changed cell. Note, the value is just the string
   * representation of the value to set the cell to, which can include doubles, booleans, strings,
   * functions, etc.
   *
   * @param coord The coordinate
   * @param value The string representation of the value
   */
  void setCellValue(Coord coord, String value);

  /**
   * Gets the number of rows of this spreadsheet. The spreadsheet has infinite rows, but this will
   * return the number of rows that have cells which have been edited.
   *
   * @return The number of rows
   */
  int getNumRows();

  /**
   * Gets the number of columns of this spreadsheet. The spreadsheet has infinite columns, but this
   * will return the number of columns that have cells which have been edited.
   *
   * @return The number of columns
   */
  int getNumColumns();

  /**
   * Gets the value of the cell at the given coordinate.
   *
   * @param coord The coordinate
   * @return The cell's value
   */
  Value getValue(Coord coord);

  /**
   * Gets the string that was used as input to create the cell.
   *
   * @param coord The coordinate for the cell
   * @return The string
   */
  String getUserString(Coord coord);
}
