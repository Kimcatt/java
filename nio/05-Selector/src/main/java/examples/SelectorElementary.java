package examples;

/**
 * @date 2018.6.25
 */
public class SelectorElementary {

	public static void main(String args[]) throws Exception {
		/*
		if (args.length <= 0) {
			System.err.println("Usage: java MultiPortEcho port [port port …]");
			System.exit(1);
		}
		*/
		int ports[] = new int[args.length];
		for (int i = 0; i < args.length; ++i) {
			ports[i] = Integer.parseInt(args[i]);
		}
		
		if (args.length <= 0) {
			ports = new int[] {8888, 9999};
		}
		
		new MultiPortEchoService(ports);
	}

}
