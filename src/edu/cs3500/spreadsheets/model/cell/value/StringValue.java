package edu.cs3500.spreadsheets.model.cell.value;

/**
 * Represents a string value in a spreadsheet cell.
 */
public class StringValue extends Value {
  String value;

  /**
   * Creates an instance of a string value.
   */
  public StringValue(String value) {
    this.value = value;
  }

}
