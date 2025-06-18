package vraag;

public class OpenVraag implements VraagStrategie {
    private final String vraag;
    private final String juistAntwoord;

    public OpenVraag(String vraag, String juistAntwoord) {
        this.vraag = vraag;
        this.juistAntwoord = juistAntwoord;
    }

    @Override
    public void stelVraag() {
        System.out.println("Vraag: " + vraag);
    }

    @Override
    public boolean controleerAntwoord(String antwoord) {
        return antwoord.equalsIgnoreCase(juistAntwoord);
    }

}
