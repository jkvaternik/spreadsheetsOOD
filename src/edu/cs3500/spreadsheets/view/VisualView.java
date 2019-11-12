package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 * Represents a visual view for the spreadsheet. This view displays the model as a table of
 * coordinates, and supports the capability of scrolling the table.
 */
public class VisualView extends JFrame implements View {
  private final ViewModel viewModel;
  private final JButton editButton;
  private final JButton increaseSizeButton;
  private final JPanel editPanel;
  private final JTextField userInputField;
  private SpreadsheetScrollPanel scrollPanel;

  public VisualView(ViewModel viewModel) {
    //Make the frame
    super("Spreadsheet");
    this.setSize(1000, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);

    this.viewModel = viewModel;

    // Set up the layout of the main spreadsheet spreadsheet
    this.setLayout(new BorderLayout());

    // Set up the scroll panel and its internal non-scrolling panel
    this.scrollPanel = new SpreadsheetScrollPanel(this.viewModel,
        new Dimension(Math.max(26, this.viewModel.getNumColumns()),
            Math.max(26, this.viewModel.getNumRows())));
    this.add(this.scrollPanel, BorderLayout.CENTER);

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
    this.increaseSizeButton.addActionListener(e -> {
      int oldWidth = VisualView.this.scrollPanel.size.width;
      int oldHeight = VisualView.this.scrollPanel.size.height;
      Dimension newDimension = new Dimension(oldWidth + 26, oldHeight + 26);
      scrollPanel.setSpreadsheetPanel(this.viewModel, newDimension);
      scrollPanel.setRowAndColHeaders(newDimension);
      this.refresh();
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
}
