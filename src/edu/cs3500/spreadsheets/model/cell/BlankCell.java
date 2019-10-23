package edu.cs3500.spreadsheets.model.cell;

import edu.cs3500.spreadsheets.model.cell.value.Value;

/**
 * Represents a blank cell.
 */
public class BlankCell implements Cell {

  @Override
  public void evaluate() {

  }

  @Override
  public String getUserString() {
    return null;
  }

  @Override
  public Value getValue() {
    return null;
  }
}
