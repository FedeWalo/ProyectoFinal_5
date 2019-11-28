package com.example.proyectofinal;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class FragmentPantallaInicio extends Fragment{
    TextView CantFitPointDelDia;
    int CantFP;

    TextView CantFPAConsimir;
    int CantFPDiaObjetivo;
    ClasePerfil objPerfil;
    ClasePerfil perfil;

    private BarChart Grafico;
    private int[]Porcentaje=new int[7];
    private String[]Dias=new String[]{"Lun,Mar,Mie,Jue,Vie,Sab,Dom"};
    private int[]Colores=new int[]{Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE};
    private int[]ColoresConIdealBuenos=new int[14];
    private int ProporcionIdealBuenos;
    private int[]PorcentajeConIdeal=new int[14];


    @Override
    public View onCreateView(LayoutInflater infladorDeLayouts, ViewGroup GrupoDeLaVista, Bundle Datos) {
        View VistaAdevolver;
        VistaAdevolver = infladorDeLayouts.inflate(R.layout.layout_pantalla_inicio,GrupoDeLaVista,false);

        ClaseObjetivosUsuario Objetivo;
        Objetivo = new ClaseObjetivosUsuario();
        objPerfil = new ClasePerfil();
        ClaseComidaConsumida comidaConsumida;
        comidaConsumida = new ClaseComidaConsumida();

        Porcentaje = comidaConsumida.ConsultarProporcionBuenosDeLosUltimos7Dias(getActivity());
        ProporcionIdealBuenos = Objetivo.ObtenerIdealPorcentajeFitPointsBuenos(getActivity());

        if (ProporcionIdealBuenos> 0) {
            PorcentajeConIdeal = new int[]{ProporcionIdealBuenos,Porcentaje[0],ProporcionIdealBuenos,Porcentaje[1],ProporcionIdealBuenos,Porcentaje[2],ProporcionIdealBuenos,Porcentaje[3],ProporcionIdealBuenos,Porcentaje[4],ProporcionIdealBuenos,Porcentaje[5],ProporcionIdealBuenos,Porcentaje[6]};
            ColoresConIdealBuenos = new int []{Color.WHITE,Color.MAGENTA,Color.WHITE,Color.MAGENTA,Color.WHITE,Color.MAGENTA,Color.WHITE,Color.MAGENTA,Color.WHITE,Color.MAGENTA,Color.WHITE,Color.MAGENTA,Color.WHITE,Color.MAGENTA};
        }

        CantFitPointDelDia = VistaAdevolver.findViewById(R.id.FitPointsConsumidos);
        CantFP = comidaConsumida.ConsultarFitPointDelDia(getActivity());
        CantFitPointDelDia.setText(""+CantFP);
        Grafico=VistaAdevolver.findViewById(R.id.GraficoDeBarra);
        createCharts();

        CantFPAConsimir = VistaAdevolver.findViewById(R.id.FitPointsQueFaltanConsumir);

        //traigo los datos del perfil
        perfil =objPerfil.TraerUltimosDatosPerfil(getActivity());

        // me fijo si cargue alguno
        if(perfil.getIdPerfil() != 0) {
            CantFPDiaObjetivo = objPerfil.FitPointsAlDia(getActivity());
            CantFPDiaObjetivo = CantFPDiaObjetivo - CantFP;
            CantFPAConsimir.setText("RESTAN "+CantFPDiaObjetivo+" FP");
        }
        else {
            CantFPAConsimir.setVisibility(View.INVISIBLE);
        }

        return VistaAdevolver;
    }

    private BarChart getSameChart(BarChart Grafico,String Descripcion,int COlorDeTexto,int ColorFondo,int TimeAnimation){
        Grafico.getDescription().setText(Descripcion);
       Grafico.getDescription().setTextSize(15);
        Grafico.setBackgroundColor(ColorFondo);
        Grafico.animateY(TimeAnimation);

        return Grafico;
    }

    private void legend(BarChart Grafico){
        Legend legend=Grafico.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> Datos=new ArrayList<>();
        for(int i=0; i<Dias.length;i++){
            LegendEntry Dato=new LegendEntry();
            Dato.formColor=Colores[i];
            Dato.label=Dias[i];
            Datos.add(Dato);
        }
        legend.setCustom(Datos);
    }

    //definicion de las barras con colores

    private ArrayList<BarEntry>getBarEntries() {
        ArrayList<BarEntry> Datos=new ArrayList<>();

        if (ProporcionIdealBuenos==0){
            for (int i=0; i < Porcentaje.length; i++) {
                Datos.add(new BarEntry(i,Porcentaje[i]));
        }
        }else{
            for (int i=0; i < PorcentajeConIdeal.length; i++) {
            Datos.add(new BarEntry(i,PorcentajeConIdeal[i]));
        }
        }
        return Datos;
    }

    private void ejex(XAxis Ejex){
        Ejex.setGranularityEnabled(true);
        Ejex.setPosition(XAxis.XAxisPosition.BOTTOM);
        Ejex.setValueFormatter(new IndexAxisValueFormatter(Dias));
    }

    private void ejey(YAxis EjeY){
       EjeY.setSpaceTop(0);
       EjeY.setAxisMinimum(0);
    }

    public void createCharts(){
        Grafico=getSameChart(Grafico,"",Color.RED,Color.TRANSPARENT,1000);
        Grafico.setData(getBarData());
        Grafico.invalidate();
        ejex(Grafico.getXAxis());
        ejey(Grafico.getAxisLeft());

    }

    private DataSet getData(DataSet dataSet){
        if (ProporcionIdealBuenos==0){
        dataSet.setColors(Colores);}
        else{
            dataSet.setColors(ColoresConIdealBuenos);
        }
        dataSet.setValueTextSize(15);

        return dataSet;
    }

    private BarData getBarData(){
        BarDataSet barDataSet=(BarDataSet)getData(new BarDataSet(getBarEntries(),""));

        BarData barData=new BarData(barDataSet);
        barData.setBarWidth(0.50f);
        return barData;
    }
}


