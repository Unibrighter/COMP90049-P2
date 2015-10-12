package rocklee.preprocess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;


/**
 * This class is used to tokenize the raw tweets input. Hopefully this can get
 * rid of the unwanted part of the message
 * 
 * Also, stemming and case-folding are also included in this class.
 * 
 * @author Kunliang Wu
 * @version 2015-10-12 16:36
 * 
 * */

public class Tokenizer
{
	// for debug and info
	private static Logger log = Logger.getLogger(Tokenizer.class);
	
	public static void rawText2CSV(String input_path, String output_path)
	{
		File input_file = new File(input_path);
		File output_file = new File(output_path);

		ArrayList<Tweet> tweet_list=new ArrayList<Tweet>();

		try
		{
			Scanner tweet_scanner = new Scanner(input_file);
			PrintWriter csv_writer=new PrintWriter(output_file);
			String raw_tweet=null;
			Tweet tmp_tweet=null;
			int count=0;
			while(tweet_scanner.hasNextLine())
			{
				count++;
				raw_tweet=tweet_scanner.nextLine();
				tmp_tweet= new Tweet(raw_tweet);
				tweet_list.add(tmp_tweet);
	
				AttributeVector.addTweet(tmp_tweet);
			}
			log.debug("All training data has been recorded!!!"+count);
			
			csv_writer.println(AttributeVector.getOutputAttributeInOneLine(true));
			
			log.debug("Begin to format CSV output!");
			
			for (int i = 0; i < tweet_list.size(); i++)
			{
				tmp_tweet=tweet_list.get(i);
				
				boolean[] vector_array=AttributeVector.collection2Array(tmp_tweet);
				csv_writer.print(tmp_tweet.getTweetID1()+",");
				csv_writer.println(Tokenizer.booleanArray2String(vector_array));
			}
			
			csv_writer.flush();
			tweet_scanner.close();
			csv_writer.close();

		} catch (Exception e)
		{

			e.printStackTrace();
		}

	}
	
	public static String booleanArray2String(boolean[] boolean_array)
	{
		StringBuffer str_buf=new StringBuffer();
		if(boolean_array[0])
			str_buf.append('1');
		else
			str_buf.append('0');
		
		for (int i = 1; i < boolean_array.length; i++)
		{
			str_buf.append(',');
			if(boolean_array[i])
				
				str_buf.append('1');
			else
				str_buf.append('0');
		}
		return str_buf.toString();
	}
	

	public static void main(String[] args) throws FileNotFoundException
	{
//
//		Scanner tweet_scanner = new Scanner(new File("/Users/bitmad/DEXTER/KT_Project2/data/train/train.txt"));
//		int count=0;
//		while(tweet_scanner.hasNext())
//		{
//			count++;
//			System.out.println(tweet_scanner.nextLine());
//		}
//		System.out.println(count);
		
		
		// get the dictionary into the memorys
		Tweet.loadDictionary(args[0]);

		//format out put
		Tokenizer.rawText2CSV(args[1], args[2]);
		
	}

}
