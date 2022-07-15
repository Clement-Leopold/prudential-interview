package com.prudential.interview.carrental.handler;

/**
 * @author clement
 */

public interface CarRentalInterface {
    /**
     *  user can rent a car with specific model for a amount of time.
     * @param username username
     * @param model car model using integer instead
     * @param reserveTime duration for renting
     * @return 1 for success,0 for failed
     */
    Integer rentCar(String username, Integer model, Integer reserveTime) throws Exception;

    /**
     * user return car.
     * @param  rentId rent event id
     * @return 1 for return success.
     */
    Integer returnCar(String rentId) throws Exception;
}
