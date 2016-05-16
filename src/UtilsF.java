
public class UtilsF {
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
}
