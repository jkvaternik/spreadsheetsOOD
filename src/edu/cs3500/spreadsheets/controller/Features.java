package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents all of the features supported by the spreadsheet.
 */
public interface Features {
  void cellSelected(Coord coord);
  void selectedCellEdited(String contents);
}
