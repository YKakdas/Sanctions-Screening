package com.yasarcan.app;

public class StringApproximation {
    public boolean StringApprox(String input, String xml) {
        input = input.toLowerCase();
        xml = xml.toLowerCase();
        if (Math.abs(input.length() - xml.length()) > 1) {
            return false;
        }
        if (input.length() == xml.length()) {
            int count = 0;
            for (int i = 0; i < input.length(); ++i) {
                if (input.charAt(i) != xml.charAt(i)) continue;
                ++count;
            }
            return count == input.length() || count == input.length() - 1;
        }
        if (input.length() > xml.length()) {
            for (int i = 0; i < input.length(); ++i) {
                String temp = input.substring(0, i) + input.substring(i + 1);
                if (!xml.equals(temp)) continue;
                return true;
            }
        } else {
            for (int i = 0; i < xml.length(); ++i) {
                String temp = xml.substring(0, i) + xml.substring(i + 1);
                if (!input.equals(temp)) continue;
                return true;
            }
        }
        return false;
    }
}