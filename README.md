# GameEnv Board Game Environment
GameEnv Board Game Environment is a project developed by students in In4matx 122 at University of California, Irvine. 

The Game Environment (under [src/GameEnv](src/GameEnv)) consists major components such as

- GameState.java
- Board.java
- Cell.java
- Move.java
- Piece.java
- Player.java

Each class represents the corresponding component in a board game. Any game can be developed by inheriting components found in GameEnv package. As examples, we have developed
1. [Othello](src/Othello)
2. [Eels and Escalator](src/EelsAndEscalator)
3. [Tic-tac-toe](src/TicTacToe)
4. [Memory](src/Memory)

See a list of [contributers](#contributers).

## Manual

### To start Game
Run main from [src/UI/Fortnite.java](src/UI/Fortnite.java)

### The Player Database
All player data is saved in file UserDB.txt using format

`{PlayerName}=>{GameName},{WinCount}=>...`

### To add new player
Fill in the filed above the "Add Player" button in the GUI and click the button. 

### To develop new plug-in
1. Derive appropriate children class from GameEnv
2. Add the new GameType into [Enums/GameType](src/Enums/GameType.java)
3. Add new game to [GameStateFactory.java](src/GameEnv/GameStateFactory.java)
4. Develop Driver that components related to GUI
5. Add new driver to [DriverFactory.java](src/GameEnv/DriverFactory.java)

## Contributers
- Randy Lim
- Princess Pancubit
- Nhi Lam
- Fernando Perez
- Angel Mondragon
- OBrian Santos
- Yuqi Huai
