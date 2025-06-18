package tests.mocks;

import player.Player;
import rooms.NormalRoom;

public class HintVraagTest {
    public static void main(String[] args) {
        HintSystemMock mockHint = new HintSystemMock();
        Player speler = new Player(1);
        VraagAltijdFout strategie = new VraagAltijdFout();
        NormalRoom kamer = new NormalRoom(mockHint, strategie, speler);

        kamer.enter();

        if (mockHint.hintGegeven) {
            System.out.println("✅ Test geslaagd: hint werd opgevraagd bij fout antwoord");
        } else {
            System.out.println("❌ Test gefaald: hint werd niet getoond");
        }
    }
}