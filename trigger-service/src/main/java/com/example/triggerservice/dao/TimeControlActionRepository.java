package com.example.triggerservice.dao;

import com.example.triggerservice.entities.TimeControl;
import com.example.triggerservice.entities.TimeControlAction;
import com.example.triggerservice.entities.Triger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeControlActionRepository extends JpaRepository<TimeControlAction , Long> {


    TimeControlAction findTimeControlActionByTimeControl(TimeControl timeControl);

}
