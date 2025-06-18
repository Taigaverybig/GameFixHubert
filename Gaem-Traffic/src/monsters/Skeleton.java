package monsters;

import player.Player;

public class Skeleton implements Monster {

    private boolean verslagen = false;
    @Override
    public boolean isVerslagen() { return verslagen; }
    @Override
    public void markVerslagen() { verslagen = true; }
    @Override
    public String getNaam() {
        return "Skeleton";
    }

    @Override
    public int getSchade() {
        return 15;
    }

    @Override
    public void valAan(Player speler) {
        if (verslagen) return;              // ← valt nooit meer aan
        speler.decreaseHP(getSchade());
        System.out.println("💀 De skeleton valt aan!");
    }

    @Override
    public void verwijder() {
        verslagen = true;
        System.out.println("💀 De skeleton verdwijnt in het niets.");
    }
    @Override
    public void update(boolean correct) {
        if (!correct) {
            System.out.println("💢 De Skeleton wordt woedend en valt aan!");
            // Roep hier `valAan(...)` aan met een geldige Player (zie toelichting hieronder)
        } else {
            System.out.println("😴 Skeleton Jockey blijft stil.");
        }
    }
}