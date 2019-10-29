package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.model.cell.BlankCell;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.FormulaCell;
import edu.cs3500.spreadsheets.model.cell.SexpVisitorFormula;
import edu.cs3500.spreadsheets.model.cell.ValueCell;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;
import java.util.Hashtable;

/**
 * A simple spreadsheet implementation which supports string, boolean, and double values. It's
 * supported operations include summing and taking products of a group of cells, determining if one
 * cell is less than another, and capitalizing the string value of a cell.
 */
public class SimpleSpreadsheet implements SpreadsheetModel {
  private Hashtable<Coord, Cell> cells;

  public static class Builder implements WorksheetBuilder<SimpleSpreadsheet> {
    private SimpleSpreadsheet spreadsheet;

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

  /**
   * Creates an instance of a simple spreadsheet. This constructor can only be called via the
   * builder.
   */
  private SimpleSpreadsheet() {
    this.cells = new Hashtable<>();
  }

  @Override
  public void clearCell(Coord coord) {
    if (this.cells.containsKey(coord)) {
      // TODO: Should we just remove this cell from the hashtable, since this is essentially
      //       equivalent behavior (cells not in the table are treated as blankCells)
      this.cells.put(coord, new BlankCell());
    }
  }

  @Override
  public void setCellValue(Coord coord, String contents) {
    Cell toAdd;
    if (contents == null) {
      toAdd = new BlankCell();
      this.cells.put(coord, toAdd);
    } else if (contents.charAt(0) == '=') {
      toAdd = new FormulaCell((new Parser().parse(contents)).accept(new SexpVisitorFormula()),
          contents, null);
      toAdd.evaluate(this.cells);
      this.cells.put(coord, toAdd);
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
  }

  /**
   * Determines if the given string is a boolean.
   * @param contents The string
   * @return If it is a boolean
   */
  private static boolean isBool(String contents) {
    return contents.equalsIgnoreCase("true") || contents.equalsIgnoreCase("false");
  }

  /**
   * Determines if the given string is a double.
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
  //TODO: I think instead of using get value, we should have evaluate return a value so we don't
  //      have to keep re-evaluating every time we edit a cell (and instead only do it every time
  //      we need to get its value).
  //      NOTE: This should work, because in evaluate, if there are any cell refs., we re-evaluate
  //      the new cells, so I don't think we would run into any issues...
  public Value getValue(Coord coord) {
    if (this.cells.containsKey(coord)) {
      return this.cells.get(coord).getValue();
    } else {
      //TODO: What should the default value be???
      return new StringValue("");
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
}
