import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.naming.spi.DirStateFactory.Result;

public class ListenerJava extends Java8BaseListener{
	
	Java8Parser parser;
	List<Response> responses;
	Set<String> arraysName;
	boolean inAssert;
	boolean badAssert;
	int countBooleanForAssert;
	public ListenerJava(Java8Parser parser)
	{
		this.parser = parser;
		responses = new ArrayList<Response>();
		arraysName = new TreeSet<String>();
		inAssert = false;
		badAssert = false;
		countBooleanForAssert = 0;
	}
	@Override 
	public void enterAssertStatement(Java8Parser.AssertStatementContext ctx) 
	{ 
		System.out.println("enterAssertStatement");
		inAssert = true;
		
	}
	@Override
	public void exitAssertStatement(Java8Parser.AssertStatementContext ctx) {
		// TODO Auto-generated method stub
		inAssert = false;
		if ( badAssert )
		{
			System.out.println(ctx.getText());
			Response auxResult = new Response(ctx.getText(), UtilsF.getGoodAssert(ctx.getText(), "AssertBool"+countBooleanForAssert++), "");
			responses.add(auxResult);
			badAssert = false;
		}
		System.out.println("exitAssertStatement");
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
	public void enterMethodInvocation(Java8Parser.MethodInvocationContext ctx) 
	{
		System.out.println("enterMethodInvocation");
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
	@Override
	public void enterMethodInvocation_lf_primary(Java8Parser.MethodInvocation_lf_primaryContext ctx) {
		// TODO Auto-generated method stub
		System.out.println("enterMethodInvocation_lf_primary");
		super.enterMethodInvocation_lf_primary(ctx);
	}
	@Override
	public void enterMethodInvocation_lfno_primary(Java8Parser.MethodInvocation_lfno_primaryContext ctx) {
		// TODO Auto-generated method stub
		System.out.println("enterMethodInvocation_lfno_primary");
		if ( inAssert )
		{
			badAssert = true;
		}
		super.enterMethodInvocation_lfno_primary(ctx);
	}
	@Override
	public void enterEnhancedForStatement(Java8Parser.EnhancedForStatementContext ctx) {
		// TODO Auto-generated method stub
		super.enterEnhancedForStatement(ctx);
		String cad = ctx.getText();
		String resp[];
		if ( !UtilsF.doItHaveFinal(cad))
		{
			resp = UtilsF.getGoodAndBad(cad);
			Response auxResponse = new Response(resp[1], resp[0], "");
			responses.add(auxResponse);
		}
		//System.out.println(ctx.getText());
	}
	@Override
	public void enterClassInstanceCreationExpression(Java8Parser.ClassInstanceCreationExpressionContext ctx) {
		// TODO Auto-generated method stub
		System.out.println("enterClassInstanceCreationExpression");
		super.enterClassInstanceCreationExpression(ctx);
	}
	@Override
	public void enterClassInstanceCreationExpression_lf_primary(Java8Parser.ClassInstanceCreationExpression_lf_primaryContext ctx) {
		// TODO Auto-generated method stub
		System.out.println("enterClassInstanceCreationExpression_lf_primary");
		super.enterClassInstanceCreationExpression_lf_primary(ctx);
	}
	@Override
	public void enterClassInstanceCreationExpression_lfno_primary(
			Java8Parser.ClassInstanceCreationExpression_lfno_primaryContext ctx) {
		System.out.println("enterClassInstanceCreationExpression_lfno_primary");
		//System.out.println(ctx.getText());
		String cad = ctx.getText();
		if ( UtilsF.isBigDecimal(cad))
		{
			if ( !UtilsF.isAGoodNewBigDecimal(cad))
			{
				Response auxResponse = new Response(cad, UtilsF.getGoodBigDecimal(cad), "");
				responses.add(auxResponse);
			}
		}
		// TODO Auto-generated method stub
		super.enterClassInstanceCreationExpression_lfno_primary(ctx);
	}
	@Override
	public void enterBasicForStatement(Java8Parser.BasicForStatementContext ctx) {
		// TODO Auto-generated method stub
		System.out.println("enterBasicForStatement");
		String cad = ctx.getText();
		if ( UtilsF.isForWithFloat(cad))
		{
			//System.out.println(cad);
			Response auxResponse = new Response(UtilsF.headerFor(cad), "You have a for with float", "");
			responses.add(auxResponse);
		}
		else if ( UtilsF.isFowWithDouble(cad))
		{
			Response auxResponse = new Response(UtilsF.headerFor(cad), "You have a for with double", "");
			responses.add(auxResponse);
		}
		super.enterBasicForStatement(ctx);
	}
}
