package tk.zielony.randomdata.person;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

/**
 * Created by Marcin on 27.03.2017.
 */
public class DrawableAvatarGenerator extends Generator<Drawable> {
    private Context context;
    private Random random = new Random();
    private Gender gender;
    private boolean preventDuplicates;

    private Set<Integer> femaleUsed = new HashSet<>();
    private Set<Integer> maleUsed = new HashSet<>();

    private int[] female = {
            R.drawable.randomdata_woman0, R.drawable.randomdata_woman1, R.drawable.randomdata_woman2, R.drawable.randomdata_woman3, R.drawable.randomdata_woman4, R.drawable.randomdata_woman5, R.drawable.randomdata_woman6, R.drawable.randomdata_woman7, R.drawable.randomdata_woman8, R.drawable.randomdata_woman9
    };

    private int[] male = {
            R.drawable.randomdata_man0, R.drawable.randomdata_man1, R.drawable.randomdata_man2, R.drawable.randomdata_man3, R.drawable.randomdata_man4, R.drawable.randomdata_man5, R.drawable.randomdata_man6, R.drawable.randomdata_man7, R.drawable.randomdata_man8, R.drawable.randomdata_man9
    };

    public DrawableAvatarGenerator(Context context) {
        this.context = context;
        gender = Gender.Both;
        preventDuplicates = true;
    }

    public DrawableAvatarGenerator(Context context, Gender gender, boolean preventDuplicates) {
        this.context = context;
        this.gender = gender;
        this.preventDuplicates = preventDuplicates;
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getType().equals(Drawable.class) && f.getName().equals("avatar");
    }

    @Override
    public Drawable next(DataContext dataContext) {
        return context.getResources().getDrawable(next2(dataContext));
    }

    private int next2(DataContext dataContext) {
        Gender g = dataContext.get(PersonDataContext.GENDER);
        if (g == null)
            dataContext.set(PersonDataContext.GENDER, g = gender == Gender.Both ? random.nextBoolean() ? Gender.Female : Gender.Male : gender == Gender.Female ? Gender.Female : Gender.Male);
        int resId;
        if (preventDuplicates) {
            if (g == Gender.Male && maleUsed.size() == male.length)
                maleUsed.clear();
            if (g == Gender.Female && femaleUsed.size() == female.length)
                femaleUsed.clear();
            do {
                resId = next3(g);
            }
            while (g == Gender.Male && maleUsed.contains(resId) || g == Gender.Female && femaleUsed.contains(resId));
            if (g == Gender.Male) {
                maleUsed.add(resId);
            } else {
                femaleUsed.add(resId);
            }
        } else {
            resId = next3(g);
        }
        return resId;
    }

    private int next3(Gender gender) {
        return gender == Gender.Female ? female[random.nextInt(female.length)] : male[random.nextInt(male.length)];
    }

    @Override
    public void reset() {
        maleUsed.clear();
        femaleUsed.clear();
    }
}
