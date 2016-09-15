package by.lykashenko.farfor.XmlConstructer;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;
import java.util.Map;

/**
 * Created by Дмитрий on 09.09.16.
 */
@Root(name = "yml_catalog",strict = false)
public class XmlConstructor {
    public  XmlConstructor(){}

    @Path("shop")
    @ElementList(name = "categories", inline = false, required = false)
    private List<Category> categories;

    @Path("shop")
    @ElementList(name = "offers", inline = false, required = false)
    private List<Offer> offers;


    public List<Category> getCategories(){
        return categories;
    }
    public List<Offer> getOffers() {return offers;}
}
