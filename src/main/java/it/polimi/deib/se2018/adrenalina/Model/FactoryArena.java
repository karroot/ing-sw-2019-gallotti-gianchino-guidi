package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.graph.Graph;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Cysko7927
 * It is a factory that produces an arena indicated by a code and the corresponding room list
 * The Roomlist is a list that contains all the room in an arena
 * The factory is static then it can be used without instantiate it
 * Operation:
 * 1)To use the method getArena to obtain the arena indicating the code and the Gameboard
 * 2)After and only after the method get arena to has been used , to use the method getRooms to obtain the corresponding room list
 * The factory can't be used in other way!!
 */
public class FactoryArena
{

    private static Square s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12; //Variable temporary
    private static boolean done; //Flag to indicate that the method get graph was called before
    private static int cod; //int to memorize the code of arena

    public FactoryArena()
    {

    }


    /**
     *Creates the arena identified by a code unique(In the game there are four arenas possible)
     * @param code code unique that identifies a arena
     * @param board game board to whom the arena belongs
     * @return arena created
     * @exception NullPointerException if board is null
     * @exception IllegalStateException If code isn't between 1 and 4
     */
    public static Graph getArena(int code, GameBoard board) throws NullPointerException,IllegalStateException
    {
        if (board == null)
            throw new  NullPointerException("Board is null");

        cod = code;

        switch (code)//Look the code
        {
            case 1: //If code is 1 built Arena 1
                return builtGraphOfArena1(board);
            case 2://If code is 2 built Arena 2
                return builtGraphOfArena2(board);
            case 3://If code is 3 built Arena 3
                return builtGraphOfArena3(board);
            case 4://If code is 4 built Arena 4
                return builtGraphOfArena4(board);
            default:
                throw new IllegalStateException("Unexpected value: " + cod + "\n Ammessi solo valori tra 1 e 4");
        }
    }

    /**
     *Creates a list of all room that they are in the arena created before by method getArena
     * @param board board that will have to contain the rooms list
     * @return list of rooms created
     * @exception IllegalStateException If code used before by getArena isn't between 1 and 4
     * @exception NullPointerException If board is null or the method getArena has not yet been called
     */
    public static List<Room> getRooms(GameBoard board) throws IllegalStateException,NullPointerException
    {
        if (board == null)
            throw new NullPointerException("Parametro board ricevuto con valore null");
        if (done == false) //If the method getArena has not yet been called
            throw new IllegalStateException("Method getArena non ancora chiamato. Impossibile creare RoomList");
        //Stop

        List<Room> rooms = new  LinkedList<>();//Create a room list empty
        Room temp; //temporary variable
        List<Square> squares = new LinkedList<>();//List of square temporary

        switch (cod) //If the cod that has been used by method getArena..
        {
            case 1: //Is 1 you create the room list of arena 1
            {
                squares = addElements1(board, rooms, squares, s5, s9, s1, s2);
                squares.add(s3);

                temp = new Room(board,squares,ColorRoom.WHITE);

                rooms.add(temp);
                squares = new LinkedList<>();

                squares.add(s4);
                squares.add(s8);

                temp = new Room(board,squares,ColorRoom.YELLOW);

                rooms.add(temp);
                squares = new LinkedList<>();

                squares.add(s6);
                squares.add(s7);

                temp = new Room(board,squares,ColorRoom.PURPLE);

                rooms.add(temp);
                squares = new LinkedList<>();

                squares.add(s10);
                squares.add(s11);

                temp = new Room(board,squares,ColorRoom.BLUE);

                rooms.add(temp);
                squares = new LinkedList<>();

                done = false;

                return rooms;

            }
            case 2://Is 2 you create the room list of arena 2
            {
                squares.add(s2);
                squares.add(s3);

                temp = new Room(board,squares,ColorRoom.WHITE);

                rooms.add(temp);
                squares = new LinkedList<>();

                squares.add(s4);
                squares.add(s8);

                temp = new Room(board,squares,ColorRoom.YELLOW);

                rooms.add(temp);
                squares = new LinkedList<>();

                squares.add(s5);
                squares = addElements1(board, rooms, squares, s6, s7, s9, s10);
                squares.add(s11);

                temp = new Room(board,squares,ColorRoom.BLUE);

                rooms.add(temp);
                squares = new LinkedList<>();

                done = false;

                return rooms;
            }
            case 3://Is 3 you create the room list of arena 3
            {
                squares.add(s2);

                temp = new Room(board,squares,ColorRoom.WHITE);

                rooms.add(temp);
                squares = new LinkedList<>();

                squares.add(s3);
                squares.add(s4);
                squares.add(s8);

                temp = new Room(board,squares,ColorRoom.YELLOW);

                rooms.add(temp);
                squares = new LinkedList<>();

                squares.add(s5);
                squares.add(s6);

                temp = new Room(board,squares,ColorRoom.RED);

                rooms.add(temp);
                squares = new LinkedList<>();

                squares.add(s9);
                squares.add(s10);
                squares.add(s11);

                temp = new Room(board,squares,ColorRoom.BLUE);

                rooms.add(temp);
                squares = new LinkedList<>();

                squares.add(s12);

                temp = new Room(board,squares,ColorRoom.GREEN);

                rooms.add(temp);
                squares = new LinkedList<>();

                done = false;

                return rooms;
            }
            case 4://Is 4 you create the room list of arena 4
            {
                squares.add(s1);
                squares.add(s2);

                temp = new Room(board,squares,ColorRoom.WHITE);


                rooms.add(temp);
                squares = new LinkedList<>();

                squares.add(s3);
                squares.add(s4);
                squares.add(s7);
                squares.add(s8);

                temp = new Room(board,squares,ColorRoom.YELLOW);

                rooms.add(temp);
                squares = new LinkedList<>();

                squares.add(s5);
                squares.add(s9);

                temp = new Room(board,squares,ColorRoom.RED);

                rooms.add(temp);
                squares = new LinkedList<>();

                squares.add(s6);

                temp = new Room(board,squares,ColorRoom.PURPLE);

                rooms.add(temp);
                squares = new LinkedList<>();

                squares.add(s10);
                squares.add(s11);

                temp = new Room(board,squares,ColorRoom.BLUE);

                rooms.add(temp);
                squares = new LinkedList<>();

                squares.add(s12);

                temp = new Room(board,squares,ColorRoom.GREEN);

                rooms.add(temp);
                squares = new LinkedList<>();

                done = false;

                return rooms;
            }
            default://If code is not valid
                throw new IllegalStateException("Unexpected value: " + cod + "\n Ammessi solo valori tra 1 e 4");
                //Stop
        }

    }

