package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.PlayerImmutable;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.UpdateModel;

import java.util.List;
import java.util.stream.Collectors;

public class CLI implements Terminal
{
    ColorId playerOfThisCli;
    UpdateModel data;

    public CLI(ColorId playerOfThisCli)
    {
        this.playerOfThisCli = playerOfThisCli;
    }

    @Override
    public void showBoard()
    {
        //Obtain all the weapons in spawnpoints
        List<String> blueWeapons = data.getDataOfAllSquare()
                .stream()
                .filter(squareImmutable -> squareImmutable.getX() ==3 && squareImmutable.getY() == 3 )
                .collect(Collectors.toList()).get(0).getWeaponCardList();

        List<String> redWeapons = data.getDataOfAllSquare()
                .stream()
                .filter(squareImmutable -> squareImmutable.getX() ==1 && squareImmutable.getY() == 2 )
                .collect(Collectors.toList()).get(0).getWeaponCardList();

        List<String> yellowWeapons = data.getDataOfAllSquare()
                .stream()
                .filter(squareImmutable -> squareImmutable.getX() ==4 && squareImmutable.getY() == 1 )
                .collect(Collectors.toList()).get(0).getWeaponCardList();

        //Print the killshootTrack
        System.out.println(data.getDataOfBoard().getKillShotTrack());

        //Print the arena
        switch (data.getDataOfBoard().getCode())
        {
            case 1:
                showBoard1();
            case 2:
                showBoard2();
            case 3:
                showBoard3();
            case 4:
                showBoard4();
        }

        //Print the weapons in spawnPoints
        System.out.println("Weapons in Blue Points:");

        System.out.println(blueWeapons);

        System.out.println("Weapons in Red Points:");

        System.out.println(redWeapons);

        System.out.println("Weapons in Yellow Points:");

        System.out.println(yellowWeapons);

        //Print the board of all players

        System.out.println("PLANCE GIOCATORI:");
        System.out.println("###############################################################################");

        for (PlayerImmutable t:data.getDataOfAllPlayer())
        {
            if (t.equals(playerOfThisCli))
                System.out.println("LA TUA PLANCIA:");

            showPlayerBoard(t);

            System.out.println("###############################################################################");
        }

    }

    public UpdateModel getData() {
        return data;
    }

    public void setData(UpdateModel data) {
        this.data = data;
    }

    @Override
    public void showPlayerBoard(PlayerImmutable player)
    {
        System.out.println(player);

        ColorId[] damages = player.getDamageCounter();

        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println(damages[0]+ "   "+damages[1]+ "   "+damages[2]+ "   "+damages[3]+ "   "+damages[4]+ "   "+damages[5]+ "   "
                +damages[6]+ "   "+damages[7]+ "   "+damages[8]+ "   "+damages[9]+ "   "+damages[10]+ "   "+damages[11]);
        System.out.println("                                                                                   ");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    @Override
    public void showMenu()
    {

    }

    @Override
    public void showFinalScore(String messageWithFinalScore)
    {
        System.out.println(messageWithFinalScore);
    }

    @Override
    public void showMessage(String message)
    {
        System.out.println(message);
    }

    @Override
    public void showError(String message)
    {
        System.out.println(message);
    }


    //############ Private Methods #############

    //Methods that to print the ARENAS
    private void showBoard1()
    {
        System.out.println(" y                                                 ");
        System.out.println("  +-----------+-----------+-----------+-----------+");
        System.out.println("  |Red        |Blue        Blue       |           |");
        System.out.println(" 3|                         Weapons   |  NULLA    |");
        System.out.println("  |           |             Point     |           |");
        System.out.println("  +           +-----  ----+----  -----+-----------+");
        System.out.println("  |Red        |Purple      Purple     |Yellow     |");
        System.out.println(" 2|  Weapons  |                                   |");
        System.out.println("  |  Point    |                       |           |");
        System.out.println("  +-----  ----+-----  ----+-----------+           +");
        System.out.println("  |White       White       White      |Yellow     |");
        System.out.println(" 1|                                     Weapons   |");
        System.out.println("  |                                   | Point     |");
        System.out.println("  +-----------+-----------+-----------+-----------+");
        System.out.println("x=      1           2            3           4     ");
    }

    private void showBoard2()
    {
        System.out.println(" y                                                 ");
        System.out.println("  +-----------+-----------+-----------+-----------+");
        System.out.println("  |Blue        Blue        Blue       |           |");
        System.out.println(" 3|                          Weapons  |  NULLA    |");
        System.out.println("  |                          Point    |           |");
        System.out.println("  +-----  ----+-----------+----  -----+-----------+");
        System.out.println("  |Red         Red         Red        |Yellow     |");
        System.out.println(" 2|  Weapons                                      |");
        System.out.println("  |  Point                            |           |");
        System.out.println("  +-----------+-----  ----+-----------+           +");
        System.out.println("  |           |White       White      |Yellow     |");
        System.out.println(" 1|  NULLA    |                          Weapons  |");
        System.out.println("  |           |                       |  Point    |");
        System.out.println("  +-----------+-----------+-----------+-----------+");
        System.out.println("x=      1           2            3           4     ");
    }

    private void showBoard3()
    {
        System.out.println(" y                                                 ");
        System.out.println("  +-----------+-----------+-----------+-----------+");
        System.out.println("  |Blue        Blue        Blue       |Green      |");
        System.out.println(" 3|                          Weapons              |");
        System.out.println("  |                          Point    |           |");
        System.out.println("  +----  -----+-----------+----  -----+------  ---+");
        System.out.println("  |Red         Red        |Yellow      Yellow     |");
        System.out.println(" 2|  Weapons              |                       |");
        System.out.println("  |  Point                |                       |");
        System.out.println("  +-----------+-----  ----+           +           +");
        System.out.println("  |           |White      |Yellow      Yellow     |");
        System.out.println(" 1|   NULLA   |                         Weapons   |");
        System.out.println("  |           |           |             Point     |");
        System.out.println("  +-----------+-----------+-----------+-----------+");
        System.out.println("x=      1           2            3           4     ");
    }

    private void showBoard4()
    {
        System.out.println(" y                                                 ");
        System.out.println("  +-----------+-----------+-----------+-----------+");
        System.out.println("  |Red        |Blue        Blue       |Green      |");
        System.out.println(" 3|                         Weapons               |");
        System.out.println("  |           |             Point     |           |");
        System.out.println("  +           +-----  ----+----  -----+------  ---+");
        System.out.println("  |Red        |Purple     |Yellow      Yellow     |");
        System.out.println(" 2|  Weapons  |           |                       |");
        System.out.println("  |  Point    |           |                       |");
        System.out.println("  +-----  ----+-----  ----+           +           +");
        System.out.println("  |White       White      |Yellow      Yellow     |");
        System.out.println(" 1|                                      Weapons  |");
        System.out.println("  |                       |              Point    |");
        System.out.println("  +-----------+-----------+-----------+-----------+");
        System.out.println("x=      1           2            3           4     ");
    }
}
