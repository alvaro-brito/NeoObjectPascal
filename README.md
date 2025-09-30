# NeoObjectPascal

Bem-vindo ao NeoObjectPascal, uma linguagem de programação moderna e poderosa, inspirada no Object Pascal, mas com recursos aprimorados para manipulação de dados, programação funcional e muito mais.

Este projeto foi criado como uma implementação funcional da linguagem, baseada nas técnicas do livro "Crafting Interpreters".

## Autor

*   Álvaro Brito

## Estrutura do Projeto

O projeto está organizado da seguinte forma:

*   `src/`: Contém o código-fonte do interpretador NeoObjectPascal, escrito em Java.
*   `docs/`: Contém a documentação educativa, explicando como usar a linguagem.
*   `examples/`: Contém vários exemplos de código em NeoObjectPascal.
*   `pom.xml`: O arquivo de configuração do Maven para construir o projeto.

## Como Construir e Executar

Para construir o projeto, você precisa ter o Maven instalado. Em seguida, execute o seguinte comando na raiz do projeto:

```bash
mvn clean install
```

Isso irá compilar o código-fonte, gerar o interpretador e executar os testes.

Para executar um arquivo NeoObjectPascal, use o seguinte comando simplificado:

```bash
java -jar target/neoobjectpascal-1.0-SNAPSHOT-jar-with-dependencies.jar <caminho_para_o_arquivo.npas>
```

Por exemplo, para executar o exemplo "Olá, Mundo!", use:

```bash
java -jar target/neoobjectpascal-1.0-SNAPSHOT-jar-with-dependencies.jar examples/01-OlaMundo.npas
```

## Exemplos Disponíveis

- `examples/01-OlaMundo.npas` - Programa básico
- `examples/04-Funcoes.npas` - Demonstração de funções
- `examples/05-JSON.npas` - Processamento de JSON
- `examples/06-CSV.npas` - Processamento de CSV
- `examples/10-ModulosCompleto.npas` - Sistema de módulos hierárquicos
- `examples/12-ProjetoHierarquico.npas` - Projeto com módulos complexos
- `examples/13-MenuInterativo.npas` - Demonstração de menu interativo
- `examples/14-EntradaUsuario.npas` - Entrada do usuário (funciona melhor em modo interativo)

## Funcionalidades

- **Sistema de Módulos Hierárquicos**: Importação com `uses helpers.matematica, lib.core.calculadora`
- **Processamento de Dados**: Suporte nativo para JSON e CSV
- **Programação Funcional**: Pipeline operators (`|>`)
- **Entrada/Saída**: `WriteLn`, `ReadLn`, `showMenu`
- **Estruturas de Controle**: `if`, `while`, `for`
- **Funções**: Declaração e chamada de funções personalizadas

## Notas sobre ReadLn

O comando `ReadLn` funciona tanto em execução interativa quanto com redirecionamento de entrada:

```bash
# Execução interativa
java -jar target/neoobjectpascal-1.0-SNAPSHOT-jar-with-dependencies.jar examples/14-EntradaUsuario.npas

# Para testes automatizados
printf "Jessie\n30\n" | java -jar target/neoobjectpascal-1.0-SNAPSHOT-jar-with-dependencies.jar examples/14-EntradaUsuario.npas
```

## Documentação e Exemplos

Consulte o diretório `docs/` para obter um guia passo a passo sobre como usar o NeoObjectPascal. O diretório `examples/` contém exemplos de código que você pode executar e modificar.

## Arquitetura Interna - Do Código Fonte ao Bytecode

O NeoObjectPascal segue uma arquitetura de interpretador baseada em árvore de sintaxe abstrata (AST). Aqui está o fluxo completo de processamento:

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Código .npas  │───▶│  ANTLR4 Lexer    │───▶│     Tokens      │
│                 │    │ (NeoObjectPascal │    │                 │
│ begin           │    │  Lexer.g4)       │    │ BEGIN, WRITELN, │
│   WriteLn("Hi") │    │                  │    │ STRING, etc.    │
│ end.            │    └──────────────────┘    └─────────────────┘
└─────────────────┘                                      │
                                                         ▼
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│ Execução Final  │◀───│   Interpretador  │◀───│  ANTLR4 Parser  │
│                 │    │ (Visitor Pattern)│    │ (NeoObjectPascal│
│ Saída: "Hi"     │    │                  │    │  Parser.g4)     │
│ Entrada: dados  │    │ - SymbolTable    │    │                 │
│ Módulos: carregados  │ - Function calls │    │ Parse Tree (AST)│
└─────────────────┘    │ - Module system  │    └─────────────────┘
                       └──────────────────┘
