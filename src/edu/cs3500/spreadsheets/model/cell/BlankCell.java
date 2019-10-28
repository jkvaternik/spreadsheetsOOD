package edu.cs3500.spreadsheets.model.cell;

import java.util.Hashtable;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;

/**
 * Represents a blank cell.
 */
public class BlankCell implements Cell {
  @Override
  public void evaluate(Hashtable<Coord, Cell> spreadsheet) {

  }

  @Override
  public String getRawContents() {
    return null;
  }

  @Override
  public Value getValue() {
    return null;
  }
}
