import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class ChatServerTest {

    @Test
    void sendToOne() {
        ChatServer server = new ChatServer();
        User echo = new User("Echo", server);
        User nova = new User("Nova", server);

        echo.sendToOne("Nova", "Hello Nova!");
        Message last = nova.getChatHistory().getLastMessage();

        assertNotNull(last);
        assertEquals("Echo", last.getSenderName());
        assertEquals(List.of("Nova"), last.getRecipients());
        assertEquals("Hello Nova!", last.getContent());
    }

    @Test
    void sendToMany() {
        ChatServer server = new ChatServer();
        User echo = new User("Echo", server);
        User nova = new User("Nova", server);
        User viz  = new User("Viz", server);

        echo.sendToMany(Arrays.asList("Nova", "Viz"), "Group hello!");

        Message nLast = nova.getChatHistory().getLastMessage();
        Message vLast = viz.getChatHistory().getLastMessage();
        Message eLast = echo.getChatHistory().getLastMessage();

        assertNotNull(nLast);
        assertNotNull(vLast);
        assertNotNull(eLast);

        assertEquals("Group hello!", nLast.getContent());
        assertEquals("Group hello!", vLast.getContent());
        assertEquals("Group hello!", eLast.getContent());
        assertEquals(List.of("Nova", "Viz"), eLast.getRecipients());
        assertEquals("Echo", eLast.getSenderName());
    }

    @Test
    void blockingTest() {
        ChatServer server = new ChatServer();
        User echo = new User("Echo", server);
        User nova = new User("Nova", server);

        echo.sendToOne("Nova", "Hey Nova!");
        Message beforeBlock = nova.getChatHistory().getLastMessage();
        assertNotNull(beforeBlock);
        assertEquals("Hey Nova!", beforeBlock.getContent());

        nova.block("Echo");
        echo.sendToOne("Nova", "You shouldn't see this!");

        Message afterBlock = nova.getChatHistory().getLastMessage();
        assertNotNull(afterBlock);
        assertEquals("Hey Nova!", afterBlock.getContent());
        assertEquals("Echo", afterBlock.getSenderName());
        assertEquals(List.of("Nova"), afterBlock.getRecipients());
    }

    @Test
    void undoTest() {
        ChatServer server = new ChatServer();
        User echo = new User("Echo", server);
        User nova = new User("Nova", server);
        User viz  = new User("Viz", server);

        echo.sendToMany(Arrays.asList("Nova", "Viz"), "Group hello!");

        assertEquals("Group hello!", nova.getChatHistory().getLastMessage().getContent());
        assertEquals("Group hello!", viz.getChatHistory().getLastMessage().getContent());
        assertEquals("Group hello!", echo.getChatHistory().getLastMessage().getContent());

        echo.undoLastMessage();

        assertNull(nova.getChatHistory().getLastMessage());
        assertNull(viz.getChatHistory().getLastMessage());
        assertNull(echo.getChatHistory().getLastMessage());
    }

    @Test
    void iteratorSimpleTwoMessageTest() {
        ChatServer server = new ChatServer();
        User echo = new User("Echo", server);
        User nova = new User("Nova", server);


        echo.sendToOne("Nova", "Hello Nova!");
        nova.sendToOne("Echo", "Hey Echo!");

        @SuppressWarnings({"rawtypes","unchecked"})
        Iterator it = echo.iterator(nova);

        List<Message> convo = new ArrayList<>();
        while (it.hasNext()) {
            convo.add((Message) it.next());
        }

        assertEquals(2, convo.size());
        assertEquals("Echo", convo.get(0).getSenderName());
        assertEquals("Hello Nova!", convo.get(0).getContent());
        assertEquals("Nova", convo.get(1).getSenderName());
        assertEquals("Hey Echo!", convo.get(1).getContent());
    }
}
