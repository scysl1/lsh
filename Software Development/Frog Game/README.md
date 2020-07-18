Tested on:
	Lab Machine
	MacOS with Java10 AND JavaFX11
	
Build script:
	MAVEN
	(To run the project, please first git clone the whole project from gitlab. Then import project from externel model and choose Maven. Everything will be ok now :-) )

For the maintaining part, both macro and micro refactoring activities were conducted. 
	Macro activities were referred to utilizing design patterns and using Maven for increase maintenance. 
		I created a Maven project first and embedded files in it. 
			During working on the project, activities like cleaning auto-generated files and testing could be performed easily with maven tool bar. 
		In total, three design patterns were used: 
			1. MVC design patterns: switching between multiple scenes in the same stage and setting up different game parameters for various levels in "Controller.java", at line 84 and 110. The reason i used MVC in these two situations is that since a lot of methods were commonly used, you can easily create an instantiation and reset the parameters of it. It is convinient and easy for future maintenance. Moreover, modifications in model will not affect the entire architecture.
			2. Factory design pattern was used when inntiating different obstacles and turtles before the game start. The factory was defined in file "physicalstuff_Factory.java". I used factory here is that large amount of attributes of them were similar which could be manipulated in similar way. Moreover, it allows loose coupling.
			3. Singleton for writing and viewing high scores in file "Write_view_score.java". Since scores were changed after each unique round, it is suitable to use singleton which allows that there was only one instance of score each time. 
	Micro activities were referred to activities like encapsulation, pulling up and replacing complex conditions with variables, etc. 
		For encapsulation, extracting methods happened nearly everywhere. A class called "physicalstuff"(physicalstuff.java) was created and extended with game elements such as obstacles and turtles. 
		A lot of attributes and methods of them were same and therfore were pulled up in "physicalstuff.java".
		Also, conditions were replaced with variables in line 155, 189 of "animal.java", line 20 of "WetTurtle.java" and line 20 of "Turtle.java". This all made codes reusabl, understandable and easy to maintained with.
		Junit tests were carefully designed for testing behaviour rather than implementation, including testing behavior and exception. Single assertion rule was obeyed.
For the addtion part, several aspects including adding info page, different levels of game and viewing permanent high scores were achieved.
	An info button in the cover page was added in file "MenuPage.fxml". 
	A back button was designed for turning back to the previous page after finishing reading the guidance("GuidancePage.fxml").
	Different interesting game levels were designed using ideas from the given website. From level1 to level3, the speed of game elements will gradually increase and more obstacles will appear. In addition, a snake is added.(Snake.java) 
	Before start, history score could be found in the cover page by clicking the "view score" button. After each round, there will also be a popup showing history score. The highest score in these three rounds will be written into file as permanent score, which was done in file "initialize/write_view_score.java"



