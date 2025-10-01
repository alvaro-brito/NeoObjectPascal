package com.neoobjectpascal;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.BaseErrorListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: java com.neoobjectpascal.Main [options] <file.npas>");
            System.out.println("Options:");
            System.out.println("  --no-warnings, -q    Suppress parsing warnings");
            System.out.println("  --help, -h           Show this help message");
            return;
        }

        // Parse command line arguments
        List<String> argList = Arrays.asList(args);
        boolean suppressWarnings = argList.contains("--no-warnings") || argList.contains("-q");
        boolean showHelp = argList.contains("--help") || argList.contains("-h");
        
        if (showHelp) {
            System.out.println("NeoObjectPascal Interpreter");
            System.out.println("Usage: java com.neoobjectpascal.Main [options] <file.npas>");
            System.out.println();
            System.out.println("Options:");
            System.out.println("  --no-warnings, -q    Suppress parsing warnings and errors");
            System.out.println("  --help, -h           Show this help message");
            System.out.println();
            System.out.println("Examples:");
            System.out.println("  java -jar neoobjectpascal.jar example.npas");
            System.out.println("  java -jar neoobjectpascal.jar --no-warnings example.npas");
            System.out.println("  java -jar neoobjectpascal.jar -q example.npas");
            return;
        }

        // Find the file path (last non-option argument)
        String filePath = null;
        for (String arg : args) {
            if (!arg.startsWith("-")) {
                filePath = arg;
            }
        }
        
        if (filePath == null) {
            System.err.println("Error: No input file specified");
            System.err.println("Use --help for usage information");
            return;
        }

        CharStream input = CharStreams.fromFileName(filePath);
        NeoObjectPascalLexer lexer = new NeoObjectPascalLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        NeoObjectPascalParser parser = new NeoObjectPascalParser(tokens);
        
        // Suppress warnings if requested
        if (suppressWarnings) {
            lexer.removeErrorListeners();
            parser.removeErrorListeners();
            // Add a silent error listener that does nothing
            BaseErrorListener silentListener = new BaseErrorListener();
            lexer.addErrorListener(silentListener);
            parser.addErrorListener(silentListener);
        }
        
        ParseTree tree = parser.program();

        // Obter o diretório base do arquivo
        String baseDirectory = getBaseDirectory(filePath);
        Interpreter visitor = new Interpreter(baseDirectory, suppressWarnings);
        visitor.visit(tree);
    }
    
    private static String getBaseDirectory(String filePath) {
        int lastSlash = Math.max(filePath.lastIndexOf('/'), filePath.lastIndexOf('\\'));
        if (lastSlash >= 0) {
            return filePath.substring(0, lastSlash + 1);
        }
        return "./";
    }
}

