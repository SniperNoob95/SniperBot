package Commands;

import Bot.SniperBot;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FFOTD {

    public static void changeFFOTD(MessageReceivedEvent event) {
        try {
            if (!Objects.requireNonNull(event.getMember()).getRoles().get(0).getName().equals("Owner")) {
                event.getChannel().sendTyping().complete();
                event.getChannel().sendMessage("You do not have permission to do this.").queue();
                return;
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            SniperBot.botLogger.logError("[FFOTD.changeFFOTD] - Failed to check member's top role.");
        }

        Random random = new Random();
        List<Member> memberList = event.getGuild().getMembers();
        Member chosenMember = memberList.get(random.nextInt(memberList.size()));
        List<Role> roles = event.getGuild().getRolesByName("Fuckface of the Day", false);

        if (roles.size() != 1) {
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage("Zero or more than one FFOTD role exists, please ping Sniper Noob to add one or remove the duplicates.").queue();
            return;
        }

        while (chosenMember.getRoles().get(0).getName().equals("Restricted")) {
            chosenMember = memberList.get(random.nextInt(memberList.size()));
        }

        for (Member member : memberList) {
            if (member.getRoles().contains(roles.get(0))) {
                event.getGuild().removeRoleFromMember(member, roles.get(0)).complete();
                break;
            }
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage("No previous FFOTD found.").queue();
        }

        event.getGuild().addRoleToMember(chosenMember, roles.get(0)).complete();
        event.getChannel().sendTyping().complete();
        event.getChannel().sendMessage(String.format("%s chosen as the new Fuckface of the Day", chosenMember.getAsMention())).queue();
    }
}
