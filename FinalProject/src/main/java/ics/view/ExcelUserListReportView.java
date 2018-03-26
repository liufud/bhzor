package ics.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import ics.model.Order;
import ics.model.OrderedProd;
import ics.model.Product;
import ics.model.ReplenishmentOrder;
import ics.model.User;

public class ExcelUserListReportView extends AbstractXlsView{
	
	@SuppressWarnings({ "unchecked" })
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		response.setHeader("Content-Disposition","attachment; filename=\"Full_Database.xls\"");
		
		List<Object> list = (List<Object>) model.get("userList");
		List<User> userList = (List<User>) list.get(0);
		List<Product> productList = (ArrayList<Product>) list.get(1);
		List<Order> orderList = (List<Order>) list.get(2);
		List<ReplenishmentOrder> rpOrderList = (List<ReplenishmentOrder>) list.get(3);
		
		Sheet userSheet = (Sheet) workbook.createSheet("User List");
		Sheet productSheet = (Sheet) workbook.createSheet("Product List");
		Sheet orderSheet = (Sheet) workbook.createSheet("Customer Order List");
		Sheet rpOrderSheet = (Sheet) workbook.createSheet("Replenishment Order List");
		//User
		//header row
		Row userHeader = userSheet.createRow(0);
		userHeader.createCell(0).setCellValue("ID");
		userHeader.createCell(1).setCellValue("USERNAME");
		userHeader.createCell(2).setCellValue("FIRST NAME");
		userHeader.createCell(3).setCellValue("LAST NAME");
		userHeader.createCell(4).setCellValue("EMAIL");
		userHeader.createCell(5).setCellValue("PHONE NUMBER");
		userHeader.createCell(6).setCellValue("ADDRESS");
		userHeader.createCell(7).setCellValue("CITY");
		userHeader.createCell(8).setCellValue("STATE");
		userHeader.createCell(9).setCellValue("ZIP");
		
		int rowNum = 1;
		
		for(User user:userList) {
			Row row = userSheet.createRow(rowNum++);
			row.createCell(0).setCellValue(user.getUserId());
			row.createCell(1).setCellValue(user.getUsername());
			row.createCell(2).setCellValue(user.getFirstName());
			row.createCell(3).setCellValue(user.getLastName());
			row.createCell(4).setCellValue(user.getEmail());
			if(null!=user.getPhoneNumber())	row.createCell(5).setCellValue(user.getPhoneNumber());
			row.createCell(6).setCellValue(user.getAddress());
			row.createCell(7).setCellValue(user.getCity());
			row.createCell(8).setCellValue(user.getState());
			row.createCell(9).setCellValue(user.getZip());
		}
		
		//Product
		Row productHeader = productSheet.createRow(0);
		productHeader.createCell(0).setCellValue("ID");
		productHeader.createCell(1).setCellValue("Product Name");
		productHeader.createCell(2).setCellValue("Cost from Factory");
		productHeader.createCell(3).setCellValue("Price for Sale");
		productHeader.createCell(4).setCellValue("Quantity");
		
		int prodRowNum = 1;
		
		for(Product product:productList) {
			Row row = productSheet.createRow(prodRowNum++);
			row.createCell(0).setCellValue(product.getProductID());
			row.createCell(1).setCellValue(product.getProductName());
			row.createCell(2).setCellValue(product.getCost());
			row.createCell(3).setCellValue(product.getPrice());
			if(null!=product.getQuantity()) row.createCell(4).setCellValue(product.getQuantity());
		}
		
		//Orders
		Row orderHeader = orderSheet.createRow(0);
		orderHeader.createCell(0).setCellValue("ID");
		int prodNum = productList.size();
		for(int i=0; i<prodNum; i++) {
			orderHeader.createCell(i+1).setCellValue(productList.get(i).getProductName());
		}
		orderHeader.createCell(prodNum+1).setCellValue("Order Total");
		orderHeader.createCell(prodNum+2).setCellValue("Created By");
		orderHeader.createCell(prodNum+3).setCellValue("Payment Method");
		orderHeader.createCell(prodNum+4).setCellValue("Order Status");
		orderHeader.createCell(prodNum+5).setCellValue("Payment Status");
		orderHeader.createCell(prodNum+6).setCellValue("Shipment Status");
		orderHeader.createCell(prodNum+7).setCellValue("Created At");
		
		int orderRowNum = 1;
		
