package by.lykashenko.farfor.Adapters;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import by.lykashenko.farfor.BD.Categories;
import by.lykashenko.farfor.Fragments.FragmentCatalog;
import by.lykashenko.farfor.R;

/**
 * Created by Дмитрий on 20.09.16.
 * Адаптер для RecyclerView групп товаров
 */
//адаптер для RecyclerView
public class AdapterCatalog extends RecyclerView.Adapter<AdapterCatalog.PersonViewHolder> {
    private List<Categories> m_list_categories;
    private Activity m_list_activity;

    public interface PressedCategory {
        void onPressedCategory(Integer index);
    }

    public AdapterCatalog(List<Categories> categoriesAll, FragmentActivity activity) {
        m_list_categories = categoriesAll;
        m_list_activity=activity;
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

                    PressedCategory pressedCategory = (PressedCategory) m_list_activity;
                    pressedCategory.onPressedCategory(id);


                }
            });
        }
    }

    @Override
    public AdapterCatalog.PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
