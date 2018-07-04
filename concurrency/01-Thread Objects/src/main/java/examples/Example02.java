package examples;

/**
 * Pausing Execution with Sleep
 * 
 * @date 2018.7.3
 */
public class Example02 {

	public static void main(String[] args) throws InterruptedException{
		String[] langs = {
			"c",
			"c++",
			"c#",
			"java",
			"javascript",
			"python"
		};
		for (int i = 0; i < langs.length; i++) {
			System.out.format("learning %s...%n", langs[i]);
			Thread.sleep(4000);
		}
		
		
	}

}
