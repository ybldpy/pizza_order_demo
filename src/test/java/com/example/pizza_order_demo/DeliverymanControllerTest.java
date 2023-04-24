package com.example.pizza_order_demo;


import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.controller.DeliverymanController;
import com.example.pizza_order_demo.model.Deliveryman;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PizzaOrderDemoApplication.class)
public class DeliverymanControllerTest {


    @Autowired
    DeliverymanController deliverymanController;
    @Test
    public void testAdd() {
        Deliveryman deliveryman = null;
        Result result = deliverymanController.addDeliveryman(deliveryman);
        System.out.println(result);
        deliveryman = new Deliveryman();
        deliveryman.setName("jjj");
        result = deliverymanController.addDeliveryman(deliveryman);
        System.out.println(result);
        deliveryman = new Deliveryman();
        deliveryman.setName("jjj");
        deliveryman.setPhone("11111111111");
        result = deliverymanController.addDeliveryman(deliveryman);
        System.out.println(result);

    }
}
