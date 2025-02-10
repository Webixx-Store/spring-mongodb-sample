package com.example.bot_binnance.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



import jakarta.validation.constraints.NotBlank;

@Document(collection = "products")
public class Product {

    @Id
    private String id;

    @NotBlank(message = "Product name is mandatory")
    private String name;
    private String description;
    private String priceTxt;
    private double price;
    private double rate;
    private String category;
    private int stock;
    private String img;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updateAt = LocalDateTime.now();
    private boolean isBest = false;
    private boolean isNew = false;
    private boolean isSale  = false;
    private List<String > sliders;
    private String location;
    private String keyword;
    private String timeDate;
    private String services;
    
    private String visaType;
    private String content;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public boolean isBest() {
		return isBest;
	}

	public void setBest(boolean isBest) {
		this.isBest = isBest;
	}

	public boolean isNew() {
		return isNew;
	}	

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public boolean isSale() {
		return isSale;
	}

	public void setSale(boolean isSale) {
		this.isSale = isSale;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public List<String> getSliders() {
		return sliders;
	}

	public void setSliders(List<String> sliders) {
		this.sliders = sliders;
	}

	public String getPriceTxt() {
		return priceTxt;
	}

	public void setPriceTxt(String priceTxt) {
		this.priceTxt = priceTxt;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTimeDate() {
		return timeDate;
	}

	public void setTimeDate(String timeDate) {
		this.timeDate = timeDate;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}
	
    
}
