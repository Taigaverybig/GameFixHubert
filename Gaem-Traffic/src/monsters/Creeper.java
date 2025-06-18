
package monsters;

import player.Player;

public class Creeper implements Monster {
    private boolean verslagen = false;
    @Override
    public boolean isVerslagen() { return verslagen; }
    @Override
    public void markVerslagen() { verslagen = true; }
    @Override
    public String getNaam() {
        return "Creeper";
    }

    @Override
    public int getSchade() {
        return 20;
    }

    @Override
    public void valAan(Player speler) {
        if (verslagen) return;              // ← valt nooit meer aan
        speler.decreaseHP(getSchade());
        System.out.println("💥 De creeper explodeert! Je verliest " + getSchade() + " HP.");
    }

    @Override
    public void verwijder() {
        verslagen = true;
        System.out.println("💨 De Creeper verdwijnt in het niets.");
    }

    @Override
    public void update(boolean correct) {
        if (!correct) {
            System.out.println("💢 De Creeper wordt woedend en valt aan!");
            // Roep hier `valAan(...)` aan met een geldige Player (zie toelichting hieronder)
        } else {
            System.out.println("😴 De Creeper blijft AFK");
        }
    }
}
