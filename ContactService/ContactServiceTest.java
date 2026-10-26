import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactServiceTest {

    private ContactService service;

    @BeforeEach
    void setUp() {
        // Starting with a new service keeps each test independent.
        service = new ContactService();
    }

    @Test
    void addsContactWithUniqueId() {
        Contact contact = new Contact("1", "John", "Smith", "1234567890", "1 Main St");

        service.addContact(contact);

        assertSame(contact, service.getContact("1"));
    }

    @Test
    void rejectsDuplicateContactId() {
        service.addContact(new Contact("1", "John", "Smith", "1234567890", "1 Main St"));

        // A second contact with the same ID should fail instead of replacing the first one.
        assertThrows(IllegalArgumentException.class,
                () -> service.addContact(new Contact("1", "Jane", "Jones", "0987654321", "2 Main St")));
        assertEquals("John", service.getContact("1").getFirstName());
        assertEquals("1234567890", service.getContact("1").getPhone());
    }

    @Test
    void deletesContactById() {
        service.addContact(new Contact("1", "John", "Smith", "1234567890", "1 Main St"));

        service.deleteContact("1");

        // If deletion worked, the same ID should no longer be retrievable.
        assertThrows(IllegalArgumentException.class, () -> service.getContact("1"));
    }

    @Test
    void updatesContactFieldsById() {
        service.addContact(new Contact("1", "John", "Smith", "1234567890", "1 Main St"));

        service.updateFirstName("1", "Jane");
        service.updateLastName("1", "Jones");
        service.updatePhone("1", "0987654321");
        service.updateAddress("1", "2 Main Street");

        Contact updated = service.getContact("1");

        assertEquals("Jane", updated.getFirstName());
        assertEquals("Jones", updated.getLastName());
        assertEquals("0987654321", updated.getPhone());
        assertEquals("2 Main Street", updated.getAddress());

        // The contact ID is intentionally unchanged because it is not an updatable field.
        assertEquals("1", updated.getContactId());
    }

    @Test
    void rejectsOperationsForMissingContact() {
        // These checks make sure update and delete operations do not silently fail.
        assertThrows(IllegalArgumentException.class, () -> service.deleteContact("missing"));
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("missing", "Jane"));
        assertThrows(IllegalArgumentException.class, () -> service.updateLastName("missing", "Jones"));
        assertThrows(IllegalArgumentException.class, () -> service.updatePhone("missing", "0987654321"));
        assertThrows(IllegalArgumentException.class, () -> service.updateAddress("missing", "2 Main Street"));
    }

    @Test
    void invalidUpdateDoesNotReplaceExistingValue() {
        service.addContact(new Contact("1", "John", "Smith", "1234567890", "1 Main St"));

        // A failed update should leave the original contact data alone.
        assertThrows(IllegalArgumentException.class,
                () -> service.updatePhone("1", "not a phone"));
        assertEquals("1234567890", service.getContact("1").getPhone());
    }

    @Test
    void rejectsNullContact() {
        assertThrows(IllegalArgumentException.class, () -> service.addContact(null));
    }

    @Test
    void updatesOnlyTheRequestedContact() {
        service.addContact(new Contact("1", "John", "Smith", "1234567890", "1 Main St"));
        service.addContact(new Contact("2", "Amy", "Brown", "1112223333", "3 Main St"));

        service.updateFirstName("1", "Jane");
        service.updateLastName("1", "Jones");
        service.updatePhone("1", "0987654321");
        service.updateAddress("1", "2 Main St");

        Contact updated = service.getContact("1");
        assertEquals("Jane", updated.getFirstName());
        assertEquals("Jones", updated.getLastName());
        assertEquals("0987654321", updated.getPhone());
        assertEquals("2 Main St", updated.getAddress());
        Contact other = service.getContact("2");
        // Updating one ID should leave the other person's information alone.
        assertEquals("Amy", other.getFirstName());
        assertEquals("Brown", other.getLastName());
        assertEquals("1112223333", other.getPhone());
        assertEquals("3 Main St", other.getAddress());
    }

    @Test
    void deletesOnlyTheRequestedContactAndAllowsIdReuse() {
        service.addContact(new Contact("1", "John", "Smith", "1234567890", "1 Main St"));
        Contact other = new Contact("2", "Amy", "Brown", "1112223333", "3 Main St");
        service.addContact(other);

        service.deleteContact("1");
        assertThrows(IllegalArgumentException.class, () -> service.getContact("1"));
        assertSame(other, service.getContact("2"));

        Contact replacement = new Contact("1", "Jane", "Jones", "0987654321", "2 Main St");
        service.addContact(replacement);
        assertSame(replacement, service.getContact("1"));
    }
}
