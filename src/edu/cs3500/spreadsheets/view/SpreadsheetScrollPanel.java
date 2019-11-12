package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

public class SpreadsheetScrollPanel extends JPanel {
  private SpreadsheetPanel spreadsheetPanel;
  private final JScrollPane scrollPane;
  private final ViewModel viewModel;
  final Dimension size;

  SpreadsheetScrollPanel(ViewModel viewModel, Dimension size) {
    super();
    this.viewModel = viewModel;
    this.size = size;

    // Create SpreadsheetPanel and add to ScrollPane
    this.setSpreadsheetPanel(this.viewModel, this.size);

    this.scrollPane = new JScrollPane(this.spreadsheetPanel);
    scrollPane.setPreferredSize(new Dimension(1000, 600));

    this.setRowAndColHeaders(this.size);

    this.add(scrollPane);
  }

  /**
   * Changes the spreadsheet panel of this scroll panel.
   * @param viewModel The viewModel
   * @param size The size of the spreadsheet panel
   */
  void setSpreadsheetPanel(ViewModel viewModel, Dimension size) {
    this.spreadsheetPanel = new SpreadsheetPanel(viewModel, size);
  }

  /**
   * Sets the row and column headers for the spreadsheet based on the given Dimension.
   * @param dimension The dimension of the spreadsheet
   */
  void setRowAndColHeaders(Dimension dimension) {
    // Modify JScrollPane
    DefaultListModel<Integer> rowsList = new DefaultListModel<>();
    DefaultListModel<String> colsList = new DefaultListModel<>();

    for (int i = 0; i < dimension.height; i++) {
      rowsList.add(i, i + 1);
    }

    for (int j = 0; j < dimension.width; j++) {
      colsList.add(j, Coord.colIndexToName(j + 1));
    }

    // Create row and column headers
    JList rows = new JList<>(rowsList);
    JList cols = new JList<>(colsList);

    rows.setFixedCellWidth(75);
    rows.setFixedCellHeight(25);

    cols.setFixedCellWidth(75);
    cols.setFixedCellHeight(25);

    rows.setCellRenderer(new HeaderRenderer());

    cols.setCellRenderer(new HeaderRenderer());
    cols.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    // Shows the column header in one row (preventing it from wrapping)
    cols.setVisibleRowCount(1);

    scrollPane.setColumnHeaderView(cols);
    scrollPane.setRowHeaderView(rows);

    this.scrollPane.revalidate();
  }


  class HeaderRenderer extends JLabel implements ListCellRenderer {

    HeaderRenderer() {
      setOpaque(true);
      setBorder(UIManager.getBorder("TableHeader.cellBorder"));
      setHorizontalAlignment(CENTER);
      setForeground(Color.white);
      setBackground(Color.lightGray);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {
      setText(value.toString());
      return this;
    }
  }
}
