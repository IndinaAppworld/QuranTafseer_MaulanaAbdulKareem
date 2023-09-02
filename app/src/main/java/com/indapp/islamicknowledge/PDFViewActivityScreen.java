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
        txtAboutusUrdu=(TextView)findViewById(R.id.txtAboutUsUrdu);

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
                            "અસ્સલામુ અલૈકુમ વ. વ.\n" +
                            "જૈસા કે મૈંને અપની આપ બીતી મેં બતાયા થા કે જેલ મેં રેહ કર મૈંને પૂરે કુર્આન શરીફ કો તર્જમા ઔર તફસીર કે સાથ પઢા, ઉસ કે બાદ મૈંને ઐસી ખરાબિયોં કો સોચના શુરૂ કિયા, જિસ મેં મુજે અપને મુઆશરે કી બદ-એ’તિકાદી (અકીદે કી ખરાબી) નઝર આઈ થી, ઔર ફિર ઐસી આયતોં પર નિશાન લગાને શુરૂ કિયે, અલ્હમ્દુ લિલ્લાહ! જેલ મેં રેહ કર મૈંને પૂરે કુર્આન શરીફ કી આયતોં પર ઇસ તરહ નિશાન લગાએ.\n" +
                            "મેરી નિય્યત સિર્ફ યહ હૈ કે મેરે બહુત સારે મુસલમાન ભાઈ કુર્આન કો ન સમઝને કી વજહ સે બહુત સારે ગલત અકીદે મેં ફંસે હુવે હૈં, વહ ઇસ કો પઢેં ઔર ઇસ નિય્યત સે પઢેં કે હમ કો ભી કુર્આન સે દીન સીખના હૈ ઔર ફિર અપની ઔર અપને ઘરવાલોં કી ઇસ્લાહ કરની હૈ, મૈં કસમ ખા કર કેહ સકતા હૂં કે આપકો બહુત ફાઇદા હોગા ઔર આપ કો કુર્આન સે, અલ્લાહ તઆલા સે ઔર ઉસકે પ્યારે રસૂલ (સલ્લલ્લાહુ અલૈહિ વસલ્લમ) સે મુહબ્બત ઔર ઝ્યાદા હો જાએગી. મેરી ઇસ કોશિશ સે કિસી એક કો ભી હિદાયત મિલ જાએગી, તો ઇન્શા અલ્લાહ મેરી ભી મગફિરત હો જાએગી ઔર ઉસ કી ભી. \n" +
                            "ઉસી કિતાબ કો ઉમ્મત કી આસાની કે લિયે એપ્લીકેશન મેં સેટ કરવાયા ગયા તાકે લોગ ઇસ સે હર જગહ ફાઇદા ઉઠા સકે, ઇસ મેં શેર કા ઓપ્શન ભી દિયા ગયા હૈ, જો ઈમેજ કી શકલ મેં શેર હોગા, ઇસ મેં અરબી મવાદ, તર્જમા ઔર તફસીર તીનોં ચીઝેં શેર હોગી, ઇસ કિતાબ મેં 104 હેડીંગ કે તહત 370 આયાત હૈ.\n" +
                            "સાથ મેં ઇસી કે ઝિમ્ન મેં દુરૂદ શરીફ કા ભી એક સેકશન દિયા ગયા હૈ, આપ જબ કિતાબ કો ખોલેંગે તો નીચે એક હરે કલર કી પટ્ટી નઝર આએગી ઉસ પર ક્લિક કરને સે દુરૂદ શરીફ ઓપન હો જાએંગે, જિસ મેં મુખ્તલિફ ફઝાઇલ કે સાથ દુરૂદ શરીફ દિયે ગએ હૈં. \n" +
                            "ઇન સારી કોશિશોં કા મકસદ સિર્ફ એક હી હૈ હમ ઔર આપ ઇસ પર અમલ કરને વાલે બન જાએ ઔર અલ્લાહ તઆલા હમસે રાઝી હો જાએ, ઔર યે હમારી નજાત કા ઝરીયા બન જાએ.\n" +
                            "દુઆઓં કા તાલિબ (આપ કા દીની ભાઈ અફરોઝ ફત્તા)");
                }


        } else if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU)) {
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
                            "السلام علیکم ورحمة اللہ وبرکاتہ\n" +
                            "\"جیسا کہ میں نے اپنی آپ بیتی میں بتایا تھا کہ جیل میں رہ کر میں نے پورے قرآن شریف کو ترجمہ اور تفسیر کے ساتھ پڑھا، اس کے بعد اللہ تعالی نے شاید قرآن ہی برکت اور بزرگوں اور ماں باپ کی دعاؤں سے مجھے جیل سے رہائی نصیب فرمائی اور میں جیل سے قرآن شریف کی محبت کو اپنے اندر لے کر نکلا، رہائی کے بعد قرآن کی اسی محبت کی وجہ سے اللہ تعالی نے مجھ جیسے گنہگار سے اپنی عظیم کتاب قرآن کریم کا یہ کام لیا، کہ اس قرآن کریم کے ترجمہ اور تفسیر کو اردو اور اپنی مادری زبان گجراتی میں ٹائپ کروا کر اس کا ایپلیکیشن بنوایا تاکہ امت اس سے فائدہ حاصل کرے اور جو سکون میں نے محسوس کیا وہ امت کا ہر فرد حاصل کرے۔\n" +
                            "اس ایپلیکیشن کی خوبی یہ ہے کہ اس کو ہم نے ٹیکس بیس پر بنایا ہے اس کا فائدہ یہ ہے کہ آپ عربی آیات، ترجمہ اور تفسیر کو ٹیکس کی شکل میں کسی کو بھی شیئر کر سکتے ہو/بھیج سکتے ہو۔ ساتھ ہی اس میں بک مارک، کلر وغیرہ آپشن دیے گئے ہیں، کلر کی خوبی یہ ہے کہ عربی آیات اور ترجمہ کو الگ الگ کلر میں بھی رکھ سکتے ہو، ساتھ ہی زوم ان اور زوم اؤٹ کا اپشن بھی دیا گیا ہے یعنی آپ اسکرین میں دو انگلیوں کے ذریعے آیات، ترجمہ اور تفسیر کو چھوٹا، بڑا کر سکتے ہو اور بھی بہت ساری خوبیاں اس میں موجود ہیں۔ ان سب کا مقصد صرف یہ ہے کہ امت اس سے فائدہ اٹھائے اور اللہ تعالی ہم سے راضی ہو جائے اور یہ ہماری اور آپ کی نجات کا ذریعہ بن جائے۔\n" +
                            "دعاؤں کا طالب (آپ کا دینی بھائی افروز  پھتا)\n" +
                            "");
                }
                else if(subtype.equalsIgnoreCase("dua"))
                {
                    txtAboutusUrdu.setText("باسمہ تعالیٰ\n" +
                            "السلام علیکم ورحمة اللہ وبرکاتہ\n" +
                            "\"جیسا کہ میں نے اپنی آپ بیتی میں بتایا تھا کہ جیل میں رہ کر میں نے پورے قرآن شریف کو ترجمہ اور تفسیر کے ساتھ پڑھا، اس کے بعد میں نے ایسی خرابیوں کو سوچنا شروع کیا جس میں مجھے اپنے معاشرے کی بد اعتقادی یعنی عقیدے کی خرابی نظر آئی تھی، اور پھر ایسی آیتوں پر نشان لگانے شروع کیے، الحمدللہ! جیل میں رہ کر میں نے پورے قرآن شریف کی آیتوں پر اس طرح نشان لگائے۔\n" +
                            "میری نیت صرف یہ ہے کہ میرے بہت سارے مسلمان بھائی قرآن کو نہ سمجھنے کی وجہ سے بہت سارے غلط عقیدے میں پھنسے ہوئے ہیں، وہ اس کو پڑھیں اور اس نیت سے پڑھیں کہ ہم کو بھی قرآن سے دین سیکھنا ہے اور پھر اپنی اور اپنے گھر والوں کی اصلاح کرنی ہے، میں قسم کھا کر کہہ سکتا ہوں کہ آپ کو بہت فائدہ ہوگا اور آپ کو قرآن سے، اللہ تعالی سے اور اس کے پیارے رسول صلی اللہ علیہ وسلم سے اور زیادہ محبت ہو جائے گی۔ میری اس کوشش سے کسی ایک کو بھی ہدایت مل جائے گی، تو انشاءاللہ میری بھی مغفرت ہو جائے گی اور اس کی بھی۔\n" +
                            "اس کتاب کو امت کی آسانی کے لیے ایپلیکیشن میں سیٹ کروایا گیا تاکہ لوگ اس سے ہر جگہ فائدہ اٹھا سکیں، اس میں شیئر کا آپشن بھی دیا گیا ہے، جو ایمیج کی شکل میں شیئر ہوگا، اس میں عربی مواد، ترجمہ اور تفسیر تینوں چیزیں شیئر ہوگی، اس کتاب میں 104 ہیڈنگ کے تحت 370 آیات ہیں۔\n" +
                            "ساتھ میں اسی کے ضمن میں درود شریف کا بھی ایک سیکشن دیا گیا ہے، آپ جب کتاب کو کھولیں گے تو نیچے ایک ہرے کلر کی پٹی نظر آئے گی، اس پر کلک کرنے سے درود شریف اوپن ہو جائیں گے، جس میں مختلف فضائل کے ساتھ درود شریف دیے گئے ہیں، اس میں بھی شیئر کا آپشن دیا گیا ہے، جو ایمیج کی شکل میں شیئر ہوگا۔\n" +
                            "ان ساری کوششوں کا مقصد صرف ایک ہی ہے کہ ہم اور آپ اس پر عمل کرنے والے بن جائیں اور اللہ تعالی ہم سے راضی ہو جائے اور یہ ہماری نجات کا ذریعہ بن جائے.\n" +
                            "دعاؤں کا طالب (آپ کا دینی بھائی افروز  پھتا)\n" +
                            "");
                }
                else if(subtype.equalsIgnoreCase("aboutus"))
                {
                    txtAboutusUrdu.setText("باسمہ تعالیٰ\n" +
                            "السلام علیکم ورحمة اللہ وبرکاتہ\n" +
                            "\"میں بہت کم پڑھا لکھا آدمی ہوں، صرف اپنی مادری زبان گجراتی جانتا ہوں، دین سے کافی دور تھا، علمائے کرام سے تعلقات کی وجہ سے آہستہ آہستہ دین کے قریب آیا، مگر دنیوی جھمیلوں سے فرصت نہیں ملتی تھی کہ دین اور قرآن سیکھوں۔ ایسے میں میرے ساتھ ایک حادثہ پیش آیا اور مجھے جیل جانا پڑا، میں نے کبھی سوچا بھی نہیں تھا کہ میرے ساتھ ایسا معاملہ پیش آسکتا ہے، اس لیے میں بہت پریشان ہوگیا اور جیل میں ہمیشہ فکر مند بیٹھا رہتا تھا۔ ایک دن ایک بھائی نے مجھ سے کہا کہ بھائی! ایسے پریشان ہونے سے آپ جیل سے چھوٹنے والے تو ہے نہیں، تم ایک کام کرو؛ قرآن شریف پڑھنا شروع کرو اور اس کا ترجمہ بھی پڑھو۔ میں نے جیل میں قرآن شریف پڑھنا شروع کیا تو آہستہ آہستہ میرا ٹینشن کم ہونے لگا اور میرا زیادہ تروقت قرآن کے ساتھ گذرنے لگا اور دل میں ایک سکون سا محسوس ہونے لگا۔\n" +
                            "اس کے بعد میں نے ایسی خرابیوں کو سوچنا شروع کیا، جس میں مجھے اپنے معاشرے کی بداعتقادی (عقیدے کی خرابی) نظرآئی تھی، اور پھر ایسی آیتوں پر نشان لگانے شروع کئے، الحمد اللہ !جیل میں رہ کر میں نے پورے قرآن شریف کی آیتوں پر اس طرح نشان لگائے ۔ \n" +
                            "اس کے بعد اللہ تعالی نے شاید قرآن ہی کی برکت اور بزرگوں اور ماں باپ کی دعاوں سے مجھے جیل سے رہائی نصیب فرمائی اور میں جیل سے قرآن شریف کی محبت کو اپنے اندر لے کر نکلا۔ \n" +
                            "مجھے بہت خوشی ہوئی کہ اللہ تعالی نے مجھ جیسے گناہ گار سے اپنی عظیم کتاب قرآن کریم کا یہ بہت چھوٹا سا کام لیا ۔\n" +
                            "اس کے بعد میں نے دل سے ارادہ کیا کہ میں اسے شائع کراؤں، مگر کیسے؟ تو میں نے اپنے ایک عالم دوست مولانا حذیفہ وستانوی صاحب (دامت برکاتہم) اکل کوا سے کہا کہ میں نے جیل میں رہ کر ایسا ایسا کام کیا ہے، پھر میں نے ان کو اپنی نشان کئے ہوئے قرآن کے مضامین کی فہرست بتلائی تو انہوں نے فرمایا : ٹھیک ہے بھائی! آپ نے بہت ہی عمدہ کام کیا ہے۔ آپ یہ مجھے دے دو، تو میں نے ان کو دے دیا اور ماشاء اللہ انہوں نے جامعہ اکل کوا کے ایک استاذ مولانا مفتی طاہر صاحب بھڑکودروی کو یہ ذمہ داری دی، انہوں نے مولانا حذیفہ صاحب کی ماتحتی اور نگرانی میں قرآن کی آیتوں کا ترجمہ اور مختصر خلاصہ لیکر اس کو کمپوٹر پر لکھوایا اور اسے کتابی شکل دی۔ پھر اس مسودہ پر مولانا حسن صاحب بھڑکودروی اور حضرت مولانا عبد المنان صاحب منیار(سورتی) نے نظرِ ثانی کی اور اصلاح کے بعد مجھے دیکھنے کے لیے بھیجا۔ میں نے خود دیکھا اور اس میں جہاں جہاں تفسیر کی ضرورت تھی اس کا اضافہ کروایا، اور اس کے مضامین کو اس طرح مرتب کیا کہ”عنوانات“ سے ہی آدھی بات سمجھ میں آجاتی ہے اورمجھے بہت اچھا لگا۔ اب یہ کام جس کو میں نے کیا اور پھر دو معتبر عالموں نے دیکھا، اور مرتب کیا اس کو چھپوا رہا ہوں، میری نیت صرف یہ ہے کہ میرے بہت سارے مسلمان بھائی قرآن کو براہ راست نہ سمجھ نے کی وجہ سے سارے غلط عقیدے میں پھنسے ہوئے ہیں، وہ اس کے پڑھیں اوراس نیت سے پڑھیں کہ ان کو بھی قرآن سے براہ راست دین سیکھنا ہے اور پھر اپنی اور اپنے گھر والوں کی اصلاح کرنی ہے۔ میں قسم کھا کر کہہ سکتا ہوں کہ آپ کو بہت فائدہ ہوگا اور آپ کو قرآن سے، اللہ تعالی سے اور اس کے پیارے رسول جناب محمد مصطفی صلی اللہ علیہ وسلم سے اور زیادہ محبت ہوجائے گی۔\"\n" +
                            "\"میری اس کتاب کو پڑھ کر کسی ایک کو بھی ہدایت مل جائے گی، تو انشاء اللہ میری بھی مغفرت ہوجائے گی اور اس کی بھی۔ میں آپ کا گناہ گار بھائی ”افروز بھائی پھتا“ آپ کواللہ کا واستہ دے کر کہتا ہوں کے آپ فرقہ بندی کا بلکل نہ سوچیں، اور صرف دین سمجھنے کے لیے اس کتاب کا ایک مرتبہ ضرور مطالعہ کریں۔ میرا مقصد صرف اور صرف اللہ کی رضامندی حاصل کرنا ہے اور یہ ہے کہ اللہ تعالی ہم سب سے راضی ہوجائے۔\n" +
                            "میرے پیارے بھائیواور دوستوں! اللہ اس کے پیارے حبیب صلی اللہ علیہ وسلم ہم سے راضی ہوگئے تو آپ یوں سمجھو کہ ہم کامیاب ہوگئے۔ اصل کامیابی یہی ہے کہ ہم سب کا پروردگار ہم سے راضی ہوجائے اور ہم آخرت میں اللہ اور ہمارے پیارے حبیب صلی اللہ علیہ وسلم کا دیدار کرسکیں، اور ہمیشہ ہمیشہ کی زندگی میں سکون حاصل ہوجائے، تو آئیے! ہم قرآن کوسمجھنے کی کوشش کرتے ہیں۔\n" +
                            "اللہ تعالی میرے اس چھوٹے سے کام کو شرف قبولیت عطافرمائے اور امت کو اس سے خوب فائدہ پہنچائے اور جن دوستوں نے میرا علمی تعاون کیا ہے اللہ تعالی ان کو دارین میں بہترین بدلہ عطافرمائیں اور ہم سب کو اللہ کا، قرآن کا، اور ہم سب کے پیارے اور چہیتے رسول حضرت محمد مصطفی صلی اللہ علیہ وسلم کا عشق عطا فرمائے اور ہر طرح کی گمراہی اور ایسے کام سے ہماری حفاظت فرمائے جو قرآن اور فرمان رسول صلی اللہ علیہ وسلم کے خلاف ہو اور ایسے کام کی توفیق عطا فرمائے جو اس کی مرضی کے مطابق ہو…آمین، یا رب العالمین!۔\n" +
                            "اگر آپ کو اس بہترین کتاب سے فائدہ ہو یا آپ کوئی مشورہ دینا چاہتے ہوں تو مندرجہ ذیل ای میل ادریس پر ہم سے رابطہ کرسکتے ہیں۔\n" +
                            "contactfortashreehulquran@gmail.com\"\n" +
                            "\"دعاؤں کا طالب (آپ کا دینی بھائی افروز  پھتا)\n" +
                            "نوٹ :اس کتاب میں زیادہ ترترجمہ اور تفسیر مولانا عبد الکریم  پاریکھ صاحب (رحمة اللہ علیہ ) کی تفسیر تشریح القرآن میں سے لیا گیا ہے ۔\"\n" +
                            "\"عزائم :\n" +
                            "۱) جیسا کے ہم نے مزید دو زبانوں (ہندی اور انگریزی) میں  اس کا ترجمہ اور تفسیر کر کے اسی ایپ میں ڈالنے کا وعدہ کیا تھا الحمد لللہ انگریزی زبان میں کام شروع ہو چکا ہے۔\n" +
                            "۲) جیسا کہ ہم نے اللہ کی رضامندی کیسے حاصل کریں؟ یہ کتاب ایڈ کرنے کا وعدہ کیا تھا جو اردو اور گجراتی زبان میں ایڈ ہو چکی ہے، یہ کتاب بھی (انگریزی اور ہندی) زبان میں بہت جلد آئیگی۔ ان شاء اللہ۔\n" +
                            "۳) روزآنہ کا ایک دینی میسیج بھیجنے کے لئے ۳۶۵ میسیجیس بھی ایڈ کیے جائینگے، ان شاء اللہ۔\"");

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