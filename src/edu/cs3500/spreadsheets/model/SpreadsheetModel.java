package edu.cs3500.spreadsheets.model;


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
   * Adds a column at the right of the spreadsheet.
   */
  void addColumn();

  /**
   * Adds a row at the bottom of the spreadsheet
   */
  void addRow();

  /**
   * Gets the number of rows of this spreadsheet.
   *
   * @return The number of rows
   */
  int getNumRows();

  /**
   * Gets the number of columns of this spreadsheet.
   *
   * @return The number of columns
   */
  int getNumColumns();

  /**
   * Gets the value of the cell at the given coordinate.
   *
   * @param coord The coordinate
   * @param <T> TODO: ?????????????? WTF IS THE RETURN TYPE ??????????????????
   * @return The cell's value
   */
  <T> T getValue(Coord coord);

  /**
   * Gets the string that was used as input to create the cell.
   *
   * TODO: NOTE FOR JAIME *************************************************************************
   * This is important for interacting with the view, since the user should be able to edit the
   * cell's previous formula without having to completely retype it
   *
   * @return The string
   */
  String getUserString();
}
