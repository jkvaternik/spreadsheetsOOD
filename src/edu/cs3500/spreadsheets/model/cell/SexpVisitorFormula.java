package edu.cs3500.spreadsheets.model.cell;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.formula.CellReference;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.function.CapitalizeFunction;
import edu.cs3500.spreadsheets.model.cell.formula.function.LessThanFunction;
import edu.cs3500.spreadsheets.model.cell.formula.function.ProductFunction;
import edu.cs3500.spreadsheets.model.cell.formula.function.SumFunction;
import edu.cs3500.spreadsheets.model.cell.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.value.ErrorValue;
import edu.cs3500.spreadsheets.model.cell.value.StringValue;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;
import java.util.ArrayList;
import java.util.List;

public class SexpVisitorFormula implements SexpVisitor<Formula> {

  /**
   * Applies the given SExp to this visitor.
   * @param s the Sexp
   * @return The result formula
   */
  private Formula apply(Sexp s) {
    return s.accept(this);
  }

  @Override
  public Formula visitBoolean(boolean b) {
    return new BooleanValue(b);
  }

  @Override
  public Formula visitNumber(double d) {
    return new DoubleValue(d);
  }

  @Override
  public Formula visitSList(List<Sexp> l) {
    return null;
  }

  @Override
  public Formula visitSymbol(String s) {
    if ("PRODUCT".equals(s)) {
      return new ProductFunction(null);
    } else if ("SUM".equals(s)) {
      return new SumFunction(null);
    } else if ("<".equals(s)) {
      return new LessThanFunction(null);
    } else if ("CAPITALIZE".equals(s)) {
      return new CapitalizeFunction(null);
    } else if (s.contains(":") && s.indexOf(":") < s.length() - 1) {
      String cell1 = s.substring(0, s.indexOf(":"));
      String cell2 = s.substring(s.indexOf(":") + 1);
      if (this.validCellString(cell1) && this.validCellString(cell2)) {
        return new CellReference(this.cellStringToCoord(cell1), this.cellStringToCoord(cell2));
      } else {
        return new ErrorValue(new IllegalArgumentException("Invalid cell reference"));
      }
    } else {
      if (this.validCellString(s)) {
        Coord cellCoord = this.cellStringToCoord(s);
        return new CellReference(cellCoord, cellCoord);
      } else {
        return new ErrorValue(new IllegalArgumentException("Invalid cell reference"));
      }
    }
  }

  /**
   * Determines if the given string is a valid representation of a cell. For it to be valid, it must
   * contain only alphanumeric characters, where all the letters come before all of the numbers.
   * @param cell The string to check
   * @return Whether or not the string is a valid cell
   */
  private boolean validCellString(String cell) {
    int finalLetter = cell.length();
    int firstNum = 0;
    // Iterate forwards through the string to check for final letter index
    for (int index = 0; index < cell.length(); index += 1) {
      char c = cell.charAt(index);
      // If the character is neither a letter nor a number, it is not a valid cell
      if (!(Character.isAlphabetic(c) || Character.isDigit(c))) {
        return false;
      } else if (Character.isAlphabetic(c)) {
        finalLetter = index;
      }
    }
    // Iterate backwards though the string to check for the earliest number index
    for (int index = cell.length() - 1; index < 0; index -= 1) {
      char c = cell.charAt(index);
      // If the character is neither a letter nor a number, it is not a valid cell
      if (!(Character.isAlphabetic(c) || Character.isDigit(c))) {
        return false;
      } else if (Character.isDigit(c)) {
        firstNum = index;
      }
    }

    return (firstNum > finalLetter);
  }

  /**
   * Converts the given cell string to a coordinate.
   * Invariant: The given cell is guaranteed to be a valid coordinate.
   * @param cell The cell string
   * @return The coordinate of the cell
   */
  private Coord cellStringToCoord(String cell) {
    int lastLetter = 0;
    for (int index = 0; index < cell.length(); index += 1) {
      if (Character.isAlphabetic(cell.charAt(index))) {
        lastLetter = index;
      }
    }
    int column = (new Coord(0, 0).colNameToIndex(cell.substring(0, lastLetter + 1)));
    int row = Integer.parseInt(cell.substring(lastLetter + 1));
    return new Coord(column, row);
  }

  @Override
  public Formula visitString(String s) {
    return new StringValue(s);
  }
}
