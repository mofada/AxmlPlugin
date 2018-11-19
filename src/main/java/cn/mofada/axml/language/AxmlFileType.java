package cn.mofada.axml.language;

import cn.mofada.axml.icons.AxmlIcons;
import com.intellij.lang.html.HTMLLanguage;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @Date 2018/11/17-17:31
 * @User fada
 * @Email fada@mofada.cn
 * @Description 文件类型类
 **/
public class AxmlFileType extends LanguageFileType {
    public static final AxmlFileType INSTANCE = new AxmlFileType();
    public static final String EXTENSION = "axml";

    private AxmlFileType() {
        super(HTMLLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Axml";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Axml Of DingTalk files";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return EXTENSION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return AxmlIcons.AXML_ICON;
    }
}
