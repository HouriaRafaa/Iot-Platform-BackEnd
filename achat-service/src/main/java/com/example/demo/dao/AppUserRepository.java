package com.example.demo.dao;

import com.example.demo.IStats;
import com.example.demo.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    public AppUser findByUserName(String username);
    public AppUser findByEmailIgnoreCase(String email);
    public AppUser findAppUserById(Long id) ;

    @Query(nativeQuery = true , value=
            "select date(joined_date) as 'date',count(*) as'count' from app_user group by date order by date;")
    public List<IStats> UserStatsMonth();
}
