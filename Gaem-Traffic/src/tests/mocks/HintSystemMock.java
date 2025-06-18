package tests.mocks;

import hints.HintSystem;

public class HintSystemMock extends HintSystem {
    public boolean hintGegeven = false;

    public HintSystemMock() {
        super(new java.util.ArrayList<>());
    }

    @Override
    public void toonHintAlsGewenst(String antwoord) {
        hintGegeven = true;
        System.out.println("📢 [MockHint] Hint werd getoond");
    }
}