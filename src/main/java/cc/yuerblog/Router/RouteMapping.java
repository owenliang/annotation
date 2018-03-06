package cc.yuerblog.Router;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 运行时可以反射类方法得到注解信息
  */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RouteMapping {
  String uri(); // 注解需要声明路由的uri
}
