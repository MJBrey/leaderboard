# league

Given a list of game results, the application will output a leaderboard with each team
ranked by results. This application will take a file as input and output the results on 
std out in the terminal from which it is run.

###Sample input
Lions 3, Snakes 3
Tarantulas 1, FC Awesome 0
Lions 1, FC Awesome 1
Tarantulas 3, Snakes 1
Lions 4, Grouches 0

###Sample output
1. Tarantulas, 6 pts
2. Lions, 5 pts
3. FC Awesome, 1 pt
3. Snakes, 1 pt
4. Grouches, 0 pts

###Instructions to run the application
using a terminal, navigate to the project root. Once in the project root,
you can run the application with `sbt run`.

Once you've run the application you should see the following printed to the
terminal `Please input file location, usage: <C:/Users/peanut/Documents/results.txt>`
Once you've entered the filename, if the input was valid as in the example input 
section of the readme, you will see the sample output as displayed above.