package edu.cs3500.spreadsheets.view;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import edu.cs3500.spreadsheets.model.SimpleSpreadsheet;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.ViewModel;

/**
 * Represents a renderer which will display the row and column headers.
 */

class ColRenderer extends JLabel implements ListCellRenderer<String> {
  private ViewModel viewModel;
  /**
   * A constructor to make an instance of a ColRenderer.
   */
  ColRenderer(ViewModel viewModel) {
    setOpaque(true);
    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    setHorizontalAlignment(CENTER);
    setForeground(Color.white);
    setBackground(Color.lightGray);

    this.viewModel = viewModel;
  }

  @Override
  public Component getListCellRendererComponent(JList<? extends String> list, String value,
                                                int index, boolean isSelected,
                                                boolean cellHasFocus) {
    setText(value);
    setPreferredSize(new Dimension(this.viewModel.getColWidth(index),
            SimpleSpreadsheet.DEFAULT_ROW_HEIGHT));
    return this;
  }
}
