import commands.AssistentCommand;
import hints.*;
import rooms.*;
import player.Player;
import commands.CommandHandler;
import vraag.*;

import java.util.*;

public class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Hintproviders instellen
        List<HintProvider> providers = new ArrayList<>();
        providers.add(new HelpHint());
        providers.add(new FunnyHint());
        HintSystem hintSystem = new HintSystem(providers);

        // Assistent aanmaken en registreren
        AssistentCommand assistent = new AssistentCommand(hintSystem);
        CommandHandler.setAssistent(assistent);

        // Speler aanmaken
        Player speler = new Player(1);
        CommandHandler.setPlayer(speler);

        // Kamers aanmaken met vraagstrategieën
        Room startRoom = new StartRoom(hintSystem,
                new OpenVraag("Wat voor app/website is handig om te gebruiken voor SCRUM?", "Trello"), speler);
        Room save1 = new SaveRoom(hintSystem,
                new MeerkeuzeVraag("Waar staat DoD voor?",
                        List.of("Design of Delivery", "Description of Development","Definition of Done", "Document of Decisions"),"Definition of Done"), speler);
        Room save2 = new SaveRoom(hintSystem,
                new MeerkeuzeVraag("Wat is het nut van Scrum-master?",
                        List.of("Het faciliteren van het Scrum-proces en het wegnemen van obstakels voor het team.", "Het goedkeuren van code voordat deze wordt gedeployed.", "Het schrijven van alle user stories en technische documentatie.", "Het managen van het team als een traditionele projectmanager."),
                        "Het faciliteren van het Scrum-proces en het wegnemen van obstakels voor het team." ), speler);

        Room normal1 = new NormalRoom(hintSystem,
                new MeerkeuzeVraag("Wat is een Sprint?",
                        List.of("Een vergaderweek waarin alleen wordt gepland en geëvalueerd.", "Een race om zoveel mogelijk werk te doen zonder pauzes.", "Een moment waarop het team feedback verzamelt van de klant.", "Een vast tijdsblok waarin een Scrum-team werkt aan het opleveren van een werkend product."),
                        "Een vast tijdsblok waarin een Scrum-team werkt aan het opleveren van een werkend product."), speler);
        Room normal2 = new NormalRoom(hintSystem,
                new MeerkeuzeVraag("Waar staat DoR voor?",
                        List.of("Definition of Ready", "Degree of Refinement", "Delivery of Results", "Definition of Review"), "Definition of Ready"), speler);

        Room easy1 = new EasyRoom(hintSystem,
                new MeerkeuzeVraag("Wat is een risico bij één gedeelde Product Owner voor meerdere teams?",
                        List.of("Te veel focus op één team","Geen risico’s; dit werkt altijd","Teams worden volledig autonoom","Vertraging in beslissingen door overbelasting "), "Vertraging in beslissingen door overbelasting "), speler);
        Room easy2 = new EasyRoom(hintSystem,
                new MeerkeuzeVraag("Wat zijn User-stories?",
                        List.of("- Technische specificaties die door developers worden geschreven.", "Verslagen van wat het team dagelijks heeft gedaan.", "Korte beschrijvingen van functionaliteit vanuit het perspectief van de eindgebruiker.", "Fictieve verhalen om het team te motiveren."),
                        "Korte beschrijvingen van functionaliteit vanuit het perspectief van de eindgebruiker."), speler);

        Room hard1 = new HardRoom(hintSystem,
                new MeerkeuzeVraag("Waar bestaat het Acceptatiecriteria uit?",
                        List.of("De planning van de Sprint.", "De feedback van de klant tijdens de review.", "De feedback van de klant tijdens de review.", "De voorwaarden waaraan een user-story moet voldoen om als 'klaar' te worden beschouwd."),
                        "De voorwaarden waaraan een user-story moet voldoen om als 'klaar' te worden beschouwd."), speler);
        Room hard2 = new HardRoom(hintSystem,
                new MeerkeuzeVraag("Wat is een Backlog?",
                        List.of("Een verzameling van de sprintverslagen", "Een document met alle fouten in de software.", "Een geordende lijst met taken, features, en verbeteringen die het team moet uitvoeren.", "Een lijst met contactpersonen van de klant."),
                        "Een geordende lijst met taken, features, en verbeteringen die het team moet uitvoeren."), speler);

        Room boss = new BossRoom(hintSystem,
                new MeerkeuzeVraag("Hoe meet je succes van gedeelde Scrum-samenwerking?",
                        List.of("Aantal sprints zonder incidenten.", "Productiviteit per individu.", "Regelmatige feedback, samenwerkingseffectiviteit en klantwaarde.", "Hoeveel taken er worden afgerond per dag."),
                        "Regelmatige feedback, samenwerkingseffectiviteit en klantwaarde."), speler);

        // Kamerlijst (zonder start/boss)
        List<Room> kamerLijst = new ArrayList<>(Arrays.asList(
                save1, save2, normal1, normal2, easy1, easy2, hard1, hard2
        ));

        Collections.shuffle(kamerLijst);
        speler.setTotaalKamers(kamerLijst.size() + 1); // +1 voor startkamer

        Set<Room> bezocht = new HashSet<>();

        // Startkamer
        startRoom.enter();
        CommandHandler.setCurrentRoom(startRoom);
        bezocht.add(startRoom);

        // Hoofdloop
        while (bezocht.size() < kamerLijst.size() + 1) {

            if (!speler.isAlive()) {
                System.out.println("\n💀 GAME OVER – je bent verslagen.");
                return;                     // stopt main(), programma eindigt netjes
            }

            System.out.println("\n🧭 Kies een kamer om te betreden:");
            int index = 1;
            Map<Integer, Room> keuzeMap = new HashMap<>();

            for (Room kamer : kamerLijst) {
                if (!bezocht.contains(kamer)) {
                    System.out.println("Kamer " + index);
                    keuzeMap.put(index, kamer);
                }
                index++;
            }

            System.out.print("Typ het kamernummer (bijv. 1) of /help: ");
            String input;
            while (true) {
                input = scanner.nextLine().trim();
                if (!CommandHandler.verwerk(input)) break;
            }

            try {
                int keuze = Integer.parseInt(input);
                Room gekozen = keuzeMap.get(keuze);
                if (gekozen != null && !bezocht.contains(gekozen)) {
                    gekozen.enter();
                    CommandHandler.setCurrentRoom(gekozen);
                    if (gekozen.isAfgerond()) {
                        bezocht.add(gekozen);
                        continue;
                    }
                    bezocht.add(gekozen);
                } else {
                    System.out.println("❌ Ongeldige keuze.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Typ een geldig nummer.");
            }
        }

        // Boss Room
        System.out.println("\n✅ Je hebt alle kamers bezocht. De Boss Room is nu beschikbaar!");
        boss.enter();
        CommandHandler.setCurrentRoom(boss);

        System.out.println("\n🎉 Einde van het spel! Goed gedaan.");
    }
}
