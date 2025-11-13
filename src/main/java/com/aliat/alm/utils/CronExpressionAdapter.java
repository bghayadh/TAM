package com.aliat.alm.utils;

public class CronExpressionAdapter {

    /**
     * Converts 5-field cron expressions (UNIX/qjcron) to Quartz 6-field expressions.
     * Supports day-of-week lists and ranges like 1-3,1,5-7.
     * Leaves 6 or 7 field Quartz expressions unchanged.
     */
    public static String toQuartzFormat(String cronExpr) {
        if (cronExpr == null || cronExpr.trim().isEmpty()) {
            throw new IllegalArgumentException("Cron expression is null or empty");
        }

        String expr = cronExpr.trim().replaceAll("\\s+", " ");
        String[] parts = expr.split(" ");

        // ✅ Already Quartz (6 or 7 fields)
        if (parts.length == 6 || parts.length == 7) {
            return expr;
        }

        // ✅ qjcron / UNIX (5 fields)
        if (parts.length == 5) {
            String minute = parts[0];
            String hour = parts[1];
            String dayOfMonth = parts[2];
            String month = parts[3];
            String dayOfWeek = parts[4];

            // Convert numeric/range/list dayOfWeek to Quartz names
            dayOfWeek = convertDayOfWeekField(dayOfWeek);

            // Adjust day-of-month vs day-of-week rules
            if (!dayOfWeek.equals("*") && !dayOfWeek.equals("?")) {
                dayOfMonth = "?";
            } else if (dayOfMonth.equals("*") && dayOfWeek.equals("*")) {
                dayOfWeek = "?";
            }

            return String.format("0 %s %s %s %s %s", minute, hour, dayOfMonth, month, dayOfWeek);
        }

        throw new IllegalArgumentException("Invalid cron expression format: " + cronExpr);
    }

    /**
     * Converts a numeric or mixed day-of-week field (1,2,5-7) into Quartz names (MON,TUE,FRI-SUN).
     */
    private static String convertDayOfWeekField(String dowField) {
        if (dowField.equals("*") || dowField.equals("?")) return dowField;

        StringBuilder result = new StringBuilder();

        // Split by comma first (for lists)
        String[] segments = dowField.split(",");
        for (int i = 0; i < segments.length; i++) {
            String seg = segments[i].trim();
            if (seg.isEmpty()) continue;

            if (seg.contains("-")) {
                // Handle ranges like 1-3 or 6-7
                String[] range = seg.split("-");
                if (range.length == 2) {
                    result.append(numToDay(range[0]))
                          .append("-")
                          .append(numToDay(range[1]));
                } else {
                    result.append(numToDay(seg));
                }
            } else {
                // Single day
                result.append(numToDay(seg));
            }

            if (i < segments.length - 1) {
                result.append(",");
            }
        }

        return result.toString();
    }

    /**
     * Maps numeric day values (0-7) to Quartz day names.
     */
    private static String numToDay(String num) {
        num = num.trim();
        switch (num) {
            case "0": case "7": return "SUN"; // both 0 and 7 are Sunday in UNIX
            case "1": return "MON";
            case "2": return "TUE";
            case "3": return "WED";
            case "4": return "THU";
            case "5": return "FRI";
            case "6": return "SAT";
            default: return num; // already text (e.g., MON)
        }
    }
}