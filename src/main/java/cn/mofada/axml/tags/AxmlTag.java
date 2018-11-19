package cn.mofada.axml.tags;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2018/11/18-14:52
 * @User fada
 * @Email fada@mofada.cn
 * @Description
 **/
public class AxmlTag {

    private String tag;
    private String description;
    private List<AttrsBean> attrs;
    private List<String> child;
    private List<String> parent;

    public List<String> getChild() {
        return child == null ? new ArrayList<>() : child;
    }

    public void setChild(List<String> child) {
        this.child = child;
    }

    public List<String> getParent() {
        return parent == null ? new ArrayList<>() : parent;
    }

    public void setParent(List<String> parent) {
        this.parent = parent;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AttrsBean> getAttrs() {
        return attrs == null ? new ArrayList<>() : attrs;
    }

    public void setAttrs(List<AttrsBean> attrs) {
        this.attrs = attrs;
    }

    public static class AttrsBean {
        /**
         * valueName : disable-scroll
         * valueType : Boolean
         * defaultValue : false
         * description : 是否阻止区域内滚动页面
         * isMust : false
         */

        private String valueName;
        private String valueType;
        private String defaultValue;
        private String description;
        private boolean isRequired;
        /**
         * isEnum : true
         * enumValue : ["scaleToFill","aspectFit","aspectFill","widthFix","top","bottom","center","left","right","top left","top right","bottom left","bottom right"]
         */

        private boolean isEnum;
        private List<String> enumValue;

        public String getValueName() {
            return valueName;
        }

        public void setValueName(String valueName) {
            this.valueName = valueName;
        }

        public String getValueType() {
            return valueType;
        }

        public void setValueType(String valueType) {
            this.valueType = valueType;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setRequired(boolean required) {
            isRequired = required;
        }

        public void setEnum(boolean anEnum) {
            isEnum = anEnum;
        }

        public boolean isRequired() {
            return isRequired;
        }

        public boolean isEnum() {
            return isEnum;
        }

        public List<String> getEnumValue() {
            return enumValue == null ? new ArrayList<>() : enumValue;
        }

        public void setEnumValue(List<String> enumValue) {
            this.enumValue = enumValue;
        }
    }
}
