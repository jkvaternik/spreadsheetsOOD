package edu.cs3500.spreadsheets.view;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SimpleSpreadsheet;
import edu.cs3500.spreadsheets.model.ViewModel;

/**
 * Represents a visual view for the spreadsheet. This view displays the model as a table of
 * coordinates, and supports the capability of scrolling the table.
 */
public class VisualEditView extends JFrame implements View {

  private static final int INCREMENT_AMOUNT = 26;
  private final JButton confirmEditButton;
  private final JButton rejectEditButton;
  private final JButton increaseSizeButton;
  private final JButton increaseRowButton;
  private final JButton increaseColButton;
  private final JButton decreaseRowButton;
  private final JButton decreaseColButton;
  private final JTextField userInputField;
  private final JMenuItem menuItemSave;
  private final JMenuItem menuItemOpen;

  private ViewModel viewModel;
  private SpreadsheetPanel spreadsheetPanel;
  private JScrollPane scrollPane;

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

    this.spreadsheetPanel.numViewRows = this.getMaxDimension().height;
    this.spreadsheetPanel.numViewCols = this.getMaxDimension().width;

    int width = 0;
    int height = 0;

    for (int row = 0; row < this.spreadsheetPanel.numViewRows; row++) {
      height += this.viewModel.getRowHeight(row + 1);
    }

    for (int col = 0; col < this.spreadsheetPanel.numViewCols; col++) {
      width += this.viewModel.getColWidth(col + 1);
    }

    this.spreadsheetPanel.setPreferredSize(new Dimension(width, height));

    this.scrollPane = new JScrollPane(this.spreadsheetPanel);
    scrollPane.setPreferredSize(new Dimension(995, 595));

    // Modify JScrollPane
    this.setHeaders(spreadsheetPanel.numViewCols, spreadsheetPanel.numViewRows);
    this.add(scrollPane, BorderLayout.CENTER);

    // Set up the panel which contains the text field and the edit button
    JPanel editPanel = new JPanel();
    editPanel.setLayout(new FlowLayout());
    this.add(editPanel, BorderLayout.SOUTH);

    // Set up the input text field and add it to its panel
    this.userInputField = new JTextField(50);
    editPanel.add(this.userInputField);

    // Set up the confirm edit button
    this.confirmEditButton = new JButton("Edit Cell");
    this.confirmEditButton.setActionCommand("Edit Cell");
    editPanel.add(this.confirmEditButton);

    // Set up the reject edit button
    this.rejectEditButton = new JButton("Clear edit");
    this.rejectEditButton.setActionCommand("Clear edit");
    this.rejectEditButton.addActionListener(e -> this.userInputField.setText(""));

    editPanel.add(this.rejectEditButton);

    // Set up the increase size button
    this.increaseSizeButton = new JButton("Increase Size");
    editPanel.add(this.increaseSizeButton);


    // Set up the buttons for increasing/decreasing row and col size
    this.increaseRowButton = new JButton("Row +");
    editPanel.add(this.increaseRowButton);

    this.decreaseRowButton = new JButton("Row -");
    editPanel.add(this.decreaseRowButton);

    this.increaseColButton = new JButton("Col +");
    editPanel.add(this.increaseColButton);

    this.decreaseColButton = new JButton("Col -");
    editPanel.add(this.decreaseColButton);


    // Create a Menu Bar
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    JMenuBar menuBar = new JMenuBar();

    JMenu menu = new JMenu("File");
    menuBar.add(menu);

    this.menuItemOpen = new JMenuItem("Open...");
    this.menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
        Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

    this.menuItemSave = new JMenuItem("Save");
    menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
        Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

    menu.add(this.menuItemOpen);
    menu.addSeparator();
    menu.add(this.menuItemSave);

    this.setJMenuBar(menuBar);

