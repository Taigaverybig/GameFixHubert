package tests.mocks;

import vraag.VraagStrategie;

public class VraagAltijdFout implements VraagStrategie {
    @Override
    public void stelVraag() {
        System.out.println("Testvraag: Wat is 2 + 2?");
    }

    @Override
    public boolean controleerAntwoord(String antwoord) {
        return false;  // altijd fout
    }
}