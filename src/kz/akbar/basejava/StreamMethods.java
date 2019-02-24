package kz.akbar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamMethods {


    /**
     * accepts array of integer numbers, each number from 1 to 9,
     * remove duplicates and compose one number from unique digits in minimal value
     * @param values - array of int
     * @return - composed from unique number of values in minimal value
     */
    private static int minValue(int[] values) {

        return Arrays.stream(values).sorted().distinct().reduce(0, (left, right) -> {
            long number = 0;
            int[] digits = Arrays.stream(values).sorted().distinct().toArray();

            for (int i = 0; i < digits.length; i++) {
                number += (long) Math.pow(10, digits.length - i - 1) * digits[i];
            }
            return (int) number;
        });
    }

    /**
     * accepts list of Integer objects, calculate sum of integers, in dependence of odd or even sum,
     * remove odd or even numbers from list
     * @param integers - list of Integers
     * @return - list of only with odd or even integers.
     */

    private static List<Integer> oddOrEven(List<Integer> integers) {

        return integers.stream().mapToInt(value -> value).filter(value -> {
            if (integers.stream().mapToInt(value1 -> value1).sum() % 2 == 0) {
                return value % 2 != 0;
            } else {
                return value % 2 == 0;
            }
        }).boxed().collect(Collectors.toList());
    }

    public static void main(String[] args) {
        int[] values = {9, 2, 4, 5, 6, 1, 4, 8, 5, 3, 7, 8};
        System.out.println(minValue(values));
        List<Integer> numbers = Arrays.stream(values).boxed().collect(Collectors.toList());
        System.out.println(oddOrEven(numbers));
    }
}
