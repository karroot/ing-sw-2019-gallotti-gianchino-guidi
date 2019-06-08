//package it.polimi.deib.se2018.adrenalina.Controller;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.*;
/*
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static it.polimi.deib.se2018.adrenalina.Model.Color.*;

public class Setup
{
    public void spawn (ColorId player, Color PointOfSpawn)
    {

    }


    public void resetPlayer (ColorId player)
    {
        //resetta i danni
    }

    public void replenishBoard ()
    {

    }




    public void initialize ()
    {

    }

    public Stack<WeaponCard> createWeaponCardStack ()
    {
        Stack<WeaponCard> weaponCardStack = new Stack<>();

        Cyberblade cyberblade = new Cyberblade(YELLOW, 23, true);
        EletroSchyte eletroSchyte = new EletroSchyte(BLUE, 222, true);
        Flamethrower flamethrower = new Flamethrower(RED, 213, true);
        Furnace furnace = new Furnace(RED, 214, true);
        GrenadeLauncher grenadeLauncher = new GrenadeLauncher(RED, 212, true);
        HeatSeeker heatSeeker = new HeatSeeker(RED, 210, true);
        Hellion hellion = new Hellion(RED, 215, true);
        LockRifle lockRifle = new LockRifle(BLUE, 0221, true);
        MachineGun machineGun = new MachineGun(BLUE, 0220, true);
        PlasmaGun plasmaGun = new PlasmaGun(BLUE, 0219, true);
        PowerGlow powerGlow = new PowerGlow(YELLOW, 024, true);
        Railgun railgun = new Railgun(YELLOW, 026, true);
        RocketLauncher rocketLauncher = new RocketLauncher(RED, 0211, true);
        Shockwave shockwave = new Shockwave(YELLOW, 028, true);
        Shotgun shotgun = new Shotgun(YELLOW, 025, true);
        Sledgehammer sledgehammer = new Sledgehammer(YELLOW,022,true);
        THOR thor = new THOR(BLUE, 0216, true);
        TractorBeam tractorBeam = new TractorBeam(BLUE, 0217, true);
        VortexCannon vortexCannon = new VortexCannon(RED, 029, true);
        Whisper whisper = new Whisper(BLUE, 0218, true);
        ZX2 zx2 = new ZX2(YELLOW, 027, true);

        weaponCardStack.add(cyberblade);
        weaponCardStack.add(eletroSchyte);
        weaponCardStack.add(flamethrower);
        weaponCardStack.add(furnace);
        weaponCardStack.add(grenadeLauncher);
        weaponCardStack.add(heatSeeker);
        weaponCardStack.add(hellion);
        weaponCardStack.add(lockRifle);
        weaponCardStack.add(machineGun);
        weaponCardStack.add(plasmaGun);
        weaponCardStack.add(powerGlow);
        weaponCardStack.add(railgun);
        weaponCardStack.add(rocketLauncher);
        weaponCardStack.add(shockwave);
        weaponCardStack.add(shotgun);
        weaponCardStack.add(sledgehammer);
        weaponCardStack.add(thor);
        weaponCardStack.add(tractorBeam);
        weaponCardStack.add(vortexCannon);
        weaponCardStack.add(whisper);
        weaponCardStack.add(zx2);

        Collections.shuffle(weaponCardStack);

        return weaponCardStack;

    }

    public Stack<PowerUpCard> createPowerUpStack ()
    {
        Stack<PowerUpCard> powerUpCardStack = new Stack<>();

        220
                    221
                            IDS

                                    2 carte per colore


    }

    public Stack<AmmoTiles> ammoTilesStack ()
    {
        Stack<AmmoTiles> ammoTilesStack = new Stack<>();

        JustAmmo justAmmo1 = new JustAmmo(042, YELLOW, BLUE);
        JustAmmo justAmmo2 = new JustAmmo (043, YELLOW, RED);
        JustAmmo justAmmo3 = new JustAmmo (044, RED, BLUE);
        JustAmmo justAmmo4 = new JustAmmo (045, RED, YELLOW);
        JustAmmo justAmmo5 = new JustAmmo (046, BLUE, YELLOW);
        JustAmmo justAmmo6= new JustAmmo (047, BLUE, RED);
        JustAmmo justAmmo7 = new JustAmmo (048, YELLOW, BLUE);
        JustAmmo justAmmo8 = new JustAmmo (049, YELLOW, RED);
        JustAmmo justAmmo9 = new JustAmmo (0410, RED, BLUE);
        JustAmmo justAmmo10 = new JustAmmo (0411, RED, YELLOW);
        JustAmmo justAmmo11 = new JustAmmo (0412, BLUE, YELLOW);
        JustAmmo justAmmo12 = new JustAmmo (0413, BLUE, RED);
        JustAmmo justAmmo13 = new JustAmmo (0414, YELLOW, BLUE);
        JustAmmo justAmmo14 = new JustAmmo (0415, YELLOW, RED);
        JustAmmo justAmmo15 = new JustAmmo (0416, RED, BLUE);
        JustAmmo justAmmo16 = new JustAmmo (0417, RED, YELLOW);
        JustAmmo justAmmo17 = new JustAmmo (0418, BLUE, YELLOW);
        JustAmmo justAmmo18 = new JustAmmo (0419, BLUE, RED);

        PowerAndAmmo powerAndAmmo1 = new PowerAndAmmo (0420, YELLOW, YELLOW);
        PowerAndAmmo powerAndAmmo2 = new PowerAndAmmo (0421, RED, RED);
        PowerAndAmmo powerAndAmmo3 = new PowerAndAmmo (0422, BLUE, BLUE);
        PowerAndAmmo powerAndAmmo4 = new PowerAndAmmo (0423, YELLOW, RED);
        PowerAndAmmo powerAndAmmo5 = new PowerAndAmmo (0424, YELLOW, BLUE);
        PowerAndAmmo powerAndAmmo6 = new PowerAndAmmo (0425, RED, BLUE);
        PowerAndAmmo powerAndAmmo7 = new PowerAndAmmo (0426, YELLOW, RED);
        PowerAndAmmo powerAndAmmo8 = new PowerAndAmmo (0427, YELLOW, BLUE);
        PowerAndAmmo powerAndAmmo9 = new PowerAndAmmo(0428, RED, BLUE);
        PowerAndAmmo powerAndAmmo10 = new PowerAndAmmo (0429, YELLOW, YELLOW);
        PowerAndAmmo powerAndAmmo11 = new PowerAndAmmo (0430, RED, RED);
        PowerAndAmmo powerAndAmmo12 = new PowerAndAmmo (0431, BLUE, BLUE);
        PowerAndAmmo powerAndAmmo13 = new PowerAndAmmo (0432, YELLOW, RED);
        PowerAndAmmo powerAndAmmo14 = new PowerAndAmmo (0433, YELLOW, BLUE);
        PowerAndAmmo powerAndAmmo15 = new PowerAndAmmo (0434, RED, BLUE);
        PowerAndAmmo powerAndAmmo16 = new PowerAndAmmo(0435, YELLOW, RED);
        PowerAndAmmo powerAndAmmo17 = new PowerAndAmmo(0436, YELLOW, BLUE);
        PowerAndAmmo powerAndAmmo18 = new PowerAndAmmo (0437, RED, BLUE);

        ammoTilesStack.add(justAmmo1);
        ammoTilesStack.add(justAmmo2);
        ammoTilesStack.add(justAmmo3);
        ammoTilesStack.add(justAmmo4);
        ammoTilesStack.add(justAmmo5);
        ammoTilesStack.add(justAmmo6);
        ammoTilesStack.add(justAmmo7);
        ammoTilesStack.add(justAmmo8);
        ammoTilesStack.add(justAmmo9);
        ammoTilesStack.add(justAmmo10);
        ammoTilesStack.add(justAmmo11);
        ammoTilesStack.add(justAmmo12);
        ammoTilesStack.add(justAmmo13);
        ammoTilesStack.add(justAmmo14);
        ammoTilesStack.add(justAmmo15);
        ammoTilesStack.add(justAmmo16);
        ammoTilesStack.add(justAmmo17);
        ammoTilesStack.add(justAmmo18);


        ammoTilesStack.add(powerAndAmmo1);
        ammoTilesStack.add(powerAndAmmo2);
        ammoTilesStack.add(powerAndAmmo3);
        ammoTilesStack.add(powerAndAmmo4);
        ammoTilesStack.add(powerAndAmmo5);
        ammoTilesStack.add(powerAndAmmo6);
        ammoTilesStack.add(powerAndAmmo7);
        ammoTilesStack.add(powerAndAmmo8);
        ammoTilesStack.add(powerAndAmmo9);
        ammoTilesStack.add(powerAndAmmo10);
        ammoTilesStack.add(powerAndAmmo11);
        ammoTilesStack.add(powerAndAmmo12);
        ammoTilesStack.add(powerAndAmmo13);
        ammoTilesStack.add(powerAndAmmo14);
        ammoTilesStack.add(powerAndAmmo15);
        ammoTilesStack.add(powerAndAmmo16);
        ammoTilesStack.add(powerAndAmmo17);
        ammoTilesStack.add(powerAndAmmo18);

        Collections.shuffle(ammoTilesStack);

        return ammoTilesStack;

    }

    public List<Player> createAllPlayers ()
    {
        List<Player> playerList = new ArrayList<>();

        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        Player player4 = new Player();
        Player player5 = new Player(); //todo mettere contatori i per numero max player e creare solo fino a dove necessario

        //aggiungi lista
        //shuffle
        //return
    }

}
*/