package it.polimi.deib.se2018.adrenalina.Model;


public interface StatePlayer {

    public Player state;

    /**
     * @param len
     */
    public void runAround( int len);


    public void grabStuff();


    public void shootPeople();


    public void reload();

}