package edu.cs3500.spreadsheets.model.cell;

import java.util.Hashtable;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;

/**
 * Represents one cell in a spreadsheet.
 * TODO: Consider making this an abstract class, since the only thing that changes between
 *       implementations is the evaluate() method. Both also have value and cellString fields.
 */
public interface Cell {

  /**
   * Evaluates this cell.
   * TODO: This probably needs some sort of input right? It's purpose is to re-evaluate the cell's
   *       value in the event that one of it's references changes.
   */
  Value evaluate(Hashtable<Coord, Cell> spreadsheet);


  /**
   * Gets the string which the user typed into the cell in order to give it it's value or function.
   * @return The string
   */
  String getRawContents();
}
