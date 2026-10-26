public class Contact {
    // The ID is final because the requirements say it should never be updated.
    private final String contactId;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        // Validate everything up front so an invalid Contact object cannot be created.
        validateContactId(contactId);
        validateFirstName(firstName);
        validateLastName(lastName);
        validatePhone(phone);
        validateAddress(address);

        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    public String getContactId() {
        return contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setFirstName(String firstName) {
        validateFirstName(firstName);
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        validateLastName(lastName);
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        validatePhone(phone);
        this.phone = phone;
    }

    public void setAddress(String address) {
        validateAddress(address);
        this.address = address;
    }

    private static void validateContactId(String contactId) {
        if (contactId == null || contactId.length() > 10) {
            throw new IllegalArgumentException("Contact ID must be non-null and no longer than 10 characters.");
        }
    }

    private static void validateFirstName(String firstName) {
        if (firstName == null || firstName.length() > 10) {
            throw new IllegalArgumentException("First name must be non-null and no longer than 10 characters.");
        }
    }

    private static void validateLastName(String lastName) {
        if (lastName == null || lastName.length() > 10) {
            throw new IllegalArgumentException("Last name must be non-null and no longer than 10 characters.");
        }
    }

    private static void validatePhone(String phone) {
        // Using a regular expression keeps this check simple and also rejects non-digits.
        if (phone == null || !phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone number must contain exactly 10 digits.");
        }
    }

    private static void validateAddress(String address) {
        if (address == null || address.length() > 30) {
            throw new IllegalArgumentException("Address must be non-null and no longer than 30 characters.");
        }
    }
}
