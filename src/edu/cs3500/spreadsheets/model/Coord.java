package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A value type representing coordinates in a {@link SpreadsheetModel}.
 */
public class Coord {
  public final int row;
  public final int col;

  /**
   * Constructs a Coordinate in a spreadsheet.
   *
   * @param col index of column
   * @param row index of row
   */
  public Coord(int col, int row) {
    if (row < 1 || col < 1) {
      throw new IllegalArgumentException("Coordinates should be strictly positive");
    }
    this.row = row;
    this.col = col;
  }

  /**
   * Makes a coord that is a copy of the given coord.
   * @param c The coord to copy
   */
  public Coord(Coord c) {
    this(c.col, c.row);
  }

  /**
   * Converts from the A-Z column naming system to a 1-indexed numeric value.
   *
   * @param name the column name
   * @return the corresponding column index
   */
  public static int colNameToIndex(String name) {
    name = name.toUpperCase();
    int ans = 0;
    for (int i = 0; i < name.length(); i++) {
      ans *= 26;
      ans += (name.charAt(i) - 'A' + 1);
    }
    return ans;
  }

  /**
   * Converts a 1-based column index into the A-Z column naming system.
   *
   * @param index the column index
   * @return the corresponding column name
   */
  public static String colIndexToName(int index) {
    StringBuilder ans = new StringBuilder();
    while (index > 0) {
      int colNum = (index - 1) % 26;
      ans.insert(0, Character.toChars('A' + colNum));
      index = (index - colNum) / 26;
    }
    return ans.toString();
  }

  /**
   * Determines if the given string is a valid representation of a cell. For it to be valid, it must
   * contain only alphanumeric characters, where all the letters come before all of the numbers.
   *
   * EDIT: With assignment 9, '$' are also allowed to indicate absolute references
   *
   * @param cell The string to check
   * @return Whether or not the string is a valid cell
   */
  public static boolean validCellString(String cell) {
    int finalLetter = cell.length();
    int firstNum = 0;
    List<Integer> dollarSignIndices = new ArrayList<>();

    // Iterate forwards through the string to check for final letter index
    for (int index = 0; index < cell.length(); index += 1) {
      char c = cell.charAt(index);
      // If the character is neither a letter nor a number, it is not a valid cell
      if (!(Character.isAlphabetic(c) || Character.isDigit(c) || c == '$')) {
        return false;
      } else if (Character.isAlphabetic(c)) {
        finalLetter = index;
      } else if (c == '$') {
        dollarSignIndices.add(index);
      }
    }
    // Iterate backwards though the string to check for the earliest number index
    for (int index = cell.length() - 1; index >= 0; index -= 1) {
      char c = cell.charAt(index);
      // If the character is neither a letter nor a number, it is not a valid cell
      if (!(Character.isAlphabetic(c) || Character.isDigit(c) || c == '$')) {
        return false;
      } else if (Character.isDigit(c)) {
        firstNum = index;
      }
    }

    if (dollarSignIndices.size() == 1 &&
        (dollarSignIndices.get(0) != 0 && dollarSignIndices.get(0) != firstNum - 1)) {
      return false;
    } else if (dollarSignIndices.size() == 2 &&
        (dollarSignIndices.get(0) != 0 || dollarSignIndices.get(1) != firstNum - 1)) {
      return false;
    }

    return dollarSignIndices.size() <= 2 && (firstNum > finalLetter);
  }

  /**
   * Converts the given cell string to a coordinate. Invariant: The given cell is guaranteed to be a
   * valid coordinate.
   *
   * @param cell The cell string
   * @return The coordinate of the cell
   */
  public static Coord cellStringToCoord(String cell) {
    String noDollarSigns = "";
    for (int index = 0; index < cell.length(); index++) {
      char c = cell.charAt(index);
      if (c != '$') {
        noDollarSigns += c;
      }
    }


    int lastLetter = 0;
    for (int index = 0; index < noDollarSigns.length(); index += 1) {
      if (Character.isAlphabetic(noDollarSigns.charAt(index))) {
        lastLetter = index;
      }
    }
    int column = (Coord.colNameToIndex(noDollarSigns.substring(0, lastLetter + 1)));
    int row = Integer.parseInt(noDollarSigns.substring(lastLetter + 1));
    return new Coord(column, row);
  }

  @Override
  public String toString() {
    return colIndexToName(this.col) + this.row;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coord coord = (Coord) o;
    return row == coord.row
            && col == coord.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }
}
