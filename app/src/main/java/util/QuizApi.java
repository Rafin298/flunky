package util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizApi {
    private static final String BASE_URL = "https://restcountries.eu/rest/v2/";
    private static QuizApi quizApi;
    private static Retrofit retrofit;


    private QuizApi(){

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized QuizApi getInstance(){
        if (quizApi == null){
            quizApi = new QuizApi();
        }
        return quizApi;
    }


    public QuizApiInterface getApi(){
        return retrofit.create(QuizApiInterface.class);
    }
}
