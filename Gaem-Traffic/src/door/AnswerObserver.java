package door;

public interface AnswerObserver {
    void update(boolean correct); // true = juist, false = fout
}
