package BlueRidingHood;

import BlueRidingHood.Game.Game;

public class Main {
    //todo try catch cazuri de eraore etc!!!, if else fara inca un if
    //todo check la clase constructori and stuff
    //todo testValues pentru a verifica daca ce crezzi tu e ce e defapt
    //todo add to documentatie schimbari plus preluari
    //todo functii cat mai specifice
    //todo add coments for documentation
    //todo add gui
    //todo check specificatori de acces
    //todo close cu tot cu fereastra
    //todo game stop fara imposibilitate de inchidere ferestra
    //todo check player alive
    //todo map3
    public static void main(String[] args) {
       // Map.map1.testMap();
        Game BlueRidingHood = Game.provideGame();
        BlueRidingHood.StartGame();
    }

}
