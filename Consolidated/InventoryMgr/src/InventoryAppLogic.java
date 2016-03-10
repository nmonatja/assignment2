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
public class InventoryAppLogic 
{
    
   
    ArrayList<Inventory> inventory_list             = null;
    ArrayList<InventoryItem> inventory_item_list    = null;
    ArrayList<TreeItem> tree_item_list    = null;
    ArrayList<ShrubItem> shrub_item_list    = null;
    ArrayList<SeedItem> seed_item_list    = null;
    ArrayList<CultureboxItem> culturebox_item_list    = null;
    ArrayList<GenomicItem> genomic_item_list    = null;
    ArrayList<ProcessingItem> processing_item_list    = null;
    ArrayList<ReferenceMaterialItem> ref_material_item_list    = null;
    
    TreeInventory treeInventory = new TreeInventory();
    ShrubInventory shrubInventory = new ShrubInventory();
    SeedInventory seedInventory = new SeedInventory();
    CultureboxInventory cultureboxInventory = new CultureboxInventory();
    GenomicInventory genomicInventory = new GenomicInventory();
    ReferenceMaterialInventory referenceMaterialInventory = new ReferenceMaterialInventory();
    ProcessingInventory processingInventory = new ProcessingInventory();
    
    public InventoryAppLogic()
    {
        
        
    }   
    
    public void addInventoryItem (String invItem, String productID, 
            String description, int quantity, float perUnitCost)
    {
        if (invItem.equals("Trees"))
        {                  
            treeInventory.add(productID, description, quantity, perUnitCost);
        }
        // if shrubs are selected then insert inventory into strubs
        // table
        else if (invItem.equals("Shrubs"))
        {
            shrubInventory.add(productID, description, quantity, perUnitCost);
        }
        else if (invItem.equals("Seeds"))
        {
            seedInventory.add(productID, description, quantity, perUnitCost);
        }             
        else if (invItem.equals("Culture Boxes"))
        {
            cultureboxInventory.add(productID, description, quantity, perUnitCost);
        }

        else if (invItem.equals("Genomics"))
        {
            genomicInventory.add(productID, description, quantity, perUnitCost);
        }

        else if (invItem.equals("Processing"))
        {
            processingInventory.add(productID, description, quantity, perUnitCost);
        }

        else if (invItem.equals("Ref Materials"))
        {
            referenceMaterialInventory.add(productID, description, quantity, perUnitCost);
        }
    }
    
 //   public <T extends InventoryItem> ArrayList<T> getAll();

//public ArrayList getInventoryItems (String invItem)
    
      public <T extends InventoryItem> ArrayList<T>  getInventoryItems (String invItem)
    {
        if (invItem.equals("Trees"))
        {
            tree_item_list = treeInventory.getAll();
            return (ArrayList<T>) tree_item_list;
        }
        else if (invItem.equals("Shrubs"))
        {
            shrub_item_list = shrubInventory.getAll();
            return (ArrayList<T>) shrub_item_list;
        }
        else if (invItem.equals("Seeds"))
        {
            seed_item_list = seedInventory.getAll();
            return (ArrayList<T>) seed_item_list;
        }             
        else if (invItem.equals("Culture Boxes"))
        {
            culturebox_item_list = cultureboxInventory.getAll();
            return (ArrayList<T>) culturebox_item_list;
        }

        else if (invItem.equals("Genomics"))
        {
            genomic_item_list = genomicInventory.getAll();
            return (ArrayList<T>) genomic_item_list;
        }

        else if (invItem.equals("Processing"))
        {
            processing_item_list = processingInventory.getAll();
            return (ArrayList<T>) processing_item_list;
        }

        else if (invItem.equals("Ref Materials"))
        {
            ref_material_item_list = referenceMaterialInventory.getAll();
            return (ArrayList<T>) ref_material_item_list;
        }
        
      return null;
        
    }
    
     public void deleteInventoryItem (String invItem, String productID)
    {
        
        if (invItem.equals("Trees"))
        {       
            TreeItem byId = treeInventory.getById(productID); 
            byId.remove();
        }
        // if shrubs are selected then insert inventory into strubs
        // table
        else if (invItem.equals("Shrubs"))
        {
            TreeItem byId = treeInventory.getById(productID); 
            byId.remove();
        }
        else if (invItem.equals("Seeds"))
        {
            ShrubItem byId = shrubInventory.getById(productID); 
            byId.remove();
        }             
        else if (invItem.equals("Culture Boxes"))
        {
            CultureboxItem byId = cultureboxInventory.getById(productID); 
            byId.remove();
        }

        else if (invItem.equals("Genomics"))
        {
            GenomicItem byId = genomicInventory.getById(productID); 
            byId.remove();
        }

        else if (invItem.equals("Processing"))
        {
            ProcessingItem byId = processingInventory.getById(productID); 
            byId.remove();
        }

        else if (invItem.equals("Ref Materials"))
        {
            ReferenceMaterialItem byId = referenceMaterialInventory.getById(productID); 
            byId.remove();
        }
    }
}