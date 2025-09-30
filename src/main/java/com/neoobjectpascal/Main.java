package com.neoobjectpascal;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: java com.neoobjectpascal.Main <file.npas>");
            return;
        }

        String filePath = args[0];
        CharStream input = CharStreams.fromFileName(filePath);
        NeoObjectPascalLexer lexer = new NeoObjectPascalLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        NeoObjectPascalParser parser = new NeoObjectPascalParser(tokens);
        ParseTree tree = parser.program();

        // Obter o diretório base do arquivo
        String baseDirectory = getBaseDirectory(filePath);
        Interpreter visitor = new Interpreter(baseDirectory);
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

