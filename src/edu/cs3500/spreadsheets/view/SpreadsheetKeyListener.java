package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.Direction;
import edu.cs3500.spreadsheets.controller.Features;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;

/**
 * Represents a mouse listener for the spreadsheets. It's intention is to handle the key events for
 * delete, enter, and the arrow keys, and translate them to appropriate features to call on a
 * features interface.
 */
public class SpreadsheetKeyListener implements KeyListener {
  private final Features features;
  private final JTextField userInputField;

  /**
   * Creates an instance of a spreadsheet key listener and passes on its information (and possibly
   * also information from the given text field) to the given features interface.
   * @param features The features which cares about this key listener.
   * @param userInputField The user input text field.
   */
  public SpreadsheetKeyListener(Features features, JTextField userInputField) {
    this.features = features;
    this.userInputField = userInputField;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    //We only care about key pressed
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_ENTER:
        features.selectedCellEdited(userInputField.getText());
        break;
      case KeyEvent.VK_LEFT:
        features.movedHighlightedCell(Direction.LEFT);
        break;
      case KeyEvent.VK_RIGHT:
        features.movedHighlightedCell(Direction.RIGHT);
        break;
      case KeyEvent.VK_DOWN:
        features.movedHighlightedCell(Direction.DOWN);
        break;
      case KeyEvent.VK_UP:
        features.movedHighlightedCell(Direction.UP);
        break;
      case KeyEvent.VK_DELETE:
      case KeyEvent.VK_BACK_SPACE:
        features.deletedSelectedCell();
        userInputField.setText("");
        break;
      default:
        //Do nothing if no other keys were typed
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //We only care about key pressed
  }
}
