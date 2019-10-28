package edu.cs3500.spreadsheets.model.cell.formula.function;

import edu.cs3500.spreadsheets.model.cell.formula.Formula;

public interface IFunc<Formula, R> extends edu.cs3500.spreadsheets.model.cell.formula.Formula {
  R apply(Formula arg1, R arg2);
}
