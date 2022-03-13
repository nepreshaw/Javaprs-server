package com.example.demo.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/request")
public class RequestController {
	@Autowired
	private RequestRepository reqRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Request>> getRequests(){
		var reqs = reqRepo.findAll();
		return new ResponseEntity<Iterable<Request>>(reqs, HttpStatus.FOUND);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Request> getRequest(@PathVariable int id) {
		var req = reqRepo.findById(id);
		if(req.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Request>(req.get(), HttpStatus.FOUND);
	}
	
	@PostMapping
	public ResponseEntity<Request> postRequest(@RequestBody Request request){
		if(request == null || request.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		reqRepo.save(request);
		return new ResponseEntity<Request>(request, HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
	public ResponsEntity
	
	

}
