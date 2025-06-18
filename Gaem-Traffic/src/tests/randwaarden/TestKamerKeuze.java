package tests.randwaarden;

import java.util.HashMap;
import java.util.Map;

public class TestKamerKeuze {

    public static void main(String[] args) {
        // Arrange
        Map<Integer, String> kamers = new HashMap<>();
        kamers.put(1, "Kamer 1");
        kamers.put(2, "Kamer 2");
        kamers.put(3, "Kamer 3");
        kamers.put(4, "Kamer 4");
        kamers.put(5, "Kamer 5");
        kamers.put(6, "Kamer 6");
        kamers.put(7, "Kamer 7");
        kamers.put(8, "Kamer 8");

        // Act + Assert
        assert !kamers.containsKey(0) : "0 mag niet als geldige kamer gelden";
        assert kamers.containsKey(1)  : "1 moet een geldige kamer zijn";
        assert kamers.containsKey(3)  : "3 moet een geldige kamer zijn";
        assert kamers.containsKey(5)  : "5 moet een geldige kamer zijn";
        assert kamers.containsKey(6)  : "6 moet een geldige kamer zijn";
        assert kamers.containsKey(7)  : "7 moet een geldige kamer zijn";
        assert kamers.containsKey(8)  : "8 moet een geldige kamer zijn";
        assert !kamers.containsKey(9) : "9 mag niet als geldige kamer gelden";

        System.out.println("✅ Alle randwaardetests voor kamerkeuze zijn geslaagd.");
    }
}