package it.polimi.deib.se2018.adrenalina.Controller;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Model;
import it.polimi.deib.se2018.adrenalina.View.Observer;
import it.polimi.deib.se2018.adrenalina.View.View;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.*;

public class Controller implements Observer<ResponseInput>
{
    private Model model;
    private View virtualView;
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



    public void executeRound()
    {
        // OGNI AZIONE FAI UPDATE MODEL

        // se primo turno fai respawnare

        // controllare stato player e modificare azioni disponibili di conseguenza
        // controllo lista danni ultimo turno per powerup granade ( e poi svuoti)
        //chiedere se vuole usare powerup ( tra ogni azione teleport) come loop (ovvero chiedo ogni volta)
        // chiedere se vuole saltare come loop (ovvero chiedo ogni volta)
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

    }
}
