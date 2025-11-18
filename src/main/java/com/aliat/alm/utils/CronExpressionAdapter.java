package com.aliat.alm.utils;

public class CronExpressionAdapter {

    /**
     * Converts 5-field cron expressions (UNIX/jqcron) to Quartz 6-field expressions.
     * Ensures Quartz rules: day-of-month and day-of-week cannot both be set.
     */
    public static String toQuartzFormat(String cronExpr) {
        if (cronExpr == null || cronExpr.trim().isEmpty()) {
            throw new IllegalArgumentException("Cron expression is null or empty");
        }

        String expr = cronExpr.trim().replaceAll("\\s+", " ");
        String[] parts = expr.split(" ");

        // Already Quartz (6 or 7 fields)
        if (parts.length == 6 || parts.length == 7) {
            return expr;
        }

        // jqCron / UNIX (5 fields)
        if (parts.length == 5) {
            String minute = parts[0];
            String hour = parts[1];
            String dayOfMonth = parts[2];
            String month = parts[3];
            String dayOfWeek = parts[4];

            // Convert numeric DOW (1,3,5-7) to Quartz names
            dayOfWeek = convertDayOfWeekField(dayOfWeek);

            // ---------- FIXED QUARTZ RULES ----------
            boolean domAny = dayOfMonth.equals("*");
            boolean dowAny = dayOfWeek.equals("*");
            boolean dowList = !dayOfWeek.equals("*") && !dayOfWeek.equals("?");

            if (!domAny && dowAny) {
                // Case: DOM has a value, DOW is "*"
                dayOfWeek = "?";
            } else if (domAny && dowList) {
                // Case: DOM "*", DOW has specific days
                dayOfMonth = "?";
            } else if (domAny && dowAny) {
                // Case: both are "*"
                dayOfWeek = "?";
            }

            return String.format("0 %s %s %s %s %s",
                    minute, hour, dayOfMonth, month, dayOfWeek);
        }

        throw new IllegalArgumentException("Invalid cron expression format: " + cronExpr);
    }

    /**
     * Converts numeric day-of-week (1,2,5-7) into Quartz names (MON,TUE,FRI-SUN)
     */
    private static String convertDayOfWeekField(String dowField) {
        if (dowField.equals("*") || dowField.equals("?")) return dowField;

        StringBuilder result = new StringBuilder();
        String[] segments = dowField.split(",");

        for (int i = 0; i < segments.length; i++) {
            String seg = segments[i].trim();

            if (seg.contains("-")) {
                String[] range = seg.split("-");
                if (range.length == 2) {
                    result.append(numToDay(range[0]))
                          .append("-")
                          .append(numToDay(range[1]));
                }
            } else {
                result.append(numToDay(seg));
            }

            if (i < segments.length - 1) {
                result.append(",");
            }
        }

        return result.toString();
    }

    /**
     * Maps numeric DOW values to Quartz day names.
     */
    private static String numToDay(String num) {
        num = num.trim();
        switch (num) {
            case "0": case "7": return "SUN";
            case "1": return "MON";
            case "2": return "TUE";
            case "3": return "WED";
            case "4": return "THU";
            case "5": return "FRI";
            case "6": return "SAT";
            default: return num; // Already text
        }
    }
}