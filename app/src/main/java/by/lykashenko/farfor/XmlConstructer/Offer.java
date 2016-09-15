package by.lykashenko.farfor.XmlConstructer;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.List;
import java.util.Map;

/**
 * Created by Дмитрий on 11.09.16.
 */
@Root
public class Offer {

    @Attribute(name = "id")
    private Integer idOffer;

    @Element(name = "url")
    private String urlOffer;

    @Element(name = "name")
    private String name;

    @Element(name = "price")
    private String price;

    @Element(name = "description", required = false)
    private String descriptiion;

    @Element(name = "picture", required = false)
    private String picture;

    @Element(name = "categoryId")
    private Integer categoryId;

    @ElementList(entry = "param", inline = true, required = false)
    private List<Param> param;

    public List<Param> getParam(){ return param;}

    public Integer getIdOffer(){
        return idOffer;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
    public String getDescriptiion(){
        return descriptiion;
    }
    public String getPicture() {
        return picture;
    }
    public Integer getCategoryId(){
        return categoryId;
    }


//    public Map<String,String> getParam(){        return param;    }
}
