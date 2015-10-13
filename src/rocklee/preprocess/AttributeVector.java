package rocklee.preprocess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class AttributeVector
{
	
//	public static HashMap<String, Short> str_map=new HashMap<String, Short>();
	
	public static HashSet<String> general_vector=new HashSet<String>();
	
	//???????,???????vector???Ð¸???????
	public static void addTweet(Tweet tweet)
	{
		//???Tweet??token
		ArrayList<String> tokens=tweet.getTokens();
		general_vector.addAll(tokens);
	}
	
	//??????????,???Ã·?????????????arraylist???????1/0????????csv???????
	public static boolean[] collection2Array(Tweet tweet)
	{
		//???Tweet??token
		ArrayList<String> tokens=tweet.getTokens();
		
		Iterator<String> iter=general_vector.iterator();
		
		boolean[] results=new boolean[AttributeVector.general_vector.size()];
		int index=0;
		
		while(iter.hasNext())
		{
			String attribute_str=iter.next();
			boolean found=false;
			
			for (int i = 0; i < tokens.size(); i++)
			{
				if(tokens.get(i).equals(attribute_str))
				{
					found=true;
					break;
				}
			}
			results[index]=found;
			index++;
		}
		
		return results;
	}
	

	//??????????????????
	public static String getOutputAttributeInOneLine(boolean withID)
	{
		Iterator<String> iter=general_vector.iterator();
		StringBuffer result=new StringBuffer();
		if(withID)
	//Êä³öid
			result.append("id,");
		result.append(iter.next());
		while(iter.hasNext())
		{
			result.append(',');
			result.append(iter.next());
		}
		return result.toString();
	}
	
	
}
