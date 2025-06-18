package hints;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class HintSystem {
    private List<HintProvider> hintProviders;
    private Random random = new Random();

    public HintSystem(List<HintProvider> hintProviders) {
        this.hintProviders = hintProviders;
    }

    public String geefHint() {
        HintProvider gekozenProvider = hintProviders.get(random.nextInt(hintProviders.size()));
        return gekozenProvider.geefHint();
    }

    public void toonHintAlsGewenst(String antwoord) {
        if (antwoord.trim().equalsIgnoreCase("ja")) {
            HintProvider gekozenProvider = hintProviders.get(random.nextInt(hintProviders.size()));
            System.out.println("Hint: " + gekozenProvider.geefHint());
        } else {
            System.out.println("Geen hint. Succes verder!");
        }
    }

    // 🆕 Shotgun Surgery fix
    public void vraagEnVerwerkHint(Scanner scanner) {
        System.out.println("Wil je een hint? (ja/nee)");
        while (true) {
            String antwoord = scanner.nextLine().trim();
            if (!commands.CommandHandler.verwerk(antwoord)) {
                toonHintAlsGewenst(antwoord);
                break;
            }
        }
    }
}
