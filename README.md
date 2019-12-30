# Adrenaline

## The project
Implementation in Java of a version of the game with the full set of rules and the terminator mode. The game can be played by different players on the same computer, or on a different computers connected on the same LAN. The project has been implemented with the Model-Controller-View design pattern.

This version supports also the disconnection and reconnection of the players, in case of crash of one of the players.

## The game
In the future, war has left the world in complete destruction and split the people into factions. The factions have decided to stop the endless war and settle their dispute in the arena. A new virtual bloodsport was created. The Adrenaline tournament. Every faction has a champion, every champion has a chance to fight and the chance to win. Will you take the chance of becoming the next champion of the Adrenaline tournament?

Play a first-person shooter on your gaming table. Grab some ammo, grab a gun, and start shooting. Build up an arsenal for a killer turn. Combat resolution is quick and diceless. And if you get shot, you get faster!

## To start the application follow the order below:
1) Launch the server
2) Launch clients (one per player)

## How to set up clients:
1) Insert in the window that opens the name and the effect phrase of the player
2) Enter the local IPv4 address of the server
3) Enter the port number (The server opens the TCP port on port 5000)
4) Select "GUI" if you want the GUI as interface mode, otherwise, if you do not select anything, the CLI will be selected automatically
5) Select RMI or Socket for communication between client and server

Note: if by chance, due to network problems, a disconnection occurs during the game you must restart the client and re-enter the same name and effect phrase used to connect at the beginning of the game otherwise the server will refuse the connection

##### Server parameters:
The parameters must be entered from terminal after writing the jar file:

java -jar Server.jar timer terminator numberTexes codeArena

The editable parameters are as follows:
- timer: timeout duration in seconds before starting the game. The game will start automatically once the three players are connected.
- Terminator: Indicates whether you want to play with Terminator mode or not. It can only be "true" or "false".
- numberTexes: indicate the number of skulls to put in the arena track(5-8)
- codeArena: indicate which arena to use for the match (1-4)

Note: If the values of some parameters are wrong, the default values are entered:

timer = 10
terminator = false
numbersTexes = 5
codeArena = 4

##### Client parameters:
The parameters must be entered from terminal after writing the jar file:

java -jar Client.jar timerAFK

timerAFK: indicate the duration in milliseconds of the timer that makes the player skip the turn in case it does not make any input in the entered time.
