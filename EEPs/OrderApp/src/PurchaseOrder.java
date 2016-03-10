
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fz4432
 */
public class PurchaseOrder 
{
    
    String first_name;
    String last_name;
    String address;
    String phone_num;
    
    ArrayList<ProductItem> productItemList  = new ArrayList<ProductItem>();
    
    PurchaseOrder()
    {
        first_name  = "";
        last_name   = "";
        address     = "";
        phone_num   = "";
    }
    PurchaseOrder(String fn, String ln, String addr, String phnum, ArrayList<ProductItem> pList)
    {
    
        first_name  = fn;
        last_name   = ln;
        address     = addr;
        phone_num   = phnum;
        
        for(ProductItem p : pList)
        {
            productItemList.add(p);
        }
    }
    
    void clear()
    {
        first_name  = "";
        last_name   = "";
        address     = "";
        phone_num   = "";
        
        productItemList.clear();
    }
    
}
