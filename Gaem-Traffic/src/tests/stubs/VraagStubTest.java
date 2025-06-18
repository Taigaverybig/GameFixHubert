package tests.stubs;

import vraag.VraagStrategie;

public class VraagStubTest {
    public static void main(String[] args) {
        VraagStrategie stubVraag = new VraagStrategie() {
            @Override
            public void stelVraag() {
                System.out.println("[StubVraag] Wat is 2 + 2?");
            }

            @Override
            public boolean controleerAntwoord(String antwoord) {
                return antwoord.equals("4");
            }
        };

        stubVraag.stelVraag();
        boolean juist = stubVraag.controleerAntwoord("4");

        if (juist) {
            System.out.println("✅ Antwoord werd juist herkend.");
        } else {
            System.out.println("❌ Antwoord werd foutief afgekeurd.");
        }
    }
}