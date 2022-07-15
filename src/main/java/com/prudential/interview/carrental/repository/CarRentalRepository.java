package com.prudential.interview.carrental.repository;


import com.prudential.interview.domain.CarRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author clement
 */
@Transactional(rollbackFor = Exception.class)
public interface CarRentalRepository extends JpaRepository<CarRental, Integer> {

    /**
     *  Update car rent table for one car returned
     * @param eventId rent car event Id
     * @return 1 or 0
     */
    @Modifying(clearAutomatically = true)
    @Query("update CarRental  c set c.returned = 1 where c.id = ?1")
    int returnCar(String eventId);

    /**
     * find car rental event by id
     * @param id event id
     * @return car rental event
     */
    CarRental findCarRentalById(String id);
}
