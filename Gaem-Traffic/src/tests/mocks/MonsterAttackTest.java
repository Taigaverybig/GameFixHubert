package tests.mocks;

import player.Player;
import rooms.NormalRoom;
import hints.HintSystem;

public class MonsterAttackTest {
    public static void main(String[] args) {
        // Mock monster
        MonsterMock mockMonster = new MonsterMock();
        // Mock vraag die altijd fout is
        VraagAltijdFout strategie = new VraagAltijdFout();
        // Dummy hintsysteem
        HintSystem dummyHintSystem = new HintSystem(new java.util.ArrayList<>());
        // Speler met 100 HP
        Player speler = new Player(1);
        NormalRoom kamer = new NormalRoom(dummyHintSystem, strategie, speler);

        kamer.addObserver(mockMonster);  // monster observer
        kamer.enter();

        if (mockMonster.aangevallen) {
            System.out.println("✅ Test geslaagd: monster viel aan bij fout antwoord");
        } else {
            System.out.println("❌ Test gefaald: monster viel niet aan");
        }
    }
}
