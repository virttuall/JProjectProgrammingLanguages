import java.io.File;
import java.io.IOException;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


public class Main {
public static void main(String[] args) throws IOException {
	// Get our lexer
	Java8Lexer lexer;
	lexer = new Java8Lexer(new ANTLRFileStream("input.txt"));
	 // Get a list of matched tokens
    CommonTokenStream tokens = new CommonTokenStream(lexer);
 
    // Pass the tokens to the parser
    Java8Parser parser = new Java8Parser(tokens);
 
    // Specify our entry point
    Java8Parser.CompilationUnitContext  compilationUnit= parser.compilationUnit();
    // Walk it and attach our listener
    ParseTreeWalker walker = new ParseTreeWalker();
    ListenerJava listener = new ListenerJava();
    walker.walk(listener, compilationUnit);
 
}
}
