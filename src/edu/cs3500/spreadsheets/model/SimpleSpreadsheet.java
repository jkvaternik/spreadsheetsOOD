package edu.cs3500.spreadsheets.model;

public class SimpleSpreadsheet implements SpreadsheetModel {

  @Override
  public void clearCell(Coord coord) {

  }

  @Override
  public void setCellValue(Coord coord, String value) {

  }

  @Override
  public void addColumn() {

  }

  @Override
  public void addRow() {

  }

  @Override
  public int getNumRows() {
    return 0;
  }

  @Override
  public int getNumColumns() {
    return 0;
  }

  @Override
  public <T> T getValue(Coord coord) {
    return null;
  }

  @Override
  public String getUserString() {
    return null;
  }
}
