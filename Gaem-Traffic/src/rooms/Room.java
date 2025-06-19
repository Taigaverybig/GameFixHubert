package rooms;

import door.*;
import hints.HintSystem;
import interfaces.Hintable;
import interfaces.Questionable;
import monsters.Monster;
import player.Player;
import vraag.VraagStrategie;

import java.util.*;

/**
 * Abstracte basis‑klasse voor alle kamers.
 *  ✓ bevat Observer‑mechanisme (Door, StatusDisplay, Monster)
 *  ✓ ondersteunt /use‑voorwerpen via Map<String,Object>
 *  ✓ integreert Joker‑functionaliteit (HintJoker & KeyJokerRoom)
 *  ✓ handhaaft logica van je originele versie (voorwerp‑map, monster‑spawn, hint flow …)
 */
public abstract class Room implements Questionable, Hintable, AnswerSubject {

    /* ========== basis‑state ========== */

    protected final String naam;
    protected final HintSystem hintSystem;
    protected final VraagStrategie vraagStrategie;
    protected final Player player;

    /* ========== spel‑state ========== */

    protected Monster monster;                // kan null zijn
    protected boolean monsterGeactiveerd = false;
    protected boolean afgerond           = false;
    private   boolean hintJokerGebruikt  = false;

    /* ========== voorwerpen & deur ========== */

    private final Map<String,Object> voorwerpen = new HashMap<>();
    private final Door deur = new Door();

    /* ========== observers ========== */

    private final List<AnswerObserver> observers = new ArrayList<>();

    /* ========== ctor ========== */

    protected Room(HintSystem hintSystem, VraagStrategie strategie, Player player, String naam) {
        this.hintSystem      = hintSystem;
        this.vraagStrategie  = strategie;
        this.player          = player;
        this.naam            = naam;
    }

    /* ------------------------------------------------------------------------
       abstracte bouwstenen – elke concrete kamer moet deze invullen
       --------------------------------------------------------------------- */

    /** print een korte beschrijving (kleur‑icoon, lore, …) */
    protected abstract void printKamerInfo();

    /** spawn eventueel een monster (kans > 0); laat leeg als je er geen wilt */
    protected abstract void spawnMonster();

    /** voeg alle /use‑voorwerpen voor deze kamer toe */
    protected abstract void voegVoorwerpenToe();

    /* ------------------------------------------------------------------------
       publieke API
       --------------------------------------------------------------------- */

    /** Wordt vanuit Game aangeroepen zodra de speler de kamer binnenloopt. */
    public void enter() {
        commands.CommandHandler.setCurrentRoom(this);

        player.nextRoom();
        printKamerInfo();
        spawnMonster();
        voegVoorwerpenToe();

        // observers registreren
        addObserver(deur);
        addObserver(new StatusDisplay());
        if (monster != null) addObserver(monster);

        stelVraag();          // start de vraag‑loop
    }

    /** Vraag‑&‑antwoord‑lus – identiek aan die van je vriend, maar bewaart functies uit origineel. */
    public void stelVraag() {
        Scanner scanner = new Scanner(System.in);
        int pogingen = 0;
        boolean juist = false;

        while (pogingen < 2 && !juist && !isAfgerond()) {
            vraagStrategie.stelVraag();

            String antwoord;
            while (true) {
                antwoord = scanner.nextLine().trim();
                if (!commands.CommandHandler.verwerk(antwoord)) break;

                // Belangrijk: als de kamer door het commando is afgerond, direct stoppen
                if (isAfgerond()) {
                    player.addScore(10);
                    System.out.println("🏆 +10 punten! Totaal: " + player.getScore());
                    return;
                }
            }

            juist = vraagStrategie.controleerAntwoord(antwoord);
            notifyObservers(juist);

            if (isAfgerond()) {
                player.addScore(10);
                System.out.println("🏆 +10 punten! Totaal: " + player.getScore());
                break;
            }

            if (!juist && monster != null && !monster.isVerslagen()) {

                monster.valAan(player);


                if (!player.isAlive()) {
                    return;   // verlaat stelVraag(); hoofdloop vangt game‑over op
                }


                // Hints netjes via HintSystem-vraagmethode
                hintSystem.vraagEnVerwerkHint(scanner);
            }

            pogingen++;
        }
    }

    /* ------------------------------------------------------------------------
       Joker‑ondersteuning
       --------------------------------------------------------------------- */

    /** wordt door HintJoker opgeroepen */
    public void gebruikHintJoker() {
        if (!hintJokerGebruikt) {
            System.out.println("🎁 Joker gebruikt! Je krijgt deze hint:");
            System.out.println("Hint: " + hintSystem.geefHint());
            hintJokerGebruikt = true;
        } else {
            System.out.println("❌ Hint Joker is in deze kamer al gebruikt.");
        }
    }

    /* ------------------------------------------------------------------------
       Voorwerp‑API (gebruikt door CommandHandler)
       --------------------------------------------------------------------- */

    public Object getObject(String key)         { return voorwerpen.get(key); }
    public void   voegVoorwerpToe(String k, Object v) { voorwerpen.put(k.toLowerCase(), v); }

    /* ------------------------------------------------------------------------
       Getters voor CommandHandler & Game
       --------------------------------------------------------------------- */

    public Monster getMonster()         { return monster; }
    public void    setMonster(Monster m){ this.monster = m; }
    public Door    getDeur()            { return deur; }

    public boolean isAfgerond()         { return afgerond; }
    public void    markAfgerond()       { afgerond = true; }

    /* ------------------------------------------------------------------------
       Observer‑implementatie
       --------------------------------------------------------------------- */

    @Override public void addObserver(AnswerObserver o)    { observers.add(o);    }
    @Override public void removeObserver(AnswerObserver o) { observers.remove(o); }
    @Override public void notifyObservers(boolean correct) {
        for (AnswerObserver o : observers) o.update(correct);
        // deur gaat open? markeer afgerond zodat Game weet dat je door mag
        if (correct) afgerond = true;
    }

    /* ------------------------------------------------------------------------
       Hintable (default)
       --------------------------------------------------------------------- */
    @Override public void vraagOmHint() {
        System.out.println("Deze kamer heeft geen extra hint.");
    }

    /* ------------------------------------------------------------------------
       Kleine util om monsters te spawnen met kans‑percentage
       --------------------------------------------------------------------- */
    protected void spawnMonsterIfChance(Monster kandidaat, double kans) {
        if (Math.random() < kans) {
            this.monster = kandidaat;
            System.out.println("👹 Een " + kandidaat.getNaam() + " sluipt rond in deze kamer!");
        }
    }
    public VraagStrategie getVraagStrategie() {
        return vraagStrategie;
    }

}
