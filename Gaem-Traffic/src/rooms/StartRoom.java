package rooms;

import hints.HintSystem;
import items.Kamerinfo;
import items.Sleutel;
import items.Zwaard;
import player.Player;
import vraag.VraagStrategie;

import java.util.Scanner;

public class StartRoom extends Room {

    String eersteKeer;

    public StartRoom(HintSystem hintSystem, VraagStrategie strategie, Player player) {
        super(hintSystem, strategie, player, "Start Room");
    }

    @Override
    public void spawnMonster() {
        // Geen monster in startkamer
    }

    public void startInfo(){
        System.out.println("\n Veel plezier met het spel.");
        System.out.println("En succes met het verslaan van de eindbaas! >:)");
    }

    @Override
    public void printKamerInfo() {

        System.out.println("🎮 Welkom in onze EscrumRoom!");
        System.out.println("Speel je het voor het eerst? (ja/nee)");
    }

    @Override
    public void stelVraag() {
        Scanner scanner = new Scanner(System.in);

        eersteKeer = scanner.next();
        if(eersteKeer.equalsIgnoreCase("Ja")){
            geefUitleg();
        }
        else{
           startInfo();
        }
    }

    private void geefUitleg() {
        System.out.println("\n📘 UITLEG:");
        System.out.println("- Je start met 100 HP.");
        System.out.println("- Elke kamer bevat een vraag.");
        System.out.println("- Je hebt 2 pogingen per kamer.");
        System.out.println("- Bij een fout antwoord kan een monster je aanvallen!");
        System.out.println("- Gebruik /help voor een lijst met commands.");
        System.out.println("- Als je HP op 0 komt, ben je DOOD.");
        System.out.println("- Je hebt meerdere items die je kan gebruiken. (Zwaard, Sleutel, Kamerinfo, Schatkist)");
        System.out.println("- Je hebt een KeyJoker die alleen werkt in makkelijke kamers.");
        System.out.println("Veel succes, avonturier! 🧭\n");
    }

    @Override
    public void voegVoorwerpenToe() {
        voegVoorwerpToe("boek", new Kamerinfo("Oude inscripties sieren de muur."));
        voegVoorwerpToe("zwaard", new Zwaard(40));
        voegVoorwerpToe("sleutel", new Sleutel("Bronzen sleutel"));
    }

}