    private static List<Square> addElements1(GameBoard board, List<Room> rooms, List<Square> squares, Square s5, Square s9, Square s1, Square s2) {
        Room temp;
        squares.add(s5);
        squares.add(s9);

        temp = new Room(board,squares, ColorRoom.RED);

        rooms.add(temp);

        squares = new LinkedList<>();

        squares.add(s1);
        squares.add(s2);
        return squares;
    }


    //Creates Graph that represent the arena 1 that it is in the second and third page of the manual
    private static Graph builtGraphOfArena1(GameBoard board)
    {
        Graph test = new Graph(); //Variable that will contain the graph

        //Support variables
        Square n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11;
        SideType[] temp = new SideType[4];


        //Construction of all nodes that represent the squares

        temp[0] = SideType.DOOR;
        temp[1] = SideType.OPEN;
        temp[2] = SideType.LIMIT;
        temp[3] = SideType.LIMIT;

        n1 = new AmmoPoint(1,1, board, ColorRoom.WHITE ,temp.clone());
        s1 = n1;

        temp[0] = SideType.DOOR;
        temp[1] = SideType.OPEN;
        temp[2] = SideType.LIMIT;
        temp[3] = SideType.OPEN;

        n2 = new AmmoPoint(2,1,board, ColorRoom.WHITE,temp.clone());
        s2 = n2;

        temp[0] = SideType.WALL;
        temp[1] = SideType.DOOR;
        temp[2] = SideType.LIMIT;
        temp[3] = SideType.OPEN;

        n3 = new AmmoPoint(3,1,board, ColorRoom.WHITE,temp.clone());
        s3 = n3;

        temp[0] = SideType.OPEN;
        temp[1] = SideType.LIMIT;
        temp[2] = SideType.LIMIT;
        temp[3] = SideType.DOOR;

        n4 = new SpawnPoint(4,1,board, ColorRoom.YELLOW,temp.clone());
        s4 = n4;

        temp[0] = SideType.OPEN;
        temp[1] = SideType.WALL;
        temp[2] = SideType.DOOR;
        temp[3] = SideType.LIMIT;

        n5 = new SpawnPoint(1,2,board, ColorRoom.RED,temp.clone());
        s5 = n5;

        temp[0] = SideType.DOOR;
        temp[1] = SideType.OPEN;
        temp[2] = SideType.DOOR;
        temp[3] = SideType.WALL;

        n6 = new AmmoPoint(2,2,board, ColorRoom.PURPLE,temp.clone());
        s6 = n6;

        temp[0] = SideType.DOOR;
        temp[1] = SideType.DOOR;
        temp[2] = SideType.WALL;
        temp[3] = SideType.OPEN;

        n7 = new AmmoPoint(3,2,board, ColorRoom.PURPLE,temp.clone());
        s7 = n7;

        temp[0] = SideType.LIMIT;
        temp[1] = SideType.LIMIT;
        temp[2] = SideType.OPEN;
        temp[3] = SideType.DOOR;

        n8 = new AmmoPoint(4,2,board, ColorRoom.YELLOW,temp.clone());
        s8 = n8;

        temp[0] = SideType.LIMIT;
        temp[1] = SideType.DOOR;
        temp[2] = SideType.OPEN;
        temp[3] = SideType.LIMIT;

        n9 = new AmmoPoint(1,3,board, ColorRoom.RED,temp.clone());
        s9 = n9;

        temp[0] = SideType.LIMIT;
        temp[1] = SideType.OPEN;
        temp[2] = SideType.DOOR;
        temp[3] = SideType.DOOR;

        n10 = new AmmoPoint(2,3,board, ColorRoom.BLUE,temp.clone());
        s10 = n10;

        temp[0] = SideType.LIMIT;
        temp[1] = SideType.LIMIT;
        temp[2] = SideType.DOOR;
        temp[3] = SideType.OPEN;

        n11 = new SpawnPoint(3,3,board, ColorRoom.BLUE,temp.clone());
        s11 = n11;

        //Construction graph with all nodes just now created and all edges

        test.addNode(n1);
        test.addNode(n2);
        test.addNode(n3);
        test.addNode(n4);
        test.addNode(n5);
        test.addNode(n6);
        test.addNode(n7);
        test.addNode(n8);
        test.addNode(n9);
        test.addNode(n10);
        test.addNode(n11);

        test.addEdge(n1,n5,SideType.DOOR);
        test.addEdge(n1,n2,SideType.OPEN);

        test.addEdge(n2,n3,SideType.OPEN);
        test.addEdge(n2,n6,SideType.DOOR);

        test.addEdge(n3,n4,SideType.DOOR);
        test.addEdge(n3,n7,SideType.WALL);

        test.addEdge(n4,n8,SideType.OPEN);

        test.addEdge(n5,n6,SideType.WALL);
        test.addEdge(n5,n9,SideType.OPEN);

        test.addEdge(n6,n10,SideType.DOOR);
        test.addEdge(n6,n7,SideType.OPEN);

        test.addEdge(n7,n11,SideType.DOOR);
        test.addEdge(n7,n8,SideType.DOOR);

        test.addEdge(n9,n10,SideType.DOOR);

        test.addEdge(n10,n11,SideType.OPEN);

        done = true;

        return test;

    }

    //Creates Graph that represent the arena 2 that it is in the second page of the manual the first to the left
    private static Graph builtGraphOfArena2(GameBoard board)
    {
        Graph test = new Graph(); //Variable that will contain the graph

        //Support variables
        Square n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12;
        SideType[] temp = new SideType[4];


        //Construction of all nodes that represent the squares



        temp[0] = SideType.DOOR;
        temp[1] = SideType.OPEN;
        temp[2] = SideType.LIMIT;
        temp[3] = SideType.LIMIT;

        n2 = new AmmoPoint(2,1,board, ColorRoom.WHITE,temp.clone());
        s2 = n2;

        temp[0] = SideType.WALL;
        temp[1] = SideType.DOOR;
        temp[2] = SideType.LIMIT;
        temp[3] = SideType.OPEN;

        n3 = new AmmoPoint(3,1,board, ColorRoom.WHITE,temp.clone());
        s3 = n3;

        temp[0] = SideType.OPEN;
        temp[1] = SideType.LIMIT;
        temp[2] = SideType.LIMIT;
        temp[3] = SideType.DOOR;

        n4 = new SpawnPoint(4,1,board, ColorRoom.YELLOW,temp.clone());
        s4 = n4;

        temp[0] = SideType.DOOR;
        temp[1] = SideType.OPEN;
        temp[2] = SideType.LIMIT;
        temp[3] = SideType.LIMIT;

        n5 = new SpawnPoint(1,2,board, ColorRoom.RED,temp.clone());
        s5 = n5;

        temp[0] = SideType.WALL;
        temp[1] = SideType.OPEN;
        temp[2] = SideType.DOOR;
        temp[3] = SideType.OPEN;

        n6 = new AmmoPoint(2,2,board, ColorRoom.RED,temp.clone());
        s6 = n6;

        temp[0] = SideType.DOOR;
        temp[1] = SideType.DOOR;
        temp[2] = SideType.WALL;
        temp[3] = SideType.OPEN;

        n7 = new AmmoPoint(3,2,board, ColorRoom.RED,temp.clone());
        s7 = n7;

        temp[0] = SideType.LIMIT;
        temp[1] = SideType.LIMIT;
        temp[2] = SideType.OPEN;
        temp[3] = SideType.DOOR;

        n8 = new AmmoPoint(4,2,board, ColorRoom.YELLOW,temp.clone());
        s8 = n8;

        temp[0] = SideType.LIMIT;
        temp[1] = SideType.OPEN;
        temp[2] = SideType.DOOR;
        temp[3] = SideType.LIMIT;

        n9 = new AmmoPoint(1,3,board, ColorRoom.BLUE,temp.clone());
        s9 = n9;

        temp[0] = SideType.LIMIT;
        temp[1] = SideType.OPEN;
        temp[2] = SideType.WALL;
        temp[3] = SideType.OPEN;

        n10 = new AmmoPoint(2,3,board, ColorRoom.BLUE,temp.clone());
        s10 = n10;

        temp[0] = SideType.LIMIT;
        temp[1] = SideType.LIMIT;
        temp[2] = SideType.DOOR;
        temp[3] = SideType.OPEN;

        n11 = new SpawnPoint(3,3,board, ColorRoom.BLUE,temp.clone());
        s11 = n11;

        //Construction graph with all nodes just now created and all edges

        test.addNode(n2);
        test.addNode(n3);
        test.addNode(n4);
        test.addNode(n5);
        test.addNode(n6);
        test.addNode(n7);
        test.addNode(n8);
        test.addNode(n9);
        test.addNode(n10);
        test.addNode(n11);


        test.addEdge(n2,n3,SideType.OPEN);
        test.addEdge(n2,n6,SideType.DOOR);

        test.addEdge(n3,n4,SideType.DOOR);
        test.addEdge(n3,n7,SideType.WALL);

        test.addEdge(n4,n8,SideType.OPEN);

        test.addEdge(n5,n6,SideType.OPEN);
        test.addEdge(n5,n9,SideType.DOOR);

        test.addEdge(n6,n10,SideType.WALL);
        test.addEdge(n6,n7,SideType.OPEN);

        test.addEdge(n7,n11,SideType.DOOR);
        test.addEdge(n7,n8,SideType.DOOR);

        test.addEdge(n9,n10,SideType.OPEN);

        test.addEdge(n10,n11,SideType.OPEN);

        done = true;

        return test;
    }

