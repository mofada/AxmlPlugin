package cn.mofada.axml.provider;

import cn.mofada.axml.tags.AxmlTag;
import cn.mofada.axml.tags.TagsList;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.XmlElementDescriptor;
import com.intellij.xml.XmlElementsGroup;
import com.intellij.xml.XmlNSDescriptor;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2018/11/18-15:17
 * @User fada
 * @Email fada@mofada.cn
 * @Description
 **/
public class AxmlTagDescriptor implements XmlElementDescriptor {
    protected final String name;
    private final PsiFile file;

    public AxmlTagDescriptor(String name, PsiFile file) {
        this.name = name;
        this.file = file;
    }

    @Override
    public String getQualifiedName() {
        return name;
    }

    @Override
    public String getDefaultName() {
        return name;
    }

    @Override
    public XmlElementDescriptor[] getElementsDescriptors(XmlTag context) {
        return EMPTY_ARRAY;
    }

    @Override
    public XmlElementDescriptor getElementDescriptor(XmlTag childTag, XmlTag contextTag) {
        return null;
    }

    @Override
    public XmlAttributeDescriptor[] getAttributesDescriptors(@Nullable XmlTag tag) {
        if (tag == null) return new XmlAttributeDescriptor[0];
        List<AxmlAttributeDescriptor> result = new ArrayList<>();
        //自带的属性
        AxmlTag tagsByTag = TagsList.getTagsByTag(tag.getName());
        if (tagsByTag != null) {
            for (AxmlTag.AttrsBean attr : tagsByTag.getAttrs()) {
                result.add(new AxmlAttributeDescriptor(attr));
            }
        }
        //公共的属性
        for (AxmlTag.AttrsBean attr : TagsList.getCommonAttribute()) {
            result.add(new AxmlAttributeDescriptor(attr));
        }

        return result.toArray(new AxmlAttributeDescriptor[result.size()]);
    }

    @Nullable
    @Override
    public XmlAttributeDescriptor getAttributeDescriptor(XmlAttribute attribute) {
        return getAttributeDescriptor(attribute.getName(), attribute.getParent());
    }

    @Nullable
    @Override
    public XmlAttributeDescriptor getAttributeDescriptor(@NonNls final String attributeName, @Nullable XmlTag context) {
        //        if (descriptor != null) return descriptor;
        return ContainerUtil.find(getAttributesDescriptors(context), descriptor1 -> attributeName.equals(descriptor1.getName()));
    }

    @Override
    public XmlNSDescriptor getNSDescriptor() {
        return null;
    }

    @Nullable
    @Override
    public XmlElementsGroup getTopGroup() {
        return null;
    }

    @Override
    public int getContentType() {
        return CONTENT_TYPE_ANY;
    }

    @Nullable
    @Override
    public String getDefaultValue() {
        return null;
    }

    @Override
    public PsiFile getDeclaration() {
        return file;
    }

    @Override
    public String getName(PsiElement context) {
        return getName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void init(PsiElement element) {
        element.add(element);
    }

    @Override
    public Object[] getDependences() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }
}
