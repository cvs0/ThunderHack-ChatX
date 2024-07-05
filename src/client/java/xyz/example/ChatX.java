package xyz.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thunder.hack.api.IAddon;
import thunder.hack.cmd.Command;
import thunder.hack.gui.hud.HudElement;
import thunder.hack.modules.Module;
import xyz.example.modules.AutoReply;
import xyz.example.modules.ChatLogger;
import xyz.example.modules.ChatModerator;
import xyz.example.modules.UserMentionAlert;

import java.util.Arrays;
import java.util.List;

public class ChatX implements IAddon {
    public static final Logger LOGGER = LoggerFactory.getLogger("chatx");

    @Override
    public void onInitialize() {
        LOGGER.info("Initialized ChatX");
    }

    @Override
    public List<Module> getModules() {
        return Arrays.asList(new AutoReply(), new ChatLogger(), new UserMentionAlert(), new ChatModerator());
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }

    @Override
    public List<HudElement> getHudElements() {
        return null;
    }

    @Override
    public String getPackage() {
        return "xyz.example";
    }

    @Override
    public String getName() {
        return "ChatX";
    }

    @Override
    public String getAuthor() {
        return "cvs0";
    }

    @Override
    public String getRepo() {
        return "https://github.com/cvs0/ThunderHack-ChatX";
    }
}