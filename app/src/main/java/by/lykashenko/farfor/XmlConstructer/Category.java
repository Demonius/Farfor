package by.lykashenko.farfor.XmlConstructer;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * Created by Дмитрий on 09.09.16.
 */
@Root
public class Category {

    @Attribute
    private Integer id;

    @Text(required = false)
    private String category;

    public String getCategory(){
        return category;
    }

    public Integer getId(){
        return id;
    }
}
