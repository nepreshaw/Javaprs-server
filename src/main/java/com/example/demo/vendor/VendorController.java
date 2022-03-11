package com.example.demo.vendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/vendor")
public class VendorController {
	@Autowired
	private VendorRepository vendRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Vendor>> getVendors(){
		var vendors = vendRepo.findAll();
		return new ResponseEntity<Iterable<Vendor>>(vendors, HttpStatus.FOUND);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Vendor> getVendor(@PathVariable int id){
		var vendor = vendRepo.findById(id);
		if(vendor.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Vendor>(vendor.get(), HttpStatus.FOUND);
	}

}
