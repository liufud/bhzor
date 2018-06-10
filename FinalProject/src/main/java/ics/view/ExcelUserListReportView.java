package ics.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import ics.model.BillingInfo;
import ics.model.Order;
import ics.model.OrderedProd;
import ics.model.Product;
import ics.model.ReceivedRpOrder;
import ics.model.ReplenishmentOrder;
import ics.model.ShippedOrder;
import ics.model.User;
import ics.services.OrderService;
import ics.services.UserService;

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
		List<ShippedOrder> shippedOrderList = (List<ShippedOrder>) list.get(4);
		List<ReceivedRpOrder> receivedRpOrderList = (List<ReceivedRpOrder>) list.get(5);
		List<BillingInfo> billingInfoList = (List<BillingInfo>) list.get(6);
		
		Sheet userSheet = (Sheet) workbook.createSheet("Usuarios");
		Sheet productSheet = (Sheet) workbook.createSheet("Productos");
		Sheet orderSheet = (Sheet) workbook.createSheet("Pedidos de Clientes");
		Sheet shippedOrderSheet = (Sheet) workbook.createSheet("Pedidos Enviadas");
		Sheet rpOrderSheet = (Sheet) workbook.createSheet("Reposicion de Producto");		
		Sheet receivedRpOrderSheet = (Sheet) workbook.createSheet("Reposicion de Producto Recibida");
		Sheet billingInfoSheet = (Sheet) workbook.createSheet("Dirección de Envío");
		
		//User
		//header row
		Row userHeader = userSheet.createRow(0);
		userHeader.createCell(0).setCellValue("ID");
		userHeader.createCell(1).setCellValue("Nombre de Usuario"); //username
		userHeader.createCell(2).setCellValue("Nombre"); //first name
		userHeader.createCell(3).setCellValue("Apellido"); //last name
		userHeader.createCell(4).setCellValue("EMAIL");
		userHeader.createCell(5).setCellValue("Telefono"); //phone numnber
		userHeader.createCell(6).setCellValue("Direccion"); //address
		userHeader.createCell(7).setCellValue("Ciudad"); //city
		userHeader.createCell(8).setCellValue("Estado"); //state
		userHeader.createCell(9).setCellValue("Codigo Postal"); //zip code
		userHeader.createCell(10).setCellValue("Tipo"); //
		userHeader.createCell(11).setCellValue("Vendedor");
		
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
			row.createCell(10).setCellValue(user.getRoleName());
			if(null!=user.getVendor()) row.createCell(11).setCellValue(user.getVendor().getFirstName() + " " + user.getVendor().getLastName());
		}
		
		//Product
		Row productHeader = productSheet.createRow(0);
		productHeader.createCell(0).setCellValue("ID");
		productHeader.createCell(1).setCellValue("Nombre de Producto");
		productHeader.createCell(2).setCellValue("Costo de Fabrica");
		productHeader.createCell(3).setCellValue("Precio de venta");
		productHeader.createCell(4).setCellValue("Cantidad");
		
		int prodRowNum = 1;
		
		for(Product product:productList) {
			Row row = productSheet.createRow(prodRowNum++);
			row.createCell(0).setCellValue(product.getProductID());
			row.createCell(1).setCellValue(product.getProductName());
			row.createCell(2).setCellValue(product.getCost());
//			row.createCell(3).setCellValue(product.getPrice());
			if(null!=product.getQuantity()) row.createCell(4).setCellValue(product.getQuantity());
		}
		
		int prodNum = productList.size();
		
		//Orders
		Row orderHeader = orderSheet.createRow(0);
		orderHeader.createCell(0).setCellValue("ID");
		orderHeader.createCell(1).setCellValue("Dia creado");
		orderHeader.createCell(2).setCellValue("Tipo de Pedido");
		for(int i=0; i<prodNum; i++) {
			orderHeader.createCell(i+3).setCellValue(productList.get(i).getProductName());
		}
