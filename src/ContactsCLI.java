import Contacts.Contact;
import Contacts.ContactManager;

import java.util.Scanner;

public class ContactsCLI extends ContactManager {

    public ContactsCLI() {
        super();
    }

    public void displayContacts() {
        System.out.println("Name | Phone number");
        System.out.println("-------------------");
        for (Contact contact : contacts) {
            System.out.println(contact.getName() + " | " + contact.getPhoneNumber());
        }
    }

    public void addContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        Contact contact = new Contact(name, phoneNumber);
        contacts.add(contact);
        System.out.println("Contact added successfully.");
    }

    public void searchContactByName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name to search: ");
        String query = scanner.nextLine();

        boolean found = false;
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(query)) {
                System.out.println("Contact found:");
                System.out.println(contact.getName() + " | " + contact.getPhoneNumber());
                found = true;
            }
        }

        if (!found) {
            System.out.println("Contact not found.");
        }
    }

    public void deleteContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name to delete: ");
        String query = scanner.nextLine();

        boolean removed = false;
        Contact contactToRemove = null;
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(query)) {
                contactToRemove = contact;
                removed = true;
                break;
            }
        }

        if (removed) {
            contacts.remove(contactToRemove);
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    public static void main(String[] args) {
        ContactsCLI contactsCLI = new ContactsCLI();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println();
            System.out.println("1. View contacts.");
            System.out.println("2. Add a new contact.");
            System.out.println("3. Search a contact by name.");
            System.out.println("4. Delete an existing contact.");
            System.out.println("5. Exit.");
            System.out.print("Enter an option (1, 2, 3, 4, or 5): ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    contactsCLI.displayContacts();
                    break;
                case 2:
                    contactsCLI.addContact();
                    break;
                case 3:
                    contactsCLI.searchContactByName();
                    break;
                case 4:
                    contactsCLI.deleteContact();
                    break;
                case 5:
                    contactsCLI.saveContactsToFile();
                    System.out.println("Exiting... Bye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 5);
    }
}
