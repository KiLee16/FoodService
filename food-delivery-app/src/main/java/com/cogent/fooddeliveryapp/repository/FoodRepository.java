package com.cogent.fooddeliveryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cogent.fooddeliveryapp.dto.FoodDto;
import com.cogent.fooddeliveryapp.enums.FoodType;

/**
 * @author : Ki Beom Lee
 * @time : 2022. 2. 22.-¿ÀÀü 9:45:17
 */
@Repository
public interface FoodRepository extends JpaRepository<FoodDto, Long> {
	public FoodDto findByFoodType(FoodType Category) ;
}
