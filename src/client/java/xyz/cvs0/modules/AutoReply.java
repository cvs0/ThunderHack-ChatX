package xyz.cvs0.modules;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.text.Text;
import thunder.hack.events.impl.PacketEvent;
import thunder.hack.modules.Module;
import thunder.hack.setting.Setting;

public class AutoReply extends Module {
    private final Setting<String> triggerMessage = new Setting<>("Trigger Message", "ThunderHack");
    private final Setting<String> replyMessage = new Setting<>("Reply Message", "On Top");
    private final Setting<Boolean> exactMatch = new Setting<>("Exact Match", false); // New setting for exact match

    public AutoReply() {
        super("AutoReply", Category.getCategory("ChatX"));
    }

    @EventHandler
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof ChatMessageS2CPacket) {
            ChatMessageS2CPacket chatPacket = (ChatMessageS2CPacket) event.getPacket();
            Text messageContent = chatPacket.unsignedContent();
            if (messageContent == null) {
                messageContent = Text.of(chatPacket.body().content());
            }

            boolean shouldReply = exactMatch.getValue() ? messageContent.getString().equals(triggerMessage.getValue()) : messageContent.getString().contains(triggerMessage.getValue());

            if (shouldReply) {
                mc.getNetworkHandler().sendChatMessage(replyMessage.getValue());
            }
        }
    }
}