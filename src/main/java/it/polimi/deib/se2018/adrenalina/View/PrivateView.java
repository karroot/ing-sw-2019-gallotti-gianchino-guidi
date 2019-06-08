package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.Controller.Reload;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.communication_message.*;
import it.polimi.deib.se2018.adrenalina.communication_message.message_asking_controller.*;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.SquareImmutable;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.UpdateModel;

import java.rmi.RemoteException;
import java.util.stream.Collectors;

public class PrivateView extends Observable<ResponseInput> implements Observer<RequestInput>
{

    Terminal terminal;
    private boolean firstTurn;
    private final Object msg = new Object();
    private int cont;
    RequestInput messageBuffer;
    NetworkHandlerRMI networkHandlerRMI = null;
    NetworkHandlerSocket networkHandlerSocket = null;

    /**
     * it Creates The private view and depending on the user's choices creates a network handler(RMI or Socket)
     * @param technoloy it represents the choice of the user (RMI or Socket)
     * @param ip ip address of the server
     * @param port port tcp of the server
     * @param gui it represent if the user chooses the gui or not
     * @throws Exception if there are problem with the network handler
     */
    public PrivateView(int technoloy,String ip,int port,boolean gui) throws Exception
    {
        if (technoloy == 1)
        {
            networkHandlerRMI = new NetworkHandlerRMI(this,ip,port);
            register(networkHandlerRMI);
        }
        else
        {
            networkHandlerSocket = new NetworkHandlerSocket(ip,port,this);

            register(networkHandlerSocket);
        }

        //todo creazione  del terminale GUI o CLI

    }



    public void showMenu()
    {

    }

    /**
     * Methods that create a new game
     */
    public void newGame()
    {
        firstTurn = true;
    }

    public void showBoard()
    {
        terminal.showBoard();
    }

    /**
     * Method that starts the execution of a round for this player
     */
    public void startRound()
    {
        if (firstTurn)
        {
         //Chiedi al controller di pescare due powerUP
            try
            {
                notify(new AskGrabPowerUp());
                notify(new AskGrabPowerUp());
            }
            catch (Exception e)
            {
                terminal.showError("Sei stato disconesso : Turno interroto");
                terminal.showError(e.getMessage());
                Thread.currentThread().interrupt();
            }

         //Chiedi al giocatore quale carta PowerUp usare per il respawn
            showPowerUp();

            int choice = selectPowerUp();

            try
            {
                notify(new AskUsePowerUpRespawn());
            }
            catch (Exception e)
            {
                terminal.showError("Sei stato disconesso : Turno interroto");
                terminal.showError(e.getMessage());
                Thread.currentThread().interrupt();
            }

            firstTurn = false;
        }

        //#######Player can use a teleporter or a newton###############

        powerUpTeleportOrNewton();

        //#######Player must choose two actions###############

        cont = 0;


        while (cont < 2) //The
        {
            //Show the actions
            showAction();
            int choice = selectAction(); //Do to selects an action to the plaer

            switch (choice)
            {
                case 1: //if the user chose "Move"
                    moveAction();
                    break;
                case 2://if the user chose "Grab"
                    grabAction();
                    break;
                case 3:
                    shootAction();//If the user chose "Shoot"
                    break;
            }

            //#######Player can use a teleporter or a newton###############

            powerUpTeleportOrNewton();
        }

        //######## Using targeting scope ######### //todo Chiedere agli altri


        //########Reload weapons###########

        reloading();

    }

    /**
     * This method being called by Network Handler to ask at the user which powerUp to use for the respawn
     */
    public void startRespawn()//todo
    {
        //Chiedi al controller per pescare due powerUp
        //Mostra i powerUp nuovi(dialog)
        //Mostra e chiedi quale powerUp usare
        //Informa il controller su quale powerUp è stato scelto
    }

    /**
     * This method show all the powerUp to the user
     */
    public void showPowerUp()
    {
        terminal.showPowerUp();
    }

    /**
     * Ask at the player to select a powerUp
     * @return integer that represent the choice of the user
     */
    public int selectPowerUp()
    {
        return terminal.selectPowerUp();
    }

    public void showWeapons()
    {

    }

    public int selectWeapon()
    {
        return 0;

    }


    /**
     * Ask at the user if he wants to use his Teleporter or Newton
     * @return true if the user said yes
     */
    public boolean askPowerUPTeleOrNew()
    {
        return terminal.askPowerUPTeleOrNew();
    }

