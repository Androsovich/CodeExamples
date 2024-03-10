package org.androsovich.applications.utils;

import org.androsovich.applications.entities.Privilege;
import org.androsovich.applications.entities.enums.OperationType;

import java.util.*;
import java.util.stream.Collectors;

import static org.androsovich.applications.constants.Constants.PRIVILEGE_DELIMITER;

public class UserUtils {

    private UserUtils(){}

    /**
     * get all available operations for the user over the applications
     */
    public static Map<String, List<String>> parsePrivileges(Collection<Privilege> privileges) {
        return privileges
                .stream()
                .map(Privilege::getName)
                .map(name-> name.split(PRIVILEGE_DELIMITER))
                .collect(
                        Collectors.groupingBy(array -> array[0],
                        Collectors.mapping(array -> array[1], Collectors.toList())));
    }

    /**
     * get all available statuses for the user over the applications
     * @param privileges user privileges list
     * @param operationType available operation like READ, WRITE, etc
     */
    public static List<String> getAvailableStatusesForUser(List<Privilege> privileges, OperationType operationType) {
        return UserUtils.parsePrivileges(privileges).get(operationType.name()).stream()
                .distinct()
                .toList();
    }
}
