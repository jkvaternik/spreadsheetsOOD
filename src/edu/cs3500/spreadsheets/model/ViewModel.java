package edu.cs3500.spreadsheets.model;

import java.util.List;

/**
 * Represents a limited-scope model which supports all of the observational (reading) methods but
 * none of the operational (writing) methods. Serves as an intermediary between the view and the
 * model to ensure the view is unable to mutate the model.
 */
public class ViewModel implements SpreadsheetModel {
  private final SpreadsheetModel realModel;

  /**
   * Makes a view model.
   * @param realModel the real spreadsheet model
   */
  public ViewModel(SpreadsheetModel realModel) {
    this.realModel = realModel;
  }

  @Override
  public void clearCell(Coord coord) {
    throw new UnsupportedOperationException("Cannot clear the contents of a cell.");
  }

  @Override
  public void setCellValue(Coord coord, String value) {
    throw new UnsupportedOperationException("Cannot set the value of a cell.");
  }

  @Override
  public int getNumRows() {
    return this.realModel.getNumRows();
  }

  @Override
  public int getNumColumns() {
    return this.realModel.getNumColumns();
  }

  @Override
  public String getValue(Coord coord) {
    return this.realModel.getValue(coord);
  }

  @Override
  public String getRawContents(Coord coord) {
    return this.realModel.getRawContents(coord);
  }

  @Override
  public List<Coord> getErrorCoords() {
    return this.realModel.getErrorCoords();
  }

  @Override
  public int getRowHeight(int row) throws IllegalArgumentException {
    return realModel.getRowHeight(row);
  }

  @Override
  public int getColWidth(int col) throws IllegalArgumentException {
    return realModel.getColWidth(col);
  }

  @Override
  public void setRowHeight(int row, int height) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Cannot set the value of a cell.");
  }

  @Override
  public void setColWidth(int col, int width) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Cannot set the value of a cell.");
  }

  @Override
  public void copyPasteContents(Coord copyCoord, Coord pasteCoord) {
    throw new UnsupportedOperationException("Cannot copy and paste cells.");
  }

  @Override
  public int maxRowChanged() {
    return realModel.maxRowChanged();
  }

  @Override
  public int maxColChanged() {
    return realModel.maxColChanged();
  }
}
