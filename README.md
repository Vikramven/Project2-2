# Project2-2

Our code is structured into 3 packages: 

<b>Agent:</b> it contains the main component for the Game logic; the map, and the agents. 

<b>Controller:</b> establishes the connections between the logic and the visuals of the user interface, also contains useful generic method and interface to be used at any time (vectors).

<b>Path:</b> contains the A* algorithm as well as essential Move method to update the agent positions.

## Set up Libraries and Dependencies: ##

	Step 1. Open the project via the zipFile, Please use the map named: "Visuals", because there are two different version of code. "GoalUpdater" contains the goalUpdater Algorithm and "Visuals" contains the A* star connected with the GUI.
	Step 2. Open in IDE (preferably Intelij)
	Step 3. Navigate in the top right corner, File -> Project Structure -> (1) Libraries -> (2) add lib folder via the + -> (3) Navigate to lib folder, select and press ok.

![image](https://user-images.githubusercontent.com/56164753/159579446-34e46ba4-09ef-481f-8842-aedb2d898054.png)

	Step 4. Navigate in the project structure to (1) Project -> Select SDK -> select openJDK version 17 and press ok.

![image](https://user-images.githubusercontent.com/56164753/159579900-caf5280f-54a9-424b-bf61-5e8b3e45dfba.png)

	Step 5. Navigate to the package src/main/java/Visuals/main/
	Step 6. Run StartGame.java


