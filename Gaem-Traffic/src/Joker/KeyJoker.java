package Joker;


import rooms.Room;

public class KeyJoker implements Joker {
    private boolean used = false;

    @Override
    public void useIn(Room room) {
        if (!used && room instanceof KeyJokerRoom) {
            ((KeyJokerRoom) room).geefSleutel();
            used = true;
        } else if (used) {
            System.out.println("KeyJoker is al gebruikt.");
        } else {
            System.out.println("In deze kamer werkt de sleutel van de KeyJoker niet!");
        }
    }
}