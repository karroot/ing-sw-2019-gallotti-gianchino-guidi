package it.polimi.deib.se2018.adrenalina.Controller;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

public class Shoot
{

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


}
