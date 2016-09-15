package by.lykashenko.farfor.Interfaces;

/**
 * Created by Дмитрий on 10.09.16.
 * interface для парсинга xml
 */


import by.lykashenko.farfor.XmlConstructer.XmlConstructor;
import retrofit2.Call;
import retrofit2.http.GET;

public interface InterfaceLoadingXml  {


    @GET("getyml/?key=ukAXxeJYZN")
    Call<XmlConstructor> getXml();

}