    //Creates Graph that represent the arena 2 that it is in the second page of the manual the second to the left
    private static Graph builtGraphOfArena3(GameBoard board)
    {
        Graph test = new Graph(); //Variable that will contain the graph

        //Support variables
        Square n12,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11;
        SideType[] temp = new SideType[4];


        //Construction of all nodes that represent the squares

        temp[0] = SideType.DOOR;
        temp[1] = SideType.DOOR;
        temp[2] = SideType.LIMIT;
        temp[3] = SideType.LIMIT;

        n2 = new AmmoPoint(2,1,board, ColorRoom.WHITE,temp.clone());
        s2 = n2;

        temp[0] = SideType.OPEN;
        temp[1] = SideType.OPEN;
        temp[2] = SideType.LIMIT;
        temp[3] = SideType.DOOR;

        n3 = new AmmoPoint(3,1,board, ColorRoom.YELLOW,temp.clone());
        s3 = n3;

        temp[0] = SideType.OPEN;
        temp[1] = SideType.LIMIT;
        temp[2] = SideType.LIMIT;
        temp[3] = SideType.OPEN;

        n4 = new SpawnPoint(4,1,board, ColorRoom.YELLOW,temp.clone());
        s4 = n4;

        temp[0] = SideType.DOOR;
        temp[1] = SideType.OPEN;
        temp[2] = SideType.LIMIT;
        temp[3] = SideType.LIMIT;

        n5 = new SpawnPoint(1,2,board, ColorRoom.RED,temp.clone());
        s5 = n5;

        temp[0] = SideType.WALL;
        temp[1] = SideType.WALL;
        temp[2] = SideType.DOOR;
        temp[3] = SideType.OPEN;

        n6 = new AmmoPoint(2,2,board, ColorRoom.RED,temp.clone());
        s6 = n6;

        temp[0] = SideType.DOOR;
        temp[1] = SideType.OPEN;
        temp[2] = SideType.OPEN;
        temp[3] = SideType.WALL;

        n7 = new AmmoPoint(3,2,board, ColorRoom.YELLOW,temp.clone());
        s7 = n7;

        temp[0] = SideType.DOOR;
        temp[1] = SideType.LIMIT;
        temp[2] = SideType.OPEN;
        temp[3] = SideType.OPEN;

        n8 = new AmmoPoint(4,2,board, ColorRoom.YELLOW,temp.clone());
        s8 = n8;

        temp[0] = SideType.LIMIT;
        temp[1] = SideType.OPEN;
        temp[2] = SideType.DOOR;
        temp[3] = SideType.LIMIT;

        n9 = new AmmoPoint(1,3,board, ColorRoom.BLUE,temp.clone());
        s9 = n9;

        temp[0] = SideType.LIMIT;
        temp[1] = SideType.OPEN;
        temp[2] = SideType.WALL;
        temp[3] = SideType.OPEN;

        n10 = new AmmoPoint(2,3,board, ColorRoom.BLUE,temp.clone());
        s10 = n10;

        temp[0] = SideType.LIMIT;
        temp[1] = SideType.DOOR;
        temp[2] = SideType.DOOR;
        temp[3] = SideType.OPEN;

        n11 = new SpawnPoint(3,3,board, ColorRoom.BLUE,temp.clone());
        s11 = n11;

        temp[0] = SideType.LIMIT;
        temp[1] = SideType.LIMIT;
        temp[2] = SideType.DOOR;
        temp[3] = SideType.DOOR;

        n12 = new AmmoPoint(4,3, board, ColorRoom.GREEN ,temp.clone());
        s12 = n12;

        //Construction graph with all nodes just now created and all edges

        test.addNode(n12);
        test.addNode(n2);
        test.addNode(n3);
        test.addNode(n4);
        test.addNode(n5);
        test.addNode(n6);
        test.addNode(n7);
        test.addNode(n8);
        test.addNode(n9);
        test.addNode(n10);
        test.addNode(n11);


        test.addEdge(n2,n3,SideType.DOOR);
        test.addEdge(n2,n6,SideType.DOOR);

        test.addEdge(n3,n4,SideType.OPEN);
        test.addEdge(n3,n7,SideType.OPEN);

        test.addEdge(n4,n8,SideType.OPEN);

        test.addEdge(n5,n6,SideType.OPEN);
        test.addEdge(n5,n9,SideType.DOOR);

        test.addEdge(n6,n10,SideType.WALL);
        test.addEdge(n6,n7,SideType.WALL);

        test.addEdge(n7,n11,SideType.DOOR);
        test.addEdge(n7,n8,SideType.OPEN);

        test.addEdge(n8,n12,SideType.DOOR);

        test.addEdge(n9,n10,SideType.OPEN);

        test.addEdge(n10,n11,SideType.OPEN);

        test.addEdge(n11,n12,SideType.DOOR);

        done = true;

        return test;

    }

