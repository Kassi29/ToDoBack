package com.kass.backendtodo.services;


import com.kass.backendtodo.models.StatusModel;
import com.kass.backendtodo.repositories.IStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {
    private final IStatus iStatus;

    public StatusService(IStatus iStatus) {
        this.iStatus = iStatus;

    }

    @Transactional(readOnly = true)
    public List<StatusModel> getAll() {
        return iStatus.findAll();
    }


    @Transactional(readOnly = true)
    public Optional<StatusModel> getById(Long id) {
        return iStatus.findById(id);
    }


    @Transactional
    public StatusModel save(StatusModel statusModel) {
        return iStatus.save(statusModel);
    }

    @Transactional
    public Optional<StatusModel> update(Long id, StatusModel newStatus) {
        Optional<StatusModel> optionalStatusModel = iStatus.findById(id);
        if (optionalStatusModel.isPresent()) {
            StatusModel statusModel = optionalStatusModel.get();
            statusModel.setName(newStatus.getName());
            return Optional.of(iStatus.save(statusModel));
        }
        return optionalStatusModel;
    }



    @Transactional
    public Optional<StatusModel> delete(Long id) {
        Optional<StatusModel> statusModelOptional = iStatus.findById(id);
        statusModelOptional.ifPresent(iStatus::delete);
        return statusModelOptional;
    }

}