//		for(int i=0; i<prodNum; i++) {
//			orderHeader.createCell(i+1).setCellValue(productList.get(i).getProductName());
//		}
		orderHeader.createCell(prodNum+3).setCellValue("Total de Pedido");
		orderHeader.createCell(prodNum+4).setCellValue("Creado Por");
		orderHeader.createCell(prodNum+5).setCellValue("Creado Para");
		orderHeader.createCell(prodNum+6).setCellValue("Metodo de Paga");
		orderHeader.createCell(prodNum+7).setCellValue("Estado de la Pedido");
		orderHeader.createCell(prodNum+8).setCellValue("Estado de la Paga");
		orderHeader.createCell(prodNum+9).setCellValue("Estado del Envio");
//		orderHeader.createCell(prodNum+8).setCellValue("Dia creado");
		orderHeader.createCell(prodNum+10).setCellValue("Tiempo pagado");
		orderHeader.createCell(prodNum+11).setCellValue("Tiempo enviado");
//		orderHeader.createCell(prodNum+10).setCellValue("Tipo de Pedido");
		
		int orderRowNum = 1;
		
		for(Order order:orderList) {
			Row row = orderSheet.createRow(orderRowNum++);
			row.createCell(0).setCellValue(order.getOrderID());
			row.createCell(1).setCellValue(order.getCreated_At());
			if(null!=order.getOrderType()) row.createCell(2).setCellValue(order.getOrderType());
			List<OrderedProd> orderedProd = order.getProducts();	
			for(int j=0; j<prodNum; j++) {
				for(int i=0; i<orderedProd.size(); i++) {
					//check if there is any orderd product										
					if(productList.get(j).getProductName().equals(orderedProd.get(i).getProductName())) {
						if(null!=orderedProd.get(i).getOrderedProductQty()) {
							row.createCell(i+3).setCellValue(orderedProd.get(i).getOrderedProductQty());
						}
					}
				}
			}
//			for(int j=0; j<prodNum; j++) {
//				for(int i=0; i<orderedProd.size(); i++) {
//					//check if there is any orderd product										
//					if(productList.get(j).getProductName().equals(orderedProd.get(i).getProductName())) {
//						if(null!=orderedProd.get(i).getOrderedProductQty()) {
//							row.createCell(i+1).setCellValue(orderedProd.get(i).getOrderedProductQty());
//						}
//					}
//				}
//			}						
//			row.createCell(prodNum+3).setCellValue(order.getTotalPrice());
			if(null!=order.getCreateByUser()) row.createCell(prodNum+4).setCellValue(order.getCreateByUser().getFirstName()+ " " +order.getCreateByUser().getLastName());
			if(null!=order.getCreateForUser()) row.createCell(prodNum+5).setCellValue(order.getCreateForUser().getFirstName()+ " " +order.getCreateForUser().getLastName());
			if(null!=order.getPaymentMethod()) row.createCell(prodNum+6).setCellValue(order.getPaymentMethod());
			row.createCell(prodNum+7).setCellValue(order.getOrderStatus());
			row.createCell(prodNum+8).setCellValue(order.getPaymentStatus());
			row.createCell(prodNum+9).setCellValue(order.getShipmentStatus());
//			row.createCell(prodNum+8).setCellValue(order.getCreated_At());
			row.createCell(prodNum+10).setCellValue(order.getPaid_At());
			row.createCell(prodNum+11).setCellValue(order.getShipped_At());			
		}
		
		//Shipped Order
		Row shippedOrderHeader = shippedOrderSheet.createRow(0);
		shippedOrderHeader.createCell(0).setCellValue("ID");
		shippedOrderHeader.createCell(1).setCellValue("Fecha Enviada");
		shippedOrderHeader.createCell(2).setCellValue("# de Lote");
		shippedOrderHeader.createCell(3).setCellValue("# de Estante");
		shippedOrderHeader.createCell(4).setCellValue("ID de Pedido");
		shippedOrderHeader.createCell(5).setCellValue("Cantidad Enviada");
		shippedOrderHeader.createCell(6).setCellValue("Nombre de Producto");
		shippedOrderHeader.createCell(7).setCellValue("# de Rastreo");
		shippedOrderHeader.createCell(8).setCellValue("Enviado por usuario");
		
		int rowNumber = 1;
		
		for(ShippedOrder so:shippedOrderList) {
			Row row = shippedOrderSheet.createRow(rowNumber++);
			row.createCell(0).setCellValue(so.getShipmentID());
			row.createCell(1).setCellValue(so.getDateShipped());
			row.createCell(2).setCellValue(so.getLotID());
			row.createCell(3).setCellValue(so.getShelfID());
			row.createCell(4).setCellValue(so.getOrderID());
			row.createCell(5).setCellValue(so.getQtyShipped());
			row.createCell(6).setCellValue(so.getShippedProductName());
			row.createCell(7).setCellValue(so.getTrackingNumber());
			row.createCell(8).setCellValue(so.getShippedByUser().getFirstName() + " " + so.getShippedByUser().getLastName());
		}
		
		//Replenishment Order
		Row rporderHeader = rpOrderSheet.createRow(0);
		rporderHeader.createCell(0).setCellValue("ID");
		rporderHeader.createCell(1).setCellValue("Fecha Creada");
		for(int i=0; i<prodNum; i++) {
			rporderHeader.createCell(i+2).setCellValue(productList.get(i).getProductName());
		}
