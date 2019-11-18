package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;

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

  @Override
  public void addFeatures(Features features) {
    //Do nothing, as there are no features supported by this view.
  }

  @Override
  public void addMouseListener(MouseListener listener) {
    //Do nothing, since any listener can listen to this view, although this view won't have any
    //mouse events.
  }

  @Override
  public void addActionListener(ActionListener listener) {
    //Do nothing, since any listener can listen to this view, although this view won't have any
    //action events.
  }

  @Override
  public void highlightCell(Coord cellCoord) {
    //Do nothing, since highlighting a cell doesn't make much sense for the textual view.
  }
}
