package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.HashMap;
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
  public static final int DEFAULT_ROW_HEIGHT = 25;
  public static final int DEFAULT_COL_WIDTH = 75;
  private static final int MIN_ROW_COL_SIZE = 25;
  private Hashtable<Coord, Cell> cells;
  private List<Coord> errorCoords;
  private HashMap<Integer, Integer> rowHeights;
  private HashMap<Integer, Integer> colWidths;

  /**
   * Creates an instance of a simple spreadsheet. This constructor can only be called via the
   * builder.
   */
  private SimpleSpreadsheet() {
    this.cells = new Hashtable<>();
    this.errorCoords = new ArrayList<>();
    this.rowHeights = new HashMap<>();
    this.colWidths = new HashMap<>();
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
    this.setCellValue(coord, "");
  }

  @Override
  public void setCellValue(Coord coord, String contents) {
    Cell toAdd;
    if (contents == null || contents.equals("")) {
      toAdd = new BlankCell();
      this.cells.put(coord, toAdd);
    } else if (contents.charAt(0) == '=') {
      try {
        toAdd = new FormulaCell(
                (Parser.parse(contents.substring(1))).accept(new SexpVisitorFormula()),
                contents);
      } catch (IllegalArgumentException e) {
        toAdd = new ValueCell(contents,
                new ErrorValue(new IllegalArgumentException("Illegal formula.")));
      }
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
    return new ArrayList<>(this.errorCoords);
  }

  @Override
  public int getRowHeight(int row) throws IllegalArgumentException {
    if (row > 0) {
      return this.rowHeights.getOrDefault(row, DEFAULT_ROW_HEIGHT);
    } else {
      throw new IllegalArgumentException("Invalid row");
    }
  }

  @Override
  public int getColWidth(int col) throws IllegalArgumentException {
    if (col > 0) {
      return this.colWidths.getOrDefault(col, DEFAULT_COL_WIDTH);
    } else {
      throw new IllegalArgumentException("Invalid col");
    }
  }

  @Override
  public void setRowHeight(int row, int height) throws IllegalArgumentException {
    if (row > 0 && height >= MIN_ROW_COL_SIZE) {
      this.rowHeights.put(row, height);
    } else {
      throw new IllegalArgumentException("Invalid row or height");
    }
  }

  @Override
  public void setColWidth(int col, int width) throws IllegalArgumentException {
    if (col > 0 && width >= MIN_ROW_COL_SIZE) {
      this.colWidths.put(col, width);
    } else {
      throw new IllegalArgumentException("Invalid col or width");
    }
  }

  @Override
  public void copyPasteContents(Coord copyCoord, Coord pasteCoord) {
    int copyCol = copyCoord.col;
    int copyRow = copyCoord.row;
    int pasteCol = pasteCoord.col;
    int pasteRow = pasteCoord.row;
    int colChange = pasteCol - copyCol;
    int rowChange = pasteRow - copyRow;
    String pasteCellString = this.cells.getOrDefault(copyCoord, new BlankCell())
        .getPasteString(colChange, rowChange);

    this.setCellValue(new Coord(copyCol + colChange, copyRow + rowChange), pasteCellString);
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
    public WorksheetBuilder<SimpleSpreadsheet> setRowHeight(int row, int height) {
      try {
        this.spreadsheet.setRowHeight(row, height);
      } catch (IllegalArgumentException e) {
        //Ignore any bad inputs
      }
      return this;
    }

    @Override
    public WorksheetBuilder<SimpleSpreadsheet> setColWidth(int col, int width) {
      try {
        this.spreadsheet.setColWidth(col, width);
      } catch (IllegalArgumentException e) {
        //Ignore any bad inputs
      }
      return this;
    }

    @Override
    public SimpleSpreadsheet createWorksheet() {
      return this.spreadsheet;
    }
  }
}
