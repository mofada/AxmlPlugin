package cn.mofada.axml.annotator;

import cn.mofada.axml.tags.AxmlTag;
import cn.mofada.axml.tags.TagsList;
import cn.mofada.axml.utils.AxmlFileUtils;
import com.intellij.lang.annotation.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.html.HtmlTag;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * @Date 2018/11/18-19:22
 * @User fada
 * @Email fada@mofada.cn
 * @Description 语法检查类
 **/
public class AxmlAnnotator implements Annotator {
    private static final String JOIN_SPLIT = ",";

    private static final String MUSTACHE_REGEX = "^\\{\\{(.+)}}$";
    private static final String NUMBER_REGEX = "[0-9]+";
    private static final String FLOAT_REGEX = "[0-9]*.?[0-9]*";
    private static final String COLOR_REGEX = "^#([0-9]|[A-F]|[a-f]){3}|([0-9]|[A-F]|[a-f]){6}$|^rgb\\(([0-9]{1,2}|1[0-9][0-9]|2[0-4][0-9]|25[0-5]),([0-9]{1,2}|1[0-9][0-9]|2[0-4][0-9]|25[0-5]),([0-9]{1,2}|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\)$|^rgba\\(([0-9]{1,2}|1[0-9][0-9]|2[0-4][0-9]|25[0-5]),([0-9]{1,2}|1[0-9][0-9]|2[0-4][0-9]|25[0-5]),([0-9]{1,2}|1[0-9][0-9]|2[0-4][0-9]|25[0-5]),([0].[0-9]{1,2}|1)\\)$";

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (!AxmlFileUtils.isAxmlFile(element)) {
            return;
        }

