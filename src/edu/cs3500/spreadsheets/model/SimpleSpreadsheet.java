package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.model.cell.BlankCell;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.FormulaCell;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.value.Value;
import edu.cs3500.spreadsheets.model.graph.Graph;
import java.text.Normalizer.Form;
import java.util.Hashtable;

/**
 * A simple spreadsheet implementation which supports string, boolean, and double values. It's
 * supported operations include summing and taking products of a group of cells, determining if one
 * cell is less than another, and capitalizing the string value of a cell.
 */
public class SimpleSpreadsheet implements SpreadsheetModel {
  private Hashtable<Coord, Cell> cells;
  private Graph<Cell> references;
  // TODO: Finish graph interface and implement it in order to keep track of cell references so
  //       we can identify cycles easily.

  public static class Builder implements WorksheetBuilder<SimpleSpreadsheet> {
    private Hashtable<Coord, Cell> cells;
    private Graph<Cell> references;

    @Override
    public WorksheetBuilder<SimpleSpreadsheet> createCell(int col, int row, String contents) {
      if (contents == null) {
        cells.put(new Coord(col, row), new BlankCell());
        return this;
      }
      if (contents.charAt(0) == '=') {
        cells.put(new Coord(col, row), new FormulaCell(null, contents, null));
      }
      return this;
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
  public void addColumn() {

  }

  @Override
  public void addRow() {

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
  public String getUserString(Coord coord) {
    return null;
  }
}
