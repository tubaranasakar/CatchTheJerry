package com.tuba.catchthejerry;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    /*setVisibility() metodu, Android uygulamalarında bir UI elemanının görünürlüğünü ayarlamak için kullanılan bir metottur.
    Bu metot, bir View nesnesinin görünmez veya görünür olmasını sağlar.
    Bu metot, View sınıfının bir yöntemidir ve bir tamsayı tipi olan visibility parametresi alır.
    Bu parametre, bir elemanın görünürlüğünü kontrol etmek için kullanılır ve üç farklı değere sahiptir:
    View.VISIBLE : Bu değer, bir elemanın tamamen görünür olmasını sağlar.
    View.INVISIBLE : Bu değer, bir elemanın görünmez hale getirir, ancak elemanın yer tutmasına izin verir.
    Yani, görüntülenmez ancak uygulanmaya devam eder.
    View.GONE : Bu değer, bir elemanın tamamen yok olmasını sağlar. Eleman yok olduğundan, yer tutmaz.
    Örneğin, myView.setVisibility(View.INVISIBLE) kullanarak bir View nesnesini görünmez hale getirebilirsiniz.*/

    TextView scoreText;
    TextView timeText;
    int score;
    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView[] imageArray; //imageView leri diziye atamak için tanımladık

    Handler handler;
    Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeText=findViewById(R.id.timeText);
        scoreText=findViewById(R.id.scoreText);

        imageView=findViewById(R.id.imageView);
        imageView2=findViewById(R.id.imageView2);
        imageView3=findViewById(R.id.imageView3);
        imageView4=findViewById(R.id.imageView4);
        imageView5=findViewById(R.id.imageView5);
        imageView6=findViewById(R.id.imageView6);
        imageView7=findViewById(R.id.imageView7);
        imageView8=findViewById(R.id.imageView8);
        imageView9=findViewById(R.id.imageView9);

        imageArray=new ImageView[] {imageView,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9}; //diziye atadık

        hideImages(); //uygulama açılır açılmaz imageları saklar

        score=0;

        new CountDownTimer(20000, 1000) { //time geri sayma işlemi
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Time: "+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                timeText.setText("Time Off");
                handler.removeCallbacks(runnable);//rastgele değişmesi durduruldu

                for (ImageView image:imageArray){//imageArraydeki her elemanı image olarak tanımladık
                    image.setVisibility(View.INVISIBLE);//görünmezliği sağladık
                }
                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Do you want to play again?");
                alert.setMessage("Your Score: "+score);
                alert.setPositiveButton("YES!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //baştan
                        Intent intent=getIntent();//uygulamayı baştan açmak için intent kullanıldı
                        finish();//önce kapatır
                        startActivity(intent);//sonra açar

                    }
                });
                alert.setNegativeButton("NO!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(MainActivity.this,"Game  Over!..",Toast.LENGTH_LONG).show();//Toast mesage verildi
                    }
                });
                alert.show();

            }
        }.start();

    }
    public void increaseScore(View view){ //Xml kısmında her imageview e increaseScore onclik tanımlandı
        //burada ise her tıklanışta scorun arttırılması işlemi yapıldı

        score++;
        scoreText.setText("Score: "+score);

    }
    public void hideImages(){ //nesneleri sakla

        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                for (ImageView image:imageArray){//imageArraydeki her elemanı image olarak tanımladık
                    image.setVisibility(View.INVISIBLE);//görünmezliği sağladık
                }
                Random random=new Random(); //random fonksiyonu
                int i= random.nextInt(9);//9 image var
                imageArray[i].setVisibility(View.VISIBLE);//random gelen imageViewi görünür yap

                handler.postDelayed(this,600);//7 milisaniyede bir değiştir
            }
        };

        handler.post(runnable);

    }


}