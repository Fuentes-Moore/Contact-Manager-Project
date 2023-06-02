import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Contact {
    private final String name;
    private final String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return name + " | " + phoneNumber;
    }
}

public class ContactManager {
    private static final String FILE_PATH = "contacts.txt";
    private List<Contact> contacts;

    public ContactManager() {
        contacts = loadContactsFromFile();
    }

    private List<Contact> loadContactsFromFile() {
        List<Contact> loadedContacts = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return loadedContacts;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                String name = parts[0].trim();
                String phoneNumber = parts[1].trim();
                loadedContacts.add(new Contact(name, phoneNumber));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not load contacts: " + e.getMessage());
        }
        return loadedContacts;
    }

    public static String formatPhoneNumber(String number) {
        StringBuilder formattedNumber = new StringBuilder();

        int length = number.length();
        int dashPosition = length % 3 == 0 ? 3 : length % 3;

        for (int i = 0; i < length; i++) {
            if (i > 0 && i % 3 == dashPosition) {
                formattedNumber.append("-");
            }
            formattedNumber.append(number.charAt(i));
        }


        return formattedNumber.toString();
    }

    private void saveContactsToFile() {
        try (PrintWriter writer = new PrintWriter(FILE_PATH)) {
            for (Contact contact : contacts) {
                writer.println(contact.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not save contacts: " + e.getMessage());
        }
    }

    private void showContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            System.out.println("Name        | Phone number");
            System.out.println("--------------------------");
            for (Contact contact : contacts) {
                System.out.println(contact.toString());
            }

        }
        System.out.println();
    }

    private void addContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the phone number: ");
        String phoneNumber = scanner.nextLine();
        String formattedNumber = formatPhoneNumber(phoneNumber);
        System.out.println("Formatted number: " + formattedNumber);
        Contact newContact = new Contact(name, phoneNumber);
        contacts.add(newContact);
        System.out.println("Contact added.\n");
    }

    private void searchContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name: ");
        String query = scanner.nextLine().toLowerCase();
        List<Contact> foundContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getName().toLowerCase().contains(query)) {
                foundContacts.add(contact);
            }
        }
        if (foundContacts.isEmpty()) {
            System.out.println("No contacts found.\n");
        } else {
            System.out.println("Found contacts:");
            for (Contact contact : foundContacts) {
                System.out.println(contact.toString());
            }
            System.out.println();
        }
    }

    private void deleteContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name to delete: ");
        String query = scanner.nextLine().toLowerCase();
        List<Contact> foundContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getName().toLowerCase().contains(query)) {
                foundContacts.add(contact);
            }
        }
        if (foundContacts.isEmpty()) {
            System.out.println("No contacts found.\n");
        } else {
            System.out.println("Multiple contacts found:");
            for (Contact contact : foundContacts) {
                System.out.println(contact.toString());
            }
            System.out.print("Are you sure you want to delete these contacts? (y/n): ");
            String confirm = scanner.nextLine().toLowerCase();
            if (confirm.equals("y")) {
                contacts.removeAll(foundContacts);
                System.out.println("Contacts deleted.\n");
            } else {
                System.out.println("Deletion canceled.\n");
            }
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("""
                           
                Main Menu:
                1. View contacts
                2. Add a new contact
                3. Search a contact by name
                4. Delete an existing contact
                5. Exit""");
            System.out.print("Enter an option (1, 2, 3, 4, or 5): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showContacts();
                case "2" -> addContact();
                case "3" -> searchContact();
                case "4" -> deleteContact();
                case "5" -> {
                    saveContactsToFile();
                    System.out.println("Exiting the application.");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.\n");
            }
        }
    }

    public static void main (String[]args){
        System.out.println("---------------------------------------------------------------");
        System.out.println("                 .__                                    \n" +
                "__  _  __  ____  |  |    ____   ____    _____    ____   \n" +
                "\\ \\/ \\/ /_/ __ \\ |  |  _/ ___\\ /  _ \\  /     \\ _/ __ \\  \n" +
                " \\     / \\  ___/ |  |__\\  \\___(  <_> )|  Y Y  \\\\  ___/  \n" +
                "  \\/\\_/   \\___  >|____/ \\___  >\\____/ |__|_|  / \\___  > \n" +
                "              \\/            \\/              \\/      \\/  ");
        System.out.println("----------------------------------------------------------------");
        System.out.println("    " +
                "" +
                "" +
                "  |\\_/|                  \n" +
                "     | @ @   Woof! \n" +
                "     |   <>              _  \n" +
                "     |  _/\\------____ ((| |))\n" +
                "     |               `--' |   \n" +
                " ____|_       ___|   |___.' \n" +
                "/_/_____/____/_______|");

//        String formattedNumber = formatPhoneNumber(phoneNumber);
//        System.out.println("Formatted number: " + formattedNumber);

        ContactManager contactManager = new ContactManager();
        contactManager.run();
    }
}
