package com.javachobo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.stream.Stream;

public class ConsoleEx1 {

	static String[] argArr;
	static LinkedList<String> q = new LinkedList<String>();
	static final int MAX_SIZE = 5;

	static File curDir;

	static {
		String fileDir = System.getProperty("user.dir");
		try {
			curDir = new File(fileDir);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);

		while (true) {
			try {
				String prompt = curDir.getCanonicalPath() + ">>";
				System.out.print(prompt);
				String input = scanner.nextLine();

				save(input);

				input = input.trim();
				argArr = input.split(" +");

				String command = argArr[0].trim();

				if ("".equals(command))
					continue;

				command = command.toLowerCase();

				if (command.equals("q")) {
					System.exit(0);
				} else if (command.equals("history")) {
					history();
				} else if (command.equals("dir")) {
					dir();
				} else if (command.equals("type")) {
					type();
				} else if(command.equals("find")){
					find();
				} else if(command.equals("find2")){
					find2();
				} else if(command.equals("cd")){
					cd();
				} else {
					for (int i = 0; i < argArr.length; i++) {
						System.out.println(argArr[i]);
					}
				}
			} catch (Exception e) {
				System.out.println("입력오류입니다.");
			}

		}
	}

	public static void save(String input) {
		if (input == null || "".equals(input))
			return;

		q.add(input);
		if (q.size() > MAX_SIZE) {
			q.removeFirst();
		}
	}

	public static void history() {
		int i = 0;
		ListIterator<String> li = q.listIterator();
		while (li.hasNext()) {
			System.out.println(++i + ". " + li.next());
		}
		// for(i=0; i<q.size(); i++){
		// System.out.println((i+1)+". "+q.get(i));
		// }
	}

	public static void dir() {
		String pattern = "";
		File[] files = curDir.listFiles();

		switch (argArr.length) {
		case 1:
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					System.out.println("[" + files[i].getName() + "]");
				} else {
					System.out.println(files[i].getName());
				}
			}
			break;
		case 2:
			pattern = argArr[1];

			pattern = pattern.replace(".", "[.]");
			pattern = pattern.replace("*", "[A-Za-z0-9]+");
			pattern = pattern.replace("?", ".");
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().matches(pattern)) {
					if (files[i].isDirectory()) {
						System.out.println("[" + files[i].getName() + "]");
					} else {
						System.out.println(files[i].getName());
					}
				}
			}

			break;
		default:
			System.out.println("USAGE : dir [FILENAME]");
		} // switch
	}

	public static void type() throws IOException {
		if (argArr.length != 2) {
			System.out.println("Usage :type FILE_NAME");
			return;
		}

		String fileName = argArr[1];
		File temp = new File(curDir, fileName);

		if(temp.isFile()){
			FileReader fr = new FileReader(temp);
			BufferedReader br = new BufferedReader(fr);
			
//			String str = null;
//			while((str = br.readLine()) != null){
//				System.out.println(str);
//			}
			Stream<String> stream = br.lines();
			Iterator<String> iterator = stream.iterator();
			while(iterator.hasNext()){
				System.out.println(iterator.next());
			}
			br.close();
			fr.close();
		} else{
			System.out.println("존재하지 않는 파일입니다.");
		}
	}
	
	public static void find() throws IOException{
		if(argArr.length != 3){
			System.out.println("Usage :find KEYWORD FILE_NAME");
			return;
		}
		
		String keyword = argArr[1];
		String fileName = argArr[2];
		
		File temp = new File(fileName);

		BufferedReader br = new BufferedReader(new FileReader(temp));
		
		if(temp.isFile()){
			String str = "";
			int lineNum = 1;
			while((str = br.readLine()) != null){
				if(str.contains(keyword)){
					System.out.println(lineNum+": "+str);
				}
				lineNum++;
			}
		} else{
			System.out.println("존재하지 않는 파일입니다.");
		}
	}
	
	public static void find2() throws IOException{
		if(argArr.length != 3){
			System.out.println("Usage :find2 KEYWORD FILE_NAME");
			return;
		}
		
		String keyword = argArr[1];
		String pattern = argArr[2];

		pattern = pattern.replace(".", "\\.");
		pattern = pattern.replace("*", "[A-Za-z0-9]+");
		pattern = pattern.replace("?", ".");

		File[] files = curDir.listFiles();
		for(int i=0; i<files.length; i++){
			if(files[i].getName().matches(pattern)){
				FileReader fr = new FileReader(files[i]);
				BufferedReader br = new BufferedReader(fr);
				
				String str = "";
				int flagForName = 1;
				for(int lineNum=1; (str = br.readLine()) != null; lineNum++){
					if(str.contains(keyword)){
						if(flagForName == 1){
							System.out.println("---------------"+files[i].getName());
							flagForName = 0;
						}
						System.out.println(lineNum + ": " + str);
					}
				}
				br.close();
				fr.close();
			}
		}
	}
	
	public static void cd(){
		if(argArr.length == 1){
			System.out.println(curDir);
			return;
		} else if(argArr.length > 2){
			System.out.println("Usage :cd [DIRECTORY]");
			return;
		}
		
		String subDir = argArr[1];
		
		if(subDir.equals("..")){
			File temp = curDir.getParentFile();
			if(temp == null){
				System.out.println("유효하지 않은 디렉토리입니다.");
			} else{
				curDir = temp;
			}
		} else if(subDir.equals(".")){
			System.out.println(curDir);
		} else{
//			String subPath = curDir.getAbsolutePath()+ "\\" + subDir;
//			File subFile = new File(subPath);
			
			File subFile = new File(curDir, subDir);
			
			if(subFile.exists() && subFile.isDirectory()){
				curDir = subFile;
			} else{
				System.out.println("유효하지 않은 디렉토리입니다.");
			}
		}
		
	}
}
