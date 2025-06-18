package monsters;

import player.Player;

public class ChickenJockey implements Monster {
    private boolean verslagen = false;
    @Override
    public boolean isVerslagen() { return verslagen; }
    @Override
    public void markVerslagen() { verslagen = true; }

    @Override
    public String getNaam() {
        return "Chicken Jockey";
    }

    @Override
    public int getSchade() {
        return 25;
    }

    @Override
    public void valAan(Player speler) {
        if (verslagen) return;              // ← valt nooit meer aan
        speler.decreaseHP(getSchade());
        System.out.println("💥 De chicken jockey valt aan! Je verliest " + getSchade() + " HP.");
    }

    @Override
    public void verwijder() {
        verslagen = true;
        System.out.println("💨 De Chicken Jockey verdwijnt in het niets.");
    }

    @Override
    public void update(boolean correct) {
        if (!correct) {
            System.out.println("🐔💢 De Chicken Jockey wordt woedend en valt aan!");
            // Roep hier `valAan(...)` aan met een geldige Player (zie toelichting hieronder)
        } else {
            System.out.println("🐔😴 De Chicken Jockey blijft stil.");
        }
    }
}