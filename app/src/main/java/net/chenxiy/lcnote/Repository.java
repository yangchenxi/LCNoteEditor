package net.chenxiy.lcnote;

import android.webkit.CookieManager;

import net.chenxiy.lcnote.net.ApiEndpointInterface;
import net.chenxiy.lcnote.net.RetrofitInstance;
import net.chenxiy.lcnote.net.pojo.NoteData;
import net.chenxiy.lcnote.net.pojo.lcRequest.UpdateNoteRequest;
import net.chenxiy.lcnote.net.pojo.lcRequest.Variables;
import net.chenxiy.lcnote.net.pojo.updatenote.UpDateNoteData;


import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class Repository {
    private static final Object LOCK = new Object();
    private static Repository repository=null;
    public ApiEndpointInterface apiService;
    public static Repository getInstance(){
        if(repository==null){
            synchronized (LOCK) {
                repository = new Repository();

            }
        }
        return repository;
    }
    public Repository() {

        apiService= RetrofitInstance.getInstance().create(ApiEndpointInterface.class);

    }
    public String getCookie(String siteName,String CookieName){
        String CookieValue = null;

        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie(siteName);
        String[] temp=cookies.split(";");
        for (String ar1 : temp ){
            if(ar1.contains(CookieName)){
                String[] temp1=ar1.split("=");
                CookieValue = temp1[1];
                break;
            }
        }
        return CookieValue;
    }
    public Call<NoteData> getNoteBookData(String titleSlug){
        StringBuilder sb=new StringBuilder();
        sb.append("{\"operationName\":\"QuestionNote\",\"variables\":{\"titleSlug\":\"");
        sb.append(titleSlug);
        sb.append("\"},\"query\":\"query QuestionNote($titleSlug: String!) {\\n  question(titleSlug: $titleSlug) {\\n    questionId\\n    note\\n    __typename\\n  }\\n}\\n\"}");
        return apiService.getNoteData(CookieManager.getInstance().getCookie("https://leetcode.com"),
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:66.0) Gecko/20100101 Firefox/66.0",
                getCookie("https://leetcode.com","csrftoken"),
                "application/json",RequestBody.create(MediaType.parse("text/plain"), sb.toString()));
    }

    public Call<UpDateNoteData> updateNoteBookData(String titleSlug, String content){
        UpdateNoteRequest requestBody=new UpdateNoteRequest();
        requestBody.setOperationName("updateNote");
        requestBody.setQuery("mutation updateNote($titleSlug: String!, $content: String!) {\n  updateNote(titleSlug: $titleSlug, content: $content) {\n    ok\n    error\n    question {\n      questionId\n      note\n      __typename\n    }\n    __typename\n  }\n}\n");
        Variables v=new Variables();
        v.setTitleSlug(titleSlug);
        v.setContent(content);
        requestBody.setVariables(v);
        return apiService.updateNoteData(CookieManager.getInstance().getCookie("https://leetcode.com"),
                "https://leetcode.com/problems/"+titleSlug+"/",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:66.0) Gecko/20100101 Firefox/66.0",
                getCookie("https://leetcode.com","csrftoken"),
                "application/json",requestBody);
    }
}
