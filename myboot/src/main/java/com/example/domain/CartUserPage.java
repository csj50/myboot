package com.example.domain;

import java.util.List;

public class CartUserPage<T> {

	private List<T> cartList;
	
	private int count;

	public List<T> getCartList() {
		return cartList;
	}

	public void setCartList(List<T> cartList) {
		this.cartList = cartList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
