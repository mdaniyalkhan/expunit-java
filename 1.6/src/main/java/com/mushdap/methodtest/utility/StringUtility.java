package com.mushdap.methodtest.utility;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Utility to extend String related operations
 */
@SuppressWarnings("WeakerAccess")
public class StringUtility {

    /**
     * Split string into String Array
     *
     * @param string    String separated by character
     * @param character character to split string
     * @return String Array
     */
    public static String[] Split(String string, char character) {

        String[] rets = string.split("\\Q" + character + "\\E");
        if (string.endsWith(character + "")) {
            // Add empty string for .Net
            ArrayList<String> ret_al = new ArrayList<String>(Arrays.asList(rets));
            ret_al.add("");
            rets = ret_al.toArray(new String[ret_al.size()]);
        }
        return rets;
    }

    /**
     * Check whether string is null or empty
     *
     * @param string String to be checked
     * @return True of string is null or empty
     */
    public static boolean isNullOrEmpty(String string) {

        return (string == null || "".equals(string));
    }
}
