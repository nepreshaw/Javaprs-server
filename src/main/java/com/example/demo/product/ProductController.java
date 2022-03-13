package com.example.demo.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/product")
public class ProductController {
	@Autowired
	private ProductRepository prodRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Product>> getProducts() {
		var products = prodRepo.findAll();
		return new ResponseEntity<Iterable<Product>>(products, HttpStatus.FOUND);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Product> getProduct(@PathVariable int id){
		var product = prodRepo.findById(id);
		if(product.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(product.get(), HttpStatus.FOUND);
	}
	
	@PostMapping
	public ResponseEntity<Product> postProduct(@RequestBody Product product){
		if(product == null || product.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		prodRepo.save(product);
		return new ResponseEntity<Product>(product, HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Product> putProduct(@PathVariable int id, @RequestBody Product product){
		if(product == null || product.getId() == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var prod = prodRepo.findById(id);
		if(prod.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		prodRepo.save(product);
		return new ResponseEntity<Product>(product, HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteProduct(@PathVariable int id) {
		var prod = prodRepo.findById(id);
		if(prod.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		prodRepo.delete(prod.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
