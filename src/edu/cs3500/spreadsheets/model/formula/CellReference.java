package edu.cs3500.spreadsheets.model.formula;

import edu.cs3500.spreadsheets.model.cell.Cell;
import java.util.List;

/**
 * Represents a reference to one or more spreadsheet cell's.
 */
public class CellReference implements Formula {
  List<Cell> referenceCells;

  public CellReference(List<Cell> referenceCells) {
    this.referenceCells = referenceCells;
  }
}
