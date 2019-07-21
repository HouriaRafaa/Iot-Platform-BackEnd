package com.example.demo.dao;



import com.example.demo.IStats;
import com.example.demo.entities.AchatCredit;
import com.example.demo.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

@RepositoryRestResource
public interface AchatCreditRepository extends JpaRepository<AchatCredit, Long> {

    @Query("select a from AchatCredit a where a.appUser = ?1")

    public List<AchatCredit> findAllByAppUser(AppUser appUser) ;
    public List<AchatCredit> findAllByDateAchat(Date dataAchat);

    @Query(nativeQuery = true ,value=
" select date(date_achat) as 'date', sum(c.price)  as 'count' from achat_credit a , credit_produit c  where a.produit_id = c.id group by date order by date;"    )
    public List<IStats> AchatDayStats() ;

    @Query(nativeQuery = true,
    value="select sum(c.price)  from achat_credit a , credit_produit c  where a.produit_id = c.id and Month(a.date_achat) = Month(current_date);")
    public int currentMonthRevenue();
}
