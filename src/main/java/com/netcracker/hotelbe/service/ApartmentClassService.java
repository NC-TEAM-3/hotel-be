package com.netcracker.hotelbe.service;

import com.netcracker.hotelbe.entity.ApartmentClass;
import com.netcracker.hotelbe.repository.ApartmentClassRepository;
import com.netcracker.hotelbe.service.filter.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

@Service
public class ApartmentClassService {

    @Autowired
    private ApartmentClassRepository apartmentClassRepository;

    @Autowired
    @Qualifier("apartmentClassValidator")
    private Validator apartmentClassValidator;

    @Autowired
    private FilterService filterService;

    public List<ApartmentClass> findAll() {
        return apartmentClassRepository.findAll();
    }

    public List<ApartmentClass> getAllByParams(Map<String, String> allParams) {
        if(allParams.size()!=0) {
            return apartmentClassRepository.findAll(filterService.fillFilter(allParams, ApartmentClass.class));
        } else {
            return apartmentClassRepository.findAll();
        }
    }

    public ApartmentClass save(final ApartmentClass apartmentClass) {
        return apartmentClassRepository.save(apartmentClass);
    }

    public ApartmentClass findById(final Long id) {
        return apartmentClassRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.valueOf(id))
        );
    }

    public ApartmentClass findByName(final String name){
        return apartmentClassRepository.findFirstByNameClass(name).orElseThrow(
                () -> new EntityNotFoundException(String.valueOf(name))
        );
    }

    public ApartmentClass update(ApartmentClass apartmentClass, final Long id) {
        apartmentClassRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.valueOf(id))
        );

        apartmentClass.setId(id);

        return apartmentClassRepository.save(apartmentClass);
    }

    public void deleteById(final Long id) {
        ApartmentClass delete = apartmentClassRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.valueOf(id))
        );

        apartmentClassRepository.delete(delete);
    }

    public void validate(final ApartmentClass apartmentClass, BindingResult bindingResult) throws MethodArgumentNotValidException {
        apartmentClassValidator.validate(apartmentClass, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
    }

    public ApartmentClass findByFields(String name, int numberOfCouchette, int numberOfRooms) {
        List<ApartmentClass> apartmentClasses = apartmentClassRepository.findByNameClassAndNumberOfCouchetteAndNumberOfRooms(name, numberOfCouchette, numberOfRooms);
        ApartmentClass apartmentClass;
        try {
            apartmentClass = apartmentClasses.get(0);
            apartmentClass.setId(null);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            apartmentClass = null;
        }
        return apartmentClass;
    }
}
