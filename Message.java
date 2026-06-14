import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Message {
    public String messageID;
    public String recipient;
    public String messageText;
    public String messageHash;
    public String timestamp;

    public static int totalMessagesSent = 0;

   
    public static ArrayList<HashMap<String, String>> messages = new ArrayList<>();

    
    public static ArrayList<String> sentMessages        = new ArrayList<>();
    public static ArrayList<String> disregardedMessages = new ArrayList<>();
    public static ArrayList<String> storedMessages      = new ArrayList<>();
    public static ArrayList<String> messageHashes       = new ArrayList<>();
    public static ArrayList<String> messageIDs          = new ArrayList<>();

    public Message(String recipient, String messageText) {
        this.messageID   = generateMessageID();
        this.recipient   = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
        this.timestamp = timestamp;
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
    return recipient.matches("^(\\+27\\d{9}|0\\d{9})$");
}

    public String createMessageHash() {
    String last2 = recipient.substring(recipient.length() - 2);
    String first3 = messageText.length() >= 3? messageText.substring(0, 3) : messageText;
    first3 = first3.toUpperCase();
    int random = (int)(Math.random() * 9000) + 1000; 
    return last2 + ":" + first3 + ":" + random;
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
                msg.put("Message ID",   messageID);
                msg.put("Message Hash", messageHash);
                msg.put("Recipient",    recipient);
                msg.put("Message",      messageText);
                messages.add(msg);

                sentMessages.add(messageText);
                messageHashes.add(messageHash);
                messageIDs.add(messageID);

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
                
                disregardedMessages.add(messageText);
                System.out.println("Message disregarded");
            }
        } else if (choice.equals("3")) {
            
            storedMessages.add(messageText);
            messageHashes.add(messageHash);
            messageIDs.add(messageID);

            HashMap<String, String> msg = new HashMap<>();
            msg.put("Message ID",   messageID);
            msg.put("Message Hash", messageHash);
            msg.put("Recipient",    recipient);
            msg.put("Message",      messageText);
            messages.add(msg);

            storeMessage();
            System.out.println("Message successfully stored.");
        }
    }

    public void displayMessage() {
        System.out.println("\n--- Message Details ---");
        System.out.println("Message ID: "   + messageID);
        System.out.println("Message Hash: " + messageHash);
        System.out.println("Recipient: "    + recipient);
        System.out.println("Message: "      + messageText);
    }

    public void storeMessage() {
        try (PrintWriter out = new PrintWriter(new FileWriter("messages.json"))) {
            out.println("[");
            for (int i = 0; i < messages.size(); i++) {
                HashMap<String, String> msg = messages.get(i);
                out.println("  {");
                out.println("    \"Message ID\": \""   + msg.get("Message ID")   + "\",");
                out.println("    \"Message Hash\": \"" + msg.get("Message Hash") + "\",");
                out.println("    \"Recipient\": \""    + msg.get("Recipient")    + "\",");
                out.println("    \"Message\": \""      + msg.get("Message")      + "\"");
                out.print("  }");
                if (i < messages.size() - 1) out.println(","); else out.println();
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

    public String getMessageID()   { return messageID; }
    public String getRecipient()   { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }

    Object sentMessage(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void loadStoredMessagesFromJSON() {
        File file = new File("messages.json");
        if (!file.exists()) return;
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            String[] blocks = content.split("\\{");
            for (String block : blocks) {
                if (!block.contains("Message ID")) continue;
                String id        = extractJsonField(block, "Message ID");
                String hash      = extractJsonField(block, "Message Hash");
                String recipient = extractJsonField(block, "Recipient");
                String msgText   = extractJsonField(block, "Message");
                if (!msgText.isEmpty()) {
                    HashMap<String, String> entry = new HashMap<>();
                    entry.put("Message ID",   id);
                    entry.put("Message Hash", hash);
                    entry.put("Recipient",    recipient);
                    entry.put("Message",      msgText);
                    messages.add(entry);
                    storedMessages.add(msgText);
                    if (!hash.isEmpty()) messageHashes.add(hash);
                    if (!id.isEmpty())   messageIDs.add(id);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load messages.json: " + e.getMessage());
        }
    }

    public static String extractJsonField(String block, String key) {
        String token = "\"" + key + "\": \"";
        int start = block.indexOf(token);
        if (start < 0) return "";
        start += token.length();
        int end = block.indexOf("\"", start);
        return end < 0 ? "" : block.substring(start, end);
    }

    /** a. Display sender and recipient of all stored messages */
    public static void displaySenderAndRecipient() {
        if (messages.isEmpty()) { System.out.println("No messages available."); return; }
        System.out.println("\n--- Sender & Recipient of All Messages ---");
        for (HashMap<String, String> msg : messages)
            System.out.println("Recipient: " + msg.get("Recipient") + " | Message: " + msg.get("Message"));
    }

    /** b. Display the longest stored message */
    public static void displayLongestMessage() {
        List<String> all = new ArrayList<>();
        all.addAll(sentMessages);
        all.addAll(storedMessages);
        if (all.isEmpty()) { System.out.println("No messages available."); return; }
        String longest = all.get(0);
        for (String m : all) if (m.length() > longest.length()) longest = m;
        System.out.println("\nLongest message:\n" + longest);
    }

    /** c. Search for a message ID and display recipient and message */
    public static void searchByMessageID(String id) {
        for (HashMap<String, String> msg : messages) {
            if (id.equals(msg.get("Message ID"))) {
                System.out.println("Recipient: " + msg.get("Recipient"));
                System.out.println("Message: "   + msg.get("Message"));
                return;
            }
        }
        System.out.println("Message ID \"" + id + "\" not found.");
    }

    /** d. Search for all messages stored for a particular recipient */
    public static void searchByRecipient(String recipient) {
        boolean found = false;
        for (HashMap<String, String> msg : messages) {
            if (recipient.equals(msg.get("Recipient"))) {
                System.out.println("- " + msg.get("Message"));
                found = true;
            }
        }
        if (!found) System.out.println("No messages found for: " + recipient);
    }

    /** e. Delete a message using the message hash */
    public static void deleteByHash(String hash) {
        Iterator<HashMap<String, String>> it = messages.iterator();
        while (it.hasNext()) {
            HashMap<String, String> msg = it.next();
            if (hash.equals(msg.get("Message Hash"))) {
                String text = msg.get("Message");
                it.remove();
                messageHashes.remove(hash);
                messageIDs.remove(msg.get("Message ID"));
                sentMessages.remove(text);
                storedMessages.remove(text);
                System.out.println("Message: \"" + text + "\" successfully deleted.");
                return;
            }
        }
        System.out.println("Hash \"" + hash + "\" not found.");
    }

    /** f. Display a full report of all stored messages */
    public static void displayFullReport() {
        System.out.println("\n=== FULL MESSAGE REPORT ===");
        if (messages.isEmpty()) { System.out.println("No messages to report."); return; }
        for (HashMap<String, String> msg : messages) {
            System.out.println("\nMessage Hash: " + msg.get("Message Hash"));
            System.out.println("Recipient:     " + msg.get("Recipient"));
            System.out.println("Message:       " + msg.get("Message"));
        }
        System.out.println("\nTotal sent: " + returnTotalMessages());
    }
}