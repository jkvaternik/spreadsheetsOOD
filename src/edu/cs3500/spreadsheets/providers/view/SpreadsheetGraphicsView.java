package edu.cs3500.spreadsheets.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import edu.cs3500.spreadsheets.model.BasicWorksheetModel;


/**
 * Represents the graphical view of the Spreadsheet. This view uses the Java Swing library,
 * specifically the JTable to display the contents of the Spreadsheet.
 */
public class SpreadsheetGraphicsView extends JFrame implements SpreadsheetView {
  private ModelToTable modelToTable;

  /**
   * Constructs a Spreadsheet graphical view with the wrapper class.
   *
   * @param mtt wrapper class.
   */
  public SpreadsheetGraphicsView(ModelToTable mtt) {
    super();
    this.modelToTable = mtt;
    this.setTitle("Microsoft Excel v2.0");
    this.setSize(500, 300);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    RowListModel listModel = new RowListModel(this.modelToTable);

    DefaultTableModel defaultTableModel = new DefaultTableModel(listModel.getSize(),
            mtt.colNames().length) {
      // Ensures that the cells cannot be edited by clients directly from the view.
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    // Populates the table with Cells.
    for (int i = 0; i < mtt.numRows(); i++) {
      for (int j = 0; j < mtt.numCols(); j++) {
        defaultTableModel.setValueAt(this.modelToTable.translate()[j][i], i, j);
      }
    }

    JTable table = new JTable(defaultTableModel);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    JList<String> rowHeader = new JList<String>(listModel);
    rowHeader.setFixedCellWidth(50);
    rowHeader.setFixedCellHeight(table.getRowHeight());
    rowHeader.setCellRenderer(new RowHeaderRenderer(table));

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setRowHeaderView(rowHeader);
    getContentPane().add(scrollPane, BorderLayout.CENTER);
  }

  /**
   * Constructs a Spreadsheet graphical view with a new Spreadsheet.
   */
  public SpreadsheetGraphicsView() {
    super();
    this.setTitle("Microsoft Excel v2.0");
    this.setSize(500, 300);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.modelToTable = new ModelToTable(new BasicWorksheetModel());

    RowListModel listModel = new RowListModel(this.modelToTable);
    DefaultTableModel defaultTableModel = new DefaultTableModel(listModel.getSize(), 0) {

      // Ensures that the cells cannot be edited by clients directly from the view.
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    JTable table = new JTable(defaultTableModel);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    JList<String> rowHeader = new JList<String>(listModel);
    rowHeader.setFixedCellWidth(50);
    rowHeader.setFixedCellHeight(table.getRowHeight());
    rowHeader.setCellRenderer(new RowHeaderRenderer(table));

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setRowHeaderView(rowHeader);
    getContentPane().add(scrollPane, BorderLayout.CENTER);
  }

  @Override
  public void render() {
    SpreadsheetGraphicsView view = new SpreadsheetGraphicsView(modelToTable);
    view.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    view.setVisible(true);
  }
}

