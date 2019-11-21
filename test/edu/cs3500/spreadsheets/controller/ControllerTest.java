package edu.cs3500.spreadsheets.controller;


import static org.junit.Assert.assertEquals;

import edu.cs3500.spreadsheets.model.SimpleSpreadsheet;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.view.SpreadsheetKeyListener;
import edu.cs3500.spreadsheets.view.SpreadsheetMouseListener;
import edu.cs3500.spreadsheets.view.View;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import javax.swing.JTextField;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the Spreadsheet controller. Utilizes mock models, views, and features in order to check
 * that the "wiring" of the 3 MVC components has been done successfully.
 */
public class ControllerTest {
  Controller controller;
  Features mockFeatures;
  View mockView;
  SpreadsheetMouseListener mouseListener;
  SpreadsheetKeyListener keyListener;
  JTextField mockUserInputField;
  SpreadsheetModel mockModel;
  SpreadsheetModel realModel;
  Appendable log;

  @Before
  public void initialize() {
    log = new StringBuilder();
    mockUserInputField = new JTextField();
    mockView = new MockView(log);
    realModel = new SimpleSpreadsheet.Builder()
        .createCell(1, 1, "6.0")
        .createCell(1, 2, "true")
        .createCell(1, 3, "Hello sir")
        .createCell(2, 1, "=A1")
        .createCell(2, 2, "=(PRODUCT A1:A2)")
        .createWorksheet();
    mockModel = new MockModel(log, realModel);
    mockFeatures = new MockFeatures(log);
    mouseListener = new SpreadsheetMouseListener(mockFeatures);
    keyListener = new SpreadsheetKeyListener(mockFeatures, mockUserInputField);
  }

  @Test
  public void testKeyboardListener() {
    assertEquals("", log.toString());
    keyListener.keyPressed(
        new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_DOWN,
            KeyEvent.CHAR_UNDEFINED));
    assertEquals("The highlighted cell has been moved in the direction: DOWN\n", log.toString());
    //Doing key released on the same input should not change the log
    keyListener.keyReleased(
        new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_DOWN,
            KeyEvent.CHAR_UNDEFINED));
    assertEquals("The highlighted cell has been moved in the direction: DOWN\n", log.toString());
  }

  @Test
  public void cellSelected() {
  }

  @Test
  public void selectedCellEdited() {
  }

  @Test
  public void movedHighlightedCell() {
  }

  @Test
  public void deletedSelectedCell() {
  }
}