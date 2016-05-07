import org.antlr.v4.runtime.misc.NotNull;

public class ListenerJava extends Java8BaseListener{
	@Override 
	public void enterAssertStatement(Java8Parser.AssertStatementContext ctx) 
	{ 
		System.out.println(ctx.getText());
	}
}