//		for(int i=0; i<prodNum; i++) {
//			rporderHeader.createCell(i+1).setCellValue(productList.get(i).getProductName());
//		}
//		rporderHeader.createCell(prodNum+1).setCellValue("Fecha Creada");
		rporderHeader.createCell(prodNum+2).setCellValue("Creado Por");
		rporderHeader.createCell(prodNum+3).setCellValue("Estado de Pedido");
		rporderHeader.createCell(prodNum+4).setCellValue("Total de Pedido");
		
		int rpOrderRowNum = 1;
		
		for(ReplenishmentOrder rpOrder:rpOrderList) {
			Row row = rpOrderSheet.createRow(rpOrderRowNum++);
			row.createCell(0).setCellValue(rpOrder.getRpOrderID());
			row.createCell(1).setCellValue(rpOrder.getCreated_At());
			List<OrderedProd> orderedProd = rpOrder.getRpProducts();
			for(int j=0; j<prodNum; j++) {
				for(int i=0; i<orderedProd.size(); i++) {
					//check if there is any ordered product										
					if(productList.get(j).getProductName().equals(orderedProd.get(i).getProductName())) {
						if(null!=orderedProd.get(i).getOrderedProductQty()) {
							row.createCell(i+2).setCellValue(orderedProd.get(i).getOrderedProductQty());
						}
					}
				}
			}
//			for(int j=0; j<prodNum; j++) {
//				for(int i=0; i<orderedProd.size(); i++) {
//					//check if there is any ordered product										
//					if(productList.get(j).getProductName().equals(orderedProd.get(i).getProductName())) {
//						if(null!=orderedProd.get(i).getOrderedProductQty()) {
//							row.createCell(i+1).setCellValue(orderedProd.get(i).getOrderedProductQty());
//						}
//					}
//				}
//			}
//			row.createCell(prodNum+1).setCellValue(rpOrder.getCreated_At());
			if(null != rpOrder.getCreateByUser())row.createCell(prodNum+2).setCellValue(rpOrder.getCreateByUser().getFirstName()+ " " +rpOrder.getCreateByUser().getLastName());
			row.createCell(prodNum+3).setCellValue(rpOrder.getOrderStatus());
			if(null!=rpOrder.getTotalPrice())row.createCell(prodNum+4).setCellValue(rpOrder.getTotalPrice());
		}
		
		//Received Replenishment Order List
		Row receivedRpOrderHeader = receivedRpOrderSheet.createRow(0);
		receivedRpOrderHeader.createCell(0).setCellValue("ID");
		receivedRpOrderHeader.createCell(1).setCellValue("Fecha Recibida");
		receivedRpOrderHeader.createCell(2).setCellValue("Fecha de Expiracion");
		receivedRpOrderHeader.createCell(3).setCellValue("# de Lote");
		receivedRpOrderHeader.createCell(4).setCellValue("# de Estante");
		receivedRpOrderHeader.createCell(5).setCellValue("Cantidad Recibida");
		receivedRpOrderHeader.createCell(6).setCellValue("Cantidad Rechazada");
		receivedRpOrderHeader.createCell(7).setCellValue("Nombre de Producto");
		receivedRpOrderHeader.createCell(8).setCellValue("ID de Pedido de Reposicion");
		receivedRpOrderHeader.createCell(9).setCellValue("Recibido  Por");
