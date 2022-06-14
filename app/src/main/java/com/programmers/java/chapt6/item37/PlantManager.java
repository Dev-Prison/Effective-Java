package com.programmers.java.chapt6.item37;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

import com.programmers.java.chapt6.item37.Plant.LifeCycle;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlantManager {

    public static void main(String[] args) {
        List<Plant> plants = Arrays.asList(
            new Plant("ANNUAL_TREE_1", LifeCycle.ANNUAL),
            new Plant("ANNUAL_TREE_2", LifeCycle.ANNUAL),
            new Plant("ANNUAL_TREE_3", LifeCycle.ANNUAL),
            new Plant("BIENNIAL_TREE_1", LifeCycle.PERENNIAL),
            new Plant("PERENNIAL_TREE_1", LifeCycle.PERENNIAL)
        );

        System.out.println(
            plants.stream()
                .collect(
                    groupingBy(plant -> plant.lifeCycle)
                )
        );
    }

}
/* -- ordinal 사용
    public static void main(String[] args) {
        Set<Plant>[] plantsByLifeCycle = (Set<Plant>[]) new Set[Plant.LifeCycle.values().length];

        List<Plant> plants = Arrays.asList(
            new Plant("ANNUAL_TREE_1", LifeCycle.ANNUAL),
            new Plant("ANNUAL_TREE_2", LifeCycle.ANNUAL),
            new Plant("ANNUAL_TREE_3", LifeCycle.ANNUAL),
            new Plant("BIENNIAL_TREE_1", LifeCycle.BIENNIAL),
            new Plant("PERENNIAL_TREE_1", LifeCycle.PERENNIAL)
        );

        for (int i = 0; i < plantsByLifeCycle.length; i++) {
            plantsByLifeCycle[i] = new HashSet<>();
        }

        for (Plant p : plants) {
            plantsByLifeCycle[p.lifeCycle.ordinal()].add(p);
        }

        for (int i = 0; i < plantsByLifeCycle.length; i++) {
            System.out.printf("%s: %s%n", Plant.LifeCycle.values()[i], plantsByLifeCycle[i]);
        }

    }
 */
/* -- EnumMap 사용
    public static void main(String[] args) {
        Map<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle = new EnumMap<>(Plant.LifeCycle.class);

        List<Plant> plants = Arrays.asList(
            new Plant("ANNUAL_TREE_1", LifeCycle.ANNUAL),
            new Plant("ANNUAL_TREE_2", LifeCycle.ANNUAL),
            new Plant("ANNUAL_TREE_3", LifeCycle.ANNUAL),
            new Plant("BIENNIAL_TREE_1", LifeCycle.BIENNIAL),
            new Plant("PERENNIAL_TREE_1", LifeCycle.PERENNIAL)
        );

        for (Plant.LifeCycle lifeCycle : LifeCycle.values()) {
            plantsByLifeCycle.put(lifeCycle, new HashSet<>());
        }

        for (Plant p : plants) {
            plantsByLifeCycle.get(p.lifeCycle).add(p);
        }

        System.out.println(plantsByLifeCycle);
    }
 */
/* -- Stream 사용
    public static void main(String[] args) {

        List<Plant> plants = Arrays.asList(
            new Plant("ANNUAL_TREE_1", LifeCycle.ANNUAL),
            new Plant("ANNUAL_TREE_2", LifeCycle.ANNUAL),
            new Plant("ANNUAL_TREE_3", LifeCycle.ANNUAL),
            new Plant("BIENNIAL_TREE_1", LifeCycle.BIENNIAL),
            new Plant("PERENNIAL_TREE_1", LifeCycle.PERENNIAL)
        );

        System.out.println(
            plants.stream()
                .collect(
                    groupingBy(plant -> plant.lifeCycle,
                        () -> new EnumMap<>(LifeCycle.class),
                        toSet()
                    )
                )
        );

    }
 */
/* -- Stream 최적화
    public static void main(String[] args) {

        List<Plant> plants = Arrays.asList(
            new Plant("ANNUAL_TREE_1", LifeCycle.ANNUAL),
            new Plant("ANNUAL_TREE_2", LifeCycle.ANNUAL),
            new Plant("ANNUAL_TREE_3", LifeCycle.ANNUAL),
            new Plant("BIENNIAL_TREE_1", LifeCycle.BIENNIAL),
            new Plant("PERENNIAL_TREE_1", LifeCycle.PERENNIAL)
        );

        System.out.println(
            plants.stream()
                .collect(
                    groupingBy(plant -> plant.lifeCycle)
                )
        );

    }
 */