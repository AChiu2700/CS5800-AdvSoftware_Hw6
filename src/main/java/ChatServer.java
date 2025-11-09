import java.util.*;

public class ChatServer {
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Set<String>> blocked = new HashMap<>();
    private final Map<String, Message> lastSentByUser = new HashMap<>();

    public void register(User user) {
        users.put(user.getName(), user);
        blocked.putIfAbsent(user.getName(), new HashSet<>());
    }

    public void unRegister(String userName) {
        users.remove(userName);
        blocked.remove(userName);
        lastSentByUser.remove(userName);
    }

    public void block(String blocker, String blockedUser) {
        blocked.putIfAbsent(blocker, new HashSet<>());
        blocked.get(blocker).add(blockedUser);
        System.out.println(blocker + " has blocked " + blockedUser);
    }

    public void sendMessage(User sender, List<String> recipients, String content) {
        Message message = new Message(sender.getName(), recipients, content);
        boolean anyDelivered = false;

        for (String rec : recipients) {
            User userRecipient = users.get(rec);
      
            // blocked
            if (blocked.getOrDefault(rec, Collections.emptySet()).contains(sender.getName())) {
                Message notSent = new Message(sender.getName(), rec, "Message did not recieve");
                sender.addToHistory(notSent);
                continue;
            }

            userRecipient.receiveMessage(message);
            anyDelivered = true;
        }

        if (anyDelivered) {
            sender.addToHistory(message);
            lastSentByUser.put(sender.getName(), message);
            sender.storeMemento(new MessageMemento(content, message.getTimestamp()));
        }
    }

    public void undoLast(User sender) {
        Message last = lastSentByUser.get(sender.getName());
        if (last == null) {
            System.out.println(sender.getName() + ": No more undos.\n");
            return;
        }

        for (String recipient : last.getRecipients()) {
            User userRecipient = users.get(recipient);
            if (userRecipient != null) {
                userRecipient.removeFromHistory(last);
            }
        }

        sender.removeFromHistory(last);
        lastSentByUser.remove(sender.getName());
        sender.clearMemento();

        System.out.println(sender.getName() + ": Unsent last message.");
    }
}
