package com.tarihinibil.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(8, 11, 16));
        getWindow().setNavigationBarColor(Color.rgb(8, 11, 16));

        webView = new WebView(this);
        webView.setBackgroundColor(Color.rgb(8, 11, 16));
        webView.setWebViewClient(new WebViewClient() {
            @Override public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                injectFocusMode();
            }
        });
        webView.setWebChromeClient(new WebChromeClient());

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setTextZoom(100);
        settings.setMediaPlaybackRequiresUserGesture(false);

        setContentView(webView);
        webView.loadUrl("file:///android_asset/index.html");
    }

    private void injectFocusMode() {
        String js = "javascript:(function(){if(document.getElementById('tbFocus'))return;"+
                "var s=document.createElement('style');s.textContent='#tbFocus{position:fixed;right:16px;bottom:86px;z-index:40;border:1px solid #d9a84e;border-radius:16px;padding:11px 13px;background:linear-gradient(135deg,#f0b94f,#d99428);color:#211504;font-weight:900;box-shadow:0 10px 30px #0008}.tbModal{position:fixed;inset:0;z-index:60;display:grid;place-items:center;background:#05080ddd;padding:24px}.tbBox{width:min(420px,100%);background:#101b24;border:1px solid #3a4a57;border-radius:24px;padding:22px;text-align:center;box-shadow:0 25px 70px #000c}.tbTimer{font-size:58px;font-weight:950;color:#ffda82;margin:18px 0}.tbBox button{border:1px solid #304252;border-radius:14px;padding:11px 15px;background:#172633;color:#fff;font-weight:850;margin:4px}.tbBox .go{background:#ffda82;color:#211504;border:0}';document.head.appendChild(s);"+
                "var b=document.createElement('button');b.id='tbFocus';b.textContent='🎯 Odaklan';document.body.appendChild(b);"+
                "b.onclick=function(){var m=document.createElement('div');m.className='tbModal';m.innerHTML='<div class=tbBox><div class=tbTimer id=tbTimer>15:00</div><h2>Odak Çalışması</h2><p style=\"color:#91a0ad\">15 dakika boyunca sadece soru çöz. Süre bitince mola ver.</p><button class=go id=tbStart>Başlat</button><button id=tbClose>Kapat</button></div>';document.body.appendChild(m);var t=900,iv;document.getElementById('tbClose').onclick=function(){clearInterval(iv);m.remove()};document.getElementById('tbStart').onclick=function(){if(iv)return;this.disabled=true;iv=setInterval(function(){t--;var mm=Math.floor(t/60),ss=t%60;document.getElementById('tbTimer').textContent=mm+':'+String(ss).padStart(2,'0');if(t<=0){clearInterval(iv);document.getElementById('tbTimer').textContent='🎉 Süre doldu!';}},1000)};};})();";
        webView.evaluateJavascript(js, null);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }
}
