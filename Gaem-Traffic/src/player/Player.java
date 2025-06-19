package player;

import Joker.*;

public class Player {
    private final int playerId;
    private int hp = 100;
    private int currentRoom = 0;
    private int totaalKamers = 0;
    private int score = 0;

    public boolean isAlive() { return hp > 0; }

    private JokerContext jokerContext = new JokerContext(new HintJoker());

    public Player(int playerId) {
        this.playerId = playerId;
    }

    public JokerContext getJokerContext() {
        return jokerContext;
    }

    public void setTotaalKamers(int totaal) {
        this.totaalKamers = totaal;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void increaseHP(int amount) {
        this.hp = Math.min(100, this.hp + amount);
    }

    public void decreaseHP(int amount) {
        this.hp = Math.max(0, this.hp - amount);
    }

    public int getHP() {
        return hp;
    }

    public int getCurrentRoom() {
        return currentRoom;
    }

    public void nextRoom() {
        this.currentRoom++;
    }

    public void setCurrentRoom(int index) {
        this.currentRoom = index;
    }

    public void addScore(int amount) {
        score += amount;
    }

    public int getScore() {
        return score;
    }

    public String getStatus() {
        String kamerTekst = totaalKamers > 0
                ? "Kamer: " + (currentRoom + 1) + " / " + totaalKamers
                : "Kamer index: " + currentRoom;

        return "📊 STATUS - Speler #" + playerId + "\n" +
                "HP: " + hp + "\n" +
                "Score: " + score + "\n" +
                kamerTekst;
    }

    @Override
    public String toString() {
        return "Player{id=" + playerId + ", hp=" + hp + ", currentRoom=" + currentRoom + ", totaal=" + totaalKamers + "}";
    }

    public void schade(int i) {
        // Implementatie later indien nodig
    }
}
