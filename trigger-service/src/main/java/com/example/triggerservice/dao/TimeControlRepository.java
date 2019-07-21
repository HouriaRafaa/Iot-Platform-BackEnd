package com.example.triggerservice.dao;


import com.example.triggerservice.entities.TimeControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TimeControlRepository  extends JpaRepository<TimeControl,Long> {

    List<TimeControl> findTimeControlByIdUser(Long id);
}
