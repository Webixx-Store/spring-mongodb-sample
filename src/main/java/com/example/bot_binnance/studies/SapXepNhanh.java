package com.example.bot_binnance.studies;

import java.util.Arrays;
import java.util.Random;

public class SapXepNhanh {
    
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    private int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (arr[j] < pivot) {
                swap(arr, ++i, j);
            }
        }
        swap(arr, i + 1, right);
        return i + 1;
    }
    
    public void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(arr, left, right);
            quickSort(arr, left, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, right);
        }
    }

    public void display(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }
    public int[] generateRandomArray(int size, int min, int max) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt((max - min) + 1) + min;
        }
        return arr;
    }
    

    public static void main(String[] args) {
        SapXepNhanh sapXepNhanh = new SapXepNhanh();
        int[] arr = sapXepNhanh.generateRandomArray(10000, 0, 1000000000);

        System.out.println("Bắt đầu sắp xếp...");

        long startTime = System.nanoTime(); // Bắt đầu đo thời gian
        sapXepNhanh.quickSort(arr, 0, arr.length - 1);
        long endTime = System.nanoTime(); // Kết thúc đo thời gian

        long duration = (endTime - startTime) / 1_000_000; // Chuyển từ nano giây sang mili giây

        System.out.println("Mảng sau khi sắp xếp:");
        sapXepNhanh.display(arr);
        System.out.println("Thời gian thực thi: " + duration + " ms");
    }

}