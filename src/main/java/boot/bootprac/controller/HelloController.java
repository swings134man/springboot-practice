package boot.bootprac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    /**
    * @info    : static 타입 메서드
    * @name    : hello
    * @date    : 2022/08/04 5:34 PM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   :
    * @return  :
    */
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello!!!"); // model의 이름 data
        return "hello";
    }


    /**
    * @info    : mvc 타입 메서드
    * @name    : hellomvc
    * @date    : 2022/08/04 5:33 PM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   :
    * @return  :
    */
    @GetMapping("hello-mvc")
    public String hellomvc(@RequestParam(value = "name", required = true) String name, Model model) {

        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {

        return "hello " + name; // hello Spring
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);

        return hello;

    }

   /************
   * @info : hello-api를 사용하기 위한 Class (DTO 역할과 동일)
   * @name : HelloController
   * @date : 2022/08/04 5:49 PM
   * @author : SeokJun Kang(swings134@gmail.com)
   * @version : 1.0.0
   ************/
    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }// class Hello

} // class
