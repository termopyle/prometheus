package org.kobic.omics.cache;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import turbo.cache.lite.thrift.service.client.CacheLiteClient;

public class CacheLiteTest {

	@Test
	public void read() throws IOException {
		
//		CacheLiteClient client = new CacheLiteClient();
//		List<String> content = client
//				.read("/express/hgh87/closha/project/last_2017_07_25_06_59_10/output/maf_split/output.split.png");
//
		File f = new File("D:/test.jpg");
//
//		int width = 263;
//		int height = 340;
//		BufferedImage image = null;
//
//		try {
//			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//			image = ImageIO.read(f);
//
//			System.out.println("Reading complete.");
//
//		} catch (IOException e) {
//			System.out.println("Error: " + e);
//		}
//
//		try {
//			f = new File("D:/Output.jpg"); // output file path
//			ImageIO.write(image, "jpg", f);
//			System.out.println("Writing complete.");
//		} catch (IOException e) {
//			System.out.println("Error: " + e);
//		}
		
		/**
		 * 
		 */
		
		// read "any" type of image (in this case a png file)
	    BufferedImage image = ImageIO.read(f);

	    // write it to byte array in-memory (jpg format)
	    ByteArrayOutputStream b = new ByteArrayOutputStream();
	    ImageIO.write(image, "jpg", b);
	    
	    // do whatever with the array...
	    byte[] jpgByteArray = b.toByteArray();

	    // convert it to a String with 0s and 1s        
	    StringBuilder sb = new StringBuilder();
	    for (byte by : jpgByteArray){
	        sb.append(Integer.toBinaryString(by & 0xFF));
	    }
	    
	    String byteToString = new String(jpgByteArray);
	    
	    System.out.println(new ByteArrayInputStream(byteToString.getBytes()).hashCode());
	    System.out.println(jpgByteArray.hashCode());
	    
	    byte[] imageInByte= new BigInteger(Hex.encodeHexString(jpgByteArray), 16).toByteArray();
	    
	    image = ImageIO.read(new ByteArrayInputStream(jpgByteArray));
	    
	    System.out.println(image);
	    
	    System.out.println(ImageIO.read(new ByteArrayInputStream(image.toString().getBytes())));
	    
	    /**
	     * 
	     */
	    
	}
	
//	public static String imgToBase64String(final RenderedImage img, final String formatName) {
//	    final ByteArrayOutputStream os = new ByteArrayOutputStream();
//	    try {
//	        ImageIO.write(img, formatName, Base64.getEncoder().wrap(os));
//	        return os.toString(StandardCharsets.ISO_8859_1.name());
//	    } catch (final IOException ioe) {
//	        throw new UncheckedIOException(ioe);
//	    }
//	}
//
//	public static BufferedImage base64StringToImg(final String base64String) {
//	    try {
//	        return ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(base64String)));
//	    } catch (final IOException ioe) {
//	        throw new UncheckedIOException(ioe);
//	    }
//	}
}
