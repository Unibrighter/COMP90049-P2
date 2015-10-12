package rocklee.preprocess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class AttributeVector
{
	
//	public static HashMap<String, Short> str_map=new HashMap<String, Short>();
	
	public static HashSet<String> general_vector=new HashSet<String>();
	
	//静态方法,向半成品的vector池中更新向量
	public static void addTweet(Tweet tweet)
	{
		//获取Tweet的token
		ArrayList<String> tokens=tweet.getTokens();
		general_vector.addAll(tokens);
	}
	
	//一个静态方法,通过该方法能够将给出的arraylist转化为一个1/0值的数组的csv表现形式
	public static boolean[] collection2Array(Tweet tweet)
	{
		//获取Tweet的token
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
	

	//输出自己的属性集合字符串
	public static String getOutputAttributeInOneLine(boolean withID)
	{
		Iterator<String> iter=general_vector.iterator();
		StringBuffer result=new StringBuffer();
		if(withID)
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
