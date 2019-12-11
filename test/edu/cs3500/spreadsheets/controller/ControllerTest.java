package edu.cs3500.spreadsheets.controller;


import org.junit.Before;
import org.junit.Test;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SimpleSpreadsheet;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.Features;
import edu.cs3500.spreadsheets.view.SpreadsheetKeyListener;
import edu.cs3500.spreadsheets.view.SpreadsheetMouseListener;
import edu.cs3500.spreadsheets.view.View;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * Tests for the Spreadsheet controller. Utilizes mock models, views, and features in order to check
 * that the "wiring" of the 3 MVC components has been done successfully.
 */
public class ControllerTest {

  private Controller controller;
  private Features mockFeatures;
  private View mockView;
  private SpreadsheetMouseListener mouseListener;
  private SpreadsheetKeyListener keyListener;
  private JTextField mockUserInputField;
  private SpreadsheetModel mockModel;
  private SpreadsheetModel realModel;
  private Appendable log;

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
    mockView = new MockView(log);
    mockFeatures = new MockFeatures(log);
    mouseListener = new SpreadsheetMouseListener(mockFeatures, realModel);
    keyListener = new SpreadsheetKeyListener(mockFeatures, mockUserInputField);
  }

  @Test
  public void testKeyboardListener() {
    assertEquals("", log.toString());

    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_W,
                    KeyEvent.CHAR_UNDEFINED));
    //Pressing a key that is not "of interest" by our listener should not change the log
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

    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_UP,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("The highlighted cell has been moved in the direction: DOWN\n"
            + "The highlighted cell has been moved in the direction: UP\n", log.toString());
    //Doing key released on the same input should not change the log
    keyListener.keyReleased(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_UP,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("The highlighted cell has been moved in the direction: DOWN\n"
            + "The highlighted cell has been moved in the direction: UP\n", log.toString());

    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_LEFT,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("The highlighted cell has been moved in the direction: DOWN\n"
            + "The highlighted cell has been moved in the direction: UP\n"
            + "The highlighted cell has been moved in the direction: LEFT\n", log.toString());
    //Doing key released on the same input should not change the log
    keyListener.keyReleased(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_LEFT,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("The highlighted cell has been moved in the direction: DOWN\n"
            + "The highlighted cell has been moved in the direction: UP\n"
            + "The highlighted cell has been moved in the direction: LEFT\n", log.toString());

    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_RIGHT,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("The highlighted cell has been moved in the direction: DOWN\n"
            + "The highlighted cell has been moved in the direction: UP\n"
            + "The highlighted cell has been moved in the direction: LEFT\n"
            + "The highlighted cell has been moved in the direction: RIGHT\n", log.toString());
    //Doing key released on the same input should not change the log
    keyListener.keyReleased(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_RIGHT,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("The highlighted cell has been moved in the direction: DOWN\n"
            + "The highlighted cell has been moved in the direction: UP\n"
            + "The highlighted cell has been moved in the direction: LEFT\n"
            + "The highlighted cell has been moved in the direction: RIGHT\n", log.toString());

    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_BACK_SPACE,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("The highlighted cell has been moved in the direction: DOWN\n"
            + "The highlighted cell has been moved in the direction: UP\n"
            + "The highlighted cell has been moved in the direction: LEFT\n"
            + "The highlighted cell has been moved in the direction: RIGHT\n"
            + "The contents of the highlighted cell have been deleted\n", log.toString());
    //Doing key released on the same input should not change the log
    keyListener.keyReleased(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_BACK_SPACE,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("The highlighted cell has been moved in the direction: DOWN\n"
            + "The highlighted cell has been moved in the direction: UP\n"
            + "The highlighted cell has been moved in the direction: LEFT\n"
            + "The highlighted cell has been moved in the direction: RIGHT\n"
            + "The contents of the highlighted cell have been deleted\n", log.toString());

    this.mockUserInputField.setText("3.0");

    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_ENTER,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("The highlighted cell has been moved in the direction: DOWN\n"
            + "The highlighted cell has been moved in the direction: UP\n"
            + "The highlighted cell has been moved in the direction: LEFT\n"
            + "The highlighted cell has been moved in the direction: RIGHT\n"
            + "The contents of the highlighted cell have been deleted\n"
            + "The selected cell has been edited. It's new contents is: 3.0\n", log.toString());
    //Doing key released on the same input should not change the log
    keyListener.keyReleased(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_ENTER,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("The highlighted cell has been moved in the direction: DOWN\n"
            + "The highlighted cell has been moved in the direction: UP\n"
            + "The highlighted cell has been moved in the direction: LEFT\n"
            + "The highlighted cell has been moved in the direction: RIGHT\n"
            + "The contents of the highlighted cell have been deleted\n"
            + "The selected cell has been edited. It's new contents is: 3.0\n", log.toString());

    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_DELETE,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("The highlighted cell has been moved in the direction: DOWN\n"
            + "The highlighted cell has been moved in the direction: UP\n"
            + "The highlighted cell has been moved in the direction: LEFT\n"
            + "The highlighted cell has been moved in the direction: RIGHT\n"
            + "The contents of the highlighted cell have been deleted\n"
            + "The selected cell has been edited. It's new contents is: 3.0\n"
            + "The contents of the highlighted cell have been deleted\n", log.toString());
    //Doing key released on the same input should not change the log
    keyListener.keyReleased(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_DELETE,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("The highlighted cell has been moved in the direction: DOWN\n"
            + "The highlighted cell has been moved in the direction: UP\n"
            + "The highlighted cell has been moved in the direction: LEFT\n"
            + "The highlighted cell has been moved in the direction: RIGHT\n"
            + "The contents of the highlighted cell have been deleted\n"
            + "The selected cell has been edited. It's new contents is: 3.0\n"
            + "The contents of the highlighted cell have been deleted\n", log.toString());

    //Now make sure copying and pasting works as intended
    keyListener.keyPressed(
        new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_C,
            KeyEvent.CHAR_UNDEFINED));
    assertEquals("The highlighted cell has been moved in the direction: DOWN\n"
        + "The highlighted cell has been moved in the direction: UP\n"
        + "The highlighted cell has been moved in the direction: LEFT\n"
        + "The highlighted cell has been moved in the direction: RIGHT\n"
        + "The contents of the highlighted cell have been deleted\n"
        + "The selected cell has been edited. It's new contents is: 3.0\n"
        + "The contents of the highlighted cell have been deleted\n"
        + "Copied the highlighted cell.\n", log.toString());

    keyListener.keyPressed(
        new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_P,
            KeyEvent.CHAR_UNDEFINED));
    assertEquals("The highlighted cell has been moved in the direction: DOWN\n"
        + "The highlighted cell has been moved in the direction: UP\n"
        + "The highlighted cell has been moved in the direction: LEFT\n"
        + "The highlighted cell has been moved in the direction: RIGHT\n"
        + "The contents of the highlighted cell have been deleted\n"
        + "The selected cell has been edited. It's new contents is: 3.0\n"
        + "The contents of the highlighted cell have been deleted\n"
        + "Copied the highlighted cell.\n"
        + "Pasted the copied cell.\n", log.toString());

  }

  @Test(expected = IllegalStateException.class)
  public void testKeyboardListener_InvalidAppendable() {
    log = new Appendable() {
      @Override
      public Appendable append(CharSequence csq) throws IOException {
        throw new IOException();
      }

      @Override
      public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new IOException();
      }

      @Override
      public Appendable append(char c) throws IOException {
        throw new IOException();
      }
    };
    mockFeatures = new MockFeatures(log);
    keyListener = new SpreadsheetKeyListener(mockFeatures, mockUserInputField);

    // When a key is pressed, the IOException should be converted to an IllegalStateException
    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_DELETE,
                    KeyEvent.CHAR_UNDEFINED));
  }

  @Test
  public void testMouseListener() {
    assertEquals("", log.toString());
    mouseListener.mouseClicked(new MouseEvent(mockUserInputField, 1, 1, MouseEvent.MOUSE_CLICKED,
            20, 25, 5, true, 5));
    assertEquals("The coord A1 has been selected\n", log.toString());

    mouseListener.mouseClicked(new MouseEvent(mockUserInputField, 1, 1, MouseEvent.MOUSE_CLICKED,
            156, 84, 5, true, 5));
    assertEquals("The coord A1 has been selected\n"
            + "The coord C4 has been selected\n", log.toString());

    mouseListener.mouseClicked(new MouseEvent(mockUserInputField, 1, 1, MouseEvent.MOUSE_CLICKED,
            10000, 2900, 5, true, 5));
    assertEquals("The coord A1 has been selected\n"
            + "The coord C4 has been selected\n"
            + "The coord ED116 has been selected\n", log.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testMouseListener_InvalidAppendable() {
    log = new Appendable() {
      @Override
      public Appendable append(CharSequence csq) throws IOException {
        throw new IOException();
      }

      @Override
      public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new IOException();
      }

      @Override
      public Appendable append(char c) throws IOException {
        throw new IOException();
      }
    };
    mockFeatures = new MockFeatures(log);
    mouseListener = new SpreadsheetMouseListener(mockFeatures, realModel);

    // When the mouse is clicked, the IOException should be converted to an IllegalStateException
    mouseListener.mouseClicked(new MouseEvent(mockUserInputField, 1, 1, MouseEvent.MOUSE_CLICKED,
            156, 84, 5, true, 5));
  }

  @Test
  public void cellSelected_ThroughMethodCall() {
    assertEquals("", log.toString());

    controller = new Controller(mockModel, mockView);
    assertEquals("Features were added to this view.\n", log.toString());

    controller.cellSelected(new Coord(1, 2));
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A2\n", log.toString());

    controller.cellSelected(new Coord(29, 32));
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A2\n"
            + "The highlighted cell is now: AC32\n", log.toString());

    controller.cellSelected(null);
    //The null coordinate should just be ignored
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A2\n"
            + "The highlighted cell is now: AC32\n", log.toString());
  }

  @Test
  public void cellSelected_ThroughListeners() {
    assertEquals("", log.toString());

    controller = new Controller(mockModel, mockView);
    mouseListener = new SpreadsheetMouseListener(controller, realModel);
    keyListener = new SpreadsheetKeyListener(controller, mockUserInputField);

    assertEquals("Features were added to this view.\n", log.toString());

    mouseListener.mouseClicked(new MouseEvent(mockUserInputField, 1, 1, MouseEvent.MOUSE_CLICKED,
            20, 25, 5, true, 5));
    //Show that the controller is properly wired to the view when a cell is selected
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A1\n", log.toString());

    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_RIGHT,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A1\n"
            + "The highlighted cell is now: B1\n"
            + "The view has been refreshed.\n", log.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void cellSelected_IllegalCoord() {
    controller = new Controller(mockModel, mockView);
    controller.cellSelected(new Coord(-1, 0));
  }

  @Test
  public void selectedCellEdited_ThroughMethodCall() {
    controller = new Controller(realModel, mockView);
    assertEquals("6.000000", realModel.getValue(new Coord(1, 1)));
    assertEquals("6.0", realModel.getRawContents(new Coord(1, 1)));

    controller.selectedCellEdited("5.0");
    //Nothing should change, since no coord has been selected
    assertEquals("6.000000", realModel.getValue(new Coord(1, 1)));
    assertEquals("6.0", realModel.getRawContents(new Coord(1, 1)));

    controller.cellSelected(new Coord(1, 1));
    controller.selectedCellEdited("5.0");
    assertEquals("5.000000", realModel.getValue(new Coord(1, 1)));
    assertEquals("5.0", realModel.getRawContents(new Coord(1, 1)));

    assertEquals("true", realModel.getValue(new Coord(1, 2)));
    assertEquals("true", realModel.getRawContents(new Coord(1, 2)));

    controller.movedHighlightedCell(Direction.DOWN);
    controller.selectedCellEdited("=(< 2.0 A1)");
    assertEquals("true", realModel.getValue(new Coord(1, 2)));
    assertEquals("=(< 2.0 A1)", realModel.getRawContents(new Coord(1, 2)));
  }

  @Test
  public void selectedCellEdited_ThroughListeners() {
    assertEquals("", log.toString());

    controller = new Controller(mockModel, mockView);
    mouseListener = new SpreadsheetMouseListener(controller, realModel);
    keyListener = new SpreadsheetKeyListener(controller, mockUserInputField);

    assertEquals("Features were added to this view.\n", log.toString());

    mouseListener.mouseClicked(new MouseEvent(mockUserInputField, 1, 1, MouseEvent.MOUSE_CLICKED,
            20, 20, 5, true, 5));
    //Show that the controller is properly wired to the view when a cell is selected
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A1\n", log.toString());
    assertEquals("6.0", realModel.getRawContents(new Coord(1, 1)));

    mockUserInputField.setText("5.0");

    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_ENTER,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A1\n"
            + "This cell at: A1 now has the raw contents: 5.0\n"
            + "The view has been refreshed.\n", log.toString());
    assertEquals("5.0", realModel.getRawContents(new Coord(1, 1)));
  }

  @Test
  public void movedHighlightedCell_ThroughMethodCall() {
    assertEquals("", log.toString());

    controller = new Controller(mockModel, mockView);
    assertEquals("Features were added to this view.\n", log.toString());

    controller.cellSelected(new Coord(1, 2));
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A2\n", log.toString());

    //Moving the highlighted cell to the left should do nothing, since we are at the left edge
    controller.movedHighlightedCell(Direction.LEFT);
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A2\n"
            + "The view has been refreshed.\n", log.toString());

    controller.movedHighlightedCell(Direction.UP);
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A2\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: A1\n"
            + "The view has been refreshed.\n", log.toString());

    //Moving the direction up again should do nothing, since we are at the top edge
    controller.movedHighlightedCell(Direction.UP);
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A2\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: A1\n"
            + "The view has been refreshed.\n"
            + "The view has been refreshed.\n", log.toString());

    controller.movedHighlightedCell(Direction.RIGHT);
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A2\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: A1\n"
            + "The view has been refreshed.\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: B1\n"
            + "The view has been refreshed.\n", log.toString());

    controller.movedHighlightedCell(Direction.DOWN);
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A2\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: A1\n"
            + "The view has been refreshed.\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: B1\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: B2\n"
            + "The view has been refreshed.\n", log.toString());

    controller.movedHighlightedCell(Direction.LEFT);
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A2\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: A1\n"
            + "The view has been refreshed.\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: B1\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: B2\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: A2\n"
            + "The view has been refreshed.\n", log.toString());
  }

  @Test
  public void movedHighlightedCell_ThroughListeners() {
    assertEquals("", log.toString());

    controller = new Controller(mockModel, mockView);
    mouseListener = new SpreadsheetMouseListener(controller, realModel);
    keyListener = new SpreadsheetKeyListener(controller, mockUserInputField);
    assertEquals("Features were added to this view.\n", log.toString());

    mouseListener.mouseClicked(new MouseEvent(mockUserInputField, 1, 1, MouseEvent.MOUSE_CLICKED,
            20, 25, 5, true, 5));
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A1\n", log.toString());

    //Moving the highlighted cell to the left should do nothing, since we are at the left edge
    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_LEFT,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A1\n"
            + "The view has been refreshed.\n", log.toString());

    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_UP,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A1\n"
            + "The view has been refreshed.\n"
            + "The view has been refreshed.\n", log.toString());

    //Moving the direction up again should do nothing, since we are at the top edge
    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_UP,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A1\n"
            + "The view has been refreshed.\n"
            + "The view has been refreshed.\n"
            + "The view has been refreshed.\n", log.toString());

    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_RIGHT,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A1\n"
            + "The view has been refreshed.\n"
            + "The view has been refreshed.\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: B1\n"
            + "The view has been refreshed.\n", log.toString());

    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_DOWN,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A1\n"
            + "The view has been refreshed.\n"
            + "The view has been refreshed.\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: B1\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: B2\n"
            + "The view has been refreshed.\n", log.toString());

    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_LEFT,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A1\n"
            + "The view has been refreshed.\n"
            + "The view has been refreshed.\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: B1\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: B2\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: A2\n"
            + "The view has been refreshed.\n", log.toString());
  }

  @Test
  public void deletedSelectedCell_ThroughMethodCalls() {
    controller = new Controller(realModel, mockView);
    assertEquals("6.000000", realModel.getValue(new Coord(1, 1)));
    assertEquals("6.0", realModel.getRawContents(new Coord(1, 1)));

    controller.deletedSelectedCell();
    //Nothing should change, since no coord has been selected
    assertEquals("6.000000", realModel.getValue(new Coord(1, 1)));
    assertEquals("6.0", realModel.getRawContents(new Coord(1, 1)));

    controller.cellSelected(new Coord(1, 1));
    controller.deletedSelectedCell();
    assertEquals("", realModel.getValue(new Coord(1, 1)));
    assertNull(realModel.getRawContents(new Coord(1, 1)));

    assertEquals("true", realModel.getValue(new Coord(1, 2)));
    assertEquals("true", realModel.getRawContents(new Coord(1, 2)));

    controller.movedHighlightedCell(Direction.DOWN);
    controller.deletedSelectedCell();
    assertEquals("", realModel.getValue(new Coord(1, 2)));
    assertNull(realModel.getRawContents(new Coord(1, 2)));

    //Deleting the same cell again should not change anything
    controller.deletedSelectedCell();
    assertEquals("", realModel.getValue(new Coord(1, 2)));
    assertNull(realModel.getRawContents(new Coord(1, 2)));
  }

  @Test
  public void deletedHighlightedCell_ThroughListeners() {
    assertEquals("", log.toString());

    controller = new Controller(mockModel, mockView);
    mouseListener = new SpreadsheetMouseListener(controller, realModel);
    keyListener = new SpreadsheetKeyListener(controller, mockUserInputField);
    assertEquals("Features were added to this view.\n", log.toString());
    assertEquals("6.000000", realModel.getValue(new Coord(1, 1)));
    assertEquals("6.0", realModel.getRawContents(new Coord(1, 1)));

    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_DELETE,
                    KeyEvent.CHAR_UNDEFINED));
    //Nothing should change about the cell, since no coord has been selected
    assertEquals("6.000000", realModel.getValue(new Coord(1, 1)));
    assertEquals("6.0", realModel.getRawContents(new Coord(1, 1)));
    assertEquals("Features were added to this view.\n", log.toString());

    mouseListener.mouseClicked(new MouseEvent(mockUserInputField, 1, 1, MouseEvent.MOUSE_CLICKED,
            20, 20, 5, true, 5));
    //Show that the delete key works for deleting
    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_DELETE,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("", realModel.getValue(new Coord(1, 1)));
    assertNull(realModel.getRawContents(new Coord(1, 1)));
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A1\n"
            + "This cell was cleared: A1\n"
            + "The view has been refreshed.\n", log.toString());

    assertEquals("true", realModel.getValue(new Coord(1, 2)));
    assertEquals("true", realModel.getRawContents(new Coord(1, 2)));

    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_DOWN,
                    KeyEvent.CHAR_UNDEFINED));
    //Show that the backspace key works for deleting
    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_BACK_SPACE,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("", realModel.getValue(new Coord(1, 2)));
    assertNull(realModel.getRawContents(new Coord(1, 2)));
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A1\n"
            + "This cell was cleared: A1\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: A2\n"
            + "The view has been refreshed.\n"
            + "This cell was cleared: A2\n"
            + "The view has been refreshed.\n", log.toString());

    //Deleting the same cell again should not change anything
    keyListener.keyPressed(
            new KeyEvent(mockUserInputField, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_BACK_SPACE,
                    KeyEvent.CHAR_UNDEFINED));
    assertEquals("", realModel.getValue(new Coord(1, 2)));
    assertNull(realModel.getRawContents(new Coord(1, 2)));
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A1\n"
            + "This cell was cleared: A1\n"
            + "The view has been refreshed.\n"
            + "The highlighted cell is now: A2\n"
            + "The view has been refreshed.\n"
            + "This cell was cleared: A2\n"
            + "The view has been refreshed.\n"
            + "This cell was cleared: A2\n"
            + "The view has been refreshed.\n", log.toString());
  }

  @Test
  public void saveFile() {
    //To test this, we will take a text file and make a spreadsheet out of it. We will then save
    //the file and make a spreadsheet from the saved file. The two spreadsheets should be equivalent
    controller = new Controller(realModel, mockView);
    try {
      File sampleOne = new File(
              "C:\\Users\\jlkaz\\IdeaProjects\\Spreadsheet\\resources\\textFiles\\fileSampleOne.txt");
      WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builder = new SimpleSpreadsheet.Builder();
      SimpleSpreadsheet originalModel = WorksheetReader.read(builder, new FileReader(sampleOne));

      controller.saveFile(sampleOne);

      File sampleOneCopy = new File(
              "C:\\Users\\jlkaz\\IdeaProjects\\Spreadsheet\\resources\\textFiles\\fileSampleOne.txt");
      WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builder2 =
              new SimpleSpreadsheet.Builder();
      SimpleSpreadsheet modelCopy = WorksheetReader.read(builder2, new FileReader(sampleOneCopy));

      assertEquals(originalModel.getNumColumns(), modelCopy.getNumColumns());
      assertEquals(originalModel.getNumRows(), modelCopy.getNumRows());
      for (int col = 1; col <= originalModel.getNumColumns(); col++) {
        for (int row = 1; row <= originalModel.getNumRows(); row++) {
          Coord coord = new Coord(col, row);
          assertEquals(originalModel.getValue(coord), modelCopy.getValue(coord));
        }
      }

    } catch (FileNotFoundException e) {
      fail("The file was not found");
    }
  }

  @Test
  public void loadFile() {
    assertEquals("", log.toString());
    controller = new Controller(mockModel, mockView);
    assertEquals("Features were added to this view.\n", log.toString());

    controller.cellSelected(new Coord(1, 1));
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A1\n", log.toString());

    controller.deletedSelectedCell();
    assertEquals("Features were added to this view.\n"
            + "The highlighted cell is now: A1\n"
            + "This cell was cleared: A1\n"
            + "The view has been refreshed.\n", log.toString());

    File sampleOne = new File(
            "C:\\Users\\jlkaz\\IdeaProjects\\Spreadsheet\\resources\\textFiles\\fileSampleOne.txt");

    // This opens then immediately closes a visual view (we aren't entirely sure why the view closes
    // immediately), but it is the best way we could think of to test loadFile since this method
    // is so tightly coupled with Swing (we couldn't find a way to abstract it out further).
    controller.loadFile(sampleOne);
  }

  @Test
  public void testCopyPaste() {
    controller = new Controller(realModel, mockView);

    assertEquals("6.0", realModel.getRawContents(new Coord(1, 1)));
    assertEquals("=A1", realModel.getRawContents(new Coord(2, 1)));

    controller.cellSelected(new Coord(1, 1));
    controller.copyCell();
    controller.cellSelected(new Coord(2, 1));
    controller.pasteCell();

    assertEquals("6.0", realModel.getRawContents(new Coord(2, 1)));

    realModel.setCellValue(new Coord(2, 1), "=A$1");

    controller.cellSelected(new Coord(2, 1));
    controller.copyCell();
    controller.cellSelected(new Coord(3, 1));
    controller.pasteCell();
    assertEquals("=B$1", realModel.getRawContents(new Coord(3, 1)));

    controller.cellSelected(new Coord(2, 2));
    controller.pasteCell();
    assertEquals("=A$1", realModel.getRawContents(new Coord(2, 2)));

    realModel.setCellValue(new Coord(1, 2), "=$A2");

    controller.cellSelected(new Coord(1, 2));
    controller.copyCell();
    controller.cellSelected(new Coord(1, 3));
    controller.pasteCell();
    assertEquals("=$A3", realModel.getRawContents(new Coord(1, 3)));

    controller.cellSelected(new Coord(2, 2));
    controller.pasteCell();
    assertEquals("=$A2", realModel.getRawContents(new Coord(2, 2)));


    //Copying the reference of A1 to the left or up should leave the reference the same, even
    //though it is not absolute
    realModel.setCellValue(new Coord(2, 2), "=A1");
    controller.cellSelected(new Coord(2, 2));
    controller.copyCell();
    controller.movedHighlightedCell(Direction.UP);
    controller.pasteCell();
    assertEquals("=A1", realModel.getRawContents(new Coord(2, 1)));

    controller.cellSelected(new Coord(1, 2));
    controller.pasteCell();
    assertEquals("=A1", realModel.getRawContents(new Coord(1, 2)));

    //Copying the reference of A1 and pasting it in a different row and column should leave the
    //reference the same
    controller.cellSelected(new Coord(3, 3));
    controller.pasteCell();
    assertEquals("=A1", realModel.getRawContents(new Coord(3, 3)));


  }

  @Test
  public void testChangeRowSize() {
    controller = new Controller(realModel, mockView);

    for (int i = 0; i < Math.max(realModel.getNumRows(), realModel.maxRowChanged()); i++) {
      assertEquals(25, realModel.getRowHeight(i + 1));
    }

    controller.changeRowSize(20);

    //Shouldn't change because a cell wasn't selected
    for (int i = 0; i < Math.max(realModel.getNumRows(), realModel.maxRowChanged()); i++) {
      assertEquals(25, realModel.getRowHeight(i + 1));
    }

    assertEquals(25, realModel.getRowHeight(1));

    controller.cellSelected(new Coord(1, 1));
    controller.changeRowSize(20);
    assertEquals(45, realModel.getRowHeight(1));

    controller.changeRowSize(-20);

    assertEquals(25, realModel.getRowHeight(1));

    controller.changeRowSize(-20);

    //Shouldn't change the size because the height would be less than the minimum row
    assertEquals(25, realModel.getRowHeight(1));
  }

  @Test
  public void testChangeColSize() {
    controller = new Controller(realModel, mockView);

    for (int i = 0; i < Math.max(realModel.getNumColumns(), realModel.maxColChanged()); i++) {
      assertEquals(75, realModel.getColWidth(i + 1));
    }

    controller.changeColSize(20);

    //Shouldn't change because a cell wasn't selected
    for (int i = 0; i < Math.max(realModel.getNumColumns(), realModel.maxColChanged()); i++) {
      assertEquals(75, realModel.getColWidth(i + 1));
    }

    controller.cellSelected(new Coord(4, 3));
    controller.changeColSize(20);
    assertEquals(95, realModel.getColWidth(4));

    controller.changeColSize(-20);

    assertEquals(75, realModel.getColWidth(1));

    controller.changeColSize(-70);

    //Shouldn't change the size because the height would be less than the minimum row
    assertEquals(75, realModel.getColWidth(4));
  }

}