        checkTags(element, holder);
    }

    /**
     * 语法检查
     *
     * @param element
     * @param holder
     */
    private void checkTags(PsiElement element, AnnotationHolder holder) {
        if (element instanceof HtmlTag) {
            //检查名字在不在控件列表
            String name = ((XmlTag) element).getName();

            // 验证当前
            AxmlTag tagsByTag = TagsList.getTagsByTag(name);
            if (tagsByTag == null) {
                holder.createWarningAnnotation(element, ((XmlTag) element).getName() + " does not exist");
                return;
            }

            //验证父元素
            if (tagsByTag.getParent().size() != 0) {
                checkTagParent(element, holder, tagsByTag);
            }

            //验证子元素
            if (tagsByTag.getChild().size() != 0) {
                checkTagChild((XmlTag) element, holder, tagsByTag);
            }

            // 验证属性值
            XmlAttribute[] attributes = ((XmlTag) element).getAttributes();
            for (XmlAttribute attribute : attributes) {
                checkAttribute(attribute, holder, tagsByTag.getAttrs());
            }
        }
    }

    /**
     * 验证子元素
     *
     * @param element
     * @param holder
     * @param tagsByTag
     */
    private void checkTagChild(XmlTag element, AnnotationHolder holder, AxmlTag tagsByTag) {
        XmlTag[] subTags = element.getSubTags();
        for (XmlTag subTag : subTags) {
            String name = subTag.getName();
            //如果子元素包含了name
            if (tagsByTag.getChild().contains(name)) {
                continue;
            } else {
                createTagAnnotation(element, holder, name, " must has child in " + arrayToString(tagsByTag.getChild()));
            }
        }
    }

    /**
     * 检查属性值
     *
     * @param attribute
     * @param holder
     * @param attrs
     */
    private void checkAttribute(XmlAttribute attribute, AnnotationHolder holder, List<AxmlTag.AttrsBean> attrs) {
        String attributeName = attribute.getName();
        String attributeValue = attribute.getValue();

        AxmlTag.AttrsBean attrsBean = getAttributeByName(attributeName, attrs);
        if (attrsBean == null)
            attrsBean = TagsList.getAttributeByName(attributeName);

        if (attrsBean != null) {
            String valueType = attrsBean.getValueType();

            // 创建消息提示
            holder.createInfoAnnotation(attribute.getNameElement(), attrsBean.getDescription());

            if (attributeValue == null) {
                return;
            }

            switch (valueType) {
                case "Boolean":
//                    if (!Objects.equals(attributeValue, "true") && !Objects.equals(attributeValue, "false") && !attributeValue.matches(MUSTACHE_REGEX)) {
                    if (!"true".equals(attributeValue) && !"false".equals(attributeValue) && !attributeValue.matches(MUSTACHE_REGEX)) {
                        createAnnotation(attribute, holder, attributeName, " can only be true or false or an expression");
                    }
                    break;
                case "Float":
                    if (!attributeValue.matches(FLOAT_REGEX) && !attributeValue.matches(MUSTACHE_REGEX)) {
                        createAnnotation(attribute, holder, attributeName, " can only be float or int");
                    }
                    break;
                case "Number":
                    if (!attributeValue.matches(NUMBER_REGEX) && !attributeValue.matches(MUSTACHE_REGEX)) {
                        createAnnotation(attribute, holder, attributeName, " can only be number");
                    }
                    break;
                case "Color":
                    if (!attributeValue.matches(COLOR_REGEX) && !attributeValue.matches(MUSTACHE_REGEX)) {
                        createAnnotation(attribute, holder, attributeName, " can only be a color value");
                    }
                    break;
                default:
            }

            //验证枚举
            if (attrsBean.isEnum()) {
                if (!attrsBean.getEnumValue().contains(attributeValue)) {
                    createAnnotation(attribute, holder, attributeName, " can only be in " + arrayToString(attrsBean.getEnumValue()));
                }
            }
        } else {
            holder.createErrorAnnotation(attribute.getNameElement(), attributeName + " attribute does not exist");
        }
    }

    /**
     * 获取属性
     *
     * @param attributeName
     * @param attrs
     * @return
     */
    private AxmlTag.AttrsBean getAttributeByName(String attributeName, List<AxmlTag.AttrsBean> attrs) {
        for (AxmlTag.AttrsBean attrsBean : attrs) {
            if (attrsBean.getValueName().equals(attributeName)) {
                return attrsBean;
            }
        }
        return null;
    }

    /**
     * 数组转字符串
     *
     * @param enumValue
     * @return
     */
    private String arrayToString(List<String> enumValue) {
        StringBuilder builder = new StringBuilder();
        for (String s : enumValue) {
            builder.append(s);
            builder.append(JOIN_SPLIT);
        }
        return builder.length() > 0 ? builder.substring(0, builder.length() - 1) : "";
    }

    /**
     * 创建一条属性提示
     *
     * @param attribute
     * @param holder
     * @param attributeName
     */
    private void createAnnotation(XmlAttribute attribute, AnnotationHolder holder, String attributeName, String message) {
        holder.createErrorAnnotation(attribute.getValueElement(), attributeName + message);
    }

    /**
     * 检查swiper-item
     *
     * @param element
     * @param holder
     * @param tagsByTag
     */
    private void checkTagParent(PsiElement element, AnnotationHolder holder, AxmlTag tagsByTag) {
        String name = ((XmlTag) element).getName();
        PsiElement parent = element.getParent();
        if (parent instanceof XmlTag) {
            if (!tagsByTag.getParent().contains(((XmlTag) parent).getName())) {
                createTagAnnotation(element, holder, name, " parent must in " + arrayToString(tagsByTag.getParent()));
            }
        } else {
            createTagAnnotation(element, holder, name, " parent must in " + arrayToString(tagsByTag.getParent()));
        }
    }

    /**
     * 创建一个标签提示
     *
     * @param element
     * @param holder
     * @param name
     * @param message
     */
    private void createTagAnnotation(PsiElement element, AnnotationHolder holder, String name, String message) {
        holder.createWarningAnnotation(element, name + message);
    }
}
