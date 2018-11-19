package cn.mofada.axml.tags;

import cn.mofada.axml.provider.AxmlAttributeDescriptor;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2018/11/18-14:51
 * @User fada
 * @Email fada@mofada.cn
 * @Description
 **/
public class TagsList {
    private static ArrayList<AxmlTag> axmlTags;
    private static Set<String> tagNames = new HashSet<>();
    private static ArrayList<AxmlTag.AttrsBean> commonAttrs = new ArrayList<>();

    /**
     * 获取所有标签
     */
    public static void loadTags() {
        InputStream inputStream = TagsList.class.getResourceAsStream("/json/axml-tag.json");
        Gson gson = new Gson();
        axmlTags = gson.fromJson(new InputStreamReader(inputStream), new TypeToken<ArrayList<AxmlTag>>() {
        }.getType());

        if (axmlTags == null) {
            axmlTags = new ArrayList<>();
        }

        for (AxmlTag axmlTag : axmlTags) {
            tagNames.add(axmlTag.getTag());
        }
    }

    /**
     * 获取标签
     *
     * @return
     */
    public static ArrayList<AxmlTag> getAxmlTags() {
        if (axmlTags.size() == 0) {
            loadTags();
        }
        return axmlTags;
    }

    /**
     * 获取标签名称
     *
     * @return
     */
    public static Set<String> getTagNames() {
        if (tagNames.size() == 0) {
            loadTags();
        }
        return tagNames;
    }

    /**
     * 根据标签名称获取标签
     *
     * @param tags
     * @return
     */
    public static AxmlTag getTagsByTag(String tags) {
        for (AxmlTag axmlTag : getAxmlTags()) {
            if (axmlTag.getTag().equals(tags)) {
                return axmlTag;
            }
        }
        return null;
    }

    /**
     * 根据属性名称获取属性
     *
     * @param name
     * @return
     */
    public static AxmlTag.AttrsBean getAttributeByName(String name) {
        for (AxmlTag.AttrsBean attrsBean : getCommonAttribute()) {
            if (attrsBean.getValueName().equals(name)) {
                return attrsBean;
            }
        }
        return null;
    }

    /**
     * 获取公共的属性
     */
    public static ArrayList<AxmlTag.AttrsBean> getCommonAttribute() {
        if (commonAttrs.size() == 0) {
            loadCommonAttribute();
        }

        return commonAttrs;
    }

    /**
     * 获取公共属性
     */
    private static void loadCommonAttribute() {
        InputStream inputStream = TagsList.class.getResourceAsStream("/json/common-attribute.json");
        Gson gson = new Gson();
        commonAttrs = gson.fromJson(new InputStreamReader(inputStream), new TypeToken<ArrayList<AxmlTag.AttrsBean>>() {
        }.getType());

        if (commonAttrs == null) {
            commonAttrs = new ArrayList<>();
        }
    }
}
