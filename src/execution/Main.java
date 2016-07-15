package execution;

import manager.ClipboardManager;

/**
 * Created by julian on 7/14/16.
 */
public class Main {

    public static void main(String args[]) {
        System.out.println("*** Prosta Clipboard Listener ***\n");
        ClipboardManager.getInstance().runClipboardListener();
    }
}
