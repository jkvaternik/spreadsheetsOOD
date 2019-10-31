package edu.cs3500.spreadsheets.model.cell.formula.function;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

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
   *
   * @param args The arguments
   */
  public Function(EFunctions func, List<Formula> args) {
    if (args == null) {
      this.args = new ArrayList<>();
      this.func = func;
    } else {
      this.args = args;
      this.func = func;
    }
  }

  @Override
  public Value evaluate(Hashtable<Coord, Cell> cells, Hashtable<Formula, Value> values) {
    if (values.containsKey(this)) {
      return values.get(this);
    }
    try {
      if (this.args.isEmpty()) {
        throw new IllegalStateException("Invalid function.");
      }
      switch (func) {
        case SUM:
          SumFunction sumFunc = new SumFunction(cells, values);
          double sum = 0.0;
          for (Formula arg : this.args) {
            sum += sumFunc.apply(arg);
          }
          values.put(this, new DoubleValue(sum));
          return new DoubleValue(sum);
        case PRODUCT:
          ProductFunction prodFunc = new ProductFunction(cells, values);
          double product = 1.0;
          for (Formula arg : this.args) {
            product = product * prodFunc.apply(arg);
          }
          if (prodFunc.getDoubleValueCount() == 0) {
            values.put(this, new DoubleValue(0.0));
            return new DoubleValue(0.0);
          } else {
            values.put(this, new DoubleValue(product));
            return new DoubleValue(product);
          }
        case LESSTHAN:
          LessThanFunction ltFunc = new LessThanFunction(cells, values);
          if (this.args.size() != 2) {
            throw new IllegalStateException("Invalid < function.");
          } else {
            double arg1 = ltFunc.apply(this.args.get(0));
            double arg2 = ltFunc.apply(this.args.get(1));

            boolean result = arg1 < arg2;

            values.put(this, new BooleanValue(result));
            return new BooleanValue(result);
          }
        case CAPITALIZE:
          CapitalizeFunction capFunc = new CapitalizeFunction(cells, values);
          if (this.args.size() != 1) {
            throw new IllegalStateException("Invalid CAPITALIZE function.");
          } else {
            String toCap = capFunc.apply(this.args.get(0));

            values.put(this, new StringValue(toCap.toUpperCase()));
            return new StringValue(toCap.toUpperCase());
          }
      }
      return new ErrorValue(new IllegalArgumentException("Function could not be evaluated."));
    } catch (IllegalStateException e) {
      values.put(this, new ErrorValue(new IllegalArgumentException("Invalid function.")));
      return new ErrorValue(new IllegalArgumentException("Invalid function."));
    }
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitFunction(this);
  }

  @Override
  public boolean containsCyclicalReference(HashSet<Coord> visitedCoords,
                                           Hashtable<Coord, Cell> cells, HashSet<Coord> coordsNoCycle) {
    for (Formula arg : this.args) {
      if (arg.containsCyclicalReference(visitedCoords, cells, coordsNoCycle)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void addArg(Formula arg) {
    this.args.add(arg);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Function) {
      return this.func == ((Function) other).func
              && this.args.equals(((Function) other).args);
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.func, this.args);
  }

  @Override
  public String toString() {
    return this.func.toString() + ", " + this.args.toString();
  }
}
