package edu.wgu.d387_sample_code;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * B1: Displays the Landon Hotel welcome message in English and French,
 * using a separate thread for each language.
 */
public class WelcomeMessage implements Runnable {

    private final Locale locale;

    public WelcomeMessage(Locale locale) {
        this.locale = locale;
    }

    @Override
    public void run() {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        System.out.println("[" + Thread.currentThread().getName() + "] "
                + locale.getDisplayLanguage(locale) + ": "
                + bundle.getString("welcome.message"));
    }

    /** Starts one thread per supported language. */
    public static void displayWelcomeMessages() throws InterruptedException {
        Thread englishThread = new Thread(new WelcomeMessage(Locale.ENGLISH), "english-thread");
        Thread frenchThread  = new Thread(new WelcomeMessage(Locale.FRENCH),  "french-thread");

        englishThread.start();
        frenchThread.start();

        englishThread.join();
        frenchThread.join();
    }
}