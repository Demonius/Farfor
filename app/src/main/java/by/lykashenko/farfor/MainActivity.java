package by.lykashenko.farfor;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.lykashenko.farfor.Adapters.AdapterCatalog;
import by.lykashenko.farfor.Adapters.AdapterOffer;
import by.lykashenko.farfor.BD.Offers;
import by.lykashenko.farfor.BD.Categories;
import by.lykashenko.farfor.Fragments.DialogShowOffer;
import by.lykashenko.farfor.Fragments.FragmentCatalog;
import by.lykashenko.farfor.Fragments.FragmentContacts;
import by.lykashenko.farfor.Fragments.FragmentOffer;
import by.lykashenko.farfor.Interfaces.InterfaceLoadingXml;
import by.lykashenko.farfor.XmlConstructer.Category;
import by.lykashenko.farfor.XmlConstructer.Offer;
import by.lykashenko.farfor.XmlConstructer.Param;
import by.lykashenko.farfor.XmlConstructer.XmlConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterCatalog.PressedCategory, AdapterOffer.PressedOffers{

    private static final String LOG_TAG = "Farfor";
    public static final String TIME_UPDATE = "time_update";
    private String[] mItemDrawer;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private SharedPreferences sharedPreferences;
    public final static String PREFERENCES_NAME = "farfor";
    private Long timeLastUpdate;
    private static String url = "http://ufa.farfor.ru/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mTitle = mDrawerTitle = getSupportActionBar().getTitle();
        mItemDrawer = getResources().getStringArray(R.array.item_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        //добавления списка в адаптер
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mItemDrawer));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        com.activeandroid.Configuration dbConfiguration = new com.activeandroid.Configuration.Builder(this)
                .setDatabaseName("Farfor.db")
                .create();

        ActiveAndroid.initialize(dbConfiguration);

        ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        SharedPreferences sharedPreferences = this.getSharedPreferences(MainActivity.PREFERENCES_NAME, Context.MODE_PRIVATE);
        timeLastUpdate = sharedPreferences.getLong(MainActivity.TIME_UPDATE, 0);


        if (networkInfo != null && networkInfo.isConnected()) { // проверка на сеть

            Date date = new Date();
            Long time = date.getTime();//текущее время

            if (time - timeLastUpdate > 15000) {
                Log.d(LOG_TAG, "update data");
                //Удаление старых данных в таблицах
                if (timeLastUpdate!=0){
                new Delete().from(Categories.class).execute();
                new Delete().from(Offers.class).execute();}



                sharedPreferences = this.getSharedPreferences(MainActivity.PREFERENCES_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong(MainActivity.TIME_UPDATE, time);
                editor.apply();
                LoadDataFarfor();//загрузка данных из сети и сохранение в базе данных
            } else {
                Log.d(LOG_TAG, "reload data");

            }

        } else {
            Snackbar.make(mDrawerLayout, getResources().getString(R.string.no_network), Snackbar.LENGTH_INDEFINITE).show();

        }

    }

    @Override
    public void onPressedCategory(Integer index) {

        Fragment fragment = new FragmentOffer();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        fragment.setArguments(bundle);
        FragmentManager fragmnetManager = getSupportFragmentManager();
        fragmnetManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack("Offer")
                .commit();


    }

    @Override
    public void onPressedOffers(Integer indexOffers) {
        List<Offers> offersList = new Select().from(Offers.class).where("idOffers = ?", indexOffers).execute();

        DialogShowOffer dialogShowOffer = new DialogShowOffer();
        dialogShowOffer.setNameOffer(offersList.get(0).name);
        dialogShowOffer.setDescriptionOffer(offersList.get(0).description);
        dialogShowOffer.setUrlPicture(offersList.get(0).picture);
        dialogShowOffer.setCenaOffer(offersList.get(0).price);
        List<String> option = new ArrayList<>();
        option.add(offersList.get(0).diametr);
        option.add(offersList.get(0).ves);
        option.add(offersList.get(0).kolichestvo);
        option.add(offersList.get(0).energy);
        option.add(offersList.get(0).belki);
        option.add(offersList.get(0).giru);
        option.add(offersList.get(0).yglevodu);

        dialogShowOffer.setOptionOffer(option);
        dialogShowOffer.show(getSupportFragmentManager(),"offer");
    }


    // обработчик нажатий на боковой панели
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            itemSelected(position);

        }

    }

    //Определение нажатой позиции и вызов соответсвующего fragment
    private void itemSelected(int position) {
        Fragment fragment = null;
        String title = null;

        String[] str = getResources().getStringArray(R.array.item_drawer);

        switch (position) {
            case 0: {
//                Toast.makeText(this, "Каталог", Toast.LENGTH_SHORT).show();
                fragment = new FragmentCatalog();
                title =  str[0];

                break;
            }
            case 1: {
//                Toast.makeText(this, "Контакты", Toast.LENGTH_SHORT).show();
                fragment = new FragmentContacts();
                title = str[1];
                break;
            }

        }
        FragmentManager fragmnetManager = getSupportFragmentManager();
        fragmnetManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        mDrawerList.setItemChecked(position, true);
        setTitle(title);
        mDrawerLayout.closeDrawer(mDrawerList);


    }

    //установка заголовка
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    private void LoadDataFarfor() {

        //скачивание данных из интернета
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(SimpleXmlConverterFactory.create()).build();
        Log.d(LOG_TAG, "старт загрузки данных");
        InterfaceLoadingXml interfaceLoadingXml = retrofit.create(InterfaceLoadingXml.class);

        Call<XmlConstructor> call = interfaceLoadingXml.getXml();


        call.enqueue(new Callback<XmlConstructor>() {
            @Override
            public void onResponse(Call<XmlConstructor> call, Response<XmlConstructor> response) {

                List<Category> categories;
                List<Offer> offers;
//                ActiveAndroid.beginTransaction();



                if (response != null) {
                    Log.d(LOG_TAG, "есть данные");
                    try {

                        categories = response.body().getCategories();
                        Log.d(LOG_TAG, "размер массива categories = " + categories.size());
                        offers = response.body().getOffers();
                        Log.d(LOG_TAG, "размер массива offers = " + offers.size());
                        Log.d(LOG_TAG, "offers name"+offers.get(200).getName());
                        //сохраняем в базу данных

                        SaveCategories(categories);
                        SaveOffers(offers);




                    } catch (Exception e) {
                        Log.d(LOG_TAG, "Ошибка считывания данных: " + e.toString());
                    }


                }
                itemSelected(0);
            }

            @Override
            public void onFailure(Call<XmlConstructor> call, Throwable t) {
                Log.d(LOG_TAG, "Call onFailure " + t.toString());
            }
        });

    }

    private void SaveOffers(List<Offer> offers) {

        Integer categoryId;
        Integer idOffers;
        String name;
        String description;
        String picture;
        String price;


        ActiveAndroid.beginTransaction();
        try {
            for (int i = 0; i < offers.size(); i++) {
                categoryId = offers.get(i).getCategoryId();
                name = offers.get(i).getName();
                description = offers.get(i).getDescriptiion();
                picture = offers.get(i).getPicture();
                price ="Цена: "+offers.get(i).getPrice();
                idOffers = offers.get(i).getIdOffer();
                List<Param> param = offers.get(i).getParam();
                String diametr = "";
                String ves = "";
                String kolichestvo = "";
                String energy = "";
                String belki = "";
                String giru = "";
                String uglevodu = "";


                if (param!=null) {
//                    Log.d(LOG_TAG,"param = "+param.get(1).getNameParam()+" text = "+param.get(1).getTextParam());
                    for (int p = 0; p < param.size(); p++) {
                        if (param.get(p).getNameParam().equals("Диаметр")) {
                            diametr =param.get(p).getTextParam();
                        } else if (param.get(p).getNameParam().equals("Вес")) {
                            ves =param.get(p).getTextParam();
                        } else if (param.get(p).getNameParam().equals("Кол-во")) {
                            kolichestvo = param.get(p).getTextParam();
                        } else if (param.get(p).getNameParam().equals("Каллорийность")) {
                            energy = param.get(p).getTextParam();
                        } else if (param.get(p).getNameParam().equals("Белки")) {
                            belki = param.get(p).getTextParam();
                        } else if (param.get(p).getNameParam().equals("Жиры")) {
                            giru = param.get(p).getTextParam();
                        } else if (param.get(p).getNameParam().equals("Углеводы")) {
                            uglevodu = param.get(p).getTextParam();
                        }
                    }
                }else {
                    diametr = "";
                    ves = "";
                    kolichestvo = "";
                    energy = "";
                    belki = "" ;
                    giru = "";
                    uglevodu = "";
                }

                //сохранение offer
                Offers tableOffers = new Offers(idOffers, categoryId, name, description, picture, price, diametr, ves, kolichestvo, energy,
                        belki, giru, uglevodu);
                tableOffers.save();


            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    private void SaveCategories(List<Category> categories) {
        ActiveAndroid.beginTransaction();
        try {
            for (int i = 0; i < categories.size(); i++) {

                Integer index = categories.get(i).getId();
                String category = categories.get(i).getCategory();
                //сохранение Категорий
                Categories tableCategories = new Categories(index, category);
                tableCategories.save();

            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

}
