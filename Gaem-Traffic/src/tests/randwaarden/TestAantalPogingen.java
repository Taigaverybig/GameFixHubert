package tests.randwaarden;

public class TestAantalPogingen {

    public static void main(String[] args) {
        // Arrange
        int maxPogingen = 2;

        // Act + Assert
        assert !isGeldigAantalPogingen(-1, maxPogingen) : "-1 mag niet geldig zijn";
        assert isGeldigAantalPogingen(0, maxPogingen)   : "0 moet geldig zijn";
        assert isGeldigAantalPogingen(1, maxPogingen)   : "1 moet geldig zijn";
        assert isGeldigAantalPogingen(2, maxPogingen)   : "2 moet geldig zijn";
        assert !isGeldigAantalPogingen(3, maxPogingen)  : "3 mag niet geldig zijn";

        System.out.println("✅ Alle randwaardetests voor pogingen zijn geslaagd.");
    }

    public static boolean isGeldigAantalPogingen(int pogingen, int max) {
        return pogingen >= 0 && pogingen <= max;
    }
}