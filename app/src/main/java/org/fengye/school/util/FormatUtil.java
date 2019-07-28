package org.fengye.school.util;

public class FormatUtil {

    public static String clearSpace(String string) {


        String content = "";
        // <p>段落替换为换行
//        content = content.replaceAll("<p .*?>", "\r\n");
        // <br><br/>替换为换行
//        content = content.replaceAll("<br\\s*/?>", "\r\n");
        // 去掉其它的<>之间的东西
        content = string.replaceAll("<p .*?>", "\r\n")
                .replaceAll("<br\\s*/?>", "\r\n")
                .replaceAll("\\<.*?>", "")
                .replaceAll(" ", "");  // 去掉空格
        return content.replaceAll("[\\t\\n\\r]", " ");
    }

}
