package org.kobic.omics.common.utils;

import java.io.File;
import java.io.IOException;
import java.rmi.server.UID;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.kobic.omics.common.utils.Utils;

public class Utils {

	private static File file;

	public String getMakeNewId() {
		String id = new UID().toString().replace(":", "_").replace("-", "_");
		return id;
	}

	public String getDate() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(dt).toString();
	}
	
	public String getDate(String title) {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
		return title + "_" + sdf.format(dt).toString();
	}


	public String getPropertiesValue(String key) {

		Properties props = new Properties();

		String value = "";

		try {
			props.load(getClass().getResourceAsStream("props/globals.properties"));
			value = (String) props.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return value;
	}

	public static Properties getProperties() {
		Properties props = new Properties();

		try {
			props.load(Utils.class.getResourceAsStream("props/globals.properties"));
		} catch (IOException e) {

			e.printStackTrace();
		}

		return props;
	}

	public static String getFileName(String path) {
		file = new File(path);
		return file.getName();
	}

	public void sendMail(String userId, String tempPassword, String email) {

		MultiPartEmail multiPartEmail = new MultiPartEmail();
		multiPartEmail.setHostName("webmail.kobic.kr");

		try {
			String title = "[KOBIC] %s's temporary password has been issued.";
			String body = "The temporary password is [%s].\n"
					+ "Make sure to change it immediately after log-in.\n"
					+ "This message is a post-only email with no reply made to your inquiries.\n\n";

			multiPartEmail.setSubject(String.format(title, userId));
			multiPartEmail.setMsg(String.format(body, tempPassword));
			multiPartEmail.setFrom("biodata@kobic.kr", "Development Team");
			multiPartEmail.addBcc("biodata@kobic.kr");
			multiPartEmail.addTo(email);
			multiPartEmail.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	public boolean identify(HttpServletRequest request) {

		boolean result = true;

		String isAdmin = (String) request.getSession().getAttribute("is_admin");
		String isLogin = (String) request.getSession().getAttribute("is_login");

		System.out.println(isAdmin + ":" + isLogin);

		if (isLogin != null && isAdmin != null) {
			if (Boolean.parseBoolean(isLogin) && Boolean.parseBoolean(isAdmin)) {
				return result;
			}
		} else {
			return false;
		}

		return false;
	}

	public static String millisToDate(long millis){
		  
		Date currentDate = new Date(millis);

		DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		
		return df.format(currentDate);
	}
	
	public static String humanReadableByteCount(long bytes, boolean si) {
	    int unit = si ? 1000 : 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
}
