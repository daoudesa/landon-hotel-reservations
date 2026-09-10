package edu.wgu.d387_sample_code;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * B1: Loads the Landon Hotel welcome message in English and French,
 * using a separate thread for each language.
 */
public class WelcomeMessage implements Runnable {

    private final Locale locale;
    private final List<String> results;

    public WelcomeMessage(Locale locale, List<String> results) {
        this.locale = locale;
        this.results = results;
    }

    @Override
    public void run() {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        results.add(bundle.getString("welcome.message"));
        System.out.println("[" + Thread.currentThread().getName() + "] "
                + locale.getDisplayLanguage(locale) + ": "
                + bundle.getString("welcome.message"));
    }

    /** Starts one thread per supported language and returns both messages. */
    public static List<String> getWelcomeMessages() throws InterruptedException {
        List<String> messages = new CopyOnWriteArrayList<>();

        Thread englishThread = new Thread(new WelcomeMessage(Locale.ENGLISH, messages), "english-thread");
        Thread frenchThread  = new Thread(new WelcomeMessage(Locale.FRENCH,  messages), "french-thread");

        englishThread.start();
        frenchThread.start();

        englishThread.join();
        frenchThread.join();

        return new ArrayList<>(messages);
    }

    /** Console output, retained for demonstration at startup. */
    public static void displayWelcomeMessages() throws InterruptedException {
        getWelcomeMessages();
    }
}