package vraag;

import java.util.List;

public class MeerkeuzeVraag implements VraagStrategie {
    private final String vraag;
    private final List<String> opties;
    private final String juistAntwoord;

    public MeerkeuzeVraag(String vraag, List<String> opties, String juistAntwoord) {
        this.vraag = vraag;
        this.opties = opties;
        this.juistAntwoord = juistAntwoord;
    }

    @Override
    public void stelVraag() {
        System.out.println("Meerkeuzevraag: " + vraag);
        for (int i = 0; i < opties.size(); i++) {
            System.out.println((i + 1) + ". " + opties.get(i));
        }
    }

    @Override
    public boolean controleerAntwoord(String antwoord) {
        // Check of het nummer is en overeenkomt met de juiste tekst
        try {
            int keuze = Integer.parseInt(antwoord);
            if (keuze >= 1 && keuze <= opties.size()) {
                return opties.get(keuze - 1).equalsIgnoreCase(juistAntwoord);
            }
        } catch (NumberFormatException ignored) {
            // Niet numeriek? Dan vergelijken we direct met de tekst
        }

        return antwoord.equalsIgnoreCase(juistAntwoord);
    }



}
