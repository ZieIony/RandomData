package tk.zielony.randomdata.common;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;
import tk.zielony.randomdata.R;

public class DrawableImageGenerator extends Generator<Drawable> {
    private Context context;
    private Random random = new Random();
    private boolean preventDuplicates;

    private Set<Integer> used = new HashSet<>();

    private int[] images = {
            R.drawable.woman0, R.drawable.woman1, R.drawable.woman2, R.drawable.woman3, R.drawable.woman4, R.drawable.woman5, R.drawable.woman6, R.drawable.woman7, R.drawable.woman8, R.drawable.woman9
    };

    public DrawableImageGenerator(Context context) {
        this.context = context;
        preventDuplicates = true;
    }

    public DrawableImageGenerator(Context context, boolean preventDuplicates) {
        this.context = context;
        this.preventDuplicates = preventDuplicates;
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getType().equals(Drawable.class) && (f.getName().equals("image") || f.getName().equals("picture"));
    }

    @Override
    public Drawable next(DataContext dataContext) {
        return context.getResources().getDrawable(next2());
    }

    private int next2() {
        int resId;
        if (preventDuplicates) {
            if (used.size() == images.length)
                used.clear();
            do {
                resId = images[random.nextInt(images.length)];
            }
            while (used.contains(resId));
            used.add(resId);
        } else {
            resId = images[random.nextInt(images.length)];
        }
        return resId;
    }

    @Override
    public void reset() {
        used.clear();
    }
}
