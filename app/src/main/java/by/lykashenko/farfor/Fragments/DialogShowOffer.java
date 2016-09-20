package by.lykashenko.farfor.Fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import by.lykashenko.farfor.R;

/**
 * Created by Дмитрий on 13.09.16.
 * Диалог для отображения дополнительных сведений для товара
 */
public class DialogShowOffer extends DialogFragment {

    private TextView nameOffer;
    private TextView descriptionOffer;
    private TextView cenaOffer;
    private TextView optionText;

    private List<String> optionOffer;
    private String urlPicture;
    private String name;
    private String description;
    private String cena;


    private ImageView imageOffer;

    private Button buttonClose;




    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View viewDialog = inflater.inflate(R.layout.dialog_offer_read, null);
        setRetainInstance(true);

        nameOffer = (TextView) viewDialog.findViewById(R.id.textName);
        descriptionOffer = (TextView) viewDialog.findViewById(R.id.textDescription);
        cenaOffer = (TextView) viewDialog.findViewById(R.id.textCena);
        imageOffer = (ImageView) viewDialog.findViewById(R.id.imageOffer);
        buttonClose = (Button) viewDialog.findViewById(R.id.buttonCloseOffer);
        optionText = (TextView) viewDialog.findViewById(R.id.textOption);

        Picasso.with(getContext()).load(urlPicture).into(imageOffer);

        String[] nameOption = getResources().getStringArray(R.array.option_item);

        nameOffer.setText(name);
        descriptionOffer.setText(description);
        cenaOffer.setText(cena);

       StringBuilder builder = new StringBuilder();

        for (int i = 0; i<optionOffer.size(); i++){
            if (!optionOffer.get(i).equals("")){
                builder.append(nameOption[i]).append(" ").append(optionOffer.get(i)).append(" \n");
            }
        }

        String option = builder.toString();
        optionText.setText(option);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return viewDialog;
    }

    public void setNameOffer(String name){
        this.name = name;
    }
    public void setDescriptionOffer (String description){
        this.description = description;
    }

    public void setUrlPicture (String urlPicture){
        this.urlPicture=urlPicture;
    }

    public void setCenaOffer (String cena){
        this.cena=cena;
    }

    public void setOptionOffer (List<String> optionOffer){
        this.optionOffer=optionOffer;
    }
}
