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
    //TODO: If we build the model first, then createCell will just use the model.addCell method.
    //      This way, we only need to write the SExp -> Formula stuff once and we can make it a
    //      private method in the model (talked to Lerner about this so I know we should do it).
    private Hashtable<Coord, Cell> cells;

    @Override
    public WorksheetBuilder<SimpleSpreadsheet> createCell(int col, int row, String contents) {
      Cell toAdd;
      if (contents == null) {
        toAdd = new BlankCell();
        cells.put(new Coord(col, row), toAdd);
        return this;
      } else if (contents.charAt(0) == '=') {
        toAdd = new FormulaCell((new Parser().parse(contents)).accept(new SexpVisitorFormula()),
            contents, null);
        toAdd.evaluate(cells);
        cells.put(new Coord(col, row), toAdd);
      } else if (isDouble(contents)) {
        toAdd = new ValueCell(contents, new DoubleValue(Double.parseDouble(contents)));
        cells.put(new Coord(col, row), toAdd);
      } else if (isBool(contents)) {
        toAdd = new ValueCell(contents, new BooleanValue(Boolean.parseBoolean(contents)));
        cells.put(new Coord(col, row), toAdd);
      } else {
        toAdd = new ValueCell(contents, new StringValue(contents));
        cells.put(new Coord(col, row), toAdd);
      }
      return this;
    }

    /**
     * Determines if the given string is a boolean.
     * @param contents The string
     * @return If it is a boolean
     * TODO: IS THIS CORRECT IN ALL CASES: i.e. if there are spaces or anything else
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
    public SimpleSpreadsheet createWorksheet() {
      return new SimpleSpreadsheet(this);
    }
  }

  private SimpleSpreadsheet(Builder builder) {

  }

  @Override
  public void clearCell(Coord coord) {

  }

  @Override
  public void setCellValue(Coord coord, String value) {

  }

  @Override
  public int getNumRows() {
    return 0;
  }

  @Override
  public int getNumColumns() {
    return 0;
  }

  @Override
  public Value getValue(Coord coord) {
    return null;
  }

  @Override
  public String getRawContents(Coord coord) {
    return null;
  }
}