//		receivedRpOrderHeader.createCell(9).setCellValue("Costo Total");
		
		int receivedRowNumber = 1;
		
		for(ReceivedRpOrder rRpO:receivedRpOrderList) {
			Row row = receivedRpOrderSheet.createRow(receivedRowNumber++);
			row.createCell(0).setCellValue(rRpO.getReceivedRpOrderID());
			row.createCell(1).setCellValue(rRpO.getDateReceived());
			row.createCell(2).setCellValue(rRpO.getExpDate());
			row.createCell(3).setCellValue(rRpO.getLotID());
			row.createCell(4).setCellValue(rRpO.getShelfID());
			row.createCell(5).setCellValue(rRpO.getQuantityReceived());
			if(null != rRpO.getQuantityRejected()) row.createCell(6).setCellValue(rRpO.getQuantityRejected());
			row.createCell(7).setCellValue(rRpO.getReceivedRpProductName());
			row.createCell(8).setCellValue(rRpO.getRpOrderID());
			if(null != rRpO.getCreateByUser()) row.createCell(9).setCellValue(rRpO.getCreateByUser().getFirstName() + " " + rRpO.getCreateByUser().getLastName());
//			row.createCell(9).setCellValue(rRpO.getTotalCost());
			
		}
		
		//BillingInfo
		Row billingInfoHeader = billingInfoSheet.createRow(0);
		billingInfoHeader.createCell(0).setCellValue("ID");
		billingInfoHeader.createCell(1).setCellValue("Direccion");
		billingInfoHeader.createCell(2).setCellValue("Ciudad");
		billingInfoHeader.createCell(3).setCellValue("Estado");
		billingInfoHeader.createCell(4).setCellValue("Codigo Postal");
		billingInfoHeader.createCell(5).setCellValue("email");
		billingInfoHeader.createCell(6).setCellValue("Creado Para");
		billingInfoHeader.createCell(7).setCellValue("Número de Teléfono");
		billingInfoHeader.createCell(8).setCellValue("ID de Pedido");
		
		int billingInfoRowNum = 1;
		
		for(BillingInfo bi:billingInfoList) {
			Row row = billingInfoSheet.createRow(billingInfoRowNum++);
			row.createCell(0).setCellValue(bi.getBillID());
			row.createCell(1).setCellValue(bi.getAddress());
			row.createCell(2).setCellValue(bi.getCity());
			row.createCell(3).setCellValue(bi.getState());
			row.createCell(4).setCellValue(bi.getPostalCode());
			row.createCell(5).setCellValue(bi.getEmail());
			row.createCell(6).setCellValue(bi.getFirstName() + " "+ bi.getLastName());
			row.createCell(7).setCellValue(bi.getPhone());
			row.createCell(8).setCellValue(bi.getOrder().getOrderID());
		}
		
	}

}
