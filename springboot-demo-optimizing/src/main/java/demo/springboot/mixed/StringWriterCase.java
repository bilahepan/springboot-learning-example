package demo.springboot.mixed;

import java.io.StringWriter;

/**
 * test case
 */
public class StringWriterCase {
    public static void main(String[] args) {
        StringWriter writer = new StringWriter();
        writer.append("\n --- An unexpected error occurred in the server ---\n\n");
        writer.append("Stacktrace:\n\n");
        System.out.println(writer.toString());
    }
}
