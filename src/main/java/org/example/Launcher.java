package org.example;

public class Launcher {

    private static Launcher launcher;

    public static Launcher getLauncher() {
        if (launcher == null) {
            synchronized (Launcher.class) {
                if (launcher == null)
                    launcher = new Launcher();
            }
        }
        return launcher;
    }

    private Launcher() {}

    void launch(String... args) {
        this.setUp(args);
        this.work(args);
        this.clean(args);
    }

    private void setUp(String... args) {
        // args[0] je putanja do database.cfg
        Config.loadProperties(args[0]);
        Config.connect(
                Config.getPropertyValue("host", ""),
                Config.getPropertyValue("port", ""),
                Config.getPropertyValue("db", ""),
                Config.getPropertyValue("user", ""),
                Config.getPropertyValue("password", "")
        );
    }

    private void work(String... args) {
        App.launch(App.class, args);
    }

    private void clean(String... args) {
        Config.disconnect();
    }
}