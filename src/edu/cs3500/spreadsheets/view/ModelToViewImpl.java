package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.providers.ModelToView;

/**
 * Implements the provider's ModelToView interface which utilizes our model rather than theirs.
 * It adapts our model to what is needed to make their views work.
 */
public class ModelToViewImpl implements ModelToView {
  private final SpreadsheetModel model;

  /**
   * Creates an instance of the model to view converter which utliizes the given model.
   * @param model The spreadsheet model
   */
  public ModelToViewImpl(SpreadsheetModel model) {
    this.model = model;
  }

  @Override
  public int numCols() {
    return model.getNumColumns();
  }

  @Override
  public int numRows() {
    return model.getNumRows();
  }

  @Override
  public String[] colNames() {
    String[] names = new String[model.getNumColumns()];
    for (int i = 0; i < names.length; i++) {
      names[i] = Coord.colIndexToName(i);
    }
    return names;
  }

  @Override
  public String[] rowNames() {
    String[] names = new String[model.getNumRows()];
    for (int i = 0; i < names.length; i++) {
      //add 1 to make it 1-indexed
      names[i] = Integer.toString(i + 1);
    }
    return names;
  }

  @Override
  public String[][] translate() {
    String[][] contents = new String[model.getNumColumns()][];

    for (int i = 0; i < contents.length; i++) {
      contents[i] = new String[model.getNumRows()];
      for (int j = 0; j < contents[i].length; j++) {
        contents[i][j] = this.model.getValue(new Coord(i + 1, j + 1));
      }
    }

    return contents;
  }

  @Override
  public String[][] formTranslate() {
    String[][] contents = new String[model.getNumColumns()][];

    for (int i = 0; i < contents.length; i++) {
      contents[i] = new String[model.getNumRows()];
      for (int j = 0; j < contents[i].length; j++) {
        contents[i][j] = this.model.getRawContents(new Coord(i + 1, j + 1));
      }
    }

    return contents;
  }
}
