package edu.cs3500.spreadsheets.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SimpleSpreadsheet;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.ViewModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.Features;
import edu.cs3500.spreadsheets.view.TextualView;
import edu.cs3500.spreadsheets.view.View;
import edu.cs3500.spreadsheets.view.VisualEditView;

/**
 * Represents the controller of the spreadsheet. It handles the linking together of the model and
 * the view so they are always in sync.
 */
public class Controller implements Features {
  private SpreadsheetModel model;
  private View view;
  private Coord selectedCoord;

  public Controller(SpreadsheetModel model, View view) {
    this.model = model;
    this.view = view;
    this.view.addFeatures(this);
  }

  @Override
  public void cellSelected(Coord coord) {
    if (coord != null) {
      this.selectedCoord = coord;
      this.view.highlightCell(coord);
    }
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
    if (this.selectedCoord != null) {
      this.model.clearCell(selectedCoord);
      this.view.refresh();
    }
  }

  @Override
  public void saveFile(File file) {
    PrintWriter fileWriter = null;
    try {
      fileWriter = new PrintWriter(file.getPath() + ".txt");

    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
    View view = new TextualView(fileWriter, new ViewModel(this.model));
    view.makeVisible();
    fileWriter.close();
  }

  @Override
  public void loadFile(File file) {
    Readable fileReader = null;
    try {
      fileReader = new FileReader(file.getPath());
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
    WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builder = new SimpleSpreadsheet.Builder();
    SimpleSpreadsheet spreadsheet = WorksheetReader.read(builder, fileReader);

    VisualEditView editView = new VisualEditView(new ViewModel(spreadsheet));
    Controller controller = new Controller(spreadsheet, editView);
    editView.makeVisible();
  }
}
