package edu.cs3500.spreadsheets.view;

import java.awt.*;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;

/**
 * Represents a visual view for the spreadsheet. This view displays the model as a table of
 * coordinates, and supports the capability of scrolling the table.
 */
public class VisualView extends JFrame implements View {
  private final ViewModel viewModel;
  private final SpreadsheetPanel spreadsheetPanel;
  private final JButton editButton;
  private final JButton increaseSizeButton;
  private final JPanel editPanel;
  private final JTextField userInputField;
  private final JScrollPane scrollPane;

  public VisualView(ViewModel viewModel) {
    //Make the frame
    super("Spreadsheet");
    this.setSize(1000, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);

    this.viewModel = viewModel;

    // Set up the layout of the main spreadsheet spreadsheet
    this.setLayout(new BorderLayout());

    // Create SpreadsheetPanel and add to ScrollPane
    this.spreadsheetPanel = new SpreadsheetPanel(viewModel, this.getMaxDimension());

    this.scrollPane = new JScrollPane(this.spreadsheetPanel);
    scrollPane.setPreferredSize(new Dimension(1000, 600));
    // Modify JScrollPane
    DefaultListModel<String> rowsList = new DefaultListModel<>();
    DefaultListModel<String> colsList = new DefaultListModel<>();

    for (int i = 0; i < this.getMaxDimension().height; i++) {
      rowsList.add(i, Integer.toString(i + 1));
    }

    for (int j = 0; j < this.getMaxDimension().width; j++) {
      colsList.add(j, Coord.colIndexToName(j + 1));
    }

    // Create row and column headers
    JList<String> rows = new JList<>(rowsList);
    JList<String> cols = new JList<>(colsList);

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

    this.add(scrollPane, BorderLayout.CENTER);

    // Set up the panel which contains the text field and the edit button
    this.editPanel = new JPanel();
    this.editPanel.setLayout(new FlowLayout());
    this.add(this.editPanel, BorderLayout.SOUTH);

    // Set up the input text field and add it to its panel
    this.userInputField = new JTextField(25);
    this.editPanel.add(this.userInputField);

    // Set up the edit button (but have it do nothing for now)
    this.editButton = new JButton("Edit Cell");
    this.editPanel.add(this.editButton);

    // Set up the increase size button (but have it do nothing for now)
    this.increaseSizeButton = new JButton("Increase Size");
    this.editPanel.add(this.increaseSizeButton);

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

  private Dimension getMaxDimension() {
    int maxRows = this.viewModel.getNumRows();
    int maxCol = this.viewModel.getNumColumns();

    return new Dimension(Math.max(maxRows, 26), Math.max(maxCol, 26));
  }

  static class HeaderRenderer extends JLabel implements ListCellRenderer<String> {

    HeaderRenderer() {
      setOpaque(true);
      setBorder(UIManager.getBorder("TableHeader.cellBorder"));
      setHorizontalAlignment(CENTER);
      setForeground(Color.white);
      setBackground(Color.lightGray);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
      setText(value);
      return this;
    }
  }
}
