

import view.SimulatorView;

/**
 * Main-luokka, joka käynnistää JavaFX-sovelluksen (käyttöliittymän).
 */


public class Main {

    /**
     * Main-metodi, joka käynnistää JavaFX-sovelluksen (käyttöliittymän).
     * @param args komentoriviparametrit
     */

    // JavaFX-sovelluksen (käyttöliittymän) käynnistäminen
    public static void main(String[] args) {
        SimulatorView.launch(SimulatorView.class);
    }
}
