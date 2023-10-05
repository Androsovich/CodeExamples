package org.androsovich.cars;

import lombok.extern.slf4j.Slf4j;
import org.androsovich.cars.entities.Car;
import org.androsovich.cars.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.List;

@SpringBootApplication
@Slf4j
public class CarsServiceApplication implements CommandLineRunner {

    @Autowired
    private CarRepository carRepository;

    public static void main(String[] args) {
        SpringApplication.run(CarsServiceApplication.class, args);
        log.info("Service A(Cars Service) started successfully");
    }

    @Override
    public void run(String... args) throws Exception {
        carRepository.saveAll(List.of(
				new Car("dsd", "aa"),
				new Car("dsd1", "aa1"),
				new Car("dsd2", "aa2"),
				new Car("dsd3", "aa3"),
				new Car("dsd4", "aa4"),
				new Car("dsd5", "aa5"))
		);
        carRepository.saveAll(List.of(
				new Car("dsd1111", "aa"),
				new Car("dsd1222", "aa1"),
				new Car("dsd233", "aa2"),
				new Car("dsd3", "aa331"),
				new Car("d22323123213", "aa4"),
				new Car("ds321321ssss5", "aa5"))
		);
        carRepository.saveAll(List.of(
				new Car("dsssaqqqssssd", "aa"),
				new Car("dsd1", "aa1"),
				new Car("dsssas2", "aa2"),
				new Car("dssadsdsadsasd3", "aa3"),
				new Car("dssadsaadsad4", "aa4"),
				new Car("dssadsadasdasdddddsad5", "aa5"))
		);
        carRepository.saveAll(List.of(
				new Car("dssssssd", "aa"),
				new Car("dsd1", "aa1"),
				new Car("dsss2", "aa2"),
				new Car("dsdsdsadsasd3", "aa3"),
				new Car("dssadadsad4", "aa4"),

				new Car("dssadsadasdasdddddsad5", "aa5"))
		);
        carRepository.saveAll(List.of(
				new Car("dssssssd", "aa"),
				new Car("ds777d1", "aa1"),
				new Car("dsss2", "aa2"),
				new Car("dsds77dsadsasd3", "aa3"),
				new Car("dssa77dadsad4", "aa4"),
				new Car("dssad77sadasdasdddddsad5", "aa5"))
		);
        carRepository.saveAll(List.of(
				new Car("dsssss888sd", "aa"),
				new Car("dsd888888881", "aa1"),
				new Car("ds88888888ss2", "aa2"),
				new Car("dsdsd88888sadsasd3", "aa3"),
				new Car("dssadad8888sad4", "aa4"),
				new Car("dssadsad88888asdas", "aa5"))
		);
    }
}