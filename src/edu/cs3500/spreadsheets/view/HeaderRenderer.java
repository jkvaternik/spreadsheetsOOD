package edu.cs3500.spreadsheets.view;


import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

/**
 * Represents a renderer which will display the row and column headers.
 */

class HeaderRenderer extends JLabel implements ListCellRenderer<String> {
  /**
   * A constructor to make an instance of a HeaderRenderer.
   */
  HeaderRenderer() {
    setOpaque(true);
    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    setHorizontalAlignment(CENTER);
    setForeground(Color.white);
    setBackground(Color.lightGray);
  }

  @Override
  public Component getListCellRendererComponent(JList<? extends String> list, String value,
                                                int index, boolean isSelected,
                                                boolean cellHasFocus) {
    setText(value);
    return this;
  }
}
