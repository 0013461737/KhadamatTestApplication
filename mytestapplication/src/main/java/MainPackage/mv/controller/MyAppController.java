package MainPackage.mv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyAppController {

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "{ \"message\":\"is ok\"";
    }
}
