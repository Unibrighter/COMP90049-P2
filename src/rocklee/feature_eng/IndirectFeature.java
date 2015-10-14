package rocklee.feature_eng;

import java.util.ArrayList;

import rocklee.preprocess.Tweet;

//every instance of this class would be considered as a filter
//to get the stemming form of a verb back into its original form
//e.g feeling => felt=> feels
//
//Also, this filter's instance takes care of nouns in multiple forms
//like withdraw=>withdrawal(s)

public class IndirectFeature
{

	private String feature_name = null;

	private ArrayList<String> various_form = null;

	// if we are pretty sure that we can identify this feature just with one
	// single string
	// then we can leave the various form blank
	public IndirectFeature(String feature_name, String[] various_array)
	{
		this.feature_name = feature_name;
		
		this.various_form=new ArrayList<String>();
		for (int i = 0; i < various_array.length; i++)
		{
			various_form.add(various_array[i]);
		}
		
	}

	// to see if the given feature is present in this piece of tweet or not
	public boolean featureMatch(Tweet tweet)
	{
		String tweet_content = tweet.getFullContent();
		String aim_string = null;
		for (int i = 0; i < various_form.size(); i++)
		{
			aim_string = various_form == null ? feature_name : various_form
					.get(i);
			if (tweet_content.indexOf(aim_string) != -1)
			{
				return true;// found a valid match
			}
		}
		return false;
	}

	public String getFeatureName()
	{
		return this.feature_name;
	}

}
