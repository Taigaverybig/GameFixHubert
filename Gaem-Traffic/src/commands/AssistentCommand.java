package commands;

import hints.*;


public class AssistentCommand {
    private final HintSystem hintSystem;

    public AssistentCommand(HintSystem hintSystem) {
        this.hintSystem = hintSystem;
    }

    public void activeer() {
        System.out.println("💡 Hint: " + hintSystem.geefHint());
        System.out.println(" educatief hulpmiddel: kijk de theorie op brightspace");
        System.out.println(" motiverende boodschap: Ga zo door! Je bent goed bezig");}
}