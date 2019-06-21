package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Track;
import it.polimi.deib.se2018.adrenalina.View.GUI.BoardGUI;
import it.polimi.deib.se2018.adrenalina.View.GUI.SetupGui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AppClient
{
    public static void main(String[] args)
    {

        //SetupGui y = new SetupGui();
    //Altre prove

        BoardGUI x = new BoardGUI();
        ColorId[] expOut = new ColorId[12]; //Expected output
        List<ColorId> temp1 = new LinkedList<>();
        temp1.add(ColorId.YELLOW);
        temp1.add(ColorId.YELLOW);

        expOut[0] = ColorId.PURPLE;
        expOut[1] = ColorId.PURPLE;
        expOut[2] = ColorId.BLUE;
        expOut[3] = ColorId.BLUE;
        expOut[4] = ColorId.TERMINATOR;
        expOut[5] = ColorId.GREY;
        expOut[6] = ColorId.GREY;
        expOut[7] = ColorId.PURPLE;
        expOut[8] = ColorId.BLUE;
        expOut[9] = ColorId.BLUE;
        expOut[10] = ColorId.GREY;
        expOut[11] = ColorId.GREEN;




        x.setPlayerBoard(ColorId.PURPLE);
        x.setWeapon("Cyberguanto",1,true);
        x.setWeapon("Martello Ionico",2,false);
        x.setWeapon("Falce Protonica",3,true);
        x.setWeaponPointB("");
        x.setWeaponPointR("");
        x.setWeaponPointY("");
        x.setAmmoY(String.valueOf(2));
        x.setAmmoR(String.valueOf(3));
        x.setAmmoB(String.valueOf(2));
        x.setPower(211,1);
        x.setPower(212,2);
        x.setPower(213,3);
        x.setPower(22,2);
        x.setScore(34);
        x.setSkullForScore(2);
        x.setDamages(expOut);

        List<Track> temp = new ArrayList<>();

        temp.add(new Track(ColorId.BLUE,1));
        temp.add(new Track(ColorId.GREY,1));
        temp.add(new Track(ColorId.PURPLE,1));


        x.setPanelSkulls(5,temp);

        x.setTextMarker(temp1.toString());

        x.addTextForInput("Scegli l'arma:");
        x.addOptionForInput("1:mitragliarice");
        x.addOptionForInput("2:fucile");

        x.getInputChoice();



    }
}
