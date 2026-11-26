package com.chalo.rides;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.view.View;
import android.graphics.Color;
import android.os.Build;
import android.util.Base64;
import java.io.OutputStream;

public class MainActivity extends Activity {
    private static final int REQUEST_OPEN_JSON = 4101, REQUEST_SAVE_FILE = 4102;
    private WebView webView;
    private ValueCallback<Uri[]> fileChooserCallback;
    private String pendingFileName, pendingMimeType, pendingBase64;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(251,247,246));
        getWindow().setNavigationBarColor(Color.rgb(251,247,246));
        if (Build.VERSION.SDK_INT >= 26) getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        webView = new WebView(this);
        webView.setBackgroundColor(Color.rgb(251,247,246));
        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true); s.setDomStorageEnabled(true); s.setDatabaseEnabled(true);
        s.setAllowFileAccess(true); s.setAllowContentAccess(true); s.setSupportZoom(false);
        s.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        webView.addJavascriptInterface(new ChaloBridge(), "ChaloNative");
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> callback, FileChooserParams params) {
                if (fileChooserCallback != null) fileChooserCallback.onReceiveValue(null);
                fileChooserCallback = callback;
                Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                i.addCategory(Intent.CATEGORY_OPENABLE); i.setType("application/json");
                try { startActivityForResult(i, REQUEST_OPEN_JSON); }
                catch (Exception e) { fileChooserCallback = null; callback.onReceiveValue(null); }
                return true;
            }
        });
        setContentView(webView);
        webView.loadUrl("file:///android_asset/www/index.html");
    }
    private class ChaloBridge {
        @JavascriptInterface public void saveFile(String name, String mime, String data) {
            runOnUiThread(() -> {
                pendingFileName=name; pendingMimeType=mime; pendingBase64=data;
                Intent i=new Intent(Intent.ACTION_CREATE_DOCUMENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType(mime==null||mime.isEmpty()?"application/octet-stream":mime);
                i.putExtra(Intent.EXTRA_TITLE,name);
                try { startActivityForResult(i,REQUEST_SAVE_FILE); }
                catch(Exception e) { Toast.makeText(MainActivity.this,"Unable to save file",Toast.LENGTH_SHORT).show(); }
            });
        }
    }
    @Override protected void onActivityResult(int request,int result,Intent data) {
        super.onActivityResult(request,result,data);
        if(request==REQUEST_OPEN_JSON) {
            if(fileChooserCallback!=null) {
                fileChooserCallback.onReceiveValue(result==RESULT_OK&&data!=null&&data.getData()!=null?new Uri[]{data.getData()}:null);
                fileChooserCallback=null;
            }
        } else if(request==REQUEST_SAVE_FILE) {
            if(result==RESULT_OK&&data!=null&&data.getData()!=null&&pendingBase64!=null) {
                try(OutputStream out=getContentResolver().openOutputStream(data.getData())) {
                    if(out!=null) { out.write(Base64.decode(pendingBase64,Base64.DEFAULT)); Toast.makeText(this,"File saved",Toast.LENGTH_SHORT).show(); }
                } catch(Exception e) { Toast.makeText(this,"Could not save file",Toast.LENGTH_LONG).show(); }
            }
            pendingFileName=pendingMimeType=pendingBase64=null;
        }
    }
    @Override public void onBackPressed() { if(webView!=null&&webView.canGoBack()) webView.goBack(); else super.onBackPressed(); }
    @Override protected void onDestroy() { if(webView!=null) { webView.removeJavascriptInterface("ChaloNative"); webView.destroy(); webView=null; } super.onDestroy(); }
}
