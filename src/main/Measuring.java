package main;

import java.time.Duration;
import java.time.Instant;

public class Measuring {

    public static void main(String[] args) {
        String string = "0123456701234567012345670123456701234567012345670123456701234567";
        char[] chars = string.toCharArray();
        Instant start = Instant.now();
        for (int i = 0; i < 1000000; i++) {
            chars[40] = 'r';
        }
        Instant end = Instant.now();
        Instant start3 = Instant.now();

        Instant end3 = Instant.now();
        System.out.println(Duration.between(start, end));
        System.out.println(Duration.between(start3, end3));
    }

}
