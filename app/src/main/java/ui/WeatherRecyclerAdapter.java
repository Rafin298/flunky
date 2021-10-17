package ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.app1.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import model.weather.WeatherList;

public class WeatherRecyclerAdapter extends RecyclerView.Adapter<WeatherRecyclerAdapter.ViewHolder>{
    Context context;
    List<WeatherList> weatherList;

    public WeatherRecyclerAdapter(Context context, List<WeatherList> weatherList) {
        this.context = context;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final WeatherRecyclerAdapter.ViewHolder holder, int position) {


        final WeatherList a = weatherList.get(position);
       // String image = updateWeatherIcon(a.weather.get(0).id);
        int condition = a.weather.get(0).id;
       // int resourceID=getResources().getIdentifier(image,"drawable",getPackageName());
        //weatherIcon.setImageResource(resourceID);

       // String imageUrl = a.getUrlToImage();
//        String url = a.getUrl();

       // Picasso.get().load(imageUrl).into(holder.imageView);

        //holder.mainTemp.setText(a.weather.get(0).main);
        double maintempResult=a.listMain.getTemp()-273.15;
        int roundedValue=(int)Math.rint(maintempResult);

        double hightempResult=a.listMain.getTemp_max()-273.15;
        int roundedValue2=(int)Math.rint(hightempResult);

        double lowmaintempResult=a.listMain.getTemp_min()-273.15;
        int roundedValue3=(int)Math.rint(lowmaintempResult);
        //weatherD.mTemperature=Integer.toString(roundedValue);
        //SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
        //Date myDate = myFormat.parse(a.dt_txt);

        holder.mainTemp.setText(Integer.toString(roundedValue)+"°C");
        holder.highTemp.setText(Integer.toString(roundedValue2)+"°C");
        holder.lowTemp.setText(Integer.toString(roundedValue3)+"°C");
       // holder.time.setText(a.getDt_txt());
        holder.date.setText(new StringBuilder(converUnixToDate(a.getDt())));
        holder.times.setText(new StringBuilder(converUnixToHour(a.getDt())));
        //holder.time.setText(dateTime(a.dt_txt));

        if(condition>=0 && condition<=300)
        {
            holder.weatherImage.setAnimation(R.raw.daythunder);
        }
        else if(condition>=300 && condition<=500)
        {
            holder.weatherImage.setAnimation(R.raw.lightrain);
        }
        else if(condition>=500 && condition<=600)
        {
            holder.weatherImage.setAnimation(R.raw.shower);
        }
        else  if(condition>=600 && condition<=700)
        {
            holder.weatherImage.setAnimation(R.raw.nightsnow);
        }
        else if(condition>=701 && condition<=771)
        {
            holder.weatherImage.setAnimation(R.raw.fog);
        }

        else if(condition>=772 && condition<=800)
        {
            holder.weatherImage.setAnimation(R.raw.overcast);
        }
        else if(condition==800)
        {
            holder.weatherImage.setAnimation(R.raw.sunny);
        }
        else if(condition>=801 && condition<=804)
        {
            holder.weatherImage.setAnimation(R.raw.cloudy);
        }
        else  if(condition>=900 && condition<=902)
        {
            holder.weatherImage.setAnimation(R.raw.daythunder);
        }
        else if(condition==903)
        {
            holder.weatherImage.setAnimation(R.raw.daysnow);
        }
        else if(condition==904)
        {
            holder.weatherImage.setAnimation(R.raw.sunny);
        }
        else if(condition>=905 && condition<=1000)
        {
            holder.weatherImage.setAnimation(R.raw.nightthunder);
        }else{
            holder.weatherImage.setAnimation(R.raw.sunny);
        }

       // holder.tvSource.setText(a.getSource().getName());
       // holder.tvDate.setText("\u2022"+dateTime(a.getPublishedAt()));

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, news2_detail.class);
//                intent.putExtra("title",a.getTitle());
//                intent.putExtra("source",a.getSource().getName());
//                intent.putExtra("time",dateTime(a.getPublishedAt()));
//                intent.putExtra("desc",a.getDescription());
//                intent.putExtra("imageUrl",a.getUrlToImage());
//                intent.putExtra("url",a.getUrl());
//                context.startActivity(intent);
//            }
//        });

    }
    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mainTemp,lowTemp,highTemp,times,date;
      //  ImageView weatherImage;
        CardView cardView;
        LottieAnimationView weatherImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainTemp = itemView.findViewById(R.id.mainTemp);
            lowTemp = itemView.findViewById(R.id.lowTemp);
            highTemp = itemView.findViewById(R.id.highTemp);
            date = itemView.findViewById(R.id.date);
            times = itemView.findViewById(R.id.times);
            weatherImage = itemView.findViewById(R.id.logos);

           // weatherImage = itemView.findViewById(R.id.weatherImage);
          //  tvSource = itemView.findViewById(R.id.tvSource);
           // tvDate = itemView.findViewById(R.id.tvDate);
           // imageView = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.card13);

        }
    }
    private static String updateWeatherIcon(int condition)
    {

        if(condition>=0 && condition<=300)
        {
            return "daythunder";
        }
        else if(condition>=300 && condition<=500)
        {
            return "lightrain";
        }
        else if(condition>=500 && condition<=600)
        {
            return "shower";
        }
        else  if(condition>=600 && condition<=700)
        {
            return "nightsnow";
        }
        else if(condition>=701 && condition<=771)
        {
            return "fog";
        }

        else if(condition>=772 && condition<=800)
        {
            return "overcast";
        }
        else if(condition==800)
        {
            return "sunny";
        }
        else if(condition>=801 && condition<=804)
        {
            return "cloudy";
        }
        else  if(condition>=900 && condition<=902)
        {
            return "daythunder";
        }
        if(condition==903)
        {
            return "daysnow";
        }
        if(condition==904)
        {
            return "sunny";
        }
        if(condition>=905 && condition<=1000)
        {
            return "nightthunder";
        }

        return "dunno";


    }
    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
//    public String converUnixToDate(long dt){
//        Date date=new Date(dt*1000L);
//        SimpleDateFormat sdf =new SimpleDateFormat("HH:mm:dd EEE MM yyyy");
//
//        String formatted = sdf.format(date);
//
//        return formatted;
//
//    }
    public String converUnixToDate(long dt){
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf =new SimpleDateFormat("EEE, MMM dd");

        String formatted = sdf.format(date);

        return formatted;

    }
    public String converUnixToHour(long dt){
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf =new SimpleDateFormat("hh:mm aaa");

        String formatted = sdf.format(date);

        return formatted;

    }


}
