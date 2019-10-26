package edu.cs3500.spreadsheets.model.cell;

import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.value.StringValue;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;
import java.util.List;

public class SexpVisitorFormula implements SexpVisitor<Formula> {

  /**
   * Applies the given SExp to this visitor.
   * @param s the Sexp
   * @return The result formula
   */
  private Formula apply(Sexp s) {
    return s.accept(this);
  }

  @Override
  public Formula visitBoolean(boolean b) {
    return new BooleanValue(b);
  }

  @Override
  public Formula visitNumber(double d) {
    return new DoubleValue(d);
  }

  @Override
  public Formula visitSList(List<Sexp> l) {
    return null;
  }

  @Override
  public Formula visitSymbol(String s) {
    return null;
  }

  @Override
  public Formula visitString(String s) {
    return new StringValue(s);
  }
}
