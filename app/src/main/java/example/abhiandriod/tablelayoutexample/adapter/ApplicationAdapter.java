package example.abhiandriod.tablelayoutexample.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import example.abhiandriod.tablelayoutexample.R;
import example.abhiandriod.tablelayoutexample.logicadenegocio.JobApplication;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.MyViewHolder> implements Filterable {
    private List<JobApplication> jobApplicationList;
    private List<JobApplication> jobApplicationListFiltered;
    private JobApplicationAdapterListener listener;
    private JobApplication deletedItem;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo1, titulo2, description, number;
        //two layers
        public RelativeLayout viewForeground, viewBackgroundDelete, viewBackgroundEdit;


        public MyViewHolder(View view) {
            super(view);
            titulo1 = view.findViewById(R.id.titleFirstLbl);
            titulo2 = view.findViewById(R.id.titleSecLbl);
            description = view.findViewById(R.id.descriptionLbl);
            number = view.findViewById(R.id.numberLbl);
            viewBackgroundDelete = view.findViewById(R.id.view_background_delete);
            viewBackgroundEdit = view.findViewById(R.id.view_background_edit);
            viewForeground = view.findViewById(R.id.view_foreground);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onContactSelected(jobApplicationListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    public ApplicationAdapter(List<JobApplication> jobApplicationlist, JobApplicationAdapterListener listener) {
        this.jobApplicationList = jobApplicationlist;
        this.listener = listener;
        //init filter
        this.jobApplicationListFiltered = jobApplicationlist;
    }

    @NonNull
    @Override
    public ApplicationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ApplicationAdapter.MyViewHolder holder, int position) {
        // rendering view
        final JobApplication jobApplication = jobApplicationListFiltered.get(position);
        holder.titulo1.setText(jobApplication.getFirstName() + " " +jobApplication.getLastName());
        holder.titulo2.setText(jobApplication.getPosition());
        holder.description.setText("Phone number: "+ jobApplication.getAreaCode()+" "+ jobApplication.getPhoneNumber());
        holder.number.setText("Email: "+jobApplication.getEmail());
    }


    @Override
    public int getItemCount() {
        return jobApplicationListFiltered.size();
    }

    public void removeItem(int position) {
        deletedItem = jobApplicationListFiltered.remove(position);
        Iterator<JobApplication> iter = jobApplicationList.iterator();
        while (iter.hasNext()) {
            JobApplication aux = iter.next();
            if (deletedItem.equals(aux))
                iter.remove();
        }
        // notify item removed
        notifyItemRemoved(position);
    }

    public void restoreItem(int position) {

        if (jobApplicationListFiltered.size() == jobApplicationList.size()) {
            jobApplicationListFiltered.add(position, deletedItem);
        } else {
            jobApplicationListFiltered.add(position, deletedItem);
            jobApplicationList.add(deletedItem);
        }
        notifyDataSetChanged();
        // notify item added by position
        notifyItemInserted(position);
    }

    public JobApplication getSwipedItem(int index) {
        if (this.jobApplicationList.size() == this.jobApplicationListFiltered.size()) { //not filtered yet
            return jobApplicationList.get(index);
        } else {
            return jobApplicationListFiltered.get(index);
        }
    }

    public void onItemMove(int fromPosition, int toPosition) {
        if (jobApplicationList.size() == jobApplicationListFiltered.size()) { // without filter
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(jobApplicationList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(jobApplicationList, i, i - 1);
                }
            }
        } else {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(jobApplicationListFiltered, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(jobApplicationListFiltered, i, i - 1);
                }
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    jobApplicationListFiltered = jobApplicationList;
                } else {
                    List<JobApplication> filteredList = new ArrayList<>();
                    for (JobApplication row : jobApplicationList) {
                        // filter use two parameters
                        if (row.getFirstName().toLowerCase().contains(charString.toLowerCase()) || row.getLastName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    jobApplicationListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = jobApplicationListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                jobApplicationListFiltered = (ArrayList<JobApplication>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface JobApplicationAdapterListener {
        void onContactSelected(JobApplication jobApplication);
    }
}
