package rocklee.Main;


public class Main
{

	public static void main(String[] args)
	{
		String s = ": Pop a vyvanse I'm workin'\" I can't stop working! Lmao";
		String[] words = s.replaceAll("[^a-zA-Z ]", "").toLowerCase()
				.split("\\s+");
		for (int i = 0; i < words.length; i++)
		{
			System.out.println(words[i]);

		}

	}
}
