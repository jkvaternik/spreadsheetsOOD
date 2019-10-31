General Structure of our Model:
- A Spreadsheet which consists of a mapping of Coordinates to Cells.
- Cells, which can either be a BlankCell, a ValueCell, or a FormulaCell.
- ValueCells contain Values, which can be StringValues, BooleanValues, DoubleValues, or ErrorValues.
- FormulaCells contain a Formula, which can be a Value, a Function, or a Cell Reference.
- Functions contain one of the allowed cell functions (sum, product, etc.) and a list of Formula
  arguments.
- A Cell Reference contains two Coords which encapsulate the rectangular region of the reference.

Now We will go more in depth about each class/interface:

Spreadsheet: