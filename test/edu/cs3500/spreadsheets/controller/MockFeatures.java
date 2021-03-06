package edu.cs3500.spreadsheets.controller;

import java.io.File;
import java.io.IOException;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.view.Features;

/**
 * Represents a mock features which logs all calls to its methods in an appendable.
 */
class MockFeatures implements Features {

  private final Appendable log;

  /**
   * Creates an instance of a mock features using the given appendable.
   *
   * @param log The appendable to log method calls.
   */
  MockFeatures(Appendable log) {
    this.log = log;
  }

  @Override
  public void cellSelected(Coord coord) {
    try {
      log.append("The coord ").append(coord.toString()).append(" has been selected").append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
  }

  @Override
  public void selectedCellEdited(String contents) {
    try {
      log.append("The selected cell has been edited. It's new contents is: ").append(contents)
          .append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
  }

  @Override
  public void movedHighlightedCell(Direction direction) {
    try {
      log.append("The highlighted cell has been moved in the direction: ")
          .append(direction.toString())
          .append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
  }

  @Override
  public void deletedSelectedCell() {
    try {
      log.append("The contents of the highlighted cell have been deleted").append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
  }

  @Override
  public void saveFile(File file) {
    try {
      log.append("Saved a file").append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
  }

  @Override
  public void loadFile(File file) {
    try {
      log.append("Loaded a file").append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
  }

  @Override
  public void changeRowSize(int heightChange) {
    try {
      log.append("The row height of the highlighted cell has been changed by: ")
          .append(Integer.toString(heightChange))
          .append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
  }

  @Override
  public void changeColSize(int widthChange) {
    try {
      log.append("The col width of the highlighted cell has been increased by: ")
          .append(Integer.toString(widthChange))
          .append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
  }

  @Override
  public void copyCell() {
    try {
      log.append("Copied the highlighted cell.")
              .append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
  }

  @Override
  public void pasteCell() {
    try {
      log.append("Pasted the copied cell.")
              .append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
  }
}
