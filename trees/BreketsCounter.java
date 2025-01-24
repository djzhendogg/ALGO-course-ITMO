package tree;

import java.io.*;
import java.util.ArrayList;

public class BreketsCounter {
    public static void main(String[] args) {
        try {
            try (Reader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"))) {
                char[] buffer = new char[1024];
                int read = reader.read(buffer);
                ArrayList<Character> chars = new ArrayList<>();
                char lastChar = 0;
                boolean good = true;
                String openBrk = "({[";
                while (read > 0 && good) {
                    for (char character : buffer) {
                        if (openBrk.contains(String.valueOf(character))) {
                            chars.add(character);
                            lastChar = character;
                        } else if (
                                (character == ')' && lastChar == '(') ||
                                        (character == '}' && lastChar == '{') ||
                                        (character == ']' && lastChar == '[')
                        ) {
                            chars.removeLast();
                            if (!chars.isEmpty()) {
                                lastChar = chars.getLast();
                            } else {
                                lastChar = 0;
                            }
                        } else if (Character.getType(character) == Character.CONTROL) {
                            break;
                        } else {
                            good = false;
                            break;
                        }
                    }
                    read = reader.read(buffer);
                }
                if (good && chars.isEmpty()) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
