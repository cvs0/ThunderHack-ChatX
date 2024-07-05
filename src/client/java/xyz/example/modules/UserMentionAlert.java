package xyz.example.modules;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.text.Text;
import thunder.hack.ThunderHack;
import thunder.hack.events.impl.PacketEvent;
import thunder.hack.gui.notification.Notification;
import thunder.hack.modules.Module;
import thunder.hack.setting.Setting;

public class UserMentionAlert extends Module {
    private final Setting<String> usernameToWatch = new Setting<>("Username", "cvs0");

    public UserMentionAlert() {
        super("User Mention Alert", Category.getCategory("ChatX"));
    }

    @EventHandler
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof ChatMessageS2CPacket) {
            ChatMessageS2CPacket chatPacket = (ChatMessageS2CPacket) event.getPacket();
            Text messageContent = chatPacket.unsignedContent();
            if (messageContent == null) {
                messageContent = Text.of(chatPacket.body().content());
            }

            if (messageContent.getString().contains(usernameToWatch.getValue())) {
                ThunderHack.notificationManager.publicity(usernameToWatch.getValue() + " has been mentioned!", "This specific user has been mentioned in the chat.", 3, Notification.Type.INFO);
            }
        }
    }
}