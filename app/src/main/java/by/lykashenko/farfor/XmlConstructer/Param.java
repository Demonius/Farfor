package by.lykashenko.farfor.XmlConstructer;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.util.Map;

/**
 * Created by Дмитрий on 13.09.16.
 */
@Root
public class Param {



    @Attribute(name = "name")
    private String nameParam;

    @Text
    private String textParam;

    public String getNameParam() {
        return nameParam;
    }

    public String getTextParam() {
        return textParam;
    }
}
