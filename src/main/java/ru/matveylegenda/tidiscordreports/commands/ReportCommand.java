package ru.matveylegenda.tidiscordreports.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.matveylegenda.tidiscordreports.TiDiscordReports;
import ru.matveylegenda.tidiscordreports.utils.configs.MainConfig;
import ru.matveylegenda.tidiscordreports.utils.configs.MessagesConfig;

import java.awt.*;
import java.util.Arrays;

import static ru.matveylegenda.tidiscordreports.utils.ColorParser.colorize;

public class ReportCommand implements CommandExecutor {
    private TiDiscordReports plugin = TiDiscordReports.getInstance();

    private MainConfig mainConfig = plugin.mainConfig;
    private MessagesConfig messagesConfig = plugin.messagesConfig;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 1 && args[0].equals("reload") && sender.hasPermission("tidiscordreports.reload")) {
            mainConfig.reloadConfig();
            messagesConfig.reloadConfig();

            sender.sendMessage(
                    colorize(messagesConfig.minecraft.reload
                            .replace("{prefix}", messagesConfig.minecraft.prefix))
            );

            return true;
        }

        if (sender instanceof Player) {
            if (args.length < 2) {
                sender.sendMessage(
                        colorize(
                                messagesConfig.minecraft.usage
                                        .replace("{prefix}", messagesConfig.minecraft.prefix)
                        )
                );

                return true;
            }

            Player player = (Player) sender;

            String reportedPlayer = args[0];
            String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

            if (player.getName().equals(reportedPlayer)) {
                player.sendMessage(
                        colorize(
                                messagesConfig.minecraft.reportYourself
                                        .replace("{prefix}", messagesConfig.minecraft.prefix)
                        )
                );

                return true;
            }

            String embedTitle = messagesConfig.discord.reportMessage.embed.title
                    .replace("{reporter}", player.getName())
                    .replace("{reported}", reportedPlayer)
                    .replace("{reason}", reason);

            String embedDescription = messagesConfig.discord.reportMessage.embed.description
                    .replace("{reporter}", player.getName())
                    .replace("{reported}", reportedPlayer)
                    .replace("{reason}", reason);

            Color embedColor = Color.decode("0x" + messagesConfig.discord.reportMessage.embed.color);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle(embedTitle)
                    .setDescription(embedDescription)
                    .setColor(embedColor);

            TextChannel channel = plugin.jda.getTextChannelById(mainConfig.channelId);

            channel.sendMessage(messagesConfig.discord.reportMessage.content)
                    .setEmbeds(embedBuilder.build())
                    .addActionRow(
                            Button.primary("report-ban-" + reportedPlayer, messagesConfig.discord.reportMessage.buttons.ban.text)
                                    .withEmoji(Emoji.fromUnicode(messagesConfig.discord.reportMessage.buttons.ban.emoji)),

                            Button.primary("report-mute-" + reportedPlayer, messagesConfig.discord.reportMessage.buttons.mute.text)
                                    .withEmoji(Emoji.fromUnicode(messagesConfig.discord.reportMessage.buttons.mute.emoji)),

                            Button.secondary("report-deny-" + reportedPlayer, messagesConfig.discord.reportMessage.buttons.deny.text)
                                    .withEmoji(Emoji.fromUnicode(messagesConfig.discord.reportMessage.buttons.deny.emoji))
                    )
                    .queue();

            player.sendMessage(
                    colorize(
                            messagesConfig.minecraft.sendReport
                                    .replace("{prefix}", messagesConfig.minecraft.prefix)
                                    .replace("{reported}", reportedPlayer)
                    )
            );

            return true;
        } else {
            sender.sendMessage("Команду может выполнять только игрок!");
            return true;
        }
    }
}
