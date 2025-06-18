package rooms;

import hints.HintSystem;
import monsters.Skeleton;
import player.Player;
import vraag.VraagStrategie;
import items.Kamerinfo;
import items.Zwaard;
import items.Sleutel;

public class HardRoom extends Room {
    public HardRoom(HintSystem hintSystem, VraagStrategie strategie, Player player) {
        super(hintSystem, strategie, player, "Hard Room");
    }

    @Override
    public void printKamerInfo() {
        System.out.println("🔴 Je bent in een moeilijke kamer.");
    }

    @Override
    public void spawnMonster() {
        spawnMonsterIfChance(new Skeleton(), 0.4);
    }

    @Override
    public void voegVoorwerpenToe() {
        voegVoorwerpToe("boek", new Kamerinfo("Oude inscripties sieren de muur."));
        voegVoorwerpToe("zwaard", new Zwaard(40));
        voegVoorwerpToe("sleutel", new Sleutel("Bronzen sleutel"));
    }
}
