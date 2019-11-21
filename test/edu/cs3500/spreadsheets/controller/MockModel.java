package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import java.io.IOException;
import java.util.List;

/**
 * Represents a mock model which logs all of its method calls to an appendable and delegates
 * to a "real" model to execute every method.
 */
class MockModel implements SpreadsheetModel {
  private final Appendable log;
  private final SpreadsheetModel realModel;
  /**
   * Creates an instance of a mock model using the given appendable and spreadsheet model.
   * @param log The appendable to log method calls.
   * @param realModel The model which serves as a delegate.
   */
  MockModel(Appendable log, SpreadsheetModel realModel) {
    this.log = log;
    this.realModel = realModel;
  }

  @Override
  public void clearCell(Coord coord) {
    try {
      log.append("This cell was cleared : ").append(coord.toString()).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
    this.realModel.clearCell(coord);
  }

  @Override
  public void setCellValue(Coord coord, String value) {
    try {
      log.append("This cell at: ").append(coord.toString()).append(" now has the raw contents: ")
          .append(value).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
    this.realModel.setCellValue(coord, value);
  }

  @Override
  public int getNumRows() {
    try {
      log.append("Got the number of rows.").append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
    return this.realModel.getNumRows();
  }

  @Override
  public int getNumColumns() {
    try {
      log.append("Got the number of columns.").append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
    return this.realModel.getNumColumns();
  }

  @Override
  public String getValue(Coord coord) {
    try {
      log.append("Got the value of the cell at the coord: ").append(coord.toString()).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
    return null;
  }

  @Override
  public String getRawContents(Coord coord) {
    try {
      log.append("Got the raw contents of the cell at the coord: ").append(coord.toString())
          .append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
    return this.realModel.getRawContents(coord);
  }

  @Override
  public List<Coord> getErrorCoords() {
    try {
      log.append("Got the error coords").append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
    return this.realModel.getErrorCoords();
  }
}
