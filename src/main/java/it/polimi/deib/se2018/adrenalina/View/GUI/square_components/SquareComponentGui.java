package it.polimi.deib.se2018.adrenalina.View.GUI.square_components;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import javax.swing.*;

/**
 * This class represents a square component with all the component to show the players on
 * a square of the arena
 * @author Cysko7927
 */
public class SquareComponentGui
{
    //All the Jlabels that will contain the icons of the players and ammoTiles
    protected JLabel blue;
    protected JLabel yellow;
    protected JLabel purple;
    protected JLabel green;
    protected JLabel grey;
    protected JLabel terminator;
    protected JLabel ammoTiles;


    /**
     * Create a square component with the coordinate x,y (Pixels) to add at the Jlabel that contains the arena image
     * @param arena Jlabel that contains the arena image
     * @param y height of the square component compared to arena image
     * @param x width of the square component compared to arena image
     */
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

    /**
     * Add the icon of a player represented by the color in this square component
     * @param player player's color to add
     */
    public void addPlayer(ColorId player)
    {
        ImageIcon im = new ImageIcon(getClass().getClassLoader().getResource(player+".png"));

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

    /**
     * Add the icon of ammotile represented by the code
     * if the square haven't got an ammotiles (code = 0) the icon will not add
     * @param code code that represents the ammoTile
     */
    public void addAmmoTiles(int code)
    {
        if (code == 0)
        {
            ammoTiles.setIcon(null);
            return;
        }
        ImageIcon im = new ImageIcon(getClass().getClassLoader().getResource("AD_ammo_0"+code+".png"));
        ammoTiles.setIcon(im);
    }

    /**
     * Remove all the icons of the players and the ammo tiles
     */
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
