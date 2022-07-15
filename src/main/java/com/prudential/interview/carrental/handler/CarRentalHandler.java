package com.prudential.interview.carrental.handler;

import com.prudential.interview.carrental.repository.CarRentalRepository;
import com.prudential.interview.carrental.repository.CarStockRepository;
import com.prudential.interview.domain.CarRental;
import com.prudential.interview.domain.CarStock;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author clement
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class CarRentalHandler implements CarRentalInterface {


    @Override
    public Integer rentCar(String username, Integer model, Integer reserveTime) throws Exception {
        try {
            CarStock originStock = carStockRepository.getByCarModel(model);
            if (originStock.getStock() == 0) {
               return 0;
            }
            int result = carStockRepository.updateCarModelStock(originStock.getStock() - 1,
                    originStock.getCarModel(),
                    originStock.getStock());
            if (result != 0) {
                rentalRepository.save(new CarRental(username, model, reserveTime));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(" business exception");

        }
    }

    @Override
    public Integer returnCar(String rentEvent) throws Exception {
        try {
            CarRental carRental = rentalRepository.findCarRentalById(rentEvent);
            int result = rentalRepository.returnCar(rentEvent);
            if (result != 1) {
                return 0;
            }
            CarStock stock = carStockRepository.getByCarModel(carRental.getCarModel());
            result = carStockRepository.updateCarModelStock(stock.getStock() + 1,
                    carRental.getCarModel(), stock.getStock());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("business exception");
        }

    }

    @Getter
    private CarRentalRepository rentalRepository;
    @Getter
    private CarStockRepository carStockRepository;

    public CarRentalHandler(CarRentalRepository carRentalRepository, CarStockRepository carStockRepository) {
        this.rentalRepository = carRentalRepository;
        this.carStockRepository = carStockRepository;
    }
}
