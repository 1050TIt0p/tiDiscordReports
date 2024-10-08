package ru.matveylegenda.tidiscordreports.utils.configs;

import net.elytrium.serializer.annotations.Comment;
import net.elytrium.serializer.annotations.CommentValue;
import net.elytrium.serializer.annotations.NewLine;
import net.elytrium.serializer.language.object.YamlSerializable;
import ru.matveylegenda.tidiscordreports.TiDiscordReports;

public class MainConfig extends YamlSerializable {

//    @Comment({
//            @CommentValue(" Bot token")
//    })
//    public String token = "TOKEN";
//
//    @NewLine(amount = 1)
//    @Comment({
//            @CommentValue(" ID of the channel where report messages will be sent")
//    })
//    public String channelId = "123";
//
//    @NewLine(amount = 1)
//    @Comment({
//            @CommentValue(" Command usage cooldown time in seconds")
//    })
//    public int cooldownTime = 300;

    @Comment({
            @CommentValue(" Токен бота")
    })
    public String token = "TOKEN";

    @NewLine(amount = 1)
    @Comment({
            @CommentValue(" ID канала в который будут отправляться сообщения с репортами")
    })
    public String channelId = "123";

    @NewLine(amount = 1)
    @Comment({
            @CommentValue(" Время задержки использования команды в секундах")
    })
    public int cooldownTime = 300;

    public void reloadConfig() {
        reload(TiDiscordReports.getInstance().mainConfigFile.toPath());
    }
}
