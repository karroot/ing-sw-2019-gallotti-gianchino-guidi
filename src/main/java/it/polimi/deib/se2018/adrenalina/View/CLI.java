package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.PlayerImmutable;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.PowerUpCardImmutable;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.UpdateModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements the CLI with all the method needed to handle it
 * @author Cysko7927
 */
public class CLI implements Terminal
{
    private ColorId playerOfThisCli;
    private UpdateModel data;

    /**
     * Create a CLI for a player
     */
    public CLI()
    {

    }

    /**
     * Print all the game board with the weapons in spawn points and the players' boards
     */
    @Override
    public void showBoard()
    {
        //Obtain all the weapons in spawnpoints
        List<String> blueWeapons = data.getDataOfAllSquare()
                .stream()
                .filter(squareImmutable -> squareImmutable.getX() ==3 && squareImmutable.getY() == 3 )
                .collect(Collectors.toList()).get(0).getWeaponCardList();

        List<String> redWeapons = data.getDataOfAllSquare()
                .stream()
                .filter(squareImmutable -> squareImmutable.getX() ==1 && squareImmutable.getY() == 2 )
                .collect(Collectors.toList()).get(0).getWeaponCardList();

        List<String> yellowWeapons = data.getDataOfAllSquare()
                .stream()
                .filter(squareImmutable -> squareImmutable.getX() ==4 && squareImmutable.getY() == 1 )
                .collect(Collectors.toList()).get(0).getWeaponCardList();

        //Print the killshootTrack
        System.out.println(data.getDataOfBoard().getKillShotTrack());

        //Print the arena
        switch (data.getDataOfBoard().getCode())
        {
            case 1:
                showBoard1();
                break;
            case 2:
                showBoard2();
                break;
            case 3:
                showBoard3();
                break;
            case 4:
                showBoard4();
        }

        //Print the weapons in spawnPoints
        System.out.println("Armi nello Shop BLU:");

        System.out.println(blueWeapons);

        System.out.println("Armi nello Shop ROSSO:");

        System.out.println(redWeapons);

        System.out.println("Armi nello Shop GIALLO:");

        System.out.println(yellowWeapons);

        //Print the board of all players

        System.out.println("PLANCE GIOCATORI:");
        System.out.println("###############################################################################");

        for (PlayerImmutable t:data.getDataOfAllPlayer())
        {
            if (t.getColor().equals(playerOfThisCli))
                System.out.println("LA TUA PLANCIA:");


            showPlayerBoard(t);

            System.out.println("###############################################################################");
        }

    }

    /**
     * Obtain the copy of the model
     * @return copy of the model
     */
    public UpdateModel getData() {
        return data;
    }

    /**
     * Change the copy of the model updated
     * @param data new copy of the model
     */
    public void setData(UpdateModel data)
    {
        this.data = data;
    }

    /**
     * Set the color of the player of this CLI
     * @param color color of the player
     */
    @Override
    public void setColorOfPlayer(ColorId color)
    {
        playerOfThisCli = color;
    }

    /**
     * Print the board of a player with all the statistics and the array of damage Points
     * @param player player to print
     */
    @Override
    public void showPlayerBoard(PlayerImmutable player)
    {
        System.out.println(player);

        ColorId[] damages = player.getDamageCounter();

        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println(damages[0]+ "   "+damages[1]+ "   "+damages[2]+ "   "+damages[3]+ "   "+damages[4]+ "   "+damages[5]+ "   "
                +damages[6]+ "   "+damages[7]+ "   "+damages[8]+ "   "+damages[9]+ "   "+damages[10]+ "   "+damages[11]);
        System.out.println("                                                                                   ");
        System.out.println("+---------------------------------------------------------------------------------+");
    }


    /**
     * Print to terminal the three action of a Round
     */
    @Override
    public void showAction()
    {
        System.out.println("Scegli un azione da fare:");
        System.out.println("1:Muovere");
        System.out.println("2:Raccogli");
        System.out.println("3:Spara");
    }

