package com.example.domain;

public class Product {

	private long id;
	
	private String name;
	
	private int price;
	
	private String detail;

	//必须要有无参构造方法
	//redis序列化时，使用的是无参构造方法进行创建对象，set方法进行赋值
	public Product() {
		
	}
	
	public Product(long id, String name, int price, String detail) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.detail = detail;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
