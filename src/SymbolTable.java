import java.util.HashSet;

public class SymbolTable {
	HashSet<Variable> variables;
	public SymbolTable()
	{
		variables = new HashSet<Variable>();
	}
	public void add(Variable variable)
	{
		variables.add(variable);
	}
}
