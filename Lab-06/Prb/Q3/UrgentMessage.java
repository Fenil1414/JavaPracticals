
interface Urgent {
}

class Sender {
    String name;

    Sender(String name) {
        this.name = name;
    }
}

class UrgentSender extends Sender implements Urgent {
    UrgentSender(String name) {
        super(name);
    }
}

public class UrgentMessage {
    static void sendMessage(Sender sender, String message) {
        System.out.println("To " + sender.name + ": " + message);

        if (sender instanceof Urgent) {
            System.out.println("To " + sender.name + ": " + message);
        }
    }

    public static void main(String[] args) {
        Sender s1 = new Sender("Rahul");
        Sender s2 = new UrgentSender("Amit");

        sendMessage(s1, "Hello!");
        sendMessage(s2, "Emergency!");
    }
}
