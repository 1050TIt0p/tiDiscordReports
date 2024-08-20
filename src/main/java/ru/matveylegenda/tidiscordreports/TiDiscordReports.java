package ru.matveylegenda.tidiscordreports;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.internal.utils.JDALogger;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import ru.matveylegenda.tidiscordreports.commands.ReportCommand;
import ru.matveylegenda.tidiscordreports.listeners.ButtonListener;
import ru.matveylegenda.tidiscordreports.listeners.ModalListener;
import ru.matveylegenda.tidiscordreports.utils.configs.MainConfig;
import ru.matveylegenda.tidiscordreports.utils.configs.MessagesConfig;

import java.io.File;

public final class TiDiscordReports extends JavaPlugin {
    private static TiDiscordReports instance;

    public File mainConfigFile = new File(getDataFolder(), "config.yml");
    public MainConfig mainConfig = new MainConfig();

    public File messagesConfigFile = new File(getDataFolder(), "messages.yml");
    public MessagesConfig messagesConfig  = new MessagesConfig();

    public JDA jda;

    private final ConsoleCommandSender consoleSender = getServer().getConsoleSender();

    @Override
    public void onEnable() {
        instance = this;

        mainConfig.reloadConfig();
        messagesConfig.reloadConfig();

        consoleSender.sendMessage(" ");
        consoleSender.sendMessage("§b  _   _ _____  _                       _ _____                       _       ");
        consoleSender.sendMessage("§b | | (_)  __ \\(_)                     | |  __ \\                     | |      ");
        consoleSender.sendMessage("§b | |_ _| |  | |_ ___  ___ ___  _ __ __| | |__) |___ _ __   ___  _ __| |_ ___ ");
        consoleSender.sendMessage("§b | __| | |  | | / __|/ __/ _ \\| '__/ _` |  _  // _ \\ '_ \\ / _ \\| '__| __/ __|");
        consoleSender.sendMessage("§b | |_| | |__| | \\__ \\ (_| (_) | | | (_| | | \\ \\  __/ |_) | (_) | |  | |_\\__ \\");
        consoleSender.sendMessage("§b  \\__|_|_____/|_|___/\\___\\___/|_|  \\__,_|_|  \\_\\___| .__/ \\___/|_|   \\__|___/");
        consoleSender.sendMessage("§b                                                   | |                       ");
        consoleSender.sendMessage("§b                                                   |_|                       ");
        consoleSender.sendMessage("§9 tiDiscordReports " + getDescription().getVersion() + " §8| §9Автор: 1050TI_top");
        consoleSender.sendMessage(" ");

        try {
            JDALogger.setFallbackLoggerEnabled(false);

            jda = JDABuilder.createDefault(mainConfig.token)
                    .enableIntents(
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.MESSAGE_CONTENT
                    )
                    .addEventListeners(
                            new ButtonListener(),
                            new ModalListener()
                    )
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }

        getCommand("report").setExecutor(new ReportCommand());
    }

    public static TiDiscordReports getInstance() {
        return instance;
    }
}
