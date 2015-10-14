package rocklee.feature_eng;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import org.apache.log4j.Logger;

import rocklee.preprocess.AttributeVector;
import rocklee.preprocess.CSV_Generator;
import rocklee.preprocess.Tweet;

//this class can been considered as the main part of the feature engineering
//all we have to do in this part is "For a piece of given tweet, we output the corresponding
//feature array"
//-and of course, the array we output takes the format as the feature we have selected for 
//ourselves in this particular scenario

public class FeatureGenerator
{
	// for debug and info
	private static Logger log = Logger.getLogger(FeatureGenerator.class);

	public static ArrayList<Tweet> tweet_list = new ArrayList<Tweet>();

	public static final int BASE_NUM_COUNT = 113;

	public static final String[] CHI_STEADY =
	{ "makes", "feel", "sick", "making", "gain", "zombie", "pain", "weight",
			"withdrawals", "crippled", "tired", "diary", "legs", "munchies",
			"hating", "pains", "gained", "muscle", "since", "olanzapine",
			"rivaroxaban", "bad", "made", "withdrawal", "quetiapine",
			"lozenge", "but", "effexor", "body", "seroquel", "restless",
			"headaches", "tendon", "retention", "crying", "allergic",
			"causing", "drowsy", "wondering", "nightmare", "messed", "caused",
			"fluid", "had", "prozac", "neck", "was", "definitely", "due",
			"taking", "skin", "lamotrigine", "stone", "suicidal", "appetite",
			"sense", "experiencing", "quetiapines", "rupture", "lingerie",
			"sweaty", "bras", "cataplexy", "distribute", "drive", "bactrim",
			"nauseous", "dairy", "prolactin", "paralysis", "addict", "obese",
			"wondered", "tremors", "humungous", "sensitivity", "bizarre",
			"eventually", "stated", "teens", "dependence", "zombified",
			"dizzy", "lung", "anyway", "boobs", "cripple", "tapering",
			"disgusting", "tummy", "advertisements", "sleep", "bed",
			"headache", "question", "slept", "don", "this", "been", "help",
			"off", "person", "wasnt", "can", "worse", "dreams", "reaction",
			"thoughts", "supposed", "sertraline", "affect", "mention", "chemo",
			"sideeffects", "dry", "suffer", "sedating", "difficult",
			"chemical", "contain", "depression", "bananas", "much", "every",
			"hours", "ugh", "addiction", "cymbalta", "before", "ive", "back",
			"time", "though", "day", "night", "nicotine", "natural", "out",
			"right", "side", "not", "never", "same" };
	public static final String[] MI_STEADY =
	{ "makes", "feel", "lozenge", "prozac", "sick", "making", "pain", "gain",
			"zombie", "weight", "person", "withdrawals", "olanzapine",
			"chemical", "contain", "crippled", "diary", "bananas", "tired",
			"but", "quetiapine", "help", "bad", "since", "seroquel", "legs",
			"rivaroxaban", "made", "effexor", "had", "hating", "munchies",
			"pains", "muscle", "gained", "depression", "withdrawal", "was",
			"body", "natural", "taking", "can", "allergic", "causing",
			"restless", "tendon", "retention", "crying", "headaches", "fda",
			"cymbalta", "messed", "drowsy", "nightmare", "caused", "wondering",
			"fluid", "lamotrigine", "this", "neck", "nicotine", "commercial",
			"due", "definitely", "sleep", "don", "same", "been", "off", "skin",
			"bed", "hurts", "next", "found", "stone", "suicidal", "sense",
			"appetite", "experiencing", "eventually", "cripple", "distribute",
			"obese", "tummy", "wondered", "rupture", "dairy", "cataplexy",
			"disgusting", "prolactin", "addict", "nauseous", "teens",
			"advertisements", "sweaty", "bras", "paralysis", "stated",
			"zombified", "dependence", "humungous", "quetiapines", "tapering",
			"anyway", "tremors", "sensitivity", "bactrim", "lung", "dizzy",
			"boobs", "drive", "lingerie", "bizarre", "slept", "headache",
			"question", "much", "use", "ive", "worse", "time", "day", "back",
			"approved", "before", "dreams", "wasnt", "every", "hours", "out",
			"night", "though", "side", "right", "not", "treatment", "risk",
			"thoughts", "supposed", "sertraline", "reaction", "trazodone",
			"addiction", "ugh", "chemo", "mention", "sedating", "sideeffects",
			"suffer", "dry", "difficult", "affect", "when", "thank", "never",
			"enough", "throat", "little", "happy", "zyprexa", "xanax",
			"smoking", "coffee", "health", "eliquis", "needs", "currently",
			"voice", "some", "once", "helps", "name" };

	private static void getRudimentaryFeatureSet()
	{
		HashSet<String> chi_set = new HashSet<String>();
		HashSet<String> mi_set = new HashSet<String>();

		log.debug("Initializing chi_square_set!");
		for (int i = 0; i < BASE_NUM_COUNT; i++)
		{
			chi_set.add(CHI_STEADY[i]);
		}
		log.debug("There are " + CHI_STEADY.length
				+ "unique steady elements for Chi square!");

		log.debug("Initializing mi_set!");
		for (int i = 0; i < BASE_NUM_COUNT; i++)
		{
			mi_set.add(MI_STEADY[i]);
		}
		log.debug("There are " + MI_STEADY.length
				+ "unique steady elements for MI!");

		HashSet<String> temp_result = new HashSet<String>();

		temp_result.clear();
		temp_result.addAll(chi_set);
		temp_result.retainAll(mi_set);
		log.debug("They share " + temp_result.size() + " elements in common!");
		log.debug("And they are£º" + temp_result);
	}

	public static void main(String[] args)
	{
		// getRudimentaryFeatureSet();

		// use some method to do the normalization

		log.debug("Process\t>>>>>>>>>>>\tNow Let's deal with feature engineering out put!");

		// get the dictionary into the memory
		Tweet.loadDictionary(args[0]);

		// format out put
		rawText2CSV(args[1], args[2]);

	}

	public static void rawText2CSV(String input_path, String output_path)
	{
		File input_file = new File(input_path);
		File output_file = new File(output_path);

		try
		{
			Scanner tweet_scanner = new Scanner(input_file);
			PrintWriter csv_writer = new PrintWriter(output_file);
			String raw_tweet = null;
			Tweet tmp_tweet = null;
			int count = 0;
			while (tweet_scanner.hasNextLine())
			{
				count++;
				raw_tweet = tweet_scanner.nextLine();
				tmp_tweet = new Tweet(raw_tweet);
				tweet_list.add(tmp_tweet);
			}
			log.debug("All inpu data has been recorded!!!" + count);

			csv_writer.println("id," + FeatureModel.getMetaData()
					+ ",ADR_CLASS");

			log.debug("Begin to format CSV output!");

			String output_tweet_perline = null;
			for (int i = 0; i < tweet_list.size(); i++)
			{
				tmp_tweet = tweet_list.get(i);

				output_tweet_perline = FeatureModel
						.getFeatureFormatInString(tmp_tweet);
				output_tweet_perline = (tmp_tweet.getTweetID1() + ",")
						+ output_tweet_perline
						+ ("," + tmp_tweet.getADRClass());

				csv_writer.println(output_tweet_perline);
			}

			csv_writer.flush();
			tweet_scanner.close();
			csv_writer.close();

		} catch (Exception e)
		{

			e.printStackTrace();
		}

	}

}
