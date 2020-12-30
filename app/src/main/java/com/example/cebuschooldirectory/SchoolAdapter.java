package com.example.cebuschooldirectory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class SchoolAdapter extends FirestoreRecyclerAdapter<SchoolModel, SchoolAdapter.SchoolHolder> {

    private OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SchoolAdapter(@NonNull FirestoreRecyclerOptions<SchoolModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SchoolHolder holder, int position, @NonNull SchoolModel model) {
        holder.textView_school_name.setText(model.getSchoolname());
        holder.textView_school_address.setText(model.getSchooladdress());
        holder.textView_school_contact.setText(model.getSchoolcontact());
        holder.textView_school_email.setText(model.getSchoolemail());
        holder.textView_school_website.setText(model.getSchoolwebsite());
        holder.textView_school_postedby.setText(model.getPostedby());
    }

    @NonNull
    @Override
    public SchoolHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_single_list, parent, false);
        return new SchoolHolder(view);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class SchoolHolder extends RecyclerView.ViewHolder{

        private TextView textView_school_name, textView_school_address, textView_school_contact, textView_school_email, textView_school_website, textView_school_postedby;

        public SchoolHolder(@NonNull View itemView) {
            super(itemView);

            textView_school_name = itemView.findViewById(R.id.textView_school_name);
            textView_school_contact = itemView.findViewById(R.id.textView_school_contact);
            textView_school_email = itemView.findViewById(R.id.textView_school_email);
            textView_school_website = itemView.findViewById(R.id.textView_school_website);
            textView_school_postedby = itemView.findViewById(R.id.textView_school_postedby);
            textView_school_address = itemView.findViewById(R.id.textView_school_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
