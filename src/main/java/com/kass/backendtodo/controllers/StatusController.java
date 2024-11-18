package com.kass.backendtodo.controllers;

import com.kass.backendtodo.models.StatusModel;
import com.kass.backendtodo.services.StatusService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/status")
public class StatusController {

    private final StatusService statusService;


    public StatusController(StatusService statusService) {
        this.statusService = statusService;

    }

    @GetMapping
    public List<StatusModel> listAll() {
        return statusService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusModel> getById(@PathVariable Long id) {
        Optional<StatusModel> optionalStatusModel = statusService.getById(id);
        return optionalStatusModel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<?> create (@Valid @RequestBody StatusModel statusModel, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return validation(bindingResult);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(statusService.save(statusModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>update(@Valid @RequestBody StatusModel statusModel ,BindingResult bindingResult ,@PathVariable Long id ) {
        if (bindingResult.hasFieldErrors()) {
            return validation(bindingResult);
        }
        Optional<StatusModel> optionalStatusModel = statusService.update(id,statusModel);
        if (optionalStatusModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalStatusModel.orElseThrow());
        }
        return ResponseEntity.notFound().build();

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<StatusModel> delete(@PathVariable Long id) {
       Optional<StatusModel> statusModelOptional = statusService.delete(id);
       if (statusModelOptional.isPresent()) {
           return ResponseEntity.ok(statusModelOptional.orElseThrow());
       }
       return ResponseEntity.notFound().build();

    }

    private ResponseEntity<?> validation(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error ->
                errors.put(error.getField(), "The field "+ error.getField()+
                        " "+error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
