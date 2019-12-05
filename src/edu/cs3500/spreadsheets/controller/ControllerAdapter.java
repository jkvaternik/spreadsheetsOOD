package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SimpleSpreadsheet;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.providers.ControllerViewRequester;
import edu.cs3500.spreadsheets.providers.GUITableGraphics;
import edu.cs3500.spreadsheets.providers.GUIView;
import edu.cs3500.spreadsheets.providers.SpreadsheetController;
import edu.cs3500.spreadsheets.view.ModelToViewImpl;
import edu.cs3500.spreadsheets.view.ViewAdapter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Adapts our controller class to our provider's controller.
 */
public class ControllerAdapter implements SpreadsheetController {
  private final Controller controller;
  //We need an implementation of the model to add a blank cell for the addRows and addCols methods
  //to make our code work with theirs
  private final SpreadsheetModel model;

  /**
   * Creates a controller adapter, which also creates the view and the controller (our version).
   * This wiring has to occur in this constructor rather than a main method due to the differences
   * in how we do the wiring from model to view to controller vs. how they handle it.
   * @param model The spreadsheet model that this MVC system is based on
   */
  public ControllerAdapter(SpreadsheetModel model) {
    ControllerViewRequester requester = new ControllerViewRequesterFormulaFix(this);
    GUIView view = new GUIView(new ModelToViewImpl(model), requester);
    controller = new Controller(model, new ViewAdapter(view));
    this.model = model;
  }

  @Override
  public void launchEditor() {
    //We do not need to do implement this method as our controller can handle starting everything
  }

  @Override
  public void cellRequest(int row, int col, String formula) {
    controller.cellSelected(new Coord(col, row));
    controller.selectedCellEdited(formula);
  }

  @Override
  public void delCell(int row, int col) {
    controller.cellSelected(new Coord(col, row));
    controller.deletedSelectedCell();
  }

  @Override
  public void loadFile(FileReader file) {
    StringBuilder fileString = new StringBuilder();
    try {
      while (file.ready()) {
        fileString.append(file.read());
      }
    } catch (IOException e) {
      //If we encounter a problem, just ignore the request
    }
    controller.loadFile(new File(fileString.toString()));
  }

  @Override
  public void saveFile(String text) {
    controller.saveFile(new File(text));
  }

  @Override
  public void addCols() {
    //We need to hard code the adding of a blank cell for the purpose of the translate 2d array
    //that they use for loading the values of the model into their view
    this.model.setCellValue(new Coord(this.model.getNumColumns() + 50, 1), "");
  }

  @Override
  public void addRows() {
    //We need to hard code the adding of a blank cell for the purpose of the translate 2d array
    //that they use for loading the values of the model into their view
    this.model.setCellValue(new Coord(1, this.model.getNumRows() + 50), "");
  }
}
