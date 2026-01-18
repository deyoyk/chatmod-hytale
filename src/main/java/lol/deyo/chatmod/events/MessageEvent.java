package lol.deyo.chatmod.events;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.component.Ref;
import lol.deyo.chatmod.main;

import java.awt.*;

public class MessageEvent {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public static void onPlayerChat(PlayerChatEvent event) {
        try {
            main plugin = main.get();


            PlayerRef sender = event.getSender();
            String message = event.getContent();

            if (plugin.isCensorWords()) {
                String censoredMessage = message;
                for (String bannedWord : plugin.getBannedWords()) {
                    if (censoredMessage.toLowerCase().contains(bannedWord.toLowerCase())) {
                        String stars = "*".repeat(bannedWord.length());
                        censoredMessage = censoredMessage.replaceAll("(?i)" + java.util.regex.Pattern.quote(bannedWord), stars);
                    }
                }
                if (!censoredMessage.equals(message)) {
                    event.setContent(censoredMessage);
                }
            } else {
                for (String bannedWord : plugin.getBannedWords()) {
                    if (message.toLowerCase().contains(bannedWord.toLowerCase())) {
                        event.setCancelled(true);

                        sender.sendMessage(Message.raw(plugin.getWarningMessage()).color(plugin.getWarningColor()));

                        return;
                    }


                }
            }
        } catch (Exception e) {
            LOGGER.atInfo().log("Error checking message: %s", e.getMessage());
        }
    }
}
