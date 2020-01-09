package Commands;

import Bot.SniperBot;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;

public class Admit {

    public static void admitMember(MessageReceivedEvent event) {
        Role memberApprover = null;
        for (Role role : event.getGuild().getRoles()) {
            if (role.getName().equals("Member Approver")) {
                memberApprover = role;
            }
        }

        try {
            if (!Objects.requireNonNull(event.getMember()).getRoles().contains(memberApprover)) {
                event.getChannel().sendTyping().complete();
                event.getChannel().sendMessage("You do not have permission to do this.").queue();
                return;
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            SniperBot.botLogger.logError("[Admit.admitMember] - Failed to check member's roles for the Member Approver role.");
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

        if (event.getMessage().getMentionedMembers().size() != 1) {
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage("You must mention exactly one member to admit.").queue();
            return;
        }

        try {
            if (!event.getMessage().getMentionedMembers().get(0).getRoles().get(0).getName().equals("Restricted")) {
                event.getChannel().sendTyping().complete();
                event.getChannel().sendMessage("This member is not restricted.").queue();
            } else {
                Role restricted = null;
                Role collector = null;
                for (Role role : event.getGuild().getRoles()) {
                    if (role.getName().equals("Restricted")) {
                        restricted = role;
                    }

                    if (role.getName().equals("Collector")) {
                        collector = role;
                    }
                }

                event.getGuild().removeRoleFromMember(Objects.requireNonNull(event.getMessage().getMentionedMembers().get(0)), Objects.requireNonNull(restricted)).queue();
                event.getGuild().addRoleToMember(event.getMessage().getMentionedMembers().get(0), Objects.requireNonNull(collector)).queue();
                event.getChannel().sendTyping().complete();
                event.getChannel().sendMessage(String.format("Admitted %s to the server.", event.getMessage().getMentionedMembers().get(0).getAsMention())).queue();
                event.getMessage().getMentionedMembers().get(0).getUser().openPrivateChannel().queue(response -> response.sendMessage("Thank you for joining the server! If you have any friends that would be interested in joining, please feel free to send them the invite link: https://discord.gg/bYH4yHw").queue());
            }
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[Admit.admitMember] - Failed to admit member.");
        }

        try {
            SniperBot.botLogger.logMessage(String.format("Member %s admitted by %s", event.getMessage().getMentionedMembers().get(0).getAsMention(), Objects.requireNonNull(event.getMember()).getAsMention()));
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[Admit.admitMember] - Failed to log admission.");
        }

    }
}
