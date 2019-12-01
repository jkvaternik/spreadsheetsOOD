package edu.cs3500.spreadsheets.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;

import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents the graphical view of the Spreadsheet. This view uses the Java Swing library,
 * specifically the JTable to display the contents of the Spreadsheet.
 */
public class GUITableGraphics extends JPanel {
  private ModelToTable modelToTable;
  private DefaultTableModel defaultTableModel;
  private RowListModel listModel;
  private JTable table;
  private Coord selectedCell;
  private JTextField tfield;
  private JScrollPane scrollPane;

  /**
   * Constructs a Spreadsheet graphical view with the wrapper class.
   *
   * @param mtt wrapper class.
   */
  GUITableGraphics(ModelToTable mtt, JTextField tf) {
    super();
    this.modelToTable = mtt;
    this.tfield = tf;

    listModel = new RowListModel(this.modelToTable);

    defaultTableModel = new DefaultTableModel(listModel.getSize(),
            mtt.colNames().length) {
      // Ensures that the cells cannot be edited by clients directly from the view.
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    // Populates the table with Cells.
    for (int i = 0; i < modelToTable.numRows(); i++) {
      for (int j = 0; j < modelToTable.numCols(); j++) {
        defaultTableModel.setValueAt(this.modelToTable.translate()[j][i], i, j);
      }
    }

    table = new JTable(defaultTableModel);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    JList<String> rowHeader = new JList<String>(listModel);
    rowHeader.setFixedCellWidth(50);
    rowHeader.setFixedCellHeight(table.getRowHeight());
    rowHeader.setCellRenderer(new RowHeaderRenderer(table));

    scrollPane = new JScrollPane(table);
    scrollPane.setRowHeaderView(rowHeader);
    scrollPane.setPreferredSize(new Dimension(640, 480));
    this.add(scrollPane, BorderLayout.CENTER);

    MouseListener mouseTable = new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        selectedCell = new Coord(col + 1, row + 1);
        tfield.setText(modelToTable.formTranslate()[col][row]);
      }
    };
    table.addMouseListener(mouseTable);
  }

  /**
   * Constructs a Spreadsheet graphical view with a new Spreadsheet.
   */
  public GUITableGraphics() {
    super();
    this.modelToTable = new ModelToTable(new BasicWorksheetModel());

    RowListModel listModel = new RowListModel(this.modelToTable);
    DefaultTableModel defaultTableModel = new DefaultTableModel(listModel.getSize(),
            0) {

      // Ensures that the cells cannot be edited by clients directly from the view.
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    JTable table = new JTable(defaultTableModel);
    table.setPreferredSize(new Dimension(640, 480));
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.setRowSelectionAllowed(true);
    table.setColumnSelectionAllowed(true);

    JList<String> rowHeader = new JList<String>(listModel);
    rowHeader.setFixedCellWidth(50);
    rowHeader.setFixedCellHeight(table.getRowHeight());
    rowHeader.setCellRenderer(new RowHeaderRenderer(table));

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setRowHeaderView(rowHeader);
    scrollPane.setPreferredSize(new Dimension(640, 480));
    this.add(scrollPane, BorderLayout.CENTER);

    MouseListener mouseTable = new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        selectedCell = new Coord(col, row);
        tfield.setText(modelToTable.formTranslate()[col][row]);
      }
    };
    table.addMouseListener(mouseTable);
  }

  /**
   * Gets the selected cell from the table.
   *
   * @return the coord of the selected cell
   */
  Coord selectedCell() {
    if (selectedCell == null) {
      return null;
    }
    return new Coord(selectedCell);
  }

  /**
   * Updates the table to match the model.
   */
  void updateTable() {
    String[][] translated = this.modelToTable.translate();
    for (int i = 0; i < modelToTable.numRows(); i++) {
      for (int j = 0; j < modelToTable.numCols(); j++) {
        defaultTableModel.setValueAt(translated[j][i], i, j);
      }
    }
  }

  /**
   * Adds rows to be displayed.
   */
  void addRow() {
    defaultTableModel.setNumRows(defaultTableModel.getRowCount() + 50);
    listModel = new RowListModel(this.modelToTable);
    JList<String> rowHeader = new JList<String>(listModel);
    rowHeader.setFixedCellWidth(50);
    rowHeader.setFixedCellHeight(table.getRowHeight());
    rowHeader.setCellRenderer(new RowHeaderRenderer(table));
    scrollPane.setRowHeaderView(rowHeader);
  }

  /**
   * Adds columns to be displayed.
   */
  void addCol() {
    defaultTableModel.setColumnCount(defaultTableModel.getColumnCount() + 50);
  }
}
