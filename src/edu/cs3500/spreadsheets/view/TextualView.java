package edu.cs3500.spreadsheets.view;

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

  }

  @Override
  public void refresh() {

  }
}
