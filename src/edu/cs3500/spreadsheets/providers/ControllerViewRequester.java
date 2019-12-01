package edu.cs3500.spreadsheets.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Allows the view to request changes to the model from the controller.
 */
public class ControllerViewRequester {
  private SpreadsheetController control;

  /**
   * Constructs a controller wrapper to handle communications between view and controller.
   *
   * @param control the controller
   */
  ControllerViewRequester(SpreadsheetController control) {
    this.control = control;
  }

  /**
   * Requests that a cell be made in the model.
   *
   * @param row     row of cell
   * @param col     col of cell
   * @param formula formula of cell
   */
  public void requestCell(int row, int col, String formula) {
    if (formula.substring(0, 1).equals("=")) {
      formula = formula.substring(1);
    }
    control.cellRequest(row, col, formula);
  }

  /**
   * Requests that a cell be deleted in the controller.
   *
   * @param row row of cell to be deleted
   * @param col col of cell to be deleted
   */
  public void delCell(int row, int col) {
    control.delCell(row, col);
  }

  /**
   * Requests a file be loaded.
   *
   * @param text the name of the file
   */
  public void loadFile(String text) {
    FileReader file = getFile(text);
    if (file == null) {
      return;
    }
    control.loadFile(file);
  }

  /**
   * Reads in a flie to the program.
   *
   * @param fileName name of file
   * @return the file
   */
  private static FileReader getFile(String fileName) {
    try {
      return new FileReader(fileName);
    } catch (FileNotFoundException e) {
      return null;
    }
  }

  /**
   * Tells the controller to save the current editor as a file.
   *
   * @param text the file name
   */
  public void saveFile(String text) {
    control.saveFile(text);
  }

  /**
   * Requests more columns.
   */
  public void requestCols() {
    control.addCols();
  }

  /**
   * Requests more rows.
   */
  public void requestRows() {
    control.addRows();
  }
}
