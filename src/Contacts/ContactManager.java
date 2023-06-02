package Contacts;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    protected List<Contacts.Contact> contacts;

    public ContactManager() {
        contacts = new ArrayList<>();
        loadContactsFromFile();
    }

    private void loadContactsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("contacts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String name = parts[0].trim();
                String phoneNumber = parts[1].trim();
                Contacts.Contact contact = new Contacts.Contact(name, phoneNumber);
                contacts.add(contact);
            }
        } catch (IOException e) {
            System.out.println("Error loading contacts from file: " + e.getMessage());
        }
    }

    public void saveContactsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("contacts.txt"))) {
            for (Contact contact : contacts) {
                writer.println(contact.getName() + " | " + contact.getPhoneNumber());
            }
            System.out.println("Contacts saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving contacts to file: " + e.getMessage());
        }
    }

}
