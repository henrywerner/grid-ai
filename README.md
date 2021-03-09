# Grid Navigation A.I.

## Files Included
`handler.java` 
: Houses the main method; Converts the input file into an int matrix.

`navigator.java`
: Contains all logic for navigating and solving the map.

`node.java`
: An object class that holds data for explored map nodes.


## Execution
I developed the program using JDK 1.8.0 and JDK 15.0.2 using the IntelliJ IDEA IDE. I also tested compiling and running using Windows command prompt.
Input is handled by passing through the map file name as an argument.

Here is an example of compiling and executing in command prompt while using a map file from the maps folder:

```
C:\Users\htwer\Desktop\grid-ai\src>javac handler.java

C:\Users\htwer\Desktop\grid-ai\src>java handler ../maps/Project1Map_0.txt
######################
#              ST  []#
#  []              []#
#  []EN          [][]#
#  []              []#
#  [][][]        [][]#
#  [][][][]    [][][]#
#  [][][][]    [][][]#
#  [][][][]EN  [][][]#
#  [][][][][]  [][][]#
#[][][][][][]EN[][][]#
######################

Starting point: (7, 0)
Endpoint found.
Solution:
Left
Left
Left
Left
Left
Down
Down

######################
#    ::::::::::::  []#
#  []::            []#
#  [];;          [][]#
#  []              []#
#  [][][]        [][]#
#  [][][][]    [][][]#
#  [][][][]    [][][]#
#  [][][][]EN  [][][]#
#  [][][][][]  [][][]#
#[][][][][][]EN[][][]#
######################
```

NOTE: When running the program within an IDE, I had to use the argument `./maps/MapFile` instead of `../maps/MapFile`.

## Map Depiction
`ST` : Starting point

`EN` : End point

`[]` : Wall; The agent is unable to pass through.

`##` : Map boundary

`::` : AI path

`;;` : AI path end point

