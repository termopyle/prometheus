package org.kobic.omics.parser;

import java.io.*;

public class SplitByLinenumber {
	public static void main(String args[]) {
		try {

		String fileName = "";
		long splitLine = 1000000;
		boolean firstHeadLine = true;
		String tmpStr = "";
		String firstLineStr = "";

		if (args.length >= 1)
			fileName = args[0];
		if (args.length >= 2) {
			splitLine = Long.parseLong(args[1]);
		}
		if (args.length >= 3 && (args[2].equals("N") || args[2].equals("n"))) {
			firstHeadLine = false;
		}

		System.out.println("원본 파일명 : " + fileName);
		System.out.println("파일당 줄수 : " + splitLine);
		System.out.println("머리글 여부 : " + firstHeadLine);

		if (args.length < 1) {
			System.out.println("사용법 : java SplitByLinenumber [파일명] [파일당 줄개수(default:1000000)] [Y/n = 첫행 머리글 여부]");
			System.out.println("파일당 줄개수(행, 자료수) default 값은 백만개, 첫행이 머리글이면 Y(default), 첫행부터 바로 자료 시작이면 n");
			System.exit(-1);
		}

		if (firstHeadLine)
			splitLine++;

		File file = new File(fileName);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		int n = 1;
		long i = 0;
		File wrFile = new File(fileName+"_"+n+".txt");
		FileWriter fw = new FileWriter(wrFile);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		while ((tmpStr=br.readLine()) != null) {
			if (i == 0)
				firstLineStr = tmpStr;
			pw.println(tmpStr);
			i++;
			if (i >= splitLine) {
				pw.close();
				pw = null;
				bw.close();
				bw = null;
				fw.close();
				fw = null;
				wrFile = null;
				n++;
				wrFile = new File(fileName+"_"+n+".txt");
				fw = new FileWriter(wrFile);
				bw = new BufferedWriter(fw);
				pw = new PrintWriter(bw);
				i = 0;
				if (firstHeadLine) {
					pw.println(firstLineStr);
					i++;
				}
			}
		}
		pw.close();
		pw = null;
		bw.close();
		bw = null;
		fw.close();
		fw = null;
		wrFile = null;

		br.close();
		fr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}