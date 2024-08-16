package com.example.bot_binnance.dto;

public class CrossoverPoint {
	    int index;
	    String type;
	    Double priceMaMin;
	    Double priceMaMax;

	    public CrossoverPoint(int index, String type , Double priceMaMin  , Double priceMaMax) {

	        this.index = index;
	        this.type = type;
	        this.priceMaMin = priceMaMin;
	        this.priceMaMax = priceMaMax;
	        
	    }

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Double getPriceMaMin() {
			return priceMaMin;
		}

		public void setPriceMaMin(Double priceMaMin) {
			this.priceMaMin = priceMaMin;
		}

		public Double getPriceMaMax() {
			return priceMaMax;
		}

		public void setPriceMaMax(Double priceMaMax) {
			this.priceMaMax = priceMaMax;
		}
	    
	    
}
