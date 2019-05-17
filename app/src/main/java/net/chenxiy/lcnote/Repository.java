package net.chenxiy.lcnote;

import net.chenxiy.lcnote.net.ApiEndpointInterface;
import net.chenxiy.lcnote.net.RetrofitInstance;

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
}
