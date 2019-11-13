package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;

/**
 * Represents a visual view for the spreadsheet. This view displays the model as a table of
 * coordinates, and supports the capability of scrolling the table.
 */
public class VisualView extends JFrame implements View {

  private static final int INCREMENT_AMOUNT = 26;

  private final ViewModel viewModel;
  private SpreadsheetPanel spreadsheetPanel;
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
    //To start, there isn't a highlighted Cell

    // Set up the layout of the main spreadsheet spreadsheet
    this.setLayout(new BorderLayout());

    // Create SpreadsheetPanel and add to ScrollPane
    this.spreadsheetPanel = new SpreadsheetPanel(viewModel);

    int numRows = this.getMaxDimension().height;
    int numCols = this.getMaxDimension().width;

    this.spreadsheetPanel.setPreferredSize(new Dimension(75 * numCols, 25 * numRows));

    this.scrollPane = new JScrollPane(this.spreadsheetPanel);
    scrollPane.setPreferredSize(new Dimension(1000, 600));
    // Modify JScrollPane
    this.setHeaders(numCols, numRows);
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
    this.increaseSizeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int oldH = VisualView.this.spreadsheetPanel.getPreferredSize().height / 25;
        int oldW = VisualView.this.spreadsheetPanel.getPreferredSize().width / 75;

        VisualView.this.spreadsheetPanel.setPreferredSize(
            new Dimension(75 * (oldW + INCREMENT_AMOUNT), 25 * (oldH + INCREMENT_AMOUNT)));
        VisualView.this.spreadsheetPanel.revalidate();
        VisualView.this.spreadsheetPanel.repaint();

        VisualView.this.setHeaders(oldW + 26, oldH + 26);
      }
    });
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

  @Override
  public void addActionListener(ActionListener listener) {
    this.editButton.addActionListener(listener);
  }

  @Override
  public void addMouseListener(MouseListener listener) {
    this.spreadsheetPanel.addMouseListener(listener);
  }

  @Override
  public void highlightCells(List<Coord> cellCoords) {
    this.spreadsheetPanel.setHighlightedCells(cellCoords);
  }

  /**
   * Calculates the max dimension of the starting grid. The max dimension is the number of rows and
   * columns in the spreadsheet respectively, rounded up to the nearest multiple of 26.
   *
   * @return The max dimension
   */
  private Dimension getMaxDimension() {
    int maxRows = this.viewModel.getNumRows();
    int maxCol = this.viewModel.getNumColumns();

    //Round these values up the the biggest multiple of 26
    int totalRows = (maxRows / 26 + 1) * 26;
    int totalCols = (maxCol / 26 + 1) * 26;

    return new Dimension(totalCols, totalRows);
  }

  /**
   * Set the row and column headers of the scroll pane.
   *
   * @param width  The width of the spreadsheet.
   * @param height The height of the spreadsheet.
   */
  private void setHeaders(int width, int height) {
    DefaultListModel<String> rowsList = new DefaultListModel<>();
    DefaultListModel<String> colsList = new DefaultListModel<>();

    for (int i = 0; i < height + 26; i++) {
      rowsList.add(i, Integer.toString(i + 1));
    }

    for (int j = 0; j < width + 26; j++) {
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
  }

  /**
   * Represents a renderer which will display the row and column headers.
   */
  static class HeaderRenderer extends JLabel implements ListCellRenderer<String> {

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
        int index, boolean isSelected, boolean cellHasFocus) {
      setText(value);
      return this;
    }
  }
}
