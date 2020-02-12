package com.logo3d.logomaker.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.text.Layout.Alignment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.logo3d.logomaker.R;
import com.logo3d.logomaker.share.Share;
import com.logo3d.logomaker.utilities.Save;
import com.xiaopo.sticker.DrawableSticker;
import com.xiaopo.sticker.StickerView;
import com.xiaopo.sticker.TextSticker;

import java.io.File;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;

public class ActivityCreatingLogo extends AppCompatActivity implements OnClickListener {

    LinearLayout add_bg;
    LinearLayout add_color;
    LinearLayout add_font;
    LinearLayout add_logo;
    LinearLayout add_text;
    LinearLayout addshapes;
    Button cancel;
    ImageView clearData;
    HorizontalScrollView infoHorizentallayout;
    LinearLayout infoLayout;
    LinearLayout lock;
    RelativeLayout mainlayout;

    Button f74ok;
    ImageView save;
    ImageView share;
    StickerView stickerView;
    EditText text_input;
    RelativeLayout text_input_layout;
    boolean visibleitem = false;

    class C04161 implements DialogInterface.OnClickListener {
        C04161() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            ActivityCreatingLogo.this.finish();
        }
    }

    class C04172 implements DialogInterface.OnClickListener {
        C04172() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.create_logo);

        setRequestedOrientation(1);
        getWindow().setSoftInputMode(48);

        this.stickerView = (StickerView) findViewById(R.id.sticker_view);
        this.add_text = (LinearLayout) findViewById(R.id.addtext);
        this.add_text.setOnClickListener(this);
        this.add_font = (LinearLayout) findViewById(R.id.addfont);
        this.add_font.setOnClickListener(this);
        this.add_color = (LinearLayout) findViewById(R.id.colors);
        this.add_color.setOnClickListener(this);
        this.add_bg = (LinearLayout) findViewById(R.id.background);
        this.add_bg.setOnClickListener(this);
        this.add_logo = (LinearLayout) findViewById(R.id.logos);
        this.add_logo.setOnClickListener(this);
        this.addshapes = (LinearLayout) findViewById(R.id.shapes);
        this.addshapes.setOnClickListener(this);
        this.lock = (LinearLayout) findViewById(R.id.lock);
        this.lock.setOnClickListener(this);
        this.save = (ImageView) findViewById(R.id.save);
        this.save.setOnClickListener(this);
        //this.share = (ImageView) findViewById(R.id.share);
       // this.share.setOnClickListener(this);
        this.clearData = (ImageView) findViewById(R.id.clearitems);
        this.clearData.setOnClickListener(this);
        this.infoHorizentallayout = (HorizontalScrollView) findViewById(R.id.allinfolayout);
        this.infoLayout = (LinearLayout) findViewById(R.id.info);
        this.mainlayout = (RelativeLayout) findViewById(R.id.mainlayout);
        this.mainlayout.setOnClickListener(this);
       
    }



    public void addTextInfo() {

        this.text_input_layout = (RelativeLayout) findViewById(R.id.addtextlayout);
        this.text_input_layout.setVisibility(0);
        this.text_input = (EditText) findViewById(R.id.textarea);
        this.f74ok = (Button) findViewById(R.id.add);
        this.cancel = (Button) findViewById(R.id.cancel);
        this.f74ok.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ActivityCreatingLogo.this.text_input.getText().toString().length() > 0) {
                    TextSticker textSticker = new TextSticker(ActivityCreatingLogo.this);
                    textSticker.setText(ActivityCreatingLogo.this.text_input.getText().toString());
                    textSticker.setTextAlign(Alignment.ALIGN_CENTER);
                    textSticker.resizeText();
                    textSticker.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    ActivityCreatingLogo.this.stickerView.addSticker(textSticker);
                    ActivityCreatingLogo.this.stickerView.invalidate();
                    ActivityCreatingLogo.this.text_input.setText("");
                    ActivityCreatingLogo.this.text_input_layout.setVisibility(8);
                    return;
                }
                Toast.makeText(ActivityCreatingLogo.this, "No Input", 0).show();
            }
        });

        this.cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivityCreatingLogo.this.text_input.setText("");
                ActivityCreatingLogo.this.text_input_layout.setVisibility(8);
            }
        });

    }

    public void addFontText() {
        this.infoLayout.removeAllViews();
        String str = "f66.ttf";
        String str2 = "f78.ttf";
        final String[] strArr = {"f1.ttf", "f2.ttf", "f3.ttf", "f4.ttf", "f5.ttf", "f6.ttf", "f7.ttf", "f8.ttf", "f9.ttf", "f10.ttf", "f11.ttf", "f12.ttf", "f13.ttf", "f14.ttf", "f15.ttf", "f16.ttf", "f17.ttf", "f18.ttf", "f19.ttf", "f20.ttf", "f21.ttf", "f22.ttf", "f23.ttf", "f24.ttf", "f25.ttf", "f26.ttf", "f27.ttf", "f28.ttf", "f29.ttf", "f30.ttf", "f31.ttf", "f32.ttf", "f33.ttf", "f34.ttf", "f35.ttf", "f36.ttf", "f37.ttf", "f38.ttf", "f39.ttf", "f40.ttf", "f41.ttf", "f42.ttf", "f43.ttf", "f44.ttf", "f45.ttf", "f46.ttf", "f47.ttf", "f48.ttf", "f49.ttf", "f50.ttf", "f51.ttf", "f52.ttf", "f53.ttf", "f54.ttf", "f55.ttf", "f56.ttf", "f57.ttf", "f58.ttf", "f59.ttf", "f60.ttf", "f61.ttf", "f62.ttf", "f63.ttf", "f64.ttf", "f65.ttf", str, str, "f67.ttf", "f68.ttf", "f69.ttf", "f70.ttf", "f71.ttf", "f72.ttf", "f73.ttf", "f74.ttf", "f75.ttf", "f76.ttf", "f77.ttf", str2, str2, "f79.ttf", "f80.ttf", "f81.ttf", "f82.ttf", "f83.ttf", "f84.ttf", "f85.ttf", "f86.ttf", "f88.ttf", "f89.ttf", "f90.ttf", "f91.ttf", "f92.ttf", "f93.ttf", "f94.ttf", "f95.ttf", "f96.ttf", "f97.ttf", "f98.ttf", "f99.ttf", "f100.ttf"};
        this.infoHorizentallayout.setVisibility(0);
        for ( int i = 0; i < strArr.length; i++) {
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LayoutParams(-2, -2));
            textView.setText("Logo Maker");
            textView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            textView.setPadding(5, 0, 0, 5);
            textView.setTypeface(Typeface.createFromAsset(getAssets(), strArr[i]));
            textView.setTextSize(25.0f);
            final int finalI = i;
            textView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Typeface createFromAsset = Typeface.createFromAsset(ActivityCreatingLogo.this.getAssets(), strArr[finalI]);
                    if (ActivityCreatingLogo.this.stickerView.getCurrentSticker() instanceof TextSticker) {
                        ((TextSticker) ActivityCreatingLogo.this.stickerView.getCurrentSticker()).setTypeface(createFromAsset);
                        ActivityCreatingLogo.this.stickerView.invalidate();
                        return;
                    }
                    Toast.makeText(ActivityCreatingLogo.this, "Cant Apply on Image", 0).show();
                }
            });
            this.infoLayout.addView(textView);
        }
    }

    public void loadBackgrounds() {
        this.infoLayout.removeAllViews();
        this.infoHorizentallayout.setVisibility(0);
        final int[] iArr = {R.drawable.bg2, R.drawable.bg3, R.drawable.bg4, R.drawable.bg5, R.drawable.bg6, R.drawable.bg7, R.drawable.bg8, R.drawable.bg9, R.drawable.bg10, R.drawable.bg12, R.drawable.bg13, R.drawable.bg14, R.drawable.bg15, R.drawable.bg16, R.drawable.bg17, R.drawable.bg18, R.drawable.bg19, R.drawable.bg20, R.drawable.bg21, R.drawable.bg22, R.drawable.bg23, R.drawable.bg24, R.drawable.bg25, R.drawable.bg26, R.drawable.bg27, R.drawable.bg28, R.drawable.bg29, R.drawable.bg30, R.drawable.bg31, R.drawable.bg32, R.drawable.bg33, R.drawable.bg34, R.drawable.bg35, R.drawable.bg36, R.drawable.bg37, R.drawable.bg38, R.drawable.bg39, R.drawable.bg40, R.drawable.bg41, R.drawable.bg42, R.drawable.bg43, R.drawable.bg44, R.drawable.bg45, R.drawable.bg46, R.drawable.bg47, R.drawable.bg48, R.drawable.bg49, R.drawable.bg50, R.drawable.bg51, R.drawable.bg52, R.drawable.bg53, R.drawable.bg54, R.drawable.bg55, R.drawable.bg56, R.drawable.bg57, R.drawable.bg58, R.drawable.bg59, R.drawable.bg60, R.drawable.bg61, R.drawable.bg62, R.drawable.bg63, R.drawable.bg64, R.drawable.bg65, R.drawable.bg66, R.drawable.bg67, R.drawable.bg68, R.drawable.bg69, R.drawable.bg70, R.drawable.bg71, R.drawable.bg72, R.drawable.bg73, R.drawable.bg74, R.drawable.bg75, R.drawable.bg76, R.drawable.bg77, R.drawable.bg78, R.drawable.bg79, R.drawable.bg80, R.drawable.bg81, R.drawable.bg82, R.drawable.bg83, R.drawable.bg84, R.drawable.bg85, R.drawable.bg86, R.drawable.bg87, R.drawable.bg88, R.drawable.bg89, R.drawable.bg90, R.drawable.bg91, R.drawable.bg92, R.drawable.bg93, R.drawable.bg94, R.drawable.bg95, R.drawable.bg96, R.drawable.bg97, R.drawable.bg98, R.drawable.bg99, R.drawable.bg100};

        int i;
        for ( i = 0; i < iArr.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LayoutParams(80, 80));
            Glide.with((FragmentActivity) this).load(Integer.valueOf(iArr[i])).into(imageView);
            final int finalI = i;
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ActivityCreatingLogo.this.stickerView.setBackgroundResource(iArr[finalI]);
                }
            });
            this.infoLayout.addView(imageView);
        }
    }

    public void loadAllcolors() {

        this.infoLayout.removeAllViews();
        this.infoHorizentallayout.setVisibility(0);
        final int[] intArray = getResources().getIntArray(R.array.allcolors);
        for (int i = 0; i < intArray.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LayoutParams(80, 80));
            imageView.setBackgroundColor(intArray[i]);
            final int finalI = i;
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (ActivityCreatingLogo.this.stickerView.getCurrentSticker() instanceof TextSticker) {
                        ((TextSticker) ActivityCreatingLogo.this.stickerView.getCurrentSticker()).setTextColor(intArray[finalI]);
                        ActivityCreatingLogo.this.stickerView.invalidate();
                    } else if (ActivityCreatingLogo.this.stickerView.getCurrentSticker() instanceof DrawableSticker) {
                        int[] iArr = intArray;

                        ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(new float[]{0.0f, 0.0f, 0.0f, 0.0f, (float) ((iArr[finalI] & 16711680) / SupportMenu.USER_MASK), 0.0f, 0.0f, 0.0f, 0.0f, (float) ((iArr[finalI] & MotionEventCompat.ACTION_POINTER_INDEX_MASK) / 255), 0.0f, 0.0f, 0.0f, 0.0f, (float) (iArr[finalI] & 255), 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
                        Drawable drawable = ((DrawableSticker) ActivityCreatingLogo.this.stickerView.getCurrentSticker()).getDrawable();
                        drawable.setColorFilter(colorMatrixColorFilter);
                        ((DrawableSticker) ActivityCreatingLogo.this.stickerView.getCurrentSticker()).setDrawable(drawable);
                        ActivityCreatingLogo.this.stickerView.replace((DrawableSticker) ActivityCreatingLogo.this.stickerView.getCurrentSticker());
                        ActivityCreatingLogo.this.stickerView.invalidate();
                    } else {
                        Toast.makeText(ActivityCreatingLogo.this, "No item found", 0).show();
                    }
                }
            });
            this.infoLayout.addView(imageView);
        }
    }

    public void loadIShapes() {
        this.infoLayout.removeAllViews();
        this.infoHorizentallayout.setVisibility(0);
        final int[] iArr = {R.drawable.shape_1, R.drawable.shape_2, R.drawable.shape_3, R.drawable.shape_4, R.drawable.shape_5, R.drawable.shape_6, R.drawable.shape_7, R.drawable.shape_8, R.drawable.shape_9, R.drawable.shape_10, R.drawable.shape_11, R.drawable.shape_12, R.drawable.shape_13, R.drawable.shape_14, R.drawable.shape_15, R.drawable.shape_16, R.drawable.shape_17, R.drawable.shape_18, R.drawable.shape_19, R.drawable.shape_20, R.drawable.shape_21, R.drawable.shape_22, R.drawable.shape_23, R.drawable.shape_24, R.drawable.shape_25, R.drawable.shape_26, R.drawable.shape_27, R.drawable.shape_28, R.drawable.shape_29, R.drawable.shape_30, R.drawable.shape_31, R.drawable.shape_32, R.drawable.shape_33, R.drawable.shape_34, R.drawable.shape_35, R.drawable.shape_36, R.drawable.shape_37, R.drawable.shape_38, R.drawable.shape_39, R.drawable.shape_40, R.drawable.shape_41, R.drawable.shape_42, R.drawable.shape_43, R.drawable.shape_44, R.drawable.shape_45, R.drawable.shape_46, R.drawable.shape_47, R.drawable.shape_48, R.drawable.shape_49, R.drawable.shape_50, R.drawable.shape_51, R.drawable.shape_52, R.drawable.shape_53, R.drawable.shape_54, R.drawable.shape_55, R.drawable.shape_56, R.drawable.shape_57, R.drawable.shape_58, R.drawable.shape_59, R.drawable.shape_60, R.drawable.shape_61, R.drawable.shape_62, R.drawable.shape_63, R.drawable.shape_64, R.drawable.shape_65, R.drawable.shape_66, R.drawable.shape_67, R.drawable.shape_68, R.drawable.shape_69, R.drawable.shape_70, R.drawable.shape_71, R.drawable.shape_72, R.drawable.shape_73, R.drawable.shape_74, R.drawable.shape_75, R.drawable.shape_76, R.drawable.shape_77, R.drawable.shape_78, R.drawable.shape_79, R.drawable.shape_80, R.drawable.shape_81, R.drawable.shape_82, R.drawable.shape_83, R.drawable.shape_84, R.drawable.shape_85, R.drawable.shape_86, R.drawable.shape_87, R.drawable.shape_88, R.drawable.shape_89, R.drawable.shape_90, R.drawable.shape_91, R.drawable.shape_92, R.drawable.shape_93, R.drawable.shape_94, R.drawable.shape_95, R.drawable.shape_96, R.drawable.shape_97, R.drawable.shape_98, R.drawable.shape_99, R.drawable.shape_100, R.drawable.shape_101, R.drawable.shape_102, R.drawable.shape_103, R.drawable.shape_104, R.drawable.shape_105, R.drawable.shape_106, R.drawable.shape_107, R.drawable.shape_108, R.drawable.shape_109, R.drawable.shape_110, R.drawable.shape_111, R.drawable.shape_112, R.drawable.shape_113, R.drawable.shape_114, R.drawable.shape_115, R.drawable.shape_116, R.drawable.shape_117, R.drawable.shape_118, R.drawable.shape_119, R.drawable.shape_120, R.drawable.shape_121, R.drawable.shape_122, R.drawable.shape_123, R.drawable.shape_124, R.drawable.shape_125, R.drawable.shape_126, R.drawable.shape_127, R.drawable.shape_128, R.drawable.shape_129, R.drawable.shape_130, R.drawable.shape_131, R.drawable.shape_132, R.drawable.shape_133, R.drawable.shape_134, R.drawable.shape_135, R.drawable.shape_136, R.drawable.shape_137, R.drawable.shape_138, R.drawable.shape_139, R.drawable.shape_140, R.drawable.shape_141, R.drawable.shape_142, R.drawable.shape_143, R.drawable.shape_144, R.drawable.shape_145, R.drawable.shape_146, R.drawable.shape_147, R.drawable.shape_148, R.drawable.shape_149, R.drawable.shape_150, R.drawable.shape_151, R.drawable.shape_152, R.drawable.shape_153, R.drawable.shape_154, R.drawable.shape_155, R.drawable.shape_156, R.drawable.shape_157, R.drawable.shape_158, R.drawable.shape_159, R.drawable.shape_160, R.drawable.shape_161, R.drawable.shape_162, R.drawable.shape_163, R.drawable.shape_164, R.drawable.shape_165, R.drawable.shape_166, R.drawable.shape_167, R.drawable.shape_168, R.drawable.shape_169, R.drawable.shape_170, R.drawable.shape_171, R.drawable.shape_172, R.drawable.shape_173, R.drawable.shape_174, R.drawable.shape_175, R.drawable.shape_176, R.drawable.shape_177, R.drawable.shape_178, R.drawable.shape_179, R.drawable.shape_180, R.drawable.shape_181, R.drawable.shape_182, R.drawable.shape_183, R.drawable.shape_184, R.drawable.shape_185, R.drawable.shape_186, R.drawable.shape_187, R.drawable.shape_188, R.drawable.shape_189, R.drawable.shape_190, R.drawable.shape_191, R.drawable.shape_192, R.drawable.shape_193, R.drawable.shape_194, R.drawable.shape_195, R.drawable.shape_196, R.drawable.shape_197, R.drawable.shape_198, R.drawable.shape_199, R.drawable.shape_200};
        for (int i = 0; i < iArr.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LayoutParams(80, 80));
            imageView.setColorFilter(ViewCompat.MEASURED_STATE_MASK);
            Glide.with((FragmentActivity) this).load(Integer.valueOf(iArr[i])).into(imageView);
            final int finalI = i;
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ActivityCreatingLogo.this.stickerView.addSticker(new DrawableSticker(ContextCompat.getDrawable(ActivityCreatingLogo.this, iArr[finalI])));
                    ActivityCreatingLogo.this.stickerView.invalidate();
                }
            });
            this.infoLayout.addView(imageView);
        }
    }

    public void loadLogos() {
        this.infoLayout.removeAllViews();
        this.infoHorizentallayout.setVisibility(0);
        final int[] iArr = {R.drawable.lg1, R.drawable.lg2, R.drawable.lg3, R.drawable.lg4, R.drawable.lg5, R.drawable.lg6, R.drawable.lg7, R.drawable.lg8, R.drawable.lg9, R.drawable.lg10, R.drawable.lg11, R.drawable.lg12, R.drawable.lg13, R.drawable.lg14, R.drawable.lg15, R.drawable.lg16, R.drawable.lg17, R.drawable.lg18, R.drawable.lg19, R.drawable.lg20, R.drawable.lg21, R.drawable.lg22, R.drawable.lg23, R.drawable.lg24, R.drawable.lg25, R.drawable.lg26, R.drawable.lg27, R.drawable.lg28, R.drawable.lg29, R.drawable.lg30, R.drawable.lg31, R.drawable.lg32, R.drawable.lg33, R.drawable.lg34, R.drawable.lg35, R.drawable.lg36, R.drawable.lg37, R.drawable.lg38, R.drawable.lg39, R.drawable.lg40, R.drawable.lg41, R.drawable.lg42, R.drawable.lg43, R.drawable.lg44, R.drawable.lg45, R.drawable.lg46, R.drawable.lg47, R.drawable.lg48, R.drawable.lg49, R.drawable.lg50, R.drawable.lg51, R.drawable.lg52, R.drawable.lg53, R.drawable.lg54, R.drawable.lg55, R.drawable.lg56, R.drawable.lg57, R.drawable.lg58, R.drawable.lg59, R.drawable.lg60, R.drawable.lg61, R.drawable.lg62, R.drawable.lg63, R.drawable.fn1, R.drawable.fn2, R.drawable.fn3, R.drawable.fn4, R.drawable.fn5, R.drawable.fn6, R.drawable.fn7, R.drawable.fn8, R.drawable.fn9, R.drawable.fn10, R.drawable.fn11, R.drawable.fn12, R.drawable.fn13, R.drawable.fn14, R.drawable.fn15, R.drawable.fn16, R.drawable.fn17, R.drawable.fn18, R.drawable.fn19, R.drawable.fn20, R.drawable.fn21, R.drawable.fn22, R.drawable.fn23, R.drawable.fn24, R.drawable.fn25, R.drawable.fn26, R.drawable.fn27, R.drawable.fn28, R.drawable.fn29, R.drawable.fn30, R.drawable.fn31, R.drawable.fn32, R.drawable.fn33, R.drawable.fn34, R.drawable.fn35, R.drawable.fn36, R.drawable.fn37, R.drawable.fn38, R.drawable.fn39, R.drawable.fn40, R.drawable.fn41, R.drawable.fn42, R.drawable.fn43, R.drawable.fn44, R.drawable.fn45, R.drawable.fn46, R.drawable.fn47, R.drawable.fn48, R.drawable.fn49, R.drawable.fn50, R.drawable.fn51, R.drawable.fn52, R.drawable.fn53, R.drawable.fn54, R.drawable.fn55, R.drawable.fn56, R.drawable.fn57, R.drawable.fn58, R.drawable.fn59, R.drawable.fn60, R.drawable.fn61, R.drawable.fn62, R.drawable.fn63, R.drawable.fn64, R.drawable.fn65, R.drawable.fn66, R.drawable.fn67, R.drawable.fn68, R.drawable.fn69, R.drawable.fn70, R.drawable.fn71, R.drawable.fn72, R.drawable.fn73, R.drawable.fn74, R.drawable.fn75, R.drawable.fn76, R.drawable.fn77, R.drawable.fn78, R.drawable.fn79, R.drawable.fn81, R.drawable.fn82, R.drawable.fn83, R.drawable.fn84, R.drawable.fn85, R.drawable.fn86, R.drawable.fn87, R.drawable.fn88, R.drawable.fn89, R.drawable.fn90, R.drawable.fl1, R.drawable.fl2, R.drawable.fl3, R.drawable.fl4, R.drawable.fl5, R.drawable.fl6, R.drawable.fl7, R.drawable.fl8, R.drawable.fl9, R.drawable.fl10, R.drawable.fl11, R.drawable.fl12, R.drawable.fl13, R.drawable.fl14, R.drawable.fl15, R.drawable.fl16, R.drawable.fl17, R.drawable.fl18, R.drawable.fl19, R.drawable.fl20, R.drawable.fl21, R.drawable.fl22, R.drawable.fl23, R.drawable.fl24, R.drawable.fl25, R.drawable.fl26, R.drawable.fl27, R.drawable.fl28, R.drawable.fl29, R.drawable.fl30, R.drawable.fl31, R.drawable.fl32, R.drawable.fl33, R.drawable.fl34, R.drawable.fl35, R.drawable.fl36, R.drawable.fl37, R.drawable.fl38, R.drawable.fl39, R.drawable.fl40, R.drawable.fl41, R.drawable.fl42, R.drawable.fl43, R.drawable.fl44, R.drawable.fl45, R.drawable.fl46, R.drawable.fl47, R.drawable.fl48, R.drawable.fl49, R.drawable.fl50, R.drawable.fl51, R.drawable.fl52, R.drawable.fl53, R.drawable.fl54, R.drawable.fl55, R.drawable.fl56, R.drawable.fl57, R.drawable.fl58, R.drawable.fl59, R.drawable.fl60, R.drawable.fl61, R.drawable.fl62, R.drawable.fl63, R.drawable.fl64, R.drawable.fl65, R.drawable.fl66, R.drawable.fl67, R.drawable.fl68, R.drawable.fl69, R.drawable.fl70, R.drawable.fl71, R.drawable.fl72, R.drawable.fl73, R.drawable.fl74, R.drawable.fl75, R.drawable.fl76, R.drawable.fl77, R.drawable.fl78, R.drawable.fl79, R.drawable.fl81, R.drawable.fl82, R.drawable.fl83, R.drawable.fl84, R.drawable.fl85, R.drawable.fl86, R.drawable.fl87, R.drawable.fl88, R.drawable.fl89, R.drawable.fl90, R.drawable.fl91, R.drawable.fl92, R.drawable.fl93, R.drawable.fl94, R.drawable.fl95, R.drawable.fl96, R.drawable.fl97, R.drawable.fl98, R.drawable.fl99, R.drawable.fl101, R.drawable.fl102, R.drawable.fl103, R.drawable.fl104, R.drawable.fl105, R.drawable.fl106, R.drawable.fl107, R.drawable.fl108, R.drawable.fl109, R.drawable.fl110, R.drawable.fl111, R.drawable.fl112, R.drawable.fl113, R.drawable.fl114, R.drawable.fl115, R.drawable.fl116, R.drawable.fl117, R.drawable.fl118, R.drawable.fl119, R.drawable.fl121, R.drawable.fl122, R.drawable.fl123, R.drawable.fl124, R.drawable.fl125, R.drawable.fl126, R.drawable.fl127, R.drawable.fl128, R.drawable.fl129, R.drawable.fl130, R.drawable.fl131, R.drawable.fl132, R.drawable.fl133, R.drawable.fl134, R.drawable.fl136, R.drawable.gm1, R.drawable.gm2, R.drawable.gm3, R.drawable.gm4, R.drawable.gm5, R.drawable.gm6, R.drawable.gm7, R.drawable.gm8, R.drawable.gm9, R.drawable.gm10, R.drawable.gm11, R.drawable.gm12, R.drawable.gm13, R.drawable.gm14, R.drawable.gm15, R.drawable.gm16, R.drawable.gm17, R.drawable.gm18, R.drawable.gm19, R.drawable.gm20, R.drawable.gm21, R.drawable.gm22, R.drawable.gm23, R.drawable.gm24, R.drawable.gm25, R.drawable.gm26, R.drawable.gm27, R.drawable.gm28, R.drawable.gm29, R.drawable.gm30, R.drawable.gm31, R.drawable.gm32, R.drawable.gm33, R.drawable.gm34, R.drawable.gm35, R.drawable.gm36, R.drawable.gm37, R.drawable.gm38, R.drawable.gm39, R.drawable.gm40, R.drawable.gm41, R.drawable.gm42, R.drawable.gm43, R.drawable.gm44, R.drawable.gm45, R.drawable.gm46, R.drawable.gm47, R.drawable.gm48, R.drawable.gm49, R.drawable.gm50, R.drawable.gm51, R.drawable.gm52, R.drawable.gm53, R.drawable.gm54, R.drawable.gm55, R.drawable.gm56, R.drawable.gm57, R.drawable.gm58, R.drawable.gm59, R.drawable.gm60, R.drawable.gm61, R.drawable.gm62, R.drawable.gm63, R.drawable.gm64, R.drawable.gm65, R.drawable.gm66, R.drawable.gm67, R.drawable.gm68, R.drawable.gm69, R.drawable.gm70, R.drawable.gm71, R.drawable.gm72, R.drawable.gm73, R.drawable.gm74, R.drawable.gm75, R.drawable.gm76, R.drawable.gm77, R.drawable.gm78, R.drawable.gm79, R.drawable.gm80, R.drawable.gm81, R.drawable.gm82, R.drawable.gm83, R.drawable.gm84, R.drawable.gm85, R.drawable.gm86, R.drawable.gm87, R.drawable.s1, R.drawable.s2, R.drawable.s3, R.drawable.s4, R.drawable.s5, R.drawable.s6, R.drawable.s7, R.drawable.s8, R.drawable.s9, R.drawable.s10, R.drawable.s11, R.drawable.s12, R.drawable.s13, R.drawable.s14, R.drawable.s15, R.drawable.s16, R.drawable.s17, R.drawable.s18, R.drawable.s19, R.drawable.s20, R.drawable.s21, R.drawable.s22, R.drawable.s23, R.drawable.s24, R.drawable.s25, R.drawable.s26, R.drawable.s27, R.drawable.s28, R.drawable.s29, R.drawable.s30, R.drawable.s31, R.drawable.s32, R.drawable.s33, R.drawable.s34, R.drawable.s35, R.drawable.s36, R.drawable.s37, R.drawable.s38, R.drawable.s39, R.drawable.s40, R.drawable.s41, R.drawable.s42, R.drawable.s43, R.drawable.s44, R.drawable.s45, R.drawable.s46, R.drawable.s47, R.drawable.s48, R.drawable.s49, R.drawable.s50, R.drawable.s51, R.drawable.s52, R.drawable.s53, R.drawable.s54, R.drawable.s55, R.drawable.s56, R.drawable.s57, R.drawable.s58, R.drawable.s59, R.drawable.s60, R.drawable.s61, R.drawable.s62, R.drawable.s63, R.drawable.s64, R.drawable.s65, R.drawable.s66, R.drawable.s67, R.drawable.s68, R.drawable.s69, R.drawable.s70, R.drawable.s71, R.drawable.s72, R.drawable.s73, R.drawable.s74, R.drawable.s75, R.drawable.s76, R.drawable.s77, R.drawable.s78, R.drawable.s79, R.drawable.s80, R.drawable.s81, R.drawable.s82, R.drawable.s83, R.drawable.s84, R.drawable.s85, R.drawable.s86, R.drawable.s87, R.drawable.s88, R.drawable.s89, R.drawable.s90, R.drawable.s91, R.drawable.s92, R.drawable.s93, R.drawable.s94, R.drawable.s95, R.drawable.s96, R.drawable.s97, R.drawable.s98, R.drawable.s99, R.drawable.s100, R.drawable.s101, R.drawable.s102, R.drawable.s103, R.drawable.s104, R.drawable.s105, R.drawable.s106, R.drawable.s107, R.drawable.ani_1, R.drawable.ani_2, R.drawable.ani_3, R.drawable.ani_4, R.drawable.ani_5, R.drawable.ani_6, R.drawable.ani_7, R.drawable.ani_8, R.drawable.ani_9, R.drawable.ani_10, R.drawable.ani_11, R.drawable.ani_12, R.drawable.ani_13, R.drawable.ani_14, R.drawable.ani_15, R.drawable.ani_16, R.drawable.ani_17, R.drawable.ani_18, R.drawable.ani_19, R.drawable.ani_20, R.drawable.ani_21, R.drawable.ani_22, R.drawable.ani_23, R.drawable.ani_24, R.drawable.ani_25, R.drawable.ani_26, R.drawable.ani_27, R.drawable.ani_28, R.drawable.ani_29, R.drawable.ani_30, R.drawable.ani_31, R.drawable.ani_32, R.drawable.ani_33, R.drawable.ani_34, R.drawable.ani_35, R.drawable.ani_36, R.drawable.ani_37, R.drawable.ani_38, R.drawable.ani_39, R.drawable.ani_40, R.drawable.ani_41, R.drawable.ani_42, R.drawable.ani_43, R.drawable.ani_44, R.drawable.ani_45, R.drawable.but_1, R.drawable.but_2, R.drawable.but_3, R.drawable.but_4, R.drawable.but_5, R.drawable.but_6, R.drawable.but_7, R.drawable.but_8, R.drawable.but_9, R.drawable.but_10, R.drawable.but_11, R.drawable.but_12, R.drawable.but_13, R.drawable.but_14, R.drawable.but_15, R.drawable.cam_1, R.drawable.cam_2, R.drawable.cam_3, R.drawable.cam_4, R.drawable.cam_5, R.drawable.cam_6, R.drawable.cam_7, R.drawable.cam_8, R.drawable.cam_9, R.drawable.cam_10, R.drawable.cam_11, R.drawable.cam_12, R.drawable.cam_13, R.drawable.cam_14, R.drawable.cam_15, R.drawable.cam_16, R.drawable.cam_17, R.drawable.cam_18, R.drawable.cam_19, R.drawable.cam_20, R.drawable.cam_21, R.drawable.car_1, R.drawable.car_2, R.drawable.car_3, R.drawable.car_4, R.drawable.car_5, R.drawable.car_6, R.drawable.car_7, R.drawable.car_8, R.drawable.car_9, R.drawable.car_10, R.drawable.car_11, R.drawable.car_12, R.drawable.car_13, R.drawable.car_14, R.drawable.car_15, R.drawable.cir_1, R.drawable.cir_2, R.drawable.cir_3, R.drawable.cir_4, R.drawable.cir_5, R.drawable.cir_6, R.drawable.cir_7, R.drawable.cir_8, R.drawable.cir_9, R.drawable.cir_10, R.drawable.cir_11, R.drawable.cir_12, R.drawable.cir_13, R.drawable.cir_14, R.drawable.cir_15, R.drawable.cir_16, R.drawable.cir_17, R.drawable.cir_18, R.drawable.cir_19, R.drawable.cir_20, R.drawable.cir_21, R.drawable.cir_22, R.drawable.cir_23, R.drawable.cir_24, R.drawable.cir_25, R.drawable.cir_26, R.drawable.cir_27, R.drawable.corp_3, R.drawable.corp_4, R.drawable.corp_5, R.drawable.corp_6, R.drawable.corp_7, R.drawable.corp_8, R.drawable.corp_9, R.drawable.corp_10, R.drawable.corp_11, R.drawable.corp_12, R.drawable.corp_13, R.drawable.corp_14, R.drawable.corp_15, R.drawable.corp_16, R.drawable.corp_17, R.drawable.corp_18, R.drawable.corp_19, R.drawable.corp_20, R.drawable.squ_1, R.drawable.squ_2, R.drawable.squ_3, R.drawable.squ_4, R.drawable.squ_5, R.drawable.squ_6, R.drawable.squ_7, R.drawable.squ_8, R.drawable.squ_9, R.drawable.squ_10, R.drawable.squ_11, R.drawable.squ_12, R.drawable.fes_1, R.drawable.fes_2, R.drawable.fes_3, R.drawable.fes_4, R.drawable.fes_5, R.drawable.fes_6, R.drawable.fes_7, R.drawable.fes_8, R.drawable.fes_9, R.drawable.fes_10, R.drawable.fes_11, R.drawable.fes_12, R.drawable.fes_13, R.drawable.fes_14, R.drawable.fes_15, R.drawable.fes_16, R.drawable.fes_17, R.drawable.fes_18, R.drawable.fes_19, R.drawable.fes_20, R.drawable.fes_21, R.drawable.fes_22, R.drawable.fes_23, R.drawable.fes_24, R.drawable.fes_25, R.drawable.fes_26, R.drawable.fes_27, R.drawable.fes_28, R.drawable.fes_29, R.drawable.fes_30, R.drawable.fes_31, R.drawable.flow_1, R.drawable.flow_2, R.drawable.flow_3, R.drawable.flow_4, R.drawable.flow_5, R.drawable.flow_6, R.drawable.flow_7, R.drawable.flow_8, R.drawable.flow_9, R.drawable.flow_10, R.drawable.flow_11, R.drawable.flow_12, R.drawable.flow_13, R.drawable.flow_14, R.drawable.flow_15, R.drawable.hall_1, R.drawable.hall_2, R.drawable.hall_3, R.drawable.hall_4, R.drawable.hall_5, R.drawable.hall_6, R.drawable.hall_7, R.drawable.hall_8, R.drawable.hall_9, R.drawable.hall_10, R.drawable.hall_11, R.drawable.hall_12, R.drawable.hall_13, R.drawable.hall_14, R.drawable.hall_15, R.drawable.hall_16, R.drawable.hall_17, R.drawable.hall_18, R.drawable.hall_19, R.drawable.hall_20, R.drawable.hea_1, R.drawable.hea_2, R.drawable.hea_3, R.drawable.hea_4, R.drawable.hea_5, R.drawable.hea_6, R.drawable.hea_7, R.drawable.hea_8, R.drawable.hea_9, R.drawable.hea_10, R.drawable.hea_11, R.drawable.hea_12, R.drawable.hea_13, R.drawable.hea_14, R.drawable.hea_15, R.drawable.hea_16, R.drawable.hea_17, R.drawable.hea_18, R.drawable.hea_19, R.drawable.hea_20, R.drawable.hea_21, R.drawable.hea_22, R.drawable.hol_1, R.drawable.hol_2, R.drawable.hol_3, R.drawable.hol_4, R.drawable.hol_5, R.drawable.hol_6, R.drawable.hol_7, R.drawable.hol_8, R.drawable.hol_9, R.drawable.hol_10, R.drawable.hol_11, R.drawable.hol_12, R.drawable.hol_13, R.drawable.hol_14, R.drawable.hol_15, R.drawable.hol_16, R.drawable.hol_17, R.drawable.hol_18, R.drawable.hol_19, R.drawable.hol_20, R.drawable.hol_21, R.drawable.hol_22, R.drawable.hol_23, R.drawable.hol_24, R.drawable.hol_25, R.drawable.lea_1, R.drawable.lea_2, R.drawable.lea_3, R.drawable.lea_4, R.drawable.lea_5, R.drawable.lea_6, R.drawable.lea_7, R.drawable.lea_8, R.drawable.lea_9, R.drawable.lea_10, R.drawable.lea_11, R.drawable.lea_12, R.drawable.lea_13, R.drawable.lea_14, R.drawable.lea_15, R.drawable.lea_16, R.drawable.lea_17, R.drawable.lea_18, R.drawable.lea_19, R.drawable.lea_20, R.drawable.lea_21, R.drawable.lea_22, R.drawable.mus_1, R.drawable.mus_2, R.drawable.mus_3, R.drawable.mus_4, R.drawable.mus_5, R.drawable.mus_6, R.drawable.mus_7, R.drawable.mus_8, R.drawable.mus_9, R.drawable.mus_10, R.drawable.mus_11, R.drawable.mus_12, R.drawable.vid_1, R.drawable.vid_2, R.drawable.vid_3, R.drawable.vid_4, R.drawable.vid_5, R.drawable.vid_6, R.drawable.vid_7, R.drawable.vid_8, R.drawable.vid_9, R.drawable.vid_10, R.drawable.vid_11, R.drawable.vid_12, R.drawable.vid_13, R.drawable.vid_14, R.drawable.vid_15, R.drawable.vid_16, R.drawable.ngo_1, R.drawable.ngo_2, R.drawable.ngo_3, R.drawable.ngo_4, R.drawable.ngo_5, R.drawable.ngo_6, R.drawable.ngo_7, R.drawable.ngo_8, R.drawable.ngo_9, R.drawable.ngo_10, R.drawable.ngo_11, R.drawable.ngo_12, R.drawable.ngo_13, R.drawable.ngo_14, R.drawable.ngo_15, R.drawable.ngo_16, R.drawable.ngo_17, R.drawable.ngo_18, R.drawable.soc_1, R.drawable.soc_2, R.drawable.soc_3, R.drawable.soc_4, R.drawable.soc_5, R.drawable.soc_6, R.drawable.soc_7, R.drawable.soc_8, R.drawable.soc_9, R.drawable.soc_10, R.drawable.soc_11, R.drawable.soc_12, R.drawable.soc_13, R.drawable.par_1, R.drawable.par_2, R.drawable.par_3, R.drawable.par_4, R.drawable.par_5, R.drawable.par_6, R.drawable.par_7, R.drawable.par_8, R.drawable.par_9, R.drawable.par_10, R.drawable.par_11, R.drawable.par_12, R.drawable.par_13, R.drawable.par_14, R.drawable.par_15, R.drawable.par_16, R.drawable.par_17, R.drawable.par_18, R.drawable.par_19, R.drawable.par_20, R.drawable.par_21, R.drawable.par_22, R.drawable.par_23, R.drawable.par_24, R.drawable.par_25, R.drawable.pro_1, R.drawable.pro_2, R.drawable.pro_3, R.drawable.pro_4, R.drawable.pro_5, R.drawable.pro_6, R.drawable.pro_7, R.drawable.pro_8, R.drawable.pro_9, R.drawable.pro_10, R.drawable.pro_11, R.drawable.pro_12, R.drawable.pro_13, R.drawable.pro_14, R.drawable.pro_15, R.drawable.pro_16, R.drawable.pro_17, R.drawable.pro_18, R.drawable.pro_19, R.drawable.pro_20, R.drawable.pro_21, R.drawable.pro_22, R.drawable.pro_23, R.drawable.pro_24, R.drawable.pro_25, R.drawable.pro_26, R.drawable.pro_27, R.drawable.pro_28, R.drawable.pro_29, R.drawable.rest_1, R.drawable.rest_2, R.drawable.rest_3, R.drawable.rest_4, R.drawable.rest_5, R.drawable.rest_6, R.drawable.rest_7, R.drawable.rest_8, R.drawable.rest_9, R.drawable.rest_10, R.drawable.rest_11, R.drawable.rest_12, R.drawable.rest_13, R.drawable.rest_14, R.drawable.rest_15, R.drawable.rest_16, R.drawable.rest_17, R.drawable.rest_18, R.drawable.rest_19, R.drawable.rest_20, R.drawable.rest_21, R.drawable.rest_22, R.drawable.rest_23, R.drawable.rest_24, R.drawable.rest_25, R.drawable.rest_26, R.drawable.rest_27, R.drawable.rest_28, R.drawable.sport_1, R.drawable.sport_2, R.drawable.sport_3, R.drawable.sport_4, R.drawable.sport_5, R.drawable.sport_6, R.drawable.sport_7, R.drawable.sport_8, R.drawable.sport_9, R.drawable.sport_10, R.drawable.sport_11, R.drawable.sport_12, R.drawable.sport_13, R.drawable.sport_14, R.drawable.sport_15, R.drawable.sport_16, R.drawable.sport_17, R.drawable.sport_18, R.drawable.star_1, R.drawable.star_2, R.drawable.star_3, R.drawable.star_4, R.drawable.star_5, R.drawable.star_6, R.drawable.star_7, R.drawable.star_8, R.drawable.star_9, R.drawable.star_10, R.drawable.star_11, R.drawable.text_1, R.drawable.text_2, R.drawable.text_3, R.drawable.text_4, R.drawable.text_5, R.drawable.text_6, R.drawable.text_7, R.drawable.text_8, R.drawable.text_9, R.drawable.text_10, R.drawable.text_11, R.drawable.text_12, R.drawable.text_13, R.drawable.text_14, R.drawable.text_15, R.drawable.text_16, R.drawable.text_17, R.drawable.text_18, R.drawable.text_19, R.drawable.text_20, R.drawable.text_21, R.drawable.text_22, R.drawable.text_23, R.drawable.text_24, R.drawable.toy_1, R.drawable.toy_2, R.drawable.toy_3, R.drawable.toy_4, R.drawable.toy_5, R.drawable.toy_6, R.drawable.toy_7, R.drawable.toy_8, R.drawable.toy_9, R.drawable.toy_10, R.drawable.toy_11, R.drawable.toy_12, R.drawable.toy_13, R.drawable.toy_14, R.drawable.toy_15, R.drawable.toy_16, R.drawable.toy_17, R.drawable.toy_18, R.drawable.toy_19, R.drawable.toy_20, R.drawable.toy_21, R.drawable.toy_22, R.drawable.toy_23, R.drawable.toy_24, R.drawable.toy_25, R.drawable.far1, R.drawable.far2, R.drawable.far3, R.drawable.far4, R.drawable.far5, R.drawable.far6, R.drawable.far7, R.drawable.far8, R.drawable.dog1, R.drawable.dog2, R.drawable.dog3, R.drawable.dog4, R.drawable.dog5, R.drawable.dog6, R.drawable.dog7, R.drawable.dog8, R.drawable.dog9, R.drawable.dog10, R.drawable.dog11, R.drawable.fly1, R.drawable.fly2, R.drawable.fly3, R.drawable.fly4, R.drawable.fly5, R.drawable.fly6, R.drawable.fly7, R.drawable.fly8, R.drawable.z1, R.drawable.z2, R.drawable.z3, R.drawable.z4, R.drawable.z5, R.drawable.z6, R.drawable.z7, R.drawable.z8, R.drawable.z9, R.drawable.z10, R.drawable.z11, R.drawable.z12, R.drawable.z13, R.drawable.z14, R.drawable.z15, R.drawable.z16, R.drawable.z17, R.drawable.z18, R.drawable.z19, R.drawable.z20, R.drawable.z21, R.drawable.z22, R.drawable.z23, R.drawable.z24, R.drawable.z25, R.drawable.z26, R.drawable.z27, R.drawable.z28, R.drawable.z29, R.drawable.z30, R.drawable.z31, R.drawable.z32, R.drawable.z33, R.drawable.z34, R.drawable.z35, R.drawable.z36, R.drawable.z37, R.drawable.z38, R.drawable.z39, R.drawable.z40, R.drawable.z41};
        for (int i = 0; i < iArr.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LayoutParams(80, 80));
            Glide.with((FragmentActivity) this).load(Integer.valueOf(iArr[i])).into(imageView);
            final int finalI = i;
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {

                    int[] iii = iArr;
                    ActivityCreatingLogo.this.stickerView.addSticker(new DrawableSticker(ContextCompat.getDrawable(ActivityCreatingLogo.this, iArr[finalI])));
                    ActivityCreatingLogo.this.stickerView.invalidate();
                }
            });
            this.infoLayout.addView(imageView);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1 && i == 1001) {
            new ImageView(this);
            new LayoutParams(-2, -2).addRule(13);
            this.stickerView.addSticker(new DrawableSticker(new BitmapDrawable(getResources(), BitmapFactory.decodeFile(getTempFile().getAbsolutePath()))));
        }
        if (i == 100) {
            this.stickerView.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeFile(getTempFile().getAbsolutePath())));
        }
        super.onActivityResult(i, i2, intent);
    }


    public void startPhotoPickIntent(int i) {
        Intent intent = new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("output", getTempUri());
        intent.putExtra("outputFormat", CompressFormat.PNG.toString());
        startActivityForResult(intent, i);
    }

    private Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }

    public void openGallery() {
        Intent intent = new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("output", getTempFileUri());
        intent.putExtra("outputFormat", CompressFormat.JPEG.toString());
        startActivityForResult(intent, 1001);
    }

    private Uri getTempFileUri() {
        return Uri.fromFile(getTempFile());
    }

    private File getTempFile() {
        File file = new File(Environment.getExternalStorageDirectory(), Share.TEMP_FILE_NAME);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public void showDialogUpload() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setTitle(null);
        Button button = (Button) dialog.findViewById(R.id.logoselect);

        ((Button) dialog.findViewById(R.id.backselect)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivityCreatingLogo.this.startPhotoPickIntent(100);
                dialog.dismiss();
            }
        });
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivityCreatingLogo.this.openGallery();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void onClick(View view) {
        if (view.equals(this.mainlayout)) {
            if (!this.stickerView.isLocked()) {
                this.stickerView.setLocked(true);
            } else {
                this.stickerView.setLocked(false);
            }
        }
        if (view.equals(this.add_text)) {

            addTextInfo();

        } else if (view.equals(this.add_font)) {

            addFontText();
            this.visibleitem = true;

        } else if (view.equals(this.add_bg)) {

            loadBackgrounds();
            this.visibleitem = true;

        } else if (view.equals(this.add_color)) {

            loadAllcolors();
            this.visibleitem = true;

        } else if (view.equals(this.add_logo)) {

            loadLogos();
            this.visibleitem = true;

        } else if (view.equals(this.addshapes)) {

            loadIShapes();
            this.visibleitem = true;

        } else if (view.equals(this.lock)) {

            showDialogUpload();

        } else {

            String str = "Cant't Save yet";

            if (view.equals(this.save)) {
                if (!this.stickerView.isLocked()) {
                    this.stickerView.setLocked(true);
                }
                try {
                    new Save(this.stickerView, ActivityCreatingLogo.this).execute(new Void[0]);
                } catch (Exception unused) {
                    Toast.makeText(this, str, 1).show();
                }

            } else if (view.equals(this.clearData)) {

                this.stickerView.setBackgroundResource(0);
                this.stickerView.removeAllStickers();
                this.stickerView.invalidate();

            } /*else if (view.equals(this.share)) {

                if (Constants.filetosave == null) {
                    if (!this.stickerView.isLocked()) {
                        this.stickerView.setLocked(true);
                    }
                    try {
                        new Save(this.stickerView).execute(new Void[0]);
                    } catch (Exception unused2) {
                        Toast.makeText(this, str, 1).show();
                    }
                }

                if (Constants.filetosave != null) {
                    try {
                        StringBuilder sb = new StringBuilder();
                        sb.append("");
                        sb.append(Constants.filetosave);
                        File file = new File(sb.toString());
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("file://");
                        sb2.append(file.getAbsolutePath());
                        Uri parse = Uri.parse(sb2.toString());
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.putExtra("android.intent.extra.STREAM", parse);
                        intent.setType("image/PNG");
                        intent.addFlags(1);
                        startActivity(Intent.createChooser(intent, "Share image File"));
                    } catch (Exception unused3) {
                        Toast.makeText(this, "Cant Share", 1).show();
                    }
                }
                this.stickerView.setLocked(false);
            }*/
        }
    }

    public void onBackPressed() {

        showBack();

        /*if (this.visibleitem) {
            this.infoHorizentallayout.setVisibility(8);
            this.infoLayout.removeAllViews();
            this.visibleitem = false;
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Have you Save ?");
        builder.setPositiveButton("Yes", new C04161());
        builder.setNegativeButton("No", new C04172());
        builder.show();*/

    }
    public void showBack()
    {

        if (this.visibleitem) {
            this.infoHorizentallayout.setVisibility(8);
            this.infoLayout.removeAllViews();
            this.visibleitem = false;
            return;
        }

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.backdlg);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setTitle(null);
        Button button = (Button) dialog.findViewById(R.id.btnno);

        ((Button) dialog.findViewById(R.id.btnyes)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivityCreatingLogo.this.finish();
                dialog.dismiss();
            }
        });
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
