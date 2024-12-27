
package com.itbulls.learnit.onlinestore.core.facades.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbulls.learnit.onlinestore.core.CoreConfigurations;
import com.itbulls.learnit.onlinestore.core.facades.PurchaseFacade;
import com.itbulls.learnit.onlinestore.core.facades.UserFacade;
import com.itbulls.learnit.onlinestore.persistence.dao.PurchaseDao;
import com.itbulls.learnit.onlinestore.persistence.dao.impl.JpaPurchaseDao;
import com.itbulls.learnit.onlinestore.persistence.dto.ProductDto;
import com.itbulls.learnit.onlinestore.persistence.dto.UserDto;
import com.itbulls.learnit.onlinestore.persistence.dto.converters.ProductDtoToProductConverter;
import com.itbulls.learnit.onlinestore.persistence.dto.converters.PurchaseDtoToPurchaseConverter;
import com.itbulls.learnit.onlinestore.persistence.dto.converters.UserDtoToUserConverter;
import com.itbulls.learnit.onlinestore.persistence.entities.Product;
import com.itbulls.learnit.onlinestore.persistence.entities.Purchase;
import com.itbulls.learnit.onlinestore.persistence.entities.PurchaseStatus;
import com.itbulls.learnit.onlinestore.persistence.entities.User;
import com.itbulls.learnit.onlinestore.persistence.entities.impl.DefaultPurchase;
import com.itbulls.learnit.onlinestore.persistence.entities.impl.DefaultPurchaseStatus;

@Service
public class DefaultPurchaseFacade implements PurchaseFacade {
	
	@Autowired
	private PurchaseDao purchaseDao = new JpaPurchaseDao();
	
	@Autowired
	private PurchaseDtoToPurchaseConverter purchaseConverter;
	
	@Autowired
	private UserFacade userFacade;
	
	
	@Override
	public void createPurchase(User user, Product product) {
		Purchase purchase = new DefaultPurchase();
		purchase.setCustomer(user);
		purchase.setProducts(new ArrayList<>(Arrays.asList(product)));
		
		var purchaseStatus = new DefaultPurchaseStatus();
		purchaseStatus.setId(1); // the initial, the first purchase status
		purchase.setPurchaseStatus(purchaseStatus);
		
		purchaseDao.savePurchase(purchaseConverter.convertPurchaseToPurchaseDto(purchase));
	}

	@Override
	public List<Purchase> getNotCompletedPurchases() {
		return purchaseConverter.convertPurchaseDtosToPurchases(purchaseDao.getNotCompletedPurchases(LAST_STATUS_OF_ORDER_FULFILMENT_ID));
	}

	@Override
	public void markFulfilmentStageForPurchaseIdAsCompleted(Integer purchaseId) {
		Purchase purchase = purchaseConverter.convertPurchaseDtoToPurchase(purchaseDao.getPurchaseById(purchaseId));
		PurchaseStatus purchaseStatus = purchase.getPurchaseStatus();
		int newPurchaseStatusId = purchaseStatus.getId() + 1;
		purchaseStatus.setId(newPurchaseStatusId);
		purchase.setPurchaseStatus(purchaseStatus);
		
		purchaseDao.updatePurchase(purchaseConverter.convertPurchaseToPurchaseDto(purchase));
		
		if (LAST_STATUS_OF_ORDER_FULFILMENT_ID.equals(newPurchaseStatusId) 
				&& purchase.getCustomer().getReferrerUser() != null) {
			User referrerUser = purchase.getCustomer().getReferrerUser();
			double shareFromPurchase = purchase.getTotalPurchaseCost() * CoreConfigurations.REFFERER_REWARD_RATE;
			referrerUser.setMoney(referrerUser.getMoney() + shareFromPurchase);
			userFacade.updateUser(referrerUser);
		}
	}
	
	@Override
	public boolean hasUserPurchasedThisProduct(Product product, User user)
	{
//		//Define converters
//		ProductDtoToProductConverter productCnv = new ProductDtoToProductConverter();
//		UserDtoToUserConverter userCnv = new UserDtoToUserConverter();
//		
//		//Get all productsDto user purchased
//		List<ProductDto> productsDtoUserPurchased =  purchaseDao.productsUserPurchased(userCnv.convertUserToUserDto(user));
//		
//		//Convert productDtos to products
//		List<Product>    productsUserPurchased =  new ArrayList<>();
//		productsUserPurchased = productCnv.convertProductDtosToProducts(productsDtoUserPurchased);
//		
//		for ( Product productInArray : productsUserPurchased)
//		{
//			 if (productInArray.getId() == product.getId())
//			 {
//				 LOGGER.info("hasUserPurchasedThisProduct true");
//				 return true;
//			 }
//		}
//		LOGGER.info("hasUserPurchasedThisProduct false");
		return false;
	}


}
