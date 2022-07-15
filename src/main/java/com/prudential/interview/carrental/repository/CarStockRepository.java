package com.prudential.interview.carrental.repository;

import com.prudential.interview.domain.CarStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * Manipulate car stock.
 *
 * @author Administrator
 */
@Transactional(rollbackOn = Exception.class)
public interface CarStockRepository extends JpaRepository<CarStock, Integer> {

    /**
     * get stock for a car model.
     *
     * @param model model number
     * @return car stock.
     */
    CarStock getByCarModel(Integer model);

    /**
     * Update car stock by optimistic lock.
     *
     * @param stock       new stock.
     * @param model       car model number.
     * @param originStock origin stock.
     * @return 1 or 0
     */
    @Modifying(clearAutomatically = true)
    @Query("update CarStock  c set c.stock = ?1 where c.carModel = ?2  and c.stock = ?3")
    int updateCarModelStock(Integer stock, Integer model, Integer originStock);


}
