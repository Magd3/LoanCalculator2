package com.loancalc.app

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.statusBarColor = Color.parseColor("#0a0a14")
        window.navigationBarColor = Color.parseColor("#0a0a14")

        val webView = WebView(this)
        setContentView(webView)

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            builtInZoomControls = false
            displayZoomControls = false
            loadWithOverviewMode = true
            useWideViewPort = true
            setSupportZoom(false)
        }
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        webView.setBackgroundColor(Color.parseColor("#08080f"))
        webView.loadDataWithBaseURL(null, HTML_CONTENT, "text/html", "UTF-8", null)
    }

    companion object {
        val HTML_CONTENT = """
<!DOCTYPE html>
<html lang="ar" dir="rtl">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>حاسبة القروض</title>
<link href="https://fonts.googleapis.com/css2?family=Cairo:wght@400;600;700;900&display=swap" rel="stylesheet">
<style>
*{box-sizing:border-box;margin:0;padding:0;-webkit-tap-highlight-color:transparent}
html,body{width:100%;min-height:100vh;background:#08080f;font-family:'Cairo',sans-serif;direction:rtl;overflow-x:hidden}
::-webkit-scrollbar{display:none}
.header{background:linear-gradient(160deg,#0d0d1a,#131326);padding:52px 18px 14px;border-bottom:1px solid #1f1f35}
.header-row{display:flex;align-items:center;gap:12px;justify-content:flex-end}
.app-icon{width:48px;height:48px;background:linear-gradient(135deg,#4f46e5,#7c3aed);border-radius:14px;display:flex;align-items:center;justify-content:center;font-size:22px;box-shadow:0 0 20px rgba(79,70,229,.5)}
.header-title{font-size:20px;font-weight:900;color:#e2e8f0}
.header-sub{font-size:11px;color:#6b7280}
.rate-badge{display:inline-flex;align-items:center;gap:8px;background:linear-gradient(135deg,#1a1232,#251848);border:1.5px solid #4f46e5;border-radius:30px;padding:7px 16px;margin:12px auto 4px;box-shadow:0 0 18px rgba(79,70,229,.3)}
.tabs{display:flex;gap:5px;background:#0a0a14;border-radius:12px;padding:4px;margin-top:10px;border:1px solid #1f1f35}
.tab{flex:1;padding:9px 4px;border:none;background:transparent;color:#6b7280;font-family:'Cairo',sans-serif;font-size:11px;font-weight:600;cursor:pointer;border-radius:10px;transition:all .3s}
.tab.active{background:linear-gradient(135deg,#4f46e5,#7c3aed);color:#fff;box-shadow:0 4px 15px rgba(79,70,229,.4)}
.content{padding:14px;padding-bottom:90px}
.tab-panel{display:none;flex-direction:column;gap:12px}
.tab-panel.active{display:flex}
.card{background:#1a1a2e;border-radius:18px;padding:16px;border:1px solid #2d2d4e}
.card-label{color:#9ca3af;font-size:12px;font-weight:700;display:flex;align-items:center;gap:6px;margin-bottom:10px}
.mode-row{display:flex;gap:8px}
.mode-btn{flex:1;padding:10px 6px;border:1.5px solid #2d2d4e;border-radius:12px;background:#111128;color:#6b7280;font-family:'Cairo',sans-serif;font-size:11px;font-weight:700;cursor:pointer;transition:all .3s}
.mode-btn.active{border-color:#4f46e5;background:linear-gradient(135deg,rgba(79,70,229,.2),rgba(124,58,237,.2));color:#a5b4fc}
.input-wrap{position:relative}
.fi{width:100%;padding:13px 16px;background:#111128;border:1.5px solid #2d2d4e;border-radius:14px;color:#e2e8f0;font-family:'Cairo',sans-serif;font-size:19px;font-weight:700;text-align:right;direction:rtl;outline:none;transition:all .3s}
.fi:focus{border-color:#4f46e5;box-shadow:0 0 0 3px rgba(79,70,229,.15)}
.fi-sfx{position:absolute;left:14px;top:50%;transform:translateY(-50%);font-size:13px;font-weight:700}
.maxbox{margin-top:12px;background:linear-gradient(135deg,#052e16,#064e3b);border-radius:12px;padding:12px 14px;border:1px solid #059669;display:flex;justify-content:space-between;align-items:center}
.maxbox .amt{color:#6ee7b7;font-size:20px;font-weight:900}
.maxbox .unit{color:#6ee7b7;font-size:10px;opacity:.7}
.maxbox .lbl{color:#34d399;font-size:12px;font-weight:700}
.maxbox .fml{color:#4b5563;font-size:11px}
.age-tl{margin-top:12px}
.tl-bar{height:10px;border-radius:5px;background:#0d0d1a;overflow:hidden;display:flex;gap:1px;margin:5px 0}
.bar-a{background:linear-gradient(90deg,#4f46e5,#818cf8);min-width:4px;transition:width .5s}
.bar-l{background:linear-gradient(90deg,#059669,#34d399);min-width:4px;transition:width .5s}
.bar-l.over{background:linear-gradient(90deg,#dc2626,#f87171)}
.bar-r{flex:1;background:#1f1f35}
.tl-leg{display:flex;justify-content:space-between;flex-wrap:wrap;gap:4px}
.leg-i{display:flex;align-items:center;gap:4px}
.leg-d{width:8px;height:8px;border-radius:2px}
.leg-t{color:#6b7280;font-size:10px}
.smsg{margin-top:10px;border-radius:10px;padding:10px 12px;font-size:12px;font-weight:700}
.smsg.ok{background:rgba(5,150,105,.1);border:1px solid #059669;color:#34d399}
.smsg.warn{background:rgba(217,119,6,.1);border:1px solid #d97706;color:#fbbf24}
.smsg.err{background:rgba(220,38,38,.15);border:1px solid #dc2626;color:#f87171}
.sl-hdr{display:flex;justify-content:space-between;align-items:center;margin-bottom:12px}
.sl-val{background:linear-gradient(135deg,#059669,#10b981);border-radius:10px;padding:4px 12px;color:white;font-size:17px;font-weight:900}
.sl-lbl{color:#9ca3af;font-size:12px;font-weight:700}
input[type=range]{-webkit-appearance:none;width:100%;height:6px;border-radius:3px;outline:none;cursor:pointer}
input[type=range]::-webkit-slider-thumb{-webkit-appearance:none;width:26px;height:26px;border-radius:50%;background:linear-gradient(135deg,#818cf8,#4f46e5);cursor:pointer;box-shadow:0 0 12px rgba(79,70,229,.6);border:2px solid #fff}
.presets{display:flex;gap:6px;margin-top:10px;flex-wrap:wrap}
.preset{padding:7px 11px;border-radius:20px;border:1px solid #2d2d4e;background:#111128;color:#9ca3af;font-family:'Cairo',sans-serif;font-size:11px;cursor:pointer;transition:all .2s}
.preset.active,.preset:active{background:linear-gradient(135deg,#4f46e5,#7c3aed);color:#fff;border-color:#4f46e5}
.preset.disabled{opacity:.3;pointer-events:none}
.slim{display:flex;justify-content:space-between;margin-top:6px}
.slim span{color:#4b5563;font-size:10px}
.hero{background:linear-gradient(135deg,#1e1b4b,#312e81);border-radius:18px;padding:18px;border:1px solid #4f46e5;text-align:center;box-shadow:0 8px 30px rgba(79,70,229,.25)}
.hero-lbl{color:#a5b4fc;font-size:12px;font-weight:700;margin-bottom:6px}
.hero-amt{color:white;font-size:34px;font-weight:900;letter-spacing:-1px}
.hero-amt span{font-size:15px;color:#a5b4fc;margin-right:6px}
.rbar-row{display:flex;justify-content:space-between;margin-bottom:4px;margin-top:10px}
.rbar{height:6px;border-radius:3px;background:#1e1b4b;overflow:hidden}
.rbar-fill{height:100%;border-radius:3px;transition:width .5s}
.sgrid{display:grid;grid-template-columns:1fr 1fr;gap:10px}
.scard{background:#1a1a2e;border:1px solid #2d2d4e;border-radius:14px;padding:13px;display:flex;flex-direction:column;gap:4px}
.scard .sl{color:#6b7280;font-size:10px}
.scard .sv{font-size:14px;font-weight:800}
.bdcard{background:#1a1a2e;border-radius:14px;padding:14px;border:1px solid #2d2d4e}
.bdrow{display:flex;justify-content:space-between;margin-bottom:8px}
.bdbar{height:10px;border-radius:5px;background:#0d0d1a;overflow:hidden;display:flex}
.bd-p{background:linear-gradient(90deg,#059669,#34d399);transition:width .5s}
.bd-i{flex:1;background:linear-gradient(90deg,#dc2626,#f87171)}
.blkcard{background:rgba(220,38,38,.1);border-radius:16px;padding:20px;border:1px solid #dc2626;text-align:center}
.sch-tbl{background:#1a1a2e;border-radius:16px;overflow:hidden;border:1px solid #2d2d4e}
.sch-hdr{display:grid;grid-template-columns:30px 1fr 1fr 1fr 1fr;gap:2px;padding:12px 8px;background:linear-gradient(135deg,#312e81,#1e1b4b);text-align:center}
.sch-hdr span{color:#a5b4fc;font-size:10px;font-weight:700}
.sch-row{display:grid;grid-template-columns:30px 1fr 1fr 1fr 1fr;gap:2px;padding:9px 8px;text-align:center;border-bottom:1px solid #1e1e35}
.sch-row:nth-child(even){background:rgba(79,70,229,.05)}
.sch-more{text-align:center;padding:10px;color:#4b5563;font-size:11px}
.donut-wrap{background:#1a1a2e;border-radius:20px;padding:20px;border:1px solid #2d2d4e;display:flex;flex-direction:column;align-items:center;gap:14px}
.dleg{display:flex;gap:20px}
.srow{background:#1a1a2e;border-radius:12px;padding:12px 14px;border:1px solid #2d2d4e;display:flex;justify-content:space-between;align-items:center}
.srow .s-lbl{display:flex;align-items:center;gap:6px;color:#9ca3af;font-size:12px}
.srow .s-val{font-size:14px;font-weight:800}
.empty{text-align:center;color:#4b5563;padding:60px 20px}
.empty .ei{font-size:36px;margin-bottom:10px}
.empty p{font-size:13px}
.bnav{position:fixed;bottom:0;left:0;right:0;background:#0a0a14;border-top:1px solid #1f1f35;padding:10px 20px 28px;display:flex;justify-content:space-around;z-index:100}
.bnav-btn{background:transparent;border:none;display:flex;flex-direction:column;align-items:center;gap:3px;cursor:pointer}
.bnav-btn span:last-child{color:#4b5563;font-size:9px;font-family:'Cairo',sans-serif}
@keyframes blink{0%,100%{opacity:1}50%{opacity:.5}}
.blink{animation:blink 1.4s ease-in-out infinite}
@keyframes fu{from{opacity:0;transform:translateY(12px)}to{opacity:1;transform:translateY(0)}}
.fu{animation:fu .3s ease forwards}
</style>
</head>
<body>

<div class="header">
  <div class="header-row">
    <div>
      <div class="header-title">حاسبة القروض</div>
      <div class="header-sub">الراتب × 90 | حد العمر 70 سنة</div>
    </div>
    <div class="app-icon">🏦</div>
  </div>
  <div style="display:flex;justify-content:center">
    <div class="rate-badge">
      <span>📌</span>
      <span style="color:#9ca3af;font-size:12px">الفائدة ثابتة</span>
      <span style="color:#818cf8;font-size:20px;font-weight:900">1.9%</span>
      <span style="color:#4b5563;font-size:11px">سنوياً</span>
    </div>
  </div>
  <div class="tabs">
    <button class="tab active" onclick="showTab('calc',this)">🧮 الحاسبة</button>
    <button class="tab" onclick="showTab('schedule',this)">📅 جدول</button>
    <button class="tab" onclick="showTab('summary',this)">📊 الملخص</button>
  </div>
</div>

<div class="content">

  <!-- CALC -->
  <div id="tab-calc" class="tab-panel active">
    <div class="mode-row">
      <button class="mode-btn active" id="btn-s" onclick="setMode('salary')">👤 احتساب من الراتب</button>
      <button class="mode-btn" id="btn-m" onclick="setMode('manual')">✏️ إدخال يدوي</button>
    </div>

    <div class="card" id="sec-s">
      <div class="card-label">👤 الراتب الشهري</div>
      <div class="input-wrap">
        <input class="fi" type="number" inputmode="numeric" id="salary" placeholder="أدخل الراتب" oninput="calc()">
        <span class="fi-sfx" style="color:#34d399">ر.س</span>
      </div>
      <div id="maxbox" style="display:none" class="maxbox">
        <div><div class="amt" id="maxval">0</div><div class="unit">ر.س</div></div>
        <div><div class="lbl">أقصى مبلغ قرض</div><div class="fml" id="maxfml">0 × 90</div></div>
      </div>
    </div>

    <div class="card" id="sec-m" style="display:none">
      <div class="card-label">💰 مبلغ القرض</div>
      <div class="input-wrap">
        <input class="fi" type="number" inputmode="numeric" id="mamount" placeholder="0" oninput="calc()">
        <span class="fi-sfx" style="color:#4f46e5">ر.س</span>
      </div>
    </div>

    <div class="card" id="age-card">
      <div class="card-label" id="age-lbl">👤 عمر العميل الحالي</div>
      <div class="input-wrap">
        <input class="fi" type="number" inputmode="numeric" id="cage" placeholder="أدخل العمر" min="18" max="69" oninput="calc()">
        <span class="fi-sfx" id="age-sfx" style="color:#9ca3af">سنة</span>
      </div>
      <div id="age-tl" style="display:none" class="age-tl">
        <div style="display:flex;justify-content:space-between;margin-bottom:5px">
          <span style="color:#4b5563;font-size:10px">70 سنة</span>
          <span style="color:#4b5563;font-size:10px">0</span>
        </div>
        <div class="tl-bar">
          <div class="bar-a" id="bar-a" style="width:0%"></div>
          <div class="bar-l" id="bar-l" style="width:0%"></div>
          <div class="bar-r"></div>
        </div>
        <div class="tl-leg">
          <div style="display:flex;gap:10px">
            <div class="leg-i"><div class="leg-d" style="background:#818cf8"></div><span class="leg-t" id="la">العمر: 0س</span></div>
            <div class="leg-i"><div class="leg-d" id="ld" style="background:#34d399"></div><span class="leg-t" id="ll">مدة: 0س</span></div>
          </div>
          <span style="font-size:10px;font-weight:700" id="ae">عند الانتهاء: -</span>
        </div>
      </div>
      <div id="age-msg"></div>
    </div>

    <div class="card">
      <div class="sl-hdr">
        <div>
          <div class="sl-val" id="dm-disp">60 <span style="font-size:10px;opacity:.7">شهر</span></div>
          <div id="lim-note" style="color:#fbbf24;font-size:10px;margin-top:2px;display:none">↓ محدود بالعمر</div>
        </div>
        <div class="sl-lbl">✂️ فترة الاستقطاع</div>
      </div>
      <input type="range" id="dslider" min="1" max="300" value="60" oninput="onSlider(this.value)">
      <div class="presets" id="presets"></div>
      <div id="alimit" style="display:none;margin-top:8px;align-items:center;gap:6px">
        <span style="color:#f59e0b;font-size:11px">⏱ الحد الأقصى:</span>
        <span style="color:#fbbf24;font-size:12px;font-weight:800" id="alval">300</span>
        <span style="color:#4b5563;font-size:10px" id="alyr"></span>
      </div>
      <div class="slim"><span>300 شهر</span><span>1 شهر</span></div>
    </div>

    <div id="res-sec" style="display:none;flex-direction:column;gap:10px">
      <div class="hero fu">
        <div class="hero-lbl">💳 القسط الشهري</div>
        <div class="hero-amt" id="mp-val">0 <span>ر.س</span></div>
        <div id="ratio-wrap" style="display:none">
          <div class="rbar-row">
            <span id="ratio-pct" style="font-size:11px;font-weight:700">0%</span>
            <span style="color:#4b5563;font-size:11px">نسبة القسط للراتب</span>
          </div>
          <div class="rbar"><div class="rbar-fill" id="ratio-fill" style="width:0%"></div></div>
        </div>
      </div>
      <div class="sgrid">
        <div class="scard"><span class="sl">مبلغ القرض</span><span class="sv" style="color:#818cf8" id="r-pr">0</span></div>
        <div class="scard"><span class="sl">إجمالي الفائدة</span><span class="sv" style="color:#f87171" id="r-int">0</span></div>
        <div class="scard"><span class="sl">إجمالي السداد</span><span class="sv" style="color:#34d399" id="r-tot">0</span></div>
        <div class="scard"><span class="sl">نسبة الفائدة الفعلية</span><span class="sv" style="color:#fbbf24" id="r-eff">0%</span></div>
      </div>
      <div class="bdcard">
        <div class="bdrow">
          <span style="color:#f87171;font-size:11px" id="bd-ip">فائدة 0%</span>
          <span style="color:#34d399;font-size:11px" id="bd-pp">أصل 100%</span>
        </div>
        <div class="bdbar"><div class="bd-p" id="bd-pbar" style="width:100%"></div><div class="bd-i"></div></div>
      </div>
    </div>

    <div id="blk-sec" style="display:none" class="blkcard blink">
      <div style="font-size:36px;margin-bottom:8px">⛔</div>
      <div style="color:#f87171;font-size:14px;font-weight:700">لا يمكن منح القرض</div>
      <div style="color:#6b7280;font-size:12px;margin-top:4px">العميل تجاوز الحد الأقصى للعمر (70 سنة)</div>
    </div>

    <div id="hint" style="text-align:center;color:#4b5563;padding:16px;font-size:13px">أدخل الراتب والعمر لحساب القرض</div>
  </div>

  <!-- SCHEDULE -->
  <div id="tab-schedule" class="tab-panel">
    <div id="sch-content"><div class="empty"><div class="ei">📋</div><p>أدخل بيانات القرض لعرض جدول السداد</p></div></div>
  </div>

  <!-- SUMMARY -->
  <div id="tab-summary" class="tab-panel">
    <div id="sum-content"><div class="empty"><div class="ei">📊</div><p>أدخل بيانات القرض لعرض الملخص</p></div></div>
  </div>

</div>

<div class="bnav">
  <button class="bnav-btn"><span>🏠</span><span>الرئيسية</span></button>
  <button class="bnav-btn"><span>📊</span><span>إحصائيات</span></button>
  <button class="bnav-btn"><span>⚙️</span><span>الإعدادات</span></button>
</div>

<script>
const RATE=1.9,MAX_AGE=70,SF=90;
const PST=[6,12,24,36,48,60,84,120,180,240,300];
let mode='salary',dm=60,maxA=300,sched=[],lastR=null;

const fmt=n=>new Intl.NumberFormat('ar-SA').format(Math.round((n||0)*100)/100);
const parse=v=>parseFloat(String(v).replace(/[^\d.]/g,''))||0;
const $=id=>document.getElementById(id);

function showTab(id,btn){
  document.querySelectorAll('.tab-panel').forEach(p=>p.classList.remove('active'));
  document.querySelectorAll('.tab').forEach(t=>t.classList.remove('active'));
  $('tab-'+id).classList.add('active');
  btn.classList.add('active');
  if(id==='schedule')renderSch();
  if(id==='summary')renderSum();
}

function setMode(m){
  mode=m;
  $('sec-s').style.display=m==='salary'?'block':'none';
  $('sec-m').style.display=m==='manual'?'block':'none';
  $('btn-s').classList.toggle('active',m==='salary');
  $('btn-m').classList.toggle('active',m==='manual');
  calc();
}

function buildPresets(){
  const row=$('presets');row.innerHTML='';
  PST.forEach(m=>{
    const b=document.createElement('button');
    b.className='preset'+(dm===m?' active':'')+(m>maxA?' disabled':'');
    b.textContent=m<12?`${m}ش`:`${m/12}س`;
    b.onclick=()=>{if(m<=maxA){dm=m;updateSl();calc();}};
    row.appendChild(b);
  });
}

function updateSl(){
  const s=$('dslider');
  s.max=maxA;s.value=Math.min(dm,maxA);dm=+s.value;
  const p=((dm-1)/299)*100;
  s.style.background=`linear-gradient(to left,#1f1f35 ${100-p}%,#059669 ${100-p}%)`;
  $('dm-disp').innerHTML=`${dm} <span style="font-size:10px;opacity:.7">شهر</span>`;
  buildPresets();
}

function onSlider(v){dm=Math.min(+v,maxA);updateSl();calc();}

function calc(){
  const sal=parse($('salary').value);
  const man=parse($('mamount').value);
  const age=parse($('cage').value);
  const pr=mode==='salary'?sal*SF:man;

  // salary box
  if(mode==='salary'&&sal>0){
    $('maxbox').style.display='flex';
    $('maxval').textContent=fmt(sal*SF);
    $('maxfml').textContent=`${fmt(sal)} × ${SF}`;
  } else $('maxbox').style.display='none';

  // age limits
  const blocked=age>=MAX_AGE;
  const rem=age>0?Math.max(0,(MAX_AGE-age)*12):300;
  maxA=Math.min(300,rem);
  if(dm>maxA)dm=Math.max(1,maxA);
  updateSl();

  const ageEnd=age>0?age+dm/12:null;

  // age limit note
  if(age>0&&maxA<300){
    $('alimit').style.display='flex';
    $('alval').textContent=maxA;
    $('alyr').textContent=`(${(maxA/12).toFixed(1)} سنة)`;
  } else $('alimit').style.display='none';

  $('lim-note').style.display=(age>0&&dm<maxA)?'none':'none'; // hidden unless clamped

  // age timeline
  const ac=document.getElementById('age-card');
  let st='none';
  if(blocked)st='blocked';
  else if(ageEnd>MAX_AGE)st='over';
  else if(ageEnd>=MAX_AGE-1)st='warn';
  else if(age>0)st='ok';

  const cl={none:['#1a1a2e','#2d2d4e','#9ca3af','👤'],ok:['#052e16','#059669','#34d399','✅'],warn:['#1c1400','#d97706','#fbbf24','⚠️'],over:['#2d0a0a','#dc2626','#f87171','🚫'],blocked:['#2d0a0a','#dc2626','#f87171','⛔']};
  const [bg,br,tx,ic]=cl[st];
  ac.style.background=bg;ac.style.borderColor=br;
  $('age-lbl').textContent=ic+' عمر العميل الحالي';
  $('age-sfx').style.color=tx;

  if(age>0&&!blocked){
    $('age-tl').style.display='block';
    const ap=Math.min((age/MAX_AGE)*100,100);
    const lp=Math.min((dm/12/MAX_AGE)*100,100-ap);
    $('bar-a').style.width=ap+'%';
    $('bar-l').style.width=lp+'%';
    const ov=ageEnd>MAX_AGE;
    $('bar-l').className='bar-l'+(ov?' over':'');
    $('ld').style.background=ov?'#f87171':'#34d399';
    $('la').textContent=`العمر: ${age}س`;
    $('ll').textContent=`مدة: ${(dm/12).toFixed(1)}س`;
    $('ae').textContent=`عند الانتهاء: ${ageEnd.toFixed(1)}س`;
    $('ae').style.color=ov?'#f87171':ageEnd>=MAX_AGE-1?'#fbbf24':'#34d399';
  } else $('age-tl').style.display='none';

  const msg=$('age-msg');
  if(st==='blocked')msg.innerHTML=`<div class="smsg err blink">⛔ العميل تجاوز الحد الأقصى للعمر (70 سنة). لا يمكن منح القرض.</div>`;
  else if(st==='over')msg.innerHTML=`<div class="smsg err">🚫 المدة تتجاوز سن 70. تم تقليل الاستقطاع تلقائياً إلى ${dm} شهر.</div>`;
  else if(st==='warn')msg.innerHTML=`<div class="smsg warn">⚠️ تنبيه: سيقترب العميل من سن 70 خلال فترة القرض.</div>`;
  else if(st==='ok')msg.innerHTML=`<div class="smsg ok">✅ مقبول — سيكون عمره ${ageEnd?.toFixed(1)} سنة عند انتهاء القرض.</div>`;
  else msg.innerHTML='';

  $('res-sec').style.display='none';
  $('blk-sec').style.display='none';
  $('hint').style.display='none';

  if(blocked){$('blk-sec').style.display='block';lastR=null;sched=[];return;}
  if(!pr||!dm){$('hint').style.display='block';lastR=null;sched=[];return;}

  const mr=RATE/100/12;
  const mp=(pr*mr*Math.pow(1+mr,dm))/(Math.pow(1+mr,dm)-1);
  const tot=mp*dm,tint=tot-pr,eff=(tint/pr)*100;

  lastR={mp,tot,tint,eff,pr,ageEnd,dm};

  let bal=pr;sched=[];
  for(let i=1;i<=dm;i++){
    const ip=bal*mr,pp=mp-ip;bal-=pp;
    sched.push({month:i,payment:mp,principal:pp,interest:ip,balance:Math.max(0,bal)});
  }

  $('res-sec').style.display='flex';
  $('mp-val').innerHTML=`${fmt(mp)} <span>ر.س</span>`;

  const sal2=parse($('salary').value);
  if(mode==='salary'&&sal2>0){
    const ratio=(mp/sal2)*100;
    $('ratio-wrap').style.display='block';
    $('ratio-pct').textContent=ratio.toFixed(1)+'% من الراتب';
    $('ratio-pct').style.color=ratio>50?'#f87171':ratio>33?'#fbbf24':'#34d399';
    $('ratio-fill').style.width=Math.min(ratio,100)+'%';
    $('ratio-fill').style.background=ratio>50?'linear-gradient(90deg,#dc2626,#f87171)':ratio>33?'linear-gradient(90deg,#d97706,#fbbf24)':'linear-gradient(90deg,#059669,#34d399)';
  } else $('ratio-wrap').style.display='none';

  $('r-pr').textContent=fmt(pr)+' ر.س';
  $('r-int').textContent=fmt(tint)+' ر.س';
  $('r-tot').textContent=fmt(tot)+' ر.س';
  $('r-eff').textContent=eff.toFixed(2)+'%';

  const pp=(pr/tot*100).toFixed(1),ip2=(tint/tot*100).toFixed(1);
  $('bd-pp').textContent=`أصل القرض ${pp}%`;
  $('bd-ip').textContent=`فائدة ${ip2}%`;
  $('bd-pbar').style.width=pp+'%';
}

function renderSch(){
  const el=$('sch-content');
  if(!sched.length){el.innerHTML='<div class="empty"><div class="ei">📋</div><p>أدخل بيانات القرض لعرض جدول السداد</p></div>';return;}
  let h='<div class="sch-tbl"><div class="sch-hdr"><span>#</span><span>قسط</span><span>أصل</span><span>فائدة</span><span>رصيد</span></div>';
  sched.slice(0,60).forEach(r=>{
    h+=`<div class="sch-row"><span style="color:#4f46e5;font-size:10px;font-weight:700">${r.month}</span><span style="color:#e2e8f0;font-size:10px">${Math.round(r.payment).toLocaleString()}</span><span style="color:#34d399;font-size:10px">${Math.round(r.principal).toLocaleString()}</span><span style="color:#f87171;font-size:10px">${Math.round(r.interest).toLocaleString()}</span><span style="color:#9ca3af;font-size:10px">${Math.round(r.balance).toLocaleString()}</span></div>`;
  });
  if(sched.length>60)h+=`<div class="sch-more">... ${sched.length-60} شهر إضافي (إجمالي ${sched.length} شهر)</div>`;
  el.innerHTML=h+'</div>';
}

function renderSum(){
  const el=$('sum-content');
  if(!lastR){el.innerHTML='<div class="empty"><div class="ei">📊</div><p>أدخل بيانات القرض لعرض الملخص</p></div>';return;}
  const r=lastR;
  const pp=(r.pr/r.tot*414.7).toFixed(1);
  const ip=(r.tint/r.tot*414.7).toFixed(1);
  const off2=(103.7-r.pr/r.tot*414.7).toFixed(1);
  let rows=[
    ['💰','مبلغ القرض',fmt(r.pr)+' ر.س','#818cf8'],
    ['📌','نسبة الفائدة','1.9% (ثابتة)','#a5b4fc'],
    ['✂️','فترة الاستقطاع',r.dm+' شهر','#34d399'],
    ['💳','القسط الشهري',fmt(r.mp)+' ر.س','#818cf8'],
    ['💸','إجمالي الفائدة',fmt(r.tint)+' ر.س','#f87171'],
    ['🏁','إجمالي السداد',fmt(r.tot)+' ر.س','#34d399'],
  ];
  if(r.ageEnd){
    const c=r.ageEnd>=MAX_AGE?'#f87171':r.ageEnd>=MAX_AGE-1?'#fbbf24':'#34d399';
    rows.push(['🎂','العمر عند الانتهاء',r.ageEnd.toFixed(1)+' سنة',c]);
  }
  el.innerHTML=`
    <div class="donut-wrap">
      <svg width="160" height="160" viewBox="0 0 160 160">
        <defs>
          <linearGradient id="gG" x1="0%" y1="0%" x2="100%" y2="0%"><stop offset="0%" stop-color="#059669"/><stop offset="100%" stop-color="#34d399"/></linearGradient>
          <linearGradient id="gR" x1="0%" y1="0%" x2="100%" y2="0%"><stop offset="0%" stop-color="#dc2626"/><stop offset="100%" stop-color="#f87171"/></linearGradient>
        </defs>
        <circle cx="80" cy="80" r="66" fill="none" stroke="#1f1f35" stroke-width="14"/>
        <circle cx="80" cy="80" r="66" fill="none" stroke="url(#gG)" stroke-width="14" stroke-dasharray="${pp} 414.7" stroke-dashoffset="103.7" stroke-linecap="round"/>
        <circle cx="80" cy="80" r="66" fill="none" stroke="url(#gR)" stroke-width="14" stroke-dasharray="${ip} 414.7" stroke-dashoffset="${off2}" stroke-linecap="round"/>
        <text x="80" y="68" text-anchor="middle" fill="#e2e8f0" font-size="17" font-weight="bold" font-family="Cairo">${fmt(r.tot)}</text>
        <text x="80" y="84" text-anchor="middle" fill="#6b7280" font-size="10" font-family="Cairo">إجمالي السداد (ر.س)</text>
        <text x="80" y="100" text-anchor="middle" fill="#818cf8" font-size="12" font-weight="bold" font-family="Cairo">فائدة 1.9% ثابتة</text>
      </svg>
      <div class="dleg">
        <div class="leg-i"><div class="leg-d" style="background:#34d399"></div><span class="leg-t">أصل القرض</span></div>
        <div class="leg-i"><div class="leg-d" style="background:#f87171"></div><span class="leg-t">الفائدة</span></div>
      </div>
    </div>
    ${rows.map(([i,l,v,c])=>`<div class="srow"><span class="s-val" style="color:${c}">${v}</span><span class="s-lbl"><span>${i}</span>${l}</span></div>`).join('')}
  `;
}

buildPresets();updateSl();
</script>
</body>
</html>
        """.trimIndent()
    }
}
