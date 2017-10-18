/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itex.entity.ResultEntity;

import java.util.Collection;
import java.util.Date;

/**
 *
 * @author tinz
 */
@Repository
public interface ResultRepository extends JpaRepository<ResultEntity, Long>{

    @Query(nativeQuery = true, value = "select * from qa.result r where r.date>=:periodBegin and r.date<=:periodEnd and r.address_id=:addressId order by r.date")
    Collection<ResultEntity> getByPeriod(@Param("periodBegin") Date periodBegin, @Param("periodEnd") Date periodEnd, @Param("addressId") Long addressId);

    @Query(nativeQuery = true, value = "select avg(r.score) from qa.result r where r.date>=:periodBegin and r.date<=:periodEnd")
    Double getAverageByDate(@Param("periodBegin") Date periodBegin, @Param("periodEnd") Date periodEnd);

    @Query(nativeQuery = true, value = "select r.id from qa.address r where r.url=:url")
    java.lang.Long getIdByUrl(@Param("url") String url);

    @Query(nativeQuery = true, value = "select max(id) from qa.result r where r.address_id=:addressId and r.type=:deviceType")
    java.lang.Long getLastScore(@Param("addressId") Long addressId, @Param("deviceType") String deviceType);

    @Query(nativeQuery = true, value = "select r.score from qa.result r where r.address_id=:addressId and r.type=:deviceType order by r.id")
    java.util.List<Integer> getLastScores(@Param("addressId") Long addressId, @Param("deviceType") String deviceType);
}
