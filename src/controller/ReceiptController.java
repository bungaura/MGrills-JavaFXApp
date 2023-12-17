package controller;

import model.Receipt;

public class ReceiptController {
	
    // Creates a new receipt with the provided order ID, payment type, payment amount, and payment date
	public static void createReceipt(int receiptOrder, String receiptPaymentType, Double receiptPaymentAmount, String receiptPaymentDate) {
		Receipt.createReceipt(receiptOrder, receiptPaymentType, receiptPaymentAmount, receiptPaymentDate);
	}
	
    // Validates the payment input, checking if the payment type is filled, valid, and if the amount is sufficient
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

}
