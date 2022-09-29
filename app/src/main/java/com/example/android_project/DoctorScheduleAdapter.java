package com.example.android_project;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoctorScheduleAdapter extends RecyclerView.Adapter<DoctorScheduleAdapter.ScheduleViewHolder> {

    Context context;
    ArrayList<Schedule> schedules;

    public DoctorScheduleAdapter(Context context, ArrayList<Schedule> schedules) {
        this.context = context;
        this.schedules = schedules;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_doctor,parent,false);
        return  new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        Schedule schedule= schedules.get(position);
        int startHour = (int) schedule.startHour;
        int startMinute =(int) ((schedule.startHour-startHour)*60.0);
        int endHour = (int) schedule.endHour;
        int endMinute =(int) ((schedule.endHour-endHour)*60.0);
        holder.setData(schedule.day,schedule.month,schedule.year,startHour,startMinute,endHour,endMinute,schedule.isFree);

    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {

        TextView date, month, year, startHour, startMinute, endHour, endMinute, isBooked;
        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            date= (TextView) itemView.findViewById(R.id.day);
            month= (TextView) itemView.findViewById(R.id.month);
            year= (TextView) itemView.findViewById(R.id.year);
            startHour= (TextView) itemView.findViewById(R.id.starHour);
            startMinute= (TextView) itemView.findViewById(R.id.starMinute);
            endHour= (TextView) itemView.findViewById(R.id.endHour);
            endMinute= (TextView) itemView.findViewById(R.id.endMinute);
            isBooked= (TextView) itemView.findViewById(R.id.isBooked);

        }
        public void setData(int date , int month, int year, int startHour, int startMinute, int endHour, int endMinute, boolean isFree ){
            this.date.setText(""+date);
            this.month.setText(""+month);
            this.year.setText(""+year);
            this.startHour.setText(""+startHour);
            this.startMinute.setText(""+startMinute);
            this.endHour.setText(""+endHour);
            this.endMinute.setText(""+endMinute);
            if(isFree){
                this.isBooked.setText("free");
                this.isBooked.setTextColor(Color.GREEN);
            }
            else{
                this.isBooked.setText("booked");
                this.isBooked.setTextColor(Color.GRAY);
            }

        }

    }

}
