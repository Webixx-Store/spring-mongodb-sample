package com.example.bot_binnance.service;

import java.util.List;
import java.util.Optional;

import com.example.bot_binnance.model.ActionLog;
import com.example.bot_binnance.model.PriceLogDto;
import com.example.bot_binnance.model.Telegram;

public interface LogService {
	 public ActionLog createActionLog(ActionLog actionLog);
	 public void deleteActionLog(String id);
	 public List<ActionLog> getAllActionLogs();
	 public Optional<ActionLog> getActionLogById(String id);
	 public PriceLogDto createPriceLog(PriceLogDto priceLogDto);
	 List<PriceLogDto> getPriceLogsFromLastTwoHours();
	 List<Double> getPriceLogsDoubleFromLastTwoHours();
	List<Double> getPriceLogsFromLastMinutes(int minutes);
	Telegram insertTelegram(Telegram telegram);
	List<ActionLog> findRecentActionLogs();
	void updateStatusByOrderId(String orderId, String status);
	 
	 
}
