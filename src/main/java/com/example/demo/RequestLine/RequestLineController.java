package com.example.demo.RequestLine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.request.RequestRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/requestline")
public class RequestLineController {
	@Autowired
	private RequestLineRepository reqLineRepo;
	@Autowired
	private RequestRepository reqRepo;
	
	@SuppressWarnings("rawtypes")
	private ResponseEntity recalcReqTotal(int requestId) {
		var reqOpt = reqRepo.findById(requestId);
		if(reqOpt.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		var request = reqOpt.get();
		var requestTotal = 0;
		for(var requestline : request.getRequestlines()) {
			requestTotal += requestline.getProduct().getPrice() * requestline.getQuantity();
		}
		request.setTotal(requestTotal);
		reqRepo.save(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<RequestLine>> getRequestLines(){
		var rls = reqLineRepo.findAll();
		return new ResponseEntity<Iterable<RequestLine>>(rls, HttpStatus.FOUND);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<RequestLine> getRequestLine(@PathVariable int id){
		var rl = reqLineRepo.findById(id);
		if(rl.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RequestLine>(rl.get(), HttpStatus.FOUND);
	}
	
	@PostMapping
	public ResponseEntity<RequestLine> postRequestLine(@RequestBody RequestLine requestline){
		if(requestline == null || requestline.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		reqLineRepo.save(requestline);
		recalcReqTotal(requestline.getRequest().getId());
		return new ResponseEntity<RequestLine>(requestline, HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<RequestLine> putRequestLine(@PathVariable int id, @RequestBody RequestLine requestline){
		if(requestline == null || requestline.getId() == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var rl = reqLineRepo.findById(id);
		if(rl.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		reqLineRepo.save(requestline);
		recalcReqTotal(requestline.getRequest().getId());
		return new ResponseEntity<RequestLine>(requestline, HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteRequestLine(@PathVariable int id, @RequestBody RequestLine requestline) {
		var rl = reqLineRepo.findById(id);
		if(rl.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		reqLineRepo.delete(rl.get());
		recalcReqTotal(requestline.getRequest().getId());
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
