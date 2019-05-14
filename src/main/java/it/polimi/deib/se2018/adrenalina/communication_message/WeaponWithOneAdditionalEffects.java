package it.polimi.deib.se2018.adrenalina.communication_message;

import java.util.LinkedList;
import java.util.List;

public abstract class WeaponWithOneAdditionalEffects extends RequestInput
{

    boolean[] avaiableMethod = new boolean[2];
    List<String> nameModes;

    @Override
    public void printActionsAndReceiveInput()
    {
        int choice1 = 0; //Da completare
        List<Integer> acceptedInt = new LinkedList<>();

        System.out.println("Scegli modalità Arma:");

        if (avaiableMethod[0])//Print the possible effects
        {
            System.out.println("1:modalità base");
            acceptedInt.add(1);
        }


    }
}
