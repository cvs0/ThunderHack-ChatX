package xyz.cvs0.modules;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.text.Text;
import thunder.hack.events.impl.PacketEvent;
import thunder.hack.modules.Module;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatLogger extends Module {
    private final String logFilePath;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor(); // ExecutorService for logging on a separate thread

    public ChatLogger() {
        super("ChatLogger", Category.getCategory("ChatX"));
        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
        logFilePath = Paths.get(desktopPath, "chatlog.txt").toString();
        ensureLogFileExists();
    }

    @EventHandler
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof ChatMessageS2CPacket) {
            ChatMessageS2CPacket chatPacket = (ChatMessageS2CPacket) event.getPacket();
            Text messageContent = chatPacket.unsignedContent();
            if (messageContent == null) {
                messageContent = Text.of(chatPacket.body().content());
            }

            logChatMessage(messageContent.getString());
        }
    }

    private void ensureLogFileExists() {
        executorService.submit(() -> {
            try {
                File logFile = new File(logFilePath);
                if (!logFile.exists()) {
                    logFile.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void logChatMessage(String message) {
        executorService.submit(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                writer.write(timestamp + " - " + message + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onDisable() {
        super.onDisable();
        executorService.shutdown();
    }
}