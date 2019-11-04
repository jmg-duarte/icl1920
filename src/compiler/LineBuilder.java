package compiler;

import java.io.IOException;
import java.nio.file.*;

public class LineBuilder {
    private final StringBuilder sb = new StringBuilder();

    private static final String HEADER =
            ".class public Main\n" +
            ".super java/lang/Object\n" +
            "\n"+
            ";\n"+
            "; standard initializer\n"+ ".method public <init>()V\n" +
            "   aload_0\n" +
            "   invokenonvirtual java/lang/Object/<init>()V\n"+
            "   return\n"+
            ".end method\n"+
            "\n"+
            ".method public static main([Ljava/lang/String;)V\n"+
            "       ; set limits used by this method\n"+
            "       .limit locals 10 \n"+
            "       .limit stack 256\n"+
            "\n"+
            "       ; setup local variables:\n"+
            "\n"+
            "       ;    1 - the PrintStream object held in java.lang.System.out\n"+
            "       getstatic java/lang/System/out Ljava/io/PrintStream;\n"+
            "\n"+
            "       ; place your bytecodes between START and END\n"+
            "       ; START ======================================================================\n"+
            "\n";

    private static final String FOOTER = "\n" +
            "       ; END ====================================================================\n"+
            "\n" +
            "       ; convert to String;\n"+
            "       invokestatic java/lang/String/valueOf(I)Ljava/lang/String;\n"+
            "       ; call println \n"+
            "       invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n"+
            "\n"+
            "       return\n"+
            "\n"+
            ".end method\n"+
            "\n";

    public void append(String line) {
        sb.append(line);
    }

    public void append(String... lines) {
        for (String l : lines) {
            this.appendLine(l);
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

    public void writeToFile(String filename) throws IOException {
        Path p = Paths.get(filename);
        System.out.println(sb.toString());
        Files.write(p,(HEADER+sb.toString()+FOOTER).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        sb.delete(0,sb.length()-1);
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
