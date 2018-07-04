package examples;

/**
 * Interrupts
 * 
 * @date 2018.7.3
 */
public class Example03 {

	public static void main(String[] args) {
		String[] langs = { "c", "c++", "c#", "java", "javascript", "python" };
		for (int i = 0; i < langs.length; i++) {
			System.out.format("learning %s...%n", langs[i]);

			try {
				if ("java".equals(langs[i])) {
					throw new InterruptedException();
				}
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				return;
			}
			
		}
	}

}
