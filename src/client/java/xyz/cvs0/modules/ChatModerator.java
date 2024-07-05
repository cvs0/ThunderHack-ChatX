package xyz.cvs0.modules;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.text.Text;
import thunder.hack.events.impl.PacketEvent;
import thunder.hack.modules.Module;
import thunder.hack.setting.Setting;

public class ChatModerator extends Module {
    private final Setting<String> blacklistedWords = new Setting<>("Blacklisted Words", "example1,example2");

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

            String[] words = blacklistedWords.getValue().split(",");
            for (String word : words) {
                if (messageContent.getString().contains(word.trim())) {
                    if (!event.isCancelled()) {
                        event.cancel();
                    }
                    break;
                }
            }
        }
    }
}