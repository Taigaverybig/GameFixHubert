
package items;

// package items;
public class Kamerinfo implements MessageDisplayable {
    private final String message;

    public Kamerinfo(String message) {
        this.message = message;
    }

    @Override
    public void showMessage() {
        System.out.println("📖 Kamerinfo: " + message);
    }
}
