package it.polimi.deib.se2018.adrenalina.Controller;

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.View.Observer;
import it.polimi.deib.se2018.adrenalina.View.View;
import it.polimi.deib.se2018.adrenalina.communication_message.*;

import it.polimi.deib.se2018.adrenalina.communication_message.message_asking_controller.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller implements Observer<ResponseInput>
{

    //logica gestione passaggio di turno
    //
    //FAI METODO che dopo inizializzazione prende giocatori con l'ordine pattuito
    // per ogni giocatore chiama il metodo starRound che non fa altro che mandare un messaggio che avvisa che inizia il turno del giocatore
    //e nel metodo start round ci sarà la chiamata allo switch
    //switch da aggiungere quel che manca
    protected static Map<ColorId, Set<ColorId>> roundDamageList = new HashMap<>(); // lista dei giocatori che ho attaccato io sono il giocatore dato dal ColorId chiave
    private Model model;
    private View virtualView;
    private ResponseInput msg;

    private GameBoard g1;
   private Player roundPlayer=null;

    boolean firstRound=true;
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

    public Controller()
    {

    }



    // AUXILIARY FUNCTIONS



    /**
     * auxiliary function to check if a boolean array has at least one available mode
     * @param arr
     * @return
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
     * auxiliary function to change a set of square into a list of square
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

    public boolean checkPlayerForGranade(ColorId pg)
    {
        Player currentPlayer=null;
        List<PowerUpCard> pcList= new LinkedList<>();
        boolean ret=false;
        for (Player pla : roundPlayer.getSquare().getGameBoard().getAllPlayer() )
        {
            if(pla.getColor().equals(pg))
                currentPlayer=pla;
        }
        pcList=currentPlayer.getPowerupCardList();
        for(PowerUpCard cp : pcList)
        {
            if (cp.equals("Granata Venom:BLUE") || cp.equals("Granata Venom:YELLOW") || cp.equals("Granata Venom:RED")) {
                ret=true;
            }
        }
        return ret;
    }

    private void drawPowerup(boolean respawn){
        PowerUpCard pc= roundPlayer.getSquare().getGameBoard().drawPowerUpCard();
        //chiedi a gio se va bene usare un flag per indicare che può pescare la prima carta siccome siamo in
        if(respawn)
            roundPlayer.addPowerUpRespawn(pc);
        else
            roundPlayer.addPowerUp(pc);
        pc.setPlayer(roundPlayer);
    }


    // END OF AUXILIARY FUNCTIONS


    public void startRound() throws InterruptedException, ExecutionException {
        for(Player rp : g1.getAllPlayer() )
        {
            Future<Boolean> prova = executor.submit(new Callable<Boolean>()
            {
                @Override
                public Boolean call() throws Exception {


                    virtualView.requestInput(new RequestStartRound(),rp.getColor()); // invia stringa a seconda dello stato
                    virtualView.getResponseWithInputs(roundPlayer.getColor());

                    return true;
                }
            });

            while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
            {

            }

            Boolean s = prova.get();
            if (!s)
            {
                // fai saltare turno
            }
                if(msg instanceof Afk ) {
        // fai saltare turno
    }





            // fai scegliere square respawn in base a carta powerup scelta

            try {
                switcher(rp.getColor());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

//Switch function

    public void switcher(ColorId player) throws Exception
    {
        for(Player p : g1.getAllPlayer()){

            if (p.getColor().equals(player))
                roundPlayer=p;
        }


        virtualView.getResponseWithInputs(player);

        MessageNet messageNet = msg;

        while (!(messageNet instanceof EndRound))
        {
            if (messageNet instanceof AskMoveAround)
            {
                runAround();
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
            else if (messageNet instanceof AskTagBackGranade)
            {
                askForPowerUpTagBackGranade();
            }
            else if (messageNet instanceof AskFirstRespawn)
            {
                askForFirstSpawn();
            }

            virtualView.getResponseWithInputs(player);

            messageNet = msg;
        }


    }


    public void askForPowerUpTeleportOrNewton() throws InterruptedException, ExecutionException {
        boolean vuota=false;
        List<String> powerUpList = new LinkedList<>();
        for (PowerUpCard pc : roundPlayer.getPowerupCardList()) // in questo modo salvo in powerUpList tutti i powerup del player
        {
            if(pc.getName().equals("Raggio Cinetico") || pc.getName().equals("Teletrasporto"))
                powerUpList.add(pc.powerToString());
        }
        if (powerUpList.isEmpty())
            vuota = true;
        if (!vuota) {
            Future<Boolean> prova = executor.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {

                    virtualView.requestInput(new RequestPowerUp(powerUpList), roundPlayer.getColor());
                    virtualView.getResponseWithInputs(roundPlayer.getColor());

                    return true;
                }
            });

            while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
            {

            }

            Boolean s = prova.get();
            if (!s) {
                // fai saltare turno
            }
                if(msg instanceof Afk ) {
        // fai saltare turno
    }

            ResponsePowerUp risp = (ResponsePowerUp) msg;


            for (String pc : risp.getChosenPowerUpList()) {
                if (pc.equals("Teletrasporto:BLUE") || pc.equals("Teletrasporto:YELLOW") || pc.equals("Teletrasporto:RED")) {
                    askForTeleport(pc);
                }

                if (pc.equals("Raggio Cinetico:BLUE") || pc.equals("Raggio Cinetico:YELLOW") || pc.equals("Raggio Cinetico:RED")) {
                    askForNewton(pc);
                }
            }
            // invia messaggio fine ciclo
            Future<Boolean> fine = executor.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    virtualView.requestInput(new End(), roundPlayer.getColor()); // invia stringa a seconda dello stato


                    return true;
                }
            });



            Boolean end = fine.get();
            if (!end) {
                // fai saltare turno
            }

        }
        else
        {
            Future<Boolean> prova = executor.submit(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {

                    virtualView.requestInput(new RequestPowerUp(powerUpList), roundPlayer.getColor());


                    return true;
                }
            });

            while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
            {

            }

            Boolean s = prova.get();
            if (!s) {
                // fai saltare turno
            }
        }
    }


    public void askForPowerUpTagBackGranade() throws InterruptedException, ExecutionException {

        Set<ColorId> attackedPlayers = new HashSet<>();
        attackedPlayers = roundDamageList.get(roundPlayer.getColor());
        Set<ColorId> filteredPlayer = attackedPlayers.stream().filter(colorId -> checkPlayerForGranade(colorId)).collect(Collectors.toSet());
        for (ColorId granadeAttacked : filteredPlayer) {
            boolean vuota = false;
            List<String> powerUpList = new LinkedList<>();
            Player granadeAttackedPlayer =null;
            for(Player p : roundPlayer.getSquare().getGameBoard().getAllPlayer())
            {
                if (p.getColor().equals(granadeAttacked))
                    granadeAttackedPlayer=p;
            }

            for (PowerUpCard pc : granadeAttackedPlayer.getPowerupCardList()) // in questo modo salvo in powerUpList tutti i powerup del player
            {
                if (pc.getName().equals("Granata Venom"))
                    powerUpList.add(pc.powerToString());
            }
            if (powerUpList.isEmpty())
                vuota = true;
            if (!vuota) {
                Future<Boolean> prova = executor.submit(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {

                        virtualView.requestInput(new RequestPowerUp(powerUpList), roundPlayer.getColor());
                        virtualView.getResponseWithInputs(roundPlayer.getColor());

                        return true;
                    }
                });

                while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
                {

                }

                Boolean s = prova.get();
                if (!s) {
                    // fai saltare turno
                }
                    if(msg instanceof Afk ) {
        // fai saltare turno
    }

                ResponsePowerUp risp = (ResponsePowerUp) msg;


                for (String pc : risp.getChosenPowerUpList()) {
                    if (pc.equals("Granata Venom:BLUE") || pc.equals("Granata Venom:YELLOW") || pc.equals("Granata Venom:RED")) {
                        askForTagBackGranade(pc,granadeAttackedPlayer);
                    }

                }
                // invia messaggio fine ciclo
                Future<Boolean> fine = executor.submit(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        virtualView.requestInput(new End(), roundPlayer.getColor()); // invia stringa a seconda dello stato


                        return true;
                    }
                });


                Boolean end = fine.get();
                if (!end) {
                    // fai saltare turno
                }

            } else {
                Future<Boolean> prova = executor.submit(new Callable<Boolean>() {

                    @Override
                    public Boolean call() throws Exception {

                        virtualView.requestInput(new RequestPowerUp(powerUpList), roundPlayer.getColor());


                        return true;
                    }
                });

                while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
                {

                }

                Boolean s = prova.get();
                if (!s) {
                    // fai saltare turno
                }
            }
        }
    }


    public void askForPowerUpTargettingScope() throws InterruptedException, ExecutionException {
        boolean empty=false;
        List<String> powerUpList = new LinkedList<>();
        for (PowerUpCard pc : roundPlayer.getPowerupCardList()) // in questo modo salvo in powerUpList tutti i powerup del player
        {
            if(pc.getName().equals("Mirino"))
                powerUpList.add(pc.powerToString());
        }
        // aggiungi controllo se roundplayer ha fatto danno , se no vuota=true;
        if (powerUpList.isEmpty())
            empty = true;
        if (!empty) {
            Future<Boolean> prova = executor.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {

                    virtualView.requestInput(new RequestPowerUp(powerUpList), roundPlayer.getColor());
                    virtualView.getResponseWithInputs(roundPlayer.getColor());

                    return true;
                }
            });

            while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
            {

            }

            Boolean s = prova.get();
            if (!s) {
                // fai saltare turno
            }
                if(msg instanceof Afk ) {
        // fai saltare turno
    }

            ResponsePowerUp risp = (ResponsePowerUp) msg;


            for (String pc : risp.getChosenPowerUpList()) {
                if (pc.equals("Mirino:BLUE") || pc.equals("Mirino:YELLOW") || pc.equals("Mirino:RED")) {
                    askForTargettingScope(pc);
                }

            }
            // invia messaggio fine ciclo
            Future<Boolean> fine = executor.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    virtualView.requestInput(new End(), roundPlayer.getColor()); // invia stringa a seconda dello stato


                    return true;
                }
            });



            Boolean end = fine.get();
            if (!end) {
                // fai saltare turno
            }

        }
        else
        {
            Future<Boolean> prova = executor.submit(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {

                    virtualView.requestInput(new RequestPowerUp(powerUpList), roundPlayer.getColor());


                    return true;
                }
            });



            Boolean s = prova.get();
            if (!s) {
                // fai saltare turno
            }
        }
    }


    public void askForTeleport(String choice) throws InterruptedException, ExecutionException {
        Teleporter tele=null;
                    Future<Boolean> rich = executor.submit(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {

                            virtualView.requestInput(new RequestTeleporter(roundPlayer.getSquare().getGameBoard(), roundPlayer), roundPlayer.getColor());
                            virtualView.getResponseWithInputs(roundPlayer.getColor());

                            return true;
                        }
                    });

                    while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
                    {

                    }

                    Boolean ric = rich.get();
                    if (!ric) {
                        // fai saltare turno
                    }
            if(msg instanceof Afk ) {
        // fai saltare turno
    }

        ResponseTeleporter sq = (ResponseTeleporter) msg;


        for (PowerUpCard cardtp : roundPlayer.getPowerupCardList() )
        {
            int i=0;
            if(cardtp.powerToString().equals(choice)){
                tele = (Teleporter) roundPlayer.usePowerUp(i);
            }
            i++;
        }

                    tele.usePowerUp(sq.getX(), sq.getY());

        }

    public void askForNewton(String choice) throws InterruptedException, ExecutionException {
        int index=0;
        Newton card=null;
        for (PowerUpCard cardtp : roundPlayer.getPowerupCardList() )
        {
            int i=0;
            if(cardtp.powerToString().equals(choice)){
                index = i;
                card= (Newton)cardtp;
            }
            i++;
        }
        Newton finalCard = card;
        Future<Boolean> rich = executor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {

                virtualView.requestInput(new RequestNewton(finalCard.checkMoveTarget(roundPlayer.getSquare().getGameBoard())), roundPlayer.getColor()); // come gli passo la richiesta ?
                virtualView.getResponseWithInputs(roundPlayer.getColor());

                return true;
            }
        });

        while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
        {

        }

        Boolean ric = rich.get();
        if (!ric) {
            // fai saltare turno
        }
            if(msg instanceof Afk ) {
        // fai saltare turno
    }

        ResponseNewton sq = (ResponseNewton) msg;


        Player target=null;
        for(Player p : roundPlayer.getSquare().getGameBoard().getAllPlayer())
        {
            if (p.getColor().equals(sq.getTarget()))
                target=p;
        }
        roundPlayer.usePowerUp(index);
        card.usePowerUp(target,sq.getX(),sq.getY());

        //aggiungi a pila scarti

    }

    public void askForTargettingScope(String choice) throws InterruptedException, ExecutionException {
        int index=0;
        TargettingScope card=null;
        for (PowerUpCard cardtp : roundPlayer.getPowerupCardList() )
        {
            int i=0;
            if(cardtp.powerToString().equals(choice)){
                index = i;
                card= (TargettingScope) cardtp;
            }
            i++;
        }
        TargettingScope finalCard = card;


        Future<Boolean> rich = executor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {

                virtualView.requestInput(new RequestTargettingScope(roundDamageList.get(finalCard.getPlayer()),finalCard.check()), roundPlayer.getColor());
                virtualView.getResponseWithInputs(roundPlayer.getColor());

                return true;
            }
        });

        while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
        {

        }

        Boolean ric = rich.get();
        if (!ric) {
            // fai saltare turno
        }
            if(msg instanceof Afk ) {
        // fai saltare turno
    }

        ResponseTargettingScope sq = (ResponseTargettingScope) msg;


        TargettingScope scope = (TargettingScope) roundPlayer.usePowerUp(index);
        scope.usePowerUp(sq.getTargetBasicMode(),sq.getTargetAmmo());
        //aggiungi a pila scarti

    }

    public void askForTagBackGranade(String choice,Player tempPlayer) throws InterruptedException, ExecutionException
    {
        TagbackGranade cardpower=null;
        for(PowerUpCard pc : tempPlayer.getPowerupCardList())
        {
            if(pc.powerToString().equals(choice))
                cardpower=(TagbackGranade) pc;
        }
        Future<Boolean> rich = executor.submit(new Callable<Boolean>()
        {
            @Override
            public Boolean call() throws Exception
            {

                virtualView.requestInput(new RequestTagbackGranade(roundPlayer.getColor()),tempPlayer.getColor());
                virtualView.getResponseWithInputs(tempPlayer.getColor());

                return true;
            }
        });

        while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
        {

        }

        Boolean ric = rich.get();
        if (!ric) {
            // fai saltare turno
        }
            if(msg instanceof Afk ) {
        // fai saltare turno
    }

        ResponseTagbackGranade response = (ResponseTagbackGranade) msg;

        if(!(response.getTargetBasicMode()==null))
        {
            cardpower.usePowerUp(roundPlayer);
            //aggiungi a pila scarti

        }


    }






    public void askForAllPowerUp() throws InterruptedException, ExecutionException //this method is used when you want to use a powerupas an ammo
    {
        boolean empty=false;
        int index=0;
        PowerUpCard card=null;
        List<String> powerUpList = new LinkedList<>();
        for (PowerUpCard pc : roundPlayer.getPowerupCardList()) // in questo modo salvo in powerUpList tutti i powerup del player
        {

                powerUpList.add(pc.powerToString());
        }

        if (powerUpList.isEmpty())
            empty = true;
        if (!empty) {
            Future<Boolean> prova = executor.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {

                    virtualView.requestInput(new RequestPowerUp(powerUpList), roundPlayer.getColor());
                    virtualView.getResponseWithInputs(roundPlayer.getColor());

                    return true;
                }
            });

            while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
            {

            }

            Boolean s = prova.get();
            if (!s) {
                // fai saltare turno
            }
                if(msg instanceof Afk ) {
        // fai saltare turno
    }

            ResponsePowerUp risp = (ResponsePowerUp) msg;


            for (String pc : risp.getChosenPowerUpList()) {

                    for (PowerUpCard cardtp : roundPlayer.getPowerupCardList() )
                    {
                        int i=0;
                        if(cardtp.powerToString().equals(pc)){
                            index = i;
                            card= (TargettingScope) cardtp;
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
                    roundPlayer.setAmmoBlue(roundPlayer.getAmmoRed()+1);
                }
                if (pc.contains("YELLOW"))
                {
                    roundPlayer.setAmmoBlue(roundPlayer.getAmmoYellow()+1);
                }



            }
            // invia messaggio fine ciclo
            Future<Boolean> fine = executor.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    virtualView.requestInput(new End(), roundPlayer.getColor()); // invia stringa a seconda dello stato


                    return true;
                }
            });



            Boolean end = fine.get();
            if (!end) {
                // fai saltare turno
            }

        }
        else
        {
            Future<Boolean> prova = executor.submit(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {

                    virtualView.requestInput(new RequestPowerUp(powerUpList), roundPlayer.getColor());


                    return true;
                }
            });



            Boolean s = prova.get();
            if (!s) {
                // fai saltare turno
            }
        }
    }







    public void spawn(int index) throws InterruptedException, ExecutionException
    {


    PowerUpCard powercard = roundPlayer.usePowerUp(index);
    Square resp= null;

           if (powercard.getColor().equals(Color.BLUE)) {
               try {
                   resp = roundPlayer.getSquare().getGameBoard().getArena().getSquare(3,3);
               } catch (SquareNotInGameBoard squareNotInGameBoard) {
               }
           }

         if (powercard.getColor().equals(Color.YELLOW)) {
             try {
                 resp = roundPlayer.getSquare().getGameBoard().getArena().getSquare(4,1);
             } catch (SquareNotInGameBoard squareNotInGameBoard) {
             }
         }
         if (powercard.getColor().equals(Color.RED)) {
             try {
                 resp = roundPlayer.getSquare().getGameBoard().getArena().getSquare(1,2);
             } catch (SquareNotInGameBoard squareNotInGameBoard) {
             }
         }

         roundPlayer.setSquare(resp);
         MethodsWeapons.moveTarget(roundPlayer,resp.getX(),resp.getY());



        }

public void askForRespawn(Player p) throws InterruptedException, ExecutionException {
    drawPowerup(true);
    List<Color> powerList= new LinkedList<>();
    for(PowerUpCard pc : roundPlayer.getPowerupCardList())
    {
        powerList.add(pc.getColor());
    }
    Future<Boolean> prova = executor.submit(new Callable<Boolean>()
    {
        @Override
        public Boolean call() throws Exception {
            Set<Square> squareToChange = roundPlayer.lookForRunAround(roundPlayer);

            virtualView.requestInput(new RequestRespawn(powerList),p.getColor()); // invia stringa a seconda dello stato
            virtualView.getResponseWithInputs(p.getColor());

            return true;
        }
    });

    while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
    {

    }

    Boolean s = prova.get();
    if (!s)
    {
        // fai saltare turno
    }
        if(msg instanceof Afk ) {
        // fai saltare turno
    }

    ResponseRespawn response = (ResponseRespawn) msg;
    roundPlayer.usePowerUp(response.getTargetSpawnPoint()); //throw away the chosen power Up
    spawn(response.getTargetSpawnPoint());
}

public void askForFirstSpawn() throws InterruptedException, ExecutionException {
    drawPowerup(false);
   askForRespawn(roundPlayer);

}

    public void runAround() throws InterruptedException, ExecutionException
    {
        Future<Boolean> prova = executor.submit(new Callable<Boolean>()
        {
            @Override
            public Boolean call() throws Exception {
            Set<Square> squareToChange = roundPlayer.lookForRunAround(roundPlayer);

                virtualView.requestInput(new RequestRunAround(changeToList(squareToChange)),roundPlayer.getColor()); // invia stringa a seconda dello stato
                virtualView.getResponseWithInputs(roundPlayer.getColor());

                return true;
            }
        });

        while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
        {

        }

        Boolean s = prova.get();
        if (!s)
        {
            // fai saltare turno
        }
            if(msg instanceof Afk ) {
        // fai saltare turno
    }

        ResponseRunAround response = (ResponseRunAround) msg;

        int chosenSquareX = response.getX();
        int chosenSquareY = response.getY();



        // fai scegliere square respawn in base a carta powerup scelta

    //g1.getRoomList().get()
        MethodsWeapons.moveTarget(roundPlayer,chosenSquareX,chosenSquareY);


    }


    public void shotEnemy() throws InterruptedException, ExecutionException {
        //FAI SCEGLIERE ARMA
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
                Future<Boolean> prova = executor.submit(new Callable<Boolean>() {

                    @Override
                    public Boolean call() throws Exception {

                        virtualView.requestInput(new RequestShootPeople(chargedWeaponsName), roundPlayer.getColor()); // invia stringa a seconda dello stato

                        virtualView.getResponseWithInputs(roundPlayer.getColor());

                        return true;
                    }
                });

            while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
            {

            }

            Boolean s = prova.get();
            if (!s) {
                // fai saltare turno
            }
                if(msg instanceof Afk ) {
        // fai saltare turno
    }

            ResponseShootPeople response = (ResponseShootPeople) msg;

            WeaponCard weaponChosen = roundPlayer.getWeaponCardList().get(response.getChosenWeapon());

            //USA ARMA

            Future<Boolean> con = executor.submit(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {

                    //  virtualView.getRequestMessage(weaponChosen.getRequestMessage,roundPlayer.getColor()); // DA IMPLEMENTARE PER OGNI ARMA
                    virtualView.getResponseWithInputs(roundPlayer.getColor());

                    return true;
                }
            });

            while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
            {

            }

            Boolean resp = con.get();
            if (!resp) {
                // fai saltare turno
            }
                if(msg instanceof Afk ) {
        // fai saltare turno
    }

            weaponChosen.useWeapon(msg);

        }


        else // invio lista vuota ma non aspetto nulla
        {

            Future<Boolean> prova = executor.submit(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {

                    virtualView.requestInput(new RequestShootPeople(chargedWeaponsName), roundPlayer.getColor()); // invia stringa a seconda dello stato



                    return true;
                }
            });

            while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
            {

            }

            Boolean s = prova.get();
            if (!s) {
                // fai saltare turno
            }


        }
    }


    public void grab() throws InterruptedException, ExecutionException, SquareNotInGameBoard
    {
    // A SECONDA DELLO STATO CHIEDI DI QUANTO VUOI MUOVERTI
        Future<Boolean> prova = executor.submit(new Callable<Boolean>()
        {
            @Override
            public Boolean call() throws Exception {
                Set<Square> squareToChange =roundPlayer.lookForGrabStuff(roundPlayer);

                virtualView.requestInput(new RequestGrabStuff(changeToList(squareToChange)),roundPlayer.getColor()); // invia stringa a seconda dello stato
                virtualView.getResponseWithInputs(roundPlayer.getColor());

                return true;
            }
        });

        while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
        {

        }

        Boolean s = prova.get();
        if (!s)
        {
            // fai saltare turno
        }
            if(msg instanceof Afk ) {
        // fai saltare turno
    }

        ResponseGrabStuff response = (ResponseGrabStuff) msg; // mi ritorna un colore

        int chosenSquareX = response.getX();
        int chosenSquareY = response.getY();




    // A SECONDA DEL CASO AMMO POINT O SPAWN POINT



        if(roundPlayer.getSquare().getGameBoard().getArena().getSquare(chosenSquareX,chosenSquareY).isAmmoPoint())
        {
            // caso ammo point
            AmmoPoint currentSquare = (AmmoPoint) roundPlayer.getSquare().getGameBoard().getArena().getSquare(chosenSquareX,chosenSquareY);
            AmmoTiles currentAmmoTiles = currentSquare.getAmmoTiles();
            currentAmmoTiles.useAmmoTilesCards(roundPlayer);
        }


        if(roundPlayer.getSquare().getGameBoard().getArena().getSquare(chosenSquareX,chosenSquareY).isSpawnPoint())
        {
            // caso spawn point, chiedi quale arma vuoi prendere tra quelle prendibili
            SpawnPoint currentSquare = (SpawnPoint) roundPlayer.getSquare().getGameBoard().getArena().getSquare(chosenSquareX,chosenSquareY);
            List<WeaponCard> currentWeaponList = currentSquare.getWeaponCardList();
            List<String> weaponsName = new LinkedList<>();
            for (WeaponCard wc : currentWeaponList)
            {
                if(StateSpecialMethods.checkGrabbable(wc,roundPlayer)) // controllo se l'arma è prendibili
                    weaponsName.add(wc.getName());
            }


            Future<Boolean> seconda = executor.submit(new Callable<Boolean>()
            {
                @Override
                public Boolean call() throws Exception {

                    virtualView.requestInput(new RequestShootPeople(weaponsName),roundPlayer.getColor());
                    virtualView.getResponseWithInputs(roundPlayer.getColor());

                    return true;
                }
            });

            while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
            {

            }

            Boolean valid = seconda.get();
            if (!valid)
            {
                // fai saltare turno
            }
                if(msg instanceof Afk ) {
        // fai saltare turno
    }

            ResponseShootPeople res = (ResponseShootPeople) msg; // mi ritorna  l'indice dell'arma scelta, CONTROLLARE SE REQUEST E RESPONSE CONTROLLANO MUNIZIONI

            WeaponCard chosenWeapon = currentWeaponList.get(res.getChosenWeapon()); // arma scelta

            //se hai meno di 3 armi aggiungila e basta
            if(roundPlayer.getWeaponCardList().size()<3) {
                roundPlayer.addWeapon(chosenWeapon);
            chosenWeapon.setPlayer(roundPlayer);

            }


            else
            { //se ha già 3 armi chiedo con quale arma vuole sostituirla

                List<WeaponCard> playerCurrentWeaponList = currentSquare.getWeaponCardList();
                List<String> playerWeaponsName = new LinkedList<>();
                for (WeaponCard wc : playerCurrentWeaponList)
                {
                    playerWeaponsName.add(wc.getName());
                }


                Future<Boolean> terza = executor.submit(new Callable<Boolean>()
                {
                    @Override
                    public Boolean call() throws Exception {

                        virtualView.requestInput(new RequestShootPeople(playerWeaponsName),roundPlayer.getColor());
                        virtualView.getResponseWithInputs(roundPlayer.getColor());

                        return true;
                    }
                });

                while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
                {

                }

                Boolean corretta = terza.get();
                if (!corretta)
                {
                    // fai saltare turno
                }
                    if(msg instanceof Afk ) {
        // fai saltare turno
    }

                ResponseShootPeople ris = (ResponseShootPeople) msg; // mi ritorna  l'indice dell'arma scelta, CONTROLLARE SE REQUEST E RESPONSE CONTROLLANO MUNIZIONI (ATTENTO)

                WeaponCard weaponToChange = roundPlayer.getWeaponCardList().get(ris.getChosenWeapon()); // arma scelta

                roundPlayer.changeWeapon(chosenWeapon,weaponToChange.getName());

                currentSquare.swapWeapon(weaponToChange,chosenWeapon);
            }
            chosenWeapon.setPlayer(roundPlayer);

        }
        MethodsWeapons.moveTarget(roundPlayer,chosenSquareX,chosenSquareY);
    }

    public void reload() throws InterruptedException, ExecutionException
    {

           List<WeaponCard> chargableWeapons =  roundPlayer.checkReload(roundPlayer);
        String chargableWeaponName=null;
        for (WeaponCard wc : chargableWeapons)
        {
            if(roundPlayer.checkReload(roundPlayer).contains(wc)) // controllo se l'arma è ancora ricaricabile dopo l'azione eseguita dal player
            {
                chargableWeaponName = (wc.getName());


                String finalChargableWeaponName = chargableWeaponName;
                Future<Boolean> prova = executor.submit(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        virtualView.requestInput(new RequestReloadWeapon(finalChargableWeaponName), roundPlayer.getColor()); // invia stringa a seconda dello stato
                        virtualView.getResponseWithInputs(roundPlayer.getColor());

                        return true;
                    }
                });

                while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
                {

                }

                Boolean s = prova.get();
                if (!s) {
                    // fai saltare turno
                }
    if(msg instanceof Afk ) {
        // fai saltare turno
    }

                ResponseReloadWeapon response = (ResponseReloadWeapon) msg;

                if (response.getTargetWeaponToReload() == 1) {
                    wc.setLoaded(true);
                    roundPlayer.setAmmoYellow(roundPlayer.getAmmoYellow() - wc.getYellowAmmoCost());
                    roundPlayer.setAmmoRed(roundPlayer.getAmmoRed() - wc.getRedAmmoCost());
                    roundPlayer.setAmmoBlue(roundPlayer.getAmmoBlue() - wc.getBlueAmmoCost());
                }

            }

        }
        // invia messaggio fine ciclo
        Future<Boolean> fine = executor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                virtualView.requestInput(new End(), roundPlayer.getColor()); // invia stringa a seconda dello stato


                return true;
            }
        });

        Boolean end = fine.get();
        if (!end) {
            // fai saltare turno
        }

    }









    public void inizialization()
    {
        // creare weapon stack
        //creare poweup stack
        // passare codice arena ( preso da menu)
        // passare skull counter ( preso da menu)
        // creare stac ammo tiles
        // creare player ( presi da menu)
        // creare listaplayer
        // passare lista player a gameboard
        // creare la gameboard
        // modificare costruttore gameboard con lista player
        // fai unpadate model
    }



    public void executeRound() throws InterruptedException, ExecutionException {



        // OGNI AZIONE FAI UPDATE MODEL

        // se primo turno fai respawnare



        //chiedere se vuole usare powerup ( tra ogni azione teleport) come loop (ovvero chiedo ogni volta)



        // chiedere se vuole saltare come loop (ovvero chiedo ogni volta) ragiona con ragazzi su come fare


        // chiede prima azione
        // a seconda della risposta fai check stato del player e fai stato.azione


       // logica azioni

        /*se sceglie spara :
        1) fai tutti i check delle armi
        2) mostra le armi che hanno almeno la modalità base disponibile
        3) prendi scelta arms giocatore
        4) mostra modalità disponibili per l'arma
        5) prendi scelta modalità e parametri da giocatore
        6) chiama  use weapon dell'arma coi parametri dati dal player
        */

        /* se sceglie run around
        1) mostra quadrati dove puoi muoverti
        2) prendi scelta giocatore
        3) sposta player a seconda dei parametri passati col moveTarget
        */

        /* se sceglie grab
           1) controlla se è un ammo point o uno spwanpoint
           A) ammopoint
                1) aggiungi l'ammo point al player (eventualmente powerup) ( toglie in automatico dallo stack)
           B) spawn point
                1) mostra armi disponibili nello spawnpoint ( attenzione che si vedono sempre tutte sulla gameboard però)
                2)se giocatore ha 3 armi
                3) scegli quale arma cambiare ( altrimenti aggiungi e basta)

         */


        /*
        * powerup/salta
        * azione/salta
        * powerup/salta
        * azione/salta
        * powerup/salta
        * ricarica
        * */
/* azione ricarica

    1) chiama check for reload  che ti dice quali armi puoi ricaricare
    2) chiede al giocatore quale arma vuole ricaricare
    3) se ne rimangono disponibili continua a chiedere quale arma vuole ricaricare

 */

/* fine turno
  1) calcolo punti ( calcola quanti danni hai fatto a un player morto e sistema la killshoot track e lo skullcounter di conseguenza)
  2)fai spawnare chi è morto ( passa temporaneamente al giocatore che deve respawnare)
  3) riempi la board con le munizioni o le armi dove mancano
*/






    }

public void getPointAndRespawn() throws ExecutionException, InterruptedException {
      
      //chiedi come fare calcolo punteggi
        for(Player p : g1.getAllPlayer())
        {
            if(p.isDead())
                askForRespawn(p);
        }
        
        
}



    public void updateModel(){

    // serializza il model e lo invia a tutte le view remote


    }


    public void createNewGame (int idArena, ColorId[] players, boolean isFrenetic, boolean onTerminator, int numSkulls, ColorId firstPlayer) throws InterruptedException, ExecutionException {
        Future<String> prova = executor.submit(new Callable<String>()
        {
            @Override
            public String call() throws Exception {



                return "Ciao";
            }
        });

        while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
        {

        }

        String s = prova.get();

    }






    public void finalScore ()
    {

    }

    public void finalFrenesy ()
    {

    }

    @Override
    public void update(ResponseInput message)
    {
        msg = message;
    }
}
