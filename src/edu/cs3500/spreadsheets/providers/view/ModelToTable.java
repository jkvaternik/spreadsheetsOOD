package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Worksheet;

/**
 * This wrapper class takes the contents of the Spreadsheet from the Hashtable and returns the
 * values as String[] to be used in the JTable.
 */
public class ModelToTable {
  private final Worksheet sheet;

  /**
   * Constructs the wrapper class using the actual Worksheet model.
   *
   * @param sheet worksheet model.
   */
  public ModelToTable(Worksheet sheet) {
    this.sheet = sheet;
  }

  /**
   * Returns the number of columns in the Spreadsheet.
   *
   * @return number of Spreadsheet columns.
   */
  public int numCols() {
    return sheet.getNumCols();
  }

  /**
   * Returns the number of rows in the Spreadsheet.
   *
   * @return number of Spreadsheet rows.
   */
  public int numRows() {
    return sheet.getNumRows();
  }

  /**
   * Gets the column headers based on the contents of the Spreadsheet model. If coords go from A1 to
   * L4, this method will return values from A to L in a String array. Returns a minimum number of
   * columns despite the amount of Spreadsheet contents, just like Excel does.
   *
   * @return column names.
   */
  public String[] colNames() {
    if (sheet.getNumCols() < 10) {
      String[] defaultCols = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
      return defaultCols;
    }
    String[] headers = new String[sheet.getNumCols()];
    for (int i = 0; i < headers.length; i++) {
      headers[i] = Coord.colIndexToName(i + 1);
    }
    return headers;
  }

  /**
   * Gets the rows headers based on the contents of the Spreadsheet model. If coords go from A1 to
   * D11, this method will return values from 1 to 11 in a String array. Returns a minimum number of
   * rows despite the amount of Spreadsheet contents, just like Excel does.
   *
   * @return row names.
   */
  String[] rowNames() {
    if (sheet.getNumRows() < 10) {
      String[] defaultRows = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
      return defaultRows;
    }
    String[] headers = new String[sheet.getNumRows()];
    for (int i = 0; i < headers.length; i++) {
      headers[i] = "" + (i + 1);
    }
    return headers;
  }

  /**
   * Wrapper method that takes the contents of the Spreadsheet hashtable and puts them into a 2D
   * array to be used in the JTable.
   *
   * @return 2D String array of Cell formulas.
   */
  public String[][] translate() {
    String[][] translation = new String[sheet.getNumCols()][sheet.getNumRows()];
    for (int i = 0; i < sheet.getNumCols(); i++) {
      for (int j = 0; j < sheet.getNumRows(); j++) {
        Coord check = new Coord(i + 1, j + 1);
        if (sheet.getHashtable().containsKey(check)) {
          Cell cell = sheet.getHashtable().get(check);
          if (cell.errorCell()) {
            translation[i][j] = "Error";
          } else if (cell.getFormula().type().equals("double")) {
            translation[i][j] = String.format("%.02f", cell.getFormula().evaluate().numberForm());
          } else {
            translation[i][j] = "" + cell;
          }
        } else {
          translation[i][j] = "";
        }
      }
    }
    return translation;
  }

  /**
   * Gets the formula to display in the formula top bar.
   *
   * @return an array map of formulas to be displayed
   */
  public String[][] formTranslate() {
    String[][] translation = new String[sheet.getNumCols()][sheet.getNumRows()];
    for (int i = 0; i < sheet.getNumCols(); i++) {
      for (int j = 0; j < sheet.getNumRows(); j++) {
        Coord check = new Coord(i + 1, j + 1);
        if (sheet.getHashtable().containsKey(check)) {
          Cell cell = sheet.getHashtable().get(check);
          if (cell.errorCell()) {
            translation[i][j] = "Error";
          } else {
            translation[i][j] = sheet.getHashtable().get(new Coord(i + 1, j + 1))
                    .unevaluatedFormula().toString();
          }
        } else {
          translation[i][j] = "";
        }
      }
    }
    return translation;
  }
}
