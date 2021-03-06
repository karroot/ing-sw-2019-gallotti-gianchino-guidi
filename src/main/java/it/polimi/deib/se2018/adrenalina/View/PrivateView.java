package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.GUI.GUI;
import it.polimi.deib.se2018.adrenalina.View.GUI.SetupGui;
import it.polimi.deib.se2018.adrenalina.communication_message.*;
import it.polimi.deib.se2018.adrenalina.communication_message.message_asking_controller.*;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.PlayerImmutable;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.SquareImmutable;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.UpdateModel;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * This class implements the view of the client that handles the inputs the CLI or GUI and memorizes the copy
 * Immutable of the model
 * @author Cysko7927
 */
public class PrivateView extends Observable<ResponseInput> implements Observer<RequestInput>
{
    private String name;
    private String action_hero_comment;
    private ColorId colorId;

    Terminal terminal;
    private final Object msg = new Object();
    private int cont;
    private RequestInput messageBuffer;
    private NetworkHandlerRMI networkHandlerRMI = null;
    private NetworkHandlerSocket networkHandlerSocket = null;

    /**
     * it Creates The private view and depending on the user's choices creates a network handler(RMI or Socket)
     * @param technology it represents the choice of the user (RMI or Socket)
     * @param ip ip address of the server
     * @param port port tcp of the server
     * @param gui it represent if the user chooses the gui or not
     * @param action_hero_comment action-hero comment inserted by user
     * @param name name inserted by user
     * @throws IOException if there are problem with the network handler
     */
    public PrivateView(int technology,String ip,int port,boolean gui,String name,String action_hero_comment) throws IOException
    {

        this.name = name;
        this.action_hero_comment = action_hero_comment;

        if (!gui)
        {
            terminal = new CLI();
        }
        else
        {
            terminal = new GUI();
        }

        if (technology == 1)
        {
            networkHandlerRMI = new NetworkHandlerRMI(this,ip,port);
            TimerAFK.setNetworkHandlerRMI(networkHandlerRMI);


            register(networkHandlerRMI);
        }
        else
        {


            networkHandlerSocket = new NetworkHandlerSocket(ip,port,this);

            TimerAFK.setNetworkHandlerSocket(networkHandlerSocket);

            register(networkHandlerSocket);
        }



    }


    public String getName()
    {
        return name;
    }

    public String getAction_hero_comment()
    {
        return action_hero_comment;
    }

    public ColorId getColorId() {
        return colorId;
    }


    public void setColorId(ColorId colorId)
    {
        this.colorId = colorId;
        terminal.setColorOfPlayer(colorId);
    }

    public void showMenu()
    {

    }

    /**
     * This method being called by Network Handler in case of disconnection to re-ask the credentials
     * of the server
     * The method will continue to ask the credentials until the connection will not be again active
     */
    public void askReconnection()
    {

        SetupGui setupGui;
        boolean finished = false;

        while (!finished)
        {
            finished = true;

            synchronized (AppClient.syncSetup)
            {
                setupGui = new SetupGui(name,action_hero_comment);

                while (!setupGui.isReady())
                {
                    try
                    {
                        AppClient.syncSetup.wait();
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                    }
                }
            }

            try
            {
                if (networkHandlerSocket!= null)
                {
                    networkHandlerSocket.startConnection(setupGui.getIP(), setupGui.getPort());
                }
                else
                {
                    networkHandlerRMI.startConnection(setupGui.getIP(), setupGui.getPort());
                }
            }
            catch (IOException e)
            {
                showError("Impossible connetersi:Controlla le informazioni inserite");
                finished = false;
            }

        }

    }

    /**
     * Methods that create a new game
     */

    public void showBoard()
    {
        terminal.showBoard();
    }

    /**
     * Method that starts the execution of a round for this player
     */
    public void startRound()
    {

        //#######Player can use a teleporter or a newton###############

        powerUpTeleportOrNewton();

        //#######Player must choose two actions###############



        cont = 0;


        while (cont < 2) //The
        {
            //Show the actions
            showAction();
            int choice = selectAction(); //Do to selects an action to the player

            switch (choice)
            {
                case 1: //if the user chose "Move"
                    moveAction();
                    break;
                case 2://if the user chose "Grab"
                    grabAction();
                    break;
                case 3:
                    shootAction();//If the user chose "Shoot"(this action includes also using the powerUps to obtain more ammo)
                    break;
            }


            //#######Player can use a teleporter or a newton###############

            powerUpTeleportOrNewton();
        }


        //########Reload weapons###########

        reloading();


        sendEndRound();
        return;
    }

