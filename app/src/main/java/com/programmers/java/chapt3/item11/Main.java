package com.programmers.java.chapt3.item11;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<PhoneNumber, String> m = new HashMap<>();
        m.put(new PhoneNumber(707,867,5309), "jenny");
        String s = m.get(new PhoneNumber(707, 867, 5309));
        System.out.println(s); // null 출력

        // 기존 hashCode
        PhoneNumber original = new PhoneNumber(707, 867, 5309);
        m.put(original, "jenny");
        String jenni = m.get(original);
        System.out.println(jenni); //제니 출력

    }
}
