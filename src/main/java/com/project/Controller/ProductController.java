package com.project.Controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Controller.dto.ProductDTO;
import com.project.models.Product;
import com.project.service.IProductService;

@RestController
@RequestMapping("api/product")
public class ProductController {

	@Autowired
	private IProductService productService;
	
	 @GetMapping("/find/{id}")
	 @PreAuthorize("hasAnyRole('USER','ADMIN','INVITED')")
	    public ResponseEntity<?> findById(@PathVariable Long id){
	       Optional<Product> productOptional = productService.findById(id);
	        if (productOptional.isPresent()){
	           Product product = productOptional.get();
	            ProductDTO productDTO = ProductDTO.builder()
	                    .id(product.getId())
	                    .name(product.getName())
	                    .price(product.getPrice())
	                    .maker(product.getMaker())
	                    .build();
	            return ResponseEntity.ok(productDTO);
	        }
	        return ResponseEntity.notFound().build();
	    }

	    @GetMapping("/findAll")
		@PreAuthorize("hasAnyRole('USER','ADMIN','INVITED')")
	    public ResponseEntity<?> findAll(){
	    	List<ProductDTO> prducList = productService.findAll().stream().map(product -> ProductDTO.builder()
	    			.id(product.getId())
	    			.name(product.getName())
	    			.price(product.getPrice())
	    			.maker(product.getMaker())
	    			.build()).toList();
	    	return ResponseEntity.ok(prducList);
	    }
	    
	    @PostMapping("/save")
		@PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<?> save(@RequestBody ProductDTO productDTO) throws URISyntaxException{
		    if(productDTO.getName().isBlank() || productDTO.getPrice() == null || productDTO.getMaker() == null) {
	    		return ResponseEntity.badRequest().build();
	    	}
	    	
	    	Product product = Product.builder()
	    			.name(productDTO.getName())
	    			.price(productDTO.getPrice())
	    			.maker(productDTO.getMaker())
	    			.build();
	    	
	    	productService.save(product);
	    	return ResponseEntity.created(new URI("/api/product/save")).build();
	    }
	    
	    @PutMapping("update/{id}")
		@PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductDTO productDTO){
	    	Optional<Product> productOptional = productService.findById(id);
	    	if(productOptional.isPresent()) {
	    		Product product = productOptional.get();
	    		product.setName(productDTO.getName());
	    		product.setPrice(productDTO.getPrice());
	    		product.setMaker(productDTO.getMaker());
	    		productService.save(product);
	    		return ResponseEntity.ok("Registro Actualizado");
	    	}
	    	return ResponseEntity.notFound().build();
	    }

	    @DeleteMapping("/delete/{id}")
		@PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<?> deleteById(@PathVariable Long id){
	    	if(id !=null) {
	    		productService.deleteById(id);
	    		return ResponseEntity.ok("Registro Eliminado");
	    	}
	    	return ResponseEntity.badRequest().build();
	    }
}