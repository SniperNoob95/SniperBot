package Commands;

import Bot.SniperBot;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;

public class Join {

    public static void requestJoin(MessageReceivedEvent event) {

        // TODO check restricted
        try {
            if (!Objects.requireNonNull(event.getMember()).getRoles().get(0).getName().equals("Restricted") && !event.getChannel().getName().equals("restricted")) {
                TextChannel channel = event.getGuild().getTextChannelsByName("restricted", false).get(0);
                event.getChannel().sendTyping().complete();
                event.getChannel().sendMessage(String.format("This command can only be used by Restricted users in the %s channel.", channel.getAsMention())).queue();
            } else {
                Role memberApprovers = event.getGuild().getRolesByName("Member Approver", false).get(0);
                event.getChannel().sendMessage(String.format("%s is requesting to join the server. Paging %s to further assist.", event.getMember().getAsMention(), memberApprovers.getAsMention())).queue();
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            SniperBot.botLogger.logError("[Join.requestJoin] - Failed to check role and channel.");
        }
    }
}
