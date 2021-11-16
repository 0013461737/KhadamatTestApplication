package MainPackage.mv.service;

import MainPackage.dbmanager.model.Order;
import MainPackage.dbmanager.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

@Component
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;



    public String manipulateOnDb(Order o){
        Order order = new Order();
        order.setName(o.getName());
        order.setFamily(o.getFamily());
        order.setPhone(o.getPhone());
        order.setAddress(o.getAddress());
        order.setAmount(o.getAmount());
        order.setQuantity(o.getQuantity());

        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        order.setTrackingCode(generatedString);


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
        order.setSortingVal(String.valueOf(last+1));

        orderRepository.save(order);

        return generatedString;
    }
}
