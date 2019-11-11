package edu.cs3500.spreadsheets.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;

public class SpreadsheetPanel extends JPanel {
  private final ViewModel viewModel;
  private final JTable table;

  public SpreadsheetPanel(ViewModel viewModel) {

    // TODO: Ask about scrolling and adding rows/columns to TableModel

    //Sets up the Table model
    int numRows = viewModel.getNumRows();
    int numCols = viewModel.getNumColumns();
    TableModel tm = new DefaultTableModel(Math.max(26, numRows), Math.max(26, numCols)) {
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    for (int row = 1; row <= numRows; row++) {
      for (int col = 1; col <= numCols; col++) {
        tm.setValueAt(viewModel.getValue(new Coord(col, row)), row - 1, col - 1);
      }
    }

    // Set the layout of the different components
    this.setLayout(new BorderLayout());
    this.viewModel = viewModel;
    this.table = new JTable(tm);

    // Set the table UI properties
    this.table.setGridColor(Color.gray);
    this.table.setPreferredScrollableViewportSize(new Dimension(600, 400));
    this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    DefaultListModel rowLM = new DefaultListModel();
    for (int row = 1; row <= table.getRowCount(); row++) {
      rowLM.add(row - 1, row);
    }

    JList rowHeader = new JList(rowLM);
    rowHeader.setFixedCellWidth(50);
    rowHeader.setFixedCellHeight(table.getRowHeight());
    rowHeader.setCellRenderer(new HeaderRenderer(this.table));

    JScrollPane scrollPane = new JScrollPane(this.table);
    scrollPane.setRowHeaderView(rowHeader);
    this.add(scrollPane);

  }
}

class HeaderRenderer extends JLabel implements ListCellRenderer {

  HeaderRenderer(JTable table) {
    JTableHeader header = table.getTableHeader();
    setOpaque(true);
    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    setHorizontalAlignment(CENTER);
    setForeground(header.getForeground());
    setBackground(header.getBackground());
    setFont(header.getFont());
    setBackground(Color.lightGray);
  }

  public Component getListCellRendererComponent(JList list, Object value, int index,
                                                boolean isSelected, boolean cellHasFocus) {
    setText(value.toString());
    return this;
  }
}

