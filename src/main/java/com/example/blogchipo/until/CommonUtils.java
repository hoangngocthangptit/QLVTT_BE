package com.example.blogchipo.until;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CommonUtils {
    public String genRandomId(String prefix) {
        // prefix + random number from 0 to 9999. For example: KHO0001, KHO0002, ...
        return prefix + String.format("%04d", (int) (Math.random() * 10000));
    }

    public LocalDate convertStringToLocalDate(String ngay) {
        if (ngay == null || ngay.isEmpty()) {
            return null;
        }
        return LocalDate.parse(ngay);
    }
}
