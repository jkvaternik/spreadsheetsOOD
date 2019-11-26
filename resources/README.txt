ASSIGNMENT 7:

Changes to our view interface:
- We replaced the addMouseListener() and addActionListener() methods with a more general
  addFeatures() method which serves as a callback that links all "important" actions (keyboard,
  mouse, etc) to the given Features.


Changes to our read-only view:
- For assignment 6, our view was read-only but was set up for editing capabilities (we had a toolbar
  for users to change values that just didn't work yet). We removed this toolbar to make it clearer
  for the user that this view cannot be edited. We also removed the increase size button since the
  view is initialized to be big enough to see all non-blank cells, and there is no need to further
  increase the size since no cells can be edited anyways.


Changes to our model:
- We altered the toString for our string values so that the values now display properly in the view.
- The clearCell() method now calls setCellValue() with an empty contents string. Our previous
  implementation led to an error when we completely removed the cell from our mapping, so now it
  just exists as a blank cell.


Edit View:
- This view allows for editing, so it includes a toolbar which has a user input field, buttons for
  either confirming or clearing the edit, and a button to increase the size so users can scroll
  further. Note***: When the increase size button is pressed, it resets the view to the top left
  corner of the spreadsheet. This is to prevent a bug where the text of cells would appear over
  the toolbar in a sufficiently large spreadsheet.
- This view utilizes the addFeatures() callback. This sets up the Keyboard and MouseListeners, which
  are both there own classes, and the action listeners for the buttons. This method processes all
  the inputs and converts them to features events which the controller can utilize to then alter the
  model and the view.
- This view supports the arrow keys to move the selected cell, the enter key to confirm an edit,
  and the delete/backspace keys to delete the contents of the selected cell.
- This view also has a menu for loading and saving files. These get set up in addFeatures so that
  the view only has to worry about whether a file was loaded or saved, and the controller can decide
  what to do with that information.


Features Interface:
The features interface serves as the logical interface between the controller and the view. It
essentially is a a list of the features which our spreadsheet supports. It's methods are:
- cellSelected(), which notifies that the cell at the given coordinate has been selected.
- selectedCellEdited(), which notifies that the current selected cell has been edited and now has
  the new given contents.
- movedHighlightedCell(), which is triggered when the highlighted cell is moved in one of the four
  directions (left, up, down, right).
- deletedSelectedCell(), which means the selected cell's contents have been deleted.
- saveFile(), which saves the current spreadsheet to a given File.
- loadFile(), which loads the spreadsheet contained in the given File.


Direction enum:
Represents one of the four directions (left, up, down, right) which works well for using the arrow
keys to move the selected cell.


Controller class:
Represents the controller for the spreadsheet which links the view to the model. It contains a field
reference to a spreadsheet model and a view, and also keeps reference of the current selected cell.
It also implements the Features interface. Here is what it does in all of the Features methods:
- cellSelected() tells the view to highlight the selected cell, and then has the view refresh itself
- selectedCellEdited() checks that there exists a selected cell and if so, tells the model to set
  the value of that cell to the new contents, and then tells the view to refresh itself.
- movedHighlightedCell() alters the selected cell coordinate based on the given direction, updates
  its reference to the selected coord, tells the view to highlight the new selected cell, then tells
  the view to refresh itself. If the new coordinate is illegal, the previously highlighted coord
  remains highlighted.
- deletedSelectedCell() checks that there exits a selected cell and if so, tells the model to clear
  that cell, and then tells the view to refresh itself.
- saveFile() creates a new file writer and passes it to a new textualView which contains the
  spreadsheet model and then calls makeVisible() on that view, which will create the file with the
  model's contents in the appropriate file path.
- loadFile() reads in the appropriate file using the Worksheet builder and reader, along with a
  file reader. Once the new spreadsheet model has been created, a new view is created based on that
  model, and a new controller is made based on the model and view. The view is then made visible,
  which closes the previous view and opens the new one with its new controller.

____________________________________________________________________________________________________

ASSIGNMENT 6:

General structure of our View:
- A View interface which determines the functionality of our views.
- A TextualView and VisualView which both implement View.
- A SpreadsheetPanel, which is where we actually draw the spreadsheet for the visual view.

Now into more depth about each of these:

View Interface:
This is our view interface. It contains the following methods:
- makeVisible(), which makes the view visible for the first time.
- refresh(), which makes the view "re-draw" itself.
- addMouseListener(), which adds a mouse listener to the view (for whatever reason it may want to
  listen to our view's mouse events).
- addActionListener(), which adds an action listener to the view (for whatever reason it may want to
  listen to our view's action events).
- highlightCells(), which signals to the view that the cells at the given coordinates are important
  and should be highlighted (the meaning of this is purposefully left vague).

Note: We did not think we would need to support adding a keyboard listener, but this may change
      depending on the specs of the next assignments.


TextualView:
This is a textual view, which writes the contents of our spreadsheet to an appendable, which is done
in the makeVisible() method. All the other methods do nothing, as it doesn't make much sense to
refresh the view or highlight cells, and we don't really care about who is listening to us, as this
view does not have any mouse or action events associated with it.


VisualView:
This is a GUI view, which utilizes Swing and is a JFrame. The View consists of a spreadsheet panel,
which draws the main spreadsheet, and a toolbar which has a text field and button for editing cells
(this feature is not working since we need a controller) and a button for increasing the size of the
spreadsheet (this does in fact work). The main panel is also scrollable, and we use a standard
ScrollPane to handle the scrolling. Here are how the interface methods work:
- makeVisible() sets the view to be visible
- refresh() repaints this view (so repaints all of its components)
- addActionListener() adds an action listener to the edit button
- addMouseListener() adds a mouse listener to the main panel
- highlightCells() just calls the setHighlightedCells() method in spreadsheetPanel, passing
  through the list of Coordinates


SpreadsheetPanel:
This is a JPanel which is the main component to our VisualView. It is also a Scrollable and a
MouseMotionListener (both of these allow us to implement scrolling with just a normal ScrollPane).
All of these interface methods are relatively straight forward to implement.

The interesting part of the panel is its paintComponent() method. In this method, we first draw the
vertical and horizontal black lines that create the grid. To do this, we keep track of the preferred
size (which is essentially the number of rows and columns of the grid) and update it whenever we
want to expand the spreadsheet. After drawing the grid, we check for highlighted cells and if there
are any, we color the appropriate grid space. After this, we display all of the cell contents of the
spreadsheet. In order to do this, we utilize a read-only ViewModel which is passed in through the
VisualView and stored as a field.


ViewModel:
This is an Object adapter which implements the Spreadsheet (model) interface. It takes in a
Spreadsheet which it delegates to for all of the observer methods. For the operation (mutator)
methods, we opted to throw an UnsupportedArgumentException rather than just doing nothing, since we
do not want to give the illusion that we modified the model and would rather tell the client that
they have performed an illegal action.


Changes from Assignment 5:
- Previously, we handled re-evaluation on get time (when you got the contents of a cell) rather than
  set time (when you set the contents of a cell). We changed that for this assignment as we realized
  you needed to get the value of a cell much more frequently than you needed to set the value.
- To handle this change, we added a Value field to our FormulaCell class. Our evaluate method for
  cells now just updates this field, while a new getValue() method on the Cell interface actually
  returns the value.
- We added a referencesCell() method on our Cell and Formula interfaces, which determine if they
  respectively reference a cell at the given coordinate. This was used to determine what cells would
  need to be re-evaluated when you changed a cell at a certain Coord. This process was handled by
  the reEvaluateCells() private method in SimpleSpreadsheet.


____________________________________________________________________________________________________

ASSIGNMENT 5:

General Structure of our Model:
- A Spreadsheet which consists of a mapping of Coordinates to Cells.
- Cells, which can either be a BlankCell, a ValueCell, or a FormulaCell.
- ValueCells contain Values, which can be StringValues, BooleanValues, DoubleValues, or ErrorValues.
- FormulaCells contain a Formula, which can be a Value, a Function, or a Cell Reference.
- Functions contain one of the allowed cell functions (sum, product, etc.) and a list of Formula
  arguments.
- A Cell Reference contains two Coords which encapsulate the rectangular region of the reference.

Now We will go more in depth about each class/interface, explaining their purposes:

Spreadsheet Interface:
The Spreadsheet is our model interface. The most important methods in the spreadsheet are:
- setCellValue which is responsible for editing a cell's contents or "adding" a new cell.
- getValue which gets the Value of a cell and returns it as a String (using the Value's toString).
- clearCell, which clears all of a cell's contents.
- getErrorCoords which gets a list of all the Coords of Cells that have error values.

The spreadsheet also has methods that will be useful for the view:
- getNumRows which returns the largest row that has an edited cell (so the view knows all rows
  beyond that consist of only blankCells).
- getNumColumns which does the same but for columns, not rows
- getRawContents which gets the string contents that were entered into a cell (so when a user edits
  a cell, the previous formula/value can be modified instead of starting from scratch)


SimpleSpreadsheet:
The SimpleSpreadsheet is our implementation of Spreadsheet. It consists of a hashtable that maps
Coords to Cells. This table only needs to store the non-blank cells, as all cells not in the table
will then be assumed to be blank.


Cell interface:
The Cell is the the basic building block of our Spreadsheet. It has 3 methods which it needs to do:
- evaluate is how we determine the value of a cell. In order to evaluate, the Cell needs to know the
  model's table of Coords to Cells, because a Cell knows which coordinates it refers to, but not the
  exact Cell at that location. For efficiency purposes, we also keep track of a mapping of Formula
  to Value, so that once we have evaluated a formula, we do not need to do it again (not counting
  re-evaluating after a change).
- getRawContents is how we determine the raw string that was used to create this cell.
- containsCyclicReference is how we determine if this cell, or any of its references, contains a
  cyclic reference. Once again, we need to take in the Coord -> Cell mapping from the model. We also
  keep track of two Sets of Coords. The first set represents a local scoping of which other Coords
  this cell has visited, in order to locally detect for a cycle. The Other Set is strictly for
  optimization, and consists of every Coord which we know does not contain a cycle, so we only have
  to check each Coord once.


BlankCell:
The BlankCell is the most basic Cell implementation, and represents the lack of a value or formula.
It evaluates to an empty StringValue and its raw contents are null (since no string was entered to
"make" it). It cannot contain a cyclic reference. It is also important to note that all BlankCells
are considered equal to one another (this may change later to alias equality if we run into issues).

ValueCell:
The ValueCell simply contains a Value. It evaluates to that Value, its rawContents is the string of
that value, and it cannot contain a cyclic reference.

FormulaCell:
The FormulaCell is the most complicated Cell, consisting of a Formula. It evaluates to whatever the
Formula evaluates to, has a raw contents of the string version of the formula, and may contain a
cyclic reference if its Formula does.


Formula Interface:
The Formula is the most diverse aspect of our cells, as it can be either a Value, a Function, or a
Cell Reference. Similarly to the Cell, it needs to be able to evaluate itself and check for cyclic
references (the behaviors are akin to the Cell's version). It also needs to be able to accept a
Formula Visitor.


Value Interface:
The Value is a formula, so it needs to do everything a formula can do. Additionally, it needs
one more method:
- getValue which returns its internal value (i.e. String, boolean, double, etc). This is useful for
  applying values to Functions.
Also, it is important to note that values evaluate to themselves (since evaluate returns a Value),
and they cannot contain cyclical references.


BooleanValue:
The BooleanValue contains a boolean. getValue returns that boolean. All BooleanValues with the same
value are considered equal.

DoubleValue:
The DoubleValue contains a double. getValue returns that double. All DoubleValues with the same
value are considered equal. It's toString is formatted to 5 decimals (i.e. "0.00000").

StringValue:
The StringValue contains a String. getValue returns that String. All StringValue with the same
value are considered equal. It's toString surrounds the string in double quotes, replaces \ with \\
and replaces " with \".

ErrorValue:
The ErrorValue contains an Exception. getValue returns that Exception. All ErrorValues with the same
error message are considered equal (even if the types are different). Note: This may change if we
run into problems. It's toString is just the error message.

ValueVisitor interface:
This interface visits Values.

IsErrorFunction:
This class is a ValueVisitor which determines if the Value is an ErrorValue. This is useful for the
getErrorCoords method in Spreadsheet.


IFunction interface:
An IFunction is a Formula, so it supports all the Formula behaviors. Additionally, it needs:
- addArg which adds a Formula argument to this IFunction. This is necessary for the SExp->Formula
  visitor.


Function class:
A Function is the implementation of IFunction. It contains a list of Formula arguments and an
EFunction, which is one of the Spreadsheet-supported functions. To evaluate a Formula, it applies
all of its arguments to the Function that it is (i.e. SUM, PRODUCT), and its behavior varies for
each. For example, SUM and PRODUCT can have any amount of arguments, while < must have 2 and
CAPITALIZE must have 1. If it runs into a problem, it is evaluated to an ErrorValue.
For containsCyclicalReference, it checks if any of it arguments contains a cycle.

EFunction Enum:
This is the enumeration of all supported functions. this ensures that if new functionality is to be
added, it can just be added to the Enum, and one more case will have to be added to the switch
statement in Function's evaluate method.

SumFunction:
This represents the SUM operation. It takes in the Coord->Cell mapping and the Formula->Value
mapping since, in order for it to evaluate a Cell Reference or Function argument, it needs to first
evaluate the argument then apply it. It ignores all non numbers, and throws an Exception if it runs
into an ErrorValue.

IFunc Interface:
Represents a generic function object from one type to another, which has an apply method.

FormulaVisitor:
An IFunc from Formulas to a generic type R, where R is whatever value type needs to be returned by
an operation. This is used to make the Function operations (SUM, PRODUCT, etc.) work.

ProductFunction:
This represents the PRODUCT operation. It takes in the Coord->Cell mapping and the Formula->Value
mapping since, in order for it to evaluate a Cell Reference or Function argument, it needs to first
evaluate the argument then apply it. It ignores all non numbers, and throws an Exception if it runs
into an ErrorValue. It also needs to keep track of how many arguments it has evaluated, since if it
has no numeric arguments, its default value is 0.

LessThanFunction:
This represents the < operation. It takes in the Coord->Cell mapping and the Formula->Value
mapping since, in order for it to evaluate a Cell Reference or Function argument, it needs to first
evaluate the argument then apply it. It throws an exception for all non-numeric arguments, and any
Cell References that are larger than just a single cell.

CapitalizeFunction:
This represents the CAPITALIZE operation. It takes in the Coord->Cell mapping and the Formula->Value
mapping since, in order for it to evaluate a Cell Reference or Function argument, it needs to first
evaluate the argument then apply it. It throws an exception for all non-numeric arguments, and any
Cell References that are larger than just a single cell.


CellReference:
A CellReference is a Formula which represents a rectangular group of cells. Its internals consist of
two coordinates, one which represents the top left corner of the rectangle, and one which is the
bottom right corner. For evaluation, a CellReference of 1 cell evaluates to that cell's evaluation
Value, but you cannot directly evaluate a multi-cell reference, so this yields an ErrorValue.
For containsCyclicReference(), each Coord in the reference is checked to see if it contains a cycle.
Once a Coord is determined to not have a cycle, it is added to the Set of Coords which don't
contain a cycle.
CellReferences also have two important helper methods:
- getAllCoords which returns a list of all the coordinates in the rectangular region.
- getAllCells which returns a list of all the Cells that are contained in the rectangular region.
  This method is currently public, since we need to access it from our Functions (when the functions
  are visiting CellReferences). We know this is not ideal, as it is not an interface method, but we
  cannot think of a fix to this problem at the moment.