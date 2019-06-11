package com.example.openapi.controller;

import com.google.common.collect.Sets;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

/**
 * @Description: TODO
 * @Author gxy
 * @Date 2019/5/24
 */
public class Test {
    public static <E> Set<E> union(Set<? extends E> s1, Set<? extends E> s2) {
        Set<E> union = Sets.newHashSet();
        union.addAll(s1);
        union.addAll(s2);
        return union;
    }

    public static <T> T[] pickTwo(T a, T b, T c) {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0:
                return toArray(a, b);
            case 1:
                return toArray(a, c);
            case 2:
                return toArray(b, c);
        }
        // Can't get herez
        throw new AssertionError();
    }

    @SafeVarargs
    public static <T> T[] toArray(T... args) {
        return args;
    }

    public static void main(String[] args) throws IOException {
        Set<Integer> integers = Sets.newHashSet(1, 3, 5);
        Set<Double> doubles = Sets.newHashSet(2.0, 4.0, 6.0);
        Set<Number> numbers = union(integers, doubles);
//        String[] attributes = pickTwo("Good", "Fast", "Cheap");
        Stream.iterate(BigInteger.valueOf(2L), BigInteger::nextProbablePrime).limit(15000).forEach(System.out::println);
//        Stream.iterate(BigInteger.valueOf(2L), BigInteger::nextProbablePrime).map(p -> BigInteger.valueOf(2L).pow(p.intValueExact()).subtract(BigInteger.ONE))
//                .filter(mersenne -> mersenne.isProbablePrime(50))
//                .limit(20)
//                .forEach(System.out::println);
    }
}