    /**
     * Ask an integer between 1 and 3 to select an action(move,grab,Shoot)
     * @return integer chosen by user
     */
    @Override
    public int selectAction()
    {
        return inputInt(1,3);
    }

    /**
     * Print the scores
     * @param messageWithFinalScore message from server tha contain all the scores of the player
     */
    @Override
    public void showFinalScore(String messageWithFinalScore)
    {
        System.out.println(messageWithFinalScore);
    }

    /**
     * Show at user a generic message
     * @param message string to show at the user
     */
    @Override
    public void showMessage(String message)
    {
        System.out.println(message);
    }

    /**
     * Show at user a message of error
     * @param message string that represents an error
     */
    @Override
    public void showError(String message)
    {
        System.out.println(message);
    }


    /**
     * Methods that ask at the user an int between min and max and returns the integer chosen by user
     * @param min minus integer accepted
     * @param max max integer accepted
     * @return integer chosen by user
     */
    @Override
    public int inputInt(int min,int max)
    {
        boolean done = false;
        int choice = 0;


        while (!done)//While the user doesn't insert a integer valid you continue to ask a integer
        {
            boolean ready = false;

            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                TimerAFK.startTimer(Thread.currentThread());

                while (!Thread.currentThread().isInterrupted() && !ready)
                {
                    if (reader.ready())
                    {
                        choice = Integer.parseInt(reader.readLine());//Read an input
                        TimerAFK.interruptTimer();
                        ready = true;
                    }

                }

                if (Thread.currentThread().isInterrupted()) //If the while is terminated because the timer is over
                {
                    System.out.println("Hai impiegato troppo tempo:Turno Saltato");
                    throw new ThreadDeath(); //Kill the thread
                }


                if (!(choice>= min && choice<=max)) //If int digits from user isn't in the range
                    throw new IOException();//Launch exception

                done = true; //If there aren't exception use: the integer is valid
            }
            catch (IOException|NumberFormatException e) //If there are problem
            {
                System.out.println("Input non valido");//Print that the input is not valid
            }
            catch (Exception e)//If the timer AFK is over
            {
                System.out.println("Hai impiegato troppo tempo:Turno Saltato");
                throw new ThreadDeath(); //Interrupt
            }

        }

