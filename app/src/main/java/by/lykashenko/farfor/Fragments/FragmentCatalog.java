package by.lykashenko.farfor.Fragments;

import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;

import java.util.List;

import by.lykashenko.farfor.Adapters.AdapterCatalog;
import by.lykashenko.farfor.BD.Categories;
import by.lykashenko.farfor.R;


/**
 * Created by Дмитрий on 08.09.16.
 * Фрагмент для отображения групп товаров.
 */


public class FragmentCatalog extends android.support.v4.app.Fragment {
    private static final String LOG_TAG = "Catalog";
    private View view;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.catalog, container, false);

        LoadCategoryList();//загрузка списка категорий

        return view;
    }

    //загрузка в список
    private void LoadCategoryList() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_list_category);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecyclerView.setItemAnimator(itemAnimator);
        List<Categories> listCategories = new Select().from(Categories.class).execute();
        Log.d(LOG_TAG,"кол-во полей = "+Integer.toString(listCategories.size()));
        if (listCategories != null) {
            mAdapter = new AdapterCatalog(listCategories, getActivity());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            Snackbar.make(getView(), getResources().getString(R.string.no_data), Snackbar.LENGTH_INDEFINITE).show();
        }


    }




}
