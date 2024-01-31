package com.abel.bibliotecapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.abel.bibliotecapp.model.Book;
import com.abel.bibliotecapp.retrofit.BookApi;
import com.abel.bibliotecapp.retrofit.RetrofitService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarActualizarActivity extends AppCompatActivity {

    TextView titulo;
    Button guardarLibro;
    TextInputEditText tiNombre, tiDesc, tiAutor, tiEditorial, tiCantidad;
    BookApi bookApi;
    String[] items = {"Astrolabio literario", "Astrolabio informativo", "Espejo de urania literario", "Espejo de urania informativo"
            , "Cometas convidados informativo", "Pasos de luna literario", "Pasos de luna informativo", "Al sol solito literario",
            "Al sol solito informativo"};

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_actualizar);

        autoCompleteTextView = findViewById(R.id.txtCategoria);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, items);
        autoCompleteTextView.setAdapter(adapterItems);

        tiNombre = findViewById(R.id.txtNombre);
        tiDesc = findViewById(R.id.txtDesc);
        tiAutor = findViewById(R.id.txtAutor);
        tiEditorial = findViewById(R.id.txtEditorial);
        tiCantidad = findViewById(R.id.txtCantidad);

        titulo = findViewById(R.id.tvTitulo);

        RetrofitService retrofitService = new RetrofitService();
        bookApi = retrofitService.getRetrofit().create(BookApi.class);

        guardarLibro = findViewById(R.id.btnGuardarLibro);

        Bundle bundle = getIntent().getExtras();
        String accion = bundle.getString("accion");

        if (accion.equals("actualizar")){
            Book book = (Book) getIntent().getSerializableExtra("book");
            tiNombre.setText(book.getNombre());
            tiDesc.setText(book.getDescripcion());
            tiCantidad.setText(String.valueOf(book.getCantidad()));
            tiAutor.setText(book.getAutor());
            tiEditorial.setText(book.getEditorial());
            autoCompleteTextView.setText(book.getCategoria());

            titulo.setText("Actualizar libro");
        }

        guardarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (accion.equals("guardar")) {
                    guardarLibros();
                }else if (accion.equals("actualizar")){
                    Book book = (Book) getIntent().getSerializableExtra("book");
                    Long id = book.getBookId();
                    actualizarLibros(id);
                }


            }
        });

    }

    private void actualizarLibros(Long id) {
        String name = tiNombre.getText().toString();
        String descr = tiDesc.getText().toString();
        String cantidadStr = tiCantidad.getText().toString();
        String autor = tiAutor.getText().toString();
        String editorial = tiEditorial.getText().toString();
        String categoria = autoCompleteTextView.getText().toString();

        if (name.isEmpty() || descr.isEmpty() || cantidadStr.isEmpty() || autor.isEmpty() ||
                editorial.isEmpty() || categoria.isEmpty()) {
            Toast.makeText(AgregarActualizarActivity.this, "Debes llenar todos los campos", Toast.LENGTH_LONG).show();
        } else {

            Long cantidad = Long.parseLong(cantidadStr);

            Book book = new Book();
            book.setBookId(id);
            book.setNombre(name);
            book.setDescripcion(descr);
            book.setCantidad(cantidad);
            book.setCategoria(categoria);
            book.setEditorial(editorial);
            book.setAutor(autor);

            System.out.println(name);
            System.out.println(descr);

            System.out.println(cantidad);
            System.out.println(autor);
            System.out.println(editorial);
            System.out.println(categoria);

            bookApi.save(book)
                    .enqueue(new Callback<Book>() {
                        @Override
                        public void onResponse(Call<Book> call, Response<Book> response) {
                            Toast.makeText(AgregarActualizarActivity.this, "Guardado listo", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<Book> call, Throwable t) {
                            Toast.makeText(AgregarActualizarActivity.this, "Guardado fallo", Toast.LENGTH_LONG).show();
                            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, "Error", t);
                            System.out.println("ERROR: " + t);
                        }
                    });
        }
    }

    public void guardarLibros() {
        String name = tiNombre.getText().toString();
        String descr = tiDesc.getText().toString();
        String cantidadStr = tiCantidad.getText().toString();
        String autor = tiAutor.getText().toString();
        String editorial = tiEditorial.getText().toString();
        String categoria = autoCompleteTextView.getText().toString();

        if (name.isEmpty() || descr.isEmpty() || cantidadStr.isEmpty() || autor.isEmpty() ||
                editorial.isEmpty() || categoria.isEmpty()) {
            Toast.makeText(AgregarActualizarActivity.this, "Debes llenar todos los campos", Toast.LENGTH_LONG).show();
        } else {

            Long cantidad = Long.parseLong(cantidadStr);

            Book book = new Book();
            book.setNombre(name);
            book.setDescripcion(descr);
            book.setCantidad(cantidad);
            book.setCategoria(categoria);
            book.setEditorial(editorial);
            book.setAutor(autor);

            System.out.println(name);
            System.out.println(descr);

            System.out.println(cantidad);
            System.out.println(autor);
            System.out.println(editorial);
            System.out.println(categoria);

            bookApi.save(book)
                    .enqueue(new Callback<Book>() {
                        @Override
                        public void onResponse(Call<Book> call, Response<Book> response) {
                            Toast.makeText(AgregarActualizarActivity.this, "Guardado listo", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<Book> call, Throwable t) {
                            Toast.makeText(AgregarActualizarActivity.this, "Guardado fallo", Toast.LENGTH_LONG).show();
                            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, "Error", t);
                            System.out.println("ERROR: " + t);
                        }
                    });
        }
    }
}