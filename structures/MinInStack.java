import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class MinInStack {
    public static void main(String[] args) {
        try {
            MyScanner in = new MyScanner(System.in);
            try {
                int n  = in.nextInt();
                MyStack stack = new MyStack();
                for (int i = 0; i < n; i++) {
                    int operation = in.nextInt();
                    if (operation == 1) {
                        stack.push(in.nextInt());
                    } else if (operation == 2) {
                        stack.pop();
                    } else {
                        System.out.println(stack.min());
                    }
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            System.err.println("Error I/O operation in read functions: " + Arrays.toString(e.getStackTrace()));
    }

    }

    public static class MyScanner {
        private static final int BUFFER_SIZE = 2048;
        private final BufferedReader reader;
        private char[] buffer = new char[BUFFER_SIZE];
        private int readPointer = 0;
        private int lookupPointer = 0;
        private int readedCharNum;
        private Cache cache = null;

        public MyScanner(InputStream input) throws UnsupportedEncodingException {
            this.reader = new BufferedReader(
                    new InputStreamReader(
                            input,
                            "UTF8"
                    )
            );
        }

        public MyScanner(String input) throws UnsupportedEncodingException {
            InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
            this.reader = new BufferedReader(
                    new InputStreamReader(
                            stream,
                            "UTF8"
                    )
            );
        }

        public String nextWord() throws IOException {
            return nextItem(false, Pattern.WORD);
        }

        public boolean hasNextWord() throws IOException {
            return !nextItem(true, Pattern.WORD).isEmpty();
        }

        public String nextWordWithDigit() throws IOException {
            return nextItem(false, Pattern.WORDDIGIT);
        }

        public boolean hasNextWordWithDigit() throws IOException {
            return !nextItem(true, Pattern.WORDDIGIT).isEmpty();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(nextItem(false, Pattern.INTEGER));
        }

        public boolean hasNextInt() throws IOException {
            return isInteger(nextItem(true, Pattern.INTEGER));
        }

        public long nextLong() throws IOException {
            return Long.parseLong(nextItem(false, Pattern.INTEGER));
        }

        public boolean hasNexLong() throws IOException {
            return hasNextInt();
        }

        public boolean hasNext() throws IOException {
            readInBufferIfEmpty();
            return readedCharNum > lookupPointer;
        }

        public String nextLine() throws IOException {
            int len = 0;
            while (hasNext()) {
                char c = nextChar();
                if (c == 10 || c == 13) {
                    break;
                }
                len++;
            }
            String line = new String(buffer,readPointer, len);

            readPointer = lookupPointer;
            return line;
        }

        public void close() throws IOException {
            reader.close();
        }

        private String nextItem(boolean isLookup, Pattern pattern) throws IOException {
            String item;
            if (cache != null && cache.getPattern() == pattern) {
                item = cache.getValue();
                if (isLookup) {
                    lookupPointer = readPointer;
                } else {
                    readPointer = cache.getCachePointer();
                    lookupPointer = cache.getCachePointer();
                    cache = null;
                }
                return item;
            }
            boolean isPreviousSpace = true;
            int len = 0;
            while (hasNext()) {
                char c = nextChar();
                if (!isSplitChar(c, pattern)) {
                    if (isPreviousSpace) {
                        isPreviousSpace = false;
                    }
                    len++;
                } else if (!isPreviousSpace){
                    break;
                } else {
                    readPointer++;
                }
            }
            if (len == 0) {
                return "";
            }
            item = new String(buffer,readPointer, len);
            if (isLookup) {
                cache = new Cache(pattern, item, lookupPointer);
                lookupPointer = readPointer;
            } else {
                readPointer = lookupPointer;
            }
            return item;
        }

        private char nextChar() throws IOException {
            readInBufferIfEmpty();
            return buffer[lookupPointer++];
        }

        private void readInBufferIfEmpty() throws IOException {
            if (lookupPointer >= readedCharNum) {
                readInBuffer();
            }
        }

        private void readInBuffer() throws IOException {
            if (readPointer < lookupPointer) {
                int offset = buffer.length - readPointer;
                readAndCopyBuffer(offset);
                lookupPointer = offset;
                readPointer = 0;
                if (readedCharNum < 0) {
                    readedCharNum = offset;
                } else {
                    readedCharNum += offset;
                }
            } else {
                buffer = new char[BUFFER_SIZE];
                readedCharNum = reader.read(buffer);
                readPointer = 0;
                lookupPointer = 0;
            }
        }

        private void readAndCopyBuffer(int offset) throws IOException {
            char[] newBuffer = new char[offset + BUFFER_SIZE];
            readedCharNum = reader.read(newBuffer, offset, BUFFER_SIZE);
            System.arraycopy(buffer, readPointer, newBuffer, 0, offset);
            buffer = newBuffer;
        }

        private boolean isInteger(String s) {
            if (s.isEmpty()) {
                return false;
            }
            int startIndex = 0;
            if (s.charAt(0) == '-') {
                if (s.length() == 1) {
                    return false;
                }
                startIndex = 1;
            }
            for (int i = startIndex; i < s.length(); i++) {
                if (Character.digit(s.charAt(i), 10) < 0) {
                    return false;
                }
            }
            return true;
        }

        private boolean isSplitChar(char character, Pattern pattern) {
            if (pattern == Pattern.WORD) {
                return isSplitCharForWord(character);
            } else if (pattern == Pattern.WORDDIGIT) {
                return isSplitCharForWordWithDigit(character);
            }  else if (pattern == Pattern.INTEGER) {
                return isSplitCharForInt(character);
            } else {
                throw new IllegalArgumentException("not supported argument: " + pattern);
            }
        }

        private boolean isSplitCharForWord(char character) {
            return !Character.isLetter(character) && character != '\'' &&
                    Character.getType(character) != Character.DASH_PUNCTUATION;
        }

        private boolean isSplitCharForWordWithDigit(char character) {
            return !Character.isLetterOrDigit(character) && character != '\'' &&
                    Character.getType(character) != Character.DASH_PUNCTUATION;
        }

        private boolean isSplitCharForInt(char character) {
            return !Character.isDigit(character) &&
                    Character.getType(character) != Character.DASH_PUNCTUATION;
        }

        private enum Pattern {
            WORD,
            INTEGER,
            WORDDIGIT
        }

        private static class Cache {
            private final Pattern pattern;
            private final String value;
            private final int cachePointer;

            public Cache(Pattern pattern, String value, int cachePointer) {
                this.pattern = pattern;
                this.value = value;
                this.cachePointer = cachePointer;
            }

            public Pattern getPattern() {
                return pattern;
            }

            public String getValue() {
                return value;
            }

            public int getCachePointer() {
                return cachePointer;
            }
        }
    }

    public static class MyStack {
        private List<IntWithMin> list = new ArrayList<>();

        public void push(int el) {
            if (list.isEmpty()) {
                list.add(new IntWithMin(el, el));
            } else {
                list.add(new IntWithMin(el, Math.min(el, min())));
            }
        }

        public void pop() {
            list.removeLast();
        }

        public int min() {
            return list.getLast().minVal();
        }

        private record IntWithMin(int value, int minVal) {
        }
    }
}