    /**
     * Ask at the user if he wants to reload his weapons
     * @return true if the user said yes
     */
    public boolean askReloading()
    {
        return terminal.askReloading();
    }

    /**
     * Show at the user the three action of a turn
     */
    public void showAction()
    {
        terminal.showAction();
    }

    /**
     * Ask at the user which action wants to do
     * @return integer that represents the choice of the user
     */
    public int selectAction()
    {
        return terminal.selectAction();
    }

    public void startFrenesy()
    {

    }

    public void showFinalScore(String messageWithFinalScore)
    {
        terminal.showFinalScore(messageWithFinalScore);
    }

    public void showMessage(String message)
    {
        terminal.showMessage(message);
    }

    public void showError(String message)
    {
        terminal.showError(message);
    }

    /**
     * Thi method being called by Network Handler to ask at view to update its copy of the model to show
     * at the user and after shows the game board updated
     * @param message message that contains the copy immutable of the model that arrives from server
     */
    public void updateModelCopy(UpdateModel message)
    {
        terminal.setData(message);
        terminal.showBoard();
    }

    /**
     * This method being used by network handler to save the message of request input that is arrived now from server with
     * a notify.
     * The message being saved in the buffer and awake the thread of startRound that it was waiting a message of request
     * @param message message that will use to ask the inputs at the user
     */
    @Override
    public void update(RequestInput message) throws Exception
    {
        synchronized (msg)
        {
            messageBuffer = message;
            msg.notifyAll();
        }

    }

    /**
     * This method puts the process on hold that arrives a message of request input
     * and after return it
     * @return message of request input that the network handler passed at view with a notify
     */
    private RequestInput getMessageFromNetwHandl()
    {
        synchronized (msg)
        {
            while (messageBuffer == null)
            {
                try {
                    msg.wait();
                }
                catch (InterruptedException e)
                {
                    terminal.showError("Sei stato disconesso");
                    Thread.currentThread().interrupt();
                }
            }

            RequestInput message = messageBuffer;
            messageBuffer = null;
            return message;
        }
    }

    //#################Private Methods######################################à

