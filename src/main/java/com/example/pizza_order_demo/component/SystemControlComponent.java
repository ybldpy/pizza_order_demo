package com.example.pizza_order_demo.component;

import org.springframework.stereotype.Component;

@Component
public class SystemControlComponent {

    private int systemState = 1;

    public boolean closed(){
        return systemState==0;
    }

    public void closeSystem(){
        systemState = 0;
    }

    public void openSystem(){
        systemState = 1;
    }
    public int getSystemState(){
        return systemState;
    }
}
