import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author FZ4432
 */
public class ShippingAppLogic 
{
    
    OrderInfo Order_info                    = new OrderInfo();
    ArrayList<Order> order_list             = null;
    ArrayList<OrderItem> order_item_list    = null;
    
    public ShippingAppLogic()
    {
        
        
    }   
    public int  GetOrderListSize()
    {
        if(order_list != null)
        {
            return order_list.size();
        }
        else
        {
            return 0;
        }
    }
    
    public  Order GetOrder(int index)
    {
        Order order = null;
        
        if(order_list !=null)
        {
            order = order_list.get(index);
        }
        return order;
    }
    
    public OpResult RetrieveOrders()
    {
        OpResult result = new OpResult();
        
        try
        {
            
            order_list = Order_info.getAll();
        }
        catch(Exception e)
        {
            result.errStr = "Unable to connect to datasource to retrive orders...";
            result.resultStatus = false;
        }
        
        if(null!=order_list)
        {
            result.msgStr = "Retrieved orders...";
            result.resultStatus = true;
        }
        
        return result;
    }
    
    public Order SelectOrder(int order_id)
    {
        Order order = null;
        try
        {
            order = Order_info.getById(order_id);
        }
        catch(Exception e)
        {
            
        }
        return order;
    }
    
    public OpResult RetrieveOrderItems(Order order)
    {
        OpResult result = new OpResult();

        try
        {
            if(order !=null)
            {
                order_item_list = order.getItems();
                result.msgStr = "Retrieved orders...";
                result.resultStatus = true;
            }
        }
        catch(Exception e)
        {
            result.errStr = "Unable to connect to datasource to retrive orders...";
            result.resultStatus = false;
        }
        
        return result;
    }
    
    public  OrderItem GetOrderItem(int index)
    {
        OrderItem orderitem = null;
        
        if(order_item_list !=null)
        {
            orderitem = order_item_list.get(index);
        }
        return orderitem;
    }
    public int GetOrderItemListSize()
    {
        if(order_item_list != null)
        {
            return order_item_list.size();
        }
        else
        {
            return 0;
        }
    }
    
    public OpResult ShipOrder(int order_id)
    {
        OpResult result = new OpResult();
        
        Order order = null;
        try
        {
            order = Order_info.getById(order_id);
            if(order !=null)
            {
                order.update(order.order_date, order.first_name, order.last_name, order.address, order.phone, order.total_cost, 1, order.order_table);
                result.msgStr = "Successfully set status to shipped...";
                result.resultStatus = true;
            }
        }
        catch(Exception e)
        {
            result.errStr = "Unable to perform datasource update to ship orders...";
            result.resultStatus = false;
        }
        
        return result;
    }
    
}
