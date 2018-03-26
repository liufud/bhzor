package ics.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ics.model.Order;
import ics.model.Product;
import ics.model.ReplenishmentOrder;
import ics.model.User;
import ics.services.OrderService;
import ics.services.ProductService;
import ics.services.ReplenishmentOrderService;
import ics.services.UserService;
import ics.view.ExcelUserListReportView;

@Controller
public class ReportController {
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ReplenishmentOrderService rpOrderService;
	
	@RequestMapping(value="report", method=RequestMethod.GET)
	public ModelAndView userListReport(HttpServletRequest req, 
										HttpServletResponse res) {
		System.out.println("Enter userListReport controler");
		String typeReport = req.getParameter("type");
		//create data
		List<User> userList = userService.getAllUsers();
		List<Product> productList = (List<Product>) productService.listProducts();
		List<Order> orderList = (List<Order>) orderService.listOrders();
		List<ReplenishmentOrder> rpOrderList = (List<ReplenishmentOrder>) rpOrderService.listOrders();
		
		List<Object> fullDB = new ArrayList<Object>();
		fullDB.add(0,userList);
		fullDB.add(1, productList);
		fullDB.add(2, orderList);
		fullDB.add(3, rpOrderList);
		
		
//		if(typeReport != null && typeReport.equals("xls")) {
//			return new ModelAndView(new ExcelUserListReportView(), "userList", fullDB);
//		}
		return new ModelAndView(new ExcelUserListReportView(), "userList", fullDB);
	}
}
