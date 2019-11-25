package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;

import java.io.File;

/**
 * Represents all of the features supported by the spreadsheet.
 */
public interface Features {

  /**
   * Represents that a cell has been selected.
   * @param coord The Coord of the selected cell
   */
  void cellSelected(Coord coord);

  /**
   * Represents that the selected cell has been edited.
   * @param contents The string of the new edit
   */
  void selectedCellEdited(String contents);

  /**
   * Represents that the highlighted cell has been moved in a direction.
   * @param direction The direction of movement
   */
  void movedHighlightedCell(Direction direction);

  /**
   * Represents that the highlighted cell's contents have been deleted.
   */
  void deletedSelectedCell();

  /**
   * Represents that a file has been saved to a file.
   *
   */
  void saveFile(File file);

  /**
   * Represents that a file has been opened from a file.
   */
  void loadFile(File file);
}
