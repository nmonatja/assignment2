
import java.util.ArrayList;
import java.util.Calendar;
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
    
    OpResult  opResult = new OpResult();
    
    public OrderAppLogic()
    {
        
        
    }
    public OpResult GetLastOpResult()
    {
        return opResult;
    }
            
    public ArrayList<ProductItem> LoadProductList(int product_type)
    {
        opResult.resultStatus   = false;
        opResult.msgStr         = "";
        opResult.errStr         = "";
        
        ArrayList<ProductItem> productItemList  = new ArrayList<ProductItem>();
        
        switch(product_type)
        {
            case 0:
            {
                ArrayList<SeedItem> item_list = null;
                try 
                {
                    item_list = seed_inv.getAll();
                } catch (SelectException ex) 
                {
                    //Logger.getLogger(OrderAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                    opResult.resultStatus = false;
                    opResult.errStr = ex.getMessage();
                    break;
                }
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
                ArrayList<TreeItem> item_list = null;
                try 
                {
                    item_list = tree_inv.getAll();
                } catch (SelectException ex) 
                {
                    //Logger.getLogger(OrderAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                    opResult.resultStatus = false;
                    opResult.errStr = ex.getMessage();
                }
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
                ArrayList<ShrubItem> item_list = null;
                try 
                {
                    item_list = shrub_inv.getAll();
                } 
                catch (SelectException ex) 
                {
                    //Logger.getLogger(OrderAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                    opResult.resultStatus = false;
                    opResult.errStr = ex.getMessage();
                }
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
                ArrayList<ProcessingItem> item_list = null;
                try 
                {
                    item_list = processing_inv.getAll();
                } 
                catch (SelectException ex) 
                {
                    //Logger.getLogger(OrderAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                    opResult.resultStatus = false;
                    opResult.errStr = ex.getMessage();
                }
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
                ArrayList<CultureboxItem> item_list = null;
                try 
                {
                    item_list = culture_box_inv.getAll();
                } 
                catch (SelectException ex) 
                {
                    //Logger.getLogger(OrderAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                    opResult.resultStatus = false;
                    opResult.errStr = ex.getMessage();
                }
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
                ArrayList<GenomicItem> item_list = null;
                try 
                {
                    item_list = genomic_inv.getAll();
                } 
                catch (SelectException ex) 
                {
                    //Logger.getLogger(OrderAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                    opResult.resultStatus = false;
                    opResult.errStr = ex.getMessage();
                }
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
                ArrayList<ReferenceMaterialItem> item_list = null;
                try 
                {
                    item_list = ref_material_inv.getAll();
                } 
                catch (SelectException ex) 
                {
                    //Logger.getLogger(OrderAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                    opResult.resultStatus = false;
                    opResult.errStr = ex.getMessage();
                }
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
        opResult.resultStatus   = false;
        opResult.msgStr         = "";
        opResult.errStr         = "";
        
        if(!mySession.isLoggedOn())
        {
            opResult.errStr = "User is not logged on...\n";
            return opResult;
        }
        
        /*Check shipping authority*/
        String auth_level = mySession.getAuthorizationLevel();
        
        if(Objects.equals(auth_level, new String("Read Only"))) 
        {
            opResult.errStr = "\nUser does not have authority to perform action...\n";
            return opResult;
        }
        
        if( Objects.equals(po.first_name, new String("")) ||
            Objects.equals(po.last_name, new String("")) ||
            Objects.equals(po.address, new String(""))||
            Objects.equals(po.phone_num, new String("")))
        {
            opResult.errStr = "\nCustomer Information not filled out...\n";
            return opResult;
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
        
        int retVal = 0;
        try 
        {
            retVal = ordInfo.add(order);
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
                opResult.resultStatus = false;
                opResult.errStr = "Unable to place order...\n";
            }
            else
            {
                opResult.resultStatus = true;
                opResult.msgStr = "Successfully placed order...\n";
            }
        } 
        catch (InsertException ex) 
        {
            //Logger.getLogger(OrderAppLogic.class.getName()).log(Level.SEVERE, null, ex);
            opResult.resultStatus = false;
            opResult.errStr = ex.getMessage();
        }
        
        return opResult;
    }
}
