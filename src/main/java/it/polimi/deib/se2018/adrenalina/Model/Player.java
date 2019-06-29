package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Cysko7927
 * It represents the player and performs the aviable actions an methods that modify his state
 */
public class Player
{

    private boolean terminator=false;
    private boolean afk = false;
    private boolean frenzy;
    private boolean flipped;

    private ColorId color;

    private int ammoYellow;

    private  int ammoRed;

    private  int ammoBlue;

    private String name;

    private String action_hero_comment;

    private boolean isFirst;

    //Rep of the damage counter
    //################

    private ColorId[] damageCounter; //Represent the damages in player's board

    private int last; //Indicate the last position free in array of damage counter

    //#################

    private StatePlayer state;

    private List<ColorId> markCounter; //List that represent the mark in Player's Board

    private int deathsCounter;//Counter of the player's deaths

    private int[] scorePoint; //Represent the the scores in player's board

    private int score; //Score of the player

    private boolean overKill;

    private  Square square;

    private List<PowerUpCard> powerupCardList; //Array of the powerUps

    private List<WeaponCard> weaponCardList; //Array of weapons

    /**
     * instantiates the player's class for the start game
     *
     * @param color Color of the player
     * @param name name of the player
     * @param action_hero_comment comment of battle
     * @param isFirst Is the first player?
     */
    public Player(ColorId color, String name, String action_hero_comment, boolean isFirst)
    {
        this.color = color;
        this.name = name;
        this.action_hero_comment = action_hero_comment;
        ammoYellow = 1;
        ammoRed = 1;
        ammoBlue =1;
        last = 0;
        this.damageCounter = new ColorId[12];
        this.isFirst = isFirst;
        deathsCounter = 0;
        score = 0;
        powerupCardList = new ArrayList<>();
        weaponCardList = new ArrayList<>();
        damageCounter = new ColorId[12];
        markCounter = new ArrayList<>();
        square = null;
        state = new Normal(); //Player is in normal condition(No adrenalized effects)
        overKill = false;
        scorePoint = new int[6]; //Player in the initial conditions has the max scores possible
        scorePoint[0] = 8;
        scorePoint[1] = 6;
        scorePoint[2] = 4;
        scorePoint[3] = 2;
        scorePoint[4] = 1;
        scorePoint[5] = 1;
        frenzy = false; //In normal condition the player is not frenzy
        flipped = false;
    }


    //Getter
    //-----------------------------
    public ColorId getColor()
    {
        return color;
    }

    public List<ColorId> getMarkCounter()
    {
        return new ArrayList<>(markCounter);
    }

    public int getAmmoYellow()
    {
        return ammoYellow;
    }

    public int getAmmoRed()
    {
        return ammoRed;
    }

    public int getAmmoBlue()
    {
        return ammoBlue;
    }

    public String getName()
    {
        return name;
    }

    public String getAction_hero_comment()
    {
        return action_hero_comment;
    }

    public boolean isFirst()
    {
        return isFirst;
    }

    public int[] getScorePoint()
    {
        return scorePoint.clone();
    }

    public boolean isFrenzy()
    {
        return frenzy;
    }

    public boolean isTerminator() {
        return terminator;
    }

    /**
     * Returns a array of ColorId that represent all damage points ordered and done by other players
     * @return Array with the damage points
     */
    public ColorId[] getDamageCounter()
    {
        return damageCounter.clone();
    }

    public int getDeathsCounter()
    {
        return deathsCounter;
    }

    /**
     * Return the score that the player has too
     * @return integer that represent the score points
     */
    public int getScore()
    {
        return score;
    }

    public boolean isFlipped()
    {
        return flipped;
    }

    /**
     * return if the player is afk or not
     * @return boolean that indicate the status of player
     */
    public boolean isAfk() {
        return afk;
    }

    /**
     * Return the square where is located the player
     * @return object square
     */
    public  Square getSquare()
    {
        return square;
    }

    /**
     * Return the number of damage points that there are in player's Board
     * @return integer that represents the number of damage points
     */
    public int getNumberOfDamagePoint()
    {
        return last;
    }

    /**
     * Return a list that contains all the weapons that the player has too
     * @return list that contains the player's weapons
     */
    public List<WeaponCard> getWeaponCardList()
    {
        return new ArrayList<>(weaponCardList);
    }

