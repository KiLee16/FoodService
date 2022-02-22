package com.cogent.fooddeliveryapp.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CollectionId;

import com.cogent.fooddeliveryapp.enums.FoodType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Ki Beom Lee
 * @time : 2022. 2. 15.-?˜¤?›„ 2:32:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FoodDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String foodName;
	@NotBlank
	private String description;
	@NotBlank
	private String foodPic;
	@NotNull
	private Float foodPrice;
	@NotNull
	@Enumerated(EnumType.STRING)
	private FoodType foodType;
}
