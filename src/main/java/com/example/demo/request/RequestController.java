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
	
	
	@GetMapping("review/{userId}")
	public ResponseEntity<Iterable<Request>> getReviews(@PathVariable int userId){
		Iterable<Request> req = reqRepo.findByStatusAndUserIdNot("REVIEW", userId);
		
		return new ResponseEntity<Iterable<Request>>(req, HttpStatus.FOUND);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("approve/{id}")
	public ResponseEntity approveRequest(@PathVariable int id, @RequestBody Request request) {
		request.setStatus("APPROVED");
		return putRequest(id, request);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("reject/{id}")
	public ResponseEntity rejectReject(@PathVariable int id, @RequestBody Request request) {
		request.setStatus("REJECTED");
		return putRequest(id, request);
	}
	
	//turn this into a ternary statement
	@PutMapping("/review")
	public ResponseEntity<Request> putReview(@RequestBody Request request){
		if(request.getTotal() <= 50) {
			request.setStatus("APPROVED");
		} else {
			request.setStatus("REVIEW");
		}
		reqRepo.save(request);
		return new ResponseEntity<Request>(request, HttpStatus.NO_CONTENT);
	}
	
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
	public ResponseEntity<Request> putRequest(@PathVariable int id, @RequestBody Request request){
		if(request == null || request.getId() == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var req = reqRepo.findById(id);
		if(req.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		reqRepo.save(request);
		return new ResponseEntity<Request>(request, HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteRequest(@PathVariable int id) {
		var req = reqRepo.findById(id);
		if(req.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		reqRepo.delete(req.get());
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
