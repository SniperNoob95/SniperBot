package Commands;

import Bot.SniperBot;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.Objects;

public class Notify {

    public static void sendNotification(MessageReceivedEvent event) {
        List<Role> roleList = event.getGuild().getRolesByName("Member Approver", false);
        if (roleList.size() != 1) {
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage("Zero or more than one Member Approver role exists, please ping Sniper Noob to add one or remove the duplicates.").queue();
            return;
        }

        try {
            if (!Objects.requireNonNull(event.getMember()).getRoles().contains(roleList.get(0))) {
                event.getChannel().sendTyping().complete();
                event.getChannel().sendMessage("You do not have permission to do this.").queue();
                return;
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            SniperBot.botLogger.logError("[Notify.sendNotification] - Failed to check member's roles.");
        }

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("restricted", false);
        if (textChannels.size() != 1) {
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage("Zero or more than one restricted channel exists, please ping Sniper Noob to add one or remove the duplicates.").queue();
            return;
        }
        if (!event.getChannel().getName().equals("restricted")) {
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage(String.format("This command can only be used in the %s channel.", textChannels.get(0).getAsMention())).queue();
            return;
        }

        roleList = event.getGuild().getRolesByName("Restricted", false);
        if (roleList.size() != 1) {
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage("Zero or more than one Restricted role exists, please ping Sniper Noob to add one or remove the duplicates.").queue();
            return;
        }

        textChannels = event.getGuild().getTextChannelsByName("help", false);
        if (textChannels.size() != 1) {
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage("Zero or more than one help channel exists, please ping Sniper Noob to add one or remove the duplicates.").queue();
            return;
        }

        event.getChannel().sendTyping().complete();
        event.getChannel().sendMessage(String.format("%s If you would like to join the server, please read the information in %s for details on membership requirements, and then type `!join`.", roleList.get(0).getAsMention(), textChannels.get(0).getAsMention())).queue();
    }
}
