package rocklee.preprocess;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class Tweet
{
	// for debug and info
	private static Logger log = Logger.getLogger(Tweet.class);

	// tweet id,since the meaning is no longer useful,use String instead of long
	private String tweetID1 = null;

	// class indicator
	private boolean adr_class = false;

	// whole content contained in the piece of tweet
	private String fullContent = null;

	// separate info
	private ArrayList<String> token_list = null;

	private static HashMap<String, String> freq_dict = null;
	private static Scanner sc = null;

	public static void loadDictionary(String file_path)
	{
		freq_dict=new HashMap<String, String>();
		File input_file = new File(file_path);
		try
		{
			Tweet.sc = new Scanner(input_file);

			while (sc.hasNextLine())
			{
				String tmp[] = sc.nextLine().split("\t");
				freq_dict.put(tmp[0], tmp[1]);
			}

			log.debug("Replacement dictionary loaded!");
			sc.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean getADRClass()
	{
		return this.adr_class;
	}
	
	public Tweet(String raw_str)
	{
		// empty string given
		if (raw_str == null || raw_str.compareTo("") == 0)
		{
			log.debug("Empty string given to set up the tweet!");
			return;
		}

		else
		{
			this.token_list=new ArrayList<String>();
			this.initializeTweet(raw_str);
		}
	}

	public ArrayList<String> getTokens()
	{
		return this.token_list;
	}

	public String getFullContent()
	{
		return this.fullContent;
	}

	public String getTweetID1()
	{
		return this.tweetID1;
	}


	private void initializeTweet(String raw_str)
	{
		// first separate the id and content
		String[] tmp = raw_str.split("\t");

		// extract tweet id
		this.tweetID1 = tmp[0];

		this.adr_class = tmp[2].equals("1") ? true : false;

		this.fullContent = tmp[3];

		// extract tokens array
		tmp = this.tokenize(this.fullContent);

		// get rid of and replace some unwanted message
		for (int i = 0; i < tmp.length; i++)
		{
			// decide whether we concern this token or not
			if (tmp[i].length() <= 2)// too short
				continue;

			//we don't want any pure number
			if(tmp[i].matches("[0-9]+"))
			{
				continue;
			}
			
			//lexical normalization ,dictionary replacement
			if (Tweet.freq_dict.containsKey(tmp[i]))
			{
				tmp[i]=freq_dict.get(tmp[i]);
			}
			this.token_list.add(tmp[i]);
		}
	}

	private String[] tokenize(String input_str)
	{
		// String input_str =
		// ": Pop a vyvanse I'm workin'\" I can't stop working! Lmao";
		// first get rid of @
		input_str = input_str.replaceAll("@\\w*", "");
		// get rid of those links
		input_str = input_str.replaceAll("http.*\\w*", "");
		// get rid of the rest of the punctuations, and case folding
		input_str = input_str.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();

		System.out.println(input_str);
		return input_str.split("\\s+");

	}

}
