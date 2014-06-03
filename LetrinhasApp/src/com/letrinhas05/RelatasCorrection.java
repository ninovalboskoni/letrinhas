package com.letrinhas05;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.letrinhas05.BaseDados.LetrinhasDB;

public class RelatasCorrection extends Activity {

	LetrinhasDB db;
	int[] valueInt;
	long[] valueLong;
	float[] valueFloat;
	String[] valueString;
	TextView testId, idEstudante, tipo, estado, numPalavCorretas, numPalavIncorretas, dataExecucao, idCorrrecao, numPalavrasMin, precisao, velocidade, expressividade, ritmo, observacoes, detalhes, totalDePalavras, Duracao;
	Button next;
	String DuracaoTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_relatas_correction);
		Bundle b = getIntent().getExtras();
	
		valueInt = b.getIntArray("ints");
		Log.d("Debug-valueInt[0]", String.valueOf(valueInt[0]));
		valueLong = b.getLongArray("longs");
		valueFloat = b.getFloatArray("floats");
		valueString = b.getStringArray("strings");
		
		DuracaoTime = b.getString("DuracaoTime");
		tipo =  (TextView) findViewById(R.id.tipo);
		numPalavCorretas = (TextView) findViewById(R.id.numPalavCorretas);
		numPalavIncorretas = (TextView) findViewById(R.id.numPalavIncorretas);
		dataExecucao = (TextView) findViewById(R.id.dataExecucao);
		numPalavrasMin = (TextView) findViewById(R.id.numPalavrasMin);
		precisao = (TextView) findViewById(R.id.precisao);
		velocidade = (TextView) findViewById(R.id.velocidade);
		expressividade = (TextView) findViewById(R.id.expressividade);
		ritmo = (TextView) findViewById(R.id.ritmo);
		observacoes = (TextView) findViewById(R.id.observacoes);
		detalhes = (TextView) findViewById(R.id.detalhes);
		totalDePalavras = (TextView) findViewById(R.id.totalDePalavras);
		next = (Button) findViewById(R.id.next);
		Duracao = (TextView) findViewById(R.id.Duracao);
		
		tipo.setText(valueString[2]);
		
		String[] ar = valueString[3].split("[ ]");
		numPalavCorretas.setText(String.valueOf(valueInt[4]));
		numPalavIncorretas.setText(String.valueOf(valueInt[5]));
		dataExecucao.setText(ar[0]);
		numPalavrasMin.setText(String.valueOf(valueFloat[0]));
		precisao.setText(String.valueOf(valueFloat[1]));
		velocidade.setText(String.valueOf(valueFloat[2]));
		expressividade.setText(String.valueOf(valueFloat[3]));
		ritmo.setText(String.valueOf(valueFloat[4]));
		observacoes.setText(valueString[0]);
		detalhes.setText(valueString[1]);
		totalDePalavras.setText(String.valueOf(valueInt[6]));
		Duracao.setText(DuracaoTime);
		
		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.relatas_correction, menu);
		return true;
	}

}
