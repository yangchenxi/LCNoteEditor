package net.chenxiy.lcnote.View;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import net.chenxiy.lcnote.R;
import net.chenxiy.lcnote.activity.NotePadActivity;
import net.chenxiy.lcnote.databinding.ProblemItemBinding;
import net.chenxiy.lcnote.net.pojo.StatStatusPair;
import net.chenxiy.lcnote.net.pojo.Stat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProblemRecyclerViewAdapter extends RecyclerView.Adapter<ProblemRecyclerViewAdapter.ProblemViewHolder>  implements Filterable {
    private List<StatStatusPair> list=new ArrayList<>();
    private List<StatStatusPair> allData=new ArrayList<>();
    @NonNull
    @Override
    public ProblemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ProblemItemBinding binding=ProblemItemBinding.inflate(inflater,parent,false);

        return new ProblemViewHolder(binding.getRoot(),binding);

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                List<StatStatusPair> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered=allData;
                } else {
                    for (StatStatusPair item : list) {
                        if (item.getStat().getQuestionId().toString().toLowerCase().contains(query.toLowerCase())||
                        item.getStat().getQuestionTitle().toLowerCase().contains(query.toLowerCase())) {
                            filtered.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                list = (ArrayList<StatStatusPair>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void setData(List<StatStatusPair> newdata){
        Collections.sort(newdata, new Comparator<StatStatusPair>() {
            @Override
            public int compare(StatStatusPair o1, StatStatusPair o2) {
                if(o1.getStat().getQuestionId()<o1.getStat().getQuestionId()){
                    return 1;
                }else if(o1.getStat().getQuestionId()<o1.getStat().getQuestionId()){
                    return 0;
                }else{
                    return -1;
                }
            }
        });
        this.list=newdata;
        this.allData=newdata;
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(@NonNull ProblemViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ProblemViewHolder extends RecyclerView.ViewHolder{
        ProblemItemBinding itemBinding;
        public ProblemViewHolder(@NonNull View itemView,ProblemItemBinding mbinding) {
            super(itemView);
            itemBinding=mbinding;

        }
        public void bind(final StatStatusPair data) {
            itemBinding.setProblemStat(data.getStat());
            if(data.getStatus()!=null&&data.getStatus().equals("ac")){
                itemBinding.problemtitle.setTextColor(Color.parseColor("#399350"));
            }else{
                itemBinding.problemtitle.setTextColor(Color.BLACK);
            }

            itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(), NotePadActivity.class);
                    intent.putExtra("titleSlug",data.getStat().getQuestionTitleSlug());
                    intent.putExtra("title",data.getStat().getQuestionTitle());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
