package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;

public class SpreadsheetPanel extends JPanel implements Scrollable, MouseMotionListener {
  private static final int CELL_WIDTH = 75;
  private static final int CELL_HEIGHT = 25;

  private final int maxXIncrement = CELL_WIDTH;
  private final int maxYIncrement = CELL_HEIGHT;

  private final ViewModel viewModel;

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

    //Display all of the correct text
    int maxCellRow = this.viewModel.getNumRows();
    int maxCellCol = this.viewModel.getNumColumns();

    // Save the clip state prior to drawing, and restore them after
    Shape initClip = g2d.getClip();

    for (int row = 1; row <= maxCellRow; row++) {
      for (int col = 1; col <= maxCellCol; col++) {
        String value = this.viewModel.getValue(new Coord(col, row));
        g2d.setClip((col - 1) * this.CELL_WIDTH, (row - 1) * this.CELL_HEIGHT, this.CELL_WIDTH, this.CELL_HEIGHT);
        g2d.drawString(value, (col - 1) * this.CELL_WIDTH, row * this.CELL_HEIGHT - this.CELL_HEIGHT / 3);
      }
    }
    g2d.setClip(initClip);
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

