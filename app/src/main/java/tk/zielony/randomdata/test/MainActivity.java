package tk.zielony.randomdata.test;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import carbon.component.AvatarTextSubtext2DateRow;
import carbon.component.DefaultAvatarTextSubtextDateItem;
import carbon.component.DefaultHeaderItem;
import carbon.component.HeaderRow;
import carbon.recycler.RowListAdapter;
import carbon.widget.RecyclerView;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.RandomData;
import tk.zielony.randomdata.common.DrawableImageGenerator;
import tk.zielony.randomdata.common.StringDateGenerator;
import tk.zielony.randomdata.common.TextGenerator;
import tk.zielony.randomdata.finance.StringAmountGenerator;
import tk.zielony.randomdata.finance.StringCardNumberGenerator;
import tk.zielony.randomdata.person.DrawableAvatarGenerator;
import tk.zielony.randomdata.person.Gender;
import tk.zielony.randomdata.person.StringNameGenerator;

public class MainActivity extends AppCompatActivity {
    RowListAdapter adapter;
    private RandomData randomData;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RowListAdapter(DefaultAvatarTextSubtextDateItem.class, AvatarTextSubtext2DateRow::new);
        adapter.addFactory(DefaultHeaderItem.class, HeaderRow::new);
        adapter.addFactory(CreditCardItem.class, CreditCardRow::new);
        recycler.setAdapter(adapter);

        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this::fillItems);

        randomData = new RandomData();
        randomData.addGenerators(new Generator[]{
                new StringNameGenerator(Gender.Both).withMatcher(f -> f.getName().equals("text") && f.getDeclaringClass().equals(DefaultAvatarTextSubtextDateItem.class) || f.getName().equals("name")),
                new TextGenerator().withMatcher(f -> f.getName().equals("subtext")),
                new DrawableAvatarGenerator(this),
                new StringDateGenerator(),
                new StringCardNumberGenerator(),
                new StringAmountGenerator(),
                new DrawableImageGenerator(this).withMatcher(f -> f.getName().equals("image"))
        });

        swipeRefresh.setRefreshing(true);
        fillItems();
    }

    private void fillItems() {
        List<Serializable>  items;
        if (Math.random() > 0.5) {
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
        } else {
            items = Arrays.asList(new CreditCardItem[10]);
        }
        randomData.fillAsync(items, () -> runOnUiThread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            adapter.setItems(items);
            swipeRefresh.setRefreshing(false);
        }));
    }
}
