package edu.cs3500.spreadsheets.model.cell.value;

import java.util.Hashtable;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;

/**
 * Represents one of the allowable value types of a cell in the spreadsheet.
 */
public abstract class Value<T> implements Formula {
  protected T value;

  public Value(T value) {
    this.value = value;
  }

  @Override
  public Value<T> evaluate(Hashtable<Coord, Cell> cells) {
    return this;
  }

  public T getValue() {
    return this.value;
  }
}
