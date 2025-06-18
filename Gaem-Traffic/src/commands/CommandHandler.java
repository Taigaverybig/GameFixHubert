package commands;

import Joker.HintJoker;
import Joker.KeyJoker;
import Joker.Joker;
import hints.HintSystem;
import items.*;
import monsters.Monster;
import player.Player;
import rooms.Room;

/**
 * Verwerkt alle slash‑commands van de speler.
 * Combineert:
 *  • /help, /status, /use (uit jouw originele code)
 *  • /hintjoker, /keyjoker, /assistent (toegevoegd door je vriend)
 */
public class CommandHandler {

    public static Room getCurrentRoom() {
        return huidigeKamer;
    }

    /* ================= state ================= */

    private static Player speler;
    private static Room   huidigeKamer;
    private static AssistentCommand assistent;

    /* ================= setters ================= */

    public static void setPlayer(Player p)           { speler       = p; }
    public static void setCurrentRoom(Room kamer)    { huidigeKamer = kamer; }
    public static void setAssistent(AssistentCommand a) { assistent = a; }

    /* ================= dispatcher ================= */

    /** @return true ↦ invoer was een command, false ↦ gewone invoer */
    public static boolean verwerk(String input) {
        if (!input.startsWith("/")) return false;           // geen command

        String[] delen = input.trim().split("\\s+", 2);
        String   cmd   = delen[0].toLowerCase();

        return switch (cmd) {
            case "/help"      -> { toonHelp();          yield true; }
            case "/status"    -> { toonStatus();        yield true; }
            case "/hintjoker" -> { gebruikHintJoker();  yield true; }
            case "/keyjoker"  -> { gebruikKeyJoker();   yield true; }
            case "/assistent" -> { gebruikAssistent();  yield true; }
            case "/use"       -> gebruikVoorwerp(delen.length == 2 ? delen[1] : "");
            default           -> { System.out.println("❌ Onbekende command."); yield true; }
        };
    }

    /* ================= helpers ================= */

    private static void toonHelp() {
        System.out.println("""
            📖 Beschikbare commands:
            /help            - Toon deze lijst
            /status          - Toon spelerstatus
            /use <voorwerp>  - Gebruik een voorwerp in de kamer
            /hintjoker       - Gebruik de Hint Joker
            /keyjoker        - Gebruik de Key Joker (alleen in Key‑Joker‑kamers)
            /assistent       - Activeer de AI‑assistent (tips & motivatie)
            """);
    }

    private static void toonStatus() {
        if (speler == null) {
            System.out.println("⚠ Geen speler geladen.");
        } else {
            System.out.println(speler.getStatus());
        }
    }

    /* -------- jokers & assistent -------- */

    private static void gebruikHintJoker() {
        if (checkSpelerEnKamer()) {
            speler.getJokerContext().setJoker(new HintJoker());
            speler.getJokerContext().useJoker(huidigeKamer);
        }
    }

    private static void gebruikKeyJoker() {
        if (checkSpelerEnKamer()) {
            speler.getJokerContext().setJoker(new KeyJoker());
            speler.getJokerContext().useJoker(huidigeKamer);
        }
    }

    private static void gebruikAssistent() {
        if (assistent == null) {
            System.out.println("⚠ Assistent is niet beschikbaar in deze context.");
        } else {
            assistent.activeer();
        }
    }

    private static boolean checkSpelerEnKamer() {
        if (speler == null || huidigeKamer == null) {
            System.out.println("⚠ Joker kan niet worden gebruikt zonder speler of kamer.");
            return false;
        }
        return true;
    }

    /* -------- /use -------- */

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
            System.out.printf("❌ Hier ligt geen \"%s\".%n", key);
        }
        else if (obj instanceof MessageDisplayable msg) {
            msg.showMessage();

        }
        else if (obj instanceof Attackable atk) {
            Monster m = huidigeKamer.getMonster();
            atk.attack(m);
            if (m != null && m.isVerslagen()) {
                huidigeKamer.removeObserver(m);
                huidigeKamer.setMonster(null);
            }

        }
        else if (obj instanceof UsableOnDoor opener) {
            opener.useOnDoor(huidigeKamer.getDeur());
            if (!huidigeKamer.isAfgerond()) {
                huidigeKamer.markAfgerond();
                System.out.println("✅ De kamer is voltooid. Je kunt een nieuwe kamer kiezen!");
            }
            return false;        // direct terug naar menu

        }
        else if (obj instanceof Rewardable reward) {
            reward.grantReward(speler);

        }
        else {
            System.out.printf("❔ \"%s\" kun je hier niet gebruiken.%n", key);
        }
        return false;
    }
}
