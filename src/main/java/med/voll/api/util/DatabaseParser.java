package med.voll.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseParser {
    public static String parseKeyNameForDuplicate(String errorMessage) {
        Pattern pattern = Pattern.compile("Duplicate entry '(.*)' for key '(.*)'");
        Matcher matcher = pattern.matcher(errorMessage);

        if (matcher.find()) {
            return matcher.group(2);
        } else {
            return "unknown";
        }
    }
}
