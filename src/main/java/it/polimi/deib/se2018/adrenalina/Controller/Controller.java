package it.polimi.deib.se2018.adrenalina.Controller;

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;

import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.View.Connection;
import it.polimi.deib.se2018.adrenalina.View.Observer;
import it.polimi.deib.se2018.adrenalina.View.View;
import it.polimi.deib.se2018.adrenalina.communication_message.*;

import it.polimi.deib.se2018.adrenalina.communication_message.message_asking_controller.*;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.UpdateModel;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class Controller implements Observer<ResponseInput>
{


    public static final  Map<ColorId, Set<ColorId>> roundDamageList = new HashMap<>(); // lista dei giocatori che ho attaccato io sono il giocatore dato dal ColorId chiave

    private boolean frenzy;
    private boolean salta;
    private Setup setup;
    private Player termi; //is the terminator
    private View virtualView;
    private boolean firstRound=true;
    private ResponseInput msg;
    private boolean endGame;
    private GameBoard g1=null;
    private Player roundPlayer=null;
    private int skullCounter;
    private boolean terminatorMode = false;
    private int codeArena;
    public static boolean first;
    //Controller deve avere un riferimento alla virtual view
    /*
    Quando il controller deve richiedere un input prima crea un thread che
    esegue in parallelo i metodi requestInput e getResponseWithInputs della virtual view e poi passa la risposta
    attraverso il metodo update
    Poi si mette in pausa fino a quando il thread non finisce
    */

    private ExecutorService executor = Executors.newFixedThreadPool(1); //numero max thread contemporanei

    public Map<ColorId, Set<ColorId>> getRoundDamageList() {
        return roundDamageList;
    }

    /**
     * This method say how many players are connected
     * Hint: using when the phase of login is completed
     * @return number of player connected
     */
    public int numberOfPlayer()
    {

        return virtualView.getConnections().size();
    }


    /**
     * This method say how many players are connected and active(online)
     * Hint: using when the phase of login is completed
     * @return number of player connected and active
     */
    public int numberOfPlayerActive()
    {

        int cont = 0;
        for (Connection t:virtualView.getConnections())
        {
            if (t.isActive())
                cont++;
        }
        return cont;
    }


    /**
     * This method returns the hero comment of player represented by an index
     * Hint: using when the phase of login is completed
     * @param index represents the number of the player which you want the hero comment
     * @return the hero comment of the player represented by the index passed
     * @throws IndexOutOfBoundsException if index is wrong
     */
    public String getHeroComment(int index) throws IndexOutOfBoundsException
    {
        if (virtualView.getConnections().size()< index || index <= 0)
            throw new IndexOutOfBoundsException();

        return virtualView.getConnections().get(index -1).getAction_hero_comment();
    }

    /**
     * This method returns the name of the player represented by an index
     * Hint: using when the phase of login is completed
     * @param index represents the number of the player which you want the name that he inserted on client
     * @return the name of the player represented by the index passed
     * @throws IndexOutOfBoundsException if index is wrong
     */
    public String getName(int index) throws IndexOutOfBoundsException
    {
        if (virtualView.getConnections().size()< index || index <= 0)
            throw new IndexOutOfBoundsException();

        return virtualView.getConnections().get(index -1).getName();
    }

    /**
     * This method returns the colorId of player represented by an index
     * Hint: using when the phase of login is completed
     * @param index represents the number of the player which you want the colorId
     * @return colorId of the player represented by the index passed
     * @throws IndexOutOfBoundsException if index is wrong
     */
    public ColorId getColorOfPlayer(int index) throws IndexOutOfBoundsException
    {
        if (virtualView.getConnections().size()< index || index <= 0)
            throw new IndexOutOfBoundsException();

        return virtualView.getConnections().get(index -1).getPlayer();
    }


    public Controller(boolean terminatorMode,int codeArena,int skullCounter,View virtualView)
    {
        this.setup = new Setup(this);
        this.terminatorMode = terminatorMode;
        this.codeArena=codeArena;
        this.skullCounter = skullCounter;
        this.virtualView = virtualView;

    }

    /**
     * this method is used to start the game
     */
    public void startGame()
    {
        g1= new GameBoard(setup.createWeaponCardStack(),setup.createPowerUpStack(),codeArena,skullCounter,setup.createAmmoTilesStack());
        Player firstFrenzy = null;
        setup.replenishBoard(g1);


        if(g1.getAllPlayer().size()<5)
            g1.setTerminatorMode(terminatorMode);

        if(g1.isTerminatorMode()) {
            termi = new Player(ColorId.PURPLE, "teminator", "terminator", false);
            g1.setTermi(termi);
        }
        //aggiungi terminator in updatemodel


        g1.setAllPlayerList(setup.createPlayers());

        while (!endGame && numberOfPlayerActive()>2)
        {
            if(roundPlayer != null && frenzy)
            {
                endGame = true; //if the last player that have played is now on frenzy this is the last round for everyone
            }

            if(g1!= null)
            {
                for (Player p : g1.getAllPlayer())
                {
                    try
                    {
                        salta = false;
                        if(frenzy)
                        {
                            if(!(p.equals(firstFrenzy)))
                            {
                            startRoundFrenzy(p);
                            if(firstFrenzy==null)
                                firstFrenzy=p;
                            }
                        }
                        else
                         startRound(p);
                    }
                    catch (InterruptedException|ExecutionException e)
                    {
                        Thread.currentThread().interrupt();
                    }
                }
                firstRound=false;
            }
        }
        //
        if(numberOfPlayerActive()>2)
            calculateFinalScore();
        else{
            calculateRapidScore();
        }

    }

    // AUXILIARY FUNCTIONS


    /**
     * this method will set the point of all player when another player is death
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private void getPointAndRespawn() throws ExecutionException, InterruptedException {
        Map<ColorId,Integer> temppoint;
        List<ColorId> lastKillerColor= new LinkedList<>();
        //chiedi come fare calcolo punteggi
        for(Player p : g1.getAllPlayer())
        {
            if(p.isDead())
            {




                if(g1.getSkullCounter()>0)
                    g1.setSkullCounter(g1.getSkullCounter()-1);




                temppoint = p.calculateScoreForEachPlayer();
                for(Player c : g1.getAllPlayer()) {
                    if (c.getColor()!=null && temppoint.get(c.getColor())!= null){
                            c.setScore(c.getScore() + temppoint.get(c.getColor()));
                        if (p.getDamageCounter()[10].equals(c.getColor())) {

                            if (lastKillerColor.contains(c.getColor()))
                                c.setScore(c.getScore() + 1);
                            g1.setKillShotTrack(c.getColor(), 1);

                            if (p.getDamageCounter()[11] != null) {
                                if (p.getDamageCounter()[11].equals(c.getColor())) {
                                    g1.getKillShotTrack().get(g1.getKillShotTrack().size() - 1).setPointCounter(2);
                                    c.addMark(p.getColor());
                                }
                            }
                            lastKillerColor.add(c.getColor());
                        }
                        //if terminator mode is active
                        if (g1.isTerminatorMode()) {
                            termi.setScore(termi.getScore() + temppoint.get(termi.getColor()));
                            if (p.getDamageCounter()[10].equals(termi.getColor())) {

                                if (lastKillerColor.contains(termi.getColor()))
                                    termi.setScore(termi.getScore() + 1);
                                g1.setKillShotTrack(termi.getColor(), 1);


                                lastKillerColor.add(termi.getColor());
                            }
                        }
                    }
                }



                if(g1.getSkullCounter()==0)
                {
                    frenzy=true;
                }

                askForRespawn(p);
            }

        }

        if(g1.isTerminatorMode())
        {
            if(termi.isDead())
            {
                if(g1.getSkullCounter()>0)
                    g1.setSkullCounter(g1.getSkullCounter()-1);




                temppoint = termi.calculateScoreForEachPlayer();
                for(Player c : g1.getAllPlayer())
                {
                    c.setScore(temppoint.get(c.getColor()));
                    if(termi.getDamageCounter()[10].equals(c.getColor())) {

                        if( lastKillerColor.contains(c.getColor()))
                            c.setScore(c.getScore()+1);
                        g1.setKillShotTrack(c.getColor(), 1);

                        if (termi.getDamageCounter()[11].equals(c.getColor())) {
                            g1.getKillShotTrack().get(g1.getKillShotTrack().size() - 1).setPointCounter(2);
                            c.addMark(termi.getColor());
                        }
                        lastKillerColor.add(c.getColor());
                    }
                }



                if(g1.getSkullCounter()==0)
                {
                    frenzy=true;
                }

                askForRespawn(termi);

            }
        }


        if(frenzy)
        {
            for(Player c : g1.getAllPlayer())
            {
                c.setFrenzy(true);
            }
        }

    }

    /**
     * auxiliary function to check if a boolean array has at least one available mode
     * @param arr the array to check
     * @return true only if there is at least one element true
     */
    private boolean checkBooleanArray(boolean[] arr)
    {
        for(boolean i : arr)
        {
            if (i)
                return true;
        }
    
        return false;
    }

    /**
     * auxiliary method to change a set of square into a list of square
     * @param squareSet the set of square that wfrom wich we take the square
     * @return a list of squares
     */
    private List<String> changeToList (Set<Square> squareSet)
    {
        List<String> stringList = new ArrayList<>();

        for (Square squareIterate : squareSet)
        {
            stringList.add(squareIterate.toStringCoordinates());
        }

        return stringList;
    }

    /**
     * auxiliary method that check if a player have a grenade power up 
     * @param pg player to check
     * @return true if the player has at least one grenade
     */
    private boolean checkPlayerForGranade(ColorId pg)
    {
        Player currentPlayer=null;
        List<PowerUpCard> pcList= new LinkedList<>();
        boolean ret=false;
        for (Player pla : g1.getAllPlayer() )
        {
            if(pla.getColor().equals(pg))
                currentPlayer=pla;
        }
        if(currentPlayer!=null)
        {
            pcList = currentPlayer.getPowerupCardList();
            for (PowerUpCard cp : pcList) {
                if (cp.powerToString().equals("Granata Venom:BLUE") || cp.powerToString().equals("Granata Venom:YELLOW") || cp.powerToString().equals("Granata Venom:RED")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    private void drawPowerup(boolean respawn){
        PowerUpCard pc= g1.drawPowerUpCard();

        if(respawn)
            roundPlayer.addPowerUpRespawn(pc);
        else
            roundPlayer.addPowerUp(pc);
        pc.setPlayer(roundPlayer);
    }


    // END OF AUXILIARY FUNCTIONS


    /**
     * check if the player is afk
     * @return true if player is afk
     */
    private boolean checkForAfk()
    {
        if(msg instanceof Afk )
        {
            roundPlayer.setAfk(true);
            virtualView.sendMessageGenericBroadcast(new GenericMessage(roundPlayer.getName() + " " + roundPlayer.getColor() + " è afk"));
        }
        return roundPlayer.isAfk();
    }

    //this method start round only for frenzy
    private void startRoundFrenzy(Player rp) throws ExecutionException, InterruptedException {

        for(Player p : g1.getAllPlayer())
        {

            if (p.equals(rp))
                roundPlayer=p;
        }

       if(roundPlayer.isFirst())
       {
           first=true;
       }

        updateModel();


        List<Player> allPlayer= g1.getAllPlayer();
        int indexLastPlayer = allPlayer.size()-1;


        if(!first) {
            List<Callable<Boolean>> callableList = new LinkedList<>();
            callableList.add(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {

                    try {
                        virtualView.requestInput(new StartFrenesy(), rp.getColor());
                        return true;
                    } catch (Exception e) {
                        return false;
                    }

                }
            });

            boolean s = executor.invokeAny(callableList);


            if (!s){ salta=true;
                    return;}
            if (checkForAfk())
                return;
            try
            {
                switcherFrenzy(rp.getColor());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            //end of user round
            if(g1.isTerminatorMode())
                executeTerminator();

            for(Player p: g1.getAllPlayer())
            {
                p.setAfk(false);
            }

            msg=null;
            getPointAndRespawn();
            setup.replenishBoard(g1);
        }
        else
            {
                List<Callable<Boolean>> callableList = new LinkedList<>();
                callableList.add(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {

                        try {
                            virtualView.requestInput(new StartFrenesyB(), rp.getColor());
                            return true;
                        } catch (Exception e) {
                            return false;
                        }

                    }
                });

                boolean s = executor.invokeAny(callableList);


                if (!s){ salta=true;
                    return;}
                if (checkForAfk())
                    return;

                try
                {
                    switcherFrenzyB(rp.getColor());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                //end of user round
                if(g1.isTerminatorMode())
                    executeTerminator();

                for(Player p: g1.getAllPlayer())
                {
                    p.setAfk(false);
                }

                msg=null;
                getPointAndRespawn();
                setup.replenishBoard(g1);
            }

    }


    /**
     * this method start the round of a player
     * @param rp player of the round
     * @throws InterruptedException 
     * @throws ExecutionException
     */
    private void startRound(Player rp) throws InterruptedException, ExecutionException {

        for (Player p : g1.getAllPlayer()) {

            if (p.equals(rp))
                roundPlayer = p;
        }
        updateModel();
        if (roundPlayer.isFirstRound()) {
            askForFirstSpawn();

        }
        if (!roundPlayer.isAfk()){
            List<Callable<Boolean>> callableList = new LinkedList<>();
        callableList.add(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {

                try {
                    virtualView.requestInput(new RequestStartRound(), rp.getColor());
                    return true;
                } catch (Exception e) {
                    return false;
                }

            }
        });

        boolean s = executor.invokeAny(callableList);


        if (!s) {
            salta = true;
            return;
        }

        checkForAfk();
        for (Player p : g1.getAllPlayer()) {
            p.setAfk(false);
        }
        msg = null;


        try {
            switcher(rp.getColor());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //end of user round

        if (g1.isTerminatorMode())
            executeTerminator();

        for (Player p : g1.getAllPlayer()) {
            p.setAfk(false);
        }

        msg = null;
        getPointAndRespawn();
        setup.replenishBoard(g1);
    }
        for(Player p: g1.getAllPlayer())
        {
            p.setAfk(false);
        }
        msg=null;
    }



    
//Switch function
    /**
     * the switcher wait for a request from the view and then it execute the called method
     * @param player color of the player that ask from the virtual view
     * @throws Exception
     */
    private void switcherFrenzy(ColorId player) throws Exception
    {


        virtualView.getResponseWithInputs(player);

        MessageNet messageNet = msg;

        while (!(messageNet instanceof EndRound)&& !roundPlayer.isAfk() && !salta)
        {
            if (messageNet instanceof AskMoveAround)
            {
                runAround(false);
            }
            else if (messageNet instanceof AskGrab)
            {
                grab();
            }
            else if (messageNet instanceof AskShoot)
            {
                shotEnemy();
            }
             else if (messageNet instanceof AskReload)
            {
                reload();
            }
            else if (messageNet instanceof AskForAllPowerups)
            {
                askForAllPowerUp();
            }
            else if (messageNet instanceof AskTargetingScope)
            {
                askForPowerUpTargettingScope();
            }
            else if (messageNet instanceof AskPowerUPTeleOrNew)
            {
                askForPowerUpTeleportOrNewton();
            }
            else if(messageNet instanceof MoveBeforeShoot)
            {
                if(!first)
                {
                    runSingleFrenzy(true);

                }
                else
                {
                    runSingleFrenzy(false);

                }
            }



            updateModel();

            if(!roundPlayer.isAfk() && !salta){
                virtualView.getResponseWithInputs(player);

                messageNet = msg;}

        }
        if(!roundPlayer.isAfk() && !salta)
            askForPowerUpTagBackGranade();

        for(Player p: g1.getAllPlayer())
        {
            p.setAfk(false);
        }
        msg=null;
        salta=false;
        // at the end of round




    }


    /**
     * the switcher wait for a request from the view and then it execute the called method, this is frenzy case when first is true
     * @param player color of the player that ask from the virtual view
     * @throws Exception
     */
    private void switcherFrenzyB(ColorId player) throws Exception
    {


        virtualView.getResponseWithInputs(player);

        MessageNet messageNet = msg;

        while (!(messageNet instanceof EndRound)&& !roundPlayer.isAfk() && !salta)
        {
           /* if (messageNet instanceof AskMoveAround)
            {
                runAround(false);    in questo caso il giocatore non può usare il runaround
            }*/
           if (messageNet instanceof AskGrab)
            {
                grab();
            }
            else if (messageNet instanceof AskShoot)
            {
                shotEnemy();
            }
            else if (messageNet instanceof AskReload)
            {
                reload();
            }
            else if (messageNet instanceof AskForAllPowerups)
            {
                askForAllPowerUp();
            }
            else if (messageNet instanceof AskTargetingScope)
            {
                askForPowerUpTargettingScope();
            }
            else if (messageNet instanceof AskPowerUPTeleOrNew)
            {
                askForPowerUpTeleportOrNewton();
            }
           else if(messageNet instanceof MoveBeforeShoot)
           {
               if(!first)
               {
                   runSingleFrenzy(true);

               }
               else
               {
                   runSingleFrenzy(false);

               }
           }



            updateModel();

            if(!roundPlayer.isAfk() && !salta){
                virtualView.getResponseWithInputs(player);

                messageNet = msg;}


        }
        if(!roundPlayer.isAfk() && !salta)
            askForPowerUpTagBackGranade();

        for(Player p: g1.getAllPlayer())
        {
            p.setAfk(false);
        }
        msg=null;
        salta=false;


    }


    /**
     * the switcher wait for a request from the view and then it execute the called method
     * @param player color of the player that ask from the virtual view
     * @throws Exception
     */
    private void switcher(ColorId player) throws Exception
    {
        MessageNet messageNet = null;

virtualView.getResponseWithInputs(player);

     messageNet = msg;


        while (!(messageNet instanceof EndRound)&& !roundPlayer.isAfk() && !salta)
        {
            if (messageNet instanceof AskMoveAround)
            {
                runAround(false);
            }
            else if (messageNet instanceof AskGrab)
            {
                grab();
            }
            else if (messageNet instanceof AskShoot)
            {
                if(roundPlayer.getState() instanceof  Adrenalized2)
                {
                    askMove();
                }
                shotEnemy();

            }
            else if (messageNet instanceof AskReload)
            {
                reload();
            }
            else if (messageNet instanceof AskForAllPowerups)
            {
               askForAllPowerUp();
            }
            else if (messageNet instanceof AskTargetingScope)
            {
                askForPowerUpTargettingScope();
            }
            else if (messageNet instanceof AskPowerUPTeleOrNew)
            {
                askForPowerUpTeleportOrNewton();
            }




            updateModel();
            checkForAfk();


            if(!roundPlayer.isAfk() && !salta)
            {
               virtualView.getResponseWithInputs(player);

                    messageNet = msg;

            }


        }

        if(!roundPlayer.isAfk() && !salta)
            askForPowerUpTagBackGranade();

        for(Player p: g1.getAllPlayer())
        {
            p.setAfk(false);
        }

        msg=null;
        salta=false;

    // at the end of round




    }

    /**
     * check if player have one power up teleport or newton and for every card ask if they want to use it and call the method to use it
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void askForPowerUpTeleportOrNewton() throws InterruptedException, ExecutionException {
        boolean vuota=false;

        boolean t;
        List<String> powerUpList = new LinkedList<>();
        for (PowerUpCard pc : roundPlayer.getPowerupCardList()) 
        {
            if(pc.getName().equals("Raggio Cinetico") || pc.getName().equals("Teletrasporto"))
                powerUpList.add(pc.powerToString());
        }
        if (powerUpList.isEmpty())
            vuota = true;
        if (!vuota) {
            List<Callable<Boolean>> callableList = new LinkedList<>();

            callableList.add(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {

                    try{virtualView.requestInput(new RequestPowerUp(powerUpList), roundPlayer.getColor());
                    virtualView.getResponseWithInputs(roundPlayer.getColor());
                        return true;
                    }
                    catch (Exception e)
                    {
                        return false;
                    }


                }
            });



            boolean s = executor.invokeAny(callableList);
            if(!s) {
                salta=true;
                return;
            }
            if(checkForAfk())
               return;


            ResponsePowerUp risp = (ResponsePowerUp) msg;


            for (String pc : risp.getChosenPowerUpList()) {
                if (pc.equals("Teletrasporto:BLUE") || pc.equals("Teletrasporto:YELLOW") || pc.equals("Teletrasporto:RED")) {
                    askForTeleport(pc);
                }

                if (pc.equals("Raggio Cinetico:BLUE") || pc.equals("Raggio Cinetico:YELLOW") || pc.equals("Raggio Cinetico:RED")) {
                    askForNewton(pc);
                }
            }
            // send end chicle message
            List<Callable<Boolean>> callableListA = new LinkedList<>();
            callableListA.add(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    try {
                        virtualView.requestInput(new End(), roundPlayer.getColor());
                        return true;
                    }
             catch(Exception e)
             {
                 return false;
             }
                }
            });



            Boolean end =  executor.invokeAny(callableListA);
            if(!end) {
             salta=true;
                return;
            }
        }
        else
        {
            List<Callable<Boolean>> callableListA = new LinkedList<>();
            callableListA.add(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {

                   try {
                       virtualView.requestInput(new RequestPowerUp(powerUpList), roundPlayer.getColor());


                       return true;
                   }
                catch(Exception e)
                {
                    return false;
                }
                }
            });


            t  =  executor.invokeAny(callableListA);
            if(!t) {
             salta=true;
                return;
            }
        }
    }

    /**
     * check if player have one power up grenade and for every card ask if they want to use it and call the method to use it
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void askForPowerUpTagBackGranade() throws InterruptedException, ExecutionException {

        Set<ColorId> attackedPlayers = new HashSet<>();
        attackedPlayers = roundDamageList.get(roundPlayer.getColor());
        Set<ColorId> filteredPlayer=null;

        if(attackedPlayers!=null)
            filteredPlayer = attackedPlayers.stream().filter(colorId -> checkPlayerForGranade(colorId)).collect(Collectors.toSet());
if(filteredPlayer!=null){
        for (ColorId granadeAttacked : filteredPlayer) {
            boolean vuota = false;
            List<String> powerUpList = new LinkedList<>();
            Player granadeAttackedPlayer = null;
            for (Player p : g1.getAllPlayer()) {
                if (p.getColor().equals(granadeAttacked))
                    granadeAttackedPlayer = p;
            }
            if(granadeAttackedPlayer!= null)
            {
                for (PowerUpCard pc : granadeAttackedPlayer.getPowerupCardList())
                {
                    if (pc.getName().equals("Granata Venom"))
                        powerUpList.add(pc.powerToString());
                }
            }
            if (powerUpList.isEmpty())
                vuota = true;
            if (!vuota) {
//send message to inform view
                List<Callable<Boolean>> callableIniz = new LinkedList<>();
                callableIniz.add(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {

                        try{
                            virtualView.requestInput(new RequestToUseGrenade(), granadeAttacked);


                            return true;
                        }
                        catch (Exception e)
                        {
                            return false;
                        }
                    }
                });
                Boolean si = executor.invokeAny(callableIniz);
                if(!si) {
                 salta=true;
                    return;
                }

                List<Callable<Boolean>> callableList = new LinkedList<>();
                callableList.add(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {

                        try{
                            virtualView.requestInput(new RequestPowerUp(powerUpList), granadeAttacked);
                            virtualView.getResponseWithInputs(granadeAttacked);

                            return true;
                        }
                        catch (Exception e)
                        {
                            return false;
                        }
                    }
                });



                Boolean s = executor.invokeAny(callableList);
                if(!s) {
                 salta=true;
                    return;
                }


                ResponsePowerUp risp = (ResponsePowerUp) msg;


                for (String pc : risp.getChosenPowerUpList()) {
                    if(granadeAttackedPlayer!=null){
                        if (pc.equals("Granata Venom:BLUE") || pc.equals("Granata Venom:YELLOW") || pc.equals("Granata Venom:RED")) {
                            askForTagBackGranade(pc,granadeAttackedPlayer);
                        }

                    }
                }
                // sends end cicle message

                List<Callable<Boolean>> callableListA = new LinkedList<>();
                callableListA.add(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        try {
                            virtualView.requestInput(new End(), roundPlayer.getColor());
                            return true;
                        }
                        catch(Exception e)
                        {
                            return false;
                        }
                    }
                });



                Boolean end =  executor.invokeAny(callableListA);
                if(!end) {
                 salta=true;
                    return;
                }
            }

        }}
    }


    /**
     * check if player have one power up targetting scope and for every card ask if they want to use it and call the method to use it
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void askForPowerUpTargettingScope() throws InterruptedException, ExecutionException {
        boolean empty=false;
        List<String> powerUpList = new LinkedList<>();
        for (PowerUpCard pc : roundPlayer.getPowerupCardList()) //in this way i save in power up list all power up in player
        {
            if(pc.getName().equals("Mirino"))
                powerUpList.add(pc.powerToString());
        }

        if(roundDamageList.get(roundPlayer.getColor()).isEmpty())
            empty=true;
        if (powerUpList.isEmpty())
            empty = true;
        if (!empty) {
            List<Callable<Boolean>> callableList = new LinkedList<>();

            callableList.add(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {

                   try {
                       virtualView.requestInput(new RequestPowerUp(powerUpList), roundPlayer.getColor());
                  
                    virtualView.getResponseWithInputs(roundPlayer.getColor());

                    return true;
                   }
                   catch (Exception e)
                   {
                       return  false;
                   }
                }
            });


            boolean s = executor.invokeAny(callableList);
            if(!s) {
             salta=true;
                return;
            }
            if(checkForAfk())
               return;


            ResponsePowerUp risp = (ResponsePowerUp) msg;


            for (String pc : risp.getChosenPowerUpList()) {
                if (pc.equals("Mirino:BLUE") || pc.equals("Mirino:YELLOW") || pc.equals("Mirino:RED")) {
                    askForTargettingScope(pc);
                }

            }
            // sends end cicle message

            List<Callable<Boolean>> callableListA = new LinkedList<>();
            callableListA.add(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    try {
                        virtualView.requestInput(new End(), roundPlayer.getColor());
                        return true;
                    }
                    catch(Exception e)
                    {
                        return false;
                    }
                }
            });



            Boolean end =  executor.invokeAny(callableListA);
            if(!end) {
             salta=true;
                return;
            }
        }
        else
        {
            List<Callable<Boolean>> callableList = new LinkedList<>();
            callableList.add(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {

                    try {
                        virtualView.requestInput(new RequestPowerUp(powerUpList), roundPlayer.getColor());

                        return true; }
                    catch (Exception e)
                    {
                        return  false;
                    }
                }
            });


            Boolean s =  executor.invokeAny(callableList);
            if(!s) {
             salta=true;
                return;
            }
        }
    }

    /**
     * this method ask the player on which square he want to move using teleport
     * @param choice the chosen card
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void askForTeleport(String choice) throws InterruptedException, ExecutionException {
        Teleporter tele=null;
        List<Callable<Boolean>> callableList = new LinkedList<>();
        callableList.add(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {

                try{   virtualView.requestInput(new RequestTeleporter(g1, roundPlayer), roundPlayer.getColor());
                virtualView.getResponseWithInputs(roundPlayer.getColor());

                return true;}
                catch (Exception e)
                {
                    return false;
                }
            }
        });



         boolean ric = executor.invokeAny(callableList);
        if(!ric){
            salta=true;
            return;}
        if(checkForAfk())
            return;


        ResponseTeleporter sq = (ResponseTeleporter) msg;

        int i=0;
        for (PowerUpCard cardtp : roundPlayer.getPowerupCardList() )
        {

            if(cardtp.powerToString().equals(choice)){
                tele = (Teleporter) roundPlayer.usePowerUp(i);
            }
            i++;
        }
        if(tele != null)
            tele.usePowerUp(sq.getX(), sq.getY());

        }

    /**
     *  this method ask the player which enemy and on which square he want to move this enemy using newton
     * @param choice the chosen card
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void askForNewton(String choice) throws InterruptedException, ExecutionException {
        int index=0;
        Newton card=null;
        int i=0;
        for (PowerUpCard cardtp : roundPlayer.getPowerupCardList() )
        {

            if(cardtp.powerToString().equals(choice)){
                index = i;
                card= (Newton)cardtp;
            }
            i++;
        }
        Newton finalCard = card;
        List<Callable<Boolean>> callableList = new LinkedList<>();
        callableList.add(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {

                try {
                    virtualView.requestInput(new RequestNewton(finalCard.checkMoveTarget(roundPlayer.getSquare().getGameBoard())), roundPlayer.getColor()); // come gli passo la richiesta ?
                    virtualView.getResponseWithInputs(roundPlayer.getColor());

                    return true;
                }
                catch (Exception e)
                {
                    return false;
                }
            }
        });



        Boolean ric =  executor.invokeAny(callableList);
        if(!ric){
            salta=true;
            return;}
        if(checkForAfk())
               return;


        ResponseNewton sq = (ResponseNewton) msg;


        Player target=null;
        for(Player p : g1.getAllPlayer())
        {
            if (p.getColor().equals(sq.getTarget()))
                target=p;
        }
        roundPlayer.usePowerUp(index);
        if(card!=null)
            card.usePowerUp(target,sq.getX(),sq.getY());

        //aggiungi a pila scarti

    }

    /**
     * this method ask the player on which enemy he want to use the targetting scope
     * @param choice the chosen card
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void askForTargettingScope(String choice) throws InterruptedException, ExecutionException {
        int index=0;
        TargettingScope card=null;
        int i=0;
        for (PowerUpCard cardtp : roundPlayer.getPowerupCardList() )
        {

            if(cardtp.powerToString().equals(choice)){
                index = i;
                card= (TargettingScope) cardtp;
            }
            i++;
        }
        TargettingScope finalCard = card;


        List<Callable<Boolean>> callableList = new LinkedList<>();
        callableList.add(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {

                try{
                virtualView.requestInput(new RequestTargettingScope(roundDamageList.get(finalCard.getPlayer().getColor()),finalCard.check()), roundPlayer.getColor());
                virtualView.getResponseWithInputs(roundPlayer.getColor());

                return true;}
                catch (Exception e)
                {
                    return false;
                }
            }
        });

       

        Boolean ric = executor.invokeAny(callableList);
        if(!ric){
            salta=true;
            return;}
        if(checkForAfk())
               return;


        ResponseTargettingScope sq = (ResponseTargettingScope) msg;


        TargettingScope scope = (TargettingScope) roundPlayer.usePowerUp(index);
        scope.usePowerUp(sq.getTargetBasicMode(),sq.getTargetAmmo());
        //aggiungi a pila scarti

    }

    /**
     *  this method ask to  temp player if he want to use the tag back grenade on roundplayer
     * @param choice the chosen card
     * @param tempPlayer one of the attacked player
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void askForTagBackGranade(String choice,Player tempPlayer) throws InterruptedException, ExecutionException
    {
        TagbackGranade cardpower=null;
        for(PowerUpCard pc : tempPlayer.getPowerupCardList())
        {
            if(pc.powerToString().equals(choice))
                cardpower=(TagbackGranade) pc;
        }


        if( cardpower!=null)
        {

            cardpower.usePowerUp(roundPlayer);
            //aggiungi a pila scarti chiadi a gio
            tempPlayer.usePowerUp(tempPlayer.getPowerupCardList().indexOf(cardpower));
        }


    }


    /**
     * this method is used when you want to use a powerupas an ammo
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void askForAllPowerUp() throws InterruptedException, ExecutionException
    {
        boolean empty=false;
        int index=0;

        List<String> powerUpList = new LinkedList<>();
        for (PowerUpCard pc : roundPlayer.getPowerupCardList())
        {

                powerUpList.add(pc.powerToString());
        }

        if (powerUpList.isEmpty())
            empty = true;
        if (!empty) {
            List<Callable<Boolean>> callableList = new LinkedList<>();
            callableList.add(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {

                    try{virtualView.requestInput(new RequestPowerUp(powerUpList), roundPlayer.getColor());
                    virtualView.getResponseWithInputs(roundPlayer.getColor());

                    return true;}
                    catch (Exception e)
                    {
                        return false;
                    }
                }
            });

           

           boolean s = executor.invokeAny(callableList);
            if(!s){
                salta=true;
                return;}
            if(checkForAfk())
               return;


            ResponsePowerUp risp = (ResponsePowerUp) msg;


            for (String pc : risp.getChosenPowerUpList()) {
                int i=0;
                for (PowerUpCard cardtp : roundPlayer.getPowerupCardList() )
                {

                    if(cardtp.powerToString().equals(pc))
                    {
                        index = i;

                    }
                    i++;
                }
                roundPlayer.usePowerUp(index);
                if (pc.contains("BLUE"))
                {
                    roundPlayer.setAmmoBlue(roundPlayer.getAmmoBlue()+1);
                }
                if (pc.contains("RED"))
                {
                    roundPlayer.setAmmoRed(roundPlayer.getAmmoRed()+1);
                }
                if (pc.contains("YELLOW"))
                {
                    roundPlayer.setAmmoYellow(roundPlayer.getAmmoYellow()+1);
                }



            }
            // sends end cicle message

            List<Callable<Boolean>> callableListA = new LinkedList<>();
            callableListA.add(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    try {
                        virtualView.requestInput(new End(), roundPlayer.getColor());
                        return true;
                    }
                    catch(Exception e)
                    {
                        return false;
                    }
                }
            });



            Boolean end =  executor.invokeAny(callableListA);
            if(!end){
                salta=true;
                return;}

        }
        else
        {
            List<Callable<Boolean>> callableList = new LinkedList<>();
            callableList.add(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {

                    try {
                        virtualView.requestInput(new RequestPowerUp(powerUpList), roundPlayer.getColor());

                        return true; }
                    catch (Exception e)
                    {
                        return  false;
                    }
                }
            });


            Boolean s =  executor.invokeAny(callableList);
            if(!s){
                salta=true;
                return;}

        }
    }

    /**
     * this method is used to spawn the terminator
     * @param index index of the power up to use for spawn
     */
    private void spawnTerminator(int index)
    {

        Square resp = null;

        if (index==2) {
            try {
                resp = g1.getArena().getSquare(3,3);
            } catch (SquareNotInGameBoard squareNotInGameBoard) {
                System.out.println(squareNotInGameBoard);
            }
        }

        if (index==3) {
            try {
                resp = g1.getArena().getSquare(4,1);
            } catch (SquareNotInGameBoard squareNotInGameBoard) {
                System.out.println(squareNotInGameBoard);
            }
        }
        if (index==1) {
            try {
                resp = g1.getArena().getSquare(1,2);
            } catch (SquareNotInGameBoard squareNotInGameBoard) {
                System.out.println(squareNotInGameBoard);
            }
        }

        termi.setSquare(resp);
        if(resp!=null)
            MethodsWeapons.moveTarget(termi, resp.getX(), resp.getY());


    }
    /**
     * this method spawn the player
     * @param index of the power up to use for spawn
     */
    public void spawn(int index)
    {


    PowerUpCard powercard = roundPlayer.usePowerUp(index);
    Square resp = null;

           if (powercard.getColor().equals(Color.BLUE)) {
               try {
                   resp = g1.getArena().getSquare(3,3);
               } catch (SquareNotInGameBoard squareNotInGameBoard) {
                   System.out.println(squareNotInGameBoard);
               }
           }

         if (powercard.getColor().equals(Color.YELLOW)) {
             try {
                 resp = g1.getArena().getSquare(4,1);
             } catch (SquareNotInGameBoard squareNotInGameBoard) {
                 System.out.println(squareNotInGameBoard);
             }
         }
         if (powercard.getColor().equals(Color.RED)) {
             try {
                 resp = g1.getArena().getSquare(1,2);
             } catch (SquareNotInGameBoard squareNotInGameBoard) {
                 System.out.println(squareNotInGameBoard);
             }
         }

         roundPlayer.setSquare(resp);
         
        if(resp!=null)
             MethodsWeapons.moveTarget(roundPlayer, resp.getX(), resp.getY());



        }

    /**
     * this method ask the player what power up he want to use for respawn
     * @param p the player that has to respawn
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void askForRespawn(Player p) throws InterruptedException, ExecutionException {

        //if first round and firstplayer he have to spawn terminator
        if (p.isFirst() && firstRound && g1.isTerminatorMode())
        {
            List<Callable<Boolean>> callableListA = new LinkedList<>();
            callableListA.add(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception
                {

                    try
                    {
                        virtualView.requestInput(new RespawnTerminator(), p.getColor());


                        return true;}
                    catch(Exception e)
                    {
                        return false;
                    }
                }
            });
            boolean an = executor.invokeAny(callableListA);



            if (!an){ salta=true;
                return;}
            if (checkForAfk())
                return;

            List<Callable<Boolean>> callableList = new LinkedList<>();
            callableList.add(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception
                {

                    try
                    {virtualView.requestInput(new RequestRespawnTerminator(), p.getColor());
                        virtualView.getResponseWithInputs(p.getColor());

                        return true;}
                    catch(Exception e)
                    {
                        return false;
                    }
                }
            });
            boolean s = executor.invokeAny(callableList);



            if (!s){ salta=true;
                    return;}
            if (checkForAfk())
                return;


            ResponseRespawn prove = (ResponseRespawn) msg;
            spawnTerminator(prove.getTargetSpawnPoint());
        }




        if (g1.isTerminatorMode() && p.equals(termi))
        {
            List<Callable<Boolean>> callableListA = new LinkedList<>();
            callableListA.add(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception
                {

                    try
                    {
                        virtualView.requestInput(new RespawnTerminator(), p.getColor());


                        return true;}
                    catch(Exception e)
                    {
                        return false;
                    }
                }
            });
            boolean an = executor.invokeAny(callableListA);



            if (!an){ salta=true;
                return;}
            if (checkForAfk())
                return;

            List<Callable<Boolean>> callableList = new LinkedList<>();
            callableList.add(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {

                   try{ virtualView.requestInput(new RequestRespawnTerminator(), roundPlayer.getColor());
                    virtualView.getResponseWithInputs(roundPlayer.getColor());

                    return true;}
                   catch(Exception e)
                   {
                       return false;
                   }
                }
            });

            boolean s = executor.invokeAny(callableList);
            if (!s){ salta=true;
                    return;}
            if (checkForAfk())
                return;


            ResponseRespawn response = (ResponseRespawn) msg;
            spawnTerminator(response.getTargetSpawnPoint());
        }
        else {
            if(!roundPlayer.isFirstRound())
                drawPowerup(true);

            updateModel();


            List<Callable<Boolean>> callableListA = new LinkedList<>();
            callableListA.add(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {

                    try
                    {
                        virtualView.requestInput(new RequestToRespawn(), p.getColor());

                        return true;
                    }
                    catch (Exception e){
                        return false;
                    }
                }
            });

            boolean an = executor.invokeAny(callableListA);
            if (!an){ salta=true;
                return;}
            if (checkForAfk())
                return;


            List<Color> powerList = new LinkedList<>();
            for (PowerUpCard pc : roundPlayer.getPowerupCardList()) {
                powerList.add(pc.getColor());
            }
            List<Callable<Boolean>> callableList = new LinkedList<>();
            callableList.add(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {

                   try
                   { virtualView.requestInput(new RequestRespawn(powerList), p.getColor());
                    virtualView.getResponseWithInputs(p.getColor());

                    return true;}
                   catch (Exception e){
                       return false;
                   }
                }
            });




            boolean s = executor.invokeAny(callableList);
            if (!s){ salta=true;
                    return;}
            if (checkForAfk())
                return;


            ResponseRespawn response = (ResponseRespawn) msg;
            spawn(response.getTargetSpawnPoint()-1);
            roundPlayer.setFirstRound(false);
            updateModel();

        }
    }

    /**
     * this method ask the player what power up he want to use for spawn
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void askForFirstSpawn() throws InterruptedException, ExecutionException
    {
        if(roundPlayer.getPowerupCardList().isEmpty()){
            drawPowerup(false);
            drawPowerup(true);
        }
    
        askForRespawn(roundPlayer);




    }
    /**
 * this method ask the player where he want to move
 * @throws InterruptedException
 * @throws ExecutionException
 */
private void runAround(boolean terminator) throws InterruptedException, ExecutionException
{ List<Callable<Boolean>> callableList = new LinkedList<>();
    Set<Square> squareToChange= new HashSet<>();

    if(!g1.isTerminatorMode() && !roundPlayer.equals(termi))
        squareToChange = roundPlayer.lookForRunAround(roundPlayer);
    else {
        squareToChange = termi.getSquare().getGameBoard().getArena().squareReachableNoWall(termi.getSquare().getX(), termi.getSquare().getY(), 1);
    }


    Set<Square> finalSquareToChange = squareToChange;
    callableList.add(new Callable<Boolean>()
    {
        @Override
        public Boolean call() throws Exception {

            try {
                virtualView.requestInput(new RequestRunAround(changeToList(finalSquareToChange)), roundPlayer.getColor());
                virtualView.getResponseWithInputs(roundPlayer.getColor());

                return true;
            }
            catch (Exception e){
                return  false;
            }
        }
    });



    boolean s = executor.invokeAny(callableList);
    if(!s){
        salta=true;
        return;}
    if(checkForAfk())
        return;


    ResponseRunAround response = (ResponseRunAround) msg;

    int chosenSquareX = response.getX();
    int chosenSquareY = response.getY();

    if(terminator)
        MethodsWeapons.moveTarget(termi,chosenSquareX,chosenSquareY);
    else
        MethodsWeapons.moveTarget(roundPlayer,chosenSquareX,chosenSquareY);


}
    /**
     * this method ask the player where he want to move
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void askMove() throws InterruptedException, ExecutionException
    { List<Callable<Boolean>> callableList = new LinkedList<>();

        callableList.add(new Callable<Boolean>()
        {
            @Override
            public Boolean call() throws Exception {
                Set<Square> squareToChange = new HashSet<>();

                    squareToChange = roundPlayer.getSquare().getGameBoard().getArena().squareReachableNoWall(roundPlayer.getSquare().getX(), roundPlayer.getSquare().getY(), 1);

                try {
                    virtualView.requestInput(new RequestRunAround(changeToList(squareToChange)), roundPlayer.getColor());
                    virtualView.getResponseWithInputs(roundPlayer.getColor());

                    return true;
                }
                catch (Exception e){
                    return  false;
                }
            }
        });



        boolean s = executor.invokeAny(callableList);
        if(!s){
            salta=true;
            return;}
        if(checkForAfk())
            return;


        ResponseRunAround response = (ResponseRunAround) msg;

        int chosenSquareX = response.getX();
        int chosenSquareY = response.getY();


        MethodsWeapons.moveTarget(roundPlayer,chosenSquareX,chosenSquareY);


    }
    /**
     * this method ask the player where he want to move
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void runSingleFrenzy(boolean singolo) throws InterruptedException, ExecutionException
    { List<Callable<Boolean>> callableList = new LinkedList<>();
        Set<Square> squareToChange = new HashSet<>();
        if(singolo)
            squareToChange = roundPlayer.getSquare().getGameBoard().getArena().squareReachableNoWall(roundPlayer.getSquare().getX(), roundPlayer.getSquare().getY(), 1);
        else
            squareToChange = roundPlayer.getSquare().getGameBoard().getArena().squareReachableNoWall(roundPlayer.getSquare().getX(), roundPlayer.getSquare().getY(), 2);

        Set<Square> finalSquareToChange = squareToChange;
        callableList.add(new Callable<Boolean>()
        {
            @Override
            public Boolean call() throws Exception {


                try {
                    virtualView.requestInput(new RequestRunAround(changeToList(finalSquareToChange)), roundPlayer.getColor());
                    virtualView.getResponseWithInputs(roundPlayer.getColor());

                    return true;
                }
                catch (Exception e){
                    return  false;
                }
            }
        });



        boolean s = executor.invokeAny(callableList);
        if(!s){
            salta=true;
            return;}
        if(checkForAfk())
            return;


        ResponseRunAround response = (ResponseRunAround) msg;

        int chosenSquareX = response.getX();
        int chosenSquareY = response.getY();


        MethodsWeapons.moveTarget(roundPlayer,chosenSquareX,chosenSquareY);


    }

    /**
     * this method
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private void executeTerminator() throws ExecutionException, InterruptedException {

        List<Callable<Boolean>> callableList = new LinkedList<>();
        callableList.add(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {

                try {
                    virtualView.requestInput(new StartTerminator(), roundPlayer.getColor());


                    return true;
                }
                catch (Exception e)
                {
                    return  false;
                }
            }});
        Boolean resp = executor.invokeAny(callableList);
        if(!resp) {
         salta=true;
            return;
        }
        if(checkForAfk())
            return;
        runAround(true);
        shootTerminator();
        checkForAfk();
        for(Player p: g1.getAllPlayer())
        {
            p.setAfk(false);
        }

        msg=null;
    }

    private void shootTerminator() throws InterruptedException, ExecutionException
    {
        List<ColorId> enemiesColors=new LinkedList<>();
        if(termi.playerThatSee(termi.getSquare().getGameBoard()).size()>1)
        {
            for(Player p: termi.playerThatSee(termi.getSquare().getGameBoard()))
            {
                enemiesColors.add(p.getColor());
            }

            if(termi.playerThatSee(termi.getSquare().getGameBoard()).size()>1)
            {
                List<Callable<Boolean>> callableList = new LinkedList<>();
                callableList.add(new Callable<Boolean>() {

                    @Override
                    public Boolean call() throws Exception {

                        try {
                            virtualView.requestInput(new RequestShootTerminator(enemiesColors), roundPlayer.getColor());
                            virtualView.getResponseWithInputs(roundPlayer.getColor());

                            return true;
                        }
                        catch (Exception e)
                        {
                            return  false;
                        }
                    }
                });



                Boolean resp = executor.invokeAny(callableList);
                if(!resp){
                    salta=true;
                    return;}
                if(checkForAfk())
                    return;

                ResponseShootPeopleTerminator response = (ResponseShootPeopleTerminator) msg;
                Player target= null;
                for(Player p : g1.getAllPlayer())
                {
                    if(p.getColor().equals(response.getTarget()))
                        target=p;

                }
                if(target!=null)
                {
                    target.doDamage(termi.getColor());
                    if (termi.getNumberOfDamagePoint() >= 6)
                    {

                        target.addMark(termi.getColor());
                    }
                }
            }

            else
                {
                    List<Callable<Boolean>> callableList = new LinkedList<>();
                    callableList.add(new Callable<Boolean>() {

                        @Override
                        public Boolean call() throws Exception {

                            try {
                                virtualView.requestInput(new RequestShootTerminator(enemiesColors), roundPlayer.getColor());


                                return true;
                            }
                            catch (Exception e)
                            {
                                return  false;
                            }
                        }
                    });

                }


        }
    }




    /**
     * this method ask the player what weapon he want to use and ask him to use it
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void shotEnemy() throws Exception {


        boolean vuota = false;
        List<WeaponCard> chargedWeapons = new LinkedList<>();
        List<String> chargedWeaponsName = new LinkedList<>();
        for (WeaponCard wc : roundPlayer.getWeaponCardList()) {
            if (checkBooleanArray(wc.checkAvailableMode()))
                chargedWeapons.add(wc);
        }

        for (WeaponCard wc : chargedWeapons) {
            chargedWeaponsName.add(wc.getName());
        }
        if (chargedWeaponsName.isEmpty())
            vuota = true;
        if (!vuota)
        {
            List<Callable<Boolean>> callableList = new LinkedList<>();
            callableList.add(new Callable<Boolean>() {

                    @Override
                    public Boolean call() throws Exception {

                        try {
                            virtualView.requestInput(new RequestShootPeople(chargedWeaponsName), roundPlayer.getColor());

                            virtualView.getResponseWithInputs(roundPlayer.getColor());

                            return true;
                        }
                        catch (Exception e)
                        {
                            return  false;
                        }
                    }
                });

           

           boolean s = executor.invokeAny(callableList);
            if(!s){
                salta=true;
                return;}

            if(checkForAfk())
               return;


            ResponseShootPeople response = (ResponseShootPeople) msg;

            WeaponCard weaponChosen =chargedWeapons.get(response.getChosenWeapon()-1);

            //USA ARMA

            List<Callable<Boolean>> callableListA = new LinkedList<>();
            callableListA.add(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {

                    try
                    {
                        RequestInput temp = weaponChosen.getRequestMessage();
                      virtualView.requestInput(temp,roundPlayer.getColor());
                    virtualView.getResponseWithInputs(roundPlayer.getColor());

                    return true;
                }
                catch (Exception e)
                {
                    return  false;
                }
                }
            });

           

            Boolean resp = executor.invokeAny(callableListA);
            if(!resp){
                salta=true;
                return;}
            if(checkForAfk())
               return;


            weaponChosen.useWeapon(msg);

        }


        else // send empty list and dont wait anything
        {

            List<Callable<Boolean>> callableList = new LinkedList<>();
            callableList.add(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {

                    try
                    {
                        virtualView.requestInput(new RequestShootPeople(chargedWeaponsName), roundPlayer.getColor());

                        return true;
                    }
                    catch (Exception e)
                    {
                        return  false;
                    }
                }
            });

           

           boolean s = executor.invokeAny(callableList);
           if(!s) {
            salta=true;
               return;
           }


        }
    }

    /**
     * this method ask the player where he want to move to grab
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void grab() throws InterruptedException, ExecutionException, SquareNotInGameBoard {
        Set<Square> squareToChange = roundPlayer.lookForGrabStuff(roundPlayer);
        List<Callable<Boolean>> callableList = new LinkedList<>();
        callableList.add(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {

                    virtualView.requestInput(new RequestGrabStuff(changeToList(squareToChange)), roundPlayer.getColor());
                    virtualView.getResponseWithInputs(roundPlayer.getColor());

                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        });


        boolean s = executor.invokeAny(callableList);
        if (!s){ salta=true;
                    return;}
        if (checkForAfk())
            return;

        ResponseGrabStuff response = (ResponseGrabStuff) msg; // mi ritorna un colore

        int chosenSquareX = response.getX();
        int chosenSquareY = response.getY();


        if (g1.getArena().getSquare(chosenSquareX, chosenSquareY).isAmmoPoint()) {
            //  ammo point case
            AmmoPoint currentSquare = (AmmoPoint) g1.getArena().getSquare(chosenSquareX, chosenSquareY);
            currentSquare.useAmmoTiles(roundPlayer);
            MethodsWeapons.moveTarget(roundPlayer, chosenSquareX, chosenSquareY);
        }


        if (g1.getArena().getSquare(chosenSquareX, chosenSquareY).isSpawnPoint())
        {
            // spawn point case
            SpawnPoint currentSquare = (SpawnPoint) g1.getArena().getSquare(chosenSquareX, chosenSquareY);
            List<WeaponCard> currentWeaponList = currentSquare.getWeaponCardList();
            List<String> weaponsName = new LinkedList<>();
            for (WeaponCard wc : currentWeaponList) {
                if (StateSpecialMethods.checkGrabbable(wc, roundPlayer)) // controllo se l'arma è prendibili
                    weaponsName.add(wc.getName());
            }

            List<Callable<Boolean>> callableListA = new LinkedList<>();
            callableListA.add(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {

                    try {
                        virtualView.requestInput(new RequestShootPeople(weaponsName), roundPlayer.getColor());
                        virtualView.getResponseWithInputs(roundPlayer.getColor());

                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
            });


            Boolean valid = executor.invokeAny(callableListA);
            if (!valid)
                return;
            if (checkForAfk())
                return;


            ResponseShootPeople res = (ResponseShootPeople) msg; // mi ritorna  l'indice dell'arma scelta, CONTROLLARE SE REQUEST E RESPONSE CONTROLLANO MUNIZIONI

            WeaponCard chosenWeapon = null;
            for (WeaponCard wp : currentWeaponList) {
                if (wp.getName().equals(weaponsName.get(res.getChosenWeapon()-1))) {
                    chosenWeapon = wp;
                }

            }

            if (chosenWeapon != null) {

                //se hai meno di 3 armi aggiungila e basta
                if (roundPlayer.getWeaponCardList().size() < 3) {
                    removeAmmoCost(roundPlayer, chosenWeapon);
                    roundPlayer.addWeapon(chosenWeapon);
                    chosenWeapon.setPlayer(roundPlayer);
                    currentSquare.drawWeapon(chosenWeapon);

                }
                else { //se ha già 3 armi chiedo con quale arma vuole sostituirla

                    List<WeaponCard> playerCurrentWeaponList = roundPlayer.getWeaponCardList();
                    List<String> playerWeaponsName = new LinkedList<>();
                    for (WeaponCard wc : playerCurrentWeaponList) {
                        playerWeaponsName.add(wc.getName());
                    }

                    List<Callable<Boolean>> callableListS = new LinkedList<>();
                    callableListS.add(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {

                            try {
                                virtualView.requestInput(new RequestShootPeople(playerWeaponsName), roundPlayer.getColor());
                                virtualView.getResponseWithInputs(roundPlayer.getColor());

                                return true;
                            } catch (Exception e) {
                                return false;
                            }
                        }
                    });


                    Boolean correct = executor.invokeAny(callableListS);
                    if (!correct)
                        return;
                    if (checkForAfk())
                        return;


                    ResponseShootPeople ris = (ResponseShootPeople) msg; // mi ritorna  l'indice dell'arma scelta, CONTROLLARE SE REQUEST E RESPONSE CONTROLLANO MUNIZIONI (ATTENTO)

                    WeaponCard weaponToChange = roundPlayer.getWeaponCardList().get(ris.getChosenWeapon() - 1); // arma scelta

                    roundPlayer.changeWeapon(chosenWeapon, weaponToChange.getName());
                    removeAmmoCost(roundPlayer, chosenWeapon);

                    currentSquare.swapWeapon(weaponToChange, chosenWeapon);
                    chosenWeapon.setPlayer(roundPlayer);
                }


            }

            MethodsWeapons.moveTarget(roundPlayer, chosenSquareX, chosenSquareY);
        }
    }
    /**
     * this method ask the player wich weapon he want to reload
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void reload() throws InterruptedException, ExecutionException
    {

        List<WeaponCard> chargableWeapons =  roundPlayer.checkReload(roundPlayer);
        String chargableWeaponName=null;

        for (WeaponCard wc : chargableWeapons)
        {
            if(roundPlayer.checkReload(roundPlayer).contains(wc)) // controllo se l'arma è ancora ricaricabile dopo l'azione eseguita dal player
            {
                chargableWeaponName = (wc.getName());


                String finalChargableWeaponName = chargableWeaponName;
                List<Callable<Boolean>> callableList = new LinkedList<>();
                callableList.add(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        try{
                        virtualView.requestInput(new RequestReloadWeapon(finalChargableWeaponName), roundPlayer.getColor()); 
                        virtualView.getResponseWithInputs(roundPlayer.getColor());

                        return true;}
                        catch (Exception e)
                        {
                            return false;
                        }
                    }
                });

               

               boolean s = executor.invokeAny(callableList);
                if(!s){
                    salta=true;
                    return;}
              if(checkForAfk())
               return;


                ResponseReloadWeapon response = (ResponseReloadWeapon) msg;

                if (response.getTargetWeaponToReload() == 1) {
                    wc.setLoaded(true);
                    roundPlayer.setAmmoYellow(roundPlayer.getAmmoYellow() - wc.getYellowAmmoCost());
                    roundPlayer.setAmmoRed(roundPlayer.getAmmoRed() - wc.getRedAmmoCost());
                    roundPlayer.setAmmoBlue(roundPlayer.getAmmoBlue() - wc.getBlueAmmoCost());
                }

            }

        }
        // sends end cicle message
        List<Callable<Boolean>> callableListH = new LinkedList<>();
        callableListH.add(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try{
                virtualView.requestInput(new End(), roundPlayer.getColor()); 
                return true;}
                catch (Exception e){
                    return  false;
                }
            }
        });

        Boolean end = executor.invokeAny(callableListH);


    }


    private void calculateRapidScore()
    {
        List<Score> scores = new ArrayList<Score>();
        List<String> printableScore=new LinkedList<>();
        for( Player p : g1.getAllPlayer())
        {
            scores.add(new Score(p.getScore(), p.getName()));

        }
        Collections.sort(scores);
        for(Score sc : scores )
        {
            String point = Integer.toString(sc.score);
            printableScore.add(sc.name + " ha totalizzato : " + point + "  punti" + "\n");
        }
        virtualView.sendMessageGenericBroadcast(new GenericMessage("Classifica  \n" + printableScore ));

    }











    public void updateModel()
    {

        virtualView.update(new UpdateModel(g1));

    }



    private void removeAmmoCost(Player p,WeaponCard weaponCard)
    {
        if(weaponCard.getColor().equals(Color.BLUE)) {
            p.setAmmoBlue(p.getAmmoBlue() - (weaponCard.getBlueAmmoCost() - 1));
            p.setAmmoRed(p.getAmmoRed()-(weaponCard.getRedAmmoCost()));
            p.setAmmoYellow(p.getAmmoYellow()-(weaponCard.getYellowAmmoCost()));
        }
        if(weaponCard.getColor().equals(Color.RED)) {
            p.setAmmoRed(p.getAmmoRed() - (weaponCard.getRedAmmoCost() - 1));
            p.setAmmoBlue(p.getAmmoBlue()-(weaponCard.getBlueAmmoCost()));
            p.setAmmoYellow(p.getAmmoYellow()-(weaponCard.getYellowAmmoCost()));
        }
        if(weaponCard.getColor().equals(Color.YELLOW)) {
            p.setAmmoRed(p.getAmmoRed()-(weaponCard.getRedAmmoCost()));
            p.setAmmoBlue(p.getAmmoBlue()-(weaponCard.getBlueAmmoCost()));
            p.setAmmoYellow(p.getAmmoYellow()-(weaponCard.getYellowAmmoCost()-1));
        }
    }




    public Map<ColorId,Integer>  finalScore () {

        int[] scorePoint = new int[5];

        scorePoint[0] = 8;
        scorePoint[1] = 6;
        scorePoint[2] = 4;
        scorePoint[3] = 2;
        scorePoint[4] = 1;


        HashMap<ColorId, Integer> colorAndPoint = new HashMap<>();
        List<ColorId> cList = new LinkedList<>();
        for (Track t : g1.getKillShotTrack()) {
            if (!colorAndPoint.containsKey(t.getPlayer())) {
                colorAndPoint.put(t.getPlayer(), t.getPointCounter());
                cList.add(t.getPlayer());
            } else {
                colorAndPoint.put(t.getPlayer(), colorAndPoint.get(t.getPlayer()) + t.getPointCounter());

            }
        }

        LinkedHashMap<ColorId, Integer> sortedMap = colorAndPoint
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        List<Map.Entry<ColorId, Integer>> temp = sortedMap
                .entrySet()
                .stream()
                .collect(Collectors.toList());

        Set<ColorId> playSamePoints = new LinkedHashSet<>();
        int i = 0;
        Map<ColorId, Integer> fin = new HashMap<>();

        while (i < temp.size())
        {
            Map.Entry<ColorId, Integer> z = temp.get(i);

            for (Map.Entry<ColorId, Integer> v : temp) //Check if there are other players with the same damage points
            {
                if (z.hashCode() != v.hashCode() && v.getValue().equals(z.getValue())) //If player are different and the damage points are equals
                {
                    playSamePoints.add(z.getKey()); //Add the two player to the list
                    playSamePoints.add(v.getKey());
                }
            }

            if (!playSamePoints.isEmpty()) //If there are parity to resolve
            {
                List<ColorId> playe = resolveParity(playSamePoints, cList); //Calculate the order of the players

                playSamePoints.clear();//Clear the list of all player with same points

                for (ColorId a : playe) //Add all the player ordered with the correct score
                {
                    fin.putIfAbsent(a, scorePoint[i]);
                    i++;
                }
            } else//Add the score normally
            {
                fin.putIfAbsent(z.getKey(), scorePoint[i]);
                i++;
            }
        }

        return fin;
    } // mappa con punti da sommare al player con colorId







    private List<ColorId> resolveParity(Set<ColorId> players, List<ColorId> playersByOccurency)
    {
        List<ColorId> playersOrd = new LinkedList<>();//Create the list that will contain the ordered players


        for (ColorId colorIdIterate : playersByOccurency) //For each damage points
        {
            if (players.contains(colorIdIterate) && !playersOrd.contains(colorIdIterate))//If the player that he did that points isn't in the list
                playersOrd.add(colorIdIterate); //Add him
        }

        return playersOrd; //Return the ordered list

    }

private void calculateFinalScore()
{
    Map<ColorId,Integer> mappa = finalScore();
    List<String> printableScore = new LinkedList<>();
    for(Player p : g1.getAllPlayer())
    {
        p.setScore(p.getScore() + mappa.get(p.getColor()));
    }
    List<Score> scores = new ArrayList<Score>();
    for( Player p : g1.getAllPlayer())
    {
        scores.add(new Score(p.getScore(), p.getName()));

    }
    Collections.sort(scores);

    for(Score sc : scores )
    {
        String point = Integer.toString(sc.score);
        printableScore.add(sc.name + " ha totalizzato : " + point + "  punti" + "\n");
    }
    virtualView.sendMessageGenericBroadcast(new GenericMessage("Classifica  \n" + printableScore ));
}

    @Override
    public void update (ResponseInput message)
    {
        msg = message;
    }



}

class Score implements Comparable<Score> {
    int score;
    String name;

    protected Score(int score, String name) {
        this.score = score;
        this.name = name;
    }

    @Override
    public int compareTo(Score o) {
        return score < o.score ? -1 : score > o.score ? 1 : 0;
    }
}

