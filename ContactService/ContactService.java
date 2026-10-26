import java.util.HashMap;
import java.util.Map;

public class ContactService {
    // A map makes it easy to look contacts up by their unique ID.
    private final Map<String, Contact> contacts = new HashMap<>();

    public void addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null.");
        }

        String contactId = contact.getContactId();

        // I check before adding so an existing contact cannot be silently replaced.
        if (contacts.containsKey(contactId)) {
            throw new IllegalArgumentException("A contact with that ID already exists.");
        }

        contacts.put(contactId, contact);
    }

    public void deleteContact(String contactId) {
        // remove() returns null when nothing was stored under the requested ID.
        if (contacts.remove(contactId) == null) {
            throw new IllegalArgumentException("Contact ID was not found.");
        }
    }

    public void updateFirstName(String contactId, String firstName) {
        getContact(contactId).setFirstName(firstName);
    }

    public void updateLastName(String contactId, String lastName) {
        getContact(contactId).setLastName(lastName);
    }

    public void updatePhone(String contactId, String phone) {
        getContact(contactId).setPhone(phone);
    }

    public void updateAddress(String contactId, String address) {
        getContact(contactId).setAddress(address);
    }

    public Contact getContact(String contactId) {
        Contact contact = contacts.get(contactId);

        // Keeping this check here lets all update methods share the same missing-ID behavior.
        if (contact == null) {
            throw new IllegalArgumentException("Contact ID was not found.");
        }

        return contact;
    }
}
