package org.arcsoft;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Slf4j
public class Main {

    public static void main(String[] args) {
        var req1 = new ArrayList<>(List.of("", "ABC", "ABC", "QQ"));
        req1.add(null);
        log.info("t1: {}", t1(req1));

        var req2 = List.of("ABC", "CDE", "EEF");
        log.info("t2: {}", t2(req2));

        var req3 = List.of("ABC", "CDEF", "EE");
        log.info("t3: {}", t3(req3));

        var req4 = List.of(5, 2, 10, 9, 4, 3, 10, 1, 13);
        log.info("t4: {}", t4(req4));

        var req5 = List.of(
                new Employee("Иванов И.И.", 25, "Инженер"),
                new Employee("Октанова И.И.", 29, "Менеджер"),
                new Employee("Григорьева А.В.", 55, "Бухгалтер"),
                new Employee("Кондратьев Н.Е.", 25, "Уборщик"),
                new Employee("Бортников С.У.", 64, "Директор"),
                new Employee("Пивоваров П.К.", 29, "Инженер"),
                new Employee("Обухов К.И.", 23, "Инженер"),
                new Employee("Усманов А.В.", 19, "Инженер")
        );
        log.info("t5: {}", t5(req5));
        log.info("t6: {}", t6(req5));


        //Task: Дана строка, которая представляет собой предложение из нескольких слов. Необходимо получить Map,
        // в которой ключом будет число, означающее длину слова, а значение это список слов указанной длины. Пробелы
        // и другие разделители необходимо убрать. Слова в разном регистре считаются одним и тем же словом.
        var req7 = "Мама мыла Окно, окно было довольно";
        log.info("t7: {}", t7(req7));

        var req8 = List.of("Мама мыла Окно, окно было довольно", "кровать", "огненный");
        log.info("t8: {}", t8(req8));

    }

    public static List<String> t1(List<String> input) {
        return input.stream()
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .distinct()
                .toList();
    }

    public static long t2(List<String> input) {
        return input.stream()
                .map(String::chars)
                .flatMap(IntStream::boxed)
                .distinct()
                .count();
    }

    public static String t3(List<String> input) {
        return input.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
    }

    public static Integer t4(List<Integer> input) {
        return input.stream()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst().orElse(null);
    }

    public static List<Employee> t5(List<Employee> input) {
        return input.stream()
                .filter(e -> e.position.equals("Инженер"))
                .sorted(Comparator.comparingInt(Employee::age).reversed())
                .limit(3)
                .toList();
    }

    public static double t6(List<Employee> input) {
        return input.stream()
                .filter(e -> e.position.equals("Инженер"))
                .mapToInt(Employee::age)
                .average()
                .orElse(0);
    }

    public static Map<Integer, List<String>> t7(String input) {
        return Arrays.stream(input.split("[^\\p{L}]+"))
                .map(String::toLowerCase)
                .distinct()
                .collect(groupingBy(String::length, toList()));
    }

    public static List<String> t8(List<String> input) {
        var result = new ArrayList<String>();
        input.stream()
                .flatMap(s -> Arrays.stream(s.split("[^\\p{L}]+")))
                .distinct()
                .collect(groupingBy(String::length, toList()))
                .entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .ifPresent(e -> result.addAll(e.getValue()));
        return result;
    }


    @Builder
    public record Employee(
            String name,
            int age,
            String position
    ) {
    }
}