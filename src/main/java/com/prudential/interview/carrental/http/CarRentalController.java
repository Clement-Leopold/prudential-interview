package com.prudential.interview.carrental.http;

import com.prudential.interview.carrental.handler.CarRentalHandler;
import com.prudential.interview.domain.CarRental;
import com.prudential.interview.domain.CarStock;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author clement
 */
@RestController
@RequestMapping("cars")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CarRentalController {

    /**
     * to eliminate the long time delay for peek traffic.
     */
    private BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(100);

    @PostMapping("rent")
    public void rentCar(@RequestBody CarRental carRental) throws Exception {
        int secondsToTimeOut = 100;
        if (!queue.offer(1, secondsToTimeOut, TimeUnit.SECONDS)) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "request queueing");
        }
        try {
            int result = this.carRentalHandler.rentCar(carRental.getUsername(), carRental.getCarModel(), carRental.getReserveSeconds());
            if (result == 0) {
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "car is not enough");
            }
        }finally {
            queue.poll();
        }


    }

    @PostMapping("return")
    public void releaseCar(@RequestBody CarRental carRental) throws Exception {
        this.carRentalHandler.returnCar(carRental.getId());
    }


    @GetMapping
    public List<CarStock> getCarStock() {
        return carRentalHandler.getCarStockRepository().findAll();
    }

    @GetMapping("rent/records")
    public List<CarRental> getCarRentalRecord() {
        try {
            return carRentalHandler.getRentalRepository().findAll();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private CarRentalHandler carRentalHandler;

    public CarRentalController(CarRentalHandler carRentalHandler) {
        this.carRentalHandler = carRentalHandler;
    }

}
