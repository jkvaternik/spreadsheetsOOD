package edu.cs3500.spreadsheets.view;

import java.io.File;

import edu.cs3500.spreadsheets.controller.Direction;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents all of the features supported by the spreadsheet.
 */
public interface Features {

  /**
   * Represents that a cell has been selected.
   *
   * @param coord The Coord of the selected cell
   */
  void cellSelected(Coord coord);

  /**
   * Represents that the selected cell has been edited.
   *
   * @param contents The string of the new edit
   */
  void selectedCellEdited(String contents);

  /**
   * Represents that the highlighted cell has been moved in a direction.
   *
   * @param direction The direction of movement
   */
  void movedHighlightedCell(Direction direction);

  /**
   * Represents that the highlighted cell's contents have been deleted.
   */
  void deletedSelectedCell();

  /**
   * Represents that a file has been saved to a file.
   */
  void saveFile(File file);

  /**
   * Represents that a file has been opened from a file.
   */
  void loadFile(File file);

  /**
   * Represents that the highlighted cell's row height has been changed.
   * @param heightChange How much to change the row height by.
   *                     Negative value means a decrease in size.
   */
  void changeRowSize(int heightChange);

  /**
   * Represents that the highlighted cell's column width has been changed.
   * @param widthChange How much to increase the column width by.
   *                    Negative value means a decrease in size.
   */
  void changeColSize(int widthChange);

  /**
   * Represents that the highlighted cell has been copied (if it exists).
   */
  void copyCell();

  /**
   * Represents that the copied cell is to be pasted to the highlighted cell. If no cell has been
   * copied, nothing will happen.
   */
  void pasteCell();
}
