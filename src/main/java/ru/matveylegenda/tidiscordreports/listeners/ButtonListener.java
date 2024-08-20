package ru.matveylegenda.tidiscordreports.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import ru.matveylegenda.tidiscordreports.TiDiscordReports;
import ru.matveylegenda.tidiscordreports.utils.configs.MessagesConfig;

public class ButtonListener extends ListenerAdapter {
    private TiDiscordReports plugin = TiDiscordReports.getInstance();

    private MessagesConfig messagesConfig = plugin.messagesConfig;

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.isAcknowledged()) {
            return;
        }

        String buttonId = event.getComponentId();
        User user = event.getUser();

        if (buttonId.contains("report-ban-")) {
            String reported = buttonId.replace("report-ban-", "");

            TextInput time = TextInput.create("report-time", messagesConfig.discord.reportMessage.modals.time, TextInputStyle.SHORT)
                    .setRequired(true)
                    .build();

            TextInput reason = TextInput.create("report-reason", messagesConfig.discord.reportMessage.modals.reason, TextInputStyle.SHORT)
                    .setRequired(true)
                    .build();

            String modalTitle = messagesConfig.discord.reportMessage.modals.ban.title.replace("{reported}", reported);
            Modal modal = Modal.create(buttonId, modalTitle)
                    .addComponents(
                            ActionRow.of(time),
                            ActionRow.of(reason)
                    )
                    .build();

            event.replyModal(modal)
                    .queue();
        }

        if (buttonId.contains("report-mute-")) {
            String reported = buttonId.replace("report-mute-", "");

            TextInput time = TextInput.create("report-time", messagesConfig.discord.reportMessage.modals.time, TextInputStyle.SHORT)
                    .setRequired(true)
                    .build();

            TextInput reason = TextInput.create("report-reason", messagesConfig.discord.reportMessage.modals.reason, TextInputStyle.SHORT)
                    .setRequired(true)
                    .build();

            String modalTitle = messagesConfig.discord.reportMessage.modals.mute.title.replace("{reported}", reported);
            Modal modal = Modal.create(buttonId, modalTitle)
                    .addComponents(
                            ActionRow.of(time),
                            ActionRow.of(reason)
                    )
                    .build();

            event.replyModal(modal)
                    .queue();
        }

        if (buttonId.contains("report-deny-")) {
            Message message = event.getMessage();

            MessageEmbed messageEmbed = message.getEmbeds().get(0);

            String updatedDescription = messageEmbed.getDescription() + messagesConfig.discord.reportMessage.decision.rejected;
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
