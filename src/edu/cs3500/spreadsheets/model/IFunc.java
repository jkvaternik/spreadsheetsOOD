package edu.cs3500.spreadsheets.model;

public interface IFunc<A, R> {
  R apply(A arg1) throws IllegalStateException;
}
