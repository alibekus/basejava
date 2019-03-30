package kz.akbar.basejava.util;

import kz.akbar.basejava.model.Organization;

public class HtmlUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String formatDate(Organization.Position position) {
        return DateUtil.format(position.getStartDate()) + " - " + DateUtil.format(position.getEndDate());
    }
}
