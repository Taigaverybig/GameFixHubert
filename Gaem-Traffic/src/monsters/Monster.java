package monsters;

import door.AnswerObserver;

public interface Monster extends AnswerObserver {
    String getNaam();

    int getSchade();

    void valAan(player.Player player);
    void verwijder();
    void update(boolean correct);

    boolean isVerslagen();
    void markVerslagen();
}