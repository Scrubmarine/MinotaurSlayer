import org.dreambot.api.input.Keyboard;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;

@ScriptManifest(name = "Minotaur Slayer", description = "Kills Minotaurs in the Stronghold of Security", author = "ScrubTheBot",
        version = 1.0, category = Category.COMBAT, image = "")
public class MinotaurScript extends AbstractScript {

    Area minotaurArea;
    Area barbArea = new Area(3077, 3424, 3084, 3417);
    Area entranceArea = new Area(1857, 5245, 1862, 5241);
    Area bankArea = new Area(3159, 3490, 3169, 3486);
    State state;

    @Override
    public int onLoop() {
        switch (getState()) {
            case WALKING_TO_BANK:
                if (bankArea.getRandomTile().distance() > 2) {
                    Walking.walk(bankArea.getRandomTile());
                    Sleep.sleep(300,5000);
                }
                break;
            case USE_BANK:
                if (!Bank.isOpen() && bankArea.contains(Players.getLocal())) {
                    GameObject bankBooth = GameObjects.closest(b -> "Grand Exchange booth".equalsIgnoreCase(b.getName()));
                    bankBooth.interactForceLeft("Bank");
                    Sleep.sleep(300, 5000);
                }
                break;
            case BANKING:
                if (Bank.isOpen()) {
                    Bank.depositAllItems();
                    Bank.withdraw("Trout", 10);
                    Bank.withdraw("Strength potion(4)", 2);
                    Keyboard.pressEsc();
                }
                break;
        }
        return 1;
    }

        private State getState() {
            if ((!Inventory.contains("Trout") || Inventory.isFull()) && !bankArea.contains(Players.getLocal().getTile())) {
                return State.WALKING_TO_BANK;
            }
            else if (Inventory.count("Trout") < 1 && Inventory.count("Strength potion(4)") < 1 && bankArea.contains(Players.getLocal().getTile()) && !Bank.isOpen()) {
                return State.USE_BANK;
            }
            else if (Bank.isOpen() && Inventory.count("Trout") < 1 && Inventory.count("Strength potion(4)") < 1) {
                return State.BANKING;
            }
            return state;
    }
}