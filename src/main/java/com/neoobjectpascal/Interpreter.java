package com.neoobjectpascal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Interpreter extends NeoObjectPascalParserBaseVisitor<Object> {

    private SymbolTable symbolTable = new SymbolTable();
    private final Map<String, Function> functions = new HashMap<>();
    private final String baseDirectory;
    private Scanner inputScanner;
    private boolean suppressWarnings;
    
    public Interpreter() {
        this("./", false);
    }
    
    public Interpreter(String baseDirectory) {
        this(baseDirectory, false);
    }
    
    public Interpreter(String baseDirectory, boolean suppressWarnings) {
        this.baseDirectory = baseDirectory;
        this.suppressWarnings = suppressWarnings;
        this.inputScanner = new Scanner(System.in);
    }

    @Override
    public Object visitProgram(NeoObjectPascalParser.ProgramContext ctx) {
        if (ctx.usesClause() != null) {
            visit(ctx.usesClause());
        }
        for (NeoObjectPascalParser.DeclarationContext decl : ctx.declaration()) {
            visit(decl);
        }
        if (ctx.block() != null) {
            visit(ctx.block());
        }
        return null;
    }

    @Override
    public Object visitBlock(NeoObjectPascalParser.BlockContext ctx) {
        for (NeoObjectPascalParser.StatementContext stmt : ctx.statement()) {
            visit(stmt);
        }
        return null;
    }

    @Override
    public Object visitFunctionDeclaration(NeoObjectPascalParser.FunctionDeclarationContext ctx) {
        String functionName = ctx.identifier().getText();
        List<String> parameters = new ArrayList<>();
        if (ctx.parameterList() != null) {
            for (NeoObjectPascalParser.ParameterContext param : ctx.parameterList().parameter()) {
                parameters.add(param.identifier().getText());
            }
        }
        Function function = new Function(functionName, parameters, ctx.block());
        functions.put(functionName, function);
        return null;
    }

    @Override
    public Object visitCall(NeoObjectPascalParser.CallContext ctx) {
        String functionName = ctx.identifier().getText();
        Function function = functions.get(functionName);
        if (function != null) {
            try {
                SymbolTable newScope = new SymbolTable(symbolTable);
                if (ctx.expressionList() != null) {
                    for (int i = 0; i < function.getParameters().size() && i < ctx.expressionList().expression().size(); i++) {
                        String paramName = function.getParameters().get(i);
                        Object value = visit(ctx.expressionList().expression(i));
                        newScope.put(paramName, new Symbol(paramName, null, value));
                    }
                }
                SymbolTable oldScope = symbolTable;
                symbolTable = newScope;
                visit(function.getBody());
                symbolTable = oldScope;
            } catch (ReturnValue returnValue) {
                return returnValue.value;
            }
        }
        return null;
    }

    @Override
    public Object visitReturnStatement(NeoObjectPascalParser.ReturnStatementContext ctx) {
        Object value = visit(ctx.expression());
        throw new ReturnValue(value);
    }

    @Override
    public Object visitVariableDeclaration(NeoObjectPascalParser.VariableDeclarationContext ctx) {
        String varName = ctx.identifier().getText();
        String typeName = ctx.type().getText();
        Type type = Type.valueOf(typeName.toUpperCase());
        Symbol symbol = new Symbol(varName, type);
        symbolTable.put(varName, symbol);
        return null;
    }

    @Override
    public Object visitAssignment(NeoObjectPascalParser.AssignmentContext ctx) {
        String varName = ctx.identifier().getText();
        Object value = visit(ctx.expression());
        Symbol symbol = symbolTable.get(varName);
        if (symbol != null) {
            if (symbol.getType() == Type.INTEGER && value instanceof String) {
                symbol.setValue(Integer.parseInt((String) value));
            } else if (symbol.getType() == Type.REAL && value instanceof String) {
                symbol.setValue(Double.parseDouble((String) value));
            } else if (symbol.getType() == Type.BOOLEAN && value instanceof String) {
                symbol.setValue(Boolean.parseBoolean((String) value));
            } else {
                symbol.setValue(value);
            }
        }
        return null;
    }

    @Override
    public Object visitWhileStatement(NeoObjectPascalParser.WhileStatementContext ctx) {
        int maxIterations = 1000; // Proteção contra loop infinito
        int iterations = 0;
        while (isTrue(visit(ctx.expression())) && iterations < maxIterations) {
            visit(ctx.statement());
            iterations++;
        }
        return null;
    }

    @Override
    public Object visitForStatement(NeoObjectPascalParser.ForStatementContext ctx) {
        String varName = ctx.identifier().getText();
        int start = (Integer) visit(ctx.expression(0));
        int end = (Integer) visit(ctx.expression(1));
        
        Symbol symbol = symbolTable.get(varName);
        if (symbol == null) {
            symbol = new Symbol(varName, Type.INTEGER);
            symbolTable.put(varName, symbol);
        }
        
        for (int i = start; i <= end; i++) {
            symbol.setValue(i);
            visit(ctx.statement());
        }
        return null;
    }

    @Override
    public Object visitIfStatement(NeoObjectPascalParser.IfStatementContext ctx) {
        if (isTrue(visit(ctx.expression()))) {
            visit(ctx.statement(0));
        } else if (ctx.statement().size() > 1) {
            visit(ctx.statement(1));
        }
        return null;
    }

    private boolean isTrue(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof Integer) {
            return (Integer) value != 0;
        }
        return false;
    }

    @Override
    public Object visitWriteLnStatement(NeoObjectPascalParser.WriteLnStatementContext ctx) {
        if (ctx.expressionList() != null) {
            for (NeoObjectPascalParser.ExpressionContext expr : ctx.expressionList().expression()) {
                System.out.print(visit(expr));
            }
        }
        System.out.println();
        return null;
    }

    @Override
    public Object visitReadLnStatement(NeoObjectPascalParser.ReadLnStatementContext ctx) {
        String varName = ctx.identifier().getText();
        Symbol symbol = symbolTable.get(varName);
        
        if (symbol != null) {
            try {
                // Força o flush do output antes de ler
                System.out.flush();
                
                String input = inputScanner.nextLine();
                
                switch (symbol.getType()) {
                    case INTEGER:
                        try {
                            symbol.setValue(Integer.parseInt(input.trim()));
                        } catch (NumberFormatException e) {
                            symbol.setValue(0);
                        }
                        break;
                    case REAL:
                        try {
                            symbol.setValue(Double.parseDouble(input.trim()));
                        } catch (NumberFormatException e) {
                            symbol.setValue(0.0);
                        }
                        break;
                    case BOOLEAN:
                        symbol.setValue(Boolean.parseBoolean(input.trim()));
                        break;
                    default:
                        symbol.setValue(input);
                        break;
                }
            } catch (Exception e) {
                // Define valor padrão em caso de erro (sem entrada disponível)
                switch (symbol.getType()) {
                    case INTEGER:
                        symbol.setValue(0);
                        break;
                    case REAL:
                        symbol.setValue(0.0);
                        break;
                    case BOOLEAN:
                        symbol.setValue(false);
                        break;
                    default:
                        symbol.setValue("");
                        break;
                }
            }
        }
        return null;
    }

    @Override
    public Object visitShowMenuStatement(NeoObjectPascalParser.ShowMenuStatementContext ctx) {
        System.out.println("--- MENU ---");
        for (int i = 0; i < ctx.expressionList().expression().size(); i++) {
            System.out.println((i + 1) + ". " + visit(ctx.expressionList().expression(i)));
        }
        System.out.println("----------");
        return null;
    }

    @Override
    public Object visitExpression(NeoObjectPascalParser.ExpressionContext ctx) {
        if (ctx.javaBlock() != null) {
            return visit(ctx.javaBlock());
        }
        
        if (ctx.primary() != null) {
            return visit(ctx.primary());
        }

        Object left = visit(ctx.expression(0));
        Object right = visit(ctx.expression(1));

        if (ctx.PIPE() != null) {
            if (ctx.expression(1).primary() != null && ctx.expression(1).primary().identifier() != null) {
                String functionName = ctx.expression(1).primary().identifier().getText();
                Function function = functions.get(functionName);
                if (function != null && !function.getParameters().isEmpty()) {
                    try {
                        SymbolTable newScope = new SymbolTable(symbolTable);
                        newScope.put(function.getParameters().get(0), new Symbol(function.getParameters().get(0), null, left));
                        SymbolTable oldScope = symbolTable;
                        symbolTable = newScope;
                        visit(function.getBody());
                        symbolTable = oldScope;
                    } catch (ReturnValue returnValue) {
                        return returnValue.value;
                    }
                }
            }
        } else if (ctx.MUL() != null) {
            return (Integer) left * (Integer) right;
        } else if (ctx.DIV() != null) {
            return (Integer) left / (Integer) right;
        } else if (ctx.ADD() != null) {
            // Se pelo menos um dos operandos for String, faz concatenação
            if (left instanceof String || right instanceof String) {
                return String.valueOf(left) + String.valueOf(right);
            }
            // Caso contrário, faz soma aritmética
            return (Integer) left + (Integer) right;
        } else if (ctx.SUB() != null) {
            return (Integer) left - (Integer) right;
        } else if (ctx.GT() != null) {
            return ((Integer) left).compareTo((Integer) right) > 0;
        } else if (ctx.LT() != null) {
            return ((Integer) left).compareTo((Integer) right) < 0;
        } else if (ctx.GTE() != null) {
            return ((Integer) left).compareTo((Integer) right) >= 0;
        } else if (ctx.LTE() != null) {
            return ((Integer) left).compareTo((Integer) right) <= 0;
        } else if (ctx.EQUAL() != null) {
            return left.equals(right);
        } else if (ctx.NOT_EQUAL() != null) {
            return !left.equals(right);
        }

        return null;
    }

    @Override
    public Object visitPrimary(NeoObjectPascalParser.PrimaryContext ctx) {
        if (ctx.INTEGER() != null) {
            return Integer.parseInt(ctx.INTEGER().getText());
        }
        if (ctx.STRING() != null) {
            String text = ctx.STRING().getText();
            return text.substring(1, text.length() - 1);
        }
        if (ctx.identifier() != null) {
            String name = ctx.identifier().getText();
            if (functions.containsKey(name)) {
                return functions.get(name);
            }
            Symbol symbol = symbolTable.get(name);
            if (symbol != null) {
                return symbol.getValue();
            }
            return null;
        }
        if (ctx.expression() != null) {
            return visit(ctx.expression());
        }
        if (ctx.call() != null) {
            return visit(ctx.call());
        }
        return null;
    }

    @Override
    public Object visitJsonParseStatement(NeoObjectPascalParser.JsonParseStatementContext ctx) {
        String jsonString = (String) visit(ctx.expression());
        String varName = ctx.identifier().getText();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Object value = objectMapper.readValue(jsonString, Object.class);
            Symbol symbol = symbolTable.get(varName);
            if (symbol != null) {
                symbol.setValue(value);
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar JSON: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Object visitCsvParseStatement(NeoObjectPascalParser.CsvParseStatementContext ctx) {
        String csvString = (String) visit(ctx.expression());
        String varName = ctx.identifier().getText();
        try {
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> allRows = reader.readAll();
            Symbol symbol = symbolTable.get(varName);
            if (symbol != null) {
                symbol.setValue(allRows);
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar CSV: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Object visitJavaBlock(NeoObjectPascalParser.JavaBlockContext ctx) {
        try {
            // Extrai o código Java (remove as chaves)
            String javaCode = ctx.JAVA_CODE().getText();
            
            // Coleta os parâmetros
            List<Object> parameters = new ArrayList<>();
            if (ctx.expressionList() != null) {
                for (NeoObjectPascalParser.ExpressionContext expr : ctx.expressionList().expression()) {
                    Object value = visit(expr);
                    parameters.add(value);
                }
            }
            
            // Executa o código Java
            return JavaExecutor.executeJavaCode(javaCode, parameters);
            
        } catch (Exception e) {
            System.err.println("Erro ao executar bloco Java: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Object visitUsesClause(NeoObjectPascalParser.UsesClauseContext ctx) {
        for (NeoObjectPascalParser.ModulePathContext modulePathContext : ctx.modulePath()) {
            String modulePath = getModulePath(modulePathContext);
            
            // Verifica se é uma biblioteca interna
            if (modulePath.startsWith("internal.")) {
                loadInternalLibrary(modulePath);
            } else {
                // Carrega módulo externo (comportamento original)
                String fileName = modulePathToFilePath(modulePath) + ".npas";
                try {
                    CharStream input = CharStreams.fromFileName(fileName);
                    NeoObjectPascalLexer lexer = new NeoObjectPascalLexer(input);
                    CommonTokenStream tokens = new CommonTokenStream(lexer);
                    NeoObjectPascalParser parser = new NeoObjectPascalParser(tokens);
                    ParseTree tree = parser.program();
                    visit(tree);
                } catch (IOException e) {
                    System.err.println("Erro ao carregar módulo " + fileName + ": " + e.getMessage());
                }
            }
        }
        return null;
    }

    private String getModulePath(NeoObjectPascalParser.ModulePathContext ctx) {
        StringBuilder path = new StringBuilder();
        for (int i = 0; i < ctx.IDENTIFIER().size(); i++) {
            if (i > 0) {
                path.append(".");
            }
            path.append(ctx.IDENTIFIER(i).getText());
        }
        return path.toString();
    }

    private String modulePathToFilePath(String modulePath) {
        // Converte helpers.matematica para helpers/matematica
        String relativePath = modulePath.replace(".", "/");
        return baseDirectory + relativePath;
    }
    
    private void loadInternalLibrary(String modulePath) {
        // Remove o prefixo "internal." e obtém o nome da biblioteca
        String libraryName = modulePath.substring("internal.".length());
        String resourcePath = "/internal/" + libraryName + ".npas";
        
        try {
            // Carrega o recurso do classpath
            InputStream resourceStream = getClass().getResourceAsStream(resourcePath);
            if (resourceStream == null) {
                if (!suppressWarnings) {
                    System.err.println("Biblioteca interna não encontrada: " + modulePath);
                }
                return;
            }
            
            // Converte InputStream para CharStream
            CharStream input = CharStreams.fromStream(resourceStream);
            NeoObjectPascalLexer lexer = new NeoObjectPascalLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            NeoObjectPascalParser parser = new NeoObjectPascalParser(tokens);
            
            // Suppress warnings if requested
            if (suppressWarnings) {
                lexer.removeErrorListeners();
                parser.removeErrorListeners();
                // Add a silent error listener that does nothing
                org.antlr.v4.runtime.BaseErrorListener silentListener = new org.antlr.v4.runtime.BaseErrorListener();
                lexer.addErrorListener(silentListener);
                parser.addErrorListener(silentListener);
            }
            
            ParseTree tree = parser.program();
            
            // Interpreta a biblioteca interna
            visit(tree);
            
            // Fecha o stream
            resourceStream.close();
            
        } catch (IOException e) {
            if (!suppressWarnings) {
                System.err.println("Erro ao carregar biblioteca interna " + modulePath + ": " + e.getMessage());
            }
        }
    }
}