		for(Order order:orderList) {
			Row row = orderSheet.createRow(orderRowNum++);
			row.createCell(0).setCellValue(order.getOrderID());
			List<OrderedProd> orderedProd = order.getProducts();
//			for(OrderedProd op:orderedProd) {
//				System.out.println("ordered prod in Order: " + op.getProductName());
//				System.out.println("ordered prod qty: " + op.getOrderedProductQty());
//			}
			
			for(int j=0; j<prodNum; j++) {
				for(int i=0; i<orderedProd.size(); i++) {
					//check if there is any orderd product										
					if(productList.get(j).getProductName().equals(orderedProd.get(i).getProductName())) {
						if(null!=orderedProd.get(i).getOrderedProductQty()) {
							row.createCell(i+1).setCellValue(orderedProd.get(i).getOrderedProductQty());
						}
					}
				}
			}
//			for(int j=0; j<prodNum; j++) {
//				for(int i=0; i<orderedProd.size(); i++) {
//					//check if there is any orderd product
//					if(null!=orderedProd) {
////						OrderedProd prod = orderedProd.get(i);
////						Product prodInList = productList.get(i);										
//						if(!orderedProd.get(i).getProductName().equals(productList.get(j).getProductName())) {
//							if(null==orderedProd.get(i).getOrderedProductQty())row.createCell(i+1).setCellValue(0);
//						}else {
//							row.createCell(i+1).setCellValue(orderedProd.get(i).getOrderedProductQty());
//						}							
//					}				
//				}
//			}
						
			row.createCell(prodNum+1).setCellValue(order.getTotalPrice());
			row.createCell(prodNum+2).setCellValue(order.getCreateByUser().getFirstName()+ " " +order.getCreateByUser().getLastName());
			if(null!=order.getPaymentMethod()) row.createCell(prodNum+3).setCellValue(order.getPaymentMethod());
			row.createCell(prodNum+4).setCellValue(order.getOrderStatus());
			row.createCell(prodNum+5).setCellValue(order.getPaymentStatus());
			row.createCell(prodNum+6).setCellValue(order.getShipmentStatus());
			row.createCell(prodNum+7).setCellValue(order.getCreated_At());	
		}
		
		//Replenishment Order
		Row rporderHeader = rpOrderSheet.createRow(0);
		rporderHeader.createCell(0).setCellValue("ID");
		for(int i=0; i<prodNum; i++) {
			rporderHeader.createCell(i+1).setCellValue(productList.get(i).getProductName());
		}
		rporderHeader.createCell(prodNum+1).setCellValue("Created At");
		rporderHeader.createCell(prodNum+2).setCellValue("Created By");
		rporderHeader.createCell(prodNum+3).setCellValue("Order Staus");
		rporderHeader.createCell(prodNum+4).setCellValue("Order Total");
		
		int rpOrderRowNum = 1;
		
		for(ReplenishmentOrder rpOrder:rpOrderList) {
			Row row = rpOrderSheet.createRow(rpOrderRowNum++);
			row.createCell(0).setCellValue(rpOrder.getRpOrderID());
			List<OrderedProd> orderedProd = rpOrder.getRpProducts();
//			System.out.println(orderedProd.size()+"------------------------------------------------------------");
//			for(OrderedProd op:orderedProd) {
//				System.out.println("ordered prod in RPOder: " + op.getProductName());
//				System.out.println("ordered prod qty: " + op.getOrderedProductQty());
//			}
//			for(int i=0; i<orderedProd.size(); i++) {
//				//check if there is any orderd product
//				if(null!=orderedProd) {
//					OrderedProd prod = orderedProd.get(i);
//					for(int j=0; j<prodNum; j++) {
//						Product prodInList = productList.get(i);
//						//if odered product name = one of the product in the product list
//						// add that the product quantity to that cell
//						if(prod.getProductName().equals(prodInList.getProductName())) {
//							if(null!=prod.getOrderedProductQty()) row.createCell(i+1).setCellValue(prod.getOrderedProductQty());
//						}else {
//							row.createCell(i+1).setCellValue(0);
//						}
//					}
//				}				
//			}
			for(int j=0; j<prodNum; j++) {
				for(int i=0; i<orderedProd.size(); i++) {
					//check if there is any ordered product										
					if(productList.get(j).getProductName().equals(orderedProd.get(i).getProductName())) {
						if(null!=orderedProd.get(i).getOrderedProductQty()) {
							row.createCell(i+1).setCellValue(orderedProd.get(i).getOrderedProductQty());
						}
					}
				}
			}
			row.createCell(prodNum+1).setCellValue(rpOrder.getCreated_At());
			row.createCell(prodNum+2).setCellValue(rpOrder.getCreateByUser().getFirstName()+ " " +rpOrder.getCreateByUser().getLastName());
			row.createCell(prodNum+3).setCellValue(rpOrder.getOrderStatus());
			if(null!=rpOrder.getTotalPrice())row.createCell(prodNum+4).setCellValue(rpOrder.getTotalPrice());
		}
	}

}
