package Joker;

import rooms.Room;

public class HintJoker implements Joker {
    @Override
    public void useIn(Room room) {
        room.gebruikHintJoker(); // Kamer beslist of het al gebruikt is
    }
}
