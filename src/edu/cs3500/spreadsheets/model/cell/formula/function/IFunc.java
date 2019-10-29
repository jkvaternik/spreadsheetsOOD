package edu.cs3500.spreadsheets.model.cell.formula.function;

import edu.cs3500.spreadsheets.model.cell.formula.Formula;

public interface IFunc<A, R> extends Formula {
  R apply(A arg1);
}
