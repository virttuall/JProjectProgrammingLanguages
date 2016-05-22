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
	Set<String> collectionsName;
	SymbolTable symbolTable;
	boolean inAssert;
	boolean badAssert;
	int countBooleanForAssert;
	public ListenerJava(Java8Parser parser)
	{
		this.parser = parser;
		responses = new ArrayList<Response>();
		arraysName = new TreeSet<String>();
		collectionsName = new TreeSet<String>();
		inAssert = false;
		badAssert = false;
		countBooleanForAssert = 0;
		symbolTable = new SymbolTable();
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
			Response auxResult = new Response(ctx.getText(), UtilsF.getGoodAssert(ctx.getText(), "AssertBool"+countBooleanForAssert++), "", "EXP06-J", ctx.start.getLine());
			responses.add(auxResult);
			badAssert = false;
		}
		System.out.println("exitAssertStatement");
	}
	@Override
	public void enterVariableDeclarator(Java8Parser.VariableDeclaratorContext ctx) {
		// TODO Auto-generated method stub
		System.out.println("enterVariableDeclarator");
		System.out.println(ctx.getText());
		String cad = ctx.getText();
		if ( UtilsF.isArray(ctx.getText()))
		{
			arraysName.add(UtilsF.nameOfArray(ctx.getText()));
		}
		if ( UtilsF.isCollection(cad))
		{
			collectionsName.add(UtilsF.getNameCollection(cad));
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
						Response auxResponse = new Response(aux, "java.util.Arrays.equals("+cad1+","+cad2+")", "", "EXP02-J", ctx.start.getLine());
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
		System.out.println(ctx.start.getLine());
		super.enterEnhancedForStatement(ctx);
		String cad = ctx.getText();
		String resp[];
		if ( !UtilsF.doItHaveFinal(cad))
		{
			resp = UtilsF.getGoodAndBad(cad);
			Response auxResponse = new Response(resp[1], resp[0], "", "DCL02-J", ctx.start.getLine());
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
		String cad = ctx.getText();
		if ( UtilsF.isBigDecimal(cad))
		{
			if ( !UtilsF.isAGoodNewBigDecimal(cad))
			{
				Response auxResponse = new Response(cad, UtilsF.getGoodBigDecimal(cad), "", "NUM10-J", ctx.start.getLine());
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
			Response auxResponse = new Response(UtilsF.headerFor(cad), "You have a for with float", "","NUM09-J", ctx.start.getLine());
			responses.add(auxResponse);
		}
		else if ( UtilsF.isFowWithDouble(cad))
		{
			Response auxResponse = new Response(UtilsF.headerFor(cad), "You have a for with double", "", "NUM09-J", ctx.start.getLine());
			responses.add(auxResponse);
		}
		super.enterBasicForStatement(ctx);
	}
	@Override
	public void enterLocalVariableDeclaration(Java8Parser.LocalVariableDeclarationContext ctx) {
		// TODO Auto-generated method stub
		
		System.out.println("enterLocalVariableDeclaration");
		String cad = ctx.getText();
		String type = ctx.start.getText();
		String name = cad.split("=")[0].substring(type.length());
		if ( name.startsWith("<"))
		{
			boolean flag = true;
			while(flag)
			{
				name = name.substring(1);
				if ( name.startsWith(">"))
				{
					name = name.substring(1);
					flag = false;
				}
			}
		}
		System.out.println("--------------------------");
		System.out.println(type + " , "+ name);
		System.out.println("--------------------------");
		super.enterLocalVariableDeclaration(ctx);
	}
}
