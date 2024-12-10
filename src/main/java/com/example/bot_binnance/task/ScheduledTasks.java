package com.example.bot_binnance.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.example.bot_binnance.common.PrivateKeyBinnance;
import com.example.bot_binnance.dto.PositionDTO;
import com.example.bot_binnance.dto.TimeFrame;
import com.example.bot_binnance.model.ActionLog;
import com.example.bot_binnance.model.Blog;
import com.example.bot_binnance.model.PriceLogDto;
import com.example.bot_binnance.service.ApiBinanceService;
import com.example.bot_binnance.service.BlogService;
import com.example.bot_binnance.service.ContentGeneratorService;
import com.example.bot_binnance.service.GridTradingBot;
import com.example.bot_binnance.service.LogService;
import com.example.bot_binnance.service.TelegramBot;
import com.example.bot_binnance.service.TickerService;

import jakarta.annotation.PostConstruct;
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
	
	@Autowired BlogService blogService;
	@Autowired ContentGeneratorService contentGeneratorService;

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
            restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
        }
    }
    
    public void generateDailyContent() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        try {
            // Giao tiếp với Binance để lấy thông tin giá
            UMFuturesClientImpl client = new UMFuturesClientImpl(
                PrivateKeyBinnance.API_KEY,
                PrivateKeyBinnance.SECRET_KEY,
                PrivateKeyBinnance.UM_BASE_URL
            );

            LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("symbol", PrivateKeyBinnance.SYMBOL);
            parameters.put("interval", "1h"); // Khung thời gian là mỗi 1 giờ
            parameters.put("limit", 200); // Lấy 200 mẫu dữ liệu gần nhất

            // Lấy thông tin giá từ Binance
            String result = client.market().klines(parameters);

            // Dữ liệu các mức giá được parse từ Binance Klines response
            List<Double> closePrices = parseClosePrices(result);
            
            if (closePrices.isEmpty()) {
                System.err.println("Không tìm thấy thông tin giá từ Binance.");
                return;
            }

            // Tạo thông tin cho nội dung bài viết dựa trên thông tin giá
            Double averagePrice = calculateAverage(closePrices);
            Double latestPrice = closePrices.get(closePrices.size() - 1);

            String prompt = createPrompt(averagePrice, latestPrice, closePrices);

            // Bot AI service tạo nội dung
            String content = contentGeneratorService.generateContent(
                "AIzaSyCNHcjHExhYkmoIekWcwCKveNqd5i60yXs", 
                prompt
            );
            
            String content2 = contentGeneratorService.generateContent(
                    "AIzaSyCNHcjHExhYkmoIekWcwCKveNqd5i60yXs", 
                    "làm ơn hãy viết nội dung này "   + prompt  + " thành 1 bài báo html chỉ chứa các thẻ text để tôi inner nó trong thẻ div sử dụng trong angular" 
                );

            if (content != null && !content.isEmpty()) {
                Blog blog = new Blog();
                blog.setTitle("Cập nhật thông tin về Bitcoin hôm nay + " + today);
                blog.setContent(content2);
                blog.setAuthor("bot_google");

                // Lưu thông tin bài viết
                blogService.saveBlog(blog);
                System.out.println("Đã tạo bài viết mới: Phân tích Bitcoin giá hôm nay.");
            } else {
                System.err.println("Không có nội dung được tạo từ bot AI.");
            }
        } catch (Exception e) {
            System.err.println("Không thể tạo bài viết: " + e.getMessage());
        }
    }

    /**
     * Phân tích và trích xuất thông tin giá đóng cửa từ kết quả API Binance.
     */
    private List<Double> parseClosePrices(String result) {
        List<Double> closePrices = new ArrayList<>();
        try {
            // Giả định parse kết quả Klines JSON và lấy thông tin đóng cửa
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray kline = jsonArray.getJSONArray(i);
                double closePrice = kline.getDouble(4); // Thông thường, chỉ số 4 là giá đóng cửa trong dữ liệu Klines
                closePrices.add(closePrice);
            }
        } catch (Exception e) {
            System.err.println("Không thể parse thông tin giá từ Binance: " + e.getMessage());
        }
        return closePrices;
    }

    /**
     * Tính trung bình của danh sách giá đóng cửa
     */
    private Double calculateAverage(List<Double> prices) {
        if (prices.isEmpty()) return 0.0;
        double sum = 0;
        for (Double price : prices) {
            sum += price;
        }
        return sum / prices.size();
    }

    /**
     * Tạo prompt gửi đến GPT hoặc AI Generator để viết nội dung HTML phân tích Bitcoin.
     */
    private String createPrompt(Double averagePrice, Double latestPrice, List<Double> prices) {
        return "Làm ơn phân tích thông tin thị trường Bitcoin hôm nay. " +
            "Dựa trên thông tin giá Bitcoin trong khung thời gian 1 giờ, tính từ dữ liệu đóng cửa gần nhất. " +
            "Thông tin cần đề cập như sau: " +
            "- Giá đóng cửa trung bình trong 200 mẫu gần đây là: " + String.format("%.2f", averagePrice) + " USD. " +
            "- Giá đóng cửa mới nhất là: " + String.format("%.2f", latestPrice) + " USD. " +
            "- Phân tích cụ thể chỉ số mua bán rsi và các chỉ số quan trọng khác trong 200 mẫu thử này";
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

	@PostConstruct
    public void initialLoad() {
        // Logic được gọi ngay sau khi Spring Boot khởi động
        System.out.println("Gọi ngay logic ban đầu sau khi Spring Boot khởi động...");
        generateDailyContent();
    }
}
