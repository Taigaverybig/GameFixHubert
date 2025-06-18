package rooms;

import door.AnswerObserver;
import door.AnswerSubject;
import door.Door;
import door.StatusDisplay;
import hints.HintSystem;
import interfaces.Questionable;
import interfaces.Hintable;
import monsters.Monster;
import player.Player;
import vraag.VraagStrategie;

import java.util.*;

public abstract class Room implements Questionable, Hintable, AnswerSubject {
    protected String naam;
    protected final HintSystem hintSystem;
    protected Monster monster = null;
    protected boolean monsterGeactiveerd = false;
    private boolean hintJokerGebruikt = false;
    protected VraagStrategie vraagStrategie;
    protected Player player;

    protected final Map<String, Object> voorwerpen = new HashMap<>();
    protected Door deur = new Door();
    protected boolean afgerond = false;

    private final List<AnswerObserver> observers = new ArrayList<>();

    public Room(HintSystem hintSystem, VraagStrategie strategie, Player player, String naam) {
        this.hintSystem = hintSystem;
        this.vraagStrategie = strategie;
        this.player = player;
        this.naam = naam;
    }

    public abstract void spawnMonster();
    public abstract void printKamerInfo();
    public abstract void voegVoorwerpenToe();

    public void enter() {
        player.nextRoom();
        printKamerInfo();
        spawnMonster();
        voegVoorwerpenToe();

        addObserver(deur);
        addObserver(new StatusDisplay());
        if (monster != null) addObserver(monster);

        stelVraag();
    }

    public void stelVraag() {
        Scanner scanner = new Scanner(System.in);
        int pogingen = 0;
        boolean juist = false;

        while (pogingen < 2 && !juist && !isAfgerond()) {
            vraagStrategie.stelVraag();

            while (true) {
                String invoer = scanner.nextLine().trim();

                if (commands.CommandHandler.verwerk(invoer)) {
                    if (isAfgerond()) {
                        return;
                    }
                    vraagStrategie.stelVraag();
                    continue;
                }

                if (isAfgerond()) {  // <-- Belangrijkste fix hier!
                    return;
                }

                juist = vraagStrategie.controleerAntwoord(invoer);
                notifyObservers(juist);
                break;
            }

            if (isAfgerond()) return;

            if (!juist && pogingen == 0 && monster != null && !monster.isVerslagen()) {
                if (!monsterGeactiveerd) {
                    monster.valAan(player);
                    monsterGeactiveerd = true;
                }
                System.out.println("Wil je een hint? (ja/nee)");
                while (true) {
                    String hintAntwoord = scanner.nextLine().trim();
                    if (!commands.CommandHandler.verwerk(hintAntwoord)) {
                        hintSystem.toonHintAlsGewenst(hintAntwoord);
                        break;
                    }
                }
            }
            pogingen++;
        }
    }










    public void gebruikHintJoker() {
        if (!hintJokerGebruikt) {
            revealHintJoker();
            hintJokerGebruikt = true;
        } else {
            System.out.println("❌ Hint Joker is al gebruikt in deze kamer.");
        }
    }

    public void revealHintJoker() {
        System.out.println("🎁 Joker gebruikt! Je krijgt een hint:");
        hintSystem.toonHintAlsGewenst("ja");
    }

    protected void spawnMonsterIfChance(Monster monsterOptie, double kans) {
        if (Math.random() < kans) {
            this.monster = monsterOptie;
            System.out.println("👹 Een " + monster.getNaam() + " staat in deze kamer!");
        }
    }

    public Object getObject(String naam) {
        return voorwerpen.get(naam.toLowerCase());
    }

    public void voegVoorwerpToe(String naam, Object object) {
        voorwerpen.put(naam.toLowerCase(), object);
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster m) {
        this.monster = m;
    }

    public Door getDeur() {
        return deur;
    }

    public boolean isAfgerond() {
        return afgerond;
    }

    public void markAfgerond() {
        this.afgerond = true;
    }

    @Override
    public void addObserver(AnswerObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(AnswerObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(boolean correct) {
        for (AnswerObserver o : observers) {
            o.update(correct);
        }
    }

    @Override
    public void vraagOmHint() {
        System.out.println("Deze kamer heeft geen hint.");
    }
}
