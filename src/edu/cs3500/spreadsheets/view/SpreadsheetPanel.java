package edu.cs3500.spreadsheets.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;

/**
 * Represents Swing component of the actual grid of a spreadsheet.
 */

public class SpreadsheetPanel extends JPanel implements Scrollable, MouseMotionListener {
  static final int CELL_WIDTH = 75;
  static final int CELL_HEIGHT = 25;

  private final int maxXIncrement = CELL_WIDTH;
  private final int maxYIncrement = CELL_HEIGHT;

  private final ViewModel viewModel;
  private Coord highlightedCell;

  /**
   * Constructs an instance of the SpreadsheetPanel based on the given ViewModel.
   *
   * @param viewModel the given ViewModel
   */
  public SpreadsheetPanel(ViewModel viewModel) {
    super();
    this.viewModel = viewModel;

    // Set up background
    this.setBackground(Color.WHITE);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);

    int maxHeight = this.getPreferredSize().height;
    int maxWidth = this.getPreferredSize().width;

    // Draw all of the cell borders
    for (int horizLine = 0; horizLine <= this.getPreferredSize().height; horizLine++) {
      g2d.drawLine(0, horizLine * CELL_HEIGHT, maxWidth, horizLine * CELL_HEIGHT);
    }
    for (int vertLine = 0; vertLine <= this.getPreferredSize().width; vertLine++) {
      g2d.drawLine(vertLine * CELL_WIDTH, 0, vertLine * CELL_WIDTH, maxHeight);
    }

    // Save the clip state prior to drawing, and restore it after
    Shape initClip = g2d.getClip();

    // Check for highlighted cells first so that the contents still appear over the highlight
    if (this.highlightedCell != null) {
      g2d.setClip((this.highlightedCell.col - 1) * CELL_WIDTH, (this.highlightedCell.row - 1) * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
      // Save previous color to restore after we are done highlighting the cell
      Color prevColor = g2d.getColor();
      g2d.setColor(Color.CYAN);
      g2d.fillRect((this.highlightedCell.col - 1) * CELL_WIDTH, (this.highlightedCell.row - 1) * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
      g2d.setColor(prevColor);
    }

    //Display all of the correct text
    int maxCellRow = this.viewModel.getNumRows();
    int maxCellCol = this.viewModel.getNumColumns();

    for (int row = 1; row <= maxCellRow; row++) {
      for (int col = 1; col <= maxCellCol; col++) {
        Coord coord = new Coord(col, row);
        String value = this.viewModel.getValue(coord);
        g2d.setClip((col - 1) * CELL_WIDTH, (row - 1) * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
        g2d.drawString(value, (col - 1) * CELL_WIDTH, row * CELL_HEIGHT - CELL_HEIGHT / 3);
      }
    }
    g2d.setClip(initClip);
  }

  /**
   * Changes the highlightedCells of this spreadsheet panel. It de-highlights any previously
   * highlighted cells.
   * @param cellCoord The Coords of the new highlighted cells.
   *                   If this is null or empty, all cells are de-highlighted.
   */
  void setHighlightedCell(Coord cellCoord) {
    this.highlightedCell = cellCoord;
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
    scrollRectToVisible(r);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    // not applicable to scrolling because we only listen to mouse dragging
  }

  @Override
  public Dimension getPreferredScrollableViewportSize() {
    return super.getPreferredSize();
  }

  @Override
  public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
    if (orientation == SwingConstants.HORIZONTAL) {
      return maxXIncrement;
    } else {
      return maxYIncrement;
    }
  }

  @Override
  public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
    if (orientation == SwingConstants.HORIZONTAL) {
      return visibleRect.width - maxXIncrement;
    } else {
      return visibleRect.height - maxYIncrement;
    }
  }

  @Override
  public boolean getScrollableTracksViewportWidth() {
    return false;
  }

  @Override
  public boolean getScrollableTracksViewportHeight() {
    return false;
  }
}

