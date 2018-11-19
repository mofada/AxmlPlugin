package cn.mofada.axml.provider;

import cn.mofada.axml.icons.AxmlIcons;
import cn.mofada.axml.tags.AxmlTag;
import cn.mofada.axml.tags.TagsList;
import cn.mofada.axml.utils.AxmlFileUtils;
import com.intellij.codeInsight.completion.XmlTagInsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.impl.source.xml.XmlElementDescriptorProvider;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlElementDescriptor;
import com.intellij.xml.XmlTagNameProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2018/11/17-18:34
 * @User fada
 * @Email fada@mofada.cn
 * @Description
 **/
public class AxmlTagNameProvider implements XmlTagNameProvider, XmlElementDescriptorProvider {
    @Nullable
    @Override
    public XmlElementDescriptor getDescriptor(XmlTag tag) {
        if (!AxmlFileUtils.isAxmlFile(tag)) {
//            return new AxmlTagDescriptor();
            return null;
        }

        if (TagsList.getTagNames().contains(tag.getName())) {
//            return new AxmlTagDescriptor(tag.getName(), tag.getContainingFile());
            return new AxmlTagDescriptor(tag.getName(), tag.getContainingFile());
        }
        return null;
    }

    @Override
    public void addTagNameVariants(List<LookupElement> elements, @NotNull XmlTag tag, String prefix) {
        if (!AxmlFileUtils.isAxmlFile(tag)) {
            return;
        }

        //清除所有的html标签, 使用自己的标签
        elements.clear();
        ArrayList<AxmlTag> axmlTags = TagsList.getAxmlTags();
        //列出所有的标签
        for (AxmlTag axmlTag : axmlTags) {
            LookupElement element = LookupElementBuilder
                    .create(axmlTag.getTag())
                    .withInsertHandler(XmlTagInsertHandler.INSTANCE)
                    .withBoldness(true)
                    .withIcon(AxmlIcons.AXML_ICON)
                    .withTypeText(axmlTag.getDescription());
            elements.add(element);
        }
    }
}
