package edu.cs3500.spreadsheets.view;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Worksheet;

/**
 * Renders a textual view of the Spreadsheet that displays in the terminal.
 */
public class SpreadsheetTextualView implements SpreadsheetView {
  private final Worksheet sheet;
  private PrintWriter out;

  /**
   * Constructs a textual view of the Spreadsheet.
   *
   * @param sheet the Spreadsheet model.
   */
  public SpreadsheetTextualView(Worksheet sheet, String file) {
    this.sheet = sheet;
    try {
      this.out = new PrintWriter(file);
    } catch (FileNotFoundException f) {
      throw new IllegalArgumentException("File not found");
    }
  }

  @Override
  public void render() {
    out.write(toString());
    out.close();
  }

  @Override
  public String toString() {
    String ret = "";
    for (Map.Entry<Coord, Cell> e : this.sheet.getHashtable().entrySet()) {
      if (e.getValue().getFormula().type().equals("double")) {
        ret += Coord.colIndexToName(e.getKey().col) + e.getKey().row + " "
                + String.format("%f", e.getValue().getFormula().evaluate().numberForm()) + "\n";
      } else {
        ret += "" + Coord.colIndexToName(e.getKey().col) + e.getKey().row
                + " " + e.getValue() + "\n";
      }
    }
    return ret;
  }
}
