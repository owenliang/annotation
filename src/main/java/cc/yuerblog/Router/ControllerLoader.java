package cc.yuerblog.Router;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

// 为了加载.class文件中的类
public class ControllerLoader extends ClassLoader {
  private String basePath;

  public ControllerLoader(String basePath) {
    this.basePath = basePath;
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    // 找到.class文件
    String path = basePath + name.replaceAll("\\.", "/");

    // 读取.class文件
    byte[] classBytes;
    InputStream ins = null;
    try {
      ins = new FileInputStream(path);
      ByteArrayOutputStream bout = new ByteArrayOutputStream();

      byte[] buffer = new byte[4096];
      int bytesNumRead = 0;
      while ((bytesNumRead = ins.read(buffer)) != -1) {
        bout.write(buffer, 0, bytesNumRead);
      }
      classBytes = bout.toByteArray();
    } catch (Exception e) {
      throw new ClassNotFoundException();
    } finally {
      try {
        if (ins != null) {
          ins.close();
        }
      } catch (Exception e) {
        throw new ClassNotFoundException();
      }
    }

    // 生成class类
    return defineClass(name, classBytes, 0, classBytes.length);
  }
}
