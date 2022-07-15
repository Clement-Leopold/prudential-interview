package com.prudential.interview.carrental.handler;

import com.prudential.interview.carrental.handler.CarRentalHandler;
import com.prudential.interview.domain.CarRental;
import com.prudential.interview.domain.CarStock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@SpringBootTest()
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CarRentalHandlerTest {

    @Test
    void contextLoads() {
    }

    @Autowired
    CarRentalHandler handler;

    /**
     * test the normal business flow
     */
    @Test
    @Transactional
    void testRentCar() throws Exception {
        // test rent car
        int result = handler.rentCar("test", 1, 10);
        Assert.isTrue(result == 1);
        // test rent car
        result = handler.rentCar("anotherUser", 1, 10);
        Assert.isTrue(result == 1);
        // test stock is empty
        CarStock carStock = handler.getCarStockRepository().getByCarModel(1);
        Assert.isTrue(carStock.getStock() == 0);
        // test rent event is equal to 2
        List<CarRental> rental = handler.getRentalRepository().findAll();
        Assert.isTrue(rental.size() == 2);
    }

    /**
     * test for race condition.
     */
    @Test
    @Transactional
    void testRentCarConcurrently() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            Runnable r = () -> {
                try {
                    countDownLatch.await();
                    handler.rentCar(UUID.randomUUID().toString(), 1, 100);
                    handler.rentCar(UUID.randomUUID().toString(), 2, 100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            Thread t = new Thread(r);
            t.start();
            countDownLatch.countDown();
        }
        Thread.sleep(5000);
        List<CarStock> left = handler.getCarStockRepository().findAll();
        List<CarRental> events = handler.getRentalRepository().findAll();
        for (CarStock c : left) {
            Assert.isTrue(c.getStock() == 0, "rent go wrong, left car is not empty");
        }
        Assert.isTrue(events.size() == 4, "rent go wrong, rent event is not equal to all of the cars");
    }
}
