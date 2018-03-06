package cc.yuerblog.Controller;

import cc.yuerblog.Router.RouteMapping;

public class IndexController {
  @RouteMapping(uri = "/login")
  public void actionLogin() {
    System.out.println("/login called");
  }

  @RouteMapping(uri = "/logout")
  public void actionLogout() {
    System.out.println("/logout called");
  }

  private void someFunc() {

  }
}
