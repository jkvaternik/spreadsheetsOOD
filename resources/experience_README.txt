Based on assignments 5-8, we are happy with the way much of our code turned out. Our model was
strong enough that we barely modified it after assignment 5, and our view was designed with enough
forethought that made implementing the controller relatively straightforward. However, there were
still a couple areas where our code could have been made cleaner/designed better. For one, the way
we handled functions and parsing the s-expressions to these functions was slightly messy and while
it can be extended to include other functionality, this would take us a bit of work. Additionally,
we wish we had been able to decouple our specific view implementation from the controller when it
comes to loading files.

In terms of our assignment 8 experience, we had some initial difficulties as our providers did not
give us their code until Saturday, after we emailed them a day prior about this issue. Communication
improved starting Monday, but we still had issues regarding their jar files, which we requested in
order to be able to debug our implementation of their views. Despite these problems, our providers
were willing to make the necessary changes we requested and even helped with the problem solving
when we determined that their model was too exposed for us properly convert our implementation into
theirs.

This issue—as well as others—did not follow the object-oriented programming design principles we’ve
discussed in class. Because of this, we struggled in following the adapter pattern for the model and
instead used their wrapper interface ModelToView to link our model to their view.