        return  choice;
    }

    /**
     * Ask at the player if he wants to reload
     * This method doesn't check if there are weapon not loaded
     * @return true if says yes
     */
    @Override
    public boolean askReloading()
    {


            System.out.println("Vuoi Ricaricare?");
            System.out.println("1:Si");
            System.out.println("2:No");

            int choice = inputInt(1,2);

            if (choice == 1)
                return true;


        return false;
    }

    /**
     * Check if the player has some Teleporter or Newton and ask if he wants
     * to use them
     * @return true if the player says yes
     */
    @Override
    public boolean askPowerUPTeleOrNew()
    {
        List<String> allPowerUps = data.getDataOfAllPlayer()
                .stream()
                .filter(playerImmutable -> playerImmutable.getColor().equals(playerOfThisCli))
                .map(PlayerImmutable::getPowerupCardList)
                .collect(Collectors.toList())
                .get(0)
                .stream()
                .map(PowerUpCardImmutable::powerToString)
                .collect(Collectors.toList()); //Obtain all powerUp of the player

        boolean thereAreTeleOrNew = false;

        for (String t:allPowerUps) //Check if there are teleporter or newton
        {
            if (t.contains("Teletrasporto") || t.contains("Raggio Cinetico"))
            {
                thereAreTeleOrNew = true;
            }
        }

        if (thereAreTeleOrNew)
        {
            System.out.println("Vuoi usare i powerUp?");
            System.out.println("1:Si");
            System.out.println("2:No");

            int choice = inputInt(1,2);

            if (choice == 1)
                return true;
        }

        return false;
    }

    /**
     * Show all the powerUp of the player on the CLI
     */
    @Override
    public void showPowerUp()
    {

        List<String> allPowerUps = data.getDataOfAllPlayer()
                .stream()
                .filter(playerImmutable -> playerImmutable.getColor().equals(playerOfThisCli))
                .map(PlayerImmutable::getPowerupCardList)
                .collect(Collectors.toList())
                .get(0)
                .stream()
                .map(PowerUpCardImmutable::powerToString)
                .collect(Collectors.toList()); //Obtain the list of all PowerUP

        int i = 1;

        if (allPowerUps.isEmpty()) //if the list is empty
        {
            System.out.println("Non hai powerUp");//Print that there aren't powerUps
            return;
        }


        System.out.println("Powerup che hai:"); //Print the name of all PowerUp
        for (String t:allPowerUps)
        {
            System.out.println(i+":"+t);
            i++;
        }

    }

    /**
     * Ask at the player to select a powerUp
     * @return integer that represent the choice of the user,if there aren't powerUps returns 0
     */
    @Override
    public int selectPowerUp()
    {

        List<String> allPowerUps = data.getDataOfAllPlayer()
                .stream()
                .filter(playerImmutable -> playerImmutable.getColor().equals(playerOfThisCli))
                .map(PlayerImmutable::getPowerupCardList)
                .collect(Collectors.toList())
                .get(0)
                .stream()
                .map(PowerUpCardImmutable::powerToString)
                .collect(Collectors.toList()); //Obtain the list of all PowerUP

        if (allPowerUps.isEmpty())
            return 0;

        return inputInt(1,allPowerUps.size());
    }

    /**
     * Ask at the user if he wants to use the Targeting Scope to do more damage
     * @return true if the user says yes
     */
    @Override
    public boolean askPowerUPTarget()
    {
        System.out.println("Vuoi usare i mirini?");

        System.out.println("1:Si");
        System.out.println("2:No");

        int choice = inputInt(1,2);

        if (choice == 1)
            return true;

        return false;
    }

    /**
     * Ask at the user if he wants to obtain more ammo using the power ups
     * printing the request on CLI
     * @return true if the user said yes
     */
    @Override
    public boolean askPowerUPForReload()
    {
        System.out.println("Vuoi usare dei powerUp per incrementare le munizioni?");

        System.out.println("1:Si");
        System.out.println("2:No");

        int choice = inputInt(1,2);

        if (choice == 1)
            return true;

        return false;
    }

    /**
     * Show at the user on the cli a possible choice for a request of input
     * @param text text to print
     */
    @Override
    public void addOptionInput(String text)
    {
        System.out.println(text);
    }

    /**
     * Show at the user on the cli the description for a request of input
     * @param text text to print
     */
    @Override
    public void addTextInput(String text)
    {
        System.out.println(text);
    }

    /**
     * Methods that ask at the user an int contained in acceptedInt and returns the integer chosen by user
     * @param acceptedInt list of all integer accepted
     * @return integer chosen by user
     */
    @Override
    public int inputInt(List<Integer> acceptedInt)
    {
        boolean done = false;
        int choice = 0;

        while (!done)//While the user doesn't insert a integer valid you continue to ask a integer
        {
            boolean ready = false;

            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                TimerAFK.startTimer(Thread.currentThread());

                while (!Thread.currentThread().isInterrupted() && !ready)
                {
                    if (reader.ready())
                    {
                        choice = Integer.parseInt(reader.readLine());//Read an input
                        TimerAFK.interruptTimer();
                        ready = true;
                    }

                }

                if (Thread.currentThread().isInterrupted()) //If the while is terminated because the timer is over
                {
                    System.out.println("Hai impiegato troppo tempo:Turno Saltato");
                    throw new ThreadDeath(); //Kill the thread
                }

                if (!acceptedInt.contains(choice))//If int inserted from user isn't in the list of accepted integer
                    throw new IOException();//Launch exception

                done = true;//If there aren't exception use: the integer is valid
            }
            catch (IOException|NumberFormatException e)//If there are problem
            {
                System.out.println("Input non valido");//Print that the input is not valid
            }

        }

        return  choice;
    }

    //############ Private Methods #############

    //Methods that needing to print the ARENAS
    private void showBoard1()
    {
        System.out.println(" y                                                 ");
        System.out.println("  +-----------+-----------+-----------+-----------+");
        System.out.println("  |Red        |Blue        Blue       |           |");
        System.out.println(" 3|                         Weapons   |  NULLA    |");
        System.out.println("  |           |             Point     |           |");
        System.out.println("  +           +-----  ----+----  -----+-----------+");
        System.out.println("  |Red        |Purple      Purple     |Yellow     |");
        System.out.println(" 2|  Weapons  |                                   |");
        System.out.println("  |  Point    |                       |           |");
        System.out.println("  +-----  ----+-----  ----+-----------+           +");
        System.out.println("  |White       White       White      |Yellow     |");
        System.out.println(" 1|                                     Weapons   |");
        System.out.println("  |                                   | Point     |");
        System.out.println("  +-----------+-----------+-----------+-----------+");
        System.out.println("x=      1           2            3           4     ");
    }

    private void showBoard2()
    {
        System.out.println(" y                                                 ");
        System.out.println("  +-----------+-----------+-----------+-----------+");
        System.out.println("  |Blue        Blue        Blue       |           |");
        System.out.println(" 3|                          Weapons  |  NULLA    |");
        System.out.println("  |                          Point    |           |");
        System.out.println("  +-----  ----+-----------+----  -----+-----------+");
        System.out.println("  |Red         Red         Red        |Yellow     |");
        System.out.println(" 2|  Weapons                                      |");
        System.out.println("  |  Point                            |           |");
        System.out.println("  +-----------+-----  ----+-----------+           +");
        System.out.println("  |           |White       White      |Yellow     |");
        System.out.println(" 1|  NULLA    |                          Weapons  |");
        System.out.println("  |           |                       |  Point    |");
        System.out.println("  +-----------+-----------+-----------+-----------+");
        System.out.println("x=      1           2            3           4     ");
    }

    private void showBoard3()
    {
        System.out.println(" y                                                 ");
        System.out.println("  +-----------+-----------+-----------+-----------+");
        System.out.println("  |Blue        Blue        Blue       |Green      |");
        System.out.println(" 3|                          Weapons              |");
        System.out.println("  |                          Point    |           |");
        System.out.println("  +----  -----+-----------+----  -----+------  ---+");
        System.out.println("  |Red         Red        |Yellow      Yellow     |");
        System.out.println(" 2|  Weapons              |                       |");
        System.out.println("  |  Point                |                       |");
        System.out.println("  +-----------+-----  ----+           +           +");
        System.out.println("  |           |White      |Yellow      Yellow     |");
        System.out.println(" 1|   NULLA   |                         Weapons   |");
        System.out.println("  |           |           |             Point     |");
        System.out.println("  +-----------+-----------+-----------+-----------+");
        System.out.println("x=      1           2            3           4     ");
    }

    private void showBoard4()
    {
        System.out.println(" y                                                 ");
        System.out.println("  +-----------+-----------+-----------+-----------+");
        System.out.println("  |Red        |Blue        Blue       |Green      |");
        System.out.println(" 3|                         Weapons               |");
        System.out.println("  |           |             Point     |           |");
        System.out.println("  +           +-----  ----+----  -----+------  ---+");
        System.out.println("  |Red        |Purple     |Yellow      Yellow     |");
        System.out.println(" 2|  Weapons  |           |                       |");
        System.out.println("  |  Point    |           |                       |");
        System.out.println("  +-----  ----+-----  ----+           +           +");
        System.out.println("  |White       White      |Yellow      Yellow     |");
        System.out.println(" 1|                                      Weapons  |");
        System.out.println("  |                       |              Point    |");
        System.out.println("  +-----------+-----------+-----------+-----------+");
        System.out.println("x=      1           2            3           4     ");
    }
}
