package edu.cs3500.spreadsheets.model.cell.formula.function;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.ErrorValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;

public class Function implements IFunction {
  private List<Formula> args;
  private EFunctions func;

  /**
   * Creates a function with the given arguments.
   * @param args The arguments
   */
  public Function(EFunctions func, List<Formula> args) {
    if (args == null) {
      this.args = new ArrayList<>();
      this.func = func;
    }
    else {
      this.args = args;
      this.func = func;
    }
  }

  @Override
  public Value evaluate(Hashtable<Coord, Cell> cells) {
    try {
      if (this.args.isEmpty()) {
        throw new IllegalStateException("Invalid function.");
      }
      switch (func) {
        case SUM:
          double sum = 0.0;
          for (Formula arg : this.args) {
            sum += new SumFunction(cells).apply(arg);
          }
          return new DoubleValue(sum);
        case PRODUCT:
          ProductFunction prodFunc = new ProductFunction(cells);
          double product = 1.0;
          for (Formula arg : this.args) {
            product = product * prodFunc.apply(arg);
          }
          if (prodFunc.getDoubleValueCount() == 0) {
            return new DoubleValue(0.0);
          } else {
            return new DoubleValue(product);
          }
        case LESSTHAN:
          LessThanFunction ltFunc = new LessThanFunction(cells);
          if (this.args.size() != 2) {
            throw new IllegalStateException("Invalid < function.");
          } else {
            double arg1 = ltFunc.apply(this.args.get(0));
            double arg2 = ltFunc.apply(this.args.get(1));

            boolean result = arg1 < arg2;

            return new BooleanValue(result);
          }
        case CAPITALIZE:
          CapitalizeFunction capFunc = new CapitalizeFunction(cells);
          if (this.args.size() != 1) {
            throw new IllegalStateException("Invalid CAPITALIZE function.");
          } else {
            String toCap = capFunc.apply(this.args.get(0));

            return new StringValue(toCap.toUpperCase());
          }
      }
      return new ErrorValue(new IllegalArgumentException("Function could not be evaluated."));
    } catch (IllegalStateException e) {
      return new ErrorValue(new IllegalArgumentException("Invalid function."));
    }
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitFunction(this);
  }

  @Override
  public List<Formula> getArgs() {
    return this.args;
  }

  @Override
  public void addArg(Formula arg) {
    this.args.add(arg);
  }
}