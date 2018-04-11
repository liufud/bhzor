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
		
		Sheet userSheet = (Sheet) workbook.createSheet("Usuarios");
		Sheet productSheet = (Sheet) workbook.createSheet("Productos");
		Sheet orderSheet = (Sheet) workbook.createSheet("Ordenes de Clientes");
		Sheet shippedOrderSheet = (Sheet) workbook.createSheet("Ordenes Enviadas");
		Sheet rpOrderSheet = (Sheet) workbook.createSheet("Reposicion de Producto");		
		Sheet receivedRpOrderSheet = (Sheet) workbook.createSheet("Reposicion de Producto Recibida");
		
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
		
		//Orders
		Row orderHeader = orderSheet.createRow(0);
		orderHeader.createCell(0).setCellValue("ID");
		int prodNum = productList.size();
		for(int i=0; i<prodNum; i++) {
			orderHeader.createCell(i+1).setCellValue(productList.get(i).getProductName());
		}
		orderHeader.createCell(prodNum+1).setCellValue("Total de Pedido");
		orderHeader.createCell(prodNum+2).setCellValue("Creado Por");
		orderHeader.createCell(prodNum+3).setCellValue("Creado Para");
		orderHeader.createCell(prodNum+4).setCellValue("Metodo de Paga");
		orderHeader.createCell(prodNum+5).setCellValue("Estado de la Pedido");
		orderHeader.createCell(prodNum+6).setCellValue("Estado de la Paga");
		orderHeader.createCell(prodNum+7).setCellValue("Estado del Envio");
		orderHeader.createCell(prodNum+8).setCellValue("Dia creado");
		orderHeader.createCell(prodNum+9).setCellValue("Tiempo pagado");
		orderHeader.createCell(prodNum+10).setCellValue("Tiempo enviado");
		orderHeader.createCell(prodNum+11).setCellValue("Tipo de Pedido");
		
		int orderRowNum = 1;
		
		for(Order order:orderList) {
			Row row = orderSheet.createRow(orderRowNum++);
			row.createCell(0).setCellValue(order.getOrderID());
			List<OrderedProd> orderedProd = order.getProducts();			
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
			row.createCell(prodNum+1).setCellValue(order.getTotalPrice());
			if(null!=order.getCreateByUser()) row.createCell(prodNum+2).setCellValue(order.getCreateByUser().getFirstName()+ " " +order.getCreateByUser().getLastName());
			if(null!=order.getCreateByUser()) row.createCell(prodNum+3).setCellValue(order.getCreateForUser().getFirstName()+ " " +order.getCreateForUser().getLastName());
			if(null!=order.getPaymentMethod()) row.createCell(prodNum+4).setCellValue(order.getPaymentMethod());
			row.createCell(prodNum+5).setCellValue(order.getOrderStatus());
			row.createCell(prodNum+6).setCellValue(order.getPaymentStatus());
			row.createCell(prodNum+7).setCellValue(order.getShipmentStatus());
			row.createCell(prodNum+8).setCellValue(order.getCreated_At());
			row.createCell(prodNum+9).setCellValue(order.getPaid_At());
			row.createCell(prodNum+10).setCellValue(order.getShipped_At());
			if(null!=order.getOrderType()) row.createCell(prodNum+11).setCellValue(order.getOrderType());
		}
		
		//Shipped Order
		Row shippedOrderHeader = shippedOrderSheet.createRow(0);
		shippedOrderHeader.createCell(0).setCellValue("ID");
		shippedOrderHeader.createCell(1).setCellValue("Fecha Enviada");
		shippedOrderHeader.createCell(2).setCellValue("# de Lote");
		shippedOrderHeader.createCell(3).setCellValue("ID de Orden");
		shippedOrderHeader.createCell(4).setCellValue("Cantidad Enviada");
		shippedOrderHeader.createCell(5).setCellValue("Nombre de Producto");
		shippedOrderHeader.createCell(6).setCellValue("# de Rastreo");
		shippedOrderHeader.createCell(7).setCellValue("Enviado por usuario");
		
		int rowNumber = 1;
		
		for(ShippedOrder so:shippedOrderList) {
			Row row = shippedOrderSheet.createRow(rowNumber++);
			row.createCell(0).setCellValue(so.getShipmentID());
			row.createCell(1).setCellValue(so.getDateShipped());
			row.createCell(2).setCellValue(so.getLotID());
			row.createCell(3).setCellValue(so.getOrderID());
			row.createCell(4).setCellValue(so.getQtyShipped());
			row.createCell(5).setCellValue(so.getShippedProductName());
			row.createCell(6).setCellValue(so.getTrackingNumber());
			row.createCell(7).setCellValue(so.getShippedByUser().getFirstName() + " " + so.getShippedByUser().getLastName());
		}
		
		//Replenishment Order
		Row rporderHeader = rpOrderSheet.createRow(0);
		rporderHeader.createCell(0).setCellValue("ID");
		for(int i=0; i<prodNum; i++) {
			rporderHeader.createCell(i+1).setCellValue(productList.get(i).getProductName());
		}
		rporderHeader.createCell(prodNum+1).setCellValue("Fecha Creada");
		rporderHeader.createCell(prodNum+2).setCellValue("Creado Por");
		rporderHeader.createCell(prodNum+3).setCellValue("Estado de Orden");
		rporderHeader.createCell(prodNum+4).setCellValue("Total de Orden");
		
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
		receivedRpOrderHeader.createCell(4).setCellValue("Cantidad Recibida");
		receivedRpOrderHeader.createCell(5).setCellValue("Cantidad Rechazada");
		receivedRpOrderHeader.createCell(6).setCellValue("Nombre de Producto");
		receivedRpOrderHeader.createCell(7).setCellValue("ID de Pedido de Reposicion");
		receivedRpOrderHeader.createCell(8).setCellValue("Costo Total");
		receivedRpOrderHeader.createCell(9).setCellValue("Recibido  Por");
		
		int receivedRowNumber = 1;
		
		for(ReceivedRpOrder rRpO:receivedRpOrderList) {
			Row row = receivedRpOrderSheet.createRow(receivedRowNumber++);
			row.createCell(0).setCellValue(rRpO.getReceivedRpOrderID());
			row.createCell(1).setCellValue(rRpO.getDateReceived());
			row.createCell(2).setCellValue(rRpO.getExpDate());
			row.createCell(3).setCellValue(rRpO.getLotID());
			row.createCell(4).setCellValue(rRpO.getQuantityReceived());
			if(null != rRpO.getQuantityRejected()) row.createCell(5).setCellValue(rRpO.getQuantityRejected());
			row.createCell(6).setCellValue(rRpO.getReceivedRpProductName());
			row.createCell(7).setCellValue(rRpO.getRpOrderID());
			row.createCell(8).setCellValue(rRpO.getTotalCost());
			if(null != rRpO.getCreateByUser()) row.createCell(9).setCellValue(rRpO.getCreateByUser().getFirstName() + " " + rRpO.getCreateByUser().getLastName());
		}
	}

}
