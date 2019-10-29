package edu.cs3500.spreadsheets.model.cell.formula.function;

import java.util.Hashtable;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;

public class Function implements Formula {
  private FormulaVisitor func;
  private List<Formula> args;


  @Override
  public Value evaluate(Hashtable<Coord, Cell> spreadsheet) {
    Value val = func.apply(args);
    return val;
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitFunction(this);
  }
}
