package org.androsovich.drivers.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.androsovich.drivers.entities.Driver;
import org.androsovich.drivers.repositories.DriverRepository;
import org.androsovich.drivers.services.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static org.androsovich.drivers.constants.CommonConstants.CRON;

@Slf4j
@Service
public class SchedulerServiceImpl  implements SchedulerService {


    @Autowired
    DriverRepository driverRepository;

    @Scheduled(cron = CRON)
    public void congratulationDriversBirthday() {
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        List<Driver> list = driverRepository.findDriverByBirthday(month, day);
        if (!list.isEmpty()) {
            list.forEach(driver -> {
                log.info("Congratulation. Driver id: {},name : {}, Date: {}", driver.getId(), driver.getFullName(), date);
            });
        }
    }
}
