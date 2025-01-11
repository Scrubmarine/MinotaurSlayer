import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Logger;

@ScriptManifest(name = "Minotaur Slayer", description = "Kills Minotaurs in the Stronghold of Security", author = "ScrubTheBot",
        version = 1.0, category = Category.COMBAT, image = "")
public class MinotaurScript extends AbstractScript {

    @Override
    public int onLoop() {
        return 0;
    }

}