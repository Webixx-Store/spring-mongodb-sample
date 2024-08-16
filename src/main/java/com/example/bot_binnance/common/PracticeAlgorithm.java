package com.example.bot_binnance.common;

import java.util.ArrayList;
public class PracticeAlgorithm {
	private ArrayList<Integer> lesson1() {
		ArrayList<Integer> arr  = new ArrayList<>() ; 
		int i = 14;
		while (i < 200) {
			if(!PracticeAlgorithm.isMultiple(i, 5) && PracticeAlgorithm.isMultiple(i, 7)) {
				arr.add(i);
			}
			i= i + 14;
		}
		return arr;
	}
	
	
	
	private int [] reverseArray(int [] arr) {
		
		int reverseArr[] = new int[arr.length];
		int size = arr.length;
		int i  = size - 1;
		int x = 0;
		while(i>=0) {
			reverseArr[x] = arr[i];
			i = i -1;	
			x = x+1;
		}
		
		return reverseArr;
	}
	
	
	private int commonConvention(int a, int b) {
		
		int x = a < b ? a : b;
		if (a % b ==0) return b;
		
		if (b % a ==0) return a;
	    
		while ( x  >=  0) {
			if(a%x == 0 && b%x ==0) {
				return x;
			}
			x = x - 1;
			
		}
		return 0;
		
		
	}
	
	private static boolean isMultiple(int num , int base) {
		if(num % base == 0) {
			return true;
		}else {
			return false;
		}
	}
	
	 public static void printArray(int[] arr) {
	        System.out.print("Các phần tử của mảng: ");
	        for (int i = 0; i < arr.length; i++) {
	            System.out.print(arr[i]);
	            if (i < arr.length - 1) {
	                System.out.print(", "); // In dấu phẩy nếu chưa phải là phần tử cuối cùng
	            }
	        }
	        System.out.println(); // In xuống dòng sau khi kết thúc
	}
	
	public static void main(String[] args) {
		PracticeAlgorithm practiceAlgorithm = new PracticeAlgorithm();
		System.out.println(practiceAlgorithm.commonConvention(8420, 84208420)); 
	}

}
