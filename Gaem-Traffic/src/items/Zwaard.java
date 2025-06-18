package items;

// package items;
import monsters.Monster;

public class Zwaard implements Attackable {
    private final int damage;

    public Zwaard(int damage) {
        this.damage = damage;
    }

    @Override
    public void attack(Monster target) {
        if (target == null) {
            System.out.println("🗡️ Er is geen monster om aan te vallen.");
            return;
        }
        if (target.isVerslagen()) {
            System.out.println("⚠️ Dit monster is al verslagen.");
            return;
        }
        target.verwijder();
        target.markVerslagen();
        System.out.println("🗡️ Je verslaat de " + target.getNaam() + "!");
    }

}