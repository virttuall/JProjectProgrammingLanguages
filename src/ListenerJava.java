import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ListenerJava extends Java8BaseListener{
	
	Java8Parser parser;
	List<Response> responses;
	Set<String> arraysName;
	public ListenerJava(Java8Parser parser)
	{
		this.parser = parser;
		responses = new ArrayList<Response>();
		arraysName = new TreeSet<String>();
	}
	@Override 
	public void enterAssertStatement(Java8Parser.AssertStatementContext ctx) 
	{ 
//		List<Java8Parser.ExpressionContext> listExp = ctx.expression();
//		for(int i = 0; i < listExp.size(); i++ )
//		{
//			System.out.println("hola: "+listExp.get(i).start.toString());
//		}
//		System.out.println(ctx.expression());
//		System.out.println(ctx.getStart().toString());
//		List<TerminalNode> list = ctx.getTokens(102);
//		System.out.println(list.toString());
	}
	
	@Override
	public void enterPrimary(Java8Parser.PrimaryContext ctx) {
		// TODO Auto-generated method stub

	}
	@Override
	public void enterArrayType(Java8Parser.ArrayTypeContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println(ctx.getText());
	}
	@Override
	public void enterVariableDeclaratorId(Java8Parser.VariableDeclaratorIdContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println(ctx.getText());
	}
	@Override
	public void enterVariableDeclarator(Java8Parser.VariableDeclaratorContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println(ctx.getText());
		if ( UtilsF.isArray(ctx.getText()))
		{
			arraysName.add(UtilsF.nameOfArray(ctx.getText()));
		}
		super.enterVariableDeclarator(ctx);
	}
	@Override
	public void enterMethodInvocation(Java8Parser.MethodInvocationContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println(ctx.getText());
		String aux;
		for ( final String cad1 : arraysName )
		{
			for( final String cad2 : arraysName )
			{
				if ( !cad1.equals(cad2))
				{
					aux = cad1+".equals("+cad2+")";
					if (ctx.getText().contains(aux))
					{
						Response auxResponse = new Response(aux, "java.util.Arrays.equals("+cad1+","+cad2+")", "");
						responses.add(auxResponse);
					}
				}
			}
		}
		super.enterMethodInvocation(ctx);
	}
}
