package com.abel.bibliotecapp.retrofit;

import com.abel.bibliotecapp.model.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookApi {

    @GET("api/v1/books")
    Call<List<Book>> getAllBooks();

    @POST("api/v1/books")
    Call<Book> save(@Body Book book);

    @DELETE("api/v1/books/delete/{bookId}")
    Call<Void> delete(@Path("bookId") Long id);

    @GET("api/v1/books/categoria/{categoria}")
        Call<List<Book>> getByCategoria(@Path("categoria") String categoria);
}
