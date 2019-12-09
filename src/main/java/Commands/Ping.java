package Commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Ping {

    public static void sendPing(MessageReceivedEvent event) {
        long time = System.currentTimeMillis();
        event.getChannel().sendMessage("Pong!")
                .queue(response -> response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue());
    }
}
