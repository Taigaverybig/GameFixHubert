package items;

import door.Door;

public class Sleutel implements UsableOnDoor {
    private final String sleutelNaam;

    public Sleutel(String sleutelNaam) {
        this.sleutelNaam = sleutelNaam;
    }

    @Override
    public void useOnDoor(Door deur) {
        if (deur == null) {
            System.out.println("❌ Er is hier geen deur.");
            return;
        }

        System.out.println("🔑 Je probeert de sleutel \"" + sleutelNaam + "\"…");

        /* ------- 50 % kans ------- */
        if (Math.random() < 0.5) {          // <‑‑ hier stel je het percentage in
            deur.setOpen(true);
            System.out.println("✅ Gelukt! De deur klikt open.");
        } else {
            System.out.println("❌ De sleutel past niet… Probeer iets anders!");
        }
    }
}