    //Creates Graph that represent the arena 2 that it is in the second page of the manual the third to the left
    private static Graph builtGraphOfArena4(GameBoard board)
    {
        Graph test = new Graph(); //Variable that will contain the graph

        //Support variables
        Square n12,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n1;
        SideType[] temp = new SideType[4];


        //Construction of all nodes that represent the squares

        temp[0] = SideType.DOOR;
        temp[1] = SideType.OPEN;
        temp[2] = SideType.LIMIT;
        temp[3] = SideType.LIMIT;

        n1 = new AmmoPoint(1,1,board, ColorRoom.WHITE,temp.clone());
        s1 = n1;

        temp[0] = SideType.DOOR;
        temp[1] = SideType.DOOR;
        temp[2] = SideType.LIMIT;
        temp[3] = SideType.OPEN;

        n2 = new AmmoPoint(2,1,board, ColorRoom.WHITE,temp.clone());
        s2 = n2;

        temp[0] = SideType.OPEN;
        temp[1] = SideType.OPEN;
        temp[2] = SideType.LIMIT;
        temp[3] = SideType.DOOR;

        n3 = new AmmoPoint(3,1,board, ColorRoom.YELLOW,temp.clone());
        s3 = n3;

        temp[0] = SideType.OPEN;
        temp[1] = SideType.LIMIT;
        temp[2] = SideType.LIMIT;
        temp[3] = SideType.OPEN;

        n4 = new SpawnPoint(4,1,board, ColorRoom.YELLOW,temp.clone());
        s4 = n4;

        temp[0] = SideType.OPEN;
        temp[1] = SideType.WALL;
        temp[2] = SideType.DOOR;
        temp[3] = SideType.LIMIT;

        n5 = new SpawnPoint(1,2,board, ColorRoom.RED,temp.clone());
        s5 = n5;

        temp[0] = SideType.DOOR;
        temp[1] = SideType.WALL;
        temp[2] = SideType.DOOR;
        temp[3] = SideType.WALL;

        n6 = new AmmoPoint(2,2,board, ColorRoom.PURPLE,temp.clone());
        s6 = n6;

        temp[0] = SideType.DOOR;
        temp[1] = SideType.OPEN;
        temp[2] = SideType.OPEN;
        temp[3] = SideType.WALL;

        n7 = new AmmoPoint(3,2,board, ColorRoom.YELLOW,temp.clone());
        s7 = n7;

        temp[0] = SideType.DOOR;
        temp[1] = SideType.LIMIT;
        temp[2] = SideType.OPEN;
        temp[3] = SideType.OPEN;

        n8 = new AmmoPoint(4,2,board, ColorRoom.YELLOW,temp.clone());
        s8 = n8;

        temp[0] = SideType.LIMIT;
        temp[1] = SideType.DOOR;
        temp[2] = SideType.OPEN;
        temp[3] = SideType.LIMIT;

        n9 = new AmmoPoint(1,3,board, ColorRoom.RED,temp.clone());
        s9 = n9;

        temp[0] = SideType.LIMIT;
        temp[1] = SideType.OPEN;
        temp[2] = SideType.DOOR;
        temp[3] = SideType.DOOR;

        n10 = new AmmoPoint(2,3,board, ColorRoom.BLUE,temp.clone());
        s10 = n10;

        temp[0] = SideType.LIMIT;
        temp[1] = SideType.DOOR;
        temp[2] = SideType.DOOR;
        temp[3] = SideType.OPEN;

        n11 = new SpawnPoint(3,3,board, ColorRoom.BLUE,temp.clone());
        s11 = n11;

        temp[0] = SideType.LIMIT;
        temp[1] = SideType.LIMIT;
        temp[2] = SideType.DOOR;
        temp[3] = SideType.DOOR;

        n12 = new AmmoPoint(4,3, board, ColorRoom.GREEN ,temp.clone());
        s12 = n12;

        //Construction graph with all nodes just now created and all edges

        test.addNode(n12);
        test.addNode(n2);
        test.addNode(n3);
        test.addNode(n4);
        test.addNode(n5);
        test.addNode(n6);
        test.addNode(n7);
        test.addNode(n8);
        test.addNode(n9);
        test.addNode(n10);
        test.addNode(n11);
        test.addNode(n1);

        test.addEdge(n1,n2,SideType.OPEN);
        test.addEdge(n1,n5,SideType.DOOR);

        test.addEdge(n2,n3,SideType.DOOR);
        test.addEdge(n2,n6,SideType.DOOR);

        test.addEdge(n3,n4,SideType.OPEN);
        test.addEdge(n3,n7,SideType.OPEN);

        test.addEdge(n4,n8,SideType.OPEN);

        test.addEdge(n5,n6,SideType.WALL);
        test.addEdge(n5,n9,SideType.OPEN);

        test.addEdge(n6,n10,SideType.DOOR);
        test.addEdge(n6,n7,SideType.WALL);

        test.addEdge(n7,n11,SideType.DOOR);
        test.addEdge(n7,n8,SideType.OPEN);

        test.addEdge(n8,n12,SideType.DOOR);

        test.addEdge(n9,n10,SideType.DOOR);

        test.addEdge(n10,n11,SideType.OPEN);

        test.addEdge(n11,n12,SideType.DOOR);

        done = true;

        return test;

    }
}
