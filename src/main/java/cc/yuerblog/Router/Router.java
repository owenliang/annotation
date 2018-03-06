package cc.yuerblog.Router;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Router {
  private class Action {
    public Action(Object object, Method method) {
      this.object = object;
      this.method = method;
    }

    public void call() {
      try {
        method.invoke(object);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }

    private Object object;
    private Method method;
  }

  private ControllerLoader controllerLoader;

  private Map<String, Object> controllerBeans = new HashMap<String, Object>();

  private Map<String, Action> uri2Action = new HashMap<String, Action>();

  public Router(String basePath) {
    controllerLoader = new ControllerLoader(basePath);
  }

  public void addRouter(String controllerClass) {
    try {
      // 加载class
      Class<?> cls = controllerLoader.loadClass(controllerClass);

      // 反射class中所有方法
      Method[] methods = cls.getDeclaredMethods();
      for (Method method : methods) {
        // 反射方法所有注解
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
          // 如果注解类型是RouteMapping, 解析其URI
          if (annotation.annotationType() == RouteMapping.class) {
            RouteMapping anno = (RouteMapping)annotation;
            // 路由uri
            String uri = anno.uri();
            // 保存Bean单例
            if (!controllerBeans.containsKey(cls.getName())) {
              controllerBeans.put(cls.getName(), cls.newInstance());
            }
            // 保存uri -> (obj,method)
            uri2Action.put(uri, new Action(controllerBeans.get(cls.getName()), method));
          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void testRoute(String uri) {
    Action action = uri2Action.get(uri);
    if (action != null) {
      action.call();
    } else {
      System.out.println(uri + " is not found");
    }
  }
}
