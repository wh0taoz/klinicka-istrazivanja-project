package org.example.view;
// gui uradjen pomocu ai

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.model.Istrazivanje;

import java.rmi.ConnectIOException;
import java.sql.Connection;
import java.util.List;

public class MainView {

    private final Stage stage;
    private final BorderPane root;
    private final String username;
    private final Connection connection;
    private VBox content;

    public MainView(Stage stage, String username, Connection connection) {
        this.stage = stage;
        this.username = username;
        this.root = new BorderPane();
        this.root.setStyle("-fx-background-color: #f0f4f8;");
        this.connection = connection;
    }

    public void show() {
        root.setLeft(createSidebar());
        content = new VBox();
        content.setPadding(new Insets(28));
        root.setCenter(content);

        showExperiments();

        Scene scene = new Scene(root, 900, 580);
        stage.setTitle("KlinikaDB");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    // ───────────────────────── sidebar ─────────────────────────

    private VBox createSidebar() {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(220);
        sidebar.setStyle(
                "-fx-background-color: #f8f8f8;" +
                        "-fx-border-color: #e0e0e0;" +
                        "-fx-border-width: 0 1 0 0;"
        );

        VBox logo = new VBox(2);
        logo.setPadding(new Insets(20, 16, 16, 16));
        logo.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");
        Label logoName = new Label("KlinikaDB");
        logoName.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");
        Label logoSub = new Label("Clinical Research System");
        logoSub.setStyle("-fx-font-size: 11; -fx-text-fill: #888;");
        logo.getChildren().addAll(logoName, logoSub);

        VBox nav = new VBox(2);
        nav.setPadding(new Insets(12, 8, 12, 8));
        VBox.setVgrow(nav, Priority.ALWAYS);

        Label expLabel = createNavSectionLabel("EXPERIMENTS");
        Button btnExperiments = createNavButton("All experiments", "●");
        Button btnStatus = createNavButton("Change status", "✎");

        Label sessLabel = createNavSectionLabel("SESSIONS");
        sessLabel.setPadding(new Insets(16, 8, 4, 8));
        Button btnSessions = createNavButton("Delete session", "✕");

        btnExperiments.setOnAction(e -> {
            resetNavButtons(btnExperiments, btnStatus, btnSessions);
            setNavActive(btnExperiments);
            showExperiments();
        });
        btnStatus.setOnAction(e -> {
            resetNavButtons(btnExperiments, btnStatus, btnSessions);
            setNavActive(btnStatus);
            showChangeStatus();
        });
        btnSessions.setOnAction(e -> {
            resetNavButtons(btnExperiments, btnStatus, btnSessions);
            setNavActive(btnSessions);
            showDeleteSession();
        });

        setNavActive(btnExperiments);

        nav.getChildren().addAll(expLabel, btnExperiments, btnStatus, sessLabel, btnSessions);

        VBox footer = new VBox(4);
        footer.setPadding(new Insets(12, 16, 16, 16));
        footer.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 1 0 0 0;");

        HBox userRow = new HBox(10);
        userRow.setAlignment(Pos.CENTER_LEFT);

        StackPane avatar = new StackPane();
        avatar.setPrefSize(30, 30);
        avatar.setStyle(
                "-fx-background-color: #E1F5EE;" +
                        "-fx-background-radius: 15;"
        );
        Label avatarLabel = new Label("DR");
        avatarLabel.setStyle("-fx-font-size: 11; -fx-font-weight: bold; -fx-text-fill: #0F6E56;");
        avatar.getChildren().add(avatarLabel);

        VBox userInfo = new VBox(1);
        Label userName = new Label(username);
        userName.setStyle("-fx-font-size: 13; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");
        Label userRole = new Label("Researcher");
        userRole.setStyle("-fx-font-size: 11; -fx-text-fill: #888;");
        userInfo.getChildren().addAll(userName, userRole);

        userRow.getChildren().addAll(avatar, userInfo);
        footer.getChildren().add(userRow);

        sidebar.getChildren().addAll(logo, nav, footer);
        return sidebar;
    }

    private Label createNavSectionLabel(String text) {
        Label lbl = new Label(text);
        lbl.setPadding(new Insets(4, 8, 4, 8));
        lbl.setStyle("-fx-font-size: 10; -fx-text-fill: #aaa; -fx-font-weight: bold;");
        return lbl;
    }

    private Button createNavButton(String text, String icon) {
        Button btn = new Button(icon + "  " + text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(9, 10, 9, 10));
        btn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-background-radius: 8;" +
                        "-fx-font-size: 13;" +
                        "-fx-text-fill: #666;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-width: 0;"
        );
        btn.setOnMouseEntered(e -> {
            if (!btn.getStyle().contains("#E1F5EE")) {
                btn.setStyle(
                        "-fx-background-color: white;" +
                                "-fx-background-radius: 8;" +
                                "-fx-font-size: 13;" +
                                "-fx-text-fill: #1a1a1a;" +
                                "-fx-cursor: hand;" +
                                "-fx-border-width: 0;"
                );
            }
        });
        btn.setOnMouseExited(e -> {
            if (!btn.getStyle().contains("#E1F5EE")) {
                btn.setStyle(
                        "-fx-background-color: transparent;" +
                                "-fx-background-radius: 8;" +
                                "-fx-font-size: 13;" +
                                "-fx-text-fill: #666;" +
                                "-fx-cursor: hand;" +
                                "-fx-border-width: 0;"
                );
            }
        });
        return btn;
    }

    private void setNavActive(Button btn) {
        btn.setStyle(
                "-fx-background-color: #E1F5EE;" +
                        "-fx-background-radius: 8;" +
                        "-fx-font-size: 13;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #0F6E56;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-width: 0;"
        );
    }