    /**
     * Return a list that contains all the power up that the player has too
     * @return list that contains the player's power up
     */
    public List<PowerUpCard> getPowerupCardList()
    {
        return new ArrayList<>(powerupCardList);
    }

    /**
     * Say if the player is dead or not because has 11 or 12 damage point
     * @return if it is true then the player is dead
     */
    public boolean isDead()
    {
        if (last == 11 || last == 12)//If in the board there are 11 or 12 damage points
        {
            return true;
        }

        return false;
    }

    public boolean isOverKill()
    {
        return overKill;
    }

    //Setter
    //------------------------


    /**
     * Set tne number of blue ammo
     * @param ammoYellow
     */
    public void setAmmoYellow(int ammoYellow)
    {
        if (this.ammoYellow + ammoYellow > 3)
            this.ammoYellow = 3;
        else
            this.ammoYellow = ammoYellow;
    }

    /**
     * Set tne number of blue ammo
     * @param ammoRed
     */
    public void setAmmoRed(int ammoRed)
    {
        if (this.ammoRed + ammoRed > 3)
            this.ammoRed = 3;
        else
            this.ammoRed = ammoRed;
    }

    /**
     * Set tne number of blue ammo
     * @param ammoBlue
     */
    public void setAmmoBlue(int ammoBlue)
    {
        if (this.ammoBlue + ammoBlue > 3)
            this.ammoBlue = 3;
        else
            this.ammoBlue = ammoBlue;
    }

    public void setTerminator(boolean terminator) {
        this.terminator = terminator;
    }

    /**
     * Set the score of player during the match
     * @param score quantity of score points to assign at the player
     * @exception IllegalArgumentException if score is negative
     */
    public void setScore(int score) throws IllegalArgumentException
    {
        if (score < 0)
            throw new IllegalArgumentException("score negativo");

        this.score = score;
    }

    /**
     * set number of the damage point to a defined value
     * @param last
     */
    public void setNumberOfDamagePoint(int last) {
        this.last = last;
    }

    /**
     *Change the position of the player in arena
     * (Requires):The square is must be valid
     * @param square square where to put the player
     * @exception NullPointerException if square is null
     */
    public void setSquare(Square square) throws NullPointerException
    {
        if (square == null)
           throw new NullPointerException("Parametro square nullo");

        this.square = square;
    }


//---------------------------

    /**
     * Add a weapon card in player's deck if player has minus of 3 weapons
     * @param weapon weapon that the player grabs from game board
     * @exception NullPointerException if weapon is null
     * @exception IllegalStateException if player has 3 weapons
     */
    public void addWeapon(WeaponCard weapon)throws NullPointerException,IllegalStateException
    {
        if (weapon == null)
            throw new NullPointerException("Parametro weapon con valore nullo");

        if (weaponCardList.size() < 3)
            weaponCardList.add(weapon);

        else {
            throw new IllegalStateException("Player ha già tre carte armi");
        }

    }

    /**
     * Exchange a old weapon with a new from game board and it returns the old too
     * (requires) the old weapon must be in list of weapon card
     * @param newWeapon  new weapon card to add in player's deck
     * @param oldWeapon name of the weapon in player's deck to exchange with the new too
     * @return the old weapon that was in player's deck
     * @exception NullPointerException if newWeapon or oldWeapon are null
     * @exception IllegalArgumentException if old weapon is not in the hands of the player
     * */
    public WeaponCard changeWeapon(WeaponCard newWeapon, String oldWeapon) throws NullPointerException,IllegalArgumentException
    {

        if (newWeapon == null || oldWeapon == null) //Check input not null
            throw new NullPointerException("Parametri armi hanno valore nullo");

        WeaponCard temp = null;

        for (WeaponCard x:weaponCardList) //Search in List of weapon card
        {
            if (x.getName().equals(oldWeapon)) //If you founded the weapon
            {
                temp = x; //Save the card
            }


        }

        if (temp == null) //If old weapon not founded
            throw new IllegalArgumentException("old weapon da restituire non presente nel mazzo del player");

        //else
        weaponCardList.remove(temp); //Remove old weapon from deck's player
        weaponCardList.add(newWeapon);//Add the new weapon in player's deck

        return temp; //Return  old weapon
    }

