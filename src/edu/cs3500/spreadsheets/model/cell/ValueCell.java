package edu.cs3500.spreadsheets.model.cell;

import edu.cs3500.spreadsheets.model.value.Value;

/**
 * Represents a cell which contains one of the accepted types of values.
 */
public class ValueCell implements Cell {
  private String cellString;
  private Value value;

  /**
   * Creates an instance of a value cell from the given cell string and value.
   * @param cellString The string
   * @param value The value
   */
  public ValueCell(String cellString, Value value) {
    this.cellString = cellString;
    this.value = value;
  }

  @Override
  public void evaluate() {
    // Do nothing here, as the cell's value is never changed.
  }

  @Override
  public String getUserString() {
    return this.cellString;
  }

  @Override
  public Value getValue() {
    return this.value;
  }
}
