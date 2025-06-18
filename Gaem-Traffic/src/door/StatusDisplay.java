package door;

public class StatusDisplay implements AnswerObserver {
    @Override
    public void update(boolean correct) {
        if (correct) {
            System.out.println("✅ Goed antwoord! Je score is verhoogd.");
        } else {
            System.out.println("❌ Fout antwoord! Pas op voor gevaar...");
        }
    }
}
