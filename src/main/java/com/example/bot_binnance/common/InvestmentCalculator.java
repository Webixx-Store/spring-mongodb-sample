package com.example.bot_binnance.common;

import java.text.DecimalFormat;

public class InvestmentCalculator {
    public static void main(String[] args) {
    	double x = 1000;
        double loanAmount =x * 1000000; // Số tiền vay 50 triệu
        double loanInterestRate = 0.15; // Lãi suất 15% mỗi năm
        int loanYears = 15; // Thời hạn vay 5 năm
        
        calculateInvestmentGrowth(50000000 , 2000000, loanInterestRate, 5);
    }
    
    public static void calculateInvestmentGrowth(double intamout , double P, double r, int t) {
        int n = 12; // Số lần tính lãi mỗi năm (hàng tháng)
        double totalAmount = intamout;
        DecimalFormat df = new DecimalFormat("###,###,### VND");
        
        System.out.println("Tháng | Số tiền tích lũy");
        System.out.println("---------------------------");
        
        for (int month = 1; month <= t * n; month++) {
            totalAmount = totalAmount * (1 + r / n) + P;
            System.out.printf("%5d | %s\n", month, df.format(totalAmount));
        }
        
        System.out.println("\nTổng số tiền sau " + t + " năm: " + df.format(totalAmount));
    }
    
    public static void calculateLoanRepaymentReducingBalance(double P, double r, int t) {
        int n = 12; // Số lần thanh toán mỗi năm
        int totalMonths = t * n;
        double monthlyPrincipal = P / totalMonths; // Tiền gốc trả đều mỗi tháng
        DecimalFormat df = new DecimalFormat("###,###,### VND");
        
        System.out.println("\nTháng | Tiền gốc | Tiền lãi | Tổng tiền trả | Dư nợ còn lại");
        System.out.println("----------------------------------------------------------------");
        
        double remainingBalance = P;
        for (int month = 1; month <= totalMonths; month++) {
            double interest = remainingBalance * (r / n);
            double totalPayment = monthlyPrincipal + interest;
            remainingBalance -= monthlyPrincipal;
            
            System.out.printf("%5d | %s | %s | %s | %s\n", 
                              month, df.format(monthlyPrincipal), df.format(interest), 
                              df.format(totalPayment), df.format(remainingBalance > 0 ? remainingBalance : 0));
        }
        
        System.out.println("\nTổng số tiền phải trả sau " + t + " năm: " + df.format((monthlyPrincipal * totalMonths) + (P * r * t / 2))); // Trung bình lãi suất giảm dần
    }
}
