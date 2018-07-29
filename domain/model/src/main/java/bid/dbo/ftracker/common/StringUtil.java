package bid.dbo.ftracker.common;

public class StringUtil {

    public static boolean isEmpty(String value){
        return value == null || value.trim().isEmpty();
    }
}
