package com.example.bot_binnance.model;

import java.time.LocalDateTime;
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
    private double price;
    private double rate;
    private String category;
    private int stock;
    private String img;
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private boolean isBest = false;
    private boolean isNew = false;
    private boolean isSale  = false;
    

    // Getters và Setters

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
    
    
}
