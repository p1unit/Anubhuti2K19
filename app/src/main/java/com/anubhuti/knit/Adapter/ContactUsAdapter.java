package com.anubhuti.knit.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anubhuti.knit.Model.ContactUs;
import com.anubhuti.knit.R;

import java.util.List;

public class ContactUsAdapter extends RecyclerView.Adapter<ContactUsAdapter.MyViewHolder> {

    List<ContactUs> lis;

    public ContactUsAdapter(List<ContactUs> lis) {
        this.lis = lis;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_list_layout,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.name.setText(lis.get(i).getName());
        holder.post.setText((lis.get(i).getPost()+","+lis.get(i).getSector()));
        holder.email.setText(lis.get(i).getEmail());
        holder.phoneNo.setText(lis.get(i).getPhone());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name,post,phoneNo,email;
        ImageView emailImage,phoneImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=(TextView)itemView.findViewById(R.id.name_cantact);
            post=(TextView)itemView.findViewById(R.id.post_contact);
            email=(TextView)itemView.findViewById(R.id.email_contact);
            phoneNo=(TextView)itemView.findViewById(R.id.phone_no_contact);
            emailImage=(ImageView)itemView.findViewById(R.id.email_image);
            phoneImage=(ImageView)itemView.findViewById(R.id.phone_image);

            emailImage.setOnClickListener(this);
            phoneImage.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.email_image:
                    handelEmailClick();
                    break;
                case R.id.phone_image:
                    handelPhoneClick();
                    break;
            }

        }

        private void handelPhoneClick() {
            // ToDo phone Click Handel
        }

        private void handelEmailClick() {

            // ToDo email Click handel
        }
    }
}
