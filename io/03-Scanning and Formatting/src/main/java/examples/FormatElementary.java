package examples;

public class FormatElementary {

	public static void main(String[] args) {

		print();

		format();

	}

	private static void print() {
		int i = 2;
		double r = Math.sqrt(i);

		System.out.print("The square root of ");
		System.out.print(i);
		System.out.print(" is ");
		System.out.print(r);
		System.out.println(".");

		i = 5;
		r = Math.sqrt(i);
		System.out.println("The square root of " + i + " is " + r + ".");
	}

	/**
	 * The elements must appear in the order shown. Working from the right, the
	 * optional elements are:
	 * 
	 * Precision. For floating point values, this is the mathematical precision of the formatted value. For s and other general conversions, this is the maximum
	 * width of the formatted value; the value is right-truncated if necessary.
	 * Width. The minimum width of the formatted value; the value is padded if
	 * necessary. By default the value is left-padded with blanks. 
	 * Flags specify additional formatting options. In the Format example, the + flag specifies
	 * that the number should always be formatted with a sign, and the 0 flag
	 * specifies that 0 is the padding character. Other flags include - (pad on the
	 * right) and , (format number with locale-specific thousands separators). Note
	 * that some flags cannot be used with certain other flags or with certain
	 * conversions. 
	 * The Argument Index allows you to explicitly match a designated
	 * argument. You can also specify < to match the same argument as the previous
	 * specifier. Thus the example could have said: System.out.format("%f,
	 * %<+020.10f %n", Math.PI);
	 * 
	 * https://docs.oracle.com/javase/tutorial/essential/io/formatting.html
	 */
	private static void format() {
		int i = 2;
		double r = Math.sqrt(i);

		System.out.format("The square root of %d is %f.%n", i, r);

		System.out.format("%f, %1$+020.10f %n", Math.PI);

		System.out.format("%f, %<+020.10f %n", Math.PI);
	}

}
