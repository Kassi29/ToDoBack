package com.kass.backendtodo.repositories;

import com.kass.backendtodo.models.StatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatus extends JpaRepository<StatusModel, Long> {
}
