package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;

import java.util.*;
import java.util.stream.Collectors;

public class MethodsWeapons {
    /**
     * Return all players reachable by a square with coordinate x and y that they are at a specified distance
     *
     * @param square   starting square
     * @param distance max distance to consider for
     * @return players reachable at the specified distance
     */
    public static Set<Player> playersReachable(Square square, int distance) {
        Set<Square> squares = square.getGameBoard().getArena().squareReachableNoWall(square.getX(), square.getY(), distance);//Obtain the square reachable

        Set<Player> result = new LinkedHashSet<>();

        for (Square c : squares)//For each square obtained you take all player in these squares
        {
            result.addAll(c.getPlayerList());
        }

        return result; //Return all player reachable
    }

    /**
     * Check if there is a target in each square exactly at distance 1 from a player indicated
     *
     * @param player player where it is taken the starting square
     * @return true if there are the target
     */
    public static boolean thereIsAPlayerInEachSquare(Player player) {

        //Obtain the square reachable to distance 1
        Set<Square> squares = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1);

        squares.remove(player.getSquare()); //Remove the starting square


        for (Square x : squares) {
            if (x.getPlayerList().isEmpty())//If there is a square without players
                return false; //Return false
        }

        return true; //Else return false
    }

    /**
     * Check if the square "e" is located to North or East or South or West of the square with coordinates x and y
     * Check max 2 moves
     *
     * @param e starting square
     * @param x coordinate x of the square to check
     * @param y coordinate y of the square to check
     */

    public static boolean checkSquareOneDirectionTwoMoves(Square e, int x, int y) {
        if ((x == e.getX() + 1 && y == e.getY()) || (x == e.getX() + 2 && y == e.getY()))//If the square (x,y) is at east
            return true;
        else if ((x == e.getX() - 1 && y == e.getY()) || (x == e.getX() - 2 && y == e.getY()))//If the square (x,y) is at west
            return true;
        else if ((x == e.getX() && y == e.getY() + 1) || (x == e.getX() && y == e.getY() + 2))//If the square (x,y) is at north
            return true;
        else
            return (x == e.getX() && y == e.getY() - 1) || (x == e.getX() && y == e.getY() - 2);//If the square (x,y) is at south
    }

    //If the square (x,y) is at north of the square e (max 2 moves)
    public static boolean checkSquareNorth(Square e, int x, int y) {
        return (x == e.getX() && y == e.getY() + 1) || (x == e.getX() && y == e.getY() + 2);
    }

    //If the square (x,y) is at south of the square e (max 2 moves)
    public static boolean checkSquareSouth(Square e, int x, int y) {
        return (x == e.getX() && y == e.getY() - 1) || (x == e.getX() && y == e.getY() - 2);
    }

    //If the square (x,y) is at east of the square e (max 2 moves)
    public static boolean checkSquareEast(Square e, int x, int y) {
        return (x == e.getX() + 1 && y == e.getY()) || (x == e.getX() + 2 && y == e.getY());
    }

    //If the square (x,y) is at west of the square e (max 2 moves)
    public static boolean checkSquareWest(Square e, int x, int y) {
        return (x == e.getX() - 1 && y == e.getY()) || (x == e.getX() - 2 && y == e.getY());
    }

    public static List<Room> roomsThatIsee(Player player) {
        Set<Square> temp = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1); //Obtain the square to distance 1

        Set<ColorRoom> colors = new HashSet<>(); //Create a set of colorRoom empty

        for (Square c : temp) //Obtain the colors of the square to distance 1
        {
            colors.add(c.getColor());
        }

        return player.getSquare().getGameBoard().getRoomList()
                .stream()
                .filter(room -> colors.contains(room.getColor()))
                .collect(Collectors.toList());
    }

    public static Set<Square> squareThatSee(Player player) throws NullPointerException {
        if (player.getSquare().getGameBoard() == null)
            throw new NullPointerException("board ha valore nullo");


        Set<Square> temp = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1); //Obtain the square to distance 1

        Set<ColorRoom> colors = new HashSet<>(); //Create a set of colorRoom empty

        for (Square c : temp) //Obtain the colors of the square to distance 1
        {
            colors.add(c.getColor());
        }

        for (Room v : player.getSquare().getGameBoard().getRoomList())//For each room in the arena
        {
            if (colors.contains(v.getColor()))//If the color of the room is in the set of color founded before
                temp.addAll(v.getSquareList());//Add all player in this room to the output

        }

        return temp; //Return the set of all squares

    }

    /**
     * @param squareList
     * @return /todo
     */
    public static boolean areSquareISeeNotMineNotEmpty(Player player, List<Square> squareList) {
        squareList.remove(player.getSquare());

        for (Square squareIterate : squareList) {
            if (!squareIterate.getPlayerList().isEmpty())
                return true;
        }

        return false;

    }

    /**
     * /todo
     *
     * @param player
     * @return
     */
    public static boolean isThereAPlayerAtDistance1(Player player) {
        Set<Square> squareSet = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1);
        squareSet.remove(player.getSquare());

        for (Square squareIterate : squareSet) {
            if (!squareIterate.getPlayerList().isEmpty()) //if there is at least 1 player at distance reachable 1
                return true;
        }

        return false;
    }

    /**
     * @param player
     * @return
     */
    public static List<Player> playersAtDistance1(Player player) {
        Set<Square> squareSet = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1);
        squareSet.remove(player.getSquare());
        List<Player> playerList = new ArrayList<>();

        for (Square squareIterate : squareSet) {
            playerList.addAll(squareIterate.getPlayerList());
        }

        return playerList;
    }


    /**
     * @param squareStart
     * @param squareTarget
     * @return
     */
    public static Square squareBehindThis(Square squareStart, Square squareTarget) {

        AmmoPoint ammoPointTemp = new AmmoPoint(99, 99, null, null, null);
        Square squareReturn;

        if (squareTarget.getX() == squareStart.getX() && squareTarget.getY() == squareStart.getY() + 1) {

            if (squareTarget.getSide()[0] == SideType.DOOR || squareTarget.getSide()[0] == SideType.OPEN) {
                ammoPointTemp.setX(squareTarget.getX());
                ammoPointTemp.setY(squareTarget.getY() + 1);
            }
        }

        if (squareTarget.getX() == squareStart.getX() + 1 && squareTarget.getY() == squareStart.getY()) {
            //isE = true;

            if (squareTarget.getSide()[1] == SideType.DOOR || squareTarget.getSide()[1] == SideType.OPEN) {
                ammoPointTemp.setX(squareTarget.getX() + 1);
                ammoPointTemp.setY(squareTarget.getY());
            }
        }


        if (squareTarget.getX() == squareStart.getX() && squareTarget.getY() == squareStart.getY() - 1) {

            //isS = true;
            if (squareTarget.getSide()[2] == SideType.DOOR || squareTarget.getSide()[2] == SideType.OPEN) {
                ammoPointTemp.setX(squareTarget.getX());
                ammoPointTemp.setY(squareTarget.getY() - 1);
            }
        }

        if (squareTarget.getX() == squareStart.getX() - 1 && squareTarget.getY() == squareStart.getY()) {
            //isW = true;
            if (squareTarget.getSide()[3] == SideType.DOOR || squareTarget.getSide()[3] == SideType.OPEN) {
                ammoPointTemp.setX(squareTarget.getX() - 1);
                ammoPointTemp.setY(squareTarget.getY());
            }
        }

        if (ammoPointTemp.getX() != 99 && ammoPointTemp.getY() != 99) {
            try {
                squareReturn = squareStart.getGameBoard().getArena().getSquare(ammoPointTemp.getX(), ammoPointTemp.getY());
                return squareReturn;
            } catch (SquareNotInGameBoard squareNotInGameBoard) {
                squareNotInGameBoard.printStackTrace();
            }

        }

        return null;

        //funzione ricerca square? non devo ritornare QUESTO, devo cercare nel grafo lo square con queste x e queste y e ritornare quello.
        // getgameboard.getarena.getsquare(x,y)
        // Se ritorno questo non è linkato a nulla!!

    }

    public static void moveTarget(Player player, int x, int y) throws IllegalArgumentException, NullPointerException {

        if (player == null)
            throw new NullPointerException("Parametro player o arena nullo");

        Square square;

        try //Obtain the square with coordinates x and y
        {
            square = player.getSquare().getGameBoard().getArena().getSquare(x, y); //obtain the square with coordinate x and y
        } catch (SquareNotInGameBoard e) //If coordinate are not valid
        {
            System.out.println(e);

            throw new IllegalArgumentException("Cordinate non valide");//Launch exceptions
        }

        player.getSquare().getRoom().removePlayerFromRoomList(player); //Remove player from room

        player.getSquare().removePlayer(player);//Remove the player from his square

        player.setSquare(square);//Add the new square on player

        square.addPlayer(player); //Add the player on the new square

        player.getSquare().getRoom().updatePlayerRoomList(); //Update the list of player inside
    }
}


