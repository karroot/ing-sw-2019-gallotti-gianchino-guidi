package it.polimi.deib.se2018.adrenalina.Controller;

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.TagbackGranade;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.Teleporter;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.View.Observer;
import it.polimi.deib.se2018.adrenalina.View.View;
import it.polimi.deib.se2018.adrenalina.communication_message.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.*;

public class Controller implements Observer<ResponseInput>
{
    private Model model;
    private View virtualView;
    private ResponseInput msg;
    Player roundPlayer= new Player(ColorId.BLUE,"prova",null,true);
    Map<ColorId, List<ColorId>> roundDamageList = new HashMap<>();

    boolean firstRound=true;
    //Controller deve avere un riferimento alla virtual view
    /*
    Quando il controller deve richiedere un input prima crea un thread che
    esegue in parallelo i metodi requestInput e getResponseWithInputs della virtual view e poi passa la risposta
    attraverso il metodo update
    Poi si mette in pausa fino a quando il thread non finisce
    */

    private ExecutorService executor = Executors.newFixedThreadPool(1); //numero max thread contemporanei

    public Controller()
    {

    }

private boolean checkBooleanArray(boolean[] arr)
{
    for(boolean i : arr)
    {
        if (i)
            return true;
    }

return false;
}

private List<String> changeToList (Set<Square> squareSet)
{
    List<String> stringList = new ArrayList<>();

    for (Square squareIterate : squareSet)
    {
        stringList.add(squareIterate.toStringCoordinates());
    }

    return stringList;
}




    public void askForTeleport() throws InterruptedException, ExecutionException {
        boolean isTp = false;
        for (PowerUpCard pc : roundPlayer.getPowerupCardList() )
        {
            if (pc.getIdPU() == 211 || pc.getIdPU() == 212 || pc.getIdPU() == 213 )
                isTp = true;
        }
        if (isTp) // se ha almeno un teleport chiedi se vuole usarlo
        {
            Future<Boolean> prova = executor.submit(new Callable<Boolean>()
            {
                @Override
                public Boolean call() throws Exception {
                    List<String> powerUpList = new LinkedList<>();
                    for (PowerUpCard pc : roundPlayer.getPowerupCardList()) // in questo modo salvo in powerUpList tutti i powerup del player
                    {
                        powerUpList.add(pc.powerToString());
                    }
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
            ResponsePowerUp choice = (ResponsePowerUp) msg;


            if (roundPlayer.getPowerupCardList().get(choice.getTargetBasicMode()).getIdPU() == 211 ||
                    roundPlayer.getPowerupCardList().get(choice.getTargetBasicMode()).getIdPU() == 212 ||
                    roundPlayer.getPowerupCardList().get(choice.getTargetBasicMode()).getIdPU() == 213) {
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
                ResponseTeleporter sq = (ResponseTeleporter) msg;


                Teleporter tele = (Teleporter) roundPlayer.usePowerUp(choice.getTargetBasicMode());
                tele.usePowerUp(sq.getX(), sq.getY());

            }
        }

    }









 public void spawna(int index) throws InterruptedException, ExecutionException {

       /* // pesca carta powerup riciclale per fare metodo pesca powerup
        roundPlayer.addPowerUp(roundPlayer.getSquare().getGameBoard().getPowerUpCardStack().pop());
        roundPlayer.addPowerUp(roundPlayer.getSquare().getGameBoard().getPowerUpCardStack().pop());
        List<Color> powerUpColorList = new LinkedList<>();
        for (PowerUpCard powerc : roundPlayer.getPowerupCardList())
        {
            powerUpColorList.add(powerc.getColor());
        } */

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





public void runAround() throws InterruptedException, ExecutionException {
    Future<Boolean> prova = executor.submit(new Callable<Boolean>()
    {
        @Override
        public Boolean call() throws Exception {
        Set<Square> squareToChange = roundPlayer.lookForRunAround(roundPlayer,roundPlayer.getSquare().getGameBoard());

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
    ResponseRunAround response = (ResponseRunAround) msg; // mi ritorna un colore

    int chosenSquareX = response.getX();
    int chosenSquareY = response.getY();



    // fai scegliere square respawn in base a carta powerup scelta

//g1.getRoomList().get()
    MethodsWeapons.moveTarget(roundPlayer,chosenSquareX,chosenSquareY);


}


public void shotEnemy() throws InterruptedException, ExecutionException {
    List<WeaponCard> chargedWeapons = new LinkedList<>();
    List<String> chargedWeaponsName = new LinkedList<>();
    for (WeaponCard wc : roundPlayer.getWeaponCardList())
    {
        if (checkBooleanArray(wc.checkAvaliableMode()))
            chargedWeapons.add(wc);
    }

    for (WeaponCard wc : chargedWeapons)
    {
        chargedWeaponsName.add(wc.getName());
    }

    Future<Boolean> prova = executor.submit(new Callable<Boolean>()
    {

        @Override
        public Boolean call() throws Exception {

            virtualView.requestInput(new RequestShootPeople(chargedWeaponsName),roundPlayer.getColor()); // invia stringa a seconda dello stato
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
    ResponseShootPeople response = (ResponseShootPeople) msg; // mi ritorna indice arma scelta


   WeaponCard weaponChosen = roundPlayer.getWeaponCardList().get(response.getTargetBasicMode());



    Future<Boolean> con = executor.submit(new Callable<Boolean>()
    {

        @Override
        public Boolean call() throws Exception {

          //  virtualView.requestInput(weaponChosen.getRequestMessage,roundPlayer.getColor()); // invia stringa a seconda dello stato
            virtualView.getResponseWithInputs(roundPlayer.getColor());

            return true;
        }
    });

    while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
    {

    }

    Boolean resp = con.get();
    if (!resp)
    {
        // fai saltare turno
    }
weaponChosen.useWeapon(msg);
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
        askForTeleport();


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


// controllo per tagbackgranade

/*
        if(roundPlayer.getPowerupCardList().get(choice.getTargetBasicMode()).getIdPU()== 22 || // da sistemare per condizioni presenti alla fine del turno
                roundPlayer.getPowerupCardList().get(choice.getTargetBasicMode()).getIdPU()== 23 ||
                roundPlayer.getPowerupCardList().get(choice.getTargetBasicMode()).getIdPU()== 24 )
        {
            Future<Boolean> rich = executor.submit(new Callable<Boolean>()
            {
                @Override
                public Boolean call() throws Exception {

                    virtualView.requestInput(new RequestTagbackGranade(roundDamageList.get(roundPlayer.getColor())),roundPlayer.getColor());
                    virtualView.getResponseWithInputs(roundPlayer.getColor());

                    return true;
                }
            });

            while (!executor.awaitTermination(200, TimeUnit.MILLISECONDS)) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
            {

            }

            Boolean ric = rich.get();
            if (!ric)
            {
                // fai saltare turno
            }
            ResponseTagbackGranade  response = (ResponseTagbackGranade) msg;

            for ( Player p : g1.getAllPlayer() )
            {
                if (p.getColor().equals((response.getTargetBasicMode())))
                {

                    TagbackGranade granade = (TagbackGranade) roundPlayer.usePowerUp(choice.getTargetBasicMode());
                    granade.usePowerUp(p);
                }
            }

        }

        roundDamageList.remove(roundPlayer.getColor());
*/

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
