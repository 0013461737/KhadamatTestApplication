package MainPackage.mv.controller;

import MainPackage.dbmanager.assets.ChannelName;
import MainPackage.dbmanager.model.Order;
import MainPackage.dbmanager.repository.OrderRepository;
import MainPackage.dbmanager.repository.UserRepository;
import MainPackage.exception.IleagalAmountException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class MyAppController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/test")
    @ResponseBody
    public String getTest(){
        orderRepository.findAll();
        return "{ \"messageId\":\"0\"}";
    }

    @PostMapping(value = "/Order" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postTest(@RequestBody Order o){
        String messageId = "{ \"messageId\":\"1000\"}";;
        boolean flag = false;
        int WEB_APPLICATION = ChannelName.valueOf("WEB_APPLICATION").ordinal();
        int MOBILE_APPLICATION = ChannelName.valueOf("MOBILE_APPLICATION").ordinal();
        Order order;
        if (o.getChannelId() == WEB_APPLICATION){
            messageId = "{ \"messageId\":\"1001\"}";
            flag = true;
        }
        else if (o.getChannelId() == MOBILE_APPLICATION){
            try{
                if (o.getAmount() > 1000){
                    messageId = "{ \"messageId\":\"1002\"}";
                    flag = false;
                    throw new IleagalAmountException();
                }
                else {
                    messageId = "{ \"messageId\":\"1003\"}";
                    flag = true;
                }
            }catch (IleagalAmountException e){
                messageId = "{ \"messageId\":\"1004\"}";
                flag = false;
            }

        }

        if (flag) {
            order = new Order();
            order.setName(o.getName());
            order.setFamily(o.getFamily());
            order.setPhone(o.getPhone());
            order.setAddress(o.getAddress());
            order.setAmount(o.getAmount());
            order.setQuantity(o.getQuantity());
            orderRepository.save(order);
        }

        return messageId;
    }
}
