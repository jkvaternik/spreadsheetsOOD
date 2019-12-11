package edu.cs3500.spreadsheets.controller;

import java.io.IOException;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.view.Features;
import edu.cs3500.spreadsheets.view.View;

/**
 * Represents a mock view which logs all of its method calls to an appendable.
 */
class MockView implements View {
  private final Appendable log;

  /**
   * Creates an instance of a mock view with the given appendable.
   *
   * @param log The appendable to log method calls.
   */
  MockView(Appendable log) {
    this.log = log;
  }

  @Override
  public void makeVisible() {
    try {
      log.append("The view is now visible.");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
  }

  @Override
  public void refresh() {
    try {
      log.append("The view has been refreshed.").append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
  }

  @Override
  public void addFeatures(Features features) {
    try {
      log.append("Features were added to this view.").append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
  }

  @Override
  public void highlightCell(Coord cellCoord) {
    try {
      log.append("The highlighted cell is now: ").append(cellCoord.toString()).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
  }

  @Override
  public void highlightCopyCell(Coord copyCoord) {
    try {
      log.append("The copied cell is now: ").append(copyCoord.toString()).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid appendable");
    }
  }
}
