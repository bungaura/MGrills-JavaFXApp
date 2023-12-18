package controller;

import model.Receipt;

public class ReceiptController {
	
	public static void createReceipt(int receiptOrder, String receiptPaymentType, Double receiptPaymentAmount, String receiptPaymentDate) {
		Receipt.createReceipt(receiptOrder, receiptPaymentType, receiptPaymentAmount, receiptPaymentDate);
	}
	
	public static String validatePaymentInput(String typeInput, Double amountInput, Double orderTotal) {
		if (typeInput.isEmpty()) {
	        return "All fields must be filled out!";
	    }
		if (!typeInput.equals("Cash") && !typeInput.equals("Debit") && !typeInput.equals("Credit")) {
	        return "Payment type must be either Cash, Debit, or Credit!";
	    }
		if (orderTotal > amountInput) {
			return "Payment amount must be greater than order total!";
		}
		return null;
	}

	public void updateReceipt(int orderId, String receiptPaymentType, int receiptPaymentAmount, String receiptPaymentDate) {
		
	}
	
	public void deleteReceipt(int orderId) {
		
	}
	
	public void getReceiptById(int receiptId) {
		
	}
	
	public void getAllReceipts() {
		
	}

}
