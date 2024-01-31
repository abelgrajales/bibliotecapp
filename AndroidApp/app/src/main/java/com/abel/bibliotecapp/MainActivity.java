package com.abel.bibliotecapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.abel.bibliotecapp.adapter.BookAdapter;
import com.abel.bibliotecapp.model.Book;
import com.abel.bibliotecapp.retrofit.BookApi;
import com.abel.bibliotecapp.retrofit.RetrofitService;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static File dir = new File(new File(Environment.getExternalStorageDirectory(), "bleh"), "bleh");
    private RecyclerView recyclerView;
    private static final int STORAGE_PERMISSION_CODE = 23;
    Button agregar, exportar;

    List<Book> librosAstroLit = new ArrayList<>();
    List<Book> librosAstroInf = new ArrayList<>();
    List<Book> librosEspejoLit = new ArrayList<>();
    List<Book> librosEspejoInf = new ArrayList<>();
    List<Book> librosCometasInf = new ArrayList<>();
    List<Book> librosPasosLit = new ArrayList<>();
    List<Book> librosPasosinf = new ArrayList<>();
    List<Book> librosAlsolLit = new ArrayList<>();
    List<Book> librosAlsolInf = new ArrayList<>();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.bookRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        agregar = findViewById(R.id.btnAgregarLibro);
        exportar = findViewById(R.id.btnExportar);

        loadBooks();
        askForPermissions();
        getBooksByCat();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AgregarActualizarActivity.class);
                intent.putExtra("accion", "guardar");
                startActivity(intent);
            }
        });

        exportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                exportarLibros();
            }
        });

    }

    public void askForPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
                return;
            }
            createDir();
        }
    }

    public void createDir(){
        if (!dir.exists()){
            dir.mkdirs();
        }
    }

    private void exportarLibros() {
        String astroLit = "Astrolabio Literario";
        String astroInf = "Astrolabio Informativo";
        String espejoLit = "Espejo de Urania Literario";
        String espejoInf = "Espejo de Urania Informativo";
        String cometasInf = "Cometas convidados Informativo";
        String pasosLit = "Pasos de luna Literario";
        String pasosInf = "Pasos de luna Informativo";
        String alsolLit = "Al sol solito Literario";
        String alsolInf = "Al sol solito Informativo";

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet(astroLit);

        HSSFRow hssfRow = hssfSheet.createRow(0);
        HSSFCell hssfCell = hssfRow.createCell(0);
        hssfCell.setCellValue("Cantidad");

        hssfCell = hssfRow.createCell(1);
        hssfCell.setCellValue("Nombre del libro");

        hssfCell = hssfRow.createCell(2);
        hssfCell.setCellValue("Editorial");

        hssfCell = hssfRow.createCell(3);
        hssfCell.setCellValue("Autor");

        hssfCell = hssfRow.createCell(4);
        hssfCell.setCellValue("Categoria");

        hssfCell = hssfRow.createCell(5);
        hssfCell.setCellValue("Descripcion");

        //llenar Astrolabio Literario
        for (int i = 0; i < librosAstroLit.size(); i++) {
            hssfRow = hssfSheet.createRow(i+1);

            hssfCell = hssfRow.createCell(0);
            hssfCell.setCellValue(librosAstroLit.get(i).getCantidad());

            hssfCell = hssfRow.createCell(1);
            hssfCell.setCellValue(librosAstroLit.get(i).getNombre());

            hssfCell = hssfRow.createCell(2);
            hssfCell.setCellValue(librosAstroLit.get(i).getEditorial());

            hssfCell = hssfRow.createCell(3);
            hssfCell.setCellValue(librosAstroLit.get(i).getAutor());

            hssfCell = hssfRow.createCell(4);
            hssfCell.setCellValue(librosAstroLit.get(i).getCategoria());

            hssfCell = hssfRow.createCell(5);
            hssfCell.setCellValue(librosAstroLit.get(i).getDescripcion());
        }

        //llenar Astrolabio informativo
        hssfSheet = hssfWorkbook.createSheet(astroInf);
        hssfRow = hssfSheet.createRow(0);
        hssfCell = hssfRow.createCell(0);
        hssfCell.setCellValue("Cantidad");

        hssfCell = hssfRow.createCell(1);
        hssfCell.setCellValue("Nombre del libro");

        hssfCell = hssfRow.createCell(2);
        hssfCell.setCellValue("Editorial");

        hssfCell = hssfRow.createCell(3);
        hssfCell.setCellValue("Autor");

        hssfCell = hssfRow.createCell(4);
        hssfCell.setCellValue("Categoria");

        hssfCell = hssfRow.createCell(5);
        hssfCell.setCellValue("Descripcion");

        for (int i = 0; i < librosAstroInf.size(); i++) {
            hssfRow = hssfSheet.createRow(i+1);

            hssfCell = hssfRow.createCell(0);
            hssfCell.setCellValue(librosAstroInf.get(i).getCantidad());

            hssfCell = hssfRow.createCell(1);
            hssfCell.setCellValue(librosAstroInf.get(i).getNombre());

            hssfCell = hssfRow.createCell(2);
            hssfCell.setCellValue(librosAstroInf.get(i).getEditorial());

            hssfCell = hssfRow.createCell(3);
            hssfCell.setCellValue(librosAstroInf.get(i).getAutor());

            hssfCell = hssfRow.createCell(4);
            hssfCell.setCellValue(librosAstroInf.get(i).getCategoria());

            hssfCell = hssfRow.createCell(5);
            hssfCell.setCellValue(librosAstroInf.get(i).getDescripcion());
        }

        //llenar Espejo de Urania Literario
        hssfSheet = hssfWorkbook.createSheet(espejoLit);
        hssfRow = hssfSheet.createRow(0);
        hssfCell = hssfRow.createCell(0);
        hssfCell.setCellValue("Cantidad");

        hssfCell = hssfRow.createCell(1);
        hssfCell.setCellValue("Nombre del libro");

        hssfCell = hssfRow.createCell(2);
        hssfCell.setCellValue("Editorial");

        hssfCell = hssfRow.createCell(3);
        hssfCell.setCellValue("Autor");

        hssfCell = hssfRow.createCell(4);
        hssfCell.setCellValue("Categoria");

        hssfCell = hssfRow.createCell(5);
        hssfCell.setCellValue("Descripcion");

        for (int i = 0; i < librosEspejoLit.size(); i++) {
            hssfRow = hssfSheet.createRow(i+1);

            hssfCell = hssfRow.createCell(0);
            hssfCell.setCellValue(librosEspejoLit.get(i).getCantidad());

            hssfCell = hssfRow.createCell(1);
            hssfCell.setCellValue(librosEspejoLit.get(i).getNombre());

            hssfCell = hssfRow.createCell(2);
            hssfCell.setCellValue(librosEspejoLit.get(i).getEditorial());

            hssfCell = hssfRow.createCell(3);
            hssfCell.setCellValue(librosEspejoLit.get(i).getAutor());

            hssfCell = hssfRow.createCell(4);
            hssfCell.setCellValue(librosEspejoLit.get(i).getCategoria());

            hssfCell = hssfRow.createCell(5);
            hssfCell.setCellValue(librosEspejoLit.get(i).getDescripcion());
        }

        //llenar Espejo de Urania Informativo
        hssfSheet = hssfWorkbook.createSheet(espejoInf);
        hssfRow = hssfSheet.createRow(0);
        hssfCell = hssfRow.createCell(0);
        hssfCell.setCellValue("Cantidad");

        hssfCell = hssfRow.createCell(1);
        hssfCell.setCellValue("Nombre del libro");

        hssfCell = hssfRow.createCell(2);
        hssfCell.setCellValue("Editorial");

        hssfCell = hssfRow.createCell(3);
        hssfCell.setCellValue("Autor");

        hssfCell = hssfRow.createCell(4);
        hssfCell.setCellValue("Categoria");

        hssfCell = hssfRow.createCell(5);
        hssfCell.setCellValue("Descripcion");

        for (int i = 0; i < librosEspejoInf.size(); i++) {
            hssfRow = hssfSheet.createRow(i+1);

            hssfCell = hssfRow.createCell(0);
            hssfCell.setCellValue(librosEspejoInf.get(i).getCantidad());

            hssfCell = hssfRow.createCell(1);
            hssfCell.setCellValue(librosEspejoInf.get(i).getNombre());

            hssfCell = hssfRow.createCell(2);
            hssfCell.setCellValue(librosEspejoInf.get(i).getEditorial());

            hssfCell = hssfRow.createCell(3);
            hssfCell.setCellValue(librosEspejoInf.get(i).getAutor());

            hssfCell = hssfRow.createCell(4);
            hssfCell.setCellValue(librosEspejoInf.get(i).getCategoria());

            hssfCell = hssfRow.createCell(5);
            hssfCell.setCellValue(librosEspejoInf.get(i).getDescripcion());
        }

        //llenar Cometas convidados informativo
        hssfSheet = hssfWorkbook.createSheet(cometasInf);
        hssfRow = hssfSheet.createRow(0);
        hssfCell = hssfRow.createCell(0);
        hssfCell.setCellValue("Cantidad");

        hssfCell = hssfRow.createCell(1);
        hssfCell.setCellValue("Nombre del libro");

        hssfCell = hssfRow.createCell(2);
        hssfCell.setCellValue("Editorial");

        hssfCell = hssfRow.createCell(3);
        hssfCell.setCellValue("Autor");

        hssfCell = hssfRow.createCell(4);
        hssfCell.setCellValue("Categoria");

        hssfCell = hssfRow.createCell(5);
        hssfCell.setCellValue("Descripcion");

        for (int i = 0; i < librosCometasInf.size(); i++) {
            hssfRow = hssfSheet.createRow(i+1);

            hssfCell = hssfRow.createCell(0);
            hssfCell.setCellValue(librosCometasInf.get(i).getCantidad());

            hssfCell = hssfRow.createCell(1);
            hssfCell.setCellValue(librosCometasInf.get(i).getNombre());

            hssfCell = hssfRow.createCell(2);
            hssfCell.setCellValue(librosCometasInf.get(i).getEditorial());

            hssfCell = hssfRow.createCell(3);
            hssfCell.setCellValue(librosCometasInf.get(i).getAutor());

            hssfCell = hssfRow.createCell(4);
            hssfCell.setCellValue(librosCometasInf.get(i).getCategoria());

            hssfCell = hssfRow.createCell(5);
            hssfCell.setCellValue(librosCometasInf.get(i).getDescripcion());
        }

        //llenar Pasos de luna literario
        hssfSheet = hssfWorkbook.createSheet(pasosLit);
        hssfRow = hssfSheet.createRow(0);
        hssfCell = hssfRow.createCell(0);
        hssfCell.setCellValue("Cantidad");

        hssfCell = hssfRow.createCell(1);
        hssfCell.setCellValue("Nombre del libro");

        hssfCell = hssfRow.createCell(2);
        hssfCell.setCellValue("Editorial");

        hssfCell = hssfRow.createCell(3);
        hssfCell.setCellValue("Autor");

        hssfCell = hssfRow.createCell(4);
        hssfCell.setCellValue("Categoria");

        hssfCell = hssfRow.createCell(5);
        hssfCell.setCellValue("Descripcion");

        for (int i = 0; i < librosPasosLit.size(); i++) {
            hssfRow = hssfSheet.createRow(i+1);

            hssfCell = hssfRow.createCell(0);
            hssfCell.setCellValue(librosPasosLit.get(i).getCantidad());

            hssfCell = hssfRow.createCell(1);
            hssfCell.setCellValue(librosPasosLit.get(i).getNombre());

            hssfCell = hssfRow.createCell(2);
            hssfCell.setCellValue(librosPasosLit.get(i).getEditorial());

            hssfCell = hssfRow.createCell(3);
            hssfCell.setCellValue(librosPasosLit.get(i).getAutor());

            hssfCell = hssfRow.createCell(4);
            hssfCell.setCellValue(librosPasosLit.get(i).getCategoria());

            hssfCell = hssfRow.createCell(5);
            hssfCell.setCellValue(librosPasosLit.get(i).getDescripcion());
        }

        //llenar Pasos de luna informativo
        hssfSheet = hssfWorkbook.createSheet(pasosInf);
        hssfRow = hssfSheet.createRow(0);
        hssfCell = hssfRow.createCell(0);
        hssfCell.setCellValue("Cantidad");

        hssfCell = hssfRow.createCell(1);
        hssfCell.setCellValue("Nombre del libro");

        hssfCell = hssfRow.createCell(2);
        hssfCell.setCellValue("Editorial");

        hssfCell = hssfRow.createCell(3);
        hssfCell.setCellValue("Autor");

        hssfCell = hssfRow.createCell(4);
        hssfCell.setCellValue("Categoria");

        hssfCell = hssfRow.createCell(5);
        hssfCell.setCellValue("Descripcion");

        for (int i = 0; i < librosPasosinf.size(); i++) {
            hssfRow = hssfSheet.createRow(i+1);

            hssfCell = hssfRow.createCell(0);
            hssfCell.setCellValue(librosPasosinf.get(i).getCantidad());

            hssfCell = hssfRow.createCell(1);
            hssfCell.setCellValue(librosPasosinf.get(i).getNombre());

            hssfCell = hssfRow.createCell(2);
            hssfCell.setCellValue(librosPasosinf.get(i).getEditorial());

            hssfCell = hssfRow.createCell(3);
            hssfCell.setCellValue(librosPasosinf.get(i).getAutor());

            hssfCell = hssfRow.createCell(4);
            hssfCell.setCellValue(librosPasosinf.get(i).getCategoria());

            hssfCell = hssfRow.createCell(5);
            hssfCell.setCellValue(librosPasosinf.get(i).getDescripcion());
        }

        //llenar Al sol solito literario
        hssfSheet = hssfWorkbook.createSheet(alsolLit);
        hssfRow = hssfSheet.createRow(0);
        hssfCell = hssfRow.createCell(0);
        hssfCell.setCellValue("Cantidad");

        hssfCell = hssfRow.createCell(1);
        hssfCell.setCellValue("Nombre del libro");

        hssfCell = hssfRow.createCell(2);
        hssfCell.setCellValue("Editorial");

        hssfCell = hssfRow.createCell(3);
        hssfCell.setCellValue("Autor");

        hssfCell = hssfRow.createCell(4);
        hssfCell.setCellValue("Categoria");

        hssfCell = hssfRow.createCell(5);
        hssfCell.setCellValue("Descripcion");

        for (int i = 0; i < librosAlsolLit.size(); i++) {
            hssfRow = hssfSheet.createRow(i+1);

            hssfCell = hssfRow.createCell(0);
            hssfCell.setCellValue(librosAlsolLit.get(i).getCantidad());

            hssfCell = hssfRow.createCell(1);
            hssfCell.setCellValue(librosAlsolLit.get(i).getNombre());

            hssfCell = hssfRow.createCell(2);
            hssfCell.setCellValue(librosAlsolLit.get(i).getEditorial());

            hssfCell = hssfRow.createCell(3);
            hssfCell.setCellValue(librosAlsolLit.get(i).getAutor());

            hssfCell = hssfRow.createCell(4);
            hssfCell.setCellValue(librosAlsolLit.get(i).getCategoria());

            hssfCell = hssfRow.createCell(5);
            hssfCell.setCellValue(librosAlsolLit.get(i).getDescripcion());
        }

        //llenar Al sol solito informativo
        hssfSheet = hssfWorkbook.createSheet(alsolInf);
        hssfRow = hssfSheet.createRow(0);
        hssfCell = hssfRow.createCell(0);
        hssfCell.setCellValue("Cantidad");

        hssfCell = hssfRow.createCell(1);
        hssfCell.setCellValue("Nombre del libro");

        hssfCell = hssfRow.createCell(2);
        hssfCell.setCellValue("Editorial");

        hssfCell = hssfRow.createCell(3);
        hssfCell.setCellValue("Autor");

        hssfCell = hssfRow.createCell(4);
        hssfCell.setCellValue("Categoria");

        hssfCell = hssfRow.createCell(5);
        hssfCell.setCellValue("Descripcion");

        for (int i = 0; i < librosAlsolInf.size(); i++) {
            hssfRow = hssfSheet.createRow(i+1);

            hssfCell = hssfRow.createCell(0);
            hssfCell.setCellValue(librosAlsolInf.get(i).getCantidad());

            hssfCell = hssfRow.createCell(1);
            hssfCell.setCellValue(librosAlsolInf.get(i).getNombre());

            hssfCell = hssfRow.createCell(2);
            hssfCell.setCellValue(librosAlsolInf.get(i).getEditorial());

            hssfCell = hssfRow.createCell(3);
            hssfCell.setCellValue(librosAlsolInf.get(i).getAutor());

            hssfCell = hssfRow.createCell(4);
            hssfCell.setCellValue(librosAlsolInf.get(i).getCategoria());

            hssfCell = hssfRow.createCell(5);
            hssfCell.setCellValue(librosAlsolInf.get(i).getDescripcion());
        }


        File filePath = new File(Environment.getExternalStorageDirectory() + "/Libros.xls");

        try{
            if (!filePath.exists()){
                filePath.createNewFile();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            hssfWorkbook.write(fileOutputStream);

            if (fileOutputStream!=null){
                fileOutputStream.flush();
                fileOutputStream.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void loadBooks() {
        RetrofitService retrofitService = new RetrofitService();
        BookApi bookApi = retrofitService.getRetrofit().create(BookApi.class);

        bookApi.getAllBooks()
                .enqueue(new Callback<List<Book>>() {
                    @Override
                    public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Book>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Fallo al cargar los empleados", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void populateListView(List<Book> bookList) {
        BookAdapter bookAdapter = new BookAdapter(MainActivity.this, bookList);
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();
    }

    public void getBooksByCat(){
        RetrofitService retrofitService = new RetrofitService();
        BookApi bookApi = retrofitService.getRetrofit().create(BookApi.class);

        bookApi.getByCategoria("Astrolabio literario").enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                librosAstroLit = response.body();
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR: " + t, Toast.LENGTH_SHORT).show();
            }
        });

        bookApi.getByCategoria("Astrolabio informativo").enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                librosAstroInf = response.body();

            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR: " + t, Toast.LENGTH_SHORT).show();
            }
        });

        bookApi.getByCategoria("Espejo de urania literario").enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                librosEspejoLit = response.body();

            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR: " + t, Toast.LENGTH_SHORT).show();
            }
        });

        bookApi.getByCategoria("Espejo de urania informativo").enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                librosEspejoInf = response.body();
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR: " + t, Toast.LENGTH_SHORT).show();
            }
        });

        bookApi.getByCategoria("Cometas convidados informativo").enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                librosCometasInf = response.body();

            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR: " + t, Toast.LENGTH_SHORT).show();
            }
        });
        bookApi.getByCategoria("Pasos de luna literario").enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                librosPasosLit = response.body();

            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR: " + t, Toast.LENGTH_SHORT).show();
            }
        });

        bookApi.getByCategoria("Pasos de luna informativo").enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                librosPasosinf = response.body();

            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR: " + t, Toast.LENGTH_SHORT).show();
            }
        });

        bookApi.getByCategoria("Al sol solito literario").enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                librosAlsolLit = response.body();

            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR: " + t, Toast.LENGTH_SHORT).show();
            }
        });

        bookApi.getByCategoria("Al sol solito informativo").enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                librosAlsolInf = response.body();

            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR: " + t, Toast.LENGTH_SHORT).show();
            }
        });

    }

}