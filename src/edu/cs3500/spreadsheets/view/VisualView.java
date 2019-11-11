package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * Represents a visual view for the spreadsheet. This view displays the model as a table of
 * coordinates, and supports the capability of scrolling the table.
 */
public class VisualView extends JFrame implements View {
  private final ViewModel viewModel;
  private SpreadsheetPanel spreadsheetPanel;
  private final JButton editButton;
  private final JButton increaseSizeButton;
  private final JPanel editPanel;
  private final JTextField userInputField;


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
    this.spreadsheetPanel = new SpreadsheetPanel(viewModel, 50);
    this.spreadsheetPanel.setPreferredSize(new Dimension(1000, 400));
    JScrollPane scrollPane = new JScrollPane(this.spreadsheetPanel);
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
}
