package it.polimi.deib.se2018.adrenalina.Model;

public class JustAmmo extends AmmoTiles
{
// nella funzione che genera gameboard chiami costruttore 36 volte con id diverso

    private int ammoCardID;
    private Color singleAmmo;
    private Color doubleAmmo;

    /**
     * Create a card with its parameters
     * @param ammoCardID is the unique int that identifies a single card. We will use the same ID that are in the file containings the images of the cards
     * @param singleAmmo indicates the first ammo drawn
     * @param doubleAmmo indicates the color of the 2 same ammo drawn
     */
    public JustAmmo(int ammoCardID, Color singleAmmo, Color doubleAmmo)
    {
        this.ammoCardID=ammoCardID;
        this.singleAmmo=singleAmmo;
        this.doubleAmmo=doubleAmmo;


    }


        /*
        new JustAmmo = JustAmmo (042, YELLOW, BLUE);
        new JustAmmo = JustAmmo (043, YELLOW, RED);
        new JustAmmo = JustAmmo (044, RED, BLUE);
        new JustAmmo = JustAmmo (045, RED, YELLOW);
        new JustAmmo = JustAmmo (046, BLUE, YELLOW);
        new JustAmmo = JustAmmo (047, BLUE, RED);
        new JustAmmo = JustAmmo (048, YELLOW, BLUE);
        new JustAmmo = JustAmmo (049, YELLOW, RED);
        new JustAmmo = JustAmmo (0410, RED, BLUE);
        new JustAmmo = JustAmmo (0411, RED, YELLOW);
        new JustAmmo = JustAmmo (0412, BLUE, YELLOW);
        new JustAmmo = JustAmmo (0413, BLUE, RED);
        new JustAmmo = JustAmmo (0414, YELLOW, BLUE);
        new JustAmmo = JustAmmo (0415, YELLOW, RED);
        new JustAmmo = JustAmmo (0416, RED, BLUE);
        new JustAmmo = JustAmmo (0417, RED, YELLOW);
        new JustAmmo = JustAmmo (0418, BLUE, YELLOW);
        new JustAmmo = JustAmmo (0419, BLUE, RED);

         */
}