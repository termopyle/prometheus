package org.kobic.omics.utils;

import org.junit.Test;
import org.kobic.omics.common.utils.Utils;

public class UtilsTest {
	
	@Test
	public void mail() {
		
		Utils app = new Utils();
		
		for(int i = 0; i < 100; i++) {
			app.sendMail("kogun82", "!@#$", "cloud_team@kobic.kr");
		}
	}
}
