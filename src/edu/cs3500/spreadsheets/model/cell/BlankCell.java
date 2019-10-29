package edu.cs3500.spreadsheets.model.cell;

import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;
import java.util.Hashtable;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;

/**
 * Represents a blank cell.
 */
public class BlankCell implements Cell {
  @Override
  public Value evaluate(Hashtable<Coord, Cell> spreadsheet) {
    return new StringValue("");
  }

  @Override
  public String getRawContents() {
    return null;
  }
}
