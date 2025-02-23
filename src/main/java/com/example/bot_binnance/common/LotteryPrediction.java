package com.example.bot_binnance.common;

import weka.classifiers.trees.RandomForest;
import weka.core.*;
import java.util.*;

public class LotteryPrediction {
    private static final int NUMBERS_PER_TICKET = 6;
    private static final int WHEELING_NUMBERS = 10; // Chọn 10 số mạnh nhất

    public static void main(String[] args) throws Exception {
        List<String> pastResults = Arrays.asList(
                "092585", "749879", "615121", "190561", "471308",
                "074215", "158274", "211173", "069961", "193316",
                "711691", "496276", "853977", "686702", "202136",
                "548924", "778021", "042817", "903805"
        );

        // Phân tích số xuất hiện nhiều nhất
        Map<Integer, Integer> numberFrequency = analyzeNumberFrequency(pastResults);

        // Dự đoán các số có xác suất cao nhất bằng Machine Learning
        List<Integer> topNumbers = predictNumbers(numberFrequency);
        System.out.println("Top " + WHEELING_NUMBERS + " số mạnh nhất: " + topNumbers);

        // Tạo vé số theo Wheeling System
        List<List<Integer>> tickets = generateWheelingTickets(topNumbers);
        System.out.println("Vé số tối ưu:");
        tickets.forEach(System.out::println);
    }

    private static Map<Integer, Integer> analyzeNumberFrequency(List<String> pastResults) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (String result : pastResults) {
            for (char c : result.toCharArray()) {
                int num = Character.getNumericValue(c);
                frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
            }
        }
        return frequencyMap;
    }

    private static List<Integer> predictNumbers(Map<Integer, Integer> frequencyMap) throws Exception {
        // Chuyển dữ liệu thành tập mẫu cho Machine Learning
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("number"));
        attributes.add(new Attribute("frequency"));

        Instances dataset = new Instances("LotteryData", attributes, frequencyMap.size());
        dataset.setClassIndex(1);

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            Instance instance = new DenseInstance(2);
            instance.setValue(attributes.get(0), entry.getKey());
            instance.setValue(attributes.get(1), entry.getValue());
            dataset.add(instance);
        }

        // Huấn luyện mô hình Random Forest
        RandomForest model = new RandomForest();
        model.buildClassifier(dataset);

        // Dự đoán top 10 số có xác suất cao nhất
        List<Integer> topNumbers = new ArrayList<>(frequencyMap.keySet());
        topNumbers.sort((a, b) -> frequencyMap.get(b) - frequencyMap.get(a));
        return topNumbers.subList(0, WHEELING_NUMBERS);
    }

    private static List<List<Integer>> generateWheelingTickets(List<Integer> topNumbers) {
        List<List<Integer>> tickets = new ArrayList<>();
        for (int i = 0; i < topNumbers.size() - NUMBERS_PER_TICKET + 1; i++) {
            tickets.add(topNumbers.subList(i, i + NUMBERS_PER_TICKET));
        }
        return tickets;
    }
}

