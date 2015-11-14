package com.example.ali.files;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    RelativeLayout containerLayout;

   // @Override
  //  protected void onCreate(Bundle savedInstanceState) {
   //     super.onCreate(savedInstanceState);
  //      setContentView(R.layout.activity_main);
  //  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        containerLayout = (RelativeLayout)findViewById(R.id.Files);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    EditText textmsg;
    static final int READ_BLOCK_SIZE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textmsg=(EditText)findViewById(R.id.editText1);
    }

    // write text to file
    public void WriteBtn(View v) {
        // add-write text into file
        try {
            FileOutputStream fileout=openFileOutput("mytextfile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(textmsg.getText().toString());
            outputWriter.close();

            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Read text from file
    public void ReadBtn(View v) {
        //reading text from file
        try {
            FileInputStream fileIn=openFileInput("mytextfile.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[READ_BLOCK_SIZE];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
            Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick1(View v) {
        try {
        String filename = "file.txt";

        FileOutputStream fos;
        fos = openFileOutput(filename,MODE_PRIVATE);


        XmlSerializer serializer = Xml.newSerializer();
        serializer.setOutput(fos, "UTF-8");
        serializer.startDocument(null, Boolean.valueOf(true));
        serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        serializer.startTag(null, "root");

        for(int j = 0 ; j < 3 ; j++)
        {

            serializer.startTag(null, "record");

            serializer.text(String.valueOf(j));


            serializer.endTag(null, "record");
        }
        serializer.endTag(null, "root");
        serializer.endDocument();

        serializer.flush();

        fos.close();

            //display file saved message
            Toast.makeText(getBaseContext(), "File xml saved successfully!",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 public String data;

    public void onClick2(View v){
        try {
            FileInputStream fis = null;
        InputStreamReader isr = null;

        fis = openFileInput("file.txt");
        isr = new InputStreamReader(fis);
        char[] inputBuffer = new char[fis.available()];
        isr.read(inputBuffer);
        data = new String(inputBuffer);
        isr.close();
        fis.close();
            Toast.makeText(getBaseContext(), data,
                    Toast.LENGTH_SHORT).show();
    /*
     * converting the String data to XML format
     * so that the DOM parser understand it as an XML input.
     */
        InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));

      //  ArrayList<XmlData> xmlDataList = new ArrayList<XmlData>();

        //XmlData xmlDataObj;
        DocumentBuilderFactory dbf;
        DocumentBuilder db;
        NodeList items =null;
        Document dom;


        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();

        dom = db.parse(is);


        // normalize the document
        dom.getDocumentElement().normalize();

        items = dom.getElementsByTagName("record");

        ArrayList<String> arr = new ArrayList<String>();
        String t="";
        for (int i=0;i<items.getLength();i++){

            Node item = items.item(i);
           // TextView editText = new TextView(getBaseContext());
          //  editText.setText(item.getNodeValue());
          //  editText.setTextColor(Color.rgb(200, 0, 0));
         //   editText.setBackgroundColor((Color.rgb(0, 255, 255)));
         //   editText.setWidth(200);
         //   editText.setHeight(100);
          //  editText.setX(Integer.valueOf(item.getNodeValue()) * 210);
         //   editText.setY(500);
         //   editText.setId(Integer.valueOf(item.getNodeValue()));
           // totalEditTexts++;
         //   containerLayout.addView(editText);
       // t = t + item.getNodeValue();
            t = t + item.getTextContent();
            arr.add(item.getTextContent());


        }
            Toast.makeText(getBaseContext(),t,
                    Toast.LENGTH_SHORT).show();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    }
