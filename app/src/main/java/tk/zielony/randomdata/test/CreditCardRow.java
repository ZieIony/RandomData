package tk.zielony.randomdata.test;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.view.ViewGroup;

import carbon.component.DataBindingComponent;
import carbon.recycler.RowFactory;
import carbon.widget.LinearLayout;
import tk.zielony.randomdata.test.databinding.CreditcardBinding;

/**
 * Created by Marcin on 2017-03-31.
 */

class CreditCardRow extends DataBindingComponent<CreditCardItem> {
    public static final RowFactory FACTORY = CreditCardRow::new;

    public CreditCardRow(ViewGroup parent){
        super(parent,R.layout.creditcard);
    }

    @Override
    public void bind(CreditCardItem data) {
        super.bind(data);
        LinearLayout scrim = ((CreditcardBinding) getBinding()).scrim;
        int mutedColor = Palette.from(((BitmapDrawable)data.image).getBitmap()).generate().getMutedColor(0);
        scrim.setBackgroundColor(mutedColor);
    }
}
