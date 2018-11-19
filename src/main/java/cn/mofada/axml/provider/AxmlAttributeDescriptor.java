package cn.mofada.axml.provider;

import cn.mofada.axml.tags.AxmlTag;
import com.intellij.lang.Language;
import com.intellij.psi.PsiElement;
import com.intellij.xml.impl.BasicXmlAttributeDescriptor;
import com.intellij.xml.impl.XmlAttributeDescriptorEx;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Date 2018/11/18-16:34
 * @User fada
 * @Email fada@mofada.cn
 * @Description 属性类
 **/
public class AxmlAttributeDescriptor extends BasicXmlAttributeDescriptor implements XmlAttributeDescriptorEx {
    private AxmlTag.AttrsBean attrsBean;

    public AxmlAttributeDescriptor(AxmlTag.AttrsBean attrsBean) {
        this.attrsBean = attrsBean;
    }

    @Nullable
    @Override
    public String handleTargetRename(@NotNull String newTargetName) {
        return newTargetName;
    }

    @Override
    public boolean isRequired() {
        return attrsBean.isRequired();
    }

    @Override
    public boolean hasIdType() {
        return false;
    }

    @Override
    public boolean hasIdRefType() {
        return false;
    }

    @Override
    public boolean isEnumerated() {
//        return attrsBean.isIsEnum() || attrsBean.getValueType().equals("Boolean");
        return false;
    }

    @Override
    public PsiElement getDeclaration() {
        return null;
    }

    @Override
    public String getName() {
        return attrsBean.getValueName();
    }

    @Override
    public void init(PsiElement element) {
    }

    @NotNull
    @Override
    public Object[] getDependences() {
        return new Object[0];
    }

    @Override
    public boolean isFixed() {
        return false;
    }

    @Override
    public String getDefaultValue() {
        return attrsBean.getDefaultValue();
    }

    @Override
    public String[] getEnumeratedValues() {
        if (attrsBean.getValueType().equals("Boolean")) {
            return new String[]{"true", "false"};
        }
        return attrsBean.getEnumValue().toArray(new String[attrsBean.getEnumValue().size()]);
    }
}
