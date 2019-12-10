package edu.cs3500.spreadsheets.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;


import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/**
 * Represents Swing component of the actual grid of a spreadsheet.
 */

public class SpreadsheetPanel extends JPanel implements Scrollable, MouseMotionListener {

  private final int maxXIncrement = 75;
  private final int maxYIncrement = 25;

  //Keep track of the pixel locations for every horizontal and vertical line so
  private int[] horizLines;
  private int[] vertLines;

  private final ViewModel viewModel;
  private Coord highlightedCell;

  // These integers are only packet-private because they are internal to the view
  int numViewRows;
  int numViewCols;

  /**
   * Constructs an instance of the SpreadsheetPanel based on the given ViewModel.
   *
   * @param viewModel the given ViewModel
   */
  public SpreadsheetPanel(ViewModel viewModel, int numViewRows, int numViewCols) {
    super();
    this.viewModel = viewModel;
    this.numViewCols = numViewCols;
    this.numViewRows = numViewRows;

    // Set up background
    this.setBackground(Color.WHITE);

    int height = 0;
    int width = 0;

    for (int row = 0; row < this.numViewRows; row++) {
      height += this.viewModel.getRowHeight(row + 1);
    }

    for (int col = 0; col < this.numViewCols; col++) {
      width += this.viewModel.getColWidth(col + 1);
    }

    this.setPreferredSize(new Dimension(width, height));

    setAutoscrolls(true); //enable synthetic drag events
    addMouseMotionListener(this); //handle mouse drags
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);

    horizLines = new int[numViewRows + 1];
    horizLines[0] = 0;
    for (int i = 1; i < horizLines.length; i++) {
      horizLines[i] = horizLines[i - 1] + this.viewModel.getRowHeight(i);
    }

    vertLines = new int[numViewCols + 1];
    vertLines[0] = 0;
    for (int i = 1; i < vertLines.length; i++) {
      vertLines[i] = vertLines[i - 1] + this.viewModel.getColWidth(i);
    }

    drawCells(g2d);

    // Save the clip state prior to drawing, and restore it after
    Shape initClip = g2d.getClip();
    drawHighlightedCell(g2d);
    drawCellContents(g2d);
    g2d.setClip(initClip);
  }

  /**
   * Draws all the cell borders on a given Graphics object.
   *
   * @param g2d the Graphics object
   */
  private void drawCells(Graphics2D g2d) {
    int maxHeight = this.getPreferredSize().height;
    int maxWidth = this.getPreferredSize().width;


    // Draw all of the cell borders
    for (int line : horizLines) {
      g2d.drawLine(0, line, maxWidth, line);
    }
    for (int line : vertLines) {
      g2d.drawLine(line, 0, line, maxHeight);
    }
  }

  /**
   * Draws the highlighted cell on a given Graphics object.
   *
   * @param g2d the Graphics object
   */
  private void drawHighlightedCell(Graphics2D g2d) {
    // Check for highlighted cells first so that the contents still appear over the highlight
    if (this.highlightedCell != null) {
      int col = this.highlightedCell.col;
      int row = this.highlightedCell.row;

      //Save clip and restore after
      Shape init = g2d.getClip();

      g2d.setClip(vertLines[col - 1], horizLines[row - 1],
          this.viewModel.getColWidth(col), this.viewModel.getRowHeight(row));
      g2d.clip(init);
      // Save previous color to restore after we are done highlighting the cell
      Color prevColor = g2d.getColor();
      g2d.setColor(Color.CYAN);
      g2d.fillRect(vertLines[col - 1], horizLines[row - 1],
          this.viewModel.getColWidth(col), this.viewModel.getRowHeight(row));
      g2d.setColor(prevColor);

      //Restores the clip
      g2d.setClip(init);
    }
  }

  /**
   * Draws all the cell contents on a given Graphics object.
   *
   * @param g2d the Graphics object
   */
  private void drawCellContents(Graphics2D g2d) {
    //Display all of the correct text
    int maxCellRow = this.viewModel.getNumRows();
    int maxCellCol = this.viewModel.getNumColumns();

    //Save clip and restore after
    Shape init = g2d.getClip();

    for (int row = 1; row <= maxCellRow; row++) {
      for (int col = 1; col <= maxCellCol; col++) {
        Coord coord = new Coord(col, row);
        String value = this.viewModel.getValue(coord);

        g2d.setClip(vertLines[col - 1], horizLines[row - 1],
            this.viewModel.getColWidth(col), this.viewModel.getRowHeight(row));
        g2d.clip(init);

        g2d.drawString(value, vertLines[col - 1],
            horizLines[row] - (horizLines[row] - horizLines[row - 1]) / 3);
      }
    }

    //Restore the clip
    g2d.setClip(init);
  }

  /**
   * Changes the highlightedCells of this spreadsheet panel. It de-highlights any previously
   * highlighted cells.
   *
   * @param cellCoord The Coords of the new highlighted cells. If this is null or empty, all cells
   *                  are de-highlighted.
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
    return new Dimension(995, 595);
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

