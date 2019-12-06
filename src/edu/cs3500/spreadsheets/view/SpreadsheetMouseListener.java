package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.sexp.Parser;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents a mouse listener for the spreadsheets. It's intention is to handle mouse clicks and
 * alert the appropriate Features as to where the click occurred.
 */
public class SpreadsheetMouseListener implements MouseListener {
  private final Features features;
  private final SpreadsheetModel model;

  /**
   * Creates an instance of a spreadsheet mouse listeners and passes on its information to the given
   * features. Also takes in a model so it knows about the different row and column sizes.
   *
   * @param features The features which cares about this mouse listener.
   * @param model The model
   */
  public SpreadsheetMouseListener(Features features, SpreadsheetModel model) {
    this.features = features;
    this.model = model;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    int clickX = e.getX();
    int clickY = e.getY();

    //We have to now account for different sized rows and columns when determining click position
    int xSoFar = 0;
    int col = 1;
    while (xSoFar < clickX) {
      xSoFar += this.model.getColWidth(col);
      col++;
    }
    int coordX = col - 1;

    int ySoFar = 0;
    int row = 1;
    while (ySoFar < clickY) {
      ySoFar += this.model.getRowHeight(row);
      row++;
    }
    int coordY = row - 1;

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
