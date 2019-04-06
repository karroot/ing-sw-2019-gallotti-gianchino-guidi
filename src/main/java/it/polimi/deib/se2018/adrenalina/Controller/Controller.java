package it.polimi.deib.se2018.adrenalina.Controller;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Model;

public class Controller
{
    private Model model;


    public void createNewGame (int idArena, ColorId[] players, boolean isFrenetic, boolean onTerminator, int numSkulls, ColorId firstPlayer)
    {

    }

    public void takePowerUp (ColorId player)
    {

    }

    public void spawn (ColorId player, Color PointOfSpawn)
    {

    }

    public void usePowerUpTeleporter (ColorId player, int x, int y)
    {

    }

    public void usePowerUpNewton (ColorId player, ColorId playerChosen, int x, int y)
    {
        //Dovrebbe restituire un eccezzione nel caso il player indichi uno square non valido
    }

    public void checkSquare (ColorId player, boolean isGrab)
    {
        //Serve per il movimento
        //
        //Ma viene riciclata anche per l'azione raccogli
    }

    public void moveInto (ColorId player, int x, int y)
    {

    }

    public void checkForShoot (ColorId player)
    {
        //Fa un check  per tutte le armi del player capendo quali hanno dei bersagli colpibili e se si hanno abbastanza munizioni per usarla
        //
        //La notify del model dirà alla view anche se è permesso un movimento prima di sparare e in tal caso restituirà tutti gli square a distanza 1
        //
        //Ci sarà una notify da parte del model alla view per tutte le modalità
    }

    public void shoot (ColorId player, String nameWeapon, ColorId playerChosen)
    {
        //è molto difficile da generalizzare questo metodo
        //Ci sono armi che prendono come bersagli intere stanze oppure bersagli multipli
        //
        //Avrebbe più senso fare che la view crea una classe particolare dove sono presenti l'arma scelta dal giocatore la modalità selezionata e i bersagli square o giocatori che siano
        //
        //La classe dipende dal'arma scelta dato che ha diverse modalità e possono essere mandate dal model tramite le notify
    }

    public void grab (ColorId player)
    {

    }

    public void checkAmmoForReload (ColorId player)
    {
        //Controlla quali armi possono essere caricate per il player in  questione con le munizioni che ha
    }

    public void reload (ColorId player)
    {
        //Ricarica arma per quel giocatore
    }

    public void calculateScoreEndRound ()
    {

    }

    public void resetPlayer (ColorId player)
    {
        //resetta i danni
    }

    public void replenishBoard ()
    {

    }

    public void finalScore ()
    {

    }

    public void finalFrenesy ()
    {

    }
}
