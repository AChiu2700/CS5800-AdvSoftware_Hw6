import java.util.*;

public class ChatHistory implements IterableByUser {
    private final List<Message> messages = new ArrayList<>();

    public void addMessage(Message message) {
        messages.add(message);
    }
    
    public boolean removeMessage (Message message){
        return messages.remove(message);
    }

    public Message getLastMessage(){
        if(messages.isEmpty())
            return null;
        return messages.get(messages.size()-1);
    }

    public List<Message> getAllMessages(){
        return new ArrayList<>(messages);
    }

    @Override
    public Iterator iterator(User userToSearchWith) {
        return new SearchMessagesByUser(getAllMessages(), userToSearchWith);
    }
}
