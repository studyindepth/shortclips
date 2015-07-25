package vn.shortclips.domain.video.valueobject;

import java.util.Iterator;
import java.util.List;

public class Sort implements Iterable<Order> {
	private List<Order> orders;

	public Sort(List<Order> orders) {
		this.orders = orders;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public Iterator<Order> iterator() {
		return this.orders.iterator();
	}

}
