package ru.matveylegenda.tidiscordreports.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import ru.matveylegenda.tidiscordreports.TiDiscordReports;
import ru.matveylegenda.tidiscordreports.utils.configs.MessagesConfig;

public class ModalListener extends ListenerAdapter {
    private TiDiscordReports plugin = TiDiscordReports.getInstance();

    private MessagesConfig messagesConfig = plugin.messagesConfig;

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (event.isAcknowledged()) {
            return;
        }

        String modalId = event.getModalId();
        User user = event.getUser();

        if (modalId.contains("report-ban-")) {
            String reported = modalId.replace("report-ban-", "");

            String time = event.getValue("report-time").getAsString();
            String reason = event.getValue("report-reason").getAsString();

            String command = messagesConfig.discord.reportMessage.buttons.ban.command
                    .replace("{reported}", reported)
                    .replace("{time}", time)
                    .replace("{reason}", reason);

            Bukkit.getScheduler().runTask(plugin, () -> {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
            });

            Message message = event.getMessage();

            MessageEmbed messageEmbed = message.getEmbeds().get(0);

            String updatedDescription = messageEmbed.getDescription() + messagesConfig.discord.reportMessage.decision.accepted;
            EmbedBuilder embedBuilder = new EmbedBuilder(messageEmbed)
                    .setDescription(updatedDescription)
                    .setFooter(user.getEffectiveName(), user.getAvatarUrl());

            event.editMessage("")
                    .setEmbeds(embedBuilder.build())
                    .setComponents()
                    .queue();
        }

        if (modalId.contains("report-mute-")) {
            String reported = modalId.replace("report-mute-", "");

            String time = event.getValue("report-time").getAsString();
            String reason = event.getValue("report-reason").getAsString();

            String command = messagesConfig.discord.reportMessage.buttons.mute.command
                    .replace("{reported}", reported)
                    .replace("{time}", time)
                    .replace("{reason}", reason);

            Bukkit.getScheduler().runTask(plugin, () -> {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
            });

            Message message = event.getMessage();

            MessageEmbed messageEmbed = message.getEmbeds().get(0);

            String updatedDescription = messageEmbed.getDescription() + messagesConfig.discord.reportMessage.decision.accepted;
            EmbedBuilder embedBuilder = new EmbedBuilder(messageEmbed)
                    .setDescription(updatedDescription)
                    .setFooter(user.getEffectiveName(), user.getAvatarUrl());

            event.editMessage("")
                    .setEmbeds(embedBuilder.build())
                    .setComponents()
                    .queue();
        }
    }
}
