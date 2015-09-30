package de.dhbw.meetme;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.hello.R;

/**
 * Created by mahandru on 24.09.2015.
 */
public class activity_main extends Activity implements LocationListener, OnClickListener {

    private LocationManager locationManager;
    private TextView anzeigeLaenge;
    private TextView anzeigeBreite;
    private TextView anzeigeHoehe;


    private SimpleDateFormat gpxZeitFormat;

    private Button startButton;
    private Button stoppButton;
    private Button speichernButton;
    private boolean datenSammeln;
    private List<Location> positionen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Testen, ob GPS verfügbar
        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            warnungUndBeenden();
        }



        // StartButton
        startButton = (Button) this.findViewById(R.id.button1);
        startButton.setOnClickListener(this);


        // Stop-Button
        stoppButton = (Button) this.findViewById(R.id.button2);
        stoppButton.setOnClickListener(this);
        stoppButton.setEnabled(false);

        // Speichern-Button
        speichernButton = (Button) this.findViewById(R.id.button3);
        speichernButton.setOnClickListener(this);
        speichernButton.setEnabled(false);

        datenSammeln = false;
        positionen   = new ArrayList<Location>();


        anzeigeBreite  = (TextView) this.findViewById(R.id.textViewBreite);
        anzeigeLaenge  = (TextView) this.findViewById(R.id.textViewLaenge);
        anzeigeHoehe   = (TextView) this.findViewById(R.id.textViewHoehe);

        gpxZeitFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    }





    /**
     * Klickbehandlung für Start/Stopp/Speichern
     */
    public void onClick(View v) {
        if(v == startButton) {
            startButton.setEnabled(false);
            stoppButton.setEnabled(true);
            speichernButton.setEnabled(false);
            datenSammeln = true;
        }
        else if(v == stoppButton) {
            startButton.setEnabled(true);
            stoppButton.setEnabled(false);
            speichernButton.setEnabled(true);
            datenSammeln = false;
        }
        else if(v == speichernButton){
            startButton.setEnabled(true);
            stoppButton.setEnabled(false);
            speichernButton.setEnabled(false);
            datenSammeln = false;

            datenSpeichern();


        }

    }









    /**
     * Dialog für Speichern anzeigen und Daten dann speichern falls gewünscht
     */
    private void datenSpeichern() {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.setOwnerActivity(this);
            dialog.setContentView(R.layout.save);

            // Dialogelemente initialisieren
            Resources res = getResources();
            dialog.setTitle(res.getText(R.string.speichernDialogTitel));

            final EditText dateiname = (EditText) dialog.findViewById(R.id.editTextDateiname);
            dateiname.setText("dateiname.gpx");

            Button speichern = (Button) dialog.findViewById(R.id.speichernOK);
            speichern.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Editable ed = dateiname.getText();

                    try {
                        positionenSchreiben(ed.toString());
                        positionen.clear();
                    }
                    catch(Exception ex) {
                        Log.d("carpelibrum", ex.getMessage());
                    }

                    dialog.dismiss();
                }
            });


            dialog.show();

        }
        catch(Exception ex) {
            Log.d("carpelibrum", ex.getMessage());

        }

    }

    /**
     * Schreibt die Daten auf SD-Karte oder internen Speicher im GPX Format
     * @throws Exception
     */
    private void positionenSchreiben(String dateiName) throws Exception {
        File sdKarte             = Environment.getExternalStorageDirectory();
        boolean sdKarteVorhanden = (sdKarte.exists() && sdKarte.canWrite());
        File datei;

        if(sdKarteVorhanden) {
            datei = new File(sdKarte.getAbsolutePath() + File.separator + dateiName);
        }
        else {
            datei = new File(getFilesDir() + File.separator + dateiName);;
        }


        BufferedWriter writer = new BufferedWriter(new FileWriter(datei));


        // Dateikopf schreiben
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
        writer.newLine();
        writer.write("<gpx xmlns=\"http://www.topografix.com/GPX/1/1\" version=\"1.1\" ");
        writer.write("creator=\"carpelibrum\" xmlns:xsi= \"http://www.w3.org/2001/XMLSchema-instance\" ");
        writer.write("xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1/gpx.xsd\"");
        writer.newLine();

        // Daten schreiben
        for(Location loc : positionen) {
            lokationSpeichern(loc, writer);
        }


        // Dateiende schreiben
        writer.write("</gpx>");

        writer.close();
    }


    /**
     * Übergebene Lokation in den gegebenen Writer schreiben
     * @param loc
     * @param writer
     */
    private void lokationSpeichern(Location loc, BufferedWriter writer) throws IOException {
        writer.write("<wpt lat=\"" + loc.getLatitude() + "\" lon=\"" + loc.getLongitude() + "\">");
        writer.newLine();
        writer.write("<ele>" + loc.getAltitude() + "</ele>");
        writer.newLine();

        String zeit = gpxZeitFormat.format(new Date(loc.getTime()));
        writer.write("<time>" + zeit + "</time>");
        writer.newLine();
        writer.write("</wpt>");
        writer.newLine();
    }


    /** Benutzer auffordern GPS zu aktivieren und Activity beenden
     *
     */
    private void warnungUndBeenden() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Resources res = getResources();
        String text   = res.getString(R.string.keinGPS);
        builder.setMessage(text);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                finish(); // Activity beenden
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }




    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
    }





    public void onLocationChanged(Location loc) {

        double laenge = loc.getLongitude();
        double breite = loc.getLatitude();

        anzeigeBreite.setText(Location.convert(breite, Location.FORMAT_SECONDS));
        anzeigeLaenge.setText(Location.convert(laenge, Location.FORMAT_SECONDS));

        if(loc.hasAltitude()) {
            anzeigeHoehe.setText(String.valueOf(loc.getAltitude()));
        }


        if(datenSammeln) {
            positionen.add(loc);
        }

    }


    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }


    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }


    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
}
