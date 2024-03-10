package com.indapp.islamicknowledge;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.indapp.fonts.CipherNormal;
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
String subtype="quran";

RelativeLayout layout_header;
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
        layout_header=(RelativeLayout)findViewById(R.id.layout_header);
        imgBack = (ImageView) findViewById(R.id.imgBack);

        if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.ENGLISH)==false)
        {  txtAboutusUrdu=(TextView)findViewById(R.id.txtAboutUsUrdu);

            txtAboutusUrdu.setVisibility(View.VISIBLE);}
        else txtAboutusUrdu=(TextView)findViewById(R.id.txtAboutUsEnglish);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        type=getIntent().getExtras().getString("type");

        if(getIntent().getExtras().containsKey("subtype"))
            subtype=getIntent().getExtras().getString("subtype");


        if(subtype.equalsIgnoreCase("dua"))
        {
            layout_header.setBackgroundResource(R.drawable.background_header_dua);
            getWindow().setStatusBarColor(getResources().getColor(R.color.duaprimarycolor));

        }
//        if(type.equalsIgnoreCase("peshlafz"))
//         FILE_PATH="peshlafz";
//        else if(type.equalsIgnoreCase("aboutus"))
//            FILE_PATH="aboutus";

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

            }
                ((GujaratiTextView) findViewById(R.id.txtMainTitleGujarati)).setText("અમારા વિશે");

                pdfView.setVisibility(View.GONE);

                ((ScrollView) findViewById(R.id.scrollView)).setVisibility(View.VISIBLE);


                if(subtype.equalsIgnoreCase("quran")) {
                    txtAboutusUrdu.setText("બિસ્મિહી તઆલા\n" +
                            "અસ્સલામુ અલૈકુમ વ. વ.\n" +
                            "\"જૈસા કે મૈંને અપની આપ બીતી મેં બતાયા થા કે જેલ મેં રેહ કર મૈંને પૂરે કુર્આન શરીફ કો તર્જમા ઔર તફસીર કે સાથ પઢા, ઉસકે બાદ અલ્લાહ તઆલા ને શાયદ કુર્આન હી કી બરકત ઔર બુઝુર્ગોં ઔર માં બાપ કી દુઆઓં સે મુજે જેલ સે રિહાઈ નસીબ ફરમાઇ ઔર મૈં જેલ સે કુર્આન શરીફ કી મુહબ્બત કો અપને અંદર લેકર નિકલા, રિહાઈ કે બાદ કુર્આન કી ઉસી મુહબ્બત કી વજહ સે અલ્લાહ તઆલા ને મુજ જૈસે ગુનેહગાર સે અપની અઝીમ કિતાબ કુર્આને કરીમ કા યહ કામ લિયા, કે ઇસ કુર્આને કરીમ કે તર્જમા ઔર તફસીર કો ઉર્દૂ ઔર અપની માદરી ઝબાન ગુજરાતી મેં ટાઇપ કરવા કર ઇસકા એપ્લીકેશન બનવાયા તાકે ઉમ્મત ઇસ સે ફાઇદા હાસિલ કરે ઔર જો સુકૂન મૈંને મેહસૂસ કિયા વો ઉમ્મત કા હર ફર્દ હાસિલ કરે.\n" +
                            "ઇસ એપ્લીકેશન કી ખૂબી યહ હૈ કે ઇસ કો હમને ટેકસ બેસ (Text Based) પર બનાયા હૈ, ઇસ કા ફાઇદા યહ હૈ કે આપ અરબી આયાત, તર્જમા ઔર તફસીર કો ટેકસ કી શકલ મેં કિસી કો ભી શેર કર સકતે હો/ભેજ સકતે હો. સાથ હી ઇસ મેં બુકમાર્ક, કલર વગૈરા ઓપ્શન દિયે ગયે હૈ, કલર કી ખૂબી યહ હૈ કે અરબી આયાત ઔર તર્જમા કો અલગ અલગ કલર મેં ભી રખ સકતે હો, સાથ હી ઝૂમ ઇન ઔર ઝૂમ આઉટ કા ઓપ્શન ભી દિયા ગયા હૈ યાની આપ સ્ક્રીન મેં દો ઉંગલિયોં કે ઝરીયે આયાત, તર્જમા ઔર તફસીર કો છોટા-બડા કર સકતે હો, ઔર ભી બહુત સારી ખૂબિયાં ઇસ મેં મૌજૂદ હૈ. ઇન સબ કા મકસદ સિર્ફ યહ હૈ કે ઉમ્મત ઇસ સે ફાઇદા ઉઠાએ ઔર અલ્લાહ તઆલા હમ સે રાઝી હો જાએ, ઔર યે હમારી ઔર આપ કી નજાત કા ઝરીયા બન જાએ.\n" +
                            "દુઆઓં કા તાલિબ (આપ કા દીની ભાઈ અફરોઝ ફત્તા)");
                }
                else if(subtype.equalsIgnoreCase("dua"))
                {
                    txtAboutusUrdu.setText("બિસ્મિહી તઆલા\n" +
                            "અસ્સલામુ અલૈકુમ વ. વ.\n" +
                            "\"જૈસા કે મૈંને અપની આપ બીતી મેં બતાયા થા કે જેલ મેં રેહ કર મૈંને પૂરે કુર્આન શરીફ કો તર્જમા ઔર તફસીર કે સાથ પઢા, ઉસ કે બાદ મૈંને ઐસી ખરાબિયોં કો સોચના શુરૂ કિયા, જિસ મેં મુજે અપને મુઆશરે કી બદ-એ’તિકાદી (અકીદે કી ખરાબી) નઝર આઈ થી, ઔર ફિર ઐસી આયતોં પર નિશાન લગાને શુરૂ કિયે, અલ્હમ્દુ લિલ્લાહ! જેલ મેં રેહ કર મૈંને પૂરે કુર્આન શરીફ કી આયતોં પર ઇસ તરહ નિશાન લગાએ.\n" +
                            "મેરી નિય્યત સિર્ફ યહ હૈ કે મેરે બહુત સારે મુસલમાન ભાઈ કુર્આન કો ન સમઝને કી વજહ સે બહુત સારે ગલત અકીદે મેં ફંસે હુવે હૈં, વહ ઇસ કો પઢેં ઔર ઇસ નિય્યત સે પઢેં કે હમ કો ભી કુર્આન સે દીન સીખના હૈ ઔર ફિર અપની ઔર અપને ઘરવાલોં કી ઇસ્લાહ કરની હૈ, મૈં કસમ ખા કર કેહ સકતા હૂં કે આપકો બહુત ફાઇદા હોગા ઔર આપ કો કુર્આન સે, અલ્લાહ તઆલા સે ઔર ઉસકે પ્યારે રસૂલ (સલ્લલ્લાહુ અલૈહિ વસલ્લમ) સે મુહબ્બત ઔર ઝ્યાદા હો જાએગી. મેરી ઇસ કોશિશ સે કિસી એક કો ભી હિદાયત મિલ જાએગી, તો ઇન્શા અલ્લાહ મેરી ભી મગફિરત હો જાએગી ઔર ઉસ કી ભી.\n" +
                            "ઉસી કિતાબ કો ઉમ્મત કી આસાની કે લિયે એપ્લીકેશન મેં સેટ કરવાયા ગયા તાકે લોગ ઇસ સે હર જગહ ફાઇદા ઉઠા સકે, ઇસ મેં શેર કા ઓપ્શન ભી દિયા ગયા હૈ, જો ઈમેજ કી શકલ મેં શેર હોગા, ઇસ મેં અરબી મવાદ, તર્જમા ઔર તફસીર તીનોં ચીઝેં શેર હોગી, ઇસ કિતાબ મેં 104 હેડીંગ કે તહત 370 આયાત હૈ.\n" +
                            "સાથ મેં ઇસી કે ઝિમ્ન મેં દુરૂદ શરીફ કા ભી એક સેકશન દિયા ગયા હૈ, આપ જબ કિતાબ કો ખોલેંગે તો નીચે એક હરે કલર કી પટ્ટી નઝર આએગી ઉસ પર ક્લિક કરને સે દુરૂદ શરીફ ઓપન હો જાએંગે, જિસ મેં મુખ્તલિફ ફઝાઇલ કે સાથ દુરૂદ શરીફ દિયે ગએ હૈં. ઇસ મેં ભી શેર કા ઓપ્શન દિયા ગયા હૈ, જો ઈમેજ કી શકલ મેં શેર હોગા.\n" +
                            "ઇન સારી કોશિશોં કા મકસદ સિર્ફ એક હી હૈ હમ ઔર આપ ઇસ પર અમલ કરને વાલે બન જાએ ઔર અલ્લાહ તઆલા હમસે રાઝી હો જાએ, ઔર યે હમારી નજાત કા ઝરીયા બન જાએ.\n" +
                            "દુઆઓં કા તાલિબ (આપ કા દીની ભાઈ અફરોઝ ફત્તા)");
                }
                else if(subtype.equalsIgnoreCase("aboutus"))
                {
                    txtAboutusUrdu.setText("બિસ્મિહી તઆલા\n" +
                            "અસ્સલામુ અલૈકુમ વરહમતુલ્લાહિ વબરકાતુહ\n" +
                            "\nમૈં બહુત કમ પઢા લિખા આદમી હૂં. સિર્ફ અપની માદરી ઝબાન ગુજરાતી જાનતા હૂં. દીન સે કાફી દૂર થા, ઉલમા-એ-કિરામ સે મિલ કર આહિસ્તા આહિસ્તા દીન કે કરીબ આયા, મગર દુન્યવી ઝમેલોં સે ફુરસત નહીં મિલતી થી કે દીન ઔર કુર્આન સીખૂં. ઐસે મેં મેરે સાથ એક હાદિસા પેશ આયા ઔર મુજે જેલ જાના પડા, મૈંને કભી સોચા ભી નહીં થા કે મેરે સાથ ઐસા હોગા, ઇસ લિયે મૈં બહુત પરેશાન હો ગયા, જેલ મેં હમેશા ફિકરમંદ બૈઠા રેહતા થા, એક દિન એક ભાઈ ને મુજ સે કહા કે, ભાઈ! ઐસે ટેન્શન લેને સે આપ જેલ સે છૂટનેવાલે તો હૈ નહીં, તુમ એક કામ કરો કુર્આન શરીફ પઢના શુરૂ કરો ઔર ઉસ કા તર્જમા ભી પઢો. મૈંને જેલ મેં કુર્આન શરીફ પઢના શુરૂ કિયા, તો મેરા આહિસ્તા આહિસ્તા ટેન્શન કમ હોને લગા ઔર મેરા ઝ્યાદા વક્ત કુર્આન કે સાથ ગુઝરને લગા ઔર દિલ મેં એક સુકૂન સા મહસૂસ હોને લગા.\n" +
                            "\nઉસકે બાદ મૈંને ઐસી ખરાબિયોં કો સોચના શુરૂ કિયા, જિસ મેં મુજે અપને મુઆશરે કી બદ-એ’તિકાદી (અકીદે કી ખરાબી) નઝર આઈ થી, ઔર ફિર ઐસી આયતોં પર નિશાન લગાને શુરૂ કિયે, અલ્હમ્દુ લિલ્લાહ! જેલ મેં રેહ કર મૈંને પૂરે કુર્આન શરીફ કી આયતોં પર ઇસ તરહ નિશાન લગાએ.\n" +
                            "\nઉસકે બાદ અલ્લાહ તઆલા ને શાયદ કુર્આન હી કી બરકત ઔર બુઝુર્ગોં ઔર માં-બાપ કી દુઆઓં સે મુજે જેલ કી રિહાઈ નસીબ ફરમાઈ ઔર મૈં જેલ સે કુર્આન શરીફ કી મુહબ્બત કો અપને અંદર લેકર નિકલા. મુજે બહુત ખુશી હુઈ કે અલ્લાહ તઆલા ને મુજ જૈસે ગુનેહગાર સે અપની અઝીમ કિતાબ કુર્આને કરીમ કા યહ બહુત છોટા કામ લિયા.\n" +
                            "\nઇસકે બાદ મુજે ફિક્ર હૂઈ કે મૈં ઇસે શાયેઅ (પ્રકાશિત) કરાઉં, મગર કૈસે? તો મૈંને મેરે એક આલિમ દોસ્ત મૌલાના હુઝૈફા વસ્તાનવી સા. (ઝી.મ.) અક્કલકુવા સે કહા કે, મૈંને ઐસા ઐસા કામ કિયા હૈ, ફિર મૈંને ઉનકો કુર્આન કે મઝામીન કી ફેહરિસ્ત બતલાઈ તો ઉન્હોંને કહા : ઠીક હૈ ભાઈ! આપને બહુત હી અચ્છા કામ કિયા હૈ, આપ યહ મુજે દે દો, તો મૈંને ઉનકો દે દિયા ઔર માશાઅલ્લાહ, ઉન્હોંને જામિઅહ અક્કલકુવા કે હિફઝ કે ઉસ્તાઝ મૌલાના મુફતી તાહિર ભડકોદ્રવી સાહબ કો યહ ઝિમ્મેદારી દી. ઉન્હોંને મૌલાના હુઝૈફા વસ્તાનવી સાહબ કી માતહતી ઔર નિગરાની મેં કુર્આન કી આયતોં કા તર્જમા ઔર મુખ્તસર ખુલાસા લે કર ઉસકો કમ્પ્યૂટર પર લિખવાયા ઔર ઉસે કિતાબી શકલ દી. કિર ઉસ મુસવ્વદે પર મૌલવી હસન ભડકોદ્રવી સાહબ ઔર હઝરત મૌલાના અબ્દુલ મન્નાન મનિયાર સૂરતી સાહબ ને નઝરે સાની (પુનરવલોકન) કી ઔર ઇસ્લાહ કે બાદ (ફાઇનલ પ્રૂફ રીડિંગ કે બાદ) મુજે દેખને કે લિયે ભેજા. મૈંને ખુદ દેખા ઔર ઉસ મેં જહાં-જહાં તફસીર કી ઝરૂરત થી ઉસકા ઇઝાફા કરવાયા ઔર ઉસકે મઝામીન કો ઇસ તરહ મુરત્તબ કિયા કે હેડિંગ પઢને સે હી આઘી બાત સમઝ મેં આ જાતી હૈ ઔર મુજે બહુત અચ્છા લગા. અબ યહ કામ જિસકો મૈંને કિયા ઔર ફિર દો મોઅતબર આલિમોં ને દેખા ઔર મુરત્તબ કિયા, ઉસકો છપવા રહા હૂં. મેરી નિય્યત સિર્ફ યહ હૈ કે મેરે બહુત સારે મુસલમાન ભાઈ કુર્આન કો બ-રાહે રાસ્ત ન સમઝને કી વજહ સે બહુત સારે ગલત અકીદે મેં ફંસે હુવે હૈં, વહ ઇસ કો પઢેં ઔર ઇસ નિય્યત સે પઢેં કે ઉનકો ભી કુર્આન સે બ-રાહે રાસ્ત દીન સીખના હૈ ઔર ફિર અપની ઔર અપને ઘરવાલોં કી ઇસ્લાહ કરની હૈ. મૈં કસમ ખા કર કેહ સકતા હૂં કે આપ કો બહુત ફાઇદા હોગા ઔર આપ કો કુર્આન સે, અલ્લાહ તઆલા સે ઔર ઉસકે પ્યારે રસૂલ સલ્લલ્લાહુ અલૈહિ વસલ્લમ સે મુહબ્બત હો જાએગી.\"");
                }


        }
        else if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU)) {
            typeface = Typeface.createFromAsset(getAssets(), "fonts/jameelnoorinastaleeq.ttf");

            ((UrduTextView) findViewById(R.id.txtMainTitleUrdu)).setVisibility(View.VISIBLE);
            if(type.equalsIgnoreCase("peshlafz"))
                ((UrduTextView) findViewById(R.id.txtMainTitleUrdu)).setText("پیش لفظ");
           else if(type.equalsIgnoreCase("aboutus")) {
                ((UrduTextView) findViewById(R.id.txtMainTitleUrdu)).setText("ہمارے بارے میں");

            }
                pdfView.setVisibility(View.GONE);

                ( (ScrollView)findViewById(R.id.scrollView)).setVisibility(View.VISIBLE);

                if(subtype.equalsIgnoreCase("quran")) {
                    txtAboutusUrdu.setText("باسمہ تعالیٰ\n" +
                            "السلام علیکم ورحمة اللہ وبرکاتہ\n\n" +
                            "جیسا کہ میں نے اپنی آپ بیتی میں بتایا تھا کہ جیل میں رہ کر میں نے پورے قرآن شریف کو ترجمہ اور تفسیر کے ساتھ پڑھا، اس کے بعد اللہ تعالی نے شاید قرآن ہی برکت اور بزرگوں اور ماں باپ کی دعاؤں سے مجھے جیل سے رہائی نصیب فرمائی اور میں جیل سے قرآن شریف کی محبت کو اپنے اندر لے کر نکلا، رہائی کے بعد قرآن کی اسی محبت کی وجہ سے اللہ تعالی نے مجھ جیسے گنہگار سے اپنی عظیم کتاب قرآن کریم کا یہ کام لیا، کہ اس قرآن کریم کے ترجمہ اور تفسیر کو اردو اور اپنی مادری زبان گجراتی میں ٹائپ کروا کر اس کا ایپلیکیشن بنوایا تاکہ امت اس سے فائدہ حاصل کرے اور جو سکون میں نے محسوس کیا وہ امت کا ہر فرد حاصل کرے۔\n\n" +
                            "اس ایپلیکیشن کی خوبی یہ ہے کہ اس کو ہم نے ٹیکس بیس پر بنایا ہے اس کا فائدہ یہ ہے کہ آپ عربی آیات، ترجمہ اور تفسیر کو ٹیکس کی شکل میں کسی کو بھی شیئر کر سکتے ہو/بھیج سکتے ہو۔ ساتھ ہی اس میں بک مارک، کلر وغیرہ آپشن دیے گئے ہیں، کلر کی خوبی یہ ہے کہ عربی آیات اور ترجمہ کو الگ الگ کلر میں بھی رکھ سکتے ہو، ساتھ ہی زوم ان اور زوم اؤٹ کا اپشن بھی دیا گیا ہے یعنی آپ اسکرین میں دو انگلیوں کے ذریعے آیات، ترجمہ اور تفسیر کو چھوٹا، بڑا کر سکتے ہو اور بھی بہت ساری خوبیاں اس میں موجود ہیں۔ ان سب کا مقصد صرف یہ ہے کہ امت اس سے فائدہ اٹھائے اور اللہ تعالی ہم سے راضی ہو جائے اور یہ ہماری اور آپ کی نجات کا ذریعہ بن جائے۔\n\n" +
                            "دعاؤں کا طالب (آپ کا دینی بھائی افروز  پھتا)\n\n" +
                            "");
                }
                else if(subtype.equalsIgnoreCase("dua"))
                {
                    txtAboutusUrdu.setText("باسمہ تعالیٰ\n" +
                            "السلام علیکم ورحمة اللہ وبرکاتہ\n\n" +
                            "\"جیسا کہ میں نے اپنی آپ بیتی میں بتایا تھا کہ جیل میں رہ کر میں نے پورے قرآن شریف کو ترجمہ اور تفسیر کے ساتھ پڑھا، اس کے بعد میں نے ایسی خرابیوں کو سوچنا شروع کیا جس میں مجھے اپنے معاشرے کی بد اعتقادی یعنی عقیدے کی خرابی نظر آئی تھی، اور پھر ایسی آیتوں پر نشان لگانے شروع کیے، الحمدللہ! جیل میں رہ کر میں نے پورے قرآن شریف کی آیتوں پر اس طرح نشان لگائے۔\n\n" +
                            "میری نیت صرف یہ ہے کہ میرے بہت سارے مسلمان بھائی قرآن کو نہ سمجھنے کی وجہ سے بہت سارے غلط عقیدے میں پھنسے ہوئے ہیں، وہ اس کو پڑھیں اور اس نیت سے پڑھیں کہ ہم کو بھی قرآن سے دین سیکھنا ہے اور پھر اپنی اور اپنے گھر والوں کی اصلاح کرنی ہے، میں قسم کھا کر کہہ سکتا ہوں کہ آپ کو بہت فائدہ ہوگا اور آپ کو قرآن سے، اللہ تعالی سے اور اس کے پیارے رسول صلی اللہ علیہ وسلم سے اور زیادہ محبت ہو جائے گی۔ میری اس کوشش سے کسی ایک کو بھی ہدایت مل جائے گی، تو انشاءاللہ میری بھی مغفرت ہو جائے گی اور اس کی بھی۔\n\n" +
                            "اس کتاب کو امت کی آسانی کے لیے ایپلیکیشن میں سیٹ کروایا گیا تاکہ لوگ اس سے ہر جگہ فائدہ اٹھا سکیں، اس میں شیئر کا آپشن بھی دیا گیا ہے، جو ایمیج کی شکل میں شیئر ہوگا، اس میں عربی مواد، ترجمہ اور تفسیر تینوں چیزیں شیئر ہوگی، اس کتاب میں 104 ہیڈنگ کے تحت 370 آیات ہیں۔\n\n" +
                            "ساتھ میں اسی کے ضمن میں درود شریف کا بھی ایک سیکشن دیا گیا ہے، آپ جب کتاب کو کھولیں گے تو نیچے ایک ہرے کلر کی پٹی نظر آئے گی، اس پر کلک کرنے سے درود شریف اوپن ہو جائیں گے، جس میں مختلف فضائل کے ساتھ درود شریف دیے گئے ہیں، اس میں بھی شیئر کا آپشن دیا گیا ہے، جو ایمیج کی شکل میں شیئر ہوگا۔\n\n" +
                            "ان ساری کوششوں کا مقصد صرف ایک ہی ہے کہ ہم اور آپ اس پر عمل کرنے والے بن جائیں اور اللہ تعالی ہم سے راضی ہو جائے اور یہ ہماری نجات کا ذریعہ بن جائے.\n\n" +
                            "دعاؤں کا طالب (آپ کا دینی بھائی افروز  پھتا)\n\n" +
                            "");
                }
                else if(subtype.equalsIgnoreCase("aboutus"))
                {
                    txtAboutusUrdu.setText("باسمہ تعالیٰ\n" +
                            "السلام علیکم ورحمة اللہ وبرکاتہ\n\n" +
                            "\"میں بہت کم پڑھا لکھا آدمی ہوں، صرف اپنی مادری زبان گجراتی جانتا ہوں، دین سے کافی دور تھا، علمائے کرام سے تعلقات کی وجہ سے آہستہ آہستہ دین کے قریب آیا، مگر دنیوی جھمیلوں سے فرصت نہیں ملتی تھی کہ دین اور قرآن سیکھوں۔ ایسے میں میرے ساتھ ایک حادثہ پیش آیا اور مجھے جیل جانا پڑا، میں نے کبھی سوچا بھی نہیں تھا کہ میرے ساتھ ایسا معاملہ پیش آسکتا ہے، اس لیے میں بہت پریشان ہوگیا اور جیل میں ہمیشہ فکر مند بیٹھا رہتا تھا۔ ایک دن ایک بھائی نے مجھ سے کہا کہ بھائی! ایسے پریشان ہونے سے آپ جیل سے چھوٹنے والے تو ہے نہیں، تم ایک کام کرو؛ قرآن شریف پڑھنا شروع کرو اور اس کا ترجمہ بھی پڑھو۔ میں نے جیل میں قرآن شریف پڑھنا شروع کیا تو آہستہ آہستہ میرا ٹینشن کم ہونے لگا اور میرا زیادہ تروقت قرآن کے ساتھ گذرنے لگا اور دل میں ایک سکون سا محسوس ہونے لگا۔\n\n" +
                            "اس کے بعد میں نے ایسی خرابیوں کو سوچنا شروع کیا، جس میں مجھے اپنے معاشرے کی بداعتقادی (عقیدے کی خرابی) نظرآئی تھی، اور پھر ایسی آیتوں پر نشان لگانے شروع کئے، الحمد اللہ !جیل میں رہ کر میں نے پورے قرآن شریف کی آیتوں پر اس طرح نشان لگائے ۔ \n\n" +
                            "اس کے بعد اللہ تعالی نے شاید قرآن ہی کی برکت اور بزرگوں اور ماں باپ کی دعاوں سے مجھے جیل سے رہائی نصیب فرمائی اور میں جیل سے قرآن شریف کی محبت کو اپنے اندر لے کر نکلا۔ \n\n" +
                            "مجھے بہت خوشی ہوئی کہ اللہ تعالی نے مجھ جیسے گناہ گار سے اپنی عظیم کتاب قرآن کریم کا یہ بہت چھوٹا سا کام لیا ۔\n\n" +
                            "اس کے بعد میں نے دل سے ارادہ کیا کہ میں اسے شائع کراؤں، مگر کیسے؟ تو میں نے اپنے ایک عالم دوست مولانا حذیفہ وستانوی صاحب (دامت برکاتہم) اکل کوا سے کہا کہ میں نے جیل میں رہ کر ایسا ایسا کام کیا ہے، پھر میں نے ان کو اپنی نشان کئے ہوئے قرآن کے مضامین کی فہرست بتلائی تو انہوں نے فرمایا : ٹھیک ہے بھائی! آپ نے بہت ہی عمدہ کام کیا ہے۔ آپ یہ مجھے دے دو، تو میں نے ان کو دے دیا اور ماشاء اللہ انہوں نے جامعہ اکل کوا کے ایک استاذ مولانا مفتی طاہر صاحب بھڑکودروی کو یہ ذمہ داری دی، انہوں نے مولانا حذیفہ صاحب کی ماتحتی اور نگرانی میں قرآن کی آیتوں کا ترجمہ اور مختصر خلاصہ لیکر اس کو کمپوٹر پر لکھوایا اور اسے کتابی شکل دی۔ پھر اس مسودہ پر مولانا حسن صاحب بھڑکودروی اور حضرت مولانا عبد المنان صاحب منیار(سورتی) نے نظرِ ثانی کی اور اصلاح کے بعد مجھے دیکھنے کے لیے بھیجا۔ میں نے خود دیکھا اور اس میں جہاں جہاں تفسیر کی ضرورت تھی اس کا اضافہ کروایا، اور اس کے مضامین کو اس طرح مرتب کیا کہ”عنوانات“ سے ہی آدھی بات سمجھ میں آجاتی ہے اورمجھے بہت اچھا لگا۔ اب یہ کام جس کو میں نے کیا اور پھر دو معتبر عالموں نے دیکھا، اور مرتب کیا اس کو چھپوا رہا ہوں، میری نیت صرف یہ ہے کہ میرے بہت سارے مسلمان بھائی قرآن کو براہ راست نہ سمجھ نے کی وجہ سے سارے غلط عقیدے میں پھنسے ہوئے ہیں، وہ اس کے پڑھیں اوراس نیت سے پڑھیں کہ ان کو بھی قرآن سے براہ راست دین سیکھنا ہے اور پھر اپنی اور اپنے گھر والوں کی اصلاح کرنی ہے۔ میں قسم کھا کر کہہ سکتا ہوں کہ آپ کو بہت فائدہ ہوگا اور آپ کو قرآن سے، اللہ تعالی سے اور اس کے پیارے رسول جناب محمد مصطفی صلی اللہ علیہ وسلم سے محبت ہوجائے گی۔");
            }


        }
        else if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.ENGLISH)) {
            typeface = Typeface.createFromAsset(getAssets(), "fonts/BLKCHCRY.ttf");

            ((CipherNormal) findViewById(R.id.txtMainTitleEnglish)).setVisibility(View.VISIBLE);
            if(type.equalsIgnoreCase("peshlafz"))
                ((CipherNormal) findViewById(R.id.txtMainTitleEnglish)).setText("Foreword");
            else if(type.equalsIgnoreCase("aboutus")) {
                ((CipherNormal) findViewById(R.id.txtMainTitleEnglish)).setText("About Us");

            }
            pdfView.setVisibility(View.GONE);

            ( (ScrollView)findViewById(R.id.scrollView)).setVisibility(View.VISIBLE);

            if(subtype.equalsIgnoreCase("quran")) {
                txtAboutusUrdu.setText("BISMIHI TA'ALAA\n" +
                        "\nASSALAMU ALAIKUM WARAHMATULLAHI WABARAKATUH\n" +
                       "\nAs I have mentioned in my autobiography that while being in the prison, I read the translation & interpretation of the whole of the Quran and, consequently, perhaps ALLAH, the Almighty, facilitated my release from the prison due to the blessings of the Quran itself as well as Duaa (Supplications) of my parents & the senior pious persons of ALLAH. So, I came out from the prison with the attachment to the Quran in my heart. Subsequent to getting released from the prison, ALLAH, the Almighty, enabled me, though being a sinner, to accomplish such a great job related to HIS great Book of the Quran. So, I got the translation & interpretation of the Quran typed in the Urdu as well as in our mother tongue Gujarati language and got a particular Application prepared for the same so that the community at large can gain the benefits out of it and, consequently, they can access to the same solace & peace of heart as I have experienced in my heart.\n" +
                        "\nThe special feature of this Application is the fact that we have got it prepared on Text Basis so you can share & send its Arabic verses, translation & interpretation to anyone in the Text format. Moreover, this Application is fitted with the Options of Bookmark, Color etc. The special feature of the Color Feature is the fact that you can put the Arabic verses & translation in different colors also; apart from the Zoom in & Zoom out Option provided therein so that you can maximize & minimize the Arabic verses, translation & interpretation with the help of just two fingers on your screen. Besides, there are numerous other Features also fitted in the Application with the only objective to facilitate the community at large to gain its benefits and to secure the Pleasure of ALLAH in favor of all of us and may HE cause it to be a cause of salvation for me & all of you.\n" +
                        "\nThe seeker of your Duaa (your brother-in-Islam, Afroz Fatta).\""+
                        "");
            }
            else if(subtype.equalsIgnoreCase("dua"))
            {
                txtAboutusUrdu.setText("BISMIHI TA'ALAA.\n" +
                        "ASSALAMU ALAIKUM WARAHMATULLAHI WABARAKATUH\n"+
"\nAs I have mentioned in my autobiography that while being in the prison, I read the translation & interpretation of the whole of the Quran. Thereafter, I thought about the evils prevalent in the society which reflected to me the degradation in the Faith of the people in our society and, therefore, I started marking the verses of the Quran related to the Faith. Consequently, ALHAMDULILLAH! I have marked all the verses of the Quran accordingly while being in the prison.\n" +
        "\nMy intention after this job is just to get it read by my brothers-in-Islam because majority of them have been reeling under the wrong & baseless convictions as they have not understood the Quran directly. So, they should read it with the intention of learning the Islam religion directly through the Quran and to reform themselves & their family members accordingly. I do swear that you will definitely get immensely benefitted out of reading it which will generate the love & attachment with the Quran, ALLAH, the Almighty, & HIS beloved Messenger Muhammed (Peace and blessings be on him)\"\". If a single one gets on the True Path of Guidance as a result of my efforts, not only myself but he will also be granted the salvation, INSHA ALLAH.\n" +
        "\nThe same book has been set in the Application for the easily access to the same by the community at large so that people can gain the benefits all across the world. We have provided an Option of SHARE also in an Image form. So, one can share the Arabic text, translation & interpretation. This book contains 370 verses under 104 Headings.\n" +
        "\nWe have provided a separate Section for Durud Sharif also along with it. When you open the book, you would find a green stripe under it. Click on that stripe to open Durud Sharif; which contains Durud Sharif with various benefits thereof. We have provided the SHARE Option for Durud Sharif also in the image form.\n" +
        "\nThe only underlying intention after all these efforts is only to ensure the implementation upon it by me & you all; and may ALLAH, the Almighty, gets pleased with of all us; and may it become a cause of our salvation.\n" +
        "\nThe seeker of Duaa (Your brother-in-Islam; Afroz Fatta).\""+
                        "");
            }
            else if(subtype.equalsIgnoreCase("aboutus"))
            {
                txtAboutusUrdu.setText("BISMIHI TA'ALAA\n" +
                        "ASSALAMU ALAIKUM V.V.\n" +
                        "\nI am the least literate man. I know only my mother tongue Gujarati. I was very remote from the religion. I have gradually accessed to the religion as a result of an association with the Ulama-e-Kiram (The Clerics). But I couldn't get free from the worldly affairs to learn the Religion & the Quran. Meanwhile, I was struck by a tragedy in my life which landed me into the imprisonment. In fact, I didn't ever think that I would happen to confront such a calamity in my life and, as such, I got insurmountably troubled. I used to be always anxiously sitting in the prison. Once a man told me that you are not going to be released from the prison just by worriedly sitting in the prison; so, you should start reciting the Quran with its translation. Hence, I began reciting the Quran in the prison which gradually ameliorated my stress; and maximum time used to be dedicated to the recitation of the Quran and, consequently, I felt a sort of solace in my heart.\n" +
                                "\n\nThereafter, I thought about the evils prevalent in the society which reflected to me the degradation in the Faith of the people in our society and, therefore, I started marking the verses of the Quran related to the Faith. Consequently, ALHAMDULILLAH! I have marked all the verses of the Quran accordingly while being in the prison.\n" +
                                "\n\nThereafter, as a consequence of the Blessings of the Quran itself as well as the Duaa (Supplications) of my parents & the senior pious persons of ALLAH, I got released from the jail carrying the fascination to the Quran in my heart. I'm tremendously pleased that ALLAH enabled me, though being a sinner, to accomplish such a small job related to the Message of the greatest Book of the Quran.\n" +
                                "\nThereafter, I thought to get it published; but I was confused as to how to get it published. So, I informed my friend Maulana Huzaifa Vastanvi Sahab at Akkalkuwa that I have performed such a task related to the Quran and showed him the Index of the Topics of the Quran prepared by me in the prison. Thereupon, he commented that All Right! You have done a wonderful job and told me to hand it over to him. So, it was handed over to him and, in turn, MAASHA ALLAH he assigned the responsibility to Maulana Mufti Tahir Bhadkodravi being a teacher of Hifz Class at Jamiah Akkalkuwa; who picked up the translation & brief summaries of the said verses of the Quran to get it computerized under the leadership & supervision of Maulana Huzaifa Vastanvi Sahab and rendered it in a book form. Thereafter, the same script was reviewed by Maulvi Hasan Bhadkodravi Sahab & Hazrat Maulana Abdul Mannan Maniyar Surti Sahab and then after its final proofreading, it was again sent to me to go through the same corrected script. So, after going through it wherever the interpretation was warranted, I got it supplemented therein and its Topics were arranged in such a way that one can understand half of the content of the Topic just by reading its Heading only which pleased me immensely. Finally, what I have done followed by its proofreading & arrangement by two reliable Clerics is going to be punished now. My intention after this job is just to get it read by my brothers-in-Islam because majority of them have been reeling under the wrong & baseless convictions as they have not understood the Quran directly. So, they should read it with the intention of learning the Islam religion directly through the Quran and to reform themselves & their family members accordingly. I do swear that you will definitely get immensely benefitted out of reading it which will generate the love & attachment with the Quran, ALLAH, the Almighty, & HIS beloved Messenger Muhammed (Peace and blessings be on him)\"\".\""
                        );

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