package br.com.kartstore.service.database.repository;

import br.com.kartstore.service.database.entity.SaleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<SaleEntity, Integer> {

     List<SaleEntity> findByNumControlIn(List<String> numControl);

     @Query(value = "from SaleEntity s " +
                    " where 1 = 1 " +
                    " and (:saleId is null or s.id = :saleId ) " +
                    " and (:createdStart is null or s.created >= :createdStart) " +
                    " and (:createdEnd is null or s.created <= :createdEnd) "
             ,nativeQuery = false)
     Page<SaleEntity> findSales(Long saleId, Date createdStart, Date createdEnd, Pageable pageable);


}
