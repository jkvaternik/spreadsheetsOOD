package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents a view for a spreadsheet.
 */
public interface View {
  /**
   * Make the view visible (for the first time).
   */
  void makeVisible();

  /**
   * Signal the view to draw itself again.
   */
  void refresh();

  /**
   * Handles the action listeners of the features from the {@link Features} interface.
   *
   * @param features The features
   */
  void addFeatures(Features features);

  /**
   * Highlights the cell at the given Coordinates. In the process, it de-highlights any previously
   * highlighted cells.
   * @param cellCoord The coordinate of the cell
   */
  void highlightCell(Coord cellCoord);

  /**
   * Highlights the cell at the given coordinate (marked as the cell which has been copied).
   * @param copyCoord The coordinate of the copied cell
   */
  void highlightCopyCell(Coord copyCoord);

}
