package compiler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class LineBuilder {
    private static final String HEADER =
            ".class public Main\n" +
                    ".super java/lang/Object\n" +
                    "\n" +
                    ".method public <init>()V\n" +
                    "aload_0\n" +
                    "invokenonvirtual java/lang/Object/<init>()V\n" +
                    "return\n" +
                    ".end method\n" +
                    "\n" +
                    ".method public static main([Ljava/lang/String;)V\n\n" +
                    ".limit locals 10 \n" +
                    ".limit stack 256\n" +
                    "\n" /*+
                    "getstatic java/lang/System/out Ljava/io/PrintStream;\n" +
                    "\n"*/ + "aconst_null\n" +
                    "astore 4\n";
    private static final String FOOTER = "\n" +
            //  "invokestatic java/lang/String/valueOf(I)Ljava/lang/String;\n" +
            //  "invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n" +
            "return\n" +
            ".end method\n";
    private final StringBuilder sb = new StringBuilder();

    public void append(String line) {
        sb.append(line);
    }

    public void append(String... lines) {
        for (String l : lines) {
            this.append(l);
        }
    }

    public void append(Assembler assembly) {
        this.appendLine(assembly.toString());
    }

    public void append(Assembler... assemblies) {
        for (Assembler assembly : assemblies) {
            this.appendLine(assembly.toString());
        }
    }

    public void appendLine(String line) {
        sb.append(line);
        sb.append("\n");
    }

    public void appendLine(String line, String comment) {
        sb.append(line);
        sb.append("\t; ");
        sb.append(comment);
        sb.append("\n");
    }

    public void addHeader() {
        sb.insert(0, HEADER);
    }

    public void addFooter() {
        sb.append(FOOTER);
    }

    public void writeToFile(String filename) throws IOException {
        new File("./jout").mkdirs();
        Path p = Paths.get("./jout/" + filename);
        // System.out.println(sb.toString());
        Files.write(p, sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        sb.delete(0, sb.length() - 1);
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
