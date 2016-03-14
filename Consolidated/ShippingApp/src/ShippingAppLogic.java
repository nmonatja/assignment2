import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    OpResult  opResult                      = new OpResult();
    
    public ShippingAppLogic()
    {
        
        
    }   
    
    public OpResult GetLastOpResult()
    {
        return opResult;
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
    
    public OpResult RetrieveOrders(Session mySession)
    {
        OpResult result = new OpResult();
        
        
        if(!mySession.isLoggedOn())
        {
            result.errStr = "User is not logged on...\n";
            return result;
        }
            
        /*No Authorization required for this action*/
        
           
        try 
        {
            order_list = Order_info.getAll();
        } 
        catch (SelectException ex) 
        {
            result.resultStatus = false;
            result.errStr = ex.getMessage();
        }
        if(null!=order_list)
        {
            result.msgStr = "Retrieved orders...\n";
            result.resultStatus = true;
        }
        
        return result;
    }
    
    public Order SelectOrder(int order_id)
    {
        Order order = null;
        
        /*Clear order item list*/
        if(order_item_list !=null)
        {
            order_item_list.clear();
        }
        
        try 
        {
            order = Order_info.getById(order_id);
        } 
        catch (SelectException ex) 
        {
            opResult.resultStatus = false;
            opResult.errStr = ex.getMessage();
        }
        if(null!=order)
        {
            opResult.msgStr = "Sucessfully selected orders..."+ order_id + "\n";
            opResult.resultStatus = true;
        }
        
        return order;
    }
    
    public OpResult RetrieveOrderItems(Order order)
    {
        OpResult result = new OpResult();


        if(order !=null)
        {
            try 
            {
                order_item_list = order.getItems();
            } 
            catch (SelectException ex) 
            {
                result.resultStatus = false;
                result.errStr = ex.getMessage();
            }
            if(order_item_list !=null)
            {
                result.msgStr = "Successfully retrieved order items for order " + order.order_id+ "\n";
                result.resultStatus = true;
            }
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
    
    public OpResult ShipOrder(int order_id, Session mySession)
    {
        OpResult result = new OpResult();
        
        if(!mySession.isLoggedOn())
        {
            result.errStr = "User is not logged on...\n";
            return result;
        }
        
        /*Check shipping authority*/
        String auth_level = mySession.getAuthorizationLevel();
        
        if(Objects.equals(auth_level, new String("Read Only"))) 
        {
            result.errStr = "\nUser does not have authority to perform action...\n";
            return result;
        }

        Order order = null;
        
        try 
        {
            order = Order_info.getById(order_id);
        } 
        catch (SelectException ex) 
        {
            result.resultStatus = false;
            result.errStr = ex.getMessage();
        }
        if(order !=null)
        {
            try 
            {
                if(order.update(order.order_date, order.first_name, order.last_name, order.address, order.phone, order.total_cost, 1, order.order_table))
                {
                    result.msgStr = "\nSuccessfully set status to shipped...\n";
                    result.resultStatus = true;
                }
            } 
            catch (UpdateException ex) 
            {
                result.resultStatus = false;
                result.errStr = ex.getMessage();
            }
        }
        return result;
    }
    
    public Float GetTotalSelectedOrderPrice()
    {
        Float total_order_price = new Float(0);
        
        for(OrderItem item : order_item_list)
        {
            total_order_price = total_order_price + item.item_price;
        }
        return total_order_price;
    }
}
