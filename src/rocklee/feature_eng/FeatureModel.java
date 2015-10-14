package rocklee.feature_eng;

import java.util.ArrayList;

import rocklee.preprocess.Tweet;

//this class takes

public class FeatureModel
{

	public static BodyPartFeature body_part_mentioned = new BodyPartFeature(
			"body_part_mentioned");

	public static SequenceLevelFeature seq_level_feature = new SequenceLevelFeature(
			"seq_level_feature");

	public static final String[] DIRECT_TOKEN_FEATURE =
	{ "been", "bad", "sick", "rupture", "fluid", "tummy", "dizzy", "sense",
			"lung", "sensitivity", "skin", "eventually", "anyway", "sleep",
			"dependence", "distribute", "retention", "caused", "tired", "neck",
			"off", "addict", "allergic", "diary", "help", "definitely",
			"tapering", "sweaty", "dairy", "but", "restless", "bed", "had",
			"body", "obese", "this", "weight", "nightmare", "tendon",
			"suicidal", "person", "since", "cataplexy", "drowsy", "can",
			"muscle", "disgusting", "prolactin", "stone",
			"nauseous",
			"due",
			"appetite",
			// stemming part
			// now here is some nouns which are very frequent in multiple form,
			// or verbs that are very easy to expand
			"pain", "boob", "gain", "leg", "tremor", "state", "don't", "teen",
			"mess", "advertise", "withdraw", "wonder", "headache", "cripple" };

	private static ArrayList<IndirectFeature> indirect_feature_list = null;

	public static void initializeIndirectFeatureList()
	{
		FeatureModel.indirect_feature_list = new ArrayList<IndirectFeature>();
		IndirectFeature tmp_feature = null;

		// experience
		tmp_feature = new IndirectFeature("WORD_experience", new String[]
		{ "experience", "experiencing", "experienced" });
		FeatureModel.indirect_feature_list.add(tmp_feature);

		// make
		tmp_feature = new IndirectFeature("WORD_make", new String[]
		{ "making", "made", "make" });
		FeatureModel.indirect_feature_list.add(tmp_feature);

		// zombie
		tmp_feature = new IndirectFeature("WORD_zombie", new String[]
		{ "zombie", "zombified", "zombify" });
		FeatureModel.indirect_feature_list.add(tmp_feature);

		// paralysis
		tmp_feature = new IndirectFeature("WORD_paralysis", new String[]
		{ "paralytic", "paralys", "paralyzation" });
		FeatureModel.indirect_feature_list.add(tmp_feature);

		// drive
		tmp_feature = new IndirectFeature("WORD_drive", new String[]
		{ "drive", "drove", "driven" });
		FeatureModel.indirect_feature_list.add(tmp_feature);

		// hate
		tmp_feature = new IndirectFeature("WORD_hate", new String[]
		{ "hate", "hating" });
		FeatureModel.indirect_feature_list.add(tmp_feature);

		// feel
		tmp_feature = new IndirectFeature("WORD_feel", new String[]
		{ "feel", "felt" });
		FeatureModel.indirect_feature_list.add(tmp_feature);

		// bras
		tmp_feature = new IndirectFeature("WORD_bra", new String[]
		{ " bras ", " bra " });
		FeatureModel.indirect_feature_list.add(tmp_feature);

		// cause
		tmp_feature = new IndirectFeature("WORD_cause", new String[]
		{ " cause", " caused", "causing" });
		FeatureModel.indirect_feature_list.add(tmp_feature);

		// cause
		tmp_feature = new IndirectFeature("WORD_cry", new String[]
		{ " cry ", " crying", " cried " });
		FeatureModel.indirect_feature_list.add(tmp_feature);

		// take
		tmp_feature = new IndirectFeature("WORD_take", new String[]
		{ " take ", " taking", " took " });
		FeatureModel.indirect_feature_list.add(tmp_feature);

	}

	public static String getMetaData()
	{

		// direct part
		String result = "been,bad,sick,rupture,fluid,tummy,dizzy,sense,lung,sensitivity,skin,eventually,anyway,sleep,dependence,distribute,retention,caused,tired,neck,off,addict,allergic,diary,help,definitely,tapering,sweaty,dairy,but,restless,bed,had,body,obese,this,weight,nightmare,tendon,suicidal,person,since,cataplexy,drowsy,can,muscle,disgusting,prolactin,stone,nauseous,due,appetite,pain,boob,gain,leg,tremor,state,don't,teen,mess,advertise,withdraw,wonder,headache,cripple";
		// indirect part

		for (int i = 0; i < indirect_feature_list.size(); i++)
		{
			result += "," + indirect_feature_list.get(i).getFeatureName();
		}

		// body part
		result += "," + body_part_mentioned.getFeatureName();

		// sequence level part
		result += "," + seq_level_feature.getFeatureName();

		return result;
	}

	// output is like "1,0,1,1,0,0,0,0"
	// we consider all the attribute as binary in this model
	public static String getFeatureFormatInString(Tweet tweet)
	{
		StringBuffer str_buf = new StringBuffer();

		// direct part
		if (tweet.containToken(DIRECT_TOKEN_FEATURE[0]))
			str_buf.append('1');
		else
			str_buf.append('0');

		for (int i = 1; i < DIRECT_TOKEN_FEATURE.length; i++)
		{
			str_buf.append(',');
			if (tweet.containSubString(DIRECT_TOKEN_FEATURE[i]))
				str_buf.append('1');
			else
				str_buf.append('0');
		}

		// indirect part

		for (int i = 0; i < FeatureModel.indirect_feature_list.size(); i++)
		{
			str_buf.append(',');
			if (indirect_feature_list.get(i).featureMatch(tweet))
			{// this feature shows itself in the given piece of tweet
				str_buf.append('1');

			} else
				str_buf.append('0');
		}

		// body part
		str_buf.append(',');
		if (body_part_mentioned.featureMatch(tweet))
		{// this feature shows itself in the given piece of tweet
			str_buf.append('1');

		} else
			str_buf.append('0');

		// sequence level part
		str_buf.append(',');
		if (seq_level_feature.featureMatch(tweet))
		{// this feature shows itself in the given piece of tweet
			str_buf.append('1');

		} else
			str_buf.append('0');
		
		return str_buf.toString();
	}

}
