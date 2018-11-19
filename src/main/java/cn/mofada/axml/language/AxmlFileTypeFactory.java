package cn.mofada.axml.language;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * @Date 2018/11/17-17:35
 * @User fada
 * @Email fada@mofada.cn
 * @Description 文件类型转换工厂
 **/
public class AxmlFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        consumer.consume(AxmlFileType.INSTANCE);
    }
}
