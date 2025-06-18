package rooms;

import hints.HintSystem;
import items.Kamerinfo;
import items.Sleutel;
import items.Zwaard;
import player.Player;
import vraag.VraagStrategie;

public class StartRoom extends Room {

    public StartRoom(HintSystem hintSystem, VraagStrategie strategie, Player player) {
        super(hintSystem, strategie, player, "Start Room");
    }

    @Override
    public void spawnMonster() {
        // Geen monster in startkamer
    }

    @Override
    public void printKamerInfo() {
        System.out.println("🎮 Welkom in het spel!");
        System.out.println("Gebruik /help voor commands.");
        System.out.println("Succes!\n");
    }

    @Override
    public void stelVraag() {
        // Geen vraag in startkamer
    }
    @Override
    public void voegVoorwerpenToe() {
        voegVoorwerpToe("boek", new Kamerinfo("Oude inscripties sieren de muur."));
        voegVoorwerpToe("zwaard", new Zwaard(40));
        voegVoorwerpToe("sleutel", new Sleutel("Bronzen sleutel"));
    }
}
