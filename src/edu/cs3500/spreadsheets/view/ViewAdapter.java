package edu.cs3500.spreadsheets.view;


import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.providers.SpreadsheetView;

/**
 * Adapts our provider's {@link edu.cs3500.spreadsheets.providers.SpreadsheetView} to our view in
 * order to link their view with our controller.
 */
public class ViewAdapter implements View {
  private final SpreadsheetView providerView;

  /**
   * Makes a view adapter using the provider's view.
   * @param providerView The provider's view
   */
  public ViewAdapter(SpreadsheetView providerView) {
    this.providerView = providerView;
  }

  @Override
  public void makeVisible() {
    providerView.render();
  }

  @Override
  public void refresh() {
    providerView.render();
  }

  @Override
  public void addFeatures(Features features) {
    //This method does not apply to our provider's view
  }

  @Override
  public void highlightCell(Coord cellCoord) {
    //This method does not apply to our provider's view
  }
}
