package lol.deyo.chatmod;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;


import lol.deyo.chatmod.commands.ReloadCommand;
import lol.deyo.chatmod.events.MessageEvent;

import javax.annotation.Nonnull;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class main extends JavaPlugin {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private static main instance;
    private final Set<String> bannedWords = new HashSet<>();
    private String warningMessage = "Hey, you cannot say that!";
    private Color warningColor = Color.RED;
    private boolean censorWords = false;

    public main(JavaPluginInit init) {
        super(init);
    }

    public static main get() {
        return instance;
    }

    @Override
    protected void setup() {
        instance = this;
        loadConfig();
        this.getCommandRegistry().registerCommand(new ReloadCommand(this));

        try {
            this.getEventRegistry().registerGlobal(PlayerChatEvent.class, MessageEvent::onPlayerChat);
            LOGGER.atInfo().log("Chat moderation event registered successfully");
        } catch (Exception e) {
            LOGGER.atInfo().log("Note: Event registration skipped - will try at runtime. %s", e.getMessage());
        }

        LOGGER.atInfo().log("Chat moderation plugin enabled with %d banned words", bannedWords.size());
    }

    @Override
    protected void shutdown() {
        LOGGER.atInfo().log("Chat moderation plugin disabled");
    }

    public void reloadConfig() {
        LOGGER.atInfo().log("Reloading configuration...");
        loadConfig();
        LOGGER.atInfo().log("Configuration reloaded with %d banned words", bannedWords.size());
    }

    public Set<String> getBannedWords() {
        return bannedWords;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public Color getWarningColor() {
        return warningColor;
    }

    public boolean isCensorWords() {
        return censorWords;
    }

    private void loadConfig() {
        bannedWords.clear();
        warningMessage = "Hey, you cannot say that!";
        warningColor = Color.RED;
        censorWords = false;
        Path dataDir = getDataDirectory();
        File configFile = dataDir.resolve("words.yml").toFile();

        if (!configFile.exists()) {
            loadDefaultConfigFromResources(configFile);
        }

        try {
            List<String> lines = Files.readAllLines(configFile.toPath());
            boolean inBannedWords = false;
            for (String line : lines) {
                String trimmed = line.trim();
                if (trimmed.startsWith("message:")) {
                    warningMessage = trimmed.substring("message:".length()).trim();
                } else if (trimmed.startsWith("color:")) {
                    String colorName = trimmed.substring("color:".length()).trim();
                    warningColor = parseColor(colorName);
                } else if (trimmed.startsWith("censorwords:")) {
                    String value = trimmed.substring("censorwords:".length()).trim();
                    censorWords = Boolean.parseBoolean(value);
                } else if (trimmed.startsWith("banned_words:")) {
                    inBannedWords = true;
                } else if (inBannedWords && trimmed.startsWith("- ")) {
                    String word = trimmed.substring(2).trim().toLowerCase();
                    if (!word.isEmpty()) {
                        bannedWords.add(word);
                    }
                } else if (inBannedWords && !trimmed.startsWith("- ") && !trimmed.isEmpty()) {
                    inBannedWords = false;
                }
            }
        } catch (IOException e) {
            LOGGER.atInfo().log("Failed to load words.yml: %s", e.getMessage());
        }
    }

    private Color parseColor(String colorName) {
        try {
            switch (colorName.toUpperCase()) {
                case "RED":
                    return Color.RED;
                case "GREEN":
                    return Color.GREEN;
                case "BLUE":
                    return Color.BLUE;
                case "YELLOW":
                    return Color.YELLOW;
                case "WHITE":
                    return Color.WHITE;
                case "BLACK":
                    return Color.BLACK;
                case "ORANGE":
                    return Color.ORANGE;
                case "GRAY":
                case "GREY":
                    return Color.GRAY;
                default:
                    return Color.RED;
            }
        } catch (Exception e) {
            return Color.RED;
        }
    }

    private void loadDefaultConfigFromResources(File configFile) {
        try {
            configFile.getParentFile().mkdirs();
            java.io.InputStream is = getClass().getClassLoader().getResourceAsStream("words.yml");
            if (is != null) {
                Files.copy(is, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                LOGGER.atInfo().log("Loaded default words.yml from resources");
            } else {
                LOGGER.atInfo().log("Could not find words.yml in resources, creating fallback config");
                createFallbackConfig(configFile);
            }
        } catch (IOException e) {
            LOGGER.atInfo().log("Failed to load default words.yml: %s", e.getMessage());
            createFallbackConfig(configFile);
        }
    }

    private void createFallbackConfig(File configFile) {
        try {
            List<String> defaultWords = List.of(
                "# Chat Filter Configuration",
                "# Available colors: RED, GREEN, BLUE, YELLOW, WHITE, BLACK, ORANGE, GRAY",
                "message: Hey, you cannot say that!",
                "color: RED",
                "censorwords: false",
                "",
                "# If censorwords is true, banned words will be replaced with *****",
                "# If false, the message will be blocked entirely",
                "",
                "# Add banned words below (one per line)",
                "# Lines starting with # are comments and will be ignored",
                "",
                "# Common banned words",
                "banned_words:",
                "  - fuck",
                "  - shit",
                "  - ass",
                "  - bitch",
                "  - damn",
                "  - hell",
                "  - crap",
                "  - dick",
                "  - piss",
                "  - bastard",
                "  - cunt",
                "  - whore",
                "  - slut",
                "  - fag",
                "  - nigger",
                "  - nigga",
                "  - motherfucker",
                "  - son_of_a_bitch",
                "  - bullshit",
                "  - asshole",
                "  - cock",
                "  - pussy",
                "  - tits",
                "  - twat",
                "  - wanker",
                "  - bugger",
                "  - bloody",
                "  - arse",
                "  - bollocks",
                "  - shag",
                "  - snog",
                "  - git",
                "  - pillock",
                "  - prick",
                "  - knob",
                "  - minger",
                "  - chav",
                "  - pikey",
                "  - gypo",
                "  - spastic",
                "  - mong",
                "  - retard",
                "  - idiot",
                "  - moron",
                "  - imbecile",
                "  - cretin",
                "  - dumbass",
                "  - jackass",
                "  - motherfucking",
                "  - fucking",
                "  - shitting",
                "  - pissed",
                "  - pissing",
                "  - crappy",
                "  - shitty",
                "  - cock_sucker",
                "  - dick_head",
                "  - piss_off",
                "  - fuck_off",
                "  - shit_head",
                "  - ass_hole",
                "  - bitch_ass",
                "  - cunt_face",
                "  - whore_bag",
                "  - slut_bucket",
                "  - fag_got",
                "  - nig_ger",
                "  - nig_ga"
            );
            Files.write(configFile.toPath(), defaultWords);
            LOGGER.atInfo().log("Created fallback words.yml");
        } catch (IOException e) {
            LOGGER.atInfo().log("Failed to create fallback words.yml: %s", e.getMessage());
        }
    }
}
