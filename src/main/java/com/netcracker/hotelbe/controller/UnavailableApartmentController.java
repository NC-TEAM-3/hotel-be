package com.netcracker.hotelbe.controller;

import com.netcracker.hotelbe.entity.UnavailableApartment;
import com.netcracker.hotelbe.service.UnavailableApartmentService;
import com.netcracker.hotelbe.utils.RuntimeExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("unavailableApartments")
public class UnavailableApartmentController {

    @Autowired
    private UnavailableApartmentService unavailableApartmentService;

    @GetMapping
    public ResponseEntity<List<UnavailableApartment>> getAll() {
        return new ResponseEntity<>(unavailableApartmentService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnavailableApartment> getById(@PathVariable("id") final Long id) {
        return new ResponseEntity<>(unavailableApartmentService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UnavailableApartment> create(@RequestBody UnavailableApartment unavailableApartment) {
        try {
            return new ResponseEntity<>(unavailableApartmentService.save(unavailableApartment), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return RuntimeExceptionHandler.handlePSQLException(e);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<UnavailableApartment> update(@RequestBody UnavailableApartment unavailableApartment, @PathVariable("id") final Long id) {
        try {
            return new ResponseEntity<>(unavailableApartmentService.update(unavailableApartment, id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return RuntimeExceptionHandler.handlePSQLException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") final Long id) {
        unavailableApartmentService.deleteById(id);

        return new ResponseEntity(HttpStatus.OK);
    }
}
