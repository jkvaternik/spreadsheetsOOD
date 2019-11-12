package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

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


  /*
    TODO:
      - Add row/col headers
      - Add scrollPane
   */

  public VisualView(ViewModel viewModel) {
    //Make the frame
    super("Spreadsheet");
    this.setSize(500, 400);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);

    this.viewModel = viewModel;

    // Set up the layout of the main spreadsheet spreadsheet
    this.setLayout(new BorderLayout());
    // TODO: Make the initial spreadsheet 50 or the max of number of rows/cols

    // Create SpreadsheetPanel and add to ScrollPane
    this.spreadsheetPanel = new SpreadsheetPanel(viewModel, 20, 25);

    this.scrollPane = new JScrollPane(this.spreadsheetPanel);
    scrollPane.setPreferredSize(new Dimension(500, 400));

    // Modify JScrollPane
    DefaultListModel rowsList = new DefaultListModel();
    DefaultListModel colsList = new DefaultListModel();

    for (int i = 0; i < 20; i++) {
      rowsList.add(i, i + 1);
      colsList.add(i, Coord.colIndexToName(i + 1));
    }

    JList rows = new JList(rowsList);
    JList cols = new JList(colsList);

    rows.setFixedCellWidth(75);
    rows.setFixedCellHeight(25);

    cols.setFixedCellWidth(75);
    cols.setFixedCellHeight(25);

    rows.setCellRenderer(new RowRenderer());

    cols.setCellRenderer(new RowRenderer());
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

  class RowRenderer extends JLabel implements ListCellRenderer {

    RowRenderer() {
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

  class HeaderRenderer extends JLabel implements TableCellRenderer {

    HeaderRenderer() {
      setOpaque(true);
      setBorder(UIManager.getBorder("TableHeader.cellBorder"));
      setHorizontalAlignment(CENTER);
      setForeground(Color.white);
      setBackground(Color.lightGray);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
      setText(value.toString());
      return this;
    }
  }
}
