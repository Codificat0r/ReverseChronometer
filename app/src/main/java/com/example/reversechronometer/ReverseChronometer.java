package com.example.reversechronometer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * CONTROL PERSONALIZADO. Los atributos personalizados que tendra este widget y cualquiera personalizado
 * van en attrs.xml en values.
 */

@SuppressLint("AppCompatCustomView")
public class ReverseChronometer extends TextView implements Runnable {

    private long overallDuration;
    private long warningDuration;
    //Cuando comienza el contador va a ser su valor.
    private long startTime;

    public ReverseChronometer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setTextColor(Color.BLACK);
        //Obtenemos los atributos personalizados en una lista TypedArray. Le decimos el styleable con los atributos
        //y el AttributeSet que contiene los valores definidos por el usuario, en el caso en el que los haya definido.
        TypedArray attribute = getContext().obtainStyledAttributes(attrs, R.styleable.ReverseChronometer);
        //Le pasamos el nombre del estilo personalizado y un valor por defecto en el caso de que no se haya definido.
        overallDuration = attribute.getInteger(R.styleable.ReverseChronometer_overallduration, 60);
        warningDuration = attribute.getInteger(R.styleable.ReverseChronometer_warningduration, 10);
        reset();
    }

    public void stop() {

    }

    @Override
    public void run() {
        //Calculamos los segundos que van pasando (cuentan hacia arriba)
        long elapsedSeconds = (SystemClock.elapsedRealtime() - startTime) / 1000;
        //Si los segundos que han pasado son menores que la duracion que queremos total, sigue
        //contando
        if (elapsedSeconds < overallDuration) {
            //Actualizar los tiempos
            long remainingSeconds = overallDuration - elapsedSeconds;
            long minutes = remainingSeconds / 60;
            long seconds = remainingSeconds - (60 * minutes);
            setText(String.format("%02d:%02d", minutes, seconds));

            //En el caso que nos encontremos en warning time:
            if (remainingSeconds < warningDuration)
                setTextColor(Color.RED);

            //Ejecutamos otra vez el run despues de que espere 1000 milisegundos.
            postDelayed(this, 1000);
        } else {
            setText("00:00");
            setTextColor(Color.BLACK);
        }
    }

    public void setOverallDuration(long overallDuration) {
        this.overallDuration = overallDuration;
    }

    public long getOverallDuration() {
        return overallDuration;
    }

    public void setWarningDuration(long warningDuration) {
        this.warningDuration = warningDuration;
    }

    public void reset() {
        startTime = SystemClock.elapsedRealtime();
        setText("--:--");
        setTextColor(Color.BLACK);
    }
}
