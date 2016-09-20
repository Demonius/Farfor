package by.lykashenko.farfor.Adapters;


import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import by.lykashenko.farfor.BD.Offers;
import by.lykashenko.farfor.R;

/**
 * Created by Дмитрий on 20.09.16.
 * Адаптер для RecyclerView для подкатегорий выбранной группы товаров.
 */


//адаптер для RecyclerView
public class AdapterOffer extends RecyclerView.Adapter<AdapterOffer.PersonViewHolder> {
    private List<Offers> m_list_offer;
    private Activity m_list_activity;

    public interface PressedOffers {
        void onPressedOffers(Integer indexOffers);
    }

    public AdapterOffer(List<Offers> offerAll, Activity activity) {
        m_list_offer = offerAll;
        m_list_activity = activity;
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView ves;
        public TextView cena;
        public CardView cv;
        public ImageView imageOffer;

        public PersonViewHolder(final View item_view) {
            super(item_view);
            name = (TextView) item_view.findViewById(R.id.textViewName);
            ves = (TextView) item_view.findViewById(R.id.textViewVes);
            cena = (TextView) item_view.findViewById(R.id.textViewCena);
            cv = (CardView) item_view.findViewById(R.id.cardViewOffer);
            imageOffer = (ImageView) item_view.findViewById(R.id.imageViewOffer);
            //подробное описание для offers
            item_view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Integer position_click = getAdapterPosition();
                    PressedOffers pressedOffers = (PressedOffers) m_list_activity;
                    pressedOffers.onPressedOffers(m_list_offer.get(position_click).idOffers);
//                    LoadDialogOffers(m_list_offer.get(position_click).idOffers);

                }
            });
        }
    }

    @Override
    public AdapterOffer.PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_holder, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personHolder, int position) {

        personHolder.name.setText(m_list_offer.get(position).name);
        personHolder.ves.setText(m_list_offer.get(position).ves);
        personHolder.cena.setText(m_list_offer.get(position).price);

        Picasso.with(m_list_activity).load(m_list_offer.get(position).picture)
                .resize((int) m_list_activity.getResources().getDimension(R.dimen.height_picture),
                        (int) m_list_activity.getResources().getDimension(R.dimen.weight_picture))
                .centerInside()
                .into(personHolder.imageOffer);


    }

    @Override
    public int getItemCount() {
        return m_list_offer.size();
    }

}

