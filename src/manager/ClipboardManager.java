package manager;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contains the way to get text from the clipboard and how retrieve it.
 *
 * Created by julian on 7/14/16.
 */
public class ClipboardManager {
    private static ClipboardManager instance;

    private boolean listen = true;
    private List<String> clipboardContainer = new ArrayList<>();
    private List<String> errors = new ArrayList<>();

    public static ClipboardManager getInstance() {
        if (instance == null) {
            instance = new ClipboardManager();
        }
        return instance;
    }

    public void runClipboardListener() {
        while(listen) {
            try {
                Thread thread = new Thread(new ClipboardRunnable());
                thread.run();
                Thread.sleep(1000);
            } catch (Exception e) { }
        }
    }

    private String formatText(String clipboardText) {
        DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        String formattedText = "[" + dateFormat.format(new Date()) + "]"
                + Constants.SEPARATOR + "\n"
                + clipboardText + "\n";
        return formattedText;
    }

    private void printClipboardText(String clipboardText) throws IOException {
        String textToWrite = formatText(clipboardText);
        Path filePath = Paths.get(Constants.FILE_PATH);

        if (Files.exists(filePath)) {
            Files.write(filePath, textToWrite.getBytes(), StandardOpenOption.APPEND);
        } else {
            Files.write(filePath, textToWrite.getBytes());
        }

        System.out.print(textToWrite);
    }

    private void saveClipboardText(String clipboardText) throws IOException {
        if (!clipboardText.isEmpty() && !clipboardContainer.contains(clipboardText)) {
            printClipboardText(clipboardText);
            clipboardContainer.add(clipboardText);
        }
    }

    private void saveErrorText(String errorText) {
        errors.add(errorText);
    }

    private class ClipboardRunnable implements Runnable {
        @Override
        public void run() {
            try {
                String clipboardText = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor);
                saveClipboardText(clipboardText.trim());
            } catch (Exception e) { }
        }
    }
}
