package edu.cs3500.spreadsheets.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SimpleSpreadsheet;
import edu.cs3500.spreadsheets.model.ViewModel;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/**
 * Represents Swing component of the actual grid of a spreadsheet.
 */

public class SpreadsheetPanel extends JPanel implements Scrollable, MouseMotionListener {

  private final int maxXIncrement = 25;
  private final int maxYIncrement = 25;

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
  public SpreadsheetPanel(ViewModel viewModel) {
    super();
    this.viewModel = viewModel;

    // Set up background
    this.setBackground(Color.WHITE);
    setAutoscrolls(true); //enable synthetic drag events
    addMouseMotionListener(this); //handle mouse drags
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);

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

    int[] horizLines = new int[numViewRows];
    horizLines[0] = this.viewModel.getRowHeight(1);
    for (int i = 1; i < horizLines.length; i++) {
      horizLines[i] = horizLines[i - 1] + this.viewModel.getRowHeight(i + 1);
    }

    int[] vertLines = new int[numViewCols];
    vertLines[0] = this.viewModel.getColWidth(1);
    for (int i = 1; i < vertLines.length; i++) {
      vertLines[i] = vertLines[i - 1] + this.viewModel.getColWidth(i + 1);
    }


    // Draw all of the cell borders
    for (int horizLine = 1; horizLine <= horizLines.length; horizLine++) {
      g2d.drawLine(0, horizLines[horizLine - 1], maxWidth, horizLines[horizLine - 1]);
    }
    for (int vertLine = 1; vertLine <= vertLines.length; vertLine++) {
      g2d.drawLine(vertLines[vertLine - 1], 0, vertLines[vertLine - 1], maxHeight);
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
      g2d.setClip((this.highlightedCell.col - 1) * viewModel.getColWidth(this.highlightedCell.col - 1),
              (this.highlightedCell.row - 1) * viewModel.getRowHeight(this.highlightedCell.row), viewModel.getColWidth(this.highlightedCell.col - 1), viewModel.getRowHeight(this.highlightedCell.row));
      // Save previous color to restore after we are done highlighting the cell
      Color prevColor = g2d.getColor();
      g2d.setColor(Color.CYAN);
      g2d.fillRect((this.highlightedCell.col - 1) * viewModel.getColWidth(this.highlightedCell.col - 1),
              (this.highlightedCell.row - 1) * viewModel.getRowHeight(this.highlightedCell.row), viewModel.getColWidth(this.highlightedCell.col - 1), viewModel.getRowHeight(this.highlightedCell.row));
      g2d.setColor(prevColor);
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

    for (int row = 1; row <= maxCellRow; row++) {
      for (int col = 1; col <= maxCellCol; col++) {
        Coord coord = new Coord(col, row);
        String value = this.viewModel.getValue(coord);
        g2d.setClip((col - 1) * viewModel.getRowHeight(col), (row - 1) * viewModel.getRowHeight(row), viewModel.getRowHeight(col), viewModel.getRowHeight(row));
        g2d.drawString(value, (col - 1) * viewModel.getRowHeight(col), row * viewModel.getRowHeight(row) - viewModel.getRowHeight(row) / 3);
      }
    }
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

