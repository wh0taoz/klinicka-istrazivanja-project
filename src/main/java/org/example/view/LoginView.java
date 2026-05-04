package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.Config;

public class LoginView {

    private final Stage stage;
    private final StackPane root;

    public LoginView(Stage stage) {
        this.stage = stage;
        this.root = new StackPane();
        this.root.setStyle("-fx-background-color: #f0f4f8;");
    }

    public void show() {
        showLoginForm();
        Scene scene = new Scene(root, 480, 520);
        stage.setTitle("KlinikaDB");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void showLoginForm() {
        VBox card = createCard();

        HBox logo = createLogo();
        HBox tabs = createTabs(true);

        Label title = new Label("Welcome back");
        title.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");
        Label subtitle = new Label("Sign in to your account");
        subtitle.setStyle("-fx-font-size: 13; -fx-text-fill: #888;");

        TextField username = createTextField("Enter your username");
        PasswordField password = createPasswordField("Enter your password");

        Label message = new Label();
        message.setStyle("-fx-font-size: 12; -fx-text-fill: #e53935;");
        message.setWrapText(true);

        Button loginBtn = createPrimaryButton("Sign in");

        loginBtn.setOnAction(e -> {
            String user = username.getText().trim();
            String pass = password.getText();
            // TODO: provera u tekstualnom fajlu korisnika
        });

        card.getChildren().addAll(
                logo, tabs,
                title, subtitle,
                createLabel("Username"), username,
                createLabel("Password"), password,
                loginBtn, message
        );

        root.getChildren().setAll(card);
    }

    private void showRegisterForm() {
        VBox card = createCard();

        HBox logo = createLogo();
        HBox tabs = createTabs(false);

        Label title = new Label("Create account");
        title.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");
        Label subtitle = new Label("Register as an external user");
        subtitle.setStyle("-fx-font-size: 13; -fx-text-fill: #888;");

        TextField username = createTextField("Choose a username");
        PasswordField password = createPasswordField("Create a password");
        PasswordField confirmPassword = createPasswordField("Repeat your password");

        Label message = new Label();
        message.setStyle("-fx-font-size: 12; -fx-text-fill: #e53935;");
        message.setWrapText(true);

        Button registerBtn = createPrimaryButton("Register");

        registerBtn.setOnAction(e -> {
            String user = username.getText().trim();
            String pass = password.getText();
            String confirm = confirmPassword.getText();
            // TODO: upis u tekstualni fajl korisnika
        });

        card.getChildren().addAll(
                logo, tabs,
                title, subtitle,
                createLabel("Username"), username,
                createLabel("Password"), password,
                createLabel("Confirm password"), confirmPassword,
                registerBtn, message
        );

        root.getChildren().setAll(card);
    }

    // ───────────────────────── helper metode ─────────────────────────

    private VBox createCard() {
        VBox card = new VBox(14);
        card.setMaxWidth(380);
        card.setPadding(new Insets(32, 36, 32, 36));
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 16;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 20, 0, 0, 4);"
        );
        return card;
    }

    private HBox createLogo() {
        HBox logo = new HBox(10);
        logo.setAlignment(Pos.CENTER_LEFT);

        StackPane iconBox = new StackPane();
        iconBox.setPrefSize(38, 38);
        iconBox.setStyle(
                "-fx-background-color: #E1F5EE;" +
                        "-fx-background-radius: 10;"
        );
        Label icon = new Label("+");
        icon.setStyle("-fx-text-fill: #1D9E75; -fx-font-size: 20; -fx-font-weight: bold;");
        iconBox.getChildren().add(icon);

        VBox textBox = new VBox(2);
        Label name = new Label("KlinikaDB");
        name.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");
        Label sub = new Label("Clinical Research Tracking System");
        sub.setStyle("-fx-font-size: 11; -fx-text-fill: #888;");
        textBox.getChildren().addAll(name, sub);

        logo.getChildren().addAll(iconBox, textBox);
        return logo;
    }

    private HBox createTabs(boolean loginActive) {
        HBox tabs = new HBox(0);
        tabs.setStyle(
                "-fx-background-color: #f0f4f8;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #e0e0e0;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-width: 1;"
        );

        String activeStyle =
                "-fx-background-color: white;" +
                        "-fx-background-radius: 9;" +
                        "-fx-font-size: 13;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #1a1a1a;" +
                        "-fx-padding: 8 20;" +
                        "-fx-cursor: hand;";

        String inactiveStyle =
                "-fx-background-color: transparent;" +
                        "-fx-background-radius: 9;" +
                        "-fx-font-size: 13;" +
                        "-fx-text-fill: #888;" +
                        "-fx-padding: 8 20;" +
                        "-fx-cursor: hand;";

        Button loginTab = new Button("Login");
        Button registerTab = new Button("Register");

        loginTab.setStyle(loginActive ? activeStyle : inactiveStyle);
        registerTab.setStyle(!loginActive ? activeStyle : inactiveStyle);

        loginTab.setMaxWidth(Double.MAX_VALUE);
        registerTab.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(loginTab, Priority.ALWAYS);
        HBox.setHgrow(registerTab, Priority.ALWAYS);

        // pozivamo showLoginForm/showRegisterForm direktno, bez Stage parametra
        loginTab.setOnAction(e -> showLoginForm());
        registerTab.setOnAction(e -> showRegisterForm());

        tabs.getChildren().addAll(loginTab, registerTab);
        return tabs;
    }

    private TextField createTextField(String placeholder) {
        TextField field = new TextField();
        field.setPromptText(placeholder);
        field.setPrefHeight(40);
        String normal =
                "-fx-background-color: #f9f9f9;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-color: #e0e0e0;" +
                        "-fx-border-radius: 8;" +
                        "-fx-border-width: 1;" +
                        "-fx-font-size: 13;" +
                        "-fx-padding: 0 12;";
        String focused =
                "-fx-background-color: white;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-color: #1D9E75;" +
                        "-fx-border-radius: 8;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-font-size: 13;" +
                        "-fx-padding: 0 12;";
        field.setStyle(normal);
        field.focusedProperty().addListener((obs, old, isFocused) ->
                field.setStyle(isFocused ? focused : normal));
        return field;
    }

    private PasswordField createPasswordField(String placeholder) {
        PasswordField field = new PasswordField();
        field.setPromptText(placeholder);
        field.setPrefHeight(40);
        String normal =
                "-fx-background-color: #f9f9f9;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-color: #e0e0e0;" +
                        "-fx-border-radius: 8;" +
                        "-fx-border-width: 1;" +
                        "-fx-font-size: 13;" +
                        "-fx-padding: 0 12;";
        String focused =
                "-fx-background-color: white;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-color: #1D9E75;" +
                        "-fx-border-radius: 8;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-font-size: 13;" +
                        "-fx-padding: 0 12;";
        field.setStyle(normal);
        field.focusedProperty().addListener((obs, old, isFocused) ->
                field.setStyle(isFocused ? focused : normal));
        return field;
    }

    private Label createLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-font-size: 12; -fx-text-fill: #555; -fx-font-weight: bold;");
        return lbl;
    }

    private Button createPrimaryButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(42);
        String normal =
                "-fx-background-color: #1D9E75;" +
                        "-fx-background-radius: 10;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;";
        String hover =
                "-fx-background-color: #0F6E56;" +
                        "-fx-background-radius: 10;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;";
        btn.setStyle(normal);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(normal));
        return btn;
    }
}