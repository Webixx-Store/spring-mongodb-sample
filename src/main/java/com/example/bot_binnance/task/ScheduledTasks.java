package com.example.bot_binnance.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.bot_binnance.common.PrivateKeyBinnance;
import com.example.bot_binnance.dto.PositionDTO;
import com.example.bot_binnance.dto.TimeFrame;
import com.example.bot_binnance.model.ActionLog;
import com.example.bot_binnance.model.PriceLogDto;
import com.example.bot_binnance.service.ApiBinanceService;
import com.example.bot_binnance.service.GridTradingBot;
import com.example.bot_binnance.service.LogService;
import com.example.bot_binnance.service.TelegramBot;
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

//	@Scheduled(fixedRate = 10000) 
//	public void delesion() throws NumberFormatException, Exception {
//		if (scheduledTaskEnabled) {
//			List<ActionLog>  logs = this.logService.findRecentActionLogs();
//			List<PositionDTO> pDto = this.binanceService.positionInformation(PrivateKeyBinnance.SYMBOL);
//			if (pDto.get(0).getPositionAmt()  == 0d && logs.size() == 0) {
//				Double currentPrice = Double.parseDouble(binanceService.getCurrentPrice().getPrice());
//				List<Double> prices = this.binanceService.getClosePrices(PrivateKeyBinnance.timeFrame);
//				tickerService.processTickerEvent(currentPrice, prices);
//			}
//		}
//	}
//
//	@Scheduled(fixedRate = 60000 * 1)
//	public void jobUpdateStatus() throws NumberFormatException, Exception {
//		
//		List<PositionDTO> pDto = this.binanceService.positionInformation(PrivateKeyBinnance.SYMBOL);
//		if (pDto.get(0).getPositionAmt() == 0d) {
//			tickerService.updateStatus();
//		}
//		
//	}
	
	
    @Scheduled(fixedRate = 30000) // chạy mỗi 30 giây
    public void fetchData() {
        String url = "https://spring-mongodb-sample.onrender.com/";
        try {
            String response = restTemplate.getForObject(url, String.class);
            System.out.println("Response: " + response);
        } catch (Exception e) {
            System.err.println("Error fetching data: " + e.getMessage());
        }
    }

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
