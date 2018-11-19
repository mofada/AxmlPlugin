package cn.mofada.axml.language;

import com.intellij.lang.Language;

/**
 * @Date 2018/11/17-17:30
 * @User fada
 * @Email fada@mofada.cn
 * @Description 定义语言
 **/
public class AxmlLanguage extends Language {
    public static final Language INSTANCE = new AxmlLanguage();

    private AxmlLanguage() {
        super("AXML");
    }
}
