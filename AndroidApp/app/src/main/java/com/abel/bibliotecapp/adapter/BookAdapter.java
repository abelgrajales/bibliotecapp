package com.abel.bibliotecapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abel.bibliotecapp.AgregarActualizarActivity;
import com.abel.bibliotecapp.MainActivity;
import com.abel.bibliotecapp.R;
import com.abel.bibliotecapp.model.Book;
import com.abel.bibliotecapp.retrofit.BookApi;
import com.abel.bibliotecapp.retrofit.RetrofitService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAdapter extends RecyclerView.Adapter<BookHolder> {

    Context context;
    private List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_book_item, parent, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        Book book = bookList.get(position);
        holder.name.setText(book.getNombre());
        holder.descripcion.setText(book.getDescripcion());
        holder.autor.setText("Autor: " + book.getAutor());
        holder.editorial.setText("Editorial: " + book.getEditorial());
        holder.categoria.setText("Categoria: " + book.getCategoria());
        holder.cantidad.setText("Cantidad: " + book.getCantidad());

        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AgregarActualizarActivity.class);
                intent.putExtra("accion", "actualizar");
                intent.putExtra("book", book);
                context.startActivity(intent);

            }
        });

        holder.borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = bookList.get(position);
                RetrofitService retrofitService = new RetrofitService();
                BookApi bookApi = retrofitService.getRetrofit().create(BookApi.class);
                bookApi.delete(book.getBookId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(context, "Borrado listo", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context, "Borrar fallo", Toast.LENGTH_LONG).show();
                        Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, "Error", t);
                        System.out.println("ERROR: " + t);
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
