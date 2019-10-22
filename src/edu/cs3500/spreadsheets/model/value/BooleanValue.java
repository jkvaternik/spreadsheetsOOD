package edu.cs3500.spreadsheets.model.value;

/**
 * Represents a boolean value in a spreadsheet cell.
 */
public class BooleanValue extends Value {
  boolean value;

  /**
   * Creates an instance of a boolean value.
   */
  public BooleanValue(boolean value) {
    this.value = value;
  }
}
