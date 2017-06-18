package cz.folprechtova.hides.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.folprechtova.hides.R;
import cz.folprechtova.hides.dto.Comment;
import cz.folprechtova.hides.dto.Hide;
import cz.folprechtova.hides.utils.FakeDataBuilder;
import cz.folprechtova.hides.utils.JSONUtils;


public class NewsActivity extends BaseBackButtonActivity {

    private LinearLayoutManager linearLayoutManager;
    private CommentAdapter commentAdapter;
    private RecyclerView recyclerView;
    private List<Comment> list;
    private List<Hide> listHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        listHide = JSONUtils.getListFromJson(FakeDataBuilder.FAKE, Hide[].class);
        list = new ArrayList<>(); //JSONUtils.getListFromJson(FakeDataBuilder.FAKE, Comment[].class);

        //List<Comment> comments = new ArrayList<>();
        for (Hide hide : listHide) {
            for (Comment comment : hide.getComments()) {
                list.add(comment);
            }
        }
        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Collections.sort(list, new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                try {
                    return sdf.parse(o1.getDate()).compareTo(sdf.parse(o2.getDate()));
                } catch (ParseException e) {
                    return 0;
                }
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        setupAdapters(list);

    }
// /**
    private void setupAdapters(List<Comment> list) {
        commentAdapter = new CommentAdapter(list);
        recyclerView.setAdapter(commentAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void refreshList(Comment newComment) {
        commentAdapter.addNewItemToTop(newComment);
        //TODO zde bychom provolali metodu RestClient.addComment
        // Ale protože naše api nejede :( musíme si vystačit pouze s takovou runtime funkčností
    }

    //adaptér k recycler view
    class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

        private List<Comment> items;

        public CommentAdapter(List<Comment> items) {
            this.items = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
            return new CommentAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
            System.out.println(items.size());
            if(holder.newsSubTitleTextView != null) holder.newsTitleTextView.setText(items.get(position).getComment());
            //holder.newsSubTitleTextView.setText(items.get(position).getDate());
            //holder.userCom.setText(items.get(position).getUser());

        }

        @Override
        public int getItemCount() {
            return items == null ? 0 : items.size();
        }

        public void addNewItemToTop(Comment comment) {
            items.add(0, comment);
            notifyItemInserted(0);
            linearLayoutManager.scrollToPositionWithOffset(0, 0);
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

            TextView newsTitleTextView;
            TextView newsSubTitleTextView;
            TextView userCom;

            public ViewHolder(View itemView) {
                super(itemView);

                newsTitleTextView = ((TextView) itemView.findViewById(R.id.newsTitleTextView));
                newsSubTitleTextView = ((TextView) itemView.findViewById(R.id.newsSubTitleTextView));
                userCom = ((TextView) itemView.findViewById(R.id.userCom));

                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }


            @Override
            public void onClick(View v) {
            }

            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        }
    }
}
