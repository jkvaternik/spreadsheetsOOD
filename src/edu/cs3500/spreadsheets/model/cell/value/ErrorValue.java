package edu.cs3500.spreadsheets.model.cell.value;

public class ErrorValue extends Value<Exception> {

  public ErrorValue(Exception value) {
    super(value);
  }
}
