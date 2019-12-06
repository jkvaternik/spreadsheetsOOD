package edu.cs3500.spreadsheets.view;

import java.awt.*;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.SimpleSpreadsheet;
import edu.cs3500.spreadsheets.model.ViewModel;

public class RowRenderer extends JLabel implements ListCellRenderer<String> {
  private ViewModel viewModel;

  /**
   * A constructor to make an instance of a ColRenderer.
   */
  RowRenderer(ViewModel viewModel) {
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
    setPreferredSize(new Dimension(SimpleSpreadsheet.DEFAULT_ROW_HEIGHT,
            this.viewModel.getRowHeight(index));
    return this;
  }
}
