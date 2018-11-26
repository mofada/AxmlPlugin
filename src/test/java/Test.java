import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date 2018/11/19-9:30
 * @User fada
 * @Email fada@mofada.cn
 * @Description
 **/
public class Test {
    private static final String MUSTACHE_REGEX = "^\\{\\{(.+)}}$";
    private static final String NUMBER_REGEX = "[0-9]+";
    private static final String COLOR_REGEX = "^#([0-9]|[A-F]|[a-f]){3}|([0-9]|[A-F]|[a-f]){6}$|^rgb\\(([0-9]{1,2}|1[0-9][0-9]|2[0-4][0-9]|25[0-5]),([0-9]{1,2}|1[0-9][0-9]|2[0-4][0-9]|25[0-5]),([0-9]{1,2}|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\)$|^rgba\\(([0-9]{1,2}|1[0-9][0-9]|2[0-4][0-9]|25[0-5]),([0-9]{1,2}|1[0-9][0-9]|2[0-4][0-9]|25[0-5]),([0-9]{1,2}|1[0-9][0-9]|2[0-4][0-9]|25[0-5]),([0].[0-9]{1,2}|1)\\)$";
    private static final String REGEX_255 = "[0-9]{1,2}|1[0-9][0-9]|2[0-4][0-9]|25[0-5]";
    private static final String FLOAT_REGEX = "-?[0-9]*.?[0-9]*";

    public static void main(String[] args) {

        String matches = "-1";
        System.out.println(matches.matches(FLOAT_REGEX));

    }


    private static String arrayToString(List<String> enumValue) {
        StringBuilder builder = new StringBuilder();
        for (String s : enumValue) {
            builder.append(s);
            builder.append(",");
        }
        return builder.length() > 0 ? builder.substring(0, builder.length() - 1) : "";
    }
}
