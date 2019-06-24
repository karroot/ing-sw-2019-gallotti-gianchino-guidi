package it.polimi.deib.se2018.adrenalina.View.GUI.square_components;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.AppClient;

import javax.swing.*;

/**
 * This class represents a square component with all the component to show the player on
 * a square of the arena
 * @author Cysko7927
 */
public class SquareComponentGui
{
    protected JLabel blue;
    protected JLabel yellow;
    protected JLabel purple;
    protected JLabel green;
    protected JLabel grey;
    protected JLabel terminator;
    protected JLabel ammoTiles;


    public SquareComponentGui(JLabel arena,int y,int x)
    {
        blue = new JLabel();
        yellow = new JLabel();
        purple = new JLabel();
        green = new JLabel();
        grey = new JLabel();
        terminator = new JLabel();
        ammoTiles = new JLabel();
        arena.add(blue);
        arena.add(yellow);
        arena.add(purple);
        arena.add(green);
        arena.add(grey);
        arena.add(terminator);
        arena.add(ammoTiles);

        SpringLayout layout = (SpringLayout) arena.getLayout();

        layout.putConstraint(SpringLayout.NORTH,blue,y,SpringLayout.NORTH,arena);
        layout.putConstraint(SpringLayout.WEST,blue,x,SpringLayout.NORTH,arena);

        layout.putConstraint(SpringLayout.NORTH,yellow,y,SpringLayout.NORTH,arena);
        layout.putConstraint(SpringLayout.WEST,yellow,x,SpringLayout.NORTH,arena);

        layout.putConstraint(SpringLayout.NORTH,purple,y,SpringLayout.NORTH,arena);
        layout.putConstraint(SpringLayout.WEST,purple,x,SpringLayout.NORTH,arena);

        layout.putConstraint(SpringLayout.NORTH,green,y,SpringLayout.NORTH,arena);
        layout.putConstraint(SpringLayout.WEST,green,x,SpringLayout.NORTH,arena);

        layout.putConstraint(SpringLayout.WEST,grey,x,SpringLayout.NORTH,arena);


        layout.putConstraint(SpringLayout.WEST,yellow,1,SpringLayout.EAST,blue);
        layout.putConstraint(SpringLayout.WEST,purple,1,SpringLayout.EAST,yellow);
        layout.putConstraint(SpringLayout.WEST,green,1,SpringLayout.EAST,purple);
        layout.putConstraint(SpringLayout.NORTH,grey,1,SpringLayout.SOUTH,blue);

        layout.putConstraint(SpringLayout.NORTH,ammoTiles,1,SpringLayout.SOUTH,yellow);
        layout.putConstraint(SpringLayout.WEST,ammoTiles,1,SpringLayout.EAST,grey);
    }

    public void addPlayer(ColorId player)
    {
        ImageIcon im = new ImageIcon(AppClient.path + player+".PNG");

        switch (player)
        {
            case BLUE:
                blue.setIcon(im);
                break;
            case PURPLE:
                purple.setIcon(im);
                break;
            case GREY:
                grey.setIcon(im);
                break;
            case YELLOW:
                yellow.setIcon(im);
                break;
            case GREEN:
                green.setIcon(im);
                break;
            case TERMINATOR:
                terminator.setIcon(im);
                break;
        }
    }

    public void addAmmoTiles(int code)
    {
        ImageIcon im = new ImageIcon(AppClient.path+"AD_ammo_0"+code+".png");
        ammoTiles.setIcon(im);
    }

    public void clearSquare()
    {
        blue.setIcon(null);
        purple.setIcon(null);
        grey.setIcon(null);
        yellow.setIcon(null);
        green.setIcon(null);
        terminator.setIcon(null);
        ammoTiles.setIcon(null);
    }
}
