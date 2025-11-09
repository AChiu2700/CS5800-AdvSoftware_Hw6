import java.util.*;

public class User implements IterableByUser{
    private final String userName;
    private final ChatServer chatServer;
    private final ChatHistory chatHistory;
    private MessageMemento lastMemento;

    public User(String userName, ChatServer chatServer) {
        this.userName = userName;
        this.chatServer = chatServer;
        this.chatHistory = new ChatHistory();
        chatServer.register(this);
    }

    public String getName() { return userName; }

    public void sendToOne(String recipient, String content) {
        List<String> list = new ArrayList<>();
        list.add(recipient);
        chatServer.sendMessage(this, list, content);
    }

    public void sendToMany(List<String> recipients, String content) {
        chatServer.sendMessage(this, recipients, content);
    }

    public void receiveMessage(Message message) {
        chatHistory.addMessage(message);
        System.out.println("Received Message: " + message);
    }

    public void addToHistory(Message message) { chatHistory.addMessage(message); }
    public void removeFromHistory(Message message) { chatHistory.removeMessage(message); }

    public void block(String otherUser) { chatServer.block(this.userName, otherUser); }

    public void storeMemento(MessageMemento message) { this.lastMemento = message; }
    public void clearMemento() { this.lastMemento = null; }
    public void undoLastMessage() { chatServer.undoLast(this); }

    public ChatHistory getChatHistory() { return chatHistory; }

    @Override
    public Iterator iterator(User userToSearchWith) {
        return chatHistory.iterator(userToSearchWith);
    }

    public void showMessagesByUser(User other) {
        System.out.println("\n" + userName + "'s chat with " + other.getName() + ":");
        Iterator<Message> it = this.iterator(other);
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
