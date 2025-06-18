package items;

import door.Door;

public class Sleutel implements UsableOnDoor {
    private final String sleutelNaam;

    public Sleutel(String sleutelNaam) {
        this.sleutelNaam = sleutelNaam;
    }

    @Override
    public void useOnDoor(Door deur) {
        if (deur != null) {
            System.out.println("🔑 Je gebruikt de sleutel: " + sleutelNaam);
            deur.setOpen(true);  // Veilig via setter
            System.out.println("🚪 De deur wordt geopend!");
        }
    }
}
