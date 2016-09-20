package by.lykashenko.farfor.Fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;

import java.util.List;

import by.lykashenko.farfor.Adapters.AdapterOffer;
import by.lykashenko.farfor.BD.Offers;
import by.lykashenko.farfor.R;

/**
 * Created by Дмитрий on 11.09.16.
 * Фрагмент для отображения подкатегорий выбранной группы товаров
 */
public class FragmentOffer extends Fragment {
    private static final String LOG_TAG = "Offer";
    private View view;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static final String diametr ="Диаметр";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.catalog, container, false);

        Integer index = getArguments().getInt("index");
        LoadDataOffer(index);


        return view;
    }

    private void LoadDataOffer(Integer index) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_list_category);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecyclerView.setItemAnimator(itemAnimator);
        List<Offers> offersList = new Select().from(Offers.class).where("categoryId = ?", index).execute();
        Log.d(LOG_TAG,"кол-во полей = "+Integer.toString(offersList.size()));
//        List<Offers> listOffer= new Select().from(Offers.class).where("categoryId", index).execute();
        if (offersList!=null){
            mAdapter = new AdapterOffer(offersList, getActivity());
            mRecyclerView.setAdapter(mAdapter);}else {
            Snackbar.make(getView(), getResources().getString(R.string.no_data), Snackbar.LENGTH_INDEFINITE).show();
        }
    }

}
