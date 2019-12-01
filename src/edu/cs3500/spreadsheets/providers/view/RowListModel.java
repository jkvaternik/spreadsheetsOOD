package edu.cs3500.spreadsheets.view;

import javax.swing.AbstractListModel;

/**
 * Represents the row list data model that provides a list with the rows as an array of Strings.
 * This is used to display the row headers on the GUI.
 */
public class RowListModel extends AbstractListModel {
  private String[] rows;

  /**
   * Constructs a RowListModel with the wrapper class.
   *
   * @param mtt wrapper class.
   */
  RowListModel(ModelToTable mtt) {
    rows = mtt.rowNames();
  }

  @Override
  public int getSize() {
    return rows.length;
  }

  @Override
  public Object getElementAt(int index) {
    return rows[index];
  }
}
