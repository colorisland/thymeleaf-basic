package hello.thymeleaf_basic.basic;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("/text-basic")
    public String textBasic(Model model) {
        // 모델 설정.
        model.addAttribute("data", "Hello Spring");
        return "basic/text-basic";
    }

    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model) {
        // 모델 설정.
        model.addAttribute("data", "Hello <b>Spring</b>");
        return "basic/text-unescaped";
    }

    @GetMapping("/variable")
    public String variable(Model model) {
        // 유저 생성.
        User userA = new User("userA", 10);
        User userB = new User("userB", 20);

        // 유저 리스트 만들기.
        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);

        // 유저 해시맵 만들기.
        Map<String, User> map = new HashMap<>();
        map.put("userA", userA);
        map.put("userB", userB);

        model.addAttribute("user", userA);
        model.addAttribute("users", list);
        model.addAttribute("userMap", map);

        return "basic/variable";
    }

    // 스프링부트에서 제공하는 http 기본 객체를 타임리프에서 사용할 수 있다.
    // 기존 model 객체:${객체명} -> 기본 객체사용: ${#객체명}
    @GetMapping("/basic-objects")
    public String basicObjects(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        // 스프링부트 3.0에서는 타임리프에서 기본객체 제공을 하지 않아서 직접 넣어줘야 한다.
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext", request.getServletContext());

        // 세션 데이터 넣어주기.
        session.setAttribute("sessionData", "hello session");
        return "basic/basic-objects";
    }

    // 스프링 빈 만들기
    @Component("helloBean")
    static class HelloBean {
        public String hello(String data) {
            return "hello " + data;
        }
    }

    @Data
    static class User {
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }
}
