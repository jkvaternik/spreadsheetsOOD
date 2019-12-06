package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.SimpleSpreadsheet;
import edu.cs3500.spreadsheets.sexp.Parser;
import java.io.IOException;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;

/**
 * Represents a textual view of a spreadsheet. This view writes to an appendable as they were read
 * by the model.
 */
public class TextualView implements View {
  private final Appendable ap;
  private final ViewModel viewModel;

  /**
   * Makes a textual view.
   * @param ap The appendable that the view writes to.
   * @param viewModel The viewModel which this view uses to access the model's data.
   */
  public TextualView(Appendable ap, ViewModel viewModel) {
    this.ap = ap;
    this.viewModel = viewModel;
  }

  @Override
  public void makeVisible() {
    int rows = this.viewModel.getNumRows();
    int cols = this.viewModel.getNumColumns();

    //Display all cell contents
    for (int row = 1; row <= rows; row++) {
      for (int col = 1; col <= cols; col++) {
        String coordString = Coord.colIndexToName(col) + row;
        String val = this.viewModel.getRawContents(new Coord(col, row));
        try {
          if (val == null) {
            continue;
          }
          if (!(val.equals(""))) {
            this.ap.append(coordString).append(" ").append(val).append("\n");
          }
        } catch (IOException e) {
          throw new IllegalStateException("View was unable to show output.");
        }
      }
    }
    //Display all row heights and cell widths (if any are changed)
    for (int row = 1; row <= rows; row++) {
      int height = this.viewModel.getRowHeight(row);
      if (height != SimpleSpreadsheet.DEFAULT_ROW_HEIGHT) {
        try {
          this.ap.append(Integer.toString(row)).append(" ").append(Integer.toString(height));
        } catch (IOException e) {
          throw new IllegalStateException("View was unable to show output.");
        }
      }
    }
    for (int col = 1; col <= cols; col++) {
      int width = this.viewModel.getColWidth(col);
      if (width != SimpleSpreadsheet.DEFAULT_COL_WIDTH) {
        try {
          this.ap.append(Integer.toString(col)).append(" ").append(Integer.toString(width));
        } catch (IOException e) {
          throw new IllegalStateException("View was unable to show output.");
        }
      }
    }
  }

  @Override
  public void refresh() {
    //Do nothing, as we do not want to write again to the appendable
  }

  @Override
  public void addFeatures(Features features) {
    //Do nothing, as there are no features supported by this view.
  }

  @Override
  public void highlightCell(Coord cellCoord) {
    //Do nothing, since highlighting a cell doesn't make much sense for the textual view.
  }
}