    //Code that handle the move action
    private void moveAction()
    {
        try
        {
            notify(new AskMoveAround());//Notify at controller that the player wants to move

            RequestInput messageRequest = getMessageFromNetwHandl(); //Obtain the request message
            messageRequest.printActionsAndReceiveInput();//Ask the input asked by controller
            ResponseInput responseForController = messageRequest.generateResponseMessage();//Obtain the response Message

            notify(responseForController);//Send the message at controller
            cont++;
        }
        catch (Exception e)
        {
            terminal.showError("Sei stato disconesso : Turno interroto");
            terminal.showError(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    //Code that handle the grab action
    private void grabAction()
    {
        try
        {
            notify(new AskGrab());//Notify at controller that the player wants to grab

            RequestInput messageRequest = getMessageFromNetwHandl(); //Obtain the request message with the squares
            //where the player can grab
            messageRequest.printActionsAndReceiveInput();//Ask the input asked by controller
            ResponseInput responseForController = messageRequest.generateResponseMessage();//Obtain the response Message

            ResponseGrabStuff temp = (ResponseGrabStuff) responseForController;

            temp.getTargetSquareToGo(); //Save the response message with the square chose

            notify(responseForController);//Send the message at controller


            int x = MethodsWeapons.getYFromString(temp.getTargetSquareToGo()); //Obtain the coordinates of the square chosen by the user to grab
            int y = MethodsWeapons.getXFromString(temp.getTargetSquareToGo());


            SquareImmutable square = terminal.getData()
                    .getDataOfAllSquare().stream()
                    .filter(squareImmutable -> x == squareImmutable.getX() && y == squareImmutable.getY())
                    .collect(Collectors.toList()).get(0);//Obtain the copy of the square immutable

            //Non c'è bisogno di controllare se la lista delle armi è vuota
            //Il player non può scegliere uno spawn Point dove non può raccogliere
            if(square.isSpawnPoint())//if the square chosen by the user to grab is a spawn Point
            {
                messageRequest = getMessageFromNetwHandl(); //Obtain the request message with the weapons //message (RequestShootPeople)
                //that the player can grab
                messageRequest.printActionsAndReceiveInput();//Ask the inputs asked by controller
                responseForController = messageRequest.generateResponseMessage();//Obtain the response Message
                notify(responseForController);//Send the message at controller with the weapon chosen by player


                //message (RequestShootPeople)
                //Richiesta sostituzione arma se ce ne sono già 3
            }


            cont++;
        }
        catch (Exception e)
        {
            terminal.showError("Sei stato disconesso : Turno interroto");
            terminal.showError(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    //Code that handle the shoot action
    private void shootAction()
    {
        try
        {

            //todo Chiedere se si vogliono usare i powerUp per aumentare le munizioni

            notify(new AskShoot());//Notify at controller that the player wants to shoot

            RequestInput messageRequest = getMessageFromNetwHandl(); //Obtain the request message that ask which
            //weapons the user wants to use

            RequestShootPeople temp = (RequestShootPeople) messageRequest;

            if (temp.getWeaponCardsName().isEmpty()) //If the player has not weapons , interrupts the action
                return;

            messageRequest.printActionsAndReceiveInput();//Ask the inputs asked by controller
            ResponseInput responseForController = messageRequest.generateResponseMessage();//Obtain the response Message with the weapon chosen
            //by user

            notify(responseForController);//Send the message at controller

            //Using the weapon########

            messageRequest = getMessageFromNetwHandl(); //Obtain the request message that ask at user
            //all input needed to use the weapon

            messageRequest.printActionsAndReceiveInput();//Ask the inputs asked by controller
            responseForController = messageRequest.generateResponseMessage();//Obtain the response Message with all input
            // inserted by user

            notify(responseForController);//Send the message at controller

            cont++;
        }
        catch (Exception e)
        {
            terminal.showError("Sei stato disconesso : Turno interroto");
            terminal.showError(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    //Code that handle the action reload
    private void reloading()
    {
        boolean choice = askReloading(); //Ask if the player wants to reload

        if (choice)
        {
            try
            {   //Il controller invia un'arma da ricaricare ogni volta
                //Se le armi sono finite il controller invia un messaggio particolare(End) : esco dal ciclo

                notify(new AskReload());//Notify at controller that the player wants to reload

                RequestInput messageRequest = getMessageFromNetwHandl(); //Obtain the request message

                while (! (messageRequest instanceof /*End*/)) //If the message contains a
                // weapons that it can be reloaded
                {
                    messageRequest.printActionsAndReceiveInput();//Ask the input asked by controller
                    ResponseInput responseForController = messageRequest.generateResponseMessage();//Obtain the response Message with
                    //with the response (yes or no)
                    notify(responseForController);//Send the message at controller
                    messageRequest = getMessageFromNetwHandl(); //Obtain the request message
                }
                //If the message is an "End" then there aren't more weapons to reload
            }
            catch (Exception e)
            {
                terminal.showError("Sei stato disconesso : Turno interroto");
                terminal.showError(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    //Code that handle the use of Teleporter Or Newton
    private void powerUpTeleportOrNewton()
    {

        //Ask to use the powerUp (Teleporter or Newton) //todo Discutere con gabriele su l'uso dei powerUP

        if (askPowerUPTeleOrNew()) //If the player has some powerUPs (Teleporter or Newton) and says that he wants to use them
        {
            try
            {
                notify(new AskPowerUPTeleOrNew());//Notify at controller that the player wants to use the powerUps
                //Se il player ha powerUP(Teleporter or Newton)

                RequestInput messageRequest = getMessageFromNetwHandl();//Obtain the request message

                while (! (messageRequest instanceof /*End*/)) //if the message contains a request to use the powerUp //todo vedere se gabriele l'ha fatto
                {
                    messageRequest.printActionsAndReceiveInput();//Ask the input asked by controller
                    ResponseInput responseForController = messageRequest.generateResponseMessage();//Obtain the response Message with
                    //with the response (yes or no) and the inputs needed
                    notify(responseForController);//Send the message of response at controller
                    messageRequest = getMessageFromNetwHandl();//Obtain the request message
                }
                //If the message is an "End" then there aren't more powerUps to ask
            }
            catch (Exception e)
            {
                terminal.showError("Sei stato disconesso : Turno interroto");
                terminal.showError(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }


        //Se il player ha powerUP(Teleporter or Newton)
        //Chiedi al player se vuole usare il powerUP
        //Se risponde si
        //askTeleportOrNewton
        //Ricevi la request
        //receive inputs
        //generate Response Message
        //Ciclo per l'uso dei power Up
        //Ciclo finisce quando si riceve il messaggio End dal controller

    }
}
