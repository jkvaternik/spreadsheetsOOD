package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.IOException;
import javax.swing.Scrollable;

/**
 * Represents a textual view of a spreadsheet. This view writes the model to an appendable.
 */
public class TextualView implements View {
  private final Appendable ap;
  private final ViewModel viewModel;

  public TextualView(Appendable ap, ViewModel viewModel) {
    this.ap = ap;
    this.viewModel = viewModel;
  }

  @Override
  public void makeVisible() {
    int rows = this.viewModel.getNumRows();
    int cols = this.viewModel.getNumColumns();
    for (int row = 1; row <= rows; row++) {
      for (int col = 1; col <= cols; col++) {
        String coordString = Coord.colIndexToName(col) + row;
        String val = this.viewModel.getRawContents(new Coord(col, row));
        try {
          if (!(val.equals(""))) {
            this.ap.append(coordString).append(" ").append(val).append("\n");
          }
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
}
