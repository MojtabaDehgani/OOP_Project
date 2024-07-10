package general;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class General {
    static Random random = new Random();
    static String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static String generateRandomString(int length) {
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }
        return result.toString();
    }

    public static String[] createCaptcha() {
        int x = random.nextInt(10);
        int y = random.nextInt(10);

        String captcha = String.format("%d plus %d", x, y);
        String answer = String.format("%d", x + y);

        return new String[]{captcha, answer};
    }

    public static void copyToClipboard(String text) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, null);
    }



}
