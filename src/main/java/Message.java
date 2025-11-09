import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Message {
    private final String sender;
    private final List<String> recipients;
    private final String content;
    private final LocalDateTime timeStamp;

    public Message(String sender, List<String> recipients, String content) {
        this.sender = sender;
        this.recipients = new ArrayList<>(recipients);
        this.content = content;
        this.timeStamp = LocalDateTime.now();
    }

    public Message(String sender, String recipient, String content) {
        this.sender = sender;
        this.recipients = new ArrayList<>();
        this.recipients.add(recipient);
        this.content = content;
        this.timeStamp = LocalDateTime.now();
    }

    public String getSenderName() { return sender; }
    public List<String> getRecipients() { return new ArrayList<>(recipients); }
    public String getContent() { return content; }
    public LocalDateTime getTimestamp() { return timeStamp; }
    
    @Override 
    public String toString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
        return "[" + timeStamp.format(format) + "] " 
               + sender + " -> " + recipients + ": " + content;
    }
    
}