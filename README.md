ByeByeLF takes text files with hard returns and 'repairs' them by removing hard returns.

For instance: this file:

>
The company was in the process
of winding up, when in walked
the executive.
<

Will be processed by ByeByeLF as:

>
The company was in the process of winding up, when in walked the executive.
<

That's pretty much it.

It uses the average length of all the lines in the file to determine the target line length.

To explain how it works: It is a finite state machine which takes input and sets flags and variables to reformat the text.



