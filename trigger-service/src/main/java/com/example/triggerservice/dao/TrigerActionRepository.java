package com.example.triggerservice.dao;
;
import com.example.triggerservice.entities.React;
import com.example.triggerservice.entities.TrigerAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TrigerActionRepository extends JpaRepository<TrigerAction,Long> {


        public TrigerAction findTrigerActionByReact(React react);

}
