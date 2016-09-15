package by.lykashenko.farfor.Fragments;

import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.List;

import by.lykashenko.farfor.BD.Categories;
import by.lykashenko.farfor.R;


/**
 * Created by Дмитрий on 08.09.16.
 */


public class FragmentCatalog extends android.support.v4.app.Fragment {
    private static final String LOG_TAG = "Catalog";
    private View view;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public interface PressedCategory {
        void onPressedCategory(Integer index);
    }

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
            mAdapter = new MyAdapter(listCategories);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            Snackbar.make(getView(), getResources().getString(R.string.no_data), Snackbar.LENGTH_INDEFINITE).show();
        }


    }

    //адаптер для RecyclerView
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.PersonViewHolder> {
        private List<Categories> m_list_categories;

        public MyAdapter(List<Categories> categoriesAll) {
            m_list_categories = categoriesAll;
        }

        public class PersonViewHolder extends RecyclerView.ViewHolder {
            public TextView title;
            public CardView cv;
            public ImageView imagePreviev;

            public PersonViewHolder(final View item_view) {
                super(item_view);
                title = (TextView) item_view.findViewById(R.id.textCategory);
                cv = (CardView) item_view.findViewById(R.id.cardViewCategory);
                imagePreviev = (ImageView) item_view.findViewById(R.id.imageCategory);
                //обработка нажатий на категории
                item_view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Integer position_click = getAdapterPosition();
                        Integer id = m_list_categories.get(position_click).index;

                        PressedCategory pressedCategory = (PressedCategory) getActivity();
                        pressedCategory.onPressedCategory(id);


                    }
                });
            }
        }

        @Override
        public MyAdapter.PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_holder, parent, false);
            PersonViewHolder pvh = new PersonViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(PersonViewHolder personHolder, int position) {
            personHolder.title.setText(m_list_categories.get(position).category.toString());

        }

        @Override
        public int getItemCount() {
            return m_list_categories.size();
        }


    }


}
