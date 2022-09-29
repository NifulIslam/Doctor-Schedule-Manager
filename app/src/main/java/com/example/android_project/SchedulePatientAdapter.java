package com.example.android_project;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SchedulePatientAdapter extends RecyclerView.Adapter<SchedulePatientAdapter.SchedulePatientViewHolder>{

    Context context;
    ArrayList<Schedule> allSchedules;

    public SchedulePatientAdapter(Context context, ArrayList<Schedule> allSchedules) {
        this.context = context;
        this.allSchedules = allSchedules;
    }

    @NonNull
    @Override
    public SchedulePatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedules_book,parent,false);
        return new SchedulePatientViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SchedulePatientViewHolder holder, int position) {
        Schedule schedule=allSchedules.get(position);
        int startHour = (int) schedule.startHour;
        int startMinute =(int) ((schedule.startHour-startHour)*60.0);
        int endHour = (int) schedule.endHour;
        int endMinute =(int) ((schedule.endHour-endHour)*60.0);

        holder.setValue(schedule.doctor,schedule.day,schedule.month,schedule.year,startHour,startMinute,endHour,endMinute,schedule.isFree);

    }

    @Override
    public int getItemCount() {
        return allSchedules.size();
    }

    public class SchedulePatientViewHolder extends RecyclerView.ViewHolder{
        TextView date, month, year, startHour, startMinute, endHour, endMinute,doctorName;
        ImageView doctorImage;
        Button bookBtn;


        public SchedulePatientViewHolder(@NonNull View itemView) {
            super(itemView);
            date= (TextView) itemView.findViewById(R.id.day);
            month= (TextView) itemView.findViewById(R.id.month);
            year= (TextView) itemView.findViewById(R.id.year);
            startHour= (TextView) itemView.findViewById(R.id.starHour);
            startMinute= (TextView) itemView.findViewById(R.id.starMinute);
            endHour= (TextView) itemView.findViewById(R.id.endHour);
            endMinute= (TextView) itemView.findViewById(R.id.endMinute);
            doctorName= (TextView) itemView.findViewById(R.id.doctorName);
            doctorImage =(ImageView) itemView.findViewById(R.id.doctorImg);
            bookBtn= (Button)itemView.findViewById(R.id.bookBtn);
            bookBtn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    String btnName=bookBtn.getText().toString();
                    if(btnName.equals("booked")){return;}

                    int startHourI = Integer.parseInt(startHour.getText().toString());
                    int startMinuteI = Integer.parseInt(startMinute.getText().toString());
                    int endHourI = Integer.parseInt(endHour.getText().toString());
                    int endMinuteI = Integer.parseInt(endMinute.getText().toString());
                    int dateI = Integer.parseInt(date.getText().toString());
                    int monthI = Integer.parseInt(month.getText().toString());
                    int yearI = Integer.parseInt(year.getText().toString());
                    String nameI = doctorName.getText().toString();

                    double startTime = 0.0+ startHourI + (1.0*startMinuteI/60);
                    double endTime = 0.0+ (1.0*endMinuteI/60)+ endHourI ;

                    Schedule  s = new Schedule(nameI,dateI,monthI,yearI,startTime,endTime,true);
                    DoctorScheduleHelper doctorScheduleHelper= new DoctorScheduleHelper(context);
                    doctorScheduleHelper.setBooked(s);
                    bookBtn.setText("booked");
                    bookBtn.setTextColor(Color.RED);




//

                }
            });

        }
        public void setValue(String name,int date , int month, int year, int startHour, int startMinute, int endHour, int endMinute,boolean isBooked){

            this.date.setText(""+date);
            this.month.setText(""+month);
            this.year.setText(""+year);
            this.startHour.setText(""+startHour);
            this.startMinute.setText(""+startMinute);
            this.endHour.setText(""+endHour);
            this.endMinute.setText(""+endMinute);
            this.doctorName.setText(name);
            if(name.equals("mr A")){
                this.doctorImage.setImageResource(R.drawable.img);
                return;
            }
            if(name.equals("mr B")){
                this.doctorImage.setImageResource(R.drawable.img_1);
                return;
            }
            if(name.equals("ms C")){
                this.doctorImage.setImageResource(R.drawable.img_2);
                return;
            }



        }

    }
}
