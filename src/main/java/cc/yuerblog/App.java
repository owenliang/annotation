package cc.yuerblog;

import cc.yuerblog.Router.Router;

public class App
{
    public static void main( String[] args ) {
      // 注册路由
      Router router = new Router("target/classes/");
      router.addRouter("cc.yuerblog.Controller.IndexController");

      // 测试路由
      router.testRoute("/login");
      router.testRoute("/logout");
    }
}