    private void sendEndRound() {
        try //notify at the controller that the round is finished
        {
            notify( new EndRound());
        }
        catch (Exception e)
        {
            terminal.showError("Sei stato disconesso : Turno interroto");
            terminal.showError(e.getMessage());
            Thread.currentThread().interrupt();
            throw new ThreadDeath();
        }

        Thread.currentThread().interrupt();
        throw new ThreadDeath();
    }

    /**
     * This method being called by Network Handler to ask at the user which powerUp to use for the respawn
     */
    public void startRespawn()
    {
        //(The controller before to start the respawn must give at player a powerUp)
        //Ask at the player which power up to use for the respawn

        RequestInput messageFromNetwHandl = getMessageFromNetwHandl();

        messageFromNetwHandl.printActionsAndReceiveInput(terminal);

        ResponseInput responseInput = messageFromNetwHandl.generateResponseMessage();

        try
        {
            notify(responseInput);
        }
        catch (Exception e)
        {
            terminal.showError("Sei stato disconesso : Turno interroto");
            terminal.showError(e.getMessage());
            Thread.currentThread().interrupt();
            throw new ThreadDeath();
        }

    }

    /**
     * This method handles the logic of respawn for the terminator
     */
    public void startTerminator()
    {
        try
        {
            //Move the terminator
            RequestInput messageFromNetwHandl = getMessageFromNetwHandl();

            messageFromNetwHandl.printActionsAndReceiveInput(terminal);//Ask at the user where to move the terminator

            ResponseInput responseInput = messageFromNetwHandl.generateResponseMessage(); //Generate and send the response message

            notify(responseInput);

            RequestShootTerminator messageRequest =(RequestShootTerminator) getMessageFromNetwHandl();


            if (messageRequest.getEnemies().size() == 0)
            {
                showMessage("Il terminator non ha bersagli non può sparare");
                return;
            }
            messageRequest.printActionsAndReceiveInput(terminal);//Ask at the user where to move the terminator

            responseInput = messageRequest.generateResponseMessage(); //Generate and send the response message

            notify(responseInput);

        }
        catch (Exception e)
        {
            terminal.showError("Sei stato disconesso : Turno interroto");
            terminal.showError(e.getMessage());
            Thread.currentThread().interrupt();
            throw new ThreadDeath();
        }
    }

    /**
     * This method handles the logic of respawn for the terminator
     */
    public void respawnTerminator()
    {
        try
        {
            RequestInput messageFromNetwHandl = getMessageFromNetwHandl();

            messageFromNetwHandl.printActionsAndReceiveInput(terminal);

            ResponseInput responseInput = messageFromNetwHandl.generateResponseMessage();

            notify(responseInput);
        }
        catch (Exception e)
        {
            terminal.showError("Sei stato disconesso : Turno interroto");
            terminal.showError(e.getMessage());
            Thread.currentThread().interrupt();
            throw new ThreadDeath();
        }
    }

