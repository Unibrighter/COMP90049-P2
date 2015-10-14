package rocklee.feature_eng;

import java.util.ArrayList;

import rocklee.preprocess.Tweet;

//this is a class to determine if a particular body part noun has been mentioned 
//in this tweet
//Note: This is not limited to body parts
//for some other collection or dictionary
//we can also use the similar structure to tell whether this kind of feature is 
//mentioned in the given tweet.

public class BodyPartFeature
{
	public static final String[] BODY_PART_NOUN={"abdomen","adenoids","adrenal","gland","anatomy","ankle","anus","appendix","arch","arm","artery","back","belly","bladder","blood","bloodvessels","body","bone","brain","breast","buttocks","calf","capillary","carpal","cartilage","cell","cervical","cheek","chest","chin","circulatorysystem","clavicle","coccyx","diaphragm","digestivesystem","ear","elbow","endocrine","esophagus","eye","eyebrow","eyelashes","eyelid","face","fallopian","feet","femur","fibula","filling","finger","fingernail","follicle","foot","forehead","gallbladder","glands","groin","gums","hair","hand","head","heart","heel","hip","humerus","immunesystem","instep","intestines","iris","jaw","kidney","knee","larynx","leg","ligament","lip","liver","lobe","lumbar","vertebrae","lungs","lymph","mandible","metacarpal","metatarsal","molar","mouth","muscle","nail","navel","neck","nerves","nipple","nose","nostril","organs","ovary","palm","pancreas","patella","pelvis","phalanges","pharynx","pinky","pituitary","pore","pupil","radius","rectum","respiratory","ribs","sacrum","scalp","scapula","senses","shin","shoulder","skeleton","skin","skull","sole","spinal","spine","spleen","sternum","stomach","tarsal","teeth","tendon","testes","thigh","thorax","throat","thumb","thyroid","tibia","tissue","toe","toenail","tongue","tonsils","tooth","torso","trachea","ulna","ureter","urethra","urinary","uterus","uvula","vein","vertebra","waist","wrist"};
	
	private String feature_name=null;
	
	private ArrayList<String> various_form=null;
	
	public BodyPartFeature(String feature_name)
	{
		this.feature_name=feature_name;
		this.getDictionary();
		
	}
	
	//usually this method would choose a file as dictionary input
	//here we just use a build-in String array
	private void getDictionary()
	{
		various_form=new ArrayList<String>();
		for (int i = 0; i < BODY_PART_NOUN.length; i++)
		{
			various_form.add(BODY_PART_NOUN[i]);
		}
	}
	
	
	//to see if the given feature is present in this piece of tweet or not
	public boolean featureMatch(Tweet tweet)
	{
		String tweet_content=tweet.getFullContent();
		for (int i = 0; i < various_form.size(); i++)
		{
			String aim_string=various_form.get(i);
			if(tweet_content.indexOf(aim_string)!=-1)
			{
				return true;//found a valid match
			}
		}
		return false;
	}
	
	public String getFeatureName()
	{
		return this.feature_name;
	}
	
}
