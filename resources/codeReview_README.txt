The first thing we noticed when reviewing your code was the type exposure in the model. The model
interface reveals both the Formula interface and the Cell class. The exposing of Formula isn’t as
big of a problem, as it is conceivable that formulas are part of the logical interface which the
model communicates by. Yet this is still probably unnecessary, as one could implement these methods
with a string parameter instead and convert that to a Formula from inside the model implementation.
The exposing of the Cell class was more problematic, as it led to other exposed classes, including
several visitors which an outsider should not be concerned about. An easy fix here is just to use a
Cell interface and hide your implementation behind it. It was due to this type exposure that we
couldn’t follow the adapter pattern to adapt our model to the providers. Other than these issues,
the model interface appears to contain the appropriate methods, although some like addRows() and
addCols() seem unnecessary since the model is not limited by size.

As for the view, the view interface is simple and effective. However, it might be beneficial to have
an addFeatures() or addActionListener() method to this interface so that one can set up callbacks
in this way rather than utilizing a ControllerViewRequestor and passing that in as a parameter to
the view’s constructor. This change will help decouple the view and controller. Another issue we
found was that the GUITableGraphics class referred directly to the model implementation. Again, it
would be better to have a model interface as a constructor parameter to avoid this tight coupling.
Finally, we were a bit confused by the translate methods in the ModelToView interface. It seems
unnecessary to convert your hashmaps into 2d arrays for the view, as this defeats the purpose of
using a hashmap in the first place. We think it would be a cleaner and more efficient solution to
query from the model whenever you wish to get the value or formula of a given cell, rather than
doing this conversion. In the end, this conversion caused us a lot of trouble. Specifically when
creating an empty spreadsheet since the JTable used in the view implementation would take in a 0x0
array and consequently not display an empty spreadsheet. To get around this issue, we had to make
sure our model was not entirely empty by hard coding a cell in our BeyondGood main method.

We think that the controller interface is very well thought out, and adapting our controller to the
providers was not very difficult. However, we think the existence of the ControllerViewRequestor is
odd, and as mentioned previously could have been removed by having an addFeatures() method in the
view interface. Additionally, the CVR has public methods on it, even though it does not implement
any interface, which seems odd to us based on the design principles of object-oriented programming.
These methods echo those in the controller interface, with only a little bit of extra functionality.
We also ran into a problem due to the removal of the ‘=’ in the string of the cell’s formula in the
CVR’s requestCell() method. This made it so our model could never interpret anything in the
spreadsheet as a formula, and we had to extend the CVR to fix this problem. The reason for this
issue may be a difference in how our model works compared to the providers, but we were never able
to figure out why this issue existed (assuming it works in the providers code).