import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class MessageTest {


    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message("0718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.getMessageText().length() <= 250);
    }

    @Test
    public void testMessageLengthFailure() {
        String longMsg = new String(new char[251]).replace('\0', 'a');
        Message msg = new Message("0718693002", longMsg);
        assertFalse(msg.getMessageText().length() <= 250);
    }

    @Test
    public void testRecipientFormatSuccess() {
        Message msg = new Message("0718693002", "Hello");
        assertTrue(msg.checkRecipientCell());
    }

    @Test
public void testRecipientFormatFailure() {
    Message msg = new Message("+2781869300", "Hello"); // only 8 digits after +27
    assertFalse(msg.checkRecipientCell());
}

    @Test
public void testMessageHashCorrect() {
    Message msg = new Message("+27821234567", "Hello world");
    String hash = msg.createMessageHash();
    assertTrue(hash.matches("\\d{2}:[A-Z]{3}:\\d{4}"));
    assertTrue(hash.startsWith("67:HEL:"));
}

    @Test
    public void testMessageIDLength() {
        Message msg = new Message("0718693002", "Hello");
        assertTrue(msg.checkMessageID());
        assertEquals(10, msg.getMessageID().length());
    }

    @Before
    public void resetArrays() {
        // Clear all static arrays before each Part 3 test
        Message.messages.clear();
        Message.sentMessages.clear();
        Message.disregardedMessages.clear();
        Message.storedMessages.clear();
        Message.messageHashes.clear();
        Message.messageIDs.clear();
        Message.totalMessagesSent = 0;
    }

    public void addSentMessage(String recipient, String text, String hash, String id) {
        java.util.HashMap<String, String> msg = new java.util.HashMap<>();
        msg.put("Message ID",   id);
        msg.put("Message Hash", hash);
        msg.put("Recipient",    recipient);
        msg.put("Message",      text);
        Message.messages.add(msg);
        Message.sentMessages.add(text);
        Message.messageHashes.add(hash);
        Message.messageIDs.add(id);
        Message.totalMessagesSent++;
    }

    public void addStoredMessage(String recipient, String text, String hash, String id) {
        java.util.HashMap<String, String> msg = new java.util.HashMap<>();
        msg.put("Message ID",   id);
        msg.put("Message Hash", hash);
        msg.put("Recipient",    recipient);
        msg.put("Message",      text);
        Message.messages.add(msg);
        Message.storedMessages.add(text);
        Message.messageHashes.add(hash);
        Message.messageIDs.add(id);
    }

    @Test
    public void testSentMessagesArrayPopulated() {
        addSentMessage("+27834557896", "Did you get the cake?",       "HASH1", "1000000001");
        addSentMessage("0838884567",   "It is dinner time!",          "HASH4", "1000000004");

        assertEquals(2, Message.sentMessages.size());
        assertEquals("Did you get the cake?", Message.sentMessages.get(0));
        assertEquals("It is dinner time!",    Message.sentMessages.get(1));
    }

    @Test
    public void testDisplayLongestMessage() {
        addSentMessage("+27834557896", "Did you get the cake?", "HASH1", "1000000001");
        addStoredMessage("+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                "HASH2", "1000000002");

        String longest = Message.sentMessages.get(0);
        for (String m : Message.sentMessages)   if (m.length() > longest.length()) longest = m;
        for (String m : Message.storedMessages) if (m.length() > longest.length()) longest = m;

        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    @Test
    public void testSearchByMessageID() {
        addSentMessage("0838884567", "It is dinner time!", "HASH4", "1000000004");

        boolean found = false;
        for (java.util.HashMap<String, String> msg : Message.messages) {
            if ("1000000004".equals(msg.get("Message ID"))) {
                assertEquals("0838884567",    msg.get("Recipient"));
                assertEquals("It is dinner time!", msg.get("Message"));
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testSearchByRecipient() {
        addStoredMessage("+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                "HASH2", "1000000002");
        addStoredMessage("+27838884567", "Ok, I am leaving without you.", "HASH5", "1000000005");

        long count = Message.messages.stream()
                .filter(m -> "+27838884567".equals(m.get("Recipient")))
                .count();
        assertEquals(2, count);

        boolean hasMsg2 = Message.messages.stream().anyMatch(m ->
                "Where are you? You are late! I have asked you to be on time.".equals(m.get("Message")));
        boolean hasMsg5 = Message.messages.stream().anyMatch(m ->
                "Ok, I am leaving without you.".equals(m.get("Message")));
        assertTrue(hasMsg2);
        assertTrue(hasMsg5);
    }

    @Test
    public void testDeleteByHash() {
        addStoredMessage("+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                "HASH2", "1000000002");

        assertEquals(1, Message.messages.size());
        Message.deleteByHash("HASH2");
        assertEquals(0, Message.messages.size());
        assertFalse(Message.messageHashes.contains("HASH2"));
    }

    @Test
    public void testDisplayReportData() {
        addSentMessage("+27834557896", "Did you get the cake?", "HASH1", "1000000001");
        addSentMessage("0838884567",   "It is dinner time!",    "HASH4", "1000000004");

        assertTrue(Message.messages.stream().anyMatch(m -> "HASH1".equals(m.get("Message Hash"))));
        assertTrue(Message.messages.stream().anyMatch(m -> "HASH4".equals(m.get("Message Hash"))));
        assertTrue(Message.messages.stream().anyMatch(m -> "+27834557896".equals(m.get("Recipient"))));
        assertTrue(Message.messages.stream().anyMatch(m -> "Did you get the cake?".equals(m.get("Message"))));
        assertTrue(Message.messages.stream().anyMatch(m -> "It is dinner time!".equals(m.get("Message"))));
    }
}