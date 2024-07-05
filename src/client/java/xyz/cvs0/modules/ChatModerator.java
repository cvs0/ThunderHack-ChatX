package xyz.cvs0.modules;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.text.Text;
import thunder.hack.events.impl.PacketEvent;
import thunder.hack.modules.Module;
import thunder.hack.setting.Setting;

import java.util.List;

public class ChatModerator extends Module {
    private final Setting<List<String>> blacklistedWords = new Setting<>("Blacklisted Words", List.of("example1", "example2"));

    public ChatModerator() {
        super("ChatModerator", Category.getCategory("ChatX"));
    }

    @EventHandler
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof ChatMessageS2CPacket) {
            ChatMessageS2CPacket chatPacket = (ChatMessageS2CPacket) event.getPacket();
            Text messageContent = chatPacket.unsignedContent();
            if (messageContent == null) {
                messageContent = Text.of(chatPacket.body().content());
            }

            for (String word : blacklistedWords.getValue()) {
                if (messageContent.getString().contains(word)) {
                    if (!event.isCancelled()) {
                        event.cancel();
                    }
                    break;
                }
            }
        }
    }
}