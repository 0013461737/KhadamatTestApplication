package MainPackage.mv.controller;

import MainPackage.dbmanager.assets.ChannelName;
import MainPackage.dbmanager.model.Order;
import MainPackage.dbmanager.repository.OrderRepository;
import MainPackage.dbmanager.repository.UserRepository;
import MainPackage.exception.IleagalAmountException;
import MainPackage.mv.service.OrderService;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
public class MyAppController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private OrderService orderService;

    @GetMapping("/test")
    @ResponseBody
    public String getTest() {
        orderRepository.findAll();
        return "{ \"messageId\":\"0\"}";
    }

    @GetMapping("/OrderListAll")
    @ResponseBody
    public String getAllOrder() {
        Iterable<Order> it = orderRepository.findAll();
        JSONObject jsonAll = new JSONObject();
        Map<String, Order> mapAll = new LinkedHashMap<>();
        final int[] i = {0};
        mapAll.clear();
        it.forEach(order -> {
            mapAll.put("ORDER" + i[0], order);
            i[0]++;
        });

        jsonAll.put("ORDER_LIST", mapAll);


        String js = jsonAll.toString();
        int first = 15;
        String js2, js3, js4, js5, js6, js7, js8, js9, js10;
        final String ord = "{\"ORDER_LIST\":[{";
        js2 = "["+js+"]";
        js3 = js2.replace("[{\"ORDER_LIST\":{\"","[{\"ORDER_LIST\":[{\"");
        js4 = js3.replace("}}}]","}}]}]");
        js5 = js4.replace("},\"","}},{\"");
//        js6 = js5.replace("}},{","}]},{");
//        js7 = js6+"]";
//        js2 = js.replaceFirst("}}}", "}}]}]");
//        js3 = js2.substring(first);
//        js4 = "[" + ord + js3;
//        js5 = js4.replace("\":{\"", "\":[{\"");
//        js6 = js5.replace("}}]}]", "}]}]}]");
//        js7 = js6.replaceAll("}]}]}]", "**********");
//        js8 = js7.replaceAll("}],", "}]},{");
//        js9 = js8.replace("**********", "}]}]}]");

        return js5;
    }
    @GetMapping("/OrderListFirst")
    @ResponseBody
    public String getFirstOrder() {
        JSONObject jsonFirst = new JSONObject();
        Map<String, Order> mapFirst = new LinkedHashMap<>();
        Iterable<Order> it = orderRepository.findAll();


        SortedSet<Long> sortedSet = new TreeSet<>();
        it.forEach(val ->{
            sortedSet.add(Long.valueOf(val.getSortingVal()));
        });
        long last;
        try{
            last = sortedSet.last();
        }catch (Exception e){
            last = 0;
        }
        Optional<Order> order = orderRepository.findBySortingVal(String.valueOf(last));
        if (order.isPresent()){
            mapFirst.put("ORDER0",order.get());
        }
        else {
            return "[{\"ORDER_LIST\":{}}]";
        }


        jsonFirst.put("ORDER_LIST",mapFirst);


        String js = jsonFirst.toString();
        int first = 15;
        String js2, js3, js4, js5, js6, js7, js8, js9, js10;
        final String ord = "{\"ORDER_LIST\":[{";
        js2 = "["+js+"]";
        js3 = js2.replace("[{\"ORDER_LIST\":{\"","[{\"ORDER_LIST\":[{\"");
        js4 = js3.replace("}}}]","}}]}]");
        js5 = js4.replace("},\"","}},{\"");
//        js6 = js5.replace("}},{","}]},{");
//        js7 = js6+"]";
//        js2 = js.replaceFirst("}}}", "}}]}]");
//        js3 = js2.substring(first);
//        js4 = "[" + ord + js3;
//        js5 = js4.replace("\":{\"", "\":[{\"");
//        js6 = js5.replace("}}]}]", "}]}]}]");
//        js7 = js6.replaceAll("}]}]}]", "**********");
//        js8 = js7.replaceAll("}],", "}]},{");
//        js9 = js8.replace("**********", "}]}]}]");

        return js5;
    }

    @PostMapping(value = "/Order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postTest(@RequestBody Order o) {
        String messageId = "\"messageId\":\"1000\"";
        ;
        boolean flag = false;
        int WEB_APPLICATION = ChannelName.valueOf("WEB_APPLICATION").ordinal();
        int MOBILE_APPLICATION = ChannelName.valueOf("MOBILE_APPLICATION").ordinal();
        Order order;
        if (o.getChannelId() == WEB_APPLICATION) {
            messageId = "\"messageId\":\"1001\"";
            flag = true;
        } else if (o.getChannelId() == MOBILE_APPLICATION) {
            try {
                if (o.getAmount() > 1000) {
                    throw new IleagalAmountException();
                } else {
                    messageId = "\"messageId\":\"1003\"";
                    flag = true;
                }
            } catch (IleagalAmountException e) {
                messageId = "\"messageId\":\"1004\"";
                flag = false;
            }

        }
        String trackCode = "";
        if (flag) {
            trackCode = orderService.manipulateOnDb(o);
        }

        return "{" + messageId.concat(" \n , \n \"trakingValue\":\"" + trackCode + "\"") + "}";
    }

}
