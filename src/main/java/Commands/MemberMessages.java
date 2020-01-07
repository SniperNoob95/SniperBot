package Commands;

import Bot.SniperBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class MemberMessages {

    public static void sendMemberMessages(MessageReceivedEvent event) {
        try {
            if (Objects.requireNonNull(event.getMember()).getRoles().get(0).getName().equals("Restricted")) {
                event.getChannel().sendTyping().complete();
                event.getChannel().sendMessage("You do not have permission to do this.").queue();
                return;
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            SniperBot.botLogger.logError("[MemberMessages.sendMemberMessages] - Failed to check member's top role.");
        }

        try {
            int count = SniperBot.databaseClient.memberMessages(event);
            if (count != -1) {
                event.getChannel().sendTyping().complete();
                event.getChannel().sendMessage(String.format("%s has sent %s messages in this server.", Objects.requireNonNull(event.getMember()).getEffectiveName(), NumberFormat.getNumberInstance(Locale.US).format(count))).queue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[MemberMessages.sendMemberMessages] - Unable to get member message count.");
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage("Unable to retrieve message count for this member.").queue();
        }
    }
}
