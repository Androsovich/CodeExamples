package com.epam.summer.lab.collectors;

import com.epam.summer.lab.models.PowerConsumption;

import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PowerCollector implements Collector<PowerConsumption, Map<Month, PowerConsumption>,
        List<PowerConsumption>> {

    public static PowerCollector toPowerConsumptionList() {
        return new PowerCollector();
    }

    @Override
    public Supplier<Map<Month, PowerConsumption>> supplier() {
        return ConcurrentSkipListMap::new;
    }

    @Override
    public BiConsumer<Map<Month, PowerConsumption>, PowerConsumption> accumulator() {
        return (map, power) -> {
            final Month month = power.getPeriod().getMonth();
            map.computeIfPresent(month, (key, value) ->
                    value.getPowerConsumption() <= power.getPowerConsumption() ? power : value);

            map.putIfAbsent(month, power);
        };
    }

    @Override
    public BinaryOperator<Map<Month, PowerConsumption>> combiner() {

        return (firstMap, secondMap) -> {
            for (Map.Entry<Month, PowerConsumption> entry : firstMap.entrySet()) {
                final Month month = entry.getKey();

                if (secondMap.containsKey(month)) {

                    final PowerConsumption secondMapValue = secondMap.get(month);
                    firstMap.computeIfPresent(month, (key, value) ->
                            value.getPowerConsumption() <= secondMapValue.getPowerConsumption() ? secondMapValue : value);
                }
            }
            for (Map.Entry<Month, PowerConsumption> entry : secondMap.entrySet()) {
                firstMap.putIfAbsent(entry.getKey(), entry.getValue());
            }

            return firstMap;
        };
    }

    @Override
    public Function<Map<Month, PowerConsumption>, List<PowerConsumption>> finisher() {
        return this::mapListFunction;
    }

    private List<PowerConsumption> mapListFunction(Map<Month, PowerConsumption> monthPowerMap) {
        List<PowerConsumption> maxPowerConsumptions = new ArrayList<>(monthPowerMap.values());
        System.out.println(maxPowerConsumptions);
        return
                maxPowerConsumptions
                        .stream()
                        .sorted(Comparator.comparing(PowerConsumption::getPeriod))
                        .collect(Collectors.toList());
    }

    @Override
    public Set<Characteristics> characteristics() {
        Set<Characteristics> characteristicsSet = new HashSet<>();
        characteristicsSet.add(Characteristics.CONCURRENT);
        return characteristicsSet;
    }
}