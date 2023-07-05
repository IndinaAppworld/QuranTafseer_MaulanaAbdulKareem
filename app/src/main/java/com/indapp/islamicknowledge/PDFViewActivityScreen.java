package com.indapp.islamicknowledge;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.indapp.fonts.GujaratiEditText;
import com.indapp.fonts.GujaratiTextView;
import com.indapp.fonts.UrduEditText;
import com.indapp.fonts.UrduTextView;
import com.indapp.utils.Constants1;

//import com.github.barteksc.pdfviewer.PDFView;


public class PDFViewActivityScreen extends Activity  {



    ImageView imgBack,imgShare,imgShareNew;

    String TITLE="";
    String type="";
    TextView txtAboutusUrdu;
Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // load title bar from Android layout
        TextView titleBar = (TextView) getWindow().findViewById(android.R.id.title);
        if (titleBar != null) {
            // set text color, YELLOW as sample
            titleBar.setTextColor(getResources().getColor(R.color.colorPrimary));
            // find parent view
            ViewParent parent = titleBar.getParent();
            if (parent != null && (parent instanceof View)) {
                // set background on parent, BRICK as sample
                View parentView = (View) parent;
                parentView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        }
        setContentView(R.layout.activity_pdfview_screen);

        imgBack = (ImageView) findViewById(R.id.imgBack);
        txtAboutusUrdu=(TextView)findViewById(R.id.txtAboutUsUrdu);




        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        type=getIntent().getExtras().getString("type");
        if(type.equalsIgnoreCase("peshlafz"))
         FILE_PATH="peshlafz";
        else if(type.equalsIgnoreCase("aboutus"))
            FILE_PATH="aboutus";

        PDFView pdfView=(PDFView)findViewById(R.id.pdfView);

        Constants1.initSharedPref(this);
        Constants1.LANGUAGE = Constants1.sp.getString("language", Constants1.GUJARATI);
        if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI)) {

            typeface = Typeface.createFromAsset(getAssets(),
                    "fonts/BHUJ UNICODE.ttf");
            ((GujaratiTextView) findViewById(R.id.txtMainTitleGujarati)).setVisibility(View.VISIBLE);
            if(type.equalsIgnoreCase("peshlafz"))
                ((GujaratiTextView) findViewById(R.id.txtMainTitleGujarati)).setText("પેશ લફઝ");
            else if(type.equalsIgnoreCase("aboutus")) {
                ((GujaratiTextView) findViewById(R.id.txtMainTitleGujarati)).setText("અમારા વિશે");

                pdfView.setVisibility(View.GONE);

                ((ScrollView) findViewById(R.id.scrollView)).setVisibility(View.VISIBLE);

              txtAboutusUrdu.setText("બિસ્મિહી તઆલા\n" +
                      "અસ્સલામુ અલકુમ વ. વ. \n" +
                      "મૈં બહુત કમ પઢા લિખા આદમી હૂં, સિર્ફ અપની માદરી ઝબાન ગુજરાતી જાનતા હૂં, દીન સે કાફી દૂર થા, ઉલમા-એ-કિરામ સે મિલ કર આહિસ્તા આહિસ્તા દીન કે કરીબ આયા, મગર દુન્યવી ઝમેલોં સે ફુરસત નહીં મિલતી થી કે દીન ઔર કુર્આન સીખું, ઐસે મેં મેરે સાથ એક હાદિસા પેશ આયા ઔર મુજે જેલ જાના પડા, મૈંને કભી સોચા ભી નહીં થા કે મેરે સાથ ઐસા હોગા, ઇસ લિયે મૈં બહુત પરેશાન હો ગયા, જેલ મેં હમેશા ફિકરમંદ બૈઠા રેહતા થા, એક દિન એક ભાઈને મુજસે કહા, કે ભાઈ! ઐસે ટેન્શન લેને સે આપ જેલ સે છૂટનેવાલે તો હૈ નહીં, તુમ એક કામ કરો; કુર્આન શરીફ પઢના શુરૂ કરો ઔર ઉસકા તર્જમા ભી પઢો. મૈંને જેલ મેં કુર્આન શરીફ પઢના શુરૂ કિયા, તો મેરા આહિસ્તા આહિસ્તા ટેન્શન કમ હોને લગા ઔર મેરા ઝિયાદા વક્ત કુર્આન કે સાથ ગુઝરને લગા ઔર દિલ મેં એક સુકૂન સા મહસૂસ હોને લગા.\n\n" +
                      "અલ્હમ્દુ લિલ્લાહ! જેલ મેં રેહ કર મૈંને પૂરે કુર્આન શરીફ કો તર્જમા ઔર તફસીર કે સાથ પઢા, ઉસકે બાદ અલ્લાહ તઆલા ને શાયદ કુર્આન હી કી બરકત ઔર બુઝુર્ગોં ઔર માં બાપ કી દુઆઓં સે મુજે જેલ સે રિહાઈ નસીબ ફરમાઇ ઔર મૈં જેલ સે કુર્આન શરીફ કી મુહબ્બત કો અપને અંદર લેકર નિકલા, મુજે બહુત ખુશી હૂઈ કે અલ્લાહ તઆલા ને મુજ જૈસે ગુનેહગાર સે અપની અઝીમ કિતાબ કુર્આને કરીમ કા યહ કામ લિયા, કે ઇસ કુર્આને કરીમ કે તર્જમા ઔર તફસીર કો ઉર્દૂ ઔર અપની માદરી ઝબાન મેં ટાઇપ કરવા કર ઇસકા એપ્લીકેશન બનવાયા તાકે ઉમ્મત ઇસ સે ફાઇદા હાસિલ કરે ઔર જો સુકૂન મૈંને મેહસૂસ કિયા વો ઉમ્મત કા હર ફર્દ હાસિલ કરે.\n\n" +
                      "મેરી નિય્યત સિર્ફ યહ હૈ કે મેરે બહુત સારે મુસલમાન ભાઈ કુર્આન કો ન સમઝને કી વજહ સે બહુત સારે ગલત અકીદે મેં ફંસે હુવે હૈં, વહ ઇસ કો પઢેં ઔર ઇસ નિય્યત સે પઢેં કે હમ કો ભી કુર્આન સે દીન સીખના હૈ ઔર ફિર અપની ઔર અપને ઘરવાલોં કી ઇસ્લાહ કરની હૈ, મૈં કસમ ખા કર કેહ સકતા હૂં કે આપકો બહુત ફાઇદા હોગા ઔર આપકો કુર્આન સે, અલ્લાહ તઆલા સે ઔર ઉસકે પ્યારે રસૂલ (સલ્લલ્લાહુ અલૈહિ વસલ્લમ) સે મુહબ્બત હો જાએગી. મેરી ઇસ કોશિશ સે કિસી એક કો ભી હિદાયત મિલ જાએગી, તો ઇન્શા અલ્લાહ મેરી ભી મગફિરત હો જાએગી ઔર ઉસકી ભી. મૈં આપકા ગુનેહગાર ભાઈ અફરોઝ ફત્તા આપ કો અલ્લાહ કા વાસ્તા દે કર કેહતા હૂં કે આપ ફિરકાબંદી કા બિલકુલ ન સોચેં ઔર સિર્ફ દીન સમઝને કે લિયે ઇસ તર્જમા ઔર તફસીર કા એક મરતબા ઝરૂર મુતાલા કરેં (પઢેં). મેરા મકસદ સિર્ફ ઔર સિર્ફ અલ્લાહ કી રઝામંદી હાસિલ કરના હૈ ઔર યે કે અલ્લાહ તઆલા હમ સબસે રાઝી હો જાએ, મેરે પ્યારે દોસ્તો ઔર ભાઇયો! અલ્લાહ ઔર ઉસકે પ્યારે હબીબ (સલ્લલ્લાહુ અલૈહિ વસલ્લમ) હમ સે રાઝી હો ગએ, તો આપ યૂં સમઝો કે હમ કામ્યાબ હો ગએ, અસલ કામ્યાબી યહી હૈ કે મેરા ઔર આપ સબ કા પૈદા કરનેવાલા અલ્લાહ હમ સે રાઝી હો જાએ ઔર હમ આખિરત મેં અલ્લાહ કે રસૂલ ઔર હમારે પ્યારે હબીબ (સલ્લલ્લાહુ અલૈહિ વસલ્લમ) કા દીદાર કર સકેં ઔર હમેશા હમેશા કી ઝિંદગી મેં સુકૂન હાસિલ હો જાએ, તો આઇયે! હમ કુર્આન કો સમઝને કી કોશિશ કરતે હૈં, અલ્લાહ તઆલા મેરે ઇસ ટૂટે ફૂટે છોટે સે કામ કો શર્ફે કુબૂલ અતા ફરમાએ ઔર ઉમ્મત કો ઇસ સે બહુત બહુત ફાઇદા પહુંચાએ. ઔર જિન દોસ્તોંને મેરા ઇસ મેં તઆવુન કિયા હૈ અલ્લાહ તઆલા ઉન કો ભી બેહતરીન બદલા અતા ફરમાએ. આમીન.\n\n" +
                      "યે તર્જમા ઔર તફસીર મૌલાના અબ્દુલ કરીમ પારેખ સાહબ કા હૈ, ઉર્દૂ ઔર ગુજરાતી દોનોં કા મવાદ પ્રિંટેડ તશરીહુલ કુર્આન મેં સે દેખ કર ટાઇપ કિયા ગયા હૈ, અગર આપ હઝરાત કો ઇસ મેં કોઈ ભી ગલતી નઝર આએં તો બરાએ કરમ હમેં નીચે દિયે ગયે મેલ પર ઇત્તિલા ઝરૂર કર દેં, તાકે અપડેટ વર્ઝન મેં ઉસે દુરૂસ્ત કર લિયા જાએ.\n\n" +
                      "અગર આપકો ઇસ એપ સે ફાઇદા હો યા આપ કોઈ મશવરા દેના ચાહતે હોં તો નીચે દિયે ગએ ઈ-મેઈલ એડ્રેસ પર હમસે રાબતા કર સકતે હૈં, આપ કા દીની ભાઈ અફરોઝ ફત્તા (સુરત)\n" +
                      "contactfortashreehulquran@gmail.com\n" +
                      "\n" +
                      "અઝાઇમ : ઇંશા અલ્લાહ આગે મઝીદ દો ઝબાનોં (હિન્દી ઔર અંગ્રેજી) મેં ભી ઇસ કા તર્જમા ઔર તફસીર ઇસી એપ મેં ડાલા જાએગા.\n\n" +
                      "સાથ હી અલ્લાહ કી રઝામંદી કૈસે હાસિલ કરેં? યે કિતાબ ભી ઇસી મેં એડ કી જાએગી.\n\n" +
                      "રોઝાના કા એક દીની મેસેજ ભેજને કે લિયે 365 મેસેજીસ ભી એડ કિયે જાએંગે.\n\n" +
                      "ઔર અગર અલ્લાહ ને ચાહા તો ઇંશા અલ્લાહ આસાન તર્જમ-એ-કુર્આન ભી ગુજરાતી, હિંદી ઔર અંગ્રેજી ઝબાનોં મેં એડ કિયા જાએગા.\n");
            }

        } else if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU)) {
            typeface = Typeface.createFromAsset(getAssets(), "fonts/jameelnoorinastaleeq.ttf");

            ((UrduTextView) findViewById(R.id.txtMainTitleUrdu)).setVisibility(View.VISIBLE);
            if(type.equalsIgnoreCase("peshlafz"))
                ((UrduTextView) findViewById(R.id.txtMainTitleUrdu)).setText("پیش لفظ");
           else if(type.equalsIgnoreCase("aboutus"))
            {
                ((UrduTextView) findViewById(R.id.txtMainTitleUrdu)).setText("ہمارے بارے میں");
                pdfView.setVisibility(View.GONE);

                ( (ScrollView)findViewById(R.id.scrollView)).setVisibility(View.VISIBLE);

                txtAboutusUrdu.setText("بسمہ تعالیٰ\nالسلام علیکم و برکاتہ\n" +
                        "میں بہت کم پڑھا لکھا آدمی ہوں ،صرف اپنی مادری زبان گجراتی جانتا ہوں، دین سے کافی دور تھا، علمائے کرام سے ملکر آہستہ آہستہ دین کے قریب آیا، مگر دنیاوی جھمیلوں سے فرصت نہیں ملتی تھی کہ دین اور قرآن سیکھو، ایسے میں میرے ساتھ ایک حادثہ پیش آیا اور مجھے جیل جانا پڑا، میں نے کبھی سوچا بھی نہیں تھاکہ میرے ساتھ ایسا ہوگا، میں بہت پریشان ہو گیا ، جیل میں ہمیشہ فکر مند بیٹھا رہتا تھا ، ایک دن ایک بھائی نے مجھ سے کہا ، کہ بھائی ! ایسے ٹینشن لینے سے آپ جیل سے چھوٹنے والے تو ہے نہیں ، تم ایک کام کرو ؛قرآن شریف پڑھنا شروع کرو اور اس کا ترجمہ بھی پڑھو ۔میں نے جیل میں قرآن شریف پڑھنا شروع کیا ،تو آہستہ آہستہ ٹینشن کم ہونے لگا اور میرا زیادہ وقت قرآن کے ساتھ گزرنے لگا اور دل میں ایک سکون سا محسوس ہونے لگا۔\n\n" +
                        "الحمدللہ! جیل میں رہ کر میں نے پورے قرآن شریف کو ترجمہ اور تفسیر کے ساتھ پڑھا، اس کے بعد اللہ تعالی نے شاید قرآن ہی کی برکت اور بزرگوں اور ماں باپ کی دعاؤں سے مجھے جیل سے رہائی نصیب فرمائی اور میں جیل سے قرآن شریف کی محبت کو اپنے اندر لے کر نکلا، مجھے بہت خوشی ہوئی کہ اللہ تعالی نے مجھ جیسے گنہگار سے اپنی عظیم کتاب قرآن کریم کا یہ کام لیا، کہ اس قرآن کریم کے ترجمہ اور تفسیر کو اردو اور اپنی مادری زبان میں ٹائپ کروا کر اس کا ایپلیکیشن بنوایا تاکہ امت اس سے فائدہ حاصل کرے اور جو سکون میں نے محسوس کیا وہ امت کا ہر فرد حاصل کریں۔\n\n" +
                        "میری نیت صرف یہ ہے کہ میرے بہت سارے مسلمان بھائی قرآن کو نہ سمجھنے کی وجہ سے غلط عقیدے میں پھنسے ہوئے ہیں ، وہ اس کو پڑھیں اور اس نیت سے پڑھیں کہ ہم کو بھی قرآن سے دین سیکھنا ہے اور پھر اپنی اور اپنے گھر والوں کی اصلاح کرنی ہے ، میں قسم کھا کر کہ سکتا ہوں کہ آپکو بہت فائدہ ہوگا اور آپ کو قرآن سے، اللہ تعالی سے اور اس کے پیارے رسول صلی اللہ علیہ وسلم سے محبت ہوجائے گی۔ میری اس کوشش سے کسی ایک کو بھی ہدایت مل جائے گی، تو انشاءاللہ میری بھی مغفرت ہوجائے گی اور اس کی بھی۔ میں آپ کا گنہگار بھائی افروز فتہّ آپ کو اللہ کا واسطہ دے کر کہتا ہوں کہ آپ فرقہ بندی کا بالکل نہ سوچیں اور صرف دین سمجھنے کے لئے اس ترجمہ اور تفسیر کا ایک مرتبہ ضرور مطالعہ کریں۔ میرا مقصد صرف اور صرف اللہ کی رضامندی حاصل کرنا ہے اور یہ کہ اللہ تعالی ہم سب سے راضی ہو جائے، میرے پیارے دوستو اور بھائیو! اللہ اور اس کے پیارے حبیب صلی اللہ علیہ وسلم ہم سے راضی ہوگئے، تو آپ یوں سمجھو کہ ہم کامیاب ہوگئے، اصل کامیابی یہی ہے کہ میرا اور آپ سب کا پیدا کرنے والا اللہ ہم سے راضی ہو جائے اور ہم آخرت میں اللہ کے رسول اور ہمارے پیارے حبیب صلی اللہ علیہ وسلم کا دیدار کر سکیں اور ہمیشہ ہمیشہ کی زندگی میں سکون حاصل ہو جائے، تو آئیے! ہم قرآن کو سمجھنے کی کوشش کرتے ہیں، اللہ تعالی میرے اس ٹوٹے پھوٹے چھوٹے سے کام کو صرف قبول عطا فرمائے اور امت کو اس سے بہت بہت فائدہ پہنچائے۔ اور جن دوستوں نے میرا اس میں تعاون کیا ہے اللہ تعالی ان کو بھی بہترین بدلہ عطا فرمائے۔ آمین۔\n\n" +
                        "یہ ترجمہ اور تفسیر مولانا عبد الکریم پاریکھ صاحب کا ہے، اردو اور گجراتی دونوں کا مواد پرنٹیڈ تشریح القرآن میں سے دیکھ کر ٹائپ کیا گیا ہے، اگر آپ حضرات کو اس میں کوئی بھی غلطی نظر آئے تو برائے کرم ہمیں نیچے دیے گئے میل پر اطلاع ضرور کردے، تاکہ اپ ڈیٹ ورژن میں اسے درست کر لیا جائے۔\n\n" +
                        "اگر آپ کو اس ایپ سے فائدہ ہو یا آپ کو ئی مشورہ دینا چاہتے ہو تو نیچے دیے گئے ای میل ایڈریس پر ہم سے رابطہ کر سکتے ہیں ، آپ کا دینی بھائی افروز فتہ (سورت)\n" +
                        "contactfortashreehulquran@gmail.com\n\n" +
                        "عزائم : ان شاء اللہ آگے مزید دو زبانوں (ہندی اور انگریزی ) میں بھی اس کا ترجمہ اور تفسیر اسی ایپ میں ڈالا جائے گا۔\n\n" +
                        "ساتھ ہی اللہ کی رضامندی کیسے حاصل کریں؟ یہ کتاب بھی اسی میں ایڈ کی جائیگی۔\n\n" +
                        "روزآنہ کا ایک دینی میسیج بھیجنے کے لیے 365؍ میسیجس بھی ایڈ کیے جائینگے۔\n\n" +
                        "اور اگر اللہ نے چاہا تو انشاءاللہ آسان ترجمۂ قرآن بھی گجراتی، ہندی اور انگریزی زبانوں میں ایڈ کیا جائیگا۔\n");


            }

        }
        txtAboutusUrdu.setTypeface(typeface);

        pdfView.fromAsset(""+FILE_PATH+"_"+Constants1.LANGUAGE.toLowerCase()+".pdf")
