package com.aliat.alm.utils;

public class NumericUtils {

	public static Double parseDouble(String value) {

		try {
			if (value == null || value.trim().isEmpty()) {
				return null;
			}

			return Double.parseDouble(value.trim());

		} catch (Exception e) {
			return null;
		}
	}

	public static Integer parseInteger(String value) {

		try {
			if (value == null || value.trim().isEmpty()) {
				return null;
			}

			return Integer.parseInt(value.trim());

		} catch (Exception e) {
			return null;
		}
	}
}