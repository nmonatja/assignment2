/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fz4432
 */
public class ProductItem 
{
    String  product_type;
    String  product_code; 
    String  description;
    int     quantity;
    float   price;    

   
    ProductItem()
    {
        product_type    = "";
        product_code    = "";
        description     = "";
        quantity        = 0;
        price           = new Float(0);
    }
    ProductItem(String  ptype,String  pcode,String  desc,int quant,float pr)
    {
        product_type    = ptype;
        product_code    = pcode;
        description     = desc;
        quantity        = quant;
        price           = pr;
    }
    
}
