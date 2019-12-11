package edu.cs3500.spreadsheets.view;

import java.awt.*;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;

/**
 * Represents a renderer which will display the row and column headers.
 */

class HeaderRenderer extends JComponent {
  protected static final int HORIZONTAL_ORIENTATION = 0;
  protected static final int VERTICAL_ORIENTATION = 1;

  private ViewModel viewModel;
  private final int MAX_HEIGHT = 25;
  private final int MAX_WIDTH = 75;
  private int numViewCols;
  private int numViewRows;
  private int orientation;

  private int[] vertLines;
  private int[] horizLines;

  /**
   * @param viewModel
   * @param numViewCols
   * @param numViewRows
   * @param orientation
   */
  public HeaderRenderer(ViewModel viewModel, int numViewCols, int numViewRows, int orientation) {
    this.viewModel = viewModel;
    this.numViewCols = numViewCols;
    this.numViewRows = numViewRows;
    this.orientation = orientation;

    // Sizing for horizontal orientation
    if (orientation == 0) {
      int width = 0;
      for (int i = 0; i < numViewCols; i++) {
        width += this.viewModel.getColWidth(i + 1);
      }

      this.setPreferredSize(new Dimension(width, MAX_HEIGHT));
    }

    // Sizing for vertical orientation
    if (orientation == 1) {
      int height = 0;
      for (int i = 0; i < numViewRows; i++) {
        height += this.viewModel.getRowHeight(i + 1);
      }
      this.setPreferredSize(new Dimension(MAX_WIDTH, height));
    }

    this.setBorder(BorderFactory.createLineBorder(Color.black));
  }

  @Override
  public void paintComponent(Graphics g) {
    Rectangle drawHere = g.getClipBounds();
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);

    // Fill clipping area with dirty brown/orange.
    g.setColor(Color.lightGray);
    g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);

    g.setFont(new Font("SansSerif", Font.PLAIN, 12));
    g.setColor(Color.black);

    if (this.orientation == VERTICAL_ORIENTATION) {
      horizLines = new int[numViewRows + 1];
      horizLines[0] = 0;
      for (int i = 1; i < horizLines.length; i++) {
        horizLines[i] = horizLines[i - 1] + this.viewModel.getRowHeight(i);
      }

      for (int line : horizLines) {
        g2d.drawLine(0, line, MAX_WIDTH, line);
      }

      for (int i = 0; i < numViewRows; i++) {
        g2d.drawString(Integer.toString(i + 1), (MAX_WIDTH / 2), horizLines[i]
                + (this.viewModel.getRowHeight(i + 1) / 2) + 5);
      }
    }

    if (this.orientation == HORIZONTAL_ORIENTATION) {
      vertLines = new int[numViewCols + 1];
      vertLines[0] = 0;
      for (int i = 1; i < vertLines.length; i++) {
        vertLines[i] = vertLines[i - 1] + this.viewModel.getColWidth(i);
      }

      for (int line : vertLines) {
        g2d.drawLine(line, 0, line, MAX_HEIGHT);
      }

      for (int i = 0; i < numViewCols; i++) {
        g2d.drawString(Coord.colIndexToName(i + 1), vertLines[i]
                + (this.viewModel.getColWidth(i + 1) / 2), MAX_HEIGHT - 5);
      }
    }
  }
}
