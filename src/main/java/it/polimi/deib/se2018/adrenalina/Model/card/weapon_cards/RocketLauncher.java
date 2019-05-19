package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;

import java.util.*;

public class RocketLauncher extends WeaponCard
{

    private boolean[] avaiableMethod = new boolean[4];
    private Player dummie = new Player(ColorId.BLUE,"a","a",false);


    public RocketLauncher( Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        this.name = "Rocket Launcher";
        yellowAmmoCost = 0;
        blueAmmoCost = 0;
        redAmmoCost = 2;
    }

    //mod base: Deal 2 damage to 1 target you can see that is not on your square. Then you may move the target 1 square.

    //with rocket jump: Move 1 or 2 squares. This effect can be used either before or after the basic effect.

    //with fragmenting warhead: During the basic effect, deal 1 damage to every player on your target's original square – including the target, even if you move it.

    //Notes: If you use the rocket jump before the basic effect, you consider only your new square when determining if a target is legal.
    // You can even move off a square so you can shoot someone on it.
    // If you use the fragmenting warhead, you deal damage to everyone on the target's square before you move the target – your target will take 3 damage total.

    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore");//If this card doesn't belong to any player, it launches an exception


        avaiableMethod[0] = false;//I suppose that the modes can't be used
        avaiableMethod[1] = false;
        avaiableMethod[2] = false;
        avaiableMethod[3] = false;

        if (isLoaded() && MethodsWeapons.areSquareISeeNotMineNotEmpty(player, (List<Square>) MethodsWeapons.squareThatSee(player)))
        {
                avaiableMethod[0] = true;

        }


        if (isLoaded() && avaiableMethod[0] && player.getAmmoBlue() > 0)
        {
            avaiableMethod[1] = true;
        }

        if (isLoaded() && avaiableMethod[0] && player.getAmmoYellow() > 0)
        {
            avaiableMethod[2] = true;
        }

        if(isLoaded() && (checkPhaseGlide().size()>1))
            avaiableMethod[3] = true;

        return avaiableMethod;

    }

    public List<Player> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvaliableMode()[0]) //check mode
            throw  new IllegalStateException("Modalità xxx dell'arma: "+name+" non eseguibile");

        Set<Player> playersTarget = player.playerThatSee(player.getSquare().getGameBoard()); //Obtain all players that can be seen

        playersTarget.remove(player);

        return new ArrayList<>(playersTarget);//Returns all targets
    }

    public void basicMode (Player playerTarget , String[] orderEffect, int xplayer,int yplayer, int xtarget,int ytarget ) throws IllegalStateException {
        if (!checkAvaliableMode()[0]) //check mode
            throw new IllegalStateException("Modalità xxx dell'arma: " + name + " non eseguibile");

        int i = 0;
        boolean[] booleans = checkAvaliableMode();
        boolean rememberToMoveTarget = false;
        while (i < orderEffect.length)
        {
            if (orderEffect[i].equals("basic"))
            {
                if (!checkAvaliableMode()[0])
                    throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

                doDamage(playerTarget, 1);
                rememberToMoveTarget = true;
            }
            if (orderEffect[i].equals("with rocket jump:") && booleans[1]) {
                if (!checkAvaliableMode()[2])
                    throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

                moveTarget(this.player, xplayer, yplayer);
                player.setAmmoBlue(this.player.getAmmoBlue() - 1);
            }
            if (orderEffect[i].equals("with fragmenting warhead") && booleans[2])
            {
                if (!checkAvaliableMode()[1])
                    throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");
                for (Player playerIterate : playerTarget.getSquare().getPlayerList())
                {
                    doDamage(playerIterate,1);
                }
                this.player.setAmmoYellow(this.player.getAmmoYellow() - 1);
            }
            i++;

            if (rememberToMoveTarget)
                moveTarget(playerTarget, xtarget, ytarget);
        }

        this.isLoaded = false;
    }


    public Set<Player> checkWithRocketJump ()
    {
        return checkPhaseGlide();
    }


    public List<Player> checkWithFragmentingWarhead ()
    {
        List<Player> playersTargetList = new ArrayList<>();
        playersTargetList = player.getSquare().getPlayerList();
        playersTargetList.remove(player);

        return playersTargetList;
    }


    public Set<Player> checkPhaseGlide() throws IllegalStateException
    {
        Set<Player> playerReachable = new HashSet<>();
        if (!checkAvaliableMode()[2]) //check mode
            throw  new IllegalStateException("Modalità dell'arma: "+name+" non eseguibile");
        Set<Square> target=player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX() , player.getSquare().getY(),2); //Obtain all players that can be targets



        for(Square i : target)
        {
            dummie.setSquare(i);
            if  (dummie.playerThatSee(dummie.getSquare().getGameBoard()).size() > 0)
                playerReachable.addAll(dummie.playerThatSee(dummie.getSquare().getGameBoard()));
        }
        return playerReachable;//Returns all targets

    }
}