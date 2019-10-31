package edu.cs3500.spreadsheets.model;

/**
 * Represents a function object.
 */
public interface IFunc<A, R> {
  /**
   * Applies function object on a value of type A and returns the data type R.
   *
   * @param applyArgument the given value
   * @return value of type R
   * @throws IllegalStateException if arguments are invalid
   */
  R apply(A applyArgument) throws IllegalStateException;
}
