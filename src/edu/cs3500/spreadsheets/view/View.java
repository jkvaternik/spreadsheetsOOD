package edu.cs3500.spreadsheets.view;

/**
 * Represents a view for a spreadsheet.
 */
public interface View {
  /**
   * Make the view visible (for the first time).
   */
  void makeVisible();

  /**
   * Signal the view to draw itself again.
   */
  void refresh();
}
