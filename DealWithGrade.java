package com.javachobo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class DealWithGrade {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Student> list = new ArrayList<Student>();
		
		Scanner scanner = null;
		
		try{
			scanner = new Scanner(new File(args[0]));			
		} catch(Exception e){
			System.out.println(args[0] + "을(를) 찾을 수 없습니다.");
			System.exit(0);
		}
		
		while(scanner.hasNext()){
			String line = scanner.nextLine();
			String[] splittedStr = line.split(",");
			String name = splittedStr[0].trim();
			int classNo = Integer.parseInt(splittedStr[1]);
			int studentNo = Integer.parseInt(splittedStr[2]);
			int Korean = Integer.parseInt(splittedStr[3]);
			int Math = Integer.parseInt(splittedStr[4]);
			int English = Integer.parseInt(splittedStr[5]);
			
			list.add(new Student(name, classNo, studentNo, Korean, Math, English));
			
		}
		 //                        이름, 반, 번호, 국어, 수학, 영어 
//        list.add(new Student("남궁성", 3,2,100,100,100)); 

        calculateSchoolRank(list);
        calculateClassRank(list);
        printList(list);

	}
	
	public static void calculateClassRank(List<Student> list){
		Collections.sort(list, new ClassTotalComparator());
		
		int prevClass = -1;
		int prevRank = -1;
		int prevTotal = -1;
		int length = list.size();
		
		for(int i=0, n=1; i<length; i++, n++){
			Student student = list.get(i);
			
			if(student.getClassNo() != prevClass){
				prevRank = -1;
				prevTotal = -1;
				n = 1;
			}
			if(student.getTotal() != prevTotal){
				student.classRank = n;
			} else{
				student.classRank = prevRank;
			}
			prevClass = student.getClassNo();
			prevRank = student.classRank;
			prevTotal = student.getTotal();		
		}
	}
	
	public static void calculateSchoolRank(List<Student> list){
		Collections.sort(list);
		
		int prevRank = -1;
		int prevTotal = -1;
		int length = list.size();
		
		for(int i=0; i<length; i++){
			if(list.get(i).getTotal() != prevTotal){
				list.get(i).schoolRank = i + 1;
			} else{
				list.get(i).schoolRank = prevRank;
			}
			prevRank = list.get(i).schoolRank;
			prevTotal = list.get(i).getTotal();
		}
	}
	
	public static void printList(List<Student> list){
		String header = Student.format("이름", 5, Student.LEFT)
							+Student.format("반", 4, Student.RIGHT)
							+Student.format("번호", 3, Student.RIGHT)
							+Student.format("국어", 4, Student.RIGHT)
							+Student.format("수학", 4, Student.RIGHT)
							+Student.format("영어", 4, Student.RIGHT)
							+Student.format("총점", 5, Student.RIGHT)
							+Student.format("전교등수", 5, Student.RIGHT)
							+Student.format("반등수", 5, Student.RIGHT);
		System.out.println(header); 
		System.out.println("===========================================================");
		for(Student s : list){
			System.out.println(s);
		}
		System.out.println("===========================================================");
	}

}

class Student implements Comparable<Student>{
	final static int LEFT = 0;
	final static int RIGHT = 1;
	final static int CENTER = 2;

	private String name;
	private int classNo;
	private int studentNo;
	private int Korean;
	private int Math;
	private int English;
	private int Total;
	int schoolRank;
	int classRank;
	
	public Student(String name, int classNo, int studentNo, int Korean, int Math, int English) {
		this.name = name;
		this.classNo = classNo;
		this.studentNo = studentNo;
		this.Korean = Korean;
		this.Math = Math;
		this.English = English;
		this.Total = Korean + Math + English;
	}
	
	@Override
	public String toString() {
		return format(name, 5, LEFT)
				+format(""+classNo, 4, 	RIGHT)
				+format(""+studentNo, 4, RIGHT)
				+format(""+Korean, 6, RIGHT)
				+format(""+Math, 6, RIGHT)
				+format(""+English, 6, RIGHT)
				+format(""+Total, 8, RIGHT)
				+format(""+schoolRank, 8, RIGHT)
				+format(""+classRank, 8, RIGHT);
	}

	@Override
	public int compareTo(Student obj) {
		 
		return obj.getTotal() - this.Total;
	}
	
	public static String format(String str, int length, int alignment){
		if(str == null)str = "";
		
		int diff = length - str.length();
		if(diff < 0)
			return str.substring(0, length);
		
		char[] source = str.toCharArray();
		char[] result = new char[length];
		
		for(int i=0; i<result.length; i++){
			result[i] = ' ';
		}
	
		int startPos = 0;
		
		if(diff%2 == 0){
			startPos = diff / 2 -1;
		} else{
			startPos = diff / 2;
		}
		
		if(alignment == CENTER){
			System.arraycopy(source, 0, result, startPos, source.length);
		} else if(alignment == LEFT){
			System.arraycopy(source, 0, result, 0, source.length);
		} else if(alignment == RIGHT){
			System.arraycopy(source, 0, result, diff, source.length);
		}
		
		return new String(result);
	}

	public String getName() {
		return name;
	}

	public int getClassNo() {
		return classNo;
	}

	public int getStudentNo() {
		return studentNo;
	}

	public int getKorean() {
		return Korean;
	}

	public int getMath() {
		return Math;
	}

	public int getEnglish() {
		return English;
	}

	public int getTotal() {
		return Total;
	}
}

class ClassTotalComparator implements Comparator<Student>{

	@Override
	public int compare(Student student1, Student student2) {
		int result = student1.getClassNo() - student2.getClassNo();
		
		if(result == 0){
			result = student2.getTotal() - student1.getTotal();
		}
		return result;
//		if(student1.getClassNo() > student2.getClassNo()){
//			return 1;
//		} else if(student1.getClassNo() == student2.getClassNo()){
//			if(student1.getTotal() > student2.getTotal()){
//				return -1;
//			} else if(student1.getTotal() == student2.getTotal()){
//				return 0;
//			} else{
//				return 1;
//			}
//		} else{
//			return -1;
//		}
	}
}

class ClassStudentNoComparator implements Comparator<Student>{

	@Override
	public int compare(Student student1, Student student2) {
		int result = student1.getClassNo() - student2.getClassNo();
		
		if(result == 0){
			result = student1.getStudentNo() - student2.getStudentNo();
		}
		return result;
		
//		if(student1.getClassNo() > student2.getClassNo()){
//			return 1;
//		} else if(student1.getClassNo() == student2.getClassNo()){
//			if(student1.getStudentNo() > student2.getStudentNo()){
//				return 1;
//			} else if(student1.getStudentNo() == student2.getStudentNo()){
//				return 0;
//			} else{
//				return -1;
//			}
//		} else{
//			return -1;
//		}	
	}
}
