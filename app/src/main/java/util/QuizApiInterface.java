package util;

import java.util.List;

import model.Headlines;
import model.quiz.countryData;
import model.quiz.quizData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuizApiInterface {
    @GET("region/asia")
    Call<List<countryData>> getCountries();
}
