package edu.cs3500.spreadsheets.view;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.table.JTableHeader;
import javax.swing.ListCellRenderer;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.UIManager;

/**
 * Renders the row headers of the Spreadsheet GUI.
 */
class RowHeaderRenderer extends JLabel implements ListCellRenderer {

  /**
   * Constructs a RowHeaderRenderer with the JTable used in the view.
   *
   * @param table JTable
   */
  RowHeaderRenderer(JTable table) {
    JTableHeader header = table.getTableHeader();
    setOpaque(true);
    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    setHorizontalAlignment(CENTER);
    setForeground(header.getForeground());
    setBackground(header.getBackground());
    setFont(header.getFont());
  }

  @Override
  public Component getListCellRendererComponent(JList list, Object value, int index,
                                                boolean isSelected, boolean cellHasFocus) {
    setText((value == null) ? "" : value.toString());
    return this;
  }
}
