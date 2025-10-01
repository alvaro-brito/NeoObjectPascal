package com.neoobjectpascal;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JavaExecutor {
    private static int classCounter = 0;
    
    public static Object executeJavaCode(String javaCode, List<Object> parameters) {
        try {
            // Gera nome único para a classe
            String className = "DynamicJavaClass" + (++classCounter);
            
            // Cria o código Java completo
            String fullJavaCode = generateFullJavaCode(className, javaCode, parameters);
            
            // Compila o código
            Class<?> compiledClass = compileJavaCode(className, fullJavaCode);
            
            // Executa o método
            Method executeMethod = compiledClass.getMethod("execute", Object[].class);
            Object[] paramArray = parameters.toArray();
            
            return executeMethod.invoke(null, (Object) paramArray);
            
        } catch (Exception e) {
            System.err.println("Erro ao executar código Java: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    private static String generateFullJavaCode(String className, String javaCode, List<Object> parameters) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("import java.time.*;\n");
        sb.append("import java.util.*;\n");
        sb.append("import java.text.*;\n");
        sb.append("import java.math.*;\n");
        sb.append("\n");
        sb.append("public class ").append(className).append(" {\n");
        sb.append("    public static Object execute(Object[] params) {\n");
        
        // Adiciona declarações de parâmetros
        for (int i = 0; i < parameters.size(); i++) {
            Object param = parameters.get(i);
            String javaType = getJavaType(param);
            sb.append("        ").append(javaType).append(" param").append(i)
              .append(" = (").append(javaType).append(") params[").append(i).append("];\n");
        }
        
        sb.append("\n");
        
        // Remove as chaves do código Java e adiciona o conteúdo
        String cleanCode = javaCode.trim();
        if (cleanCode.startsWith("{") && cleanCode.endsWith("}")) {
            cleanCode = cleanCode.substring(1, cleanCode.length() - 1);
        }
        
        sb.append("        ").append(cleanCode.replace("\n", "\n        ")).append("\n");
        sb.append("    }\n");
        sb.append("}\n");
        
        return sb.toString();
    }
    
    private static String getJavaType(Object obj) {
        if (obj == null) return "Object";
        if (obj instanceof Integer) return "Integer";
        if (obj instanceof String) return "String";
        if (obj instanceof Boolean) return "Boolean";
        if (obj instanceof Double) return "Double";
        return "Object";
    }
    
    private static Class<?> compileJavaCode(String className, String javaCode) throws Exception {
        // Obtém o compilador Java
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new RuntimeException("Java Compiler não disponível. Certifique-se de estar usando JDK, não JRE.");
        }
        
        // Cria um sistema de arquivos em memória
        InMemoryFileManager fileManager = new InMemoryFileManager(compiler.getStandardFileManager(null, null, null));
        
        // Cria o objeto de código fonte
        JavaSourceFromString sourceFile = new JavaSourceFromString(className, javaCode);
        
        // Cria um DiagnosticCollector para capturar erros
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        
        // Compila o código
        List<JavaSourceFromString> sourceFiles = Arrays.asList(sourceFile);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, sourceFiles);
        
        boolean success = task.call();
        if (!success) {
            // Mostra os erros de compilação
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                System.err.println(diagnostic.toString());
            }
            System.err.println("Código Java gerado:");
            System.err.println(javaCode);
            throw new RuntimeException("Falha na compilação do código Java");
        }
        
        // Carrega a classe compilada
        InMemoryClassLoader classLoader = new InMemoryClassLoader(fileManager.getCompiledClasses());
        return classLoader.loadClass(className);
    }
    
    // Classe para representar código fonte Java em memória
    static class JavaSourceFromString extends SimpleJavaFileObject {
        final String code;
        
        JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }
        
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }
    
    // Gerenciador de arquivos em memória
    static class InMemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {
        private final Map<String, ByteArrayOutputStream> compiledClasses = new HashMap<>();
        
        protected InMemoryFileManager(JavaFileManager fileManager) {
            super(fileManager);
        }
        
        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
            return new InMemoryJavaFileObject(className, kind);
        }
        
        Map<String, byte[]> getCompiledClasses() {
            Map<String, byte[]> result = new HashMap<>();
            for (Map.Entry<String, ByteArrayOutputStream> entry : compiledClasses.entrySet()) {
                result.put(entry.getKey(), entry.getValue().toByteArray());
            }
            return result;
        }
        
        class InMemoryJavaFileObject extends SimpleJavaFileObject {
            private final String className;
            
            InMemoryJavaFileObject(String className, Kind kind) {
                super(URI.create("string:///" + className.replace('.', '/') + kind.extension), kind);
                this.className = className;
            }
            
            @Override
            public OutputStream openOutputStream() {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                compiledClasses.put(className, baos);
                return baos;
            }
        }
    }
    
    // ClassLoader para carregar classes compiladas em memória
    static class InMemoryClassLoader extends ClassLoader {
        private final Map<String, byte[]> compiledClasses;
        
        InMemoryClassLoader(Map<String, byte[]> compiledClasses) {
            this.compiledClasses = compiledClasses;
        }
        
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] classBytes = compiledClasses.get(name);
            if (classBytes == null) {
                throw new ClassNotFoundException(name);
            }
            return defineClass(name, classBytes, 0, classBytes.length);
        }
    }
}