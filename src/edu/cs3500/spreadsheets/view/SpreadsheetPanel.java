package edu.cs3500.spreadsheets.view;

import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;

public class SpreadsheetPanel extends JPanel {
  private final ViewModel viewModel;
  int size;
  private final int CELL_WIDTH = 75;
  private final int CELL_HEIGHT = 25;

  public SpreadsheetPanel(ViewModel viewModel, int size) {

    /* TODO: New plan
              - 3 Components in our frame: Row header, column header, and cells
              - The cell component consists of a Graphics2D drawing of a bunch of lines
              - For controlling, we will have the notion of highlighting a cell and will have an
                input text field where you can set the value of the highlighted cell
              - Scrolling will be hard, but everything else should be easy
     */
    super();
    this.viewModel = viewModel;
    this.size = size;

    // Set up background
    this.setBackground(Color.WHITE);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);

    int maxHeight = this.size * this.CELL_HEIGHT;
    int maxWidth = this.size * this.CELL_WIDTH;

    // Draw all of the cell borders
    for (int horizLine = 0; horizLine <= size; horizLine++) {
      g2d.drawLine(0, horizLine * CELL_HEIGHT, maxWidth, horizLine * CELL_HEIGHT);
    }
    for (int vertLine = 0; vertLine <= size; vertLine++) {
      g2d.drawLine(vertLine * CELL_WIDTH, 0, vertLine * CELL_WIDTH, maxHeight);
    }

    //Display all of the correct text
    int maxCellRow = this.viewModel.getNumRows();
    int maxCellCol = this.viewModel.getNumColumns();

    for (int row = 1; row <= maxCellRow; row++) {
      for (int col = 1; col <= maxCellCol; col++) {
        String value = this.viewModel.getValue(new Coord(col, row));
        AffineTransform initState = g2d.getTransform();
        g2d.setClip((col - 1) * this.CELL_WIDTH, row * this.CELL_HEIGHT, this.CELL_WIDTH, this.CELL_HEIGHT);
        g2d.drawString(value, (col - 1) * this.CELL_WIDTH, row * this.CELL_HEIGHT);
        g2d.setTransform(initState);
      }
    }
  }
}

