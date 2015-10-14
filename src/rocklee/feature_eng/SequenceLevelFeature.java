package rocklee.feature_eng;

import rocklee.preprocess.Tweet;

//check if there is pattern like 
//here we just use "make (me|my body part) XXXX"
//but also , we can use some NLA approach to gain more patterns like this 
//and test whether a raw tweet has a matching tweet or not 

//if we have enough time to expand this 
//we shall set this class as an abstract Class

//a specific pattern would have to extend this class s

public class SequenceLevelFeature
{
	
	private  String feature_name=null;
	
	
	public static final  String[] SEQUENCE_PATTERN={""};
	
	public SequenceLevelFeature(String feature_name)
	{
		this.feature_name=feature_name;
	}
	
	public  boolean featureMatch(Tweet tweet)
	{
		String tmp_pattern="(make|making|made)\\s.*";
		String tweet_content=tweet.getFullContent();
		for (int i = 0; i < BodyPartFeature.BODY_PART_NOUN.length; i++)
		{
			tmp_pattern+="("+BodyPartFeature.BODY_PART_NOUN[i]+")";
			if(tweet_content.matches(tmp_pattern))
			{
				return true;
			}
		}
		
		return false;
		
	}
	
	public String getFeatureName()
	{
		return this.feature_name;
	}
	
	
}
