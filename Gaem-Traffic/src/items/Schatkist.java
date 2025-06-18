package items;

// items package
import player.Player;

public class Schatkist implements Rewardable {
    private final int hpBonus;
    private final int scoreBonus;

    public Schatkist(int hpBonus, int scoreBonus) {
        this.hpBonus = hpBonus;
        this.scoreBonus = scoreBonus;
    }

    @Override
    public void grantReward(Player speler) {
        speler.increaseHP(hpBonus);
        speler.addScore(scoreBonus);
        System.out.println("🎁 Je opent de schatkist!");
        System.out.println("❤️ +" + hpBonus + " HP, 🏆 +" + scoreBonus + " punten.");
    }
}