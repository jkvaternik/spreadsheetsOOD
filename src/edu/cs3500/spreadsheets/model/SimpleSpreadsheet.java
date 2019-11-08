package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.model.cell.BlankCell;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.FormulaCell;
import edu.cs3500.spreadsheets.model.cell.SexpVisitorFormula;
import edu.cs3500.spreadsheets.model.cell.ValueCell;
import edu.cs3500.spreadsheets.model.cell.formula.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.ErrorValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.IsErrorFunction;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;
import edu.cs3500.spreadsheets.sexp.Parser;

/**
 * A simple spreadsheet implementation which supports string, boolean, and double values. It's
 * supported operations include summing and taking products of a group of cells, determining if one
 * cell is less than another, and capitalizing the string value of a cell.
 */
public class SimpleSpreadsheet implements SpreadsheetModel {
  private Hashtable<Coord, Cell> cells;
  private List<Coord> errorCoords;

  /**
   * Creates an instance of a simple spreadsheet. This constructor can only be called via the
   * builder.
   */
  private SimpleSpreadsheet() {
    this.cells = new Hashtable<>();
    this.errorCoords = new ArrayList<>();
  }

  /**
   * Determines if the given string is a boolean.
   *
   * @param contents The string
   * @return If it is a boolean
   */
  private static boolean isBool(String contents) {
    return contents.equalsIgnoreCase("true") || contents.equalsIgnoreCase("false");
  }

  /**
   * Determines if the given string is a double.
   *
   * @param contents The string
   * @return If it is a double
   */
  private static boolean isDouble(String contents) {
    try {
      double d = Double.parseDouble(contents);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  @Override
  public void clearCell(Coord coord) {
    if (this.cells.containsKey(coord)) {
      this.cells.remove(coord);
    }
  }

  @Override
  public void setCellValue(Coord coord, String contents) {
    Cell toAdd;
    if (contents == null) {
      toAdd = new BlankCell();
      this.cells.put(coord, toAdd);
    } else if (contents.charAt(0) == '=') {
      toAdd = new FormulaCell(
              (Parser.parse(contents.substring(1))).accept(new SexpVisitorFormula()),
              contents);
      this.cells.put(coord, toAdd);
      if (toAdd.containsCyclicalReference(new HashSet<>(), this.cells, new HashSet<>())) {
        this.errorCoords.add(coord);
        toAdd = new FormulaCell(new ErrorValue(
            new IllegalStateException("This cell contains a cyclical reference.")), contents);
        toAdd.evaluate(cells, new Hashtable<>());
        this.cells.put(coord, toAdd);
      } else {
        toAdd.evaluate(this.cells, new Hashtable<>());
        if (toAdd.getValue().accept(new IsErrorFunction())) {
          this.errorCoords.add(coord);
        }
        this.cells.put(coord, toAdd);
      }
    } else if (isDouble(contents)) {
      toAdd = new ValueCell(contents, new DoubleValue(Double.parseDouble(contents)));
      this.cells.put(coord, toAdd);
    } else if (isBool(contents)) {
      toAdd = new ValueCell(contents, new BooleanValue(Boolean.parseBoolean(contents)));
      this.cells.put(coord, toAdd);
    } else {
      toAdd = new ValueCell(contents, new StringValue(contents));
      this.cells.put(coord, toAdd);
    }

    // After altering the value of a cell, re-evaluate all cells which depend on it
    this.reEvaluateCells(coord);
  }

  /**
   * Re-evaluates every cell that depends on the Cell at the given Coord, then recursively
   * re-evaluates all of those cell's dependants.
   * INVARIANT: This method will not run until we check for cycles, so it is guaranteed that this
   * recursion will terminate.
   * Side Note: A cell may be re-evaluated multiple times (i.e. if C1 references A1 and B1, and B1
   * references A1), but at the termination of the recursion, it will have the correct value.
   *
   * @param coord The coordinate of the cell that changed
   */
  private void reEvaluateCells(Coord coord) {
    if (this.cells.containsKey(coord)) {
      for (Cell cell : this.cells.values()) {
        if (cell.referencesCell(coord)) {
          cell.evaluate(cells, new Hashtable<>());
        }
      }
    }
  }

  @Override
  public int getNumRows() {
    int maxRow = 0;
    for (Coord c : this.cells.keySet()) {
      if (c.row > maxRow) {
        maxRow = c.row;
      }
    }
    return maxRow;
  }

  @Override
  public int getNumColumns() {
    int maxCol = 0;
    for (Coord c : this.cells.keySet()) {
      if (c.col > maxCol) {
        maxCol = c.col;
      }
    }
    return maxCol;
  }

  @Override
  public String getValue(Coord coord) {
    if (this.cells.containsKey(coord)) {
      return this.cells.get(coord).getValue().toString();
    } else {
      return "";
    }
  }

  @Override
  public String getRawContents(Coord coord) {
    if (this.cells.containsKey(coord)) {
      return this.cells.get(coord).getRawContents();
    } else {
      // If the coordinates are not in our hashtable, the cell has not yet been edited
      return "";
    }
  }

  @Override
  public List<Coord> getErrorCoords() {
    return this.errorCoords;
  }

  /**
   * Represents instance of a WorksheetBuilder which helps build a spreadsheet.
   */
  public static class Builder implements WorksheetBuilder<SimpleSpreadsheet> {
    private SimpleSpreadsheet spreadsheet;

    /** Constructor for the WorksheetBuilder. */
    public Builder() {
      this.spreadsheet = new SimpleSpreadsheet();
    }

    @Override
    public WorksheetBuilder<SimpleSpreadsheet> createCell(int col, int row, String contents) {
      Coord c = new Coord(col, row);
      this.spreadsheet.setCellValue(c, contents);
      return this;
    }

    @Override
    public SimpleSpreadsheet createWorksheet() {
      return this.spreadsheet;
    }
  }
}