    private void resetNavButtons(Button... buttons) {
        for (Button btn : buttons) {
            btn.setStyle(
                    "-fx-background-color: transparent;" +
                            "-fx-background-radius: 8;" +
                            "-fx-font-size: 13;" +
                            "-fx-text-fill: #666;" +
                            "-fx-cursor: hand;" +
                            "-fx-border-width: 0;"
            );
        }
    }

    // ───────────────────────── forme ─────────────────────────

    private void showExperiments() {
        content.getChildren().clear();

        Label title = new Label("All experiments");
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");
        Label subtitle = new Label("Overview of planned and completed experiments");
        subtitle.setStyle("-fx-font-size: 13; -fx-text-fill: #888;");

        TableView<Istrazivanje> table = new TableView<>();
        table.setStyle("-fx-font-size: 13;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Istrazivanje, String> colNaziv = new TableColumn<>("Name");
        colNaziv.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getNaziv()));

        TableColumn<Istrazivanje, String> colProtokol = new TableColumn<>("Protocol ID");
        colProtokol.setCellValueFactory(data ->
                new SimpleStringProperty(String.valueOf(data.getValue().getProtokolId())));

        table.getColumns().addAll(colNaziv, colProtokol);
        List<Istrazivanje> lista = Istrazivanje.readAll(connection);
        table.getItems().addAll(lista);

        VBox.setVgrow(table, Priority.ALWAYS);
        VBox.setMargin(title, new Insets(0, 0, 4, 0));
        VBox.setMargin(subtitle, new Insets(0, 0, 16, 0));

        content.getChildren().addAll(title, subtitle, table);
    }

    private void showChangeStatus() {
        content.getChildren().clear();

        Label title = new Label("Change experiment status");
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");
        Label subtitle = new Label("Select an experiment and update its status");
        subtitle.setStyle("-fx-font-size: 13; -fx-text-fill: #888;");

        VBox form = new VBox(12);
        form.setMaxWidth(400);
        VBox.setMargin(form, new Insets(16, 0, 0, 0));

        Label expLabel = createFormLabel("Experiment");
        ComboBox<String> expCombo = new ComboBox<>();
        expCombo.getItems().addAll("Drug trial phase 1", "Therapy efficacy", "Intervention study");
        expCombo.setPromptText("Select experiment");
        expCombo.setMaxWidth(Double.MAX_VALUE);
        styleComboBox(expCombo);

        Label statusLabel = createFormLabel("New status");
        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("Planned", "Started", "Cancelled", "Completed successfully", "Completed unsuccessfully");
        statusCombo.setPromptText("Select status");
        statusCombo.setMaxWidth(Double.MAX_VALUE);
        styleComboBox(statusCombo);

        Label message = new Label();
        message.setWrapText(true);

        Button saveBtn = createPrimaryButton("Save changes");
        saveBtn.setOnAction(e -> {
            if (expCombo.getValue() == null || statusCombo.getValue() == null) {
                message.setStyle("-fx-font-size: 12; -fx-text-fill: #e53935;");
                message.setText("Please select both experiment and status.");
                return;
            }
            message.setStyle("-fx-font-size: 12; -fx-text-fill: #1D9E75;");
            message.setText("Status updated successfully.");
            // TODO: update u bazi
        });

        form.getChildren().addAll(expLabel, expCombo, statusLabel, statusCombo, saveBtn, message);

        VBox.setMargin(title, new Insets(0, 0, 4, 0));
        VBox.setMargin(subtitle, new Insets(0, 0, 0, 0));
        content.getChildren().addAll(title, subtitle, form);
    }

    private void showDeleteSession() {
        content.getChildren().clear();

        Label title = new Label("Delete session");
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");
        Label subtitle = new Label("Only sessions from experiments you participate in can be deleted");
        subtitle.setStyle("-fx-font-size: 13; -fx-text-fill: #888;");

        TableView<String[]> table = createTable(
                new String[]{"Date", "Start", "End", "Experiment"},
                new String[][]{
                        {"2026-05-10", "09:00", "11:00", "Drug trial phase 1"},
                        {"2026-05-12", "13:00", "15:30", "Therapy efficacy"}
                }
        );

        VBox.setVgrow(table, Priority.ALWAYS);
        VBox.setMargin(title, new Insets(0, 0, 4, 0));
        VBox.setMargin(subtitle, new Insets(0, 0, 16, 0));

        content.getChildren().addAll(title, subtitle, table);
        // TODO: ucitati sesije iz baze i dodati dugme za brisanje
    }

    // ───────────────────────── helper metode ─────────────────────────

    private TableView<String[]> createTable(String[] columns, String[][] rows) {
        TableView<String[]> table = new TableView<>();
        table.setStyle("-fx-font-size: 13;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        for (int i = 0; i < columns.length; i++) {
            final int idx = i;
            TableColumn<String[], String> col = new TableColumn<>(columns[i]);
            col.setCellValueFactory(data ->
                    new javafx.beans.property.SimpleStringProperty(data.getValue()[idx]));
            table.getColumns().add(col);
        }

        for (String[] row : rows) {
            table.getItems().add(row);
        }

        return table;
    }

    private Label createFormLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-font-size: 12; -fx-text-fill: #555; -fx-font-weight: bold;");
        return lbl;
    }

    private void styleComboBox(ComboBox<String> combo) {
        combo.setStyle(
                "-fx-background-color: #f9f9f9;" +
                        "-fx-border-color: #e0e0e0;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-font-size: 13;"
        );
        combo.setPrefHeight(40);
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