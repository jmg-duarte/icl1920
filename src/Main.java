import ast.ASTNode;
import compiler.CoreCompiler;
import env.Environment;
import parser.Parser;
import types.IType;
import value.IValue;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            return;
        }
        switch (args[0]) {
            case "-r": {
                repl();
                break;
            }
            case "-c": {
                compile(args[1]);
                break;
            }
            case "-t": {
                typecheck();
                break;
            }
        }
    }

    private static void compile(String arg) throws Exception {
        deleteDirectoryStream(Paths.get("./jout/"));
        deleteDirectoryStream(Paths.get("./classes/"));
        new Parser(new FileInputStream(arg));
        ASTNode exp = Parser.Start();
        CoreCompiler c = new CoreCompiler();
        Environment<IType> globalScope = new Environment<>();
        exp.typecheck(globalScope);
        c.compile(exp);
        try (Stream<Path> walk = Files.walk(Paths.get("./jout/"))) {
            List<String> result = walk.filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());

            for (String path : result) {
                run(String.format("java -jar jasmin.jar %s -d ./classes/", path), ".");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        run("java Main", "./classes");
    }

    private static void run(String command, String cwd) throws IOException {

        Process proc = Runtime.getRuntime().exec(command, new String[]{}, new File(cwd));
        InputStream stdout = proc.getInputStream();
        InputStream stderr = proc.getErrorStream();

        String line;
        BufferedReader out = new BufferedReader(new InputStreamReader(stdout));
        while ((line = out.readLine()) != null) {
            System.out.println(line);
        }

        BufferedReader err = new BufferedReader(new InputStreamReader(stderr));
        while ((line = err.readLine()) != null) {
            System.err.println(line);
        }
    }

    private static void repl() {
        new Parser(System.in);
        ASTNode exp;
        Environment<IValue> globalScope = new Environment<>();

        while (true) {
            try {
                exp = Parser.Start();
                Environment<IType> typeEnvironment = new Environment<>();
                exp.typecheck(typeEnvironment);
                System.out.println(exp.eval(globalScope));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Syntax Error!");
                Parser.ReInit(System.in);
            }
        }
    }

    private static void typecheck() {
        new Parser(System.in);
        ASTNode exp;
        Environment<IType> globalScope = new Environment<>();
        try {
            exp = Parser.Start();
            System.out.println(exp.typecheck(globalScope));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Syntax Error!");
            Parser.ReInit(System.in);
        }
    }

    private static void deleteDirectoryStream(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }
}
