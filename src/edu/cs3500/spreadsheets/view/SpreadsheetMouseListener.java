package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Represents a mouse listener for the spreadsheets. It's intention is to handle mouse clicks and
 * alert the appropriate Features as to where the click occurred.
 */
public class SpreadsheetMouseListener implements MouseListener {
  private final Features features;

  /**
   * Creates an instance of a spreadsheet mouse listeners and passes on its information to the given
   * features.
   * @param features The features which cares about this mouse listener.
   */
  public SpreadsheetMouseListener(Features features) {
    this.features = features;
  }
  @Override
  public void mouseClicked(MouseEvent e) {
    int clickX = e.getX();
    int clickY = e.getY();

    int coordX = clickX / SpreadsheetPanel.CELL_WIDTH + 1;
    int coordY = clickY / SpreadsheetPanel.CELL_HEIGHT + 1;
    Coord cellCoord = new Coord(coordX, coordY);
    features.cellSelected(cellCoord);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    //We only care about mouse clicks
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    //We only care about mouse clicks
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    //We only care about mouse clicks
  }

  @Override
  public void mouseExited(MouseEvent e) {
    //We only care about mouse clicks
  }
}
