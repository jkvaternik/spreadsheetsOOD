package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.providers.ControllerViewRequester;
import edu.cs3500.spreadsheets.providers.SpreadsheetController;

/**
 * Exactly the same as the provider's controller-view-requester except that it fixes the issue where
 * formulas are stored as strings and not formulas (because they removed the '=')
 */
public class ControllerViewRequesterFormulaFix extends ControllerViewRequester {

  /**
   * Constructs a controller wrapper to handle communications between view and controller.
   *
   * @param control the controller
   */
  public ControllerViewRequesterFormulaFix(
      SpreadsheetController control) {
    super(control);
  }

  @Override
  public void requestCell(int row, int col, String formula) {
    super.control.cellRequest(row, col, formula);
  }
}
