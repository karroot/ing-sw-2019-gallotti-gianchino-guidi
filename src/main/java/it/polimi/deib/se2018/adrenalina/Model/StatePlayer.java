package it.polimi.deib.se2018.adrenalina.Model;


public interface StatePlayer {

    private Player state;

    /**
     * @param len
     */
    public void runAround(unsigned int len);


    public void grabStuff();


    public void shootPeople();


    public void reload();

}