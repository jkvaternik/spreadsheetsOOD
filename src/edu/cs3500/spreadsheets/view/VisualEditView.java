package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;

/**
 * Represents a visual view for the spreadsheet. This view displays the model as a table of
 * coordinates, and supports the capability of scrolling the table.
 */
public class VisualEditView extends JFrame implements View {

  private static final int INCREMENT_AMOUNT = 26;

  private final ViewModel viewModel;
  private SpreadsheetPanel spreadsheetPanel;
  private final JButton confirmEditButton;
  private final JButton rejectEditButton;
  private final JScrollPane scrollPane;
  private final JTextField userInputField;

  /**
   * Constructs an instance of the VisualReadView based on the ViewModel.
   *
   * @param viewModel the given ViewModel
   */
  public VisualEditView(ViewModel viewModel) {
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
    JPanel editPanel = new JPanel();
    editPanel.setLayout(new FlowLayout());
    this.add(editPanel, BorderLayout.SOUTH);

    // Set up the input text field and add it to its panel
    this.userInputField = new JTextField(25);
    editPanel.add(userInputField);

    // Set up the confirm edit button
    this.confirmEditButton = new JButton("Edit Cell");
    this.confirmEditButton.setActionCommand("Edit Cell");
    editPanel.add(this.confirmEditButton);

    // Set up the reject edit button
    this.rejectEditButton = new JButton("Clear edit");
    this.rejectEditButton.setActionCommand("Clear edit");
    this.rejectEditButton.addActionListener(e -> {
      this.userInputField.setText("");
    });

    editPanel.add(this.rejectEditButton);

    // Set up the increase size button (but have it do nothing for now)
    JButton increaseSizeButton = new JButton("Increase Size");
    increaseSizeButton.addActionListener(e -> {
      int oldH = VisualEditView.this.spreadsheetPanel.getPreferredSize().height
          / SpreadsheetPanel.CELL_HEIGHT;
      int oldW = VisualEditView.this.spreadsheetPanel.getPreferredSize().width
          / SpreadsheetPanel.CELL_WIDTH;

      VisualEditView.this.spreadsheetPanel.setPreferredSize(
          new Dimension(SpreadsheetPanel.CELL_WIDTH * (oldW + INCREMENT_AMOUNT),
              SpreadsheetPanel.CELL_HEIGHT * (oldH + INCREMENT_AMOUNT)));
      VisualEditView.this.spreadsheetPanel.revalidate();
      VisualEditView.this.spreadsheetPanel.repaint();

      VisualEditView.this.setHeaders(oldW + 26, oldH + 26);
    });
    editPanel.add(increaseSizeButton);

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
  public void addFeatures(Features features) {
    this.confirmEditButton
            .addActionListener(evt -> {
              String userString = "";
              if (userInputField.getText() != null) {
                userString = userInputField.getText();
              }
              features.selectedCellEdited(userString);
            });
    this.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int clickX = e.getX();
        int clickY = e.getY();

        int coordX = clickX / SpreadsheetPanel.CELL_WIDTH + 1;
        int coordY = clickY / SpreadsheetPanel.CELL_HEIGHT + 1;
        Coord cellCoord = new Coord(coordX, coordY);
        features.cellSelected(cellCoord);
        System.out.println(cellCoord.toString());
      }

      @Override
      public void mousePressed(MouseEvent e) {
        //We only care about mouse clicks
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        //We only care about mouse clicks
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        //We only care about mouse clicks
      }

      @Override
      public void mouseExited(MouseEvent e) {
        //We only care about mouse clicks
      }
    });
  }

  @Override
  public void addActionListener(ActionListener listener) {
    this.confirmEditButton.addActionListener(listener);
  }

  @Override
  public void addMouseListener(MouseListener listener) {
    this.spreadsheetPanel.addMouseListener(listener);
  }

  @Override
  public void highlightCell(Coord cellCoord) {
    this.spreadsheetPanel.setHighlightedCell(cellCoord);
    this.userInputField.setText(this.viewModel.getRawContents(cellCoord));
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
