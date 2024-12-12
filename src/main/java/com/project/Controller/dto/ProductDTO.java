package com.project.Controller.dto;

import java.math.BigDecimal;

import com.project.models.Maker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
	
         private Long id;
	     private String name;
	     private BigDecimal price;
	     private Maker maker;

}
