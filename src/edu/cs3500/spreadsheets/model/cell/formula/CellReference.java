package edu.cs3500.spreadsheets.model.cell.formula;

import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.value.Value;

import java.util.List;

/**
 * Represents a reference to one or more spreadsheet cell's.
 * TODO: Consider having two subclasses, one for single references and one for multi references.
 */
public class CellReference implements Formula {
  List<Cell> referenceCells;

  public CellReference(List<Cell> referenceCells) {
    this.referenceCells = referenceCells;
  }

  @Override
  public Value evaluate() {
    return null;
  }
}
