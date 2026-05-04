package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.view.LoginView;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        new LoginView(primaryStage).show();
    }
}