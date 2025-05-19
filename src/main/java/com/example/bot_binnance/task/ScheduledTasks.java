package com.example.bot_binnance.task;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.example.bot_binnance.common.BinanceFeeCalculator;
import com.example.bot_binnance.common.PrivateKeyBinnance;
import com.example.bot_binnance.common.SimpleSwingTrader;
import com.example.bot_binnance.common.SimpleSwingTrader.TradeSignal;
import com.example.bot_binnance.dto.OrderDto;
import com.example.bot_binnance.dto.PositionDTO;
import com.example.bot_binnance.dto.trade.CandleStick;
import com.example.bot_binnance.model.ActionLog;
import com.example.bot_binnance.service.ApiBinanceService;
import com.example.bot_binnance.service.BlogService;
import com.example.bot_binnance.service.ContentGeneratorService;
import com.example.bot_binnance.service.GridTradingBot;
import com.example.bot_binnance.service.LogService;
import com.example.bot_binnance.service.TickerService;


@Component
public class ScheduledTasks {

	static boolean scheduledTaskEnabled = true;

	@Autowired
	ApiBinanceService binanceService;
	@Autowired
	LogService logService;

	@Autowired
	TickerService tickerService;

	@Autowired
	GridTradingBot gridTradingBot;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	BlogService blogService;
	@Autowired
	ContentGeneratorService contentGeneratorService;
	

	@Scheduled(fixedRate = (60 * 1000 * 1) ) // chạy mỗi 30 giây
	public void fetchData() {
			try {
				SimpleSwingTrader simpleSwingTrader = new SimpleSwingTrader();
				List<CandleStick> candleSticks = this.binanceService.getCandleSticks("1m");
				TradeSignal trade = simpleSwingTrader.analyzeMarket(candleSticks);
				System.out.println(trade.toString());
				String signal = trade.getSignal();
				List<PositionDTO> pDto = this.binanceService.positionInformation(PrivateKeyBinnance.SYMBOL);
				if (pDto.get(0).getPositionAmt() == 0d) {
					this.binanceService.cancelOpenOrder();
				}
				
				if (pDto.get(0).getPositionAmt() == 0d && !trade.getSignal().equals("WAIT")) {
					// **Mở lệnh Market**
					Double profit = BinanceFeeCalculator.calculateFeesAndProfit(trade.getEntryPrice() , trade.getTakeProfit()  ).getProfit();
					Double lost = Math.abs(trade.getStopLoss() - trade.getEntryPrice()) * PrivateKeyBinnance.QUANTITY; 
					
					if(profit > lost*0.8) {
						List<OrderDto> orderDtos = this.binanceService.createLimitOrderWithTPAndSL(trade.getEntryPrice(), trade.getStopLoss(), trade.getTakeProfit(), signal);
						for (OrderDto item : orderDtos) {
							this.logService.createActionLogOrd(item);
						}
					}
				}
			
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		

	}
	
	@Scheduled(fixedRate = 30000) // chạy mỗi 30 giây
	public void fetchData1() {
			String url = "https://spring-mongodb-sample.onrender.com";
			try {
				restTemplate.getForObject(url, String.class);
			} catch (Exception e) {
				// TODO: handle exception
			}
	}

//    @Scheduled(fixedRate = (60 * 1000 * 3) + (45 * 1000)) // 3 phút + 45 giây
//    public   void fetchData1() {
//    	if (lock.tryLock()) {
//    		try {
//            	List<List<Double>> prices = this.binanceService.getCloseHighLowPrices("5m"); // Gọi phương thức để lấy 3 danh sách
//            	List<Double> closePrices = prices.get(0);  // Giá đóng
//            	List<Double> highPrices = prices.get(1);   // Giá cao nhất
//            	List<Double> lowPrices = prices.get(2);    // Giá thấp nhất
//            	Double currentPrice = Double.parseDouble(binanceService.getCurrentPrice().getPrice());
//            	// Lấy tín hiệu giao dịch (BUY, SELL hoặc No Action)
//            	String signal = Wuyx59Strategy.checkTradeSignal(closePrices , highPrices , lowPrices);
//            	// Gọi hàm tính toán Stop Loss và Take Profit
//            	//double[] slTpValues = Wuyx59Strategy.calculateSLTP(closePrices, highPrices , lowPrices ,  signal);
//            	double stopLoss = 0d;
//            	double takeProfit = 0d;
//	        	 if ("BUY".equals(signal)) {
//	                 stopLoss = currentPrice - 315d;
//	                 takeProfit = currentPrice + 350d;
//	             } else if ("SELL".equals(signal)) {
//	            	 stopLoss = currentPrice + 315d;
//	                 takeProfit = currentPrice - 350d;
//	             } 
//        		ActionLog log = new ActionLog();
//        		log.setPrice(currentPrice);
//        		log.setSide(signal);
//        		log.setTakeProfit(takeProfit);
//        		log.setStoplost(stopLoss);
//        		logService.createActionLog(log);
//        		
//        		List<PositionDTO> pDto = this.binanceService.positionInformation(PrivateKeyBinnance.SYMBOL);
//        		if (pDto.get(0).getPositionAmt()  == 0d && !signal.equals("No Action") ) {
//        			 // **Mở lệnh Market**
//                    OrderDto marketOrder = this.binanceService.createOrder(
//                        currentPrice,            // Giá hiện tại
//                        signal,                  // BUY hoặc SELL
//                        BinanceOrderType.MARKET	, // Lệnh Market
//                        0                         // Không có orderId
//                    );
//
//                    // 2**Tạo Stop-Loss Order**
//                    OrderDto stopLossOrder = this.binanceService.createOrder(
//                        stopLoss, 
//                        signal.equals("BUY") ? "SELL" : "BUY", // Ngược chiều lệnh chính
//                        BinanceOrderType.STOP_MARKET, 
//                        0
//                    );
//                    
//              
//                    OrderDto takeProfitOrder = this.binanceService.createOrder(
//                        takeProfit, 
//                        signal.equals("BUY") ? "SELL" : "BUY", // Ngược chiều lệnh chính
//                        BinanceOrderType.TAKE_PROFIT_MARKET, 
//                        0
//                    );
//                    System.out.println("Market Order: " + marketOrder);
//                    System.out.println("Stop-Loss Order: " + stopLossOrder);
//                    System.out.println("Take-Profit Order: " + takeProfitOrder);
//    			}
//            } catch (Exception e) {
//            	System.out.println(e.getMessage());
//            }finally {
//                lock.unlock(); // Giải phóng khóa sau khi hoàn thành
//            }
//    	}  
//    }

	public static void enableScheduledTask() {
		scheduledTaskEnabled = true;
	}

	public static void disableScheduledTask() {
		scheduledTaskEnabled = false;
	}

	public static boolean getScheduledTaskEnabled() {
		return scheduledTaskEnabled;
	}
}
