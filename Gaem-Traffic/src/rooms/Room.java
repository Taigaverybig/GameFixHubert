package rooms;

import door.*;
import hints.HintSystem;
import interfaces.Hintable;
import interfaces.Questionable;
import monsters.Monster;
import player.Player;
import vraag.VraagStrategie;

import java.util.*;

public abstract class Room implements Questionable, Hintable, AnswerSubject {

    protected final String naam;
    protected final HintSystem hintSystem;
    protected final VraagStrategie vraagStrategie;
    protected final Player player;

    protected Monster monster;
    protected boolean monsterGeactiveerd = false;
    protected boolean afgerond = false;
    private boolean hintJokerGebruikt = false;

    private final Map<String, Object> voorwerpen = new HashMap<>();
    private final Door deur = new Door();
    private final List<AnswerObserver> observers = new ArrayList<>();

    protected Room(HintSystem hintSystem, VraagStrategie strategie, Player player, String naam) {
        this.hintSystem = hintSystem;
        this.vraagStrategie = strategie;
        this.player = player;
        this.naam = naam;
    }

    public void enter() {
        commands.CommandHandler.setCurrentRoom(this);
        player.nextRoom();
        printKamerInfo();
        spawnMonster();
        voegVoorwerpenToe();
        registreerObservers();
        stelVraag();
    }

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
            }

            juist = vraagStrategie.controleerAntwoord(antwoord);
            notifyObservers(juist);

            if (isAfgerond()) break;

            if (!juist && pogingen == 0 && monster != null && !monster.isVerslagen()) {
                if (!monsterGeactiveerd) {
                    monster.valAan(player);
                    monsterGeactiveerd = true;
                }
                hintSystem.vraagEnVerwerkHint(scanner);
            }
            pogingen++;
        }
    }

    public void gebruikHintJoker() {
        if (!hintJokerGebruikt) {
            System.out.println("🎁 Joker gebruikt! Je krijgt deze hint:");
            System.out.println("Hint: " + hintSystem.geefHint());
            hintJokerGebruikt = true;
        } else {
            System.out.println("❌ Hint Joker is in deze kamer al gebruikt.");
        }
    }

    public Object getObject(String key) { return voorwerpen.get(key); }
    public void voegVoorwerpToe(String k, Object v) { voorwerpen.put(k.toLowerCase(), v); }
    public Monster getMonster() { return monster; }
    public void setMonster(Monster m) { this.monster = m; }
    public Door getDeur() { return deur; }
    public boolean isAfgerond() { return afgerond; }
    public void markAfgerond() { afgerond = true; }

    public void registreerObservers() {
        addObserver(deur);
        addObserver(new StatusDisplay());
        if (monster != null) addObserver(monster);
    }

    public void deregistreerMonster() {
        if (monster != null) {
            removeObserver(monster);
            monster = null;
        }
    }

    @Override public void addObserver(AnswerObserver o) { observers.add(o); }
    @Override public void removeObserver(AnswerObserver o) { observers.remove(o); }
    @Override public void notifyObservers(boolean correct) {
        for (AnswerObserver o : observers) o.update(correct);
        if (correct) afgerond = true;
    }

    @Override public void vraagOmHint() {
        System.out.println("Deze kamer heeft geen extra hint.");
    }

    protected void spawnMonsterIfChance(Monster kandidaat, double kans) {
        if (Math.random() < kans) {
            this.monster = kandidaat;
            System.out.println("👹 Een " + kandidaat.getNaam() + " sluipt rond in deze kamer!");
        }
    }

    protected abstract void printKamerInfo();
    protected abstract void spawnMonster();
    protected abstract void voegVoorwerpenToe();
}
