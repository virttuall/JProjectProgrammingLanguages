
public class UtilsF {
	
	//for EXP02-J
	public static boolean isArray(String cad)
	{
		int count = 0;
		for( int i = 0; i < cad.length(); i++ )
		{
			if ( cad.charAt(i) == '[' ) count++;
		}
		if ( count == 1) return true;
		return false;
	}
	public static String nameOfArray(String cad)
	{
		return cad.split("=")[0];
	}
	
	//for DCL02-J
	public static boolean doItHaveFinal(String cad)
	{
		String auxCad = cad.substring(4);
		if (auxCad.startsWith("final"))
		{
			return true;
		}
		return false;
	}
	public static String[] getGoodAndBad(String cad)
	{
		String auxCad = cad.substring(4);
		String bad="", good="";
		int c = 1;
		for(int i = 0; i < auxCad.length(); i++ )
		{
			if ( auxCad.substring(i, i+1).equals("(") )
			{
				c++;
			}
			else if ( auxCad.substring(i, i+1).equals(")"))
			{
				c--;
			}
			if ( c == 0)
			{
				bad = "for("+auxCad.substring(0, i+1);
				good = "for(final"+auxCad.substring(0, i+1);
				break;
			}
		}
		return new String[]{good, bad};
	}
	
	//for EXP06-J
	public static String getGoodAssert(String cad, String nameBool)
	{
		String auxCad = cad.substring(6);
		String result = "boolean "+nameBool +" = " + auxCad;
		result += "\n";
		result += "assert "+nameBool+";";
		return result;
	}
}
