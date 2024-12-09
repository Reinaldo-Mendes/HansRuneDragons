package behaviour.sendDiscordMessage;

import framework.Branch;
import main.Main;

public class SendDiscordMessageBranch extends Branch {
    @Override
    public boolean isValid() {
        return Main.discordTimer.finished();
    }
}
