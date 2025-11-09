import java.time.LocalDateTime;

public class MessageMemento {
    private final String content;
    private final LocalDateTime timeStamp;
    
    public MessageMemento ( String content, LocalDateTime timeStamp) {
        this.content = content;
        this.timeStamp = timeStamp;
    }
    
    public String getContent() { return content; }
    public LocalDateTime getTimeStamp() {return timeStamp;}
}
