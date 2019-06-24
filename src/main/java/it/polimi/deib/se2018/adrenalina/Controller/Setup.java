package it.polimi.deib.se2018.adrenalina.Controller;//package it.polimi.deib.se2018.adrenalina.Controller;


import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static it.polimi.deib.se2018.adrenalina.Model.Color.*;

public class Setup
{

    Controller controller;
    private int codeArena;
    private boolean terminator;

    public Setup(Controller controller,int codeArena,boolean terminator)
    {
        this.controller = controller;
        this.codeArena = codeArena;
        this.terminator = terminator;
    }

    public void spawn (ColorId player, Color PointOfSpawn)
    {

    }


    public void resetPlayer (ColorId player)
    {
        //resetta i danni
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
        LockRifle lockRifle = new LockRifle(BLUE, 221, true);
        MachineGun machineGun = new MachineGun(BLUE, 220, true);
        PlasmaGun plasmaGun = new PlasmaGun(BLUE, 219, true);
        PowerGlow powerGlow = new PowerGlow(YELLOW, 24, true);
        Railgun railgun = new Railgun(YELLOW, 26, true);
        RocketLauncher rocketLauncher = new RocketLauncher(RED, 211, true);
        Shockwave shockwave = new Shockwave(YELLOW, 28, true);
        Shotgun shotgun = new Shotgun(YELLOW, 25, true);
        Sledgehammer sledgehammer = new Sledgehammer(YELLOW,22,true);
        THOR thor = new THOR(BLUE, 216, true);
        TractorBeam tractorBeam = new TractorBeam(BLUE, 217, true);
        VortexCannon vortexCannon = new VortexCannon(RED, 29, true);
        Whisper whisper = new Whisper(BLUE, 218, true);
        ZX2 zx2 = new ZX2(YELLOW, 27, true);

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

        PowerUpCard newtonRed1 = new Newton(RED, 29);
        PowerUpCard newtonRed2 = new Newton(RED, 29);
        PowerUpCard newtonBlue1 = new Newton(BLUE,28);
        PowerUpCard newtonBlue2 = new Newton(BLUE, 28);
        PowerUpCard newtonYellow1 = new Newton(YELLOW, 210);
        PowerUpCard newtonYellow2 = new Newton(YELLOW, 210);

        PowerUpCard teleporterRed1 = new Teleporter(RED, 212);
        PowerUpCard teleporterRed2 = new Teleporter(RED, 212);
        PowerUpCard teleporterBlue1 = new Teleporter(BLUE,211);
        PowerUpCard teleporterBlue2 = new Teleporter(BLUE, 211);
        PowerUpCard teleporterYellow1 = new Teleporter(YELLOW, 213);
        PowerUpCard teleporterYellow2 = new Teleporter(YELLOW, 213);

        PowerUpCard targettingScopeRed1 = new TargettingScope(RED, 26);
        PowerUpCard targettingScopeRed2 = new TargettingScope(RED, 26);
        PowerUpCard targettingScopeBlue1 = new TargettingScope(BLUE,25);
        PowerUpCard targettingScopeBlue2 = new TargettingScope(BLUE, 25);
        PowerUpCard targettingScopeYellow1 = new TargettingScope(YELLOW, 27);
        PowerUpCard targettingScopeYellow2 = new TargettingScope(YELLOW, 27);

        PowerUpCard tagbackGrenadeRed1 = new TagbackGranade(RED, 23);
        PowerUpCard tagbackGrenadeRed2 = new TagbackGranade(RED, 23);
        PowerUpCard tagbackGrenadeBlue1 = new TagbackGranade(BLUE,22);
        PowerUpCard tagbackGrenadeBlue2 = new TagbackGranade(BLUE, 22);
        PowerUpCard tagbackGrenadeYellow1 = new TagbackGranade(YELLOW, 24);
        PowerUpCard tagbackGrenadeYellow2 = new TagbackGranade(YELLOW, 24);


        powerUpCardStack.add(newtonRed1);
        powerUpCardStack.add(newtonRed2);
        powerUpCardStack.add(newtonBlue1);
        powerUpCardStack.add(newtonBlue2);
        powerUpCardStack.add(newtonYellow1);
        powerUpCardStack.add(newtonYellow2);

        powerUpCardStack.add(teleporterRed1);
        powerUpCardStack.add(teleporterRed2);
        powerUpCardStack.add(teleporterBlue1);
        powerUpCardStack.add(teleporterBlue2);
        powerUpCardStack.add(teleporterYellow1);
        powerUpCardStack.add(teleporterYellow2);

        powerUpCardStack.add(targettingScopeRed1);
        powerUpCardStack.add(targettingScopeRed2);
        powerUpCardStack.add(targettingScopeBlue1);
        powerUpCardStack.add(targettingScopeBlue2);
        powerUpCardStack.add(targettingScopeYellow1);
        powerUpCardStack.add(targettingScopeYellow2);

        powerUpCardStack.add(tagbackGrenadeRed1);
        powerUpCardStack.add(tagbackGrenadeRed2);
        powerUpCardStack.add(tagbackGrenadeBlue1);
        powerUpCardStack.add(tagbackGrenadeBlue2);
        powerUpCardStack.add(tagbackGrenadeYellow1);
        powerUpCardStack.add(tagbackGrenadeYellow2);

        Collections.shuffle(powerUpCardStack);

        return powerUpCardStack;

    }

    public Stack<AmmoTiles> ammoTilesStack ()
    {
        Stack<AmmoTiles> ammoTilesStack = new Stack<>();

        JustAmmo justAmmo1 = new JustAmmo(42, YELLOW, BLUE);
        JustAmmo justAmmo2 = new JustAmmo (43, YELLOW, RED);
        JustAmmo justAmmo3 = new JustAmmo (44, RED, BLUE);
        JustAmmo justAmmo4 = new JustAmmo (45, RED, YELLOW);
        JustAmmo justAmmo5 = new JustAmmo (46, BLUE, YELLOW);
        JustAmmo justAmmo6= new JustAmmo (47, BLUE, RED);
        JustAmmo justAmmo7 = new JustAmmo (42, YELLOW, BLUE);
        JustAmmo justAmmo8 = new JustAmmo (43, YELLOW, RED);
        JustAmmo justAmmo9 = new JustAmmo (44, RED, BLUE);
        JustAmmo justAmmo10 = new JustAmmo (45, RED, YELLOW);
        JustAmmo justAmmo11 = new JustAmmo (46, BLUE, YELLOW);
        JustAmmo justAmmo12 = new JustAmmo (47, BLUE, RED);
        JustAmmo justAmmo13 = new JustAmmo (42, YELLOW, BLUE);
        JustAmmo justAmmo14 = new JustAmmo (43, YELLOW, RED);
        JustAmmo justAmmo15 = new JustAmmo (44, RED, BLUE);
        JustAmmo justAmmo16 = new JustAmmo (45, RED, YELLOW);
        JustAmmo justAmmo17 = new JustAmmo (46, BLUE, YELLOW);
        JustAmmo justAmmo18 = new JustAmmo (47, BLUE, RED);

        PowerAndAmmo powerAndAmmo1 = new PowerAndAmmo (420, YELLOW, YELLOW);
        PowerAndAmmo powerAndAmmo2 = new PowerAndAmmo (421, RED, RED);
        PowerAndAmmo powerAndAmmo3 = new PowerAndAmmo (422, BLUE, BLUE);
        PowerAndAmmo powerAndAmmo4 = new PowerAndAmmo (423, YELLOW, RED);
        PowerAndAmmo powerAndAmmo5 = new PowerAndAmmo (424, YELLOW, BLUE);
        PowerAndAmmo powerAndAmmo6 = new PowerAndAmmo (425, RED, BLUE);
        PowerAndAmmo powerAndAmmo7 = new PowerAndAmmo (426, YELLOW, RED);
        PowerAndAmmo powerAndAmmo8 = new PowerAndAmmo (427, YELLOW, BLUE);
        PowerAndAmmo powerAndAmmo9 = new PowerAndAmmo(428, RED, BLUE);
        PowerAndAmmo powerAndAmmo10 = new PowerAndAmmo (420, YELLOW, YELLOW);
        PowerAndAmmo powerAndAmmo11 = new PowerAndAmmo (421, RED, RED);
        PowerAndAmmo powerAndAmmo12 = new PowerAndAmmo (422, BLUE, BLUE);
        PowerAndAmmo powerAndAmmo13 = new PowerAndAmmo (423, YELLOW, RED);
        PowerAndAmmo powerAndAmmo14 = new PowerAndAmmo (424, YELLOW, BLUE);
        PowerAndAmmo powerAndAmmo15 = new PowerAndAmmo (425, RED, BLUE);
        PowerAndAmmo powerAndAmmo16 = new PowerAndAmmo(426, YELLOW, RED);
        PowerAndAmmo powerAndAmmo17 = new PowerAndAmmo(427, YELLOW, BLUE);
        PowerAndAmmo powerAndAmmo18 = new PowerAndAmmo (428, RED, BLUE);

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


    public List<Player> createPlayers ()
    {
        List<Player> playerList = new ArrayList<>();

        int j = controller.numberOfPlayer();

        boolean isFirst = true;

        for (int i=1; i==j; i++)
        {
            if (isFirst)
            {
                playerList.add(new Player(controller.getColorOfPlayer(i), controller.getName(i), controller.getHeroComment(i), true));
                isFirst = false;
            }
            else
                {
                playerList.add(new Player(controller.getColorOfPlayer(i), controller.getName(i), controller.getHeroComment(i),false));
                }
        }
        return playerList;
    }

    public void replenishBoard (GameBoard gameBoard)
    {

        for (Room roomIterate : gameBoard.getRoomList())
        {
            for (Square squareIterate : roomIterate.getSquareList())
            {
                if (squareIterate.isSpawnPoint())
                {
                    if (((SpawnPoint) squareIterate).getWeaponCardList().size()<3) {
                        try{
                        ((SpawnPoint) squareIterate).addNewWeapon();
                    }
                    catch(NullPointerException e){

                    }
                    }
                }

                if (squareIterate.isAmmoPoint())
                {
                    if (((AmmoPoint) squareIterate).getAmmoTiles() == null)
                        ((AmmoPoint) squareIterate).replaceAmmoTiles();
                }
            }
        }

    }



}
