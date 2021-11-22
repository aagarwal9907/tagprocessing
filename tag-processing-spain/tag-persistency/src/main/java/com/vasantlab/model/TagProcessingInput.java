package com.vasantlab.model;

/**
 * This Class will be used for processing the inputs from the Gui for starting
 * the job of tagging the orders
 * 
 * @author Shubha A
 *
 */
public class TagProcessingInput {


	private String orderId;
	private String barcode;
	

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}


	@Override
	public String toString() {
		return "TagProcessingInput [orderId=" + orderId + ", barcode=" + barcode + "]";
	}
}
