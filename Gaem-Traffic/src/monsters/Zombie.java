package monsters;

import player.Player;

public class Zombie implements Monster {
    private boolean verslagen = false;
    @Override
    public boolean isVerslagen() { return verslagen; }
    @Override
    public void markVerslagen() { verslagen = true; }

    @Override
    public String getNaam() {
        return "Zombie";
    }

    @Override
    public int getSchade() {
        return 30;
    }

    @Override
    public void valAan(Player speler) {
        if (verslagen) return;              // ← valt nooit meer aan
        speler.decreaseHP(getSchade());
        System.out.println("💥 De Zombie bijt! Je verliest " + getSchade() + " HP.");
    }

    @Override
    public void verwijder() {
        verslagen = true;
        System.out.println("💨 De Zombie verdwijnt in het niets.");
    }

    @Override
    public void update(boolean correct) {
        if (!correct) {
            System.out.println("💢 De Zombie wordt woedend en valt aan!");
            // Roep hier `valAan(...)` aan met een geldige Player (zie toelichting hieronder)
        } else {
            System.out.println("😴 De Zombie blijft stil.");
        }
    }
}