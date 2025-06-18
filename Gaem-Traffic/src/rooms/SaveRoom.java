package rooms;

import hints.HintSystem;
import items.Kamerinfo;
import items.Sleutel;
import items.Zwaard;
import player.Player;
import vraag.VraagStrategie;

public class SaveRoom extends Room {

    public SaveRoom(HintSystem hintSystem, VraagStrategie strategie, Player player) {
        super(hintSystem, strategie, player, "Save Room");
    }

    @Override
    public void spawnMonster() {
    }

    @Override
    public void printKamerInfo() {
        System.out.println("💾 Je bent in een Save Room!");
        System.out.println("Je voelt je opgeladen... +20 HP!");
        player.increaseHP(20);
        System.out.println("Nieuwe HP: " + player.getHP());
    }

    @Override
    public void stelVraag() {
    }
    @Override
    public void voegVoorwerpenToe() {
        voegVoorwerpToe("boek", new Kamerinfo("Oude inscripties sieren de muur."));
        voegVoorwerpToe("zwaard", new Zwaard(40));
        voegVoorwerpToe("sleutel", new Sleutel("Bronzen sleutel"));
    }
}
