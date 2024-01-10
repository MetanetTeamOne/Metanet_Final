package com.metanet.finalproject.orders.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class OrdersInsert {
	private int orders_count;
	private int orders_price;
	private int laundry_id;
}
