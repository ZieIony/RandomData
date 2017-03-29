package tk.zielony.randomdata.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;

import carbon.component.AvatarTextSubtext2DateRow;
import carbon.component.DefaultAvatarTextSubtextDateItem;
import carbon.recycler.RowListAdapter;
import carbon.widget.RecyclerView;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.RandomData;
import tk.zielony.randomdata.common.StringDateGenerator;
import tk.zielony.randomdata.common.TextGenerator;
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

        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        adapter = new RowListAdapter<>(DefaultAvatarTextSubtextDateItem.class, AvatarTextSubtext2DateRow.FACTORY);
        recycler.setAdapter(adapter);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this::fillItems);

        randomData = new RandomData();
        randomData.addGenerators(new Generator[]{
                new StringNameGenerator(Gender.Both).withMatcher(f -> f.getName().equals("text")),
                new TextGenerator().withMatcher(f -> f.getName().equals("subtext")),
                new DrawableAvatarGenerator(this),
                new StringDateGenerator()
        });

        swipeRefresh.setRefreshing(true);
        fillItems();
    }

    private void fillItems() {
        DefaultAvatarTextSubtextDateItem[] items = new DefaultAvatarTextSubtextDateItem[10];
        randomData.fillAsync(items, () -> runOnUiThread(() -> {
            adapter.setItems(Arrays.asList(items));
            swipeRefresh.setRefreshing(false);
        }));
    }
}
