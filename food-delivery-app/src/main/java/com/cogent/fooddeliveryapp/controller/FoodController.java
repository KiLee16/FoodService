package com.cogent.fooddeliveryapp.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.fooddeliveryapp.IdNotFoundException;
import com.cogent.fooddeliveryapp.dto.FoodDto;
import com.cogent.fooddeliveryapp.enums.FoodType;
import com.cogent.fooddeliveryapp.exception.NoDataFoundException;
import com.cogent.fooddeliveryapp.repository.FoodRepository;

/**
 * @author : Ki Beom Lee
 * @time : 2022. 2. 22.-���� 9:22:37
 */
@RestController
@RequestMapping("/api/food")
@Validated  // to use with min 
//validated vs valid  : to use with min / validate received values 
public class FoodController {
	@Autowired
	FoodRepository foodRepository; 
	
	@PostMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?>  registerFood(@Valid @RequestBody FoodDto food) {
		
		FoodDto food2 = foodRepository.save(food);
		
		
		return ResponseEntity.status(201).body(food2) ;
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?>  getFoodById( @PathVariable("id") @Min(1) long id) {
		
		FoodDto food2 = foodRepository.findById(id).orElseThrow(()-> new IdNotFoundException("food id not found"));
		
		
		return ResponseEntity.ok(food2) ;
	}
	
	@GetMapping(value = "/")
	public ResponseEntity<?>  getAllFood( ) {
		List<FoodDto> foods = new ArrayList<>();
		foods = foodRepository.findAll();
		if(foods.size() == 0) {
			
			Map<String, Object> map = new HashMap<>();
			map.put("message", "fail");
			map.put("data","no food are available ");// this object it will transform it into JSON (implicitly).
			// it will use json api.
			
			return ResponseEntity.status(200).body(map);
		}
			
		return ResponseEntity.ok(foods) ;
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?>  updateFoodById( @PathVariable("id") @Min(1) long id, @Valid @RequestBody FoodDto food) {
		
		FoodDto food2 = foodRepository.findById(id).orElseThrow(()-> new IdNotFoundException("Soory Food Not Found"));
		food.setId(id); 
		food2 = food; 
		foodRepository.save(food2);
		
		return ResponseEntity.ok(food2) ;
	}
	
	
	@GetMapping(value = "/cate/{category}")
	public ResponseEntity<?>  getByCategory(@PathVariable("category") FoodType category ) {
		System.out.println(category);
		FoodDto food = foodRepository.findByFoodType(category);
		
		if(food == null) {
			throw new NoDataFoundException("no items in the category");
		}
			
		return ResponseEntity.status(200).body(food) ;
	}
	
	@GetMapping("/all/desc")
	
	public ResponseEntity<?>  getAllDesc() {

		
		List<FoodDto> foods = new ArrayList<>();
		foods = foodRepository.findAll();
		Collections.sort(foods, (a,b) -> b.getId().compareTo(a.getId()));
		return ResponseEntity.status(200).body(foods) ;
	}
	
@GetMapping("/all/asc")
	
	public ResponseEntity<?>  getAllAsc() {

		
		List<FoodDto> foods = new ArrayList<>();
		foods = foodRepository.findAll();
		Collections.sort(foods, (a,b) -> a.getId().compareTo(b.getId()));
		return ResponseEntity.status(200).body(foods) ;
	}
	
	
	
}

	
	
	
	

