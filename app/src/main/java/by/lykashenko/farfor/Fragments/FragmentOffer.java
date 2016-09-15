package by.lykashenko.farfor.Fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.lykashenko.farfor.BD.Offers;
import by.lykashenko.farfor.Interfaces.ImageDownloader;
import by.lykashenko.farfor.R;
import by.lykashenko.farfor.XmlConstructer.Offer;

/**
 * Created by Дмитрий on 11.09.16.
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
            mAdapter = new MyAdapter(offersList);
            mRecyclerView.setAdapter(mAdapter);}else {
            Snackbar.make(getView(), getResources().getString(R.string.no_data), Snackbar.LENGTH_INDEFINITE).show();
        }
    }
    //адаптер для RecyclerView
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.PersonViewHolder> {
        private List<Offers> m_list_offer;

        public MyAdapter(List<Offers> offerAll) {
            m_list_offer = offerAll;
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
                        LoadDialogOffers(m_list_offer.get(position_click).idOffers);

                    }
                });
            }
        }

        @Override
        public MyAdapter.PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_holder, parent, false);
            PersonViewHolder pvh = new PersonViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(PersonViewHolder personHolder, int position) {

            personHolder.name.setText(m_list_offer.get(position).name);
            personHolder.ves.setText(m_list_offer.get(position).ves);
            personHolder.cena.setText(m_list_offer.get(position).price);

            ImageDownloader imageDownloader = new ImageDownloader();
            imageDownloader.setMode(ImageDownloader.Mode.CORRECT);
            try {
                imageDownloader.download(m_list_offer.get(position).picture, personHolder.imageOffer);
            }catch (NullPointerException e){
                personHolder.imageOffer.setImageDrawable(getResources().getDrawable(R.drawable.ic_drawer));
            }

        }

        @Override
        public int getItemCount() {
            return m_list_offer.size();
        }


    }

    private void LoadDialogOffers(Integer idOffers) {

        List<Offers> offersList = new Select().from(Offers.class).where("idOffers = ?", idOffers).execute();

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

        dialogShowOffer.show(getFragmentManager(), "offer");
    }
}