    this.pack();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void refresh() {
    this.spreadsheetPanel.revalidate();
    this.spreadsheetPanel.repaint();

    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void addFeatures(Features features) {
    this.confirmEditButton.addActionListener(evt -> {
      String userString = "";
      if (userInputField.getText() != null) {
        userString = userInputField.getText();
      }
      features.selectedCellEdited(userString);
    });

    this.increaseColButton.addActionListener(evt -> {
      features.changeColSize(10);
      this.setHeaders(spreadsheetPanel.numViewCols, spreadsheetPanel.numViewRows);

      Dimension temp = spreadsheetPanel.getPreferredSize();
      spreadsheetPanel.setPreferredSize(new Dimension(temp.width + 10, temp.height));

      VisualEditView.this.spreadsheetPanel.revalidate();
      VisualEditView.this.spreadsheetPanel.repaint();
    });
    this.increaseRowButton.addActionListener(evt -> {
      features.changeRowSize(10);
      this.setHeaders(spreadsheetPanel.numViewCols, spreadsheetPanel.numViewRows);

      Dimension temp = spreadsheetPanel.getPreferredSize();
      spreadsheetPanel.setPreferredSize(new Dimension(temp.width + 10, temp.height));

      VisualEditView.this.spreadsheetPanel.revalidate();
      VisualEditView.this.spreadsheetPanel.repaint();
    });
    this.decreaseColButton.addActionListener(evt -> {
      features.changeColSize(-10);
      this.setHeaders(spreadsheetPanel.numViewCols, spreadsheetPanel.numViewRows);

      Dimension temp = spreadsheetPanel.getPreferredSize();
      spreadsheetPanel.setPreferredSize(new Dimension(temp.width - 10, temp.height));

      VisualEditView.this.spreadsheetPanel.revalidate();
      VisualEditView.this.spreadsheetPanel.repaint();
    });
    this.decreaseRowButton.addActionListener(evt -> {
      features.changeRowSize(-10);
      this.setHeaders(spreadsheetPanel.numViewCols, spreadsheetPanel.numViewRows);

      Dimension temp = spreadsheetPanel.getPreferredSize();
      spreadsheetPanel.setPreferredSize(new Dimension(temp.width, temp.height - 10));

      VisualEditView.this.spreadsheetPanel.revalidate();
      VisualEditView.this.spreadsheetPanel.repaint();
    });

    this.increaseSizeButton.addActionListener(e -> {
      int oldH = VisualEditView.this.spreadsheetPanel.getPreferredSize().height;
      int oldW = VisualEditView.this.spreadsheetPanel.getPreferredSize().width;

      VisualEditView.this.spreadsheetPanel.setPreferredSize(
              new Dimension(oldW + (SimpleSpreadsheet.DEFAULT_COL_WIDTH * INCREMENT_AMOUNT),
                      oldH + (SimpleSpreadsheet.DEFAULT_ROW_HEIGHT * INCREMENT_AMOUNT)));

      VisualEditView.this.spreadsheetPanel.numViewCols += 26;
      VisualEditView.this.spreadsheetPanel.numViewRows += 26;

      //VisualEditView.this.spreadsheetPanel.scrollRectToVisible(new Rectangle(0, 0, 0, 0));

      VisualEditView.this.spreadsheetPanel.revalidate();
      VisualEditView.this.spreadsheetPanel.repaint();

      VisualEditView.this.setHeaders(spreadsheetPanel.numViewCols, spreadsheetPanel.numViewRows);
    });

    this.spreadsheetPanel.addMouseListener(new SpreadsheetMouseListener(features, viewModel));

    // The text field needs its own action listener so it knows what to do when the user has
    // pressed entered while the field is in focus
    this.userInputField
        .addActionListener(e -> features.selectedCellEdited(userInputField.getText()));

    this.addKeyListener(new SpreadsheetKeyListener(features, this.userInputField));

    this.menuItemOpen.addActionListener(e -> {
      final JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fileChooser.setAcceptAllFileFilterUsed(false);

      fileChooser.addChoosableFileFilter(new FileFilter() {
        @Override
        public boolean accept(File f) {
          if (f.isDirectory()) {
            return true;
          } else {
            return f.getName().toLowerCase().endsWith(".txt");
          }
        }

        @Override
        public String getDescription() {
          return "Text file (*.txt)";
        }
      });

      int returnVal = fileChooser.showOpenDialog(this);

      if (returnVal == JFileChooser.APPROVE_OPTION) {
        // Creates a JOptionPane that warns user of losing unsaved changes
        Object[] options = {"OK", "CANCEL"};
        int optionVal = JOptionPane.showOptionDialog(this,
            "Any unsaved changes will be lost. Would you like to continue?",
            "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
            null, options, options[0]);
        if (optionVal == JOptionPane.YES_OPTION) {
          File file = fileChooser.getSelectedFile();
          // Sets this view as not visibile
          this.setVisible(false);
          features.loadFile(file);
          // Destroys this view, returning any memory consumed back to the OS
          this.dispose();
        }
      }
    });

    this.menuItemSave.addActionListener(e -> {
      final JFileChooser fileChooser = new JFileChooser();
      int returnVal = fileChooser.showSaveDialog(this);

      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        features.saveFile(file);
      }
    });
  }

  @Override
  public void highlightCell(Coord cellCoord) {
    this.spreadsheetPanel.setHighlightedCell(cellCoord);
    this.userInputField.setText(this.viewModel.getRawContents(cellCoord));
    this.refresh();
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
    this.scrollPane.setColumnHeaderView(new HeaderRenderer(this.viewModel,
            width, height, HeaderRenderer.HORIZONTAL_ORIENTATION));
    this.scrollPane.setRowHeaderView(new HeaderRenderer(this.viewModel,
            width, height, HeaderRenderer.VERTICAL_ORIENTATION));
  }
}
