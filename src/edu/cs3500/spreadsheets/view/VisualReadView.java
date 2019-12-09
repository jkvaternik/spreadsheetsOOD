package edu.cs3500.spreadsheets.view;


import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

/**
 * Represents a visual view for the spreadsheet. This view displays the model as a table of
 * coordinates, and supports the capability of scrolling the table.
 */
public class VisualReadView extends JFrame implements View {

  private final ViewModel viewModel;
  private final JScrollPane scrollPane;
  private SpreadsheetPanel spreadsheetPanel;

  /**
   * Constructs an instance of the VisualReadView based on the ViewModel.
   *
   * @param viewModel the given ViewModel
   */
  public VisualReadView(ViewModel viewModel) {
    //Make the frame
    super("Spreadsheet");
    this.setSize(1000, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);

    this.viewModel = viewModel;
    //To start, there isn't a highlighted Cell

    // Set up the layout of the main spreadsheet spreadsheet
    this.setLayout(new BorderLayout());

    // Create SpreadsheetPanel and add to ScrollPane
    this.spreadsheetPanel = new SpreadsheetPanel(viewModel);

    int numRows = this.getMaxDimension().height;
    int numCols = this.getMaxDimension().width;

    int width = 0;
    int height = 0;

    for (int row = 0; row < numRows; row++) {
      height += this.viewModel.getRowHeight(row + 1);
    }

    for (int col = 0; col < numCols; col++) {
      width += this.viewModel.getColWidth(col + 1);
    }

    this.spreadsheetPanel.setPreferredSize(new Dimension(width, height));

    this.scrollPane = new JScrollPane(this.spreadsheetPanel);
    scrollPane.setPreferredSize(new Dimension(1000, 600));
    // Modify JScrollPane
    this.setHeaders(numCols, numRows);
    this.add(scrollPane, BorderLayout.CENTER);

    this.pack();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void addFeatures(Features features) {
    //Do nothing, as there are no features supported because this view is READ-ONLY.
  }

  @Override
  public void highlightCell(Coord cellCoord) {
    //Do nothing, since you cannot highlight a cell because this view is READ-ONLY.
  }

  /**
   * Calculates the max dimension of the starting grid. The max dimension is the number of rows and
   * columns in the spreadsheet respectively, rounded up to the nearest multiple of 26.
   *
   * @return The max dimension
   */
  private Dimension getMaxDimension() {
    int maxRows = this.viewModel.getNumRows();
    int maxCol = this.viewModel.getNumColumns();

    //Round these values up the the biggest multiple of 26
    int totalRows = (maxRows / 26 + 1) * 26;
    int totalCols = (maxCol / 26 + 1) * 26;

    return new Dimension(totalCols, totalRows);
  }

  /**
   * Set the row and column headers of the scroll pane.
   *
   * @param width  The width of the spreadsheet.
   * @param height The height of the spreadsheet.
   */
  private void setHeaders(int width, int height) {
    this.scrollPane.setColumnHeaderView(new HeaderRenderer(this.viewModel,
            width, height, HeaderRenderer.HORIZONTAL_ORIENTATION));
    this.scrollPane.setRowHeaderView(new HeaderRenderer(this.viewModel,
            width, height, HeaderRenderer.VERTICAL_ORIENTATION));
  }
}
