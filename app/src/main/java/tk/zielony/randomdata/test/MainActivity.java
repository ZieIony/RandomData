package tk.zielony.randomdata.test;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import carbon.component.AvatarTextSubtext2DateRow;
import carbon.component.DefaultAvatarTextSubtextDateItem;
import carbon.component.DefaultHeaderItem;
import carbon.component.HeaderRow;
import carbon.recycler.RowListAdapter;
import carbon.widget.RecyclerView;
import tk.zielony.randomdata.RandomData;
import tk.zielony.randomdata.common.DateGenerator;
import tk.zielony.randomdata.common.DrawableImageGenerator;
import tk.zielony.randomdata.common.EnumGenerator;
import tk.zielony.randomdata.common.FloatGenerator;
import tk.zielony.randomdata.common.TextGenerator;
import tk.zielony.randomdata.finance.StringAmountGenerator;
import tk.zielony.randomdata.finance.StringCardNumberGenerator;
import tk.zielony.randomdata.person.DrawableAvatarGenerator;
import tk.zielony.randomdata.person.Gender;
import tk.zielony.randomdata.person.StringNameGenerator;
import tk.zielony.randomdata.transformer.DateToStringTransformer;

public class MainActivity extends AppCompatActivity {
    RowListAdapter<Serializable> adapter;
    private RandomData randomData;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RowListAdapter<>();
        adapter.putFactory(DefaultAvatarTextSubtextDateItem.class, AvatarTextSubtext2DateRow::new);
        adapter.putFactory(DefaultHeaderItem.class, HeaderRow::new);
        adapter.putFactory(CreditCardItem.class, CreditCardRow::new);
        recycler.setAdapter(adapter);

        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this::fillItems);

        swipeRefresh.setRefreshing(true);
        fillItems();
    }

    private void fillItems() {
        new Thread() {
            @Override
            public void run() {
                List<Serializable> items;
                if (Math.random() > 0.5) {
                    randomData = new RandomData();
                    randomData.addGenerator(String.class, new StringNameGenerator(Gender.Both).withMatcher(f -> "text".equals(f.getName()) && f.getDeclaringClass().equals(DefaultAvatarTextSubtextDateItem.class)));
                    randomData.addGenerator(String.class, new TextGenerator().withMatcher(f -> "subtext".equals(f.getName())));
                    randomData.addGenerator(Drawable.class, new DrawableAvatarGenerator(MainActivity.this));
                    randomData.addGenerator(String.class, new DateGenerator().withTransformer(new DateToStringTransformer()));

                    items = Arrays.asList(
                            new DefaultHeaderItem("Header"),
                            new DefaultAvatarTextSubtextDateItem(),
                            new DefaultAvatarTextSubtextDateItem(),
                            new DefaultHeaderItem("Header"),
                            new DefaultAvatarTextSubtextDateItem(),
                            new DefaultAvatarTextSubtextDateItem(),
                            new DefaultHeaderItem("Header"),
                            new DefaultAvatarTextSubtextDateItem(),
                            new DefaultAvatarTextSubtextDateItem(),
                            new DefaultHeaderItem("Header"),
                            new DefaultAvatarTextSubtextDateItem(),
                            new DefaultAvatarTextSubtextDateItem(),
                            new DefaultHeaderItem("Header"),
                            new DefaultAvatarTextSubtextDateItem(),
                            new DefaultAvatarTextSubtextDateItem());
                    randomData.fill(items);
                } else {
                    randomData = new RandomData();
                    randomData.addGenerator(String.class, new StringNameGenerator(Gender.Both));
                    randomData.addGenerator(String.class, new DateGenerator().withTransformer(new DateToStringTransformer()));
                    randomData.addGenerator(String.class, new StringCardNumberGenerator());
                    randomData.addGenerator(String.class, new StringAmountGenerator());
                    randomData.addGenerator(Drawable.class, new DrawableImageGenerator(MainActivity.this));
                    randomData.addGenerator(Validity.class, new EnumGenerator<>(Validity.class));
                    randomData.addGenerator(Float.class, new FloatGenerator());

                    items = new ArrayList<>(randomData.generateList(CreditCardItem.class, 10));
                }
                runOnUiThread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    adapter.setItems(items);
                    swipeRefresh.setRefreshing(false);
                });
            }
        }.start();
    }
}