```

### Componentes Principais

#### 1. **Análise Léxica (Lexer)**
- **Arquivo**: [`NeoObjectPascalLexer.g4`](src/main/antlr4/com/neoobjectpascal/NeoObjectPascalLexer.g4:1)
- **Função**: Converte código fonte em tokens
- **Tokens**: `BEGIN`, `END`, `WRITELN`, `READLN`, `IDENTIFIER`, `STRING`, etc.
- **Exemplo**: `WriteLn("Hello")` → `[WRITELN, LPAREN, STRING, RPAREN]`

#### 2. **Análise Sintática (Parser)**
- **Arquivo**: [`NeoObjectPascalParser.g4`](src/main/antlr4/com/neoobjectpascal/NeoObjectPascalParser.g4:1)
- **Função**: Constrói árvore de sintaxe abstrata (AST)
- **Regras**: `program`, `statement`, `expression`, `declaration`
- **Saída**: Parse Tree estruturada

#### 3. **Interpretação (Visitor)**
- **Arquivo**: [`Interpreter.java`](src/main/java/com/neoobjectpascal/Interpreter.java:18)
- **Padrão**: Visitor Pattern do ANTLR4
- **Função**: Percorre AST e executa código
- **Componentes**:
  - **SymbolTable**: Gerencia variáveis e escopo
  - **Function Map**: Armazena funções definidas pelo usuário
  - **Module System**: Carrega e processa módulos externos

#### 4. **Sistema de Módulos**
- **Resolução**: Caminhos relativos ao arquivo `.npas`
- **Exemplo**: `uses helpers.matematica` → `helpers/matematica.npas`
- **Processamento**: Cada módulo é parseado e interpretado recursivamente

### Fluxo de Execução Detalhado

```
1. Main.java recebe arquivo .npas
   ├─ Determina diretório base
   └─ Cria CharStream do arquivo

2. ANTLR4 Lexer processa caracteres
   ├─ Identifica tokens (palavras-chave, operadores, literais)
   └─ Gera CommonTokenStream

3. ANTLR4 Parser constrói AST
   ├─ Aplica regras gramaticais
   ├─ Valida sintaxe
   └─ Cria ParseTree

4. Interpreter visita nós da AST
   ├─ visitProgram() - ponto de entrada
   ├─ visitUsesClause() - carrega módulos
   ├─ visitDeclaration() - processa declarações
   └─ visitBlock() - executa statements

5. Execução de Statements
   ├─ visitWriteLnStatement() → System.out.println()
   ├─ visitReadLnStatement() → Scanner.nextLine()
   ├─ visitAssignment() → SymbolTable.put()
   ├─ visitCall() → Function execution
   └─ visitExpression() → Avaliação de expressões

6. Sistema de Tipos e Valores
   ├─ Type.java - enumeração de tipos
   ├─ Symbol.java - variáveis tipadas
   └─ Conversões automáticas (String→Integer, etc.)
```

### Dependências e Bibliotecas

#### **ANTLR4 Runtime** (`org.antlr:antlr4-runtime:4.13.1`)
- **Lexer/Parser**: Geração automática de analisadores
- **Visitor Pattern**: Percorrimento da AST
- **Error Handling**: Tratamento de erros sintáticos

#### **Jackson** (`com.fasterxml.jackson.core:jackson-databind:2.15.2`)
- **JSON Processing**: `JSON.parse()` statements
- **Object Mapping**: Conversão JSON → Java Objects

#### **OpenCSV** (`com.opencsv:opencsv:5.7.1`)
- **CSV Processing**: `CSV.parse()` statements
- **Data Parsing**: Conversão CSV → List<String[]>

### Compilação e Empacotamento

```
Maven Build Process:
├─ antlr4-maven-plugin
│  ├─ Gera Lexer.java e Parser.java
│  └─ Cria classes Visitor/Listener
├─ maven-compiler-plugin
│  └─ Compila Java → Bytecode (.class)
└─ maven-assembly-plugin
   └─ Cria JAR com dependências incluídas
```

### Características Técnicas

- **Interpretação Direta**: Não gera bytecode intermediário
- **Tree-Walking Interpreter**: Executa diretamente na AST
- **Tipagem Dinâmica**: Conversões automáticas em runtime
- **Escopo Lexical**: SymbolTable com herança de escopo
- **Módulos Hierárquicos**: Sistema de imports com caminhos relativos
- **Error Recovery**: Tratamento robusto de erros de entrada/parsing

Este design permite execução eficiente de código NeoObjectPascal mantendo simplicidade arquitetural e facilidade de extensão para novas funcionalidades.
