package by.lykashenko.farfor.BD;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import by.lykashenko.farfor.XmlConstructer.Offer;

/**
 * Created by Дмитрий on 11.09.16.
 */
@Table(name = "offers", id = "_id")
public class Offers extends Model {

    @Column(name = "idOffers")
    public Integer idOffers;

    @Column(name = "categoryId")
    public Integer categoryId;

    @Column(name = "name")
    public String name;

    @Column(name = "description")
    public String description;

    @Column(name = "picture")
    public String picture;

    @Column(name = "price")
    public String price;

    @Column(name = "diametr")
    public String diametr;

    @Column(name = "ves")
    public String ves;

    @Column(name = "kolichestvo")
    public String kolichestvo;

    @Column(name = "energy")
    public String energy;

    @Column(name = "belki")
    public String belki;

    @Column(name = "giru")
    public String giru;

    @Column(name = "yglevodu")
    public String yglevodu;

    public Offers() {

        super();
    }

    public Offers(Integer idOffers,
            Integer categoryId,
                  String name,
                  String description,
                  String picture,
                  String price,
                  String diametr,
                  String ves,
                  String kolichestvo,
                  String energy,
                  String belki,
                  String giru,
                  String yglevodu) {
        super();
        this.idOffers = idOffers;
        this.categoryId = categoryId;
        this.description = description;
        this.name = name;
        this.picture = picture;
        this.price = price;
        this.diametr = diametr;
        this.ves = ves;
        this.kolichestvo = kolichestvo;
        this.energy = energy;
        this.belki = belki;
        this.giru = giru;
        this.yglevodu = yglevodu;
    }

}
