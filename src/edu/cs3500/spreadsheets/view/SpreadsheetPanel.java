package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import java.util.List;
import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;

public class SpreadsheetPanel extends JPanel implements Scrollable, MouseMotionListener {
  private static final int CELL_WIDTH = 75;
  private static final int CELL_HEIGHT = 25;

  private final int maxXIncrement = CELL_WIDTH;
  private final int maxYIncrement = CELL_HEIGHT;

  private final ViewModel viewModel;
  private final List<Coord> highlightedCells;

  public SpreadsheetPanel(ViewModel viewModel, List<Coord> highlightedCells) {
    super();
    this.viewModel = viewModel;
    this.highlightedCells = highlightedCells;

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

    //Display all of the correct text
    int maxCellRow = this.viewModel.getNumRows();
    int maxCellCol = this.viewModel.getNumColumns();

    // Save the clip state prior to drawing, and restore it after
    Shape initClip = g2d.getClip();

    // Check for highlighted cells first so that the contents still appear over the highlight
    for (Coord c : this.highlightedCells) {
      g2d.setClip((c.col - 1) * CELL_WIDTH, (c.row - 1) * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
      // Save previous color to restore after we are done highlighting the cell
      Color prevColor = g2d.getColor();
      g2d.setColor(Color.YELLOW);
      g2d.fillRect((c.col - 1) * CELL_WIDTH, (c.row - 1) * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
      g2d.setColor(prevColor);
    }

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
   * @param cellCoords The Coords of the new highlighted cells.
   *                   If this is null or empty, all cells are de-highlighted.
   */
  void setHighlightedCells(List<Coord> cellCoords) {
    this.highlightedCells.clear();
    if (cellCoords != null) {
      this.highlightedCells.addAll(cellCoords);
    }
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
    scrollRectToVisible(r);
  }

  @Override
  public void mouseMoved(MouseEvent e) {

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

