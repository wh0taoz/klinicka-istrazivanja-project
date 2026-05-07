package org.example.controller;

import org.example.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoginController {

    private static final String FILE_PATH = "users.txt";

    // Ucitava sve korisnike iz txt fajla
    private List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return users;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    users.add(new User(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    // Provjerava da li korisnik postoji i da li je lozinka ispravna
    public boolean login(String username, String password) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Registruje novog korisnika ako korisnicko ime nije zauzeto
    public boolean register(String username, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return false;
        }

        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; // korisnicko ime vec postoji
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(username + ":" + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}