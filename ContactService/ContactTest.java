import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ContactTest {

    @Test
    void createsValidContact() {
        Contact contact = new Contact("1234567890", "Esteban", "Garcia", "9165551212", "123 Main Street");

        // First I verify that a normal contact stores every value correctly.
        assertEquals("1234567890", contact.getContactId());
        assertEquals("Esteban", contact.getFirstName());
        assertEquals("Garcia", contact.getLastName());
        assertEquals("9165551212", contact.getPhone());
        assertEquals("123 Main Street", contact.getAddress());
    }

    @Test
    void allowsMaximumFieldLengths() {
        // These values sit right on the allowed boundaries from the requirements.
        Contact contact = new Contact(
                "1234567890",
                "ABCDEFGHIJ",
                "KLMNOPQRST",
                "1234567890",
                "123456789012345678901234567890");

        assertEquals(10, contact.getContactId().length());
        assertEquals(10, contact.getFirstName().length());
        assertEquals(10, contact.getLastName().length());
        assertEquals(10, contact.getPhone().length());
        assertEquals(30, contact.getAddress().length());
    }

    @Test
    void rejectsNullOrLongContactId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact(null, "John", "Smith", "1234567890", "1 Main St"));
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("12345678901", "John", "Smith", "1234567890", "1 Main St"));
    }

    @Test
    void rejectsInvalidFirstName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", null, "Smith", "1234567890", "1 Main St"));
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "ABCDEFGHIJK", "Smith", "1234567890", "1 Main St"));
    }

    @Test
    void rejectsInvalidLastName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "John", null, "1234567890", "1 Main St"));
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "John", "ABCDEFGHIJK", "1234567890", "1 Main St"));
    }

    @Test
    void rejectsInvalidPhone() {
        // I included both wrong lengths and non-numeric input since the phone must be exactly 10 digits.
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "John", "Smith", null, "1 Main St"));
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "John", "Smith", "123456789", "1 Main St"));
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "John", "Smith", "12345678901", "1 Main St"));
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "John", "Smith", "12345abcde", "1 Main St"));
    }

    @Test
    void rejectsInvalidAddress() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "John", "Smith", "1234567890", null));
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "John", "Smith", "1234567890",
                        "1234567890123456789012345678901"));
    }

    @Test
    void validatesUpdatedFields() {
        Contact contact = new Contact("1", "John", "Smith", "1234567890", "1 Main St");

        // Updates should follow the same validation rules as the constructor.
        for (String invalidName : new String[] {null, "ABCDEFGHIJK"}) {
            assertThrows(IllegalArgumentException.class, () -> contact.setFirstName(invalidName));
            assertThrows(IllegalArgumentException.class, () -> contact.setLastName(invalidName));
            assertEquals("John", contact.getFirstName());
            assertEquals("Smith", contact.getLastName());
        }
        for (String invalidPhone : new String[] {null, "123456789", "12345678901", "12345abcde"}) {
            assertThrows(IllegalArgumentException.class, () -> contact.setPhone(invalidPhone));
            assertEquals("1234567890", contact.getPhone());
        }
        for (String invalidAddress : new String[] {null, "1234567890123456789012345678901"}) {
            assertThrows(IllegalArgumentException.class, () -> contact.setAddress(invalidAddress));
            assertEquals("1 Main St", contact.getAddress());
        }
        assertEquals("1", contact.getContactId());
    }

    @Test
    void acceptsUpdatesAtMaximumLengths() {
        Contact contact = new Contact("1", "John", "Smith", "1234567890", "1 Main St");
        contact.setFirstName("ABCDEFGHIJ");
        contact.setLastName("KLMNOPQRST");
        contact.setPhone("0987654321");
        contact.setAddress("123456789012345678901234567890");

        assertEquals("ABCDEFGHIJ", contact.getFirstName());
        assertEquals("KLMNOPQRST", contact.getLastName());
        assertEquals("0987654321", contact.getPhone());
        assertEquals("123456789012345678901234567890", contact.getAddress());
        assertEquals("1", contact.getContactId());
    }
}
