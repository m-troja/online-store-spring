package com.itbulls.learnit.onlinestore.persistence.dao;

import java.util.List;

import com.itbulls.learnit.onlinestore.persistence.dto.ProductDto;
import com.itbulls.learnit.onlinestore.persistence.dto.PurchaseDto;
import com.itbulls.learnit.onlinestore.persistence.dto.UserDto;


public interface PurchaseDao {
	
	void savePurchase(PurchaseDto order);

	List<PurchaseDto> getPurchasesByUserId(int userId);
	
	List<PurchaseDto> getPurchases();

	List<PurchaseDto> getNotCompletedPurchases(Integer lastFulfilmentStageId);

	PurchaseDto getPurchaseById(Integer purchaseId);

	void updatePurchase(PurchaseDto convertPurchaseToPurchaseDto);
	
//	List<ProductDto> productsUserPurchased(UserDto userDto);
	
}
