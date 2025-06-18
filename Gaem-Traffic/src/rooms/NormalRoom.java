package rooms;

import commands.CommandHandler;
import door.*;
import hints.HintSystem;
import items.Kamerinfo;
import items.Sleutel;
import items.Zwaard;
import player.Player;
import vraag.VraagStrategie;

import java.util.Scanner;

public class NormalRoom extends Room {
    public NormalRoom(HintSystem hintSystem, VraagStrategie strategie, Player player) {
        super(hintSystem, strategie, player, "Normal Room");
    }

    @Override
    public void spawnMonster() {
        spawnMonsterIfChance(new monsters.Zombie(), 0.8);
    }

    @Override
    public void printKamerInfo() {
        System.out.println("⚪ Je bent in een normale kamer.");
    }
    @Override
    public void voegVoorwerpenToe() {
        voegVoorwerpToe("boek", new Kamerinfo("Oude inscripties sieren de muur."));
        voegVoorwerpToe("zwaard", new Zwaard(40));
        voegVoorwerpToe("sleutel", new Sleutel("Bronzen sleutel"));
    }
}