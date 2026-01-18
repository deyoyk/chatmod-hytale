package lol.deyo.chatmod.commands;

import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;

import lol.deyo.chatmod.main;

import javax.annotation.Nonnull;

public class ReloadCommand extends CommandBase {
    private final main plugin;

    public ReloadCommand(main plugin) {
        super("reloadchat", "Reloads the chat moderation configuration");
        this.setPermissionGroup(GameMode.Adventure);
        this.plugin = plugin;
    }

    @Override
    protected void executeSync(@Nonnull CommandContext ctx) {
        plugin.reloadConfig();
        ctx.sendMessage(Message.raw("Chat moderation configuration reloaded! Banned words: " + plugin.getBannedWords().size()));
    }
}
