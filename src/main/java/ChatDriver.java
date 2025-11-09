import java.util.*;

public class ChatDriver {
    public static void main(String[] args) {
        ChatServer server = new ChatServer();

        User echo = new User("Echo", server);
        User nova = new User("Nova", server);
        User viz = new User("Viz", server);

        System.out.println("\nSend to One:");
        echo.sendToOne("Nova", "Hey Nova!");
        nova.sendToOne("Echo","How are you?");
        viz.sendToOne("Nova", "Hi Friend!");
        nova.sendToOne("Viz", "Wrong number, sorry.");

        echo.showMessagesByUser(nova);
        viz.showMessagesByUser(nova);

        System.out.println("\n---- Block ----\n");
        nova.block("Viz");

        System.out.println("Viz sends Nova another Message: You Sure?");
        viz.sendToOne("Nova", "You Sure?");
        viz.showMessagesByUser(nova);

        System.out.println("\n---- Unsend Group Message ----");
        System.out.println("\nSend to Many:");
        nova.sendToMany(Arrays.asList("Echo", "Viz"), "Group hello!");
        nova.showMessagesByUser(viz);
        nova.showMessagesByUser(echo);

        System.out.println();
        nova.undoLastMessage();

        System.out.println("Echo log: " + echo.getChatHistory().getLastMessage());
        System.out.println("Nova log: " + nova.getChatHistory().getLastMessage());
        System.out.println("Viz log: "  + viz.getChatHistory().getLastMessage());

        System.out.println("\n---- User Chat Logs ----");
        echo.showMessagesByUser(nova);
        viz.showMessagesByUser(nova);
        nova.showMessagesByUser(viz);
    }
}
