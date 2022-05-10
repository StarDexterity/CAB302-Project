# Computer-Assisted MazeDesignTool
This project is developed and maintained by BMTT. Computer-Assisted MazeDesignTool (CAMDT) is a cutting edge maze design tool that allows users to quickly and easily create and export fun and challenging mazes using automated maze generation and solving technology. The final product will include the following features:
- Random maze generation
- Manual maze editing
- Ability to insert logos
- Maze with start and end points as pictures
- Automatic maze solving and metrics (solution line, total pass through, total dead end)
- Maze information (title, author, description, dates, etc)
- A central database that stores the mazes
- The ability to export multiple mazes to image

    
## Scrum Timeline 


Week 8: “I want to be able to randomly generate a maze” 
By doing this we expect to be able to have the maze’s underlying data structure functioning as this is an integral part of the app and without it; further requirements would not be possible to implement. Once the maze can be represented as data, a generation algorithm can be developed to automatically generate the maze given a set of parameters.  

Since the mazes underlying data structure should be implemented in an appropriate manner that it should be created and searched through by algorithms with ease, this requirement was selected for implementation before week 9’s. Manually constructing a maze can also be done after the generation of the maze, so it made sense to complete it afterwards. 

Week 9: “I want to be able to manually construct a maze” 

To manually construct the maze, we expect that walls should be able to be added as well as removed manually by the user. Walls should only be toggleable if they don’t compromise the mazes outer walls. To accommodate this restriction, walls togglable nature should be able to be disabled within the swing component that allows them to be interacted with. Once a maze is generated (automatically or manually) it can be altered by the user at any time. A maze can be generated manually by only generating the necessary components (I.e., the boundary walls). Implementing this requirement will involve mostly changes to the applications UI to allow for interactions and alteration of the preexisting maze structure created in the previous week. The only pre-requirement for this step is that the underlying data structure is completed, and a basic UI is in place. 

Week 10: “I want to be able to insert a logo into the maze” 

To do this we will need to make it so that the maze can work around a logo or image inserted into the maze by the user. This would be as if the logo was to be considered as additional boundaries for the application, possibly altering the path the solution must take. Once the logo has been inserted, the application will have to check that the image has not made the maze unsolvable, a feature that will be implemented in a later stage. This step cannot be completed until the previous two steps have been fully implemented, as it requires maze generation to be working. 

Week 11: “I want to be able to verify mazes and have the solution shown with a red line” + “I want to be able to see metrics for the difficulty of the maze” 

These tasks are tied together as the difficulty metrics for a maze will not be available until a solution has been found. Once a solution is found then the metrics would just need to be stored and presented to the user when viewing the maze. The difficulty metrics here refer to the application being able to discern what parts of the maze are dead ends, and which parts of the maze are included in the solution. To accomplish this an algorithm will be necessary so that the application knows what a dead end is and that if it hits one then it needs to continue until it finds the correct route. Once this component is completed then the maze software itself will be completed. 

 

Week 12: “I want to be able to store mazes in a database + “I want to be able to print the maze” 

To store the mazes the maze must be stored in an integer format so that the maze can be recalled as well as the solution for it. This database should also store the title, author, description, and several other features. Once the database is linked so that new mazes are added, current mazes can be updated and deleted, and the final component can be added. This component is so that a user can export and print a maze. Once the maze is in the database, saved as an image, then this should only require exporting that image either with or without the solution. This is the final official requirement prescribed for the project so once this component is completed then bug testing can begin. 

 

Week 13: Add finishing touches, and discover and fix any remaining bugs 

This final week can be used to finish components that may not be fully completed as well as checking for bugs or problems within the program. Additional features that have been proposed can be implemented if time permits. Our goal is to complete all major components before this week, however, if a problem does arise this week has been put aside to not compromise the final version of the application. Once we are comfortable with the finished product then the app can be released to Maze Co on or before the due date. 
