package cn.mofada.axml.utils;

import cn.mofada.axml.language.AxmlFileType;
import com.intellij.psi.PsiElement;

/**
 * @Date 2018/11/19-0:34
 * @User fada
 * @Email fada@mofada.cn
 * @Description
 **/
public class AxmlFileUtils {
    /**
     * 验证是否是axml文件
     *
     * @param element
     * @return
     */
    public static boolean isAxmlFile(PsiElement element) {
        if (element == null) {
            return false;
        }

        if (element.getContainingFile() == null) {
            return false;
        }

        String name = element.getContainingFile().getOriginalFile().getName();
        return name.endsWith(AxmlFileType.EXTENSION);
    }
}