    /**
     * Add a power up card in player's deck if the player hasn't already 3 power up
     * @param powerupCard power up card to add
     * @exception NullPointerException if powerupCard is null
     * @exception IllegalStateException if player has already three power up cards
     */
    public void addPowerUp(PowerUpCard powerupCard) throws NullPointerException,IllegalStateException
    {
        if (powerupCard == null) //Check not null
            throw new NullPointerException("Parametro powerupCard è nullo");

        if (powerupCardList.size() < 3) //If player has not 3 powerup cards
            powerupCardList.add(powerupCard);//Add power up to the list
        else
            throw new IllegalStateException("Player ha già tre carte powerup"); //Launch exceptions

    }
    /**
     * Add a power up card in player's deck if the player hasn't already 3 power up
     * @param powerupCard power up card to add
     * @exception NullPointerException if powerupCard is null
     * @exception IllegalStateException if player has already three power up cards
     */
    public void addPowerUpRespawn(PowerUpCard powerupCard) throws NullPointerException,IllegalStateException
    {
        if (powerupCard == null) //Check not null
            throw new NullPointerException("Parametro powerupCard è nullo");

        if (powerupCardList.size() < 4) //If player has not 3 powerup cards
            powerupCardList.add(powerupCard);//Add power up to the list
        else
            throw new IllegalStateException("Player ha già tre carte powerup"); //Launch exceptions

    }
    /**
     * Remove card power up from player's deck that has been used
     * @param indexDeckCard index of card power up that has been used
     * @return card power up that has been used
     * @exception IllegalArgumentException if index card is greater than 3 or there isn't the power up card with this index
     */
    public PowerUpCard usePowerUp(int indexDeckCard) throws IllegalArgumentException
    {
        if(indexDeckCard != 0 && indexDeckCard != 1 && indexDeckCard != 2)//If index is not valid
            throw new IllegalArgumentException("indice non valido");//Launch exception

        if (indexDeckCard <= powerupCardList.size() - 1) //If index can be used
        {
            PowerUpCard temp = powerupCardList.get(indexDeckCard); //get power up using the index

            powerupCardList.remove(indexDeckCard); //Remove power up card
            return temp; //Return power up card
        }
        else//If index can't be used because the size of list is smaller
            throw new IllegalArgumentException("indice non valido perchè power up non presente");//Launch exceptions


    }

    //Procedures to handle the marks


    /**
     * Add mark done by a player if checkMarker(player) is between 0 and 2 else it does not add
     * @param player color of the player that marked this
     */
    public void addMark(ColorId player)
    {
        if (checkMarker(player) < 3 && checkMarker(player) >= 0) //If the player didn't do 3 marks
            markCounter.add(player);//Add marks on player's board
    }

    /**
     * Remove a mark and use it like damage if damage counter is not full else mark is lost
     * @param player player to witch belong the mark
     */
    public void useMark(ColorId player)
    {
        markCounter.remove(player); //Remove marker from player's board

        if (last >= 0 && last <= 11)
        {
            damageCounter[last] = player;//Add a damage point in player's board
            last++; //next place in array is free
        }

        checkState(); //Check the state of the player
    }

    public void setFrenzy(boolean frenzy)
    {
        this.frenzy = frenzy;
        checkState();
    }

    /**
     * //Calculate the number of marks done by a player
     * @param player player that done the marks
     * @return number of marks done by player
     */
    public int checkMarker(ColorId player)
    {
        int cont = 0; //Reset counter

        for (ColorId x:markCounter) //search in list of marks
        {
            if (x.equals(player)) //If marks x is of the player
                cont++;//Increment counter
        }

        return cont;//Return counter
    }

    /**
     * Add a damage point in player's deck if there aren't 12 point damage
     * else it does nothing
     * @param player player that did the damage
     */
    public void doDamage(ColorId player)
    {
        if (last >=0 && last <=11) //If there aren't 12 point damage
        {
            damageCounter[last] = player; //Do point damage
            last++;
        }

        checkState();//Check the state of player
    }



    //Methods to handle the respawn

    /**
     * Respawn the player in a Spawn point of the arena if player is death
     * @param square Spawn point where the player will respawn
     * @exception NullPointerException if square is null
     * @exception IllegalStateException if player is not death
     */
    public void respawn(SpawnPoint square) throws NullPointerException ,IllegalStateException
    {
        if (square == null) //Chech exceptions
            throw new NullPointerException("Parametro null");
        if (last >=0 && last <=10)
            throw new IllegalStateException("Impossibile fare il respawn player vivo");

        last = 0; //There aren't more damage points
        this.square = square; //Player in spawn point
        damageCounter = new ColorId[12];//The board is free now
        incrementDeathsCount(); //Increments counter of deaths and check the state of player
    }


    /**
     * Set the player in frenzy mode or not
     * frenzy mode can be on only if the player has not damage point
     * @exception IllegalStateException if the player has some damage points
     */
    public void setOnFrenzy() throws IllegalStateException
    {
        if (last != 0)
            throw new IllegalStateException("Giocatore:"+ name + " " + color + "Ha punti danno impossibile attivare frenesia finale");

        frenzy = true;
        checkState();
    }

    //Operations to handle score
    //Questo metodo forse ha più senso metterlo nella classe model
    //Ovviamente dovrà essere più leggibile aggiungendo metodi privati

    /**
     * if damage of this player is 11 or 12 returns a hash map with key color of player and value the score that he obtained doing damage
     * to this player and it frees player's board
     * It consider also the first blood but not the mark of revenge and the double kills
     * Else it launch exceptions if No Possible calculate Score
     * Requires : the number of player must be max 5 like written in the instruction set
     * @return Score
     * @exception IllegalStateException if the getNumberOfDamagePoint isn't 11 or 12
     */
    public Map<ColorId,Integer> calculateScoreForEachPlayer() throws IllegalStateException
    {
        if (last != 11 && last != 12)
            throw new IllegalStateException("Giocatore vivo Impossibile calcolare punteggio");

        Set<ColorId> players = new HashSet<>();
        Map<ColorId,Integer> scorePlayers = new Hashtable<>();
        int cont = 0;

        for (int i = 0; i<last; i++) //See which players did damage to this player
        {
            players.add(damageCounter[i]);
        }

        for (ColorId x: players)//For each player
        {
            cont = 0;//Reset Counter

            for (int i = 0; i<last; i++)//Calculate the damage points
            {
                if (x.equals(damageCounter[i]))
                    cont++;
            }

            scorePlayers.putIfAbsent(x,cont);//Insert the damage points of a player in the map


        }

        //Order the damage points of all player
        List<Map.Entry<ColorId,Integer>> temp = scorePlayers
                .entrySet()
                .stream()
                .sorted((o1, o2) -> o2.getValue() - o1.getValue())
                .collect(Collectors.toList());


        Set<ColorId> playSamePoints = new LinkedHashSet<>(); //Create a list to contain the player with same damage points
        int i = 0; //Counter for cycle
        Map<ColorId,Integer> fin = new HashMap<>();//Hash map final that will contain all the scores

        while (i< temp.size())//For each player with his damage points
        {
            Map.Entry<ColorId,Integer> z = temp.get(i);

            for (Map.Entry<ColorId,Integer> v:temp) //Check if there are other players with the same damage points
            {
                if (z.hashCode()!= v.hashCode() && v.getValue().equals(z.getValue())) //If player are different and the damage points are equals
                {
                    playSamePoints.add(z.getKey()); //Add the two player to the list
                    playSamePoints.add(v.getKey());
                }
            }

            if (!playSamePoints.isEmpty()) //If there are parity to resolve
            {
                List<ColorId> playe = resolveParity(playSamePoints); //Calculate the order of the players

                playSamePoints.clear();//Clear the list of all player with same points

                for (ColorId a: playe) //Add all the player ordered with the correct score
                {
                    fin.putIfAbsent(a,scorePoint[i]);
                    i++;
                }
            }
            else//Add the score normally
            {
                fin.putIfAbsent(z.getKey(),scorePoint[i]);
                i++;
            }

        }

        fin.replace(damageCounter[0],fin.get(damageCounter[0]) + 1); //See who did the first Blood and assign the score

        return fin;
    }


    /**
     * It returns all the players that this player can see also trough a door in other rooms
     * In the list there is also the player that can see the others
     * @param board Game board where there is the Room list of the arena
     * @return Players that He can see
     * @exception NullPointerException if board is null
     */
    public Set<Player> playerThatSee(GameBoard board) throws NullPointerException
    {
        if (board == null)
            throw new NullPointerException("board ha valore nullo");

        Set<Player> players = new HashSet<>(); //Create a set of players empty


        Set<Square> temp = board.getArena().squareReachableNoWall(getSquare().getX(),getSquare().getY(),1); //Obtain the square to distance 1


        Set<ColorRoom> colors = new HashSet<>(); //Create a set of colorRoom empty

        for (Square c : temp) //Obtain the colors of the square to distance 1
        {
            colors.add(c.getColor());
        }

        for (Room v : board.getRoomList())//For each room in the arena
        {
            if (colors.contains(v.getColor()))//If the color of the room is in the set of color founded before
                players.addAll(v.getPlayerRoomList());//Add all player in this room to the output

        }

        return players; //Return the set of all players

    }

    public void setAfk(boolean afk) {
        this.afk = afk;
    }

    public  Set<Square> lookForRunAround(Player player)
    {
        return state.lookForRunAround(player);
    }


    public  Set<Square> lookForGrabStuff(Player player) // solo grab null
    {
        return state.lookForGrabStuff(player);
    }

    public List<Player> lookForShootPeople(Player player)
    {
        return state.lookForShootPeople(player);
    }

    public List<WeaponCard> checkReload(Player player)
    {
        return state.checkReload(player);
    }

    //#################################
    //Private methods


    //Handle the state of the player and the max score that can be obtained grossing the board(implements a transition function)
    private void checkState()
    {
        if(last >= 0 && last <=2 && frenzy == false) //If the damage points are between 0 and 2
        {
            state = new Normal();//player is in normal condition
            this.overKill = false;
        }
        if (last >= 3 && last <= 5 && frenzy == false) //If the damage points are between 3 and 5
            state = new Adrenalized1(); //player has an adrenaline action

        if (last >= 6 && last <=10 && frenzy == false)//If the damage points are between 6 and 10
            state = new Adrenalized2(); //player has the two adrenaline action

        if(last == 11 && frenzy == false) //If the damage points are 11
            state = new Death(); //player is death

        if(last == 12 && frenzy == false) //If the damage points are 12
            this.overKill = true; //player is overKilled
        if(frenzy == true)
            state = new FRENZY();

        //Handle the max score that can be obtained
        //It sees the number of deaths of the player
        if (deathsCounter == 1)
        {
            scorePoint[0] = 6;
            scorePoint[1] = 4;
            scorePoint[2] = 2;
            scorePoint[3] = 1;
            scorePoint[4] = 1;
            scorePoint[5] = 1;
        }
        if (deathsCounter == 2)
        {
            scorePoint[0] = 4;
            scorePoint[1] = 2;
            scorePoint[2] = 1;
            scorePoint[3] = 1;
            scorePoint[4] = 1;
            scorePoint[5] = 1;
        }
        if (deathsCounter == 3)
        {
            scorePoint[0] = 2;
            scorePoint[1] = 1;
            scorePoint[2] = 1;
            scorePoint[3] = 1;
            scorePoint[4] = 1;
            scorePoint[5] = 1;
        }
        if (deathsCounter >= 4)
        {
            scorePoint[0] = 1;
            scorePoint[1] = 1;
            scorePoint[2] = 1;
            scorePoint[3] = 1;
            scorePoint[4] = 1;
            scorePoint[5] = 1;
        }
        if (frenzy == true && flipped)//If the player is frenzy
        {
            scorePoint[0] = 2; //Insert the scores of the frenzy mode
            scorePoint[1] = 1;
            scorePoint[2] = 1;
            scorePoint[3] = 1;
            scorePoint[4] = 1;
            scorePoint[5] = 1;
        }
    }

    //Take a list of players disordered and returns a list ordered based on who did for first the damage
    private List<ColorId> resolveParity(Set<ColorId> players)
    {
        List<ColorId> playersOrd = new LinkedList<>();//Create the list that will contain the ordered players


        for (int i =0; i<12; i++) //For each damage points
        {
            if (players.contains(damageCounter[i]) && !playersOrd.contains(damageCounter[i]))//If the player that he did that points isn't in the list
                playersOrd.add(damageCounter[i]); //Add him
        }

        return playersOrd; //Return the ordered list

    }

    public StatePlayer getState() {
        return state;
    }

//  Increments of one the counter that represent the number of death of player

    private void incrementDeathsCount()
    {
        deathsCounter++;
        if (frenzy) //If the player is dead during the mode frenzy
        {
            flipped = true;//His board are flipped
        }
        checkState();//check the state of the player
    }


}