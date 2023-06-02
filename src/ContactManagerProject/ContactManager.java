package ContactManagerProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactManager {
    private static final String FILE_PATH = "contacts.txt";
    private List<Contact> contacts;

    public ContactManager() {
        contacts = loadContactsFromFile(); // Step 1: Load contacts from a file
    }

    private List<Contact> loadContactsFromFile() {
        List<Contact> loadedContacts = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return loadedContacts; // If the file doesn't exist, return an empty list
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|"); // Split the line by '|' character
                String name = parts[0].trim(); // Extract the name (first part)
                String phoneNumber = parts[1].trim(); // Extract the phone number (second part)
                loadedContacts.add(new Contact(name, phoneNumber)); // Create a new Contact object and add it to the list
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not load contacts: " + e.getMessage());
        }
        return loadedContacts; // Return the list of loaded contacts
    }

    public static String formatPhoneNumber(String number) {
        StringBuilder formattedNumber = new StringBuilder();

        int length = number.length();
        int dashPosition = length % 3 == 0 ? 3 : length % 3;

        for (int i = 0; i < length; i++) {
            if (i > 0 && i % 3 == dashPosition && i != length - 1) {
                formattedNumber.append("-");
            }
            formattedNumber.append(number.charAt(i));
        }

        return formattedNumber.toString(); // Return the formatted phone number
    }

    public static String formatName(String name) {
        int desiredLength = 15; // Adjust this value based on your desired spacing

        if (name.length() >= desiredLength) {
            return name;
        } else {
            StringBuilder formattedName = new StringBuilder(name);

            for (int i = 0; i < desiredLength - name.length(); i++) {
                formattedName.append(" ");
            }

            return formattedName.toString(); // Return the formatted name
        }
    }

    private void saveContactsToFile() {
        try (PrintWriter writer = new PrintWriter(FILE_PATH)) {
            for (Contact contact : contacts) {
                writer.println(contact.toString()); // Write each contact to the file
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not save contacts: " + e.getMessage());
        }
    }

    private void showContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            System.out.println("Name            | Phone number");
            System.out.println("--------------------------------");
            for (Contact contact : contacts) {
                String formattedName = formatName(contact.getName()); // Format the name
                String formattedPhoneNumber = formatPhoneNumber(contact.getPhoneNumber()); // Format the phone number
                System.out.println(formattedName + " | " + formattedPhoneNumber); // Print the formatted contact information
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
        String formattedNumber = formatPhoneNumber(phoneNumber); // Format the phone number
        System.out.println("Formatted number: " + formattedNumber);
        Contact newContact = new Contact(name, formattedNumber);
        contacts.add(newContact); // Add the new contact to the list
        System.out.println("Contact added.\n");
    }

    private void searchContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name: ");
        String query = scanner.nextLine().toLowerCase();
        List<Contact> foundContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getName().toLowerCase().contains(query)) {
                foundContacts.add(contact); // Add matching contacts to the list
            }
        }
        if (foundContacts.isEmpty()) {
            System.out.println("No contacts found.\n");
        } else {
            System.out.println("Found contacts:");
            for (Contact contact : foundContacts) {
                System.out.println(contact.toString()); // Print the details of each found contact
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
                foundContacts.add(contact); // Add matching contacts to the list
            }
        }
        if (foundContacts.isEmpty()) {
            System.out.println("No contacts found.\n");
        } else {
            System.out.println("Multiple contacts found:");
            for (Contact contact : foundContacts) {
                System.out.println(contact.toString()); // Print the details of each found contact
            }
            System.out.print("Are you sure you want to delete these contacts? (y/n): ");
            String confirm = scanner.nextLine().toLowerCase();
            if (confirm.equals("y")) {
                contacts.removeAll(foundContacts); // Remove the found contacts from the list
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
                5. Save and Exit""");
            System.out.println("Enter an option (1, 2, 3, 4, or 5): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showContacts(); // Show contacts
                case "2" -> addContact(); // Add a new contact
                case "3" -> searchContact(); // Search for a contact
                case "4" -> deleteContact(); // Delete a contact
                case "5" -> {
                    saveContactsToFile(); // Save contacts to the file
                    System.out.println("Exiting the application.");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.\n");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\n" +
                "░██╗░░░░░░░██╗███████╗██╗░░░░░░█████╗░░█████╗░███╗░░░███╗███████╗██╗██╗██╗\n" +
                "░██║░░██╗░░██║██╔════╝██║░░░░░██╔══██╗██╔══██╗████╗░████║██╔════╝██║██║██║\n" +
                "░╚██╗████╗██╔╝█████╗░░██║░░░░░██║░░╚═╝██║░░██║██╔████╔██║█████╗░░██║██║██║\n" +
                "░░████╔═████║░██╔══╝░░██║░░░░░██║░░██╗██║░░██║██║╚██╔╝██║██╔══╝░░╚═╝╚═╝╚═╝\n" +
                "░░╚██╔╝░╚██╔╝░███████╗███████╗╚█████╔╝╚█████╔╝██║░╚═╝░██║███████╗██╗██╗██╗\n" +
                "░░░╚═╝░░░╚═╝░░╚══════╝╚══════╝░╚════╝░░╚════╝░╚═╝░░░░░╚═╝╚══════╝╚═╝╚═╝╚═╝");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("                                 .\n" +
                "              . .                     -:-             .  .  .\n" +
                "            .'.:,'.        .  .  .     ' .           . \\ | / .\n" +
                "            .'.;.`.       ._. ! ._.       \\          .__\\:/__.\n" +
                "             `,:.'         ._\\!/_.                     .';`.      . ' .\n" +
                "             ,'             . ! .        ,.,      ..======..       .:.\n" +
                "            ,                 .         ._!_.     ||::: : | .        ',\n" +
                "     .====.,                  .           ;  .~.===: : : :|   ..===.\n" +
                "     |.::'||      .=====.,    ..=======.~,   |\"|: :|::::::|   ||:::|=====|\n" +
                "  ___| :::|!__.,  |:::::|!_,   |: :: ::|\"|l_l|\"|:: |:;;:::|___!| ::|: : :|\n" +
                " |: :|::: |:: |!__|; :: |: |===::: :: :|\"||_||\"| : |: :: :|: : |:: |:::::|\n" +
                " |:::| _::|: :|:::|:===:|::|:::|:===F=:|\"!/|\\!\"|::F|:====:|::_:|: :|::__:|\n" +
                " !_[]![_]_!_[]![]_!_[__]![]![_]![_][I_]!//_:_\\\\![]I![_][_]!_[_]![]_!_[__]!\n" +
                " -----------------------------------\"---''''```---\"-----------------------\n" +
                " _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ |= _ _:_ _ =| _ _ _ _ _ _ _ _ _ _ _ _\n" +
                "                                     |=    :    =|                Valkyrie\n" +
                "_____________________________________L___________J________________________");
        ContactManager contactManager = new ContactManager();
        contactManager.run(); // Run the contact manager application
    }
}
