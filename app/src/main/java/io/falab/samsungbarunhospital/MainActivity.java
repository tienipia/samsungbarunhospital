package io.falab.samsungbarunhospital;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    private ViewFlipper mViewFlipper;

    private ImageView[] imageButtons;

    private Button buttonLeft;

    private Button buttonRight;

    private long last_clicked_left = 0L;

    private long last_clicked_right = 0L;

    private int counterLeft = 0;

    private int counterRight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButtons = new ImageView[5];

        imageButtons[0] = findViewById(R.id.imageButton1);
        imageButtons[1] = findViewById(R.id.imageButton2);
        imageButtons[2] = findViewById(R.id.imageButton3);
        imageButtons[3] = findViewById(R.id.imageButton4);
        imageButtons[4] = findViewById(R.id.imageButton5);

        imageButtons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(MainActivity.this, "com.google.android.youtube");
            }
        });
        imageButtons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startNewActivity(MainActivity.this, "kr.co.captv.pooqV2");
                startNewActivity(MainActivity.this, "com.movienet.touch.wavveon");
            }
        });
        imageButtons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("http://samsungbarunhospital.com");
            }
        });
        imageButtons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.naver.com");

            }
        });
        imageButtons[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openWebPage("https://blog.naver.com/samsungbarunhospital");
            }
        });

        buttonLeft = findViewById(R.id.btnLeft);
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public synchronized void onClick(View v) {
                long cur = System.currentTimeMillis();

                if ((cur - last_clicked_left) < 500) {
                    counterLeft = counterLeft + 1;
                } else {
                    counterLeft = 1;
                }

                if (counterLeft > 3) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    final EditText input = new EditText(MainActivity.this);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder.setView(input);
                    builder.setTitle("관리자 모드");
                    builder.setMessage("관리자 비밀번호를 입력해주세요.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if ("1872".equals(input.getText().toString())) {
                                        Toast.makeText(getApplicationContext(), "설정 진입", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                    builder.setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    last_clicked_left = 0;
                                    counterLeft = 0;
                                }
                            });
                    builder.show();
                } else {
                    last_clicked_left = cur;
                }
            }
        });


        buttonRight = findViewById(R.id.btnRight);
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public synchronized void onClick(View v) {
                long cur = System.currentTimeMillis();

                if ((cur - last_clicked_right) < 500) {
                    counterRight = counterRight + 1;
                } else {
                    counterRight = 1;
                }

                if (counterRight > 3) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    final EditText input = new EditText(MainActivity.this);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder.setView(input);
                    builder.setTitle("런처 실행");
                    builder.setMessage("관리자 비밀번호를 입력해주세요.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if ("1872".equals(input.getText().toString())) {
                                        startNewActivity(MainActivity.this, "com.android.launcher3");
                                    } else {
                                        Toast.makeText(getApplicationContext(), "비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                    builder.setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    last_clicked_right = 0;
                                    counterRight = 0;
                                }
                            });
                    builder.show();
                } else {
                    last_clicked_right = cur;
                }

            }
        });

        mViewFlipper = findViewById(R.id.view_flipper);

        Animation inAnim = new AlphaAnimation(0f, 1f);
        inAnim.setDuration(300);
        mViewFlipper.setInAnimation(inAnim);

        Animation outAnim = new AlphaAnimation(1f, 0f);
        outAnim.setDuration(300);
        mViewFlipper.setOutAnimation(outAnim);

        mViewFlipper.startFlipping();
        mViewFlipper.setFlipInterval(60000);
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void startNewActivity(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            // We found the activity now start the activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + packageName));
            context.startActivity(intent);
        }
    }
}