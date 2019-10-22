package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.value.Value;

/**
 * A simple spreadsheet implementation which supports string, boolean, and double values. It's
 * supported operations include summing and taking products of a group of cells, determining if one
 * cell is less than another, and capitalizing the string value of a cell.
 */
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
  public Value getValue(Coord coord) {
    return null;
  }

  @Override
  public String getUserString() {
    return null;
  }
}
