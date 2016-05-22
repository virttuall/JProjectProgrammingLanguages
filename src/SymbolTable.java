import java.util.HashMap;
import java.util.HashSet;

public class SymbolTable {
	HashMap<String, String> variables;
	public SymbolTable()
	{
		variables = new HashMap<String, String>();
	}
	public void add(Variable variable)
	{
		variables.put(variable.name, variable.type);
	}
	public Variable search(String name)
	{
		String type = variables.get(name);
		return new Variable(name, type);
	}
}
