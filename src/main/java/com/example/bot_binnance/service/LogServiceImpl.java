package com.example.bot_binnance.service;
import org.springframework.data.domain.Sort;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.bot_binnance.model.ActionLog;
import com.example.bot_binnance.model.PriceLogDto;
import com.example.bot_binnance.model.Telegram;
import com.example.bot_binnance.repository.ActionLogRepository;
import com.example.bot_binnance.repository.PriceLogRepository;
import com.example.bot_binnance.repository.TelegramRepository;
@Service
public class LogServiceImpl implements LogService {
	

	  @Autowired MongoTemplate mongoTemplate;
	 
	  @Autowired
	  private ActionLogRepository repository;
	  
	  @Autowired
	  private PriceLogRepository priceLogRepository;
	  
	  @Autowired TelegramRepository telegramRepository;

	    // Thêm mới ActionLog
	   @Override
	    public ActionLog createActionLog(ActionLog actionLog) {
	        actionLog.setTimeCreate(LocalDateTime.now());
	        actionLog.setTimeUpdate(LocalDateTime.now());
	        return repository.save(actionLog);
	    }

	  

	    // Xóa ActionLog
	   @Override
	    public void deleteActionLog(String id) {
	        repository.deleteById(id);
	    }

	    // Lấy tất cả ActionLog
	   @Override
	    public List<ActionLog> getAllActionLogs() {
	        return repository.findAll();
	    }

	    // Lấy ActionLog theo ID
	   @Override
	    public Optional<ActionLog> getActionLogById(String id) {
	        return repository.findById(id);
	    }

	@Override
	public PriceLogDto createPriceLog(PriceLogDto priceLogDto) {
		// TODO Auto-generated method stub
		return priceLogRepository.save(priceLogDto);
	}
	
	@Override
	 public List<PriceLogDto> getPriceLogsFromLastTwoHours() {
	        LocalDateTime twoHoursAgo = LocalDateTime.now().minusHours(2);
	        Query query = new Query();
	        query.addCriteria(Criteria.where("timeCreate").gte(twoHoursAgo));
	        return mongoTemplate.find(query, PriceLogDto.class);
	    }
	
	    @Override
	    public List<Double>  getPriceLogsFromLastMinutes(int minutes) {
	        LocalDateTime thirtyMinutesAgo = LocalDateTime.now().minusMinutes(minutes);
	        Query query = new Query();
	        query.addCriteria(Criteria.where("timeCreate").gte(thirtyMinutesAgo));
	        List<PriceLogDto> priceLogs = mongoTemplate.find(query, PriceLogDto.class);
	        List<Double> prices = new ArrayList<>();
	        for (PriceLogDto log : priceLogs) {
	            prices.add(log.getPrice());
	        }
	        return prices;
	    }
	@Override
	 public List<Double> getPriceLogsDoubleFromLastTwoHours() {
	        LocalDateTime twoHoursAgo = LocalDateTime.now().minusHours(2);
	        Query query = new Query();
	        query.addCriteria(Criteria.where("timeCreate").gte(twoHoursAgo));
	        query.with(Sort.by(Sort.Direction.DESC, "timeCreate"));

	        List<PriceLogDto> priceLogs = mongoTemplate.find(query, PriceLogDto.class);

	        List<Double> prices = new ArrayList<>();
	        for (PriceLogDto log : priceLogs) {
	            prices.add(log.getPrice());
	        }
	        return prices;
	    }

	
	@Override
	public Telegram insertTelegram(Telegram telegram) {
		// TODO Auto-generated method stub
		  Optional<Telegram> existingTelegram = telegramRepository.findByTelegramId(telegram.getTelegramId());
		 if (existingTelegram.isPresent()) {
	            // Nếu tồn tại, cập nhật thông tin
	            Telegram existing = existingTelegram.get();
	            if(telegram.getPrivateKey()!= null && telegram.getPrivateKey() != "") {
	            	existing.setPrivateKey(telegram.getPrivateKey());
	            }
	            if(telegram.getPublicKey()!=null && telegram.getPublicKey() != "") {
	            	existing.setPublicKey(telegram.getPublicKey());
	            }
	            
	            existing.setUserName(telegram.getUserName());
	            existing.setTimeUpdate(LocalDateTime.now());
	            telegramRepository.save(existing);
	            return existingTelegram.get();
	        } else {
	            // Nếu không tồn tại, chèn tài liệu mới
	            return telegramRepository.save(telegram);
	            
	        }
	}
	
	@Override
	public List<ActionLog> findRecentActionLogs() {
        List<String> typeOrders = Arrays.asList("STOP_MARKET", "TAKE_PROFIT_MARKET");
        String status = "NEW";
        return repository.findByTypeOrderInAndStatusOrderByTimeCreateDesc(typeOrders, status);
    }
	
	@Override
	 public void updateStatusByOrderId(String orderId, String status) {
		 Query query = new Query(Criteria.where("orderId").is(orderId));
	     Update update = new Update().set("status", status)
	    		                     .set("timeUpdate", LocalDateTime.now());;
	     mongoTemplate.updateFirst(query, update, ActionLog.class);
	 }
	
	

	
	
	
	

}