    /**
     * Method that handles the use of Tagback Granade
     */
    public void requestToUseGrenade()
    {
        try
        {

            RequestInput messageRequest = getMessageFromNetwHandl();//Obtain the request message
            //to ask which powerUps to use

            RequestPowerUp temp = (RequestPowerUp) messageRequest;

            if (temp.getPowerUptoChose().isEmpty())//Check if there are powerUps
            {
                terminal.showMessage("Non hai Powerup da usare");
                return;
            }

            terminal.showMessage("Sei stato colpito decidi se vuoi usare le granate venom");

            messageRequest.printActionsAndReceiveInput(terminal);//Ask the input asked by controller

            ResponseInput responseForController = messageRequest.generateResponseMessage();

            notify(responseForController);

        }
        catch (Exception e)
        {
            terminal.showError("Sei stato disconesso : Turno interroto");
            terminal.showError(e.getMessage());
            Thread.currentThread().interrupt();
        }
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
     * Ask at the user if he wants to use the Targeting Scope to do more damage
     * @return true if the user says yes
     */
    public boolean askPowerUPTarget()
    {
        return terminal.askPowerUPTarget();
    }

    /**
     * Ask at the user if he wants to obtain more ammo using the power ups
     * @return true if the user said yes
     */
    public boolean askPowerUPForReload()
    {
        return terminal.askPowerUPForReload();
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

    public void startFrenesy(boolean firstPlayerNotPassed)
    {

            //#######Player can use a teleporter or a newton###############

            powerUpTeleportOrNewton();

            if (firstPlayerNotPassed)
            {
                //#######Player must choose two actions###############

                cont = 0;


                while (cont < 2) //The
                {
                    //Show the actions
                    terminal.addTextInput("Scegli un azione per frenesia finale:");
                    terminal.addOptionInput("1:Muovi,ricarica e se puoi spara");
                    terminal.addOptionInput("2:Muovi");
                    terminal.addOptionInput("3:Raccogli");

                    int choice = selectAction(); //Do to selects an action to the player

                    switch (choice)
                    {
                        case 1: //if the user chose "Shoot"
                            shootActionFrenesy();
                            break;
                        case 2://if the user chose "Move"
                            moveAction();
                            break;
                        case 3:
                            grabAction();//If the user chose "Grab"
                            break;
                    }
                }
            }
            else
            {
                terminal.addTextInput("Scegli un azione per frenesia finale:");
                terminal.addOptionInput("1:Muovi,ricarica e se puoi spara");
                terminal.addOptionInput("2:Raccogli");


                int choice = terminal.inputInt(1,2); //Do to selects an action to the player

                switch (choice)
                {
                    case 1: //if the user chose "Shoot"
                        shootActionFrenesy();
                        break;
                    case 2://if the user chose "Grab"
                        grabAction();
                        break;
                }
            }



                //#######Player can use a teleporter or a newton###############

                powerUpTeleportOrNewton();


                //######## Using targeting scope #########

                targetingScopeAction();


        sendEndRound();


    }


    /**
     * Method that show at the user the final scores
     * @param messageWithFinalScore message sent by controller that contains all scores
     */
    public void showFinalScore(String messageWithFinalScore)
    {
        terminal.showFinalScore(messageWithFinalScore);
    }

    /**
     * Method that show at the user a generic text message
     * @param message text to print
     */
    public void showMessage(String message)
    {
        terminal.showMessage(message);
    }

    /**
     * Method that show at the user a generic error message
     * @param message text to print
     */
    public void showError(String message)
    {
        terminal.showError(message);
    }

    /**
     * Thi method being called by Network Handler to ask at view to update its copy of the model to show
     * at the user , after shows the game board updated and send at the controller a message to say that
     * the model was update with success
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
            System.out.println("Messaggio Arrivato");
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
        //Wait 800 ms(To resolve latency problems)
        try
        {
            Thread.sleep(800);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            System.out.println(e);
            throw new ThreadDeath();
        }

        synchronized (msg)
        {
            while (messageBuffer == null)
            {
                try
                {
                    System.out.println("Attesa messaggio");
                    msg.wait();
                }
                catch (InterruptedException e)
                {
                    terminal.showError("Sei stato disconesso");
                    Thread.currentThread().interrupt();
                    throw new ThreadDeath();
                }
            }

            RequestInput message = messageBuffer;
            System.out.println("Ricevuto:"+message);
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
            messageRequest.printActionsAndReceiveInput(terminal);//Ask the input asked by controller
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
            messageRequest.printActionsAndReceiveInput(terminal);//Ask the input asked by controller
            ResponseInput responseForController = messageRequest.generateResponseMessage();//Obtain the response Message

            ResponseGrabStuff temp = (ResponseGrabStuff) responseForController;

            //Save the response message with the square chose

            notify(responseForController);//Send the message at controller


            int x = temp.getX(); //Obtain the coordinates of the square chosen by the user to grab
            int y = temp.getY();


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
                messageRequest.printActionsAndReceiveInput(terminal);//Ask the inputs asked by controller
                responseForController = messageRequest.generateResponseMessage();//Obtain the response Message

                notify(responseForController);//Send the message at controller with the weapon chosen by player

                //Case: if the player has three weapons##################ààà
                PlayerImmutable thisPlayer = obtainPlayerOfThisClient();


                if (thisPlayer != null && thisPlayer.getWeaponCardList().size() == 3)
                {
                    messageRequest = getMessageFromNetwHandl(); //Obtain the request message with the weapons to change

                    messageRequest.printActionsAndReceiveInput(terminal);//Ask the inputs asked by controller

                    responseForController = messageRequest.generateResponseMessage();//Obtain the response Message

                    notify(responseForController);//Send the message at controller with the weapon chosen by player
                }

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
            //Ask if the player wants to use his power ups to increase his ammo
            if (askPowerUPForReload())
            {
                notify(new AskForAllPowerups());

                RequestInput messageRequest = getMessageFromNetwHandl();

                RequestPowerUp temp = (RequestPowerUp) messageRequest;

                if (temp.getPowerUptoChose().isEmpty()) //If the controller says that player hasn't power ups
                {
                    showMessage("Non hai powerUp da usare"); //Skip to shoot action
                }
                else
                {
                    useUntilEnd(messageRequest);
                    //If the message is an "End" then there aren't more powerUps to ask
                }
            }


            notify(new AskShoot());//Notify at controller that the player wants to shoot


            PlayerImmutable thisPlayer = obtainPlayerOfThisClient();

            if (thisPlayer!=null && thisPlayer.getLast() >= 6) //If the player is in adrenaline mode
            {
                //For first the player must be choice a square where to move
                RequestInput messageRequest = getMessageFromNetwHandl();
                messageRequest.printActionsAndReceiveInput(terminal);//Ask the inputs asked by controller
                ResponseInput responseForController = messageRequest.generateResponseMessage();//Obtain the response Message with the Square chosen
                //by user

                notify(responseForController);//Send the message at controller
            }

            RequestInput messageRequest = getMessageFromNetwHandl(); //Obtain the request message that ask which
            //weapons the user wants to use

            RequestShootPeople temp = (RequestShootPeople) messageRequest;

            if (temp.getWeaponCardsName().isEmpty()) //If the player has not weapons , interrupts the action
            {
                showMessage("Non hai armi da usare,scegli un'altra azione.");
                return;
            }


            messageRequest.printActionsAndReceiveInput(terminal);//Ask the inputs asked by controller
            ResponseInput responseForController = messageRequest.generateResponseMessage();//Obtain the response Message with the weapon chosen
            //by user

            notify(responseForController);//Send the message at controller

            //Using the weapon########

            messageRequest = getMessageFromNetwHandl(); //Obtain the request message that ask at user
            //all input needed to use the weapon

            messageRequest.printActionsAndReceiveInput(terminal);//Ask the inputs asked by controller
            responseForController = messageRequest.generateResponseMessage();//Obtain the response Message with all input
            // inserted by user

            notify(responseForController);//Send the message at controller

            //######## Using targeting scope #########
            targetingScopeAction();

            cont++;
        }
        catch (Exception e)
        {
            terminal.showError("Sei stato disconesso : Turno interroto");
            terminal.showError(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    //Shoot Action for Frenesy
    private void shootActionFrenesy()
    {
        //1 mando richiesta al Controller per muoversi (Usare richiesta diversa)
        try
        {
            notify(new MoveBeforeShoot());//Notify at controller that the player wants to shoot

            RequestInput messageRequest = getMessageFromNetwHandl();
            messageRequest.printActionsAndReceiveInput(terminal);//Ask the inputs asked by controller
            ResponseInput responseForController = messageRequest.generateResponseMessage();//Obtain the response Message with the weapon chosen
            //by user

            notify(responseForController);

            reloading();

            //Ask if the player wants to use his power ups to increase his ammo
            if (askPowerUPForReload())
            {
                notify(new AskForAllPowerups());

                messageRequest = getMessageFromNetwHandl();

                RequestPowerUp temp = (RequestPowerUp) messageRequest;

                if (temp.getPowerUptoChose().isEmpty()) //If the controller says that player hasn't power ups
                {
                    showMessage("Non hai powerUp da usare"); //Skip to shoot action
                }
                else
                {
                    messageRequest.printActionsAndReceiveInput(terminal);//Ask the inputs asked by controller
                    responseForController = messageRequest.generateResponseMessage();//Obtain the response Message with the weapon chosen
                    //with all power ups that the player chose to increase his ammo

                    notify(responseForController);//Send the message at controller
                }


            }

            notify(new AskShoot());//Notify at controller that the player wants to shoot

            messageRequest = getMessageFromNetwHandl(); //Obtain the request message that ask which
            //weapons the user wants to use

            RequestShootPeople temp = (RequestShootPeople) messageRequest;

            if (temp.getWeaponCardsName().isEmpty()) //If the player has not weapons , interrupts the action
            {
                showMessage("Non hai armi da usare,scegli un'altra azione.");
                return;
            }


            messageRequest.printActionsAndReceiveInput(terminal);//Ask the inputs asked by controller
            responseForController = messageRequest.generateResponseMessage();//Obtain the response Message with the weapon chosen
            //by user

            notify(responseForController);//Send the message at controller

            //Using the weapon########

            messageRequest = getMessageFromNetwHandl(); //Obtain the request message that ask at user
            //all input needed to use the weapon

            messageRequest.printActionsAndReceiveInput(terminal);//Ask the inputs asked by controller
            responseForController = messageRequest.generateResponseMessage();//Obtain the response Message with all input
            // inserted by user

            notify(responseForController);//Send the message at controller


            //######## Using targeting scope #########

            targetingScopeAction();


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

                if (messageRequest instanceof End)
                    showMessage("Non ci sono armi da ricaricare");

                while (! (messageRequest instanceof End)) //If the message contains a
                // weapons that it can be reloaded
                {
                    messageRequest.printActionsAndReceiveInput(terminal);//Ask the input asked by controller
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

        //Ask to use the powerUp (Teleporter or Newton)

        if (askPowerUPTeleOrNew()) //If the player has some powerUPs (Teleporter or Newton) and says that he wants to use them
        {
            try
            {
                notify(new AskPowerUPTeleOrNew());//Notify at controller that the player wants to use the powerUps


                RequestInput messageRequest = getMessageFromNetwHandl();//Obtain the request message
                //to ask which powerUps to use

                RequestPowerUp temp = (RequestPowerUp) messageRequest;

                if (temp.getPowerUptoChose().isEmpty())//Check if there are powerUps
                {
                    terminal.showMessage("Non hai Powerup da usare");
                    return;
                }

                useUntilEnd(messageRequest);
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

    private void useUntilEnd(RequestInput messageRequest) throws Exception {
        messageRequest.printActionsAndReceiveInput(terminal);//Ask the input asked by controller

        ResponseInput responseForController = messageRequest.generateResponseMessage();

        notify(responseForController);


        messageRequest = getMessageFromNetwHandl();//Obtain the request message

        while (! (messageRequest instanceof End)) //if the message contains a request to use the powerUp
        {
            messageRequest.printActionsAndReceiveInput(terminal);//Ask the input asked by controller
            responseForController = messageRequest.generateResponseMessage();//Obtain the response Message with
            //with the response (yes or no) and the inputs needed
            notify(responseForController);//Send the message of response at controller
            messageRequest = getMessageFromNetwHandl();//Obtain the request message
        }
    }

    //Code that handles using of power ups targeting scope
    private void targetingScopeAction()
    {
        if (askPowerUPTarget()) //Ask at the player if he wants to use a targeting scope
        {
            try //if he said yes
            {
                notify(new AskTargetingScope());//Ask at the controller to use the targeting Scope

                RequestInput messageRequest = getMessageFromNetwHandl(); //Obtain the request message

                RequestPowerUp temp = (RequestPowerUp) messageRequest;

                if (temp.getPowerUptoChose().isEmpty()) //If the controller says that the player hasn't any targeting scope
                {
                    showMessage("Non hai mirini da usare");
                    return; //Interrupt the method
                }


                useUntilEnd(messageRequest);
                //If the message is an "End" then there aren't more targeting scope to use



            }
            catch (Exception e)
            {
                terminal.showError("Sei stato disconesso : Turno interroto");
                terminal.showError(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    private PlayerImmutable obtainPlayerOfThisClient()
    {
        PlayerImmutable thisPlayer = null;

        for (PlayerImmutable t:terminal.getData().getDataOfAllPlayer())
        {
            if (t.getColor().equals(colorId))
            {
                thisPlayer = t;
            }
        }
        return thisPlayer;
    }
}