//        pdfView.fromFile(new File(FILE_PATH))
////            pdfView.fromFile(new File(LOAD_FILE_PATH))
////        pdfView.fromFile(new File(Constants1.BASE_PATH+"/"+getIntent().getExtras().getString("FILENAME")))
////                .onPageChange(this)
//                .enableAnnotationRendering(true)
//                .defaultPage(0)
//                .pageFitPolicy(FitPolicy.WIDTH)
//
////                .nightMode(true) // toggle night mode
////                .swipeHorizontal(true)
////                .pageSnap(true)
////                .autoSpacing(true)
////                .pageFling(true)
//
//
//
//
////                .onLoad(this)/
                .scrollHandle(new DefaultScrollHandle(this))
//
//
                .spacing(0) // in dp
//
////                .onPageError(this)
//
                .load();
//
//
////        new com.github.barteksc.pdfviewer.link.DefaultLinkHandler(pdfView);
//        imgShare=(ImageView)findViewById(R.id.imgShare);
//        Constants1.changeButtonColor(imgShare, "#000000");
////        imgShareNew=(ImageView)findViewById(R.id.imgShareNew);
////        Constants1.changeButtonColor(imgShareNew, "#000000");
//        if(true)//TITLE.equalsIgnoreCase("Commission") || TITLE.equalsIgnoreCase("Renewal Notice"))
//        {
//            imgShare.setVisibility(View.VISIBLE);
//            imgShare.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                        Constants1.displayAlertDialog(PDFViewActivityScreen.this,"Commission","File has been downloaded and Saved on \n\n"+FILE_PATH);
//
//                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//                    StrictMode.setVmPolicy(builder.build());
//                    Intent intentShareFile = new Intent(Intent.ACTION_SEND);
//                    File fileWithinMyDir = new File(FILE_PATH);
//                    if(fileWithinMyDir.exists())
//                    {
//
//                        Uri  uri=FileProvider.getUriForFile(PDFViewActivityScreen.this, "com.indapp.katariaapp.fileprovider",fileWithinMyDir);
//                        intentShareFile.setType("application/pdf");
//                        intentShareFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        intentShareFile.putExtra(Intent.EXTRA_STREAM, uri);  //Uri.parse(FILE_PATH));
//                        intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
//                                "Sharing File...");
//                        intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");
//                        intentShareFile.putExtra(Intent.EXTRA_EMAIL,""+EMAILID);
//                        startActivity(Intent.createChooser(intentShareFile, "Share File"));
//                    }
//                }
//            });
//
//            imgShareNew.setVisibility(View.VISIBLE);
//            imgShareNew.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//                    StrictMode.setVmPolicy(builder.build());
//                    Intent intentShareFile = new Intent(Intent.ACTION_SEND);
//                    File fileWithinMyDir = new File(FILE_PATH);
//                    if(fileWithinMyDir.exists())
//                    {
//                        intentShareFile.setType("application/pdf");
//                        intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse(FILE_PATH));
//                        intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
//                                "Sharing File...");
//                        intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");
//                        startActivity(Intent.createChooser(intentShareFile, "Share File"));
//                    }
//
//                }
//            });

        findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        }
//        else
//        {
//            imgShare.setVisibility(View.GONE);
//        }

//    }
//    PDFView pdfView;
    String FIRM_NAME="",DATA_CODE="",FILE_PATH="";
}