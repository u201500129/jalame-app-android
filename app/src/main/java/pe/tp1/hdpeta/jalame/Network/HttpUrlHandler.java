package pe.tp1.hdpeta.jalame.Network;

import android.os.NetworkOnMainThreadException;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUrlHandler {
    private String method = "GET";
    private String path;
    private String fullPath = "http://services.tarrillobarba.com.pe:6789/jalame/";
    private String jsonString;
    private JSONObject jsonObjet;

    public HttpUrlHandler() {
    }

    public HttpUrlHandler(String method, String path) {
        this.method = method;
        this.path = path;
    }


    public boolean readREST(){
        boolean resultado = false;
        fullPath += path;

        //ESTO DEBE ESTAR EN UN THEREAD o ASYNC TASK

         try {
            URL restServiceURL = new URL(fullPath);

            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod(method);

            httpConnection.setRequestProperty("Accept", "application/json");

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
                jsonString = responseBuffer.readLine();
                resultado = true;
            }else {
                Log.w("RESTfull READ: ",  "Url:" + fullPath + "  \n Codigo:" + httpConnection.getResponseCode() + " " + httpConnection.getResponseMessage());
            }

            httpConnection.disconnect();


        } catch (MalformedURLException e) {
            Log.w("RESTfull", "ERROR: " + "HTTP URL: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.w("RESTfull", "ERROR: " + "HTTP IOE: " + e.getMessage());
            e.printStackTrace();
        }
        return  resultado;
    }




    private  boolean  writeREST(){
        boolean resultado = false;
        fullPath += path;

        //ESTO DEBE ESTAR EN UN THEREAD o ASYNC TASK

        HttpURLConnection httpConnection;

        try {
            //Connect
            httpConnection = (HttpURLConnection) ((new URL (fullPath).openConnection()));
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            httpConnection.setUseCaches (false);
            httpConnection.setInstanceFollowRedirects(false);
            httpConnection.setRequestMethod("PUT");
            httpConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.connect();

            if (jsonObjet != null && !jsonString.equals("")) {

                //Write
                DataOutputStream os = new DataOutputStream(httpConnection.getOutputStream());
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                if (jsonObjet != null) {
                    os.writeBytes(jsonObjet.toString());
                } else if (!jsonString.equals("")) {
                    os.writeBytes(jsonString);
                }

                os.flush();
                os.close();

            }

            //en caso sea Success leemos respuesta
            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
                jsonString = responseBuffer.readLine();
                resultado = true;
            }else {
                Log.w("RESTfull Read: ", "Url:" + fullPath + "  \n Codigo:" + httpConnection.getResponseCode() + " " + httpConnection.getResponseMessage());
            }

            //Desconectar
            httpConnection.disconnect();

        } catch (NetworkOnMainThreadException e) {
            Log.w("RESTfull", "ERROR: " + "NET: " + e.getMessage());
            System.out.println("ERROR Net: \n" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            Log.w("RESTfull", "ERROR: " + "ENCODE: " + e.getMessage());
            System.out.println("ERROR Encode: \n" + e.getMessage());
        } catch (IOException e) {
            Log.w("RESTfull", "ERROR: " + "I/O: " + e.getMessage());
            System.out.println("ERROR Except: \n" + e.getMessage());
        }
        return resultado;
    }





    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public JSONObject getJsonObjet() {
        return jsonObjet;
    }

    public void setJsonObjet(JSONObject jsonObjet) {
        this.jsonObjet = jsonObjet;
    }




}
