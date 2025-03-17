package com.example.bot_binnance.common;

import com.example.bot_binnance.dto.trade.FeeBinnance;

public class BinanceFeeCalculator {
    private static final double FEE_RATE = 0.0005; 

    public static FeeBinnance calculateFeesAndProfit(double entryPrice, double exitPrice) {
        double buyCost = entryPrice * PrivateKeyBinnance.QUANTITY;
        double sellRevenue = exitPrice * PrivateKeyBinnance.QUANTITY;
        
        double buyFee = buyCost * FEE_RATE;
        double sellFee = sellRevenue * FEE_RATE;
        double totalFees = buyFee + sellFee;
        boolean isLong = exitPrice > entryPrice; 
        double sideFactor = isLong ? -1 : 1; 
        double netProfit = (entryPrice - exitPrice) * PrivateKeyBinnance.QUANTITY * sideFactor - totalFees;
        return new FeeBinnance(buyFee, sellFee, netProfit, totalFees);
    }
    

}
