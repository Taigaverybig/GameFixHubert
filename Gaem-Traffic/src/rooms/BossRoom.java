package rooms;

import commands.CommandHandler;
import door.*;
import hints.HintSystem;
import player.Player;
import vraag.VraagStrategie;

import java.util.Scanner;

import items.Kamerinfo;
import items.Zwaard;
import items.Sleutel;
import commands.CommandHandler;
import door.StatusDisplay;
import hints.HintSystem;
import monsters.ChickenJockey;
import player.Player;
import vraag.VraagStrategie;

public class BossRoom extends Room {

    public BossRoom(HintSystem hintSystem, VraagStrategie strategie, Player player) {
        super(hintSystem, strategie, player, "Boss Room");
    }

    @Override
    public void enter() {
        player.nextRoom();
        System.out.println("🟥 Je bent in de BossRoom.");

        spawnMonster();

        CommandHandler.setCurrentRoom(this);

        if (isAfgerond()) {
            System.out.println("✅ Je hebt deze kamer al voltooid.");
            return;
        }



        addObserver(new door.Door());
        addObserver(new StatusDisplay());
        if (monster != null) addObserver(monster);

        stelVraag(); // inherited
    }

    @Override
    public void spawnMonster() {
        double kans = 0.4;
        if (Math.random() < kans) {
            this.monster = new ChickenJockey();
            System.out.println("⚠️ Je voelt een dreiging... er is iets in deze kamer.");
        }
    }

    @Override
    public void printKamerInfo() {
        System.out.println("🟥 De lucht trilt... dit is de eindbaas kamer.");
    }
    @Override
    public void voegVoorwerpenToe() {
        voegVoorwerpToe("boek", new Kamerinfo("Oude inscripties sieren de muur."));
        voegVoorwerpToe("zwaard", new Zwaard(40));
        voegVoorwerpToe("sleutel", new Sleutel("Bronzen sleutel"));
    }
}
