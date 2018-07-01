package examples;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;

/**
 * @date 2018.7.1
 * @since JDK 8
 */
public class ObjectStreamElementary {

	static final String dataFile = "invoicedata";

	static final BigDecimal[] prices = { 
			BigDecimal.valueOf(19.99), 
			BigDecimal.valueOf(29.99), 
			BigDecimal.valueOf(15.99), 
			BigDecimal.valueOf(3.1), 
			BigDecimal.valueOf(4.99)
	};
	static final int[] units = { 12, 8, 13, 29, 50 };
	static final String[] descs = { "Java T-shirt", "Java Mug", "Duke Juggling Dolls", "Java Pin", "Java Key Chain" };

	public static void main(String[] args) {
		try {
			demo();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private static void demo() throws IOException, ClassNotFoundException {
		//写入文件(二进制文件)
		ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)));
		for (int i = 0; i < prices.length; i++) {
			out.writeObject(prices[i]);
			out.writeInt(units[i]);
			out.writeUTF(descs[i]);
		}
		out.flush();
		out.close();

		//读取文件(二进制文件)
		ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(dataFile)));

		BigDecimal price;
		int unit;
		String desc;
		BigDecimal total = BigDecimal.ZERO;
		
		try {
		    while (true) {
		        price = (BigDecimal) in.readObject();
		        unit = in.readInt();
		        desc = in.readUTF();
		        System.out.format("You ordered %d" + " units of %s at $%.2f%n",
		            unit, desc, price);
		        total = total.add(BigDecimal.valueOf(unit).multiply(price));
		    }
		} catch (EOFException e) {
		}
		in.close();
	}
}
