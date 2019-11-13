package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

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
   * Adds a mouse listener to this view to handle any mouse-related events.
   *
   * @param listener The mouse listener
   */
  void addMouseListener(MouseListener listener);

  /**
   * Adds an action listener to this view to handle any non-mouse-related events (i.e. buttons)
   *
   * @param listener The action listener
   */

  void addActionListener(ActionListener listener);

  /**
   * Highlights the cell at the given Coordinate.
   * @param cellCoord The coordinate of the cell
   */
  void highlightCell(Coord cellCoord);
}
