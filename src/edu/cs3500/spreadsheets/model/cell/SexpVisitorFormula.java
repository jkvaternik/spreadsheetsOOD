package edu.cs3500.spreadsheets.model.cell;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.formula.CellReference;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.function.EFunctions;
import edu.cs3500.spreadsheets.model.cell.formula.function.Function;
import edu.cs3500.spreadsheets.model.cell.formula.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.ErrorValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;
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
    try {
      Formula f = this.apply(l.get(0));
      Function func = (Function) f;
      for (Sexp s : l.subList(1, l.size())) {
        func.addArg(this.apply(s));
      }
      return func;
    } catch (ClassCastException | IndexOutOfBoundsException e) {
      return new ErrorValue(new IllegalArgumentException("Invalid formula"));
    }
  }

  @Override
  public Formula visitSymbol(String s) {
    if ("PRODUCT".equals(s)) {
      return new Function(EFunctions.PRODUCT,null);
    } else if ("SUM".equals(s)) {
      return new Function(EFunctions.SUM,null);
    } else if ("<".equals(s)) {
      return new Function(EFunctions.LESSTHAN,null);
    } else if ("CAPITALIZE".equals(s)) {
      return new Function(EFunctions.CAPITALIZE,null);
    } else if (s.contains(":") && s.indexOf(":") < s.length() - 1) {
      String cell1 = s.substring(0, s.indexOf(":"));
      String cell2 = s.substring(s.indexOf(":") + 1);
      if (Coord.validCellString(cell1)
          && Coord.validCellString(cell2)) {
        Coord from = Coord.cellStringToCoord(cell1);
        Coord to = Coord.cellStringToCoord(cell2);
        if (from.col <= to.col && from.row <= to.col) {
          return new CellReference(Coord.cellStringToCoord(cell1),
              Coord.cellStringToCoord(cell2));
        } else {
          return new ErrorValue(new IllegalArgumentException("Invalid cell reference"));
        }
      } else {
        return new ErrorValue(new IllegalArgumentException("Invalid cell reference"));
      }
    } else {
      if (Coord.validCellString(s)) {
        Coord cellCoord = Coord.cellStringToCoord(s);
        return new CellReference(cellCoord, cellCoord);
      } else {
        return new ErrorValue(new IllegalArgumentException("Invalid cell reference"));
      }
    }
  }

  @Override
  public Formula visitString(String s) {
    return new StringValue(s);
  }
}
