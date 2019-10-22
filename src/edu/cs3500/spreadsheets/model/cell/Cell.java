package edu.cs3500.spreadsheets.model.cell;

import edu.cs3500.spreadsheets.model.value.Value;

/**
 * Represents one cell in a spreadsheet.
 */
public interface Cell {

  /**
   * Evaluates this cell.
   * TODO: This probably needs some sort of input right? It's purpose is to re-evaluate the cell's
   *       value in the event that one of it's references changes.
   */
  void evaluate();


  /**
   * Gets the string which the user typed into the cell in order to give it it's value or function.
   * @return The string
   */
  String getUserString();

  /**
   * Gets the cell's value, which is either it's value or the value by applying it's function to
   * it's reference cells.
   * @return The value
   */
  Value getValue();
}
