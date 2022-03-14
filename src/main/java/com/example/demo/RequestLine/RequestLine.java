package com.example.demo.RequestLine;

import javax.persistence.*;

import com.example.demo.product.Product;
import com.example.demo.request.Request;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class RequestLine {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(nullable=false)
	private int quantity = 1;
	
	@JsonBackReference
	@ManyToOne(optional=false)
	@JoinColumn(name="requestId")
	private Request request;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="productId")
	private Product product;
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	public Request getRequest() {
		return request;
	}



	public void setRequest(Request request) {
		this.request = request;
	}



	public Product getProduct() {
		return product;
	}



	public void setProduct(Product product) {
		this.product = product;
	}



	public RequestLine() {}
	
	//work on the receing end for product and request

}
