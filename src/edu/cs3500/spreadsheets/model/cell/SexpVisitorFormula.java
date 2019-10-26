package edu.cs3500.spreadsheets.model.cell;

import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.function.CapitalizeFunction;
import edu.cs3500.spreadsheets.model.cell.formula.function.LessThanFunction;
import edu.cs3500.spreadsheets.model.cell.formula.function.ProductFunction;
import edu.cs3500.spreadsheets.model.cell.formula.function.SumFunction;
import edu.cs3500.spreadsheets.model.cell.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.value.StringValue;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;
import java.util.ArrayList;
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
  //TODO: How do I process cell references??? Convert to coordinates???
  public Formula visitSymbol(String s) {
    if ("PRODUCT".equals(s)) {
      return new ProductFunction(null);
    } else if ("SUM".equals(s)) {
      return new SumFunction(null);
    } else if ("<".equals(s)) {
      return new LessThanFunction(null);
    } else if ("CAPITALIZE".equals(s)) {
      return new CapitalizeFunction(null);
    } else if (s.equals("A1")) {
      // Make a single cell reference
      return new BooleanValue(true);
    } else if (s.equals("A1:B2")) {
      // Make a multi cell reference
      return new BooleanValue(true);
    } else {
      throw new IllegalArgumentException("Invalid symbol: " + s);
    }
  }

  @Override
  public Formula visitString(String s) {
    return new StringValue(s);
  }
}
