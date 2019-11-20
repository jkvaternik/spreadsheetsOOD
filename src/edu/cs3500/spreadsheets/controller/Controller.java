package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the controller of the spreadsheet. It handles the linking together of the model and
 * the view so they are always in sync.
 */
public class Controller implements Features {
  private final SpreadsheetModel model;
  private final View view;
  private Coord selectedCoord;

  public Controller(SpreadsheetModel model, View view) {
    this.model = model;
    this.view = view;
    this.view.addFeatures(this);
  }

  @Override
  public void cellSelected(Coord coord) {
    this.selectedCoord = coord;
    this.view.highlightCell(coord);
    this.view.refresh();
  }

  @Override
  public void selectedCellEdited(String contents) {
    if (this.selectedCoord != null) {
      this.model.setCellValue(selectedCoord, contents);
      this.view.refresh();
    }
  }

  @Override
  public void movedHighlightedCell(Direction direction) {
    int newCol = this.selectedCoord.col;
    int newRow = this.selectedCoord.row;
    switch (direction) {
      case UP:
        newRow -= 1;
        break;
      case DOWN:
        newRow += 1;
        break;
      case LEFT:
        newCol -= 1;
        break;
      case RIGHT:
        newCol += 1;
        break;
      default:
        //Do nothing if entered direction was null
    }
    try {
      this.selectedCoord = new Coord(newCol, newRow);
      this.view.highlightCell(selectedCoord);
    } catch (IllegalArgumentException e) {
      //If the new coord is illegal, catch the exception and keep the original coord
    }
    this.view.refresh();
  }

  @Override
  public void deletedSelectedCell() {
    this.model.clearCell(selectedCoord);
    this.view.refresh();
  }

  @Override
  public void saveFile() {

  }

  @Override
  public void loadFile() {

  }
}
