package ru.matveylegenda.tidiscordreports.utils.configs;

import net.elytrium.serializer.annotations.Comment;
import net.elytrium.serializer.annotations.CommentValue;
import net.elytrium.serializer.annotations.NewLine;
import net.elytrium.serializer.language.object.YamlSerializable;
import ru.matveylegenda.tidiscordreports.TiDiscordReports;

public class MessagesConfig extends YamlSerializable {

    public Minecraft minecraft = new Minecraft();

    public static class Minecraft {
        @Comment({
                @CommentValue(" {prefix} - Префикс перед сообщениями")
        })
        public String prefix = "&#5E08FBR&#6816F5ᴇ&#7123F0ᴘ&#7B31EAᴏ&#853EE4ʀ&#8E4CDFᴛ&#9859D9s &8»";

        @NewLine(amount = 1)
        @Comment({
                @CommentValue(" Сообщение отправляемое при использовании команды с недостаточным количеством аргументов")
        })
        public String usage = "{prefix} &fИспользование: &#5E08FB/report <игрок> <причина>";

        @Comment({
                @CommentValue(" Сообщение отправляемое при попытке подать жалобу на себя")
        })
        public String reportYourself = "{prefix} &fНельзя подавать репорт на себя";

        @Comment({
                @CommentValue(" Сообщение отправляемое при подачи репорта")
        })
        public String sendReport = "{prefix} &fРепорт на игрока &#5E08FB{reported} &fуспешно отправлен";

        @Comment({
                @CommentValue(" Сообщение отправляемое при перезагрузке конфига")
        })
        public String reload = "{prefix} &fКонфиг перезагружен";
    }

    public Discord discord = new Discord();

    @NewLine(amount = 1)
    public static class Discord {
        @Comment({
                @CommentValue(" Настройка сообщения репорта")
        })
        public ReportMessage reportMessage = new ReportMessage();

        public static class ReportMessage {
            @Comment({
                    @CommentValue(" Содержимое сообщения")
            })
            public String content = "";

            public Embed embed = new Embed();

            @NewLine(amount = 1)
            @Comment({
                    @CommentValue(" Настройка эмбеда")
            })
            public static class Embed {
                @Comment({
                        @CommentValue(" Заголовок эмбеда")
                })
                public String title = "Репорт";

                @Comment({
                        @CommentValue(" Описание эмбеда")
                })
                public String description =
                        "**Пожаловался:**\n" +
                                "{reporter}\n" +
                                "\n" +
                                "**Пожаловались на:**\n" +
                                "{reported}\n" +
                                "\n" +
                                "**Причина:**\n" +
                                "{reason}";

                @Comment({
                        @CommentValue(" Цвет эмбеда")
                })
                public String color = "3f24d6";
            }

            public Buttons buttons = new Buttons();

            @NewLine(amount = 1)
            @Comment({
                    @CommentValue(" Настройка кнопок")
            })
            public static class Buttons {
                public Ban ban = new Ban();

                @Comment({
                        @CommentValue(" Настройка кнопки для бана")
                })
                public static class Ban {
                    @Comment({
                            @CommentValue(" Эмодзи кнопки")
                    })
                    public String emoji = "🚫";

                    @Comment({
                            @CommentValue(" Текст кнопки")
                    })
                    public String text = "Забанить";

                    @Comment({
                            @CommentValue(" Команда выполняемая при нажатии на кнопку")
                    })
                    public String command = "ban {reported} {time} {reason}";
                }

                public Mute mute = new Mute();

                @NewLine(amount = 1)
                @Comment({
                        @CommentValue(" Настройка кнопки для мута")
                })
                public static class Mute {
                    @Comment({
                            @CommentValue(" Эмодзи кнопки")
                    })
                    public String emoji = "🔇";

                    @Comment({
                            @CommentValue(" Текст кнопки")
                    })
                    public String text = "Замутить";

                    @Comment({
                            @CommentValue(" Команда выполняемая при нажатии на кнопку")
                    })
                    public String command = "mute {reported} {time} {reason}";
                }

                public Deny deny = new Deny();

                @NewLine(amount = 1)
                @Comment({
                        @CommentValue(" Настройка кнопки для отклонения репорта")
                })
                public static class Deny {
                    @Comment({
                            @CommentValue(" Эмодзи кнопки")
                    })
                    public String emoji = "❌";

                    @Comment({
                            @CommentValue(" Текст кнопки")
                    })
                    public String text = "Отклонить";
                }
            }

            public Modals modals = new Modals();

            @NewLine(amount = 1)
            @Comment({
                    @CommentValue(" Настройка модального окна для указания времени и причины")
            })
            public static class Modals {
                public Ban ban = new Ban();

                public static class Ban {
                    @Comment({
                            @CommentValue(" Заголовок модального окна")
                    })
                    public String title = "Забанить игрока {reported}";
                }

                public Mute mute = new Mute();

                @NewLine(amount = 1)
                public static class Mute {
                    @Comment({
                            @CommentValue(" Заголовок модального окна")
                    })
                    public String title = "Замутить игрока {reported}";
                }

                @Comment({
                        @CommentValue(" Текст для указания времени")
                })
                public String time = "Время";

                @Comment({
                        @CommentValue(" Текст для указания причины")
                })
                public String reason = "Причина";
            }

            public Decision decision = new Decision();

            @NewLine(amount = 1)
            @Comment({
                    @CommentValue(" Текст добавляемый к сообщению после вынесения решения")
            })
            public static class Decision {
                @Comment({
                        @CommentValue(" Текст добавляемый к сообщению при принятии")
                })
                public String accepted = "\n \n**Статус:**\n✅ Принято";

                @Comment({
                        @CommentValue(" Текст добавляемый к сообщению при отклонении")
                })
                public String rejected = "\n \n**Статус:**\n❌ Отклонено";
            }
        }
    }

    public void reloadConfig() {
        reload(TiDiscordReports.getInstance().messagesConfigFile.toPath());
    }
}
