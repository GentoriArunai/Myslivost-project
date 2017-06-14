package cz.folprechtova.hides.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import cz.folprechtova.hides.utils.FakeDataBuilder;
import cz.folprechtova.hides.utils.JSONUtils;
import cz.folprechtova.hides.R;
import cz.folprechtova.hides.dto.Hide;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        /**
         * TAKTO BYCHOM MOHLI VOLAT Z API
        RestClient.get().getAllHides().enqueue(new Callback<Hide>() {
            @Override
            public void onResponse(Call<Hide> call, Response<Hide> response) {
                response.body(); //obsahuje seznam posedů a komentářů
            }

            @Override
            public void onFailure(Call<Hide> call, Throwable t) {

            }
        });

         MY SI MÍSTO TOHO DATA VEZMEME Z FAKE BUILDERU
        */

        //s použitím GSON knihovny přepošleme do metody typ třídy ve tvaru Class[].class
        List<Hide> list = JSONUtils.getListFromJson(FakeDataBuilder.FAKE, Hide[].class);

        setupAdapters(list);

    }

    private void setupAdapters(List<Hide> list){
        HideAdapter hideAdapter = new HideAdapter(list);
        recyclerView.setAdapter(hideAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    //adaptér k recycler view
    class HideAdapter extends RecyclerView.Adapter<HideAdapter.ViewHolder>{

        private List<Hide> items;

        public HideAdapter(List<Hide> items) {
            this.items = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hide_item, parent, false);
            return new ViewHolder(itemView);
        }

        /* Mapování listu */
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.titleStand.setText(items.get(position).getName());
            // holder.checkOcc --------- jak propojit tenhle checkBox s hodnotou z FAKE jsonu?
        }

        /*Počet posedů v listu*/
        @Override
        public int getItemCount() {
            return items == null? 0 : items.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

            TextView titleStand;
            CheckBox checkOcc;

            public ViewHolder(View itemView) {
                super(itemView);

                titleStand = ((TextView) itemView.findViewById(R.id.titleStand));
                checkOcc = ((CheckBox) itemView.findViewById(R.id.checkOccupied));

                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HideDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("HIDE", items.get(getLayoutPosition()));
                intent.putExtras(bundle);
                startActivity(intent); //díky tomu že Hide i Comment implementují Serializable, lze je narvat do bundles to intentu a v jiné aktivitě si je vzít
            }

            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("HIDE", items.get(getLayoutPosition()));
                intent.putExtras(bundle);
                startActivity(intent); //díky tomu že Hide i Comment implementují Serializable, lze je narvat do bundles to intentu a v jiné aktivitě si je vzít
                return true;
            }
        }
    }
}
