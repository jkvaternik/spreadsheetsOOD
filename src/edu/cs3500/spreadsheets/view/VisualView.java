package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * Represents a visual view for the spreadsheet. This view displays the model as a table of
 * coordinates, and supports the capability of scrolling the table.
 */
public class VisualView extends JFrame implements View {
  private final ViewModel viewModel;
  private final JTable table;

  /*
    TODO (Idea for how to do this):
     - Keep track of the top left and bottom right cell that are currently "in scope"
     - Render columns and rows, and get the cell's value for each cell in the range (and display it)
     - When we implement scrolling, all that should need to be updated is the "in scope" Coords.
   */

  public VisualView(ViewModel viewModel) {

    //Make the frame
    super("Spreadsheet");
    this.setSize(500,500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Sets up the Table model
    int numRows = viewModel.getNumRows();
    int numCols = viewModel.getNumColumns();
    TableModel tm = new DefaultTableModel(Math.max(25, numRows), Math.max(25, numCols));
    for (int row = 1; row <= numRows; row++) {
      for (int col = 1; col <= numCols; col++) {
        tm.setValueAt(viewModel.getValue(new Coord(col, row)), row - 1, col - 1);
      }
    }


    //Set the layout of the different components
    this.setLayout(new BorderLayout());
    this.viewModel = viewModel;
    this.table = new JTable(tm);

    this.add(table);

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
}
