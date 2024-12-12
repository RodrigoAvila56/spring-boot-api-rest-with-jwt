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

import com.project.Controller.dto.MakerDTO;
import com.project.models.Maker;
import com.project.service.IMakerService;

@RestController
@RequestMapping("/api/maker")
public class MakerController {

    @Autowired
    private IMakerService makerService;

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','INVITED')")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Maker>  makerOptional = makerService.findById(id);

        if (makerOptional.isPresent()){
            Maker maker = makerOptional.get();

            MakerDTO makerDTO = MakerDTO.builder()
                    .id(maker.getId())
                    .name(maker.getName())
                    .productList(maker.getProductList())
                    .build();
            return ResponseEntity.ok(makerDTO);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/findAll")
    @PreAuthorize("hasAnyRole('USER','ADMIN','INVITED')")
    public ResponseEntity<?> findAll(){
    	List<MakerDTO> makerkist = makerService.findAll().stream().map(maker -> MakerDTO.builder().id(maker.getId())
    			.name(maker.getName())
    			.productList(maker.getProductList())
    			.build()).toList();
    	return ResponseEntity.ok(makerkist);
    }
    
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> save(@RequestBody MakerDTO makerDTO) throws URISyntaxException{
        if(makerDTO.getName().isBlank()) {
    		return ResponseEntity.badRequest().build();
    	}
    	makerService.save(Maker.builder().name(makerDTO.getName()).build());
    	return ResponseEntity.created(new URI("/api/maker/save")).build();
    }
  
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateMaker(@PathVariable Long id,@RequestBody MakerDTO makerDTO){
        Optional<Maker> makerOptional = makerService.findById(id);
    	
    	if (makerOptional.isPresent()) {
			Maker maker = makerOptional.get();
			maker.setName(makerDTO.getName());
			makerService.save(maker);
			return ResponseEntity.ok("Registro Actualizado");
		}
    	return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
    	if(id !=null) {
    		makerService.deleteById(id);
    		return ResponseEntity.ok("Registro Eliminado");
    	}
    	return ResponseEntity.badRequest().build();
    }

}