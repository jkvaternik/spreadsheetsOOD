package edu.cs3500.spreadsheets.view;

import javax.swing.JFrame;

/**
 * Represents a visual view for the spreadsheet. This view displays the model as a table of
 * coordinates, and supports the capability of scrolling the table.
 */
public class VisualView extends JFrame implements View {
  private final ViewModel viewModel;

  /*
    TODO (Idea for how to do this):
     - Keep track of the top left and bottom right cell that are currently "in scope"
     - Render columns and rows, and get the cell's value for each cell in the range (and display it)
     - When we implement scrolling, all that should need to be updated is the "in scope" Coords.
   */

  public VisualView(ViewModel viewModel) {
    this.viewModel = viewModel;
  }

  @Override
  public void makeVisible() {

  }

  @Override
  public void refresh() {

  }
}
