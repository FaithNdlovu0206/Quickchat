import java.util.*;
import java.io.*;

public class Message {
    public String messageID;
    public String recipient;
    public String messageText;
    public String messageHash;

    public static int totalMessagesSent = 0;
    public static ArrayList<HashMap<String, String>> messages = new ArrayList<>();

    public Message(String recipient, String messageText) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }


    Message(int i, String string, String hi_Mike_can_you_join_us_for_dinner_tonigh) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String generateMessageID() {
        Random rand = new Random();
        long num = 1000000000L + rand.nextInt(900000000);
        return String.valueOf(num);
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public boolean checkRecipientCell() {
        return recipient.matches("^\\+\\d{10,12}$");
    }

    public String createMessageHash() {
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 0 ? words[words.length - 1] : "";

        return (messageID.substring(0, 2) + ":" + totalMessagesSent + ":" + firstWord + lastWord).toUpperCase();
    }

    public void sentMessage(Scanner sc) {
        System.out.println("\nChoose an option:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");

        String choice = sc.nextLine();

        if (choice.equals("1")) {
            if (checkRecipientCell() && messageText.length() <= 250) {
                totalMessagesSent++;
                messageHash = createMessageHash();

                HashMap<String, String> msg = new HashMap<>();
                msg.put("Message ID", messageID);
                msg.put("Message Hash", messageHash);
                msg.put("Recipient", recipient);
                msg.put("Message", messageText);
                messages.add(msg);

                System.out.println("Message successfully sent.");
                displayMessage();
            } else {
                if (!checkRecipientCell()) {
                    System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                }
                if (messageText.length() > 250) {
                    int extra = messageText.length() - 250;
                    System.out.println("Message exceeds 250 characters by " + extra + ", please reduce the size.");
                }
            }
        } else if (choice.equals("2")) {
            System.out.println("Press 0 to delete the message");
            if (sc.nextLine().equals("0")) {
                System.out.println("Message disregarded");
            }
        } else if (choice.equals("3")) {
            storeMessage();
            System.out.println("Message successfully stored.");
        }
    }

    public void displayMessage() {
        System.out.println("\n--- Message Details ---");
        System.out.println("Message ID: " + messageID);
        System.out.println("Message Hash: " + messageHash);
        System.out.println("Recipient: " + recipient);
        System.out.println("Message: " + messageText);
    }

    public void storeMessage() {
        try (PrintWriter out = new PrintWriter(new FileWriter("messages.json"))) {
            out.println("[");
            for (int i = 0; i < messages.size(); i++) {
                HashMap<String, String> msg = messages.get(i);

                out.println("  {");
                out.println("    \"Message ID\": \"" + msg.get("Message ID") + "\",");
                out.println("    \"Message Hash\": \"" + msg.get("Message Hash") + "\",");
                out.println("    \"Recipient\": \"" + msg.get("Recipient") + "\",");
                out.println("    \"Message\": \"" + msg.get("Message") + "\"");
                out.print("  }");

                if (i < messages.size() - 1) {
                    out.println(",");
                } else {
                    out.println();
                }
            }
            out.println("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    public static void printAllMessages() {
        System.out.println("\n--- All Sent Messages ---");
        for (HashMap<String, String> msg : messages) {
            System.out.println(msg);
        }
        System.out.println("\nTotal messages sent: " + returnTotalMessages());
    }

    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }


    Object sentMessage(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}