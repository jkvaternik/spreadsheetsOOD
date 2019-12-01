package edu.cs3500.spreadsheets.providers;

import java.util.Objects;

/**
 * Represents a cell in a spreadsheet containing a formula. The contents of the cell could change.
 */
public final class Cell {
  private Formula f;
  private Formula originalFormula;
  private boolean error;

  /**
   * Constructs a cell containing a formula.
   *
   * @param f the formula for the cell
   */
  Cell(Formula f) {
    if (f == null) {
      throw new IllegalArgumentException("Cell can not be created with a null formula");
    }
    this.f = f;
    this.originalFormula = f.accept(new FormulaCopyConstructor());
    this.error = false;
  }

  /**
   * Copy constructor for a cell.
   *
   * @param c the cell to copy
   */
  public Cell(Cell c) {
    this.f = c.f;
    this.originalFormula = c.originalFormula;
    this.error = c.error;
  }

  /**
   * Allows for quick construction while creating cells.
   *
   * @param evaluate evaluated cell
   * @param f        original formula
   */
  public Cell(Value evaluate, Formula f) {
    this.f = evaluate;
    this.originalFormula = f;
    this.error = false;
  }

  /**
   * Evaluates the contents in a cell to a single Value.
   */
  public void evaluateCell() {
    if (f == null) {
      return;
    }
    this.f = f.evaluate();
  }

  /**
   * Getter method ONLY used for testing purposes. This would be package-private to avoid mutation,
   * but it is public for testing purposes.
   *
   * @return the formula
   */
  public Formula getFormula() {
    return this.f.accept(new FormulaCopyConstructor());
  }

  /**
   * Gets the original non evaluated formula of a cell.
   *
   * @return the original formula
   */
  public Formula unevaluatedFormula() {
    return this.originalFormula;
  }

  /**
   * The formula becomes the unevaluated version of the formula.
   */
  public void revertFormula() {
    this.f = this.originalFormula.accept(new RevertVisitor());
  }

  /**
   * Checks to see if the cell is errant.
   *
   * @return true if there is an error
   */
  public boolean errorCell() {
    return this.error;
  }

  /**
   * Sets the cells error value to true.
   */
  void setError() {
    this.error = true;
  }

  @Override
  public String toString() {
    return this.f.toString();
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (!(that instanceof Cell)) {
      return false;
    }

    return ((Cell) that).f == (this.f);
  }

  @Override
  public int hashCode() {
    return Objects.hash(f);
  }
}
