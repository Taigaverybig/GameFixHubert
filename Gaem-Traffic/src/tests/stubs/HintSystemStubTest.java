package tests.stubs;

import hints.HintProvider;
import hints.HintSystem;

import java.util.List;

public class HintSystemStubTest {
    public static void main(String[] args) {
        HintProvider stubHint = () -> "Dit is een stubhint!";
        HintSystem hintSystem = new HintSystem(List.of(stubHint));

        System.out.println("Simulatie: gebruiker vraagt om hint...");
        hintSystem.toonHintAlsGewenst("ja");
    }
}
