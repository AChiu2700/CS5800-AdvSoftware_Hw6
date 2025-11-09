import java.util.*;

public class SearchMessagesByUser implements Iterator<Message> {
    private List<Message> messages;
    private String targetName;
    private int index = 0;
    private Message nextMatch = null;

    public SearchMessagesByUser(List<Message> messages, User userToSearchWith) {
        this.messages = messages;
        this.targetName = userToSearchWith.getName();
        findNext();
    }

    private void findNext() {
        nextMatch = null;
        while (index < messages.size()) {
            Message msg = messages.get(index++);
            boolean fromTarget = msg.getSenderName().equals(targetName);
            boolean toTarget = msg.getRecipients().contains(targetName);
            if (fromTarget || toTarget) {
                nextMatch = msg;
                return;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nextMatch != null;
    }

    @Override
    public Message next() {
        Message current = nextMatch;
        findNext();
        return current;
    }
}
