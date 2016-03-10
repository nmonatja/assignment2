
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fz4432
 */
public class OrderAppLogic 
{
    
    SeedInventory seed_inv = new SeedInventory();
    
    TreeInventory tree_inv = new TreeInventory();
    
    ShrubInventory shrub_inv = new ShrubInventory();
    
    ProcessingInventory processing_inv = new ProcessingInventory();
    
    CultureboxInventory culture_box_inv = new CultureboxInventory();
    
    GenomicInventory genomic_inv = new GenomicInventory();
    
    ReferenceMaterialInventory ref_material_inv = new ReferenceMaterialInventory();
    
    public OrderAppLogic()
    {
        
        
    }
    
    public ArrayList<ProductItem> LoadProductList(int product_type)
    {
        ArrayList<ProductItem> productItemList  = new ArrayList<ProductItem>();
        
        switch(product_type)
        {
            case 0:
            {
                ArrayList<SeedItem> item_list = seed_inv.getAll();
                if(item_list != null)
                {
                    for(SeedItem s : item_list)
                    {
                        if (s.deleted != 1)
                        {
                            ProductItem productItem = new ProductItem(new String("Seeds"),s.product_code,s.description,s.quantity,s.price);
                            productItemList.add(productItem);
                        }
                    }
                }
                break;
            }
            case 1:
            {
                ArrayList<TreeItem> item_list = tree_inv.getAll();
                if(item_list != null)
                {
                    for(TreeItem s : item_list)
                    {
                        if (s.deleted != 1)
                        {
                            ProductItem productItem = new ProductItem(new String("Trees"),s.product_code,s.description,s.quantity,s.price);
                            productItemList.add(productItem);
                        }
                    }
                }
                break;
            }
            case 2:
            {
                ArrayList<ShrubItem> item_list = shrub_inv.getAll();
                if(item_list != null)
                {
                    for(ShrubItem s : item_list)
                    {
                        if (s.deleted != 1)
                        {
                            ProductItem productItem = new ProductItem(new String("Shrubs"),s.product_code,s.description,s.quantity,s.price);
                            productItemList.add(productItem);
                        }
                    }
                }
                break;
            }
            case 3:
            {
                ArrayList<ProcessingItem> item_list = processing_inv.getAll();
                if(item_list != null)
                {
                    for(ProcessingItem s : item_list)
                    {
                        if (s.deleted != 1)
                        {
                            ProductItem productItem = new ProductItem(new String("Processing Item"),s.product_code,s.description,s.quantity,s.price);
                            productItemList.add(productItem);
                        }
                    }
                }
                break;
            }
            case 4:
            {
                ArrayList<CultureboxItem> item_list = culture_box_inv.getAll();
                if(item_list != null)
                {
                    for(CultureboxItem s : item_list)
                    {
                        if (s.deleted != 1)
                        {
                            ProductItem productItem = new ProductItem(new String("Culture Box"),s.product_code,s.description,s.quantity,s.price);
                            productItemList.add(productItem);
                        }
                    }
                }
                break;
            }
            case 5:
            {
                ArrayList<GenomicItem> item_list = genomic_inv.getAll();
                if(item_list != null)
                {
                    for(GenomicItem s : item_list)
                    {
                        if (s.deleted != 1)
                        {
                            ProductItem productItem = new ProductItem(new String("Genomics"),s.product_code,s.description,s.quantity,s.price);
                            productItemList.add(productItem);
                        }
                    }
                }
                break;
            }
            case 6:
            {
                ArrayList<ReferenceMaterialItem> item_list = ref_material_inv.getAll();
                if(item_list != null)
                {
                    for(ReferenceMaterialItem s : item_list)
                    {
                        if (s.deleted != 1)
                        {
                            ProductItem productItem = new ProductItem(new String("Reference Material"),s.product_code,s.description,s.quantity,s.price);
                            productItemList.add(productItem);
                        }
                    }
                }
                break;
            }
            
        }
        return productItemList;
        
    }
    
    ArrayList<String> GetProductTypes()
    {
        ArrayList<String> product_type_list = new ArrayList<String>();
        
        product_type_list.add(0, "Seeds");
        product_type_list.add(1, "Trees");
        product_type_list.add(2, "Shrubs");
        product_type_list.add(3, "Processing");
        product_type_list.add(4, "Culture Box");
        product_type_list.add(5, "Genomics");
        product_type_list.add(6, "Reference Material");
        
        return product_type_list;
    }
    
    OpResult PlacePurchaseOrder(PurchaseOrder po, Session mySession)
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
        
        if( Objects.equals(po.first_name, new String("")) ||
            Objects.equals(po.last_name, new String("")) ||
            Objects.equals(po.address, new String(""))||
            Objects.equals(po.phone_num, new String("")))
        {
            result.errStr = "\nCustomer Information not filled out...\n";
            return result;
        }
        
        Calendar rightNow = Calendar.getInstance();

        int TheHour = rightNow.get(rightNow.HOUR_OF_DAY);
        int TheMinute = rightNow.get(rightNow.MINUTE);
        int TheSecond = rightNow.get(rightNow.SECOND);
        int TheDay = rightNow.get(rightNow.DAY_OF_WEEK);
        int TheMonth = rightNow.get(rightNow.MONTH);
        int TheYear = rightNow.get(rightNow.YEAR);
        String orderTableName = "order" + String.valueOf(rightNow.getTimeInMillis());

        String order_date = TheMonth + "/" + TheDay + "/" + TheYear + " "
                + TheHour + ":" + TheMinute  + ":" + TheSecond;
        
        Float total_cost = new Float(0);
        
        for (ProductItem p : po.productItemList)
        {
            total_cost = total_cost + (p.price * new Float(p.quantity));
        }
        
        Order order = new Order(0,  order_date, po.first_name, po.last_name, po.address, po.phone_num, total_cost, 0, orderTableName);
        
        OrderInfo ordInfo = new OrderInfo();
        
        int retVal = ordInfo.add(order);
        
        for (ProductItem p : po.productItemList)
        {
            OrderItem item = new OrderItem(null, p.product_code, p.description, p.price, orderTableName);
            retVal = order.addItem(item);
            
            if(retVal == 0)
            {
                break;
            }
        }
        
        
        if(retVal == 0)
        {
            result.resultStatus = false;
            result.errStr = "Unable to place order...\n";
        }
        else
        {
            result.resultStatus = true;
            result.msgStr = "Successfully placed order...\n";
        }
        
        return result;
    }
    
}
