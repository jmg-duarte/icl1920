package compiler;

import java.io.IOException;
import java.nio.file.*;

public class LineBuilder {
    private final StringBuilder sb = new StringBuilder();

    public void append(String line) {
        sb.append(line);
    }

    public void append(String... lines) {
        for (String l : lines) {
            sb.append(l);
        }
    }

    public void append(Assembler assembly) {
        sb.append(assembly.toString());
    }

    public void append(Assembler... assemblies) {
        for (Assembler assembly : assemblies) {
            sb.append(assembly.toString());
        }
    }

    public void appendLine(String line) {
        sb.append(line);
        sb.append("\n");
    }

    public void writeToFile(String filename) throws IOException {
        Path p = Paths.get(filename);
        Files.write(p, sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
