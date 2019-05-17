package net.chenxiy.lcnote;

import net.chenxiy.lcnote.net.pojo.ProblemData;

import org.junit.Test;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void retrofit(){
        Repository.getInstance().apiService.getProblemData("").enqueue(new Callback<ProblemData>() {
            @Override
            public void onResponse(Call<ProblemData> call, Response<ProblemData> response) {
                System.out.println(response.body().getUserName());
            }

            @Override
            public void onFailure(Call<ProblemData> call, Throwable t) {

            }
        });

    }
}