/**
 * 
 */
package ics.model;

import org.springframework.stereotype.Component;

/**
 * @author fudi
 *
 */

@Component
public class OrderedProd extends Product {
	private Integer orderedProductQty;

	public Integer getOrderedProductQty() {
		return orderedProductQty;
	}

	public void setOrderedProductQty(Integer orderedProductQty) {
		this.orderedProductQty = orderedProductQty;
	}

	public OrderedProd() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderedProd(Integer orderedProductQty) {
		super();
		this.orderedProductQty = orderedProductQty;
	}

}
