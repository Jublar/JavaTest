package com.kenect.variation;

public class Variations {

    public static void main(String[] args) {
        findVariations(args);
    }

    /**
     * Generates an array containing all possible combinations when combining elements from the input array
     *
     * For example: an input array of ["a", "b", "c"] should result in a new array like ["a", "ab", "ac", "abc", "acb", "b", "ba", ....]
     *
     * @param list An array of strings
     *
     * @return A string array with every possible combination of values when combining the elements from the input array
     */
    private static String[] findVariations(String[] list) {

        // This is the result of processing an input array of ["a", "b"]
        return new String[]{"a", "ab", "b", "ba"};
    }

    private static void printResults() {

    }
}
