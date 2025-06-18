package tests.mocks;

import monsters.Monster;
import player.Player;

public class MonsterMock implements Monster {
    public boolean aangevallen = false;

    @Override
    public String getNaam() {
        return "MockMonster";
    }

    @Override
    public int getSchade() {
        return 10;
    }

    @Override
    public void valAan(Player speler) {
        aangevallen = true;
        System.out.println("💢 [Mock] Monster viel aan");
    }

    @Override
    public void verwijder() {}

    @Override
    public void update(boolean correct) {
        if (!correct) {
            valAan(null); // in echte test geef je speler mee
        }
    }

    @Override
    public boolean isVerslagen() {
        return false;
    }

    @Override
    public void markVerslagen() {}
}