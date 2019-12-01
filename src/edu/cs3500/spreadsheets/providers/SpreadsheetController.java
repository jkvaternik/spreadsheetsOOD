package edu.cs3500.spreadsheets.controller;

import java.io.FileReader;

/**
 * Outlines a controller for a spreadsheet program.
 */
public interface SpreadsheetController {

  /**
   * Launches a spreadsheet into editor mode.
   */
  void launchEditor();

  /**
   * Creates a new cell in the model.
   *
   * @param row     cell row
   * @param col     cell column
   * @param formula cell formula
   */
  void cellRequest(int row, int col, String formula);

  /**
   * Deletes a cell in the model.
   *
   * @param row cell row
   * @param col cell column
   */
  void delCell(int row, int col);

  /**
   * Loads a file into the view.
   *
   * @param file the file to load
   */
  void loadFile(FileReader file);

  /**
   * Saves the current state of the editor in a file.
   *
   * @param text name of file
   */
  void saveFile(String text);

  /**
   * Adds columns possible to create cells in.
   */
  void addCols();

  /**
   * Adds columns possible to create cells in.
   */
  void addRows();
}
