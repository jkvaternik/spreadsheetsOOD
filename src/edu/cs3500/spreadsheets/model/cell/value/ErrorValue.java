package edu.cs3500.spreadsheets.model.cell.value;

/**
 * Represents an error value in a spreadsheet cell.
 */
public class ErrorValue extends Value<Exception> {

  /**
   * Creates an instance of an error value.
   */
  public ErrorValue(Exception value) {
    super(value);
  }
}
