package Commands;

import Bot.SniperBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;

public class Admit {

    public static void admitMember(MessageReceivedEvent event) {
        try {
            if (!Objects.requireNonNull(event.getMember()).getRoles().get(0).getName().equals("Owner")
                    && !event.getMember().getRoles().get(0).getName().equals("Moderator")
                    && !event.getMember().getRoles().get(0).getName().equals("Member Approver")) {
                event.getChannel().sendTyping().complete();
                event.getChannel().sendMessage("You do not have permission to admit members.").queue();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[Admit.admitMember] - Failed to check member roles.");
        }

         try {
             if (!event.getChannel().getName().equals("restricted")) {
                 event.getChannel().sendTyping().complete();
                 event.getChannel().sendMessage(String.format("Members can only be admitted in the %s channel",
                         event.getGuild().getTextChannelsByName("restricted", false).get(0).getAsMention())).queue();
                 return;
             }
         } catch (Exception e) {
             e.printStackTrace();
             SniperBot.botLogger.logError("[Admit.admitMember] - Failed to check channel.");
         }


    }
}
