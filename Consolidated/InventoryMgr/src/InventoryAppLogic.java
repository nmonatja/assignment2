import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * This is business logic layer class
 * It contains methods to access data tier
 * The business logic consists of the functionality
 * of the legacy inventory application: 
 * 1) Add Item
 * 2) Delete Item
 * 3) List Inventory
 * 4) Decrement
 */
public class InventoryAppLogic 
{
    //define variables to hold data tier data, this are presented to the presentation layer
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
    
    //default constructor
    public InventoryAppLogic()
    {
        
        
    }   
    
    //adds an inventory item 
    public void addInventoryItem (String invItem, String productID, 
            String description, int quantity, float perUnitCost)
    {
        switch (invItem) {
        // check what inventory item the user selected and add it to the database through
            //data tier
            case "Trees":
                try {
                    treeInventory.add(productID, description, quantity, perUnitCost);
                } catch (InsertException ex) {
                    Logger.getLogger(InventoryAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                }   break;
            case "Shrubs":
                try {
                    shrubInventory.add(productID, description, quantity, perUnitCost);
                } catch (InsertException ex) {
                    Logger.getLogger(InventoryAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                }   break;
            case "Seeds":
                try {
                    seedInventory.add(productID, description, quantity, perUnitCost);
                } catch (InsertException ex) {
                    Logger.getLogger(InventoryAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                }   break;
            case "Culture Boxes":
                try {
                    cultureboxInventory.add(productID, description, quantity, perUnitCost);
                } catch (InsertException ex) {
                    Logger.getLogger(InventoryAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                }   break;
            case "Genomics":
                try {
                    genomicInventory.add(productID, description, quantity, perUnitCost);
                } catch (InsertException ex) {
                    Logger.getLogger(InventoryAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                }   break;
            case "Processing":
                try {
                    processingInventory.add(productID, description, quantity, perUnitCost);
                } catch (InsertException ex) {
                    Logger.getLogger(InventoryAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                }   break;
            case "Ref Materials":
                try {
                    referenceMaterialInventory.add(productID, description, quantity, perUnitCost);
                } catch (InsertException ex) {
                    Logger.getLogger(InventoryAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                }   break;
            default:
                break;
        }
    }
    
    //returns inventory item - used for displaying items to the user
      public <T extends InventoryItem> ArrayList<T>  getInventoryItems (String invItem)
    {
        switch (invItem) {
            case "Trees":
                try {
                    tree_item_list = treeInventory.getAll();
                } catch (SelectException ex) {
                    Logger.getLogger(InventoryAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                }
                return (ArrayList<T>) tree_item_list;
            case "Shrubs":
                try {
                    shrub_item_list = shrubInventory.getAll();
                } catch (SelectException ex) {
                    Logger.getLogger(InventoryAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                }
                return (ArrayList<T>) shrub_item_list;
            case "Seeds":
                try {
                    seed_item_list = seedInventory.getAll();
                } catch (SelectException ex) {
                    Logger.getLogger(InventoryAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                }
                return (ArrayList<T>) seed_item_list;
            case "Culture Boxes":
                try {
                    culturebox_item_list = cultureboxInventory.getAll();
                } catch (SelectException ex) {
                    Logger.getLogger(InventoryAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                }
                return (ArrayList<T>) culturebox_item_list;
            case "Genomics":
                try {
                    genomic_item_list = genomicInventory.getAll();
                } catch (SelectException ex) {
                    Logger.getLogger(InventoryAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                }
                return (ArrayList<T>) genomic_item_list;
            case "Processing":
                try {
                    processing_item_list = processingInventory.getAll();
                } catch (SelectException ex) {
                    Logger.getLogger(InventoryAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                }
                return (ArrayList<T>) processing_item_list;
            case "Ref Materials":
                try {
                    ref_material_item_list = referenceMaterialInventory.getAll();
                } catch (SelectException ex) {
                    Logger.getLogger(InventoryAppLogic.class.getName()).log(Level.SEVERE, null, ex);
                }
                return (ArrayList<T>) ref_material_item_list;
            default:
                break;
        }
        
      return null;
        
    }
    
      //delete invntory item that user selected
     public void deleteInventoryItem (String invItem, String productID) throws SelectException, DeleteException
    {
        
        switch (invItem) {
            case "Trees":
                {
                    TreeItem byId = treeInventory.getById(productID);
                    byId.remove();
                    break;
                }
                // if shrubs are selected then insert inventory into strubs
                // table
            case "Shrubs":
                {
                    ShrubItem byId = shrubInventory.getById(productID);
                    byId.remove();
                    break;
                }
            case "Seeds":
                {
                    SeedItem byId = seedInventory.getById(productID);
                    byId.remove();
                    break;
                }
            case "Culture Boxes":
                {
                    CultureboxItem byId = cultureboxInventory.getById(productID);
                    byId.remove();
                    break;
                }
            case "Genomics":
                {
                    GenomicItem byId = genomicInventory.getById(productID);
                    byId.remove();
                    break;
                }
            case "Processing":
                {
                    ProcessingItem byId = processingInventory.getById(productID);
                    byId.remove();
                    break;
                }
            case "Ref Materials":
                {
                    ReferenceMaterialItem byId = referenceMaterialInventory.getById(productID);
                    byId.remove();
                    break;
                }
            default:
                break;
        }
    }
     
     //decrements item's quantity by one
     public void decrementInventoryItem (String invItem, String productID) throws SelectException, UpdateException
    {
        
        switch (invItem) {
            case "Trees":
                {
                    TreeItem byId = treeInventory.getById(productID);
                    byId.update(null, null, byId.quantity - 1, null);
                    break;
                }
            case "Shrubs":
                {
                    ShrubItem byId = shrubInventory.getById(productID);
                    byId.update(null, null, byId.quantity - 1, null);
                    break;
                }
            case "Seeds":
                {
                    SeedItem byId = seedInventory.getById(productID);
                    byId.update(null, null, byId.quantity - 1, null);
                    break;
                }
            case "Culture Boxes":
                {
                    CultureboxItem byId = cultureboxInventory.getById(productID);
                    byId.update(null, null, byId.quantity - 1, null);
                    break;
                }
            case "Genomics":
                {
                    GenomicItem byId = genomicInventory.getById(productID);
                    byId.update(null, null, byId.quantity - 1, null);
                    break;
                }
            case "Processing":
                {
                    ProcessingItem byId = processingInventory.getById(productID);
                    byId.update(null, null, byId.quantity - 1, null);
                    break;
                }
            case "Ref Materials":
                {
                    ReferenceMaterialItem byId = referenceMaterialInventory.getById(productID);
                    byId.update(null, null, byId.quantity - 1, null);
                    break;
                }
            default:
                break;
        }
    }
     
}