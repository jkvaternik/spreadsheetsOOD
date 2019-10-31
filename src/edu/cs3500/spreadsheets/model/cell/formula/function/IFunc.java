package edu.cs3500.spreadsheets.model.cell.formula.function;

public interface IFunc<A, R> {
  R apply(A arg1) throws IllegalStateException;
}
