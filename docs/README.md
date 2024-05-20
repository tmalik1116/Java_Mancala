# Mancala

Mancala is a traditional two-player turn-based strategy board game. The game typically consists of a board with two rows of six pits (or cups) each and two larger pits called stores, one for each player. The objective of the game is to capture more stones (or seeds) than the opponent.

## Description

The purpose of this Mancala program is to provide a digital implementation of the Mancala game, allowing users to play against each other or against a computer opponent. The program is designed to simulate the rules and mechanics of the traditional Mancala game, providing an engaging and strategic gaming experience. Players can make moves by selecting a pit on their side of the board, causing the stones in that pit to be distributed in a counterclockwise fashion. The game involves capturing stones, managing strategies, and ultimately aiming to accumulate the most stones in a player's store.

## Getting Started

### Dependencies

* Java
* gradle


### Executing program
* clone the repository
* cd into the root folder (GP4)
```
gradle build
java -cp build/classes/java/main ui.MancalaUI
```

* Expected output:
> Task :pmdMain
Removed misconfigured rule: LoosePackageCoupling  cause: No packages or classes specified
624 PMD rule violations were found. See the report at: file:///home/tmalik_fw13/Desktop/CIS2430/coursework/GP4/build/reports/pmd/main.html

Deprecated Gradle features were used in this build, making it incompatible with Gradle 9.0.

You can use '--warning-mode all' to show the individual deprecation warnings and determine if they come from your own scripts or plugins.

For more on this, please refer to https://docs.gradle.org/8.3/userguide/command_line_interface.html#sec:command_line_warnings in the Gradle documentation.

BUILD SUCCESSFUL in 2s
3 actionable tasks: 3 executed


## Limitations

There are some PMD violations that I was unable to remove. Otherwise the program is fully functional and compiles as expected. 

## Author Information

Talha Malik
1242190
tmalik04@uoguelph.ca

## Development History

* 0.1
    * Initial Release

## Acknowledgments

* Portions of this code were re-used from A2 in which parts were written by AI
* Partial credit to GPT-3.5 LLM


