package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;

public class SpreadsheetPanel extends JPanel implements Scrollable, MouseMotionListener {
  private final ViewModel viewModel;
  Dimension size;
  private final int CELL_WIDTH = 75;
  private final int CELL_HEIGHT = 25;

  private final int maxXIncrement = CELL_WIDTH;
  private final int maxYIncrement = CELL_HEIGHT;

  public SpreadsheetPanel(ViewModel viewModel, Dimension size) {
    super();
    this.viewModel = viewModel;
    this.size = size;

    this.setPreferredSize(new Dimension(CELL_WIDTH * size.width, CELL_HEIGHT * size.height));

    // Set up background
    this.setBackground(Color.WHITE);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);

    int maxHeight = this.size.width * this.CELL_HEIGHT;
    int maxWidth = this.size.height * this.CELL_WIDTH;

    // Draw all of the cell borders
    for (int horizLine = 0; horizLine <= size.height; horizLine++) {
      g2d.drawLine(0, horizLine * CELL_HEIGHT, maxWidth, horizLine * CELL_HEIGHT);
    }
    for (int vertLine = 0; vertLine <= size.width; vertLine++) {
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
    //Get the current position.
    int currentPosition = 0;
    if (orientation == SwingConstants.HORIZONTAL) {
      currentPosition = visibleRect.x;
      if (direction < 0) {
        int newPosition = currentPosition - (currentPosition / maxXIncrement) * maxXIncrement;
        return (newPosition == 0) ? maxXIncrement : newPosition;
      } else {
        return ((currentPosition / maxXIncrement) + 1) * maxXIncrement - currentPosition;
      }
    } else {
      currentPosition = visibleRect.y;
      if (direction < 0) {
        int newPosition = currentPosition - (currentPosition / maxYIncrement) * maxYIncrement;
        return (newPosition == 0) ? maxYIncrement : newPosition;
      } else {
        return ((currentPosition / maxYIncrement) + 1) * maxYIncrement - currentPosition;
      }
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

