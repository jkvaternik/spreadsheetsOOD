package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

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
   * Adds a mouse listener to this view to handle any mouse-related events.
   *
   * @param listener The mouse listener
   */
  void addMouseListener(MouseListener listener);

  /**
   * Adds an action listener to this view to handle any non-mouse-related events (i.e. buttons).
   *
   * @param listener The action listener
   */

  void addActionListener(ActionListener listener);

  /**
   * Highlights the cells at the given Coordinates. In the process, it de-highlights any previously
   * highlighted cells.
   * @param cellCoord The coordinate of the cell
   */
  void highlightCell(Coord cellCoord);
}
