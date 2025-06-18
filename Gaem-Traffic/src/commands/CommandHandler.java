package commands;

import monsters.Monster;
import player.Player;
import rooms.Room;
import door.Door;
import commands.AssistentCommand;
import items.*;

public class CommandHandler {
    private static Player speler;
    private static Room huidigeKamer;
    private static AssistentCommand assistent;

    public static void setPlayer(Player p) {
        speler = p;
    }

    public static void setCurrentRoom(Room kamer) {
        huidigeKamer = kamer;
    }

    public static void setAssistent(AssistentCommand a) {
        assistent = a;
    }

    /** @return true als het een commando was, false als gewone invoer */
    public static boolean verwerk(String input) {
        if (!input.startsWith("/")) return false;

        String[] delen = input.trim().split("\\s+", 2);
        String cmd = delen[0].toLowerCase();

        switch (cmd) {
            case "/help":
                toonHelp();
                return true;
            case "/status":
                toonStatus();
                return true;
            case "/hintjoker":
                gebruikHintJoker();
                return true;
            case "/keyjoker":
                gebruikKeyJoker();
                return true;
            case "/assistent":
                gebruikAssistent();
                return true;
            case "/use":
                return gebruikVoorwerp(delen.length == 2 ? delen[1] : "");
            default:
                System.out.println("❌ Onbekende command.");
                return true;
        }
    }

    private static void toonHelp() {
        System.out.println("""
            📖 Beschikbare commands:
            /help          - Toon deze lijst
            /status        - Toon spelerstatus
            /hintjoker     - Gebruik de Hint Joker
            /keyjoker      - Gebruik de Key Joker (alleen in KeyJoker-kamer)
            /assistent     - Activeer de assistent voor hulp, tips en motivatie
            /use <object>  - Gebruik een voorwerp in de kamer
            """);
    }

    private static void toonStatus() {
        if (speler == null) {
            System.out.println("⚠ Geen speler geladen.");
        } else {
            System.out.println(speler.getStatus());
        }
    }

    private static void gebruikHintJoker() {
        if (speler == null || huidigeKamer == null) {
            System.out.println("⚠ Joker kan niet worden gebruikt zonder speler of kamer.");
            return;
        }
        speler.getJokerContext().setJoker(new Joker.HintJoker());
        speler.getJokerContext().useJoker(huidigeKamer);
    }

    private static void gebruikKeyJoker() {
        if (speler == null || huidigeKamer == null) {
            System.out.println("⚠ Joker kan niet worden gebruikt zonder speler of kamer.");
            return;
        }
        speler.getJokerContext().setJoker(new Joker.KeyJoker());
        speler.getJokerContext().useJoker(huidigeKamer);
    }

    private static void gebruikAssistent() {
        if (assistent == null) {
            System.out.println("⚠ Assistent is niet beschikbaar in deze context.");
        } else {
            assistent.activeer();
        }
    }

    private static boolean gebruikVoorwerp(String key) {
        if (key.isEmpty()) {
            System.out.println("⚠ Gebruik: /use <voorwerp>");
            return true;
        }

        if (huidigeKamer == null) {
            System.out.println("⚠ Er is momenteel geen kamer actief.");
            return true;
        }

        Object obj = huidigeKamer.getObject(key.toLowerCase());

        if (obj == null) {
            System.out.println("❌ Hier ligt geen \"" + key + "\".");
        } else if (obj instanceof MessageDisplayable msg) {
            msg.showMessage();
        } else if (obj instanceof Attackable atk) {
            Monster m = huidigeKamer.getMonster();
            atk.attack(m);
            if (m != null && m.isVerslagen()) {
                huidigeKamer.removeObserver(m);
                huidigeKamer.setMonster(null);
            }
        } else if (obj instanceof UsableOnDoor opener) {
            opener.useOnDoor(huidigeKamer.getDeur());
            if (!huidigeKamer.isAfgerond()) {
                huidigeKamer.markAfgerond();
                System.out.println("✅ De kamer is voltooid. Je kunt een nieuwe kamer kiezen!");
            }
            return false; // terug naar hoofdmenu
        } else if (obj instanceof Rewardable reward) {
            reward.grantReward(speler);
        } else {
            System.out.println("❔ Je kunt \"" + key + "\" niet gebruiken.");
        }
        return false;
    }
}
