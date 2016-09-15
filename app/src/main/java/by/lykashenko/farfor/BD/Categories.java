package by.lykashenko.farfor.BD;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Дмитрий on 11.09.16.
 */
@Table(name = "category", id = "_id")
public class Categories extends Model {

    @Column(name = "type")
    public Integer index;

    @Column(name = "category")
    public String category;

    public Categories() {
        super();
    }

    public Categories(Integer index,
                      String category) {
        super();
        this.index = index;
        this.category = category;
    }
}
