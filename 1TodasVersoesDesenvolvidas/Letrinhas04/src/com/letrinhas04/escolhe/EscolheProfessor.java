package com.letrinhas04.escolhe;

import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.letrinhas04.R;
import com.letrinhas04.BaseDados.LetrinhasDB;
import com.letrinhas04.ClassesObjs.Escola;
import com.letrinhas04.ClassesObjs.Professor;
import com.letrinhas04.util.SystemUiHider;

public class EscolheProfessor extends Activity {

	Button volt;
	public int nProfs, numero = 0, idEscola;
	String Escola;
	List<Professor> profs;
	LetrinhasDB db;
	ListView list;
	Integer[] image;
	int[] idProf;
	String[] username;
	String[] password;
	String[] telefone;
	String[] email;
	String[] fotoNome;
	int[] estado;

	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;
	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 1000;
	/*********************************************************************
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;
	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_escolhe_professor);

		// sacar os extras
		Bundle b = getIntent().getExtras();
		idEscola = b.getInt("Escola_ID");
		Escola = b.getString("Escola");
		((TextView) findViewById(R.id.escPEscola)).setText(Escola);
		
		
		// new line faz a rota��o do ecr�n em 180 graus
				int currentOrientation = getResources().getConfiguration().orientation;
				if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
				} else {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
				}

		// esconder o title************************************************+
		final View contentView = findViewById(R.id.escProf);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});

		// Bot�o de voltar
		volt = (Button) findViewById(R.id.btnVoltarProf);
		volt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// sair da activity
				finish();
			}
		});

		makeTabela();
	}

	/**
	 * Novo m�todo para criar o painel din�mico para os bot�es de selec��o do professor
	 * 
	 * @author Thiago
	 */
	@SuppressLint("NewApi")
	private void makeTabela() {

		// Cria o objecto da base de dados
		db = new LetrinhasDB(this);
		profs = db.getAllProfesorsBySchool(idEscola);
		nProfs = profs.size();
		idProf = new int[profs.size()];
		username = new String[profs.size()];
		fotoNome = new String[profs.size()];
		;

		// password = new String[profs.size()];
		// telefone = new String[profs.size()];
		// email = new String[profs.size()];
		// estado = new int[profs.size()];
		for (int i = 0; i < nProfs; i++) {
			username[i] = profs.get(i).getNome();
			idProf[i] = profs.get(i).getId();
			fotoNome[i] = profs.get(i).getFotoNome();
		}

		for (Professor cn : profs) {
			String storage = cn.getEmail() + "," + cn.getFotoNome() + ","
					+ cn.getId() + "," + cn.getNome() + "," + cn.getPassword()
					+ "," + cn.getTelefone() + "," + cn.getUsername();
			Log.d("letrinhas-Professores da "+Escola+" ", storage.toString());
		}

		/**
		 * Scroll view com uma table de 4 colunas(max)
		 */
		// tabela a editar
		TableLayout tabela = (TableLayout) findViewById(R.id.tblEscolheProf);
		// linha da tabela a editar
		TableRow linha = (TableRow) findViewById(R.id.Proflinha01);
		// 1� bot�o
		Button bt = (Button) findViewById(R.id.PrfBtOriginal);
		bt.setText("teste professores");

		// Contador de controlo
		int cont = 0;
		for (int i = 0; i < nProfs / 4; i++) {
			// nova linha da tabela
			TableRow linha1 = new TableRow(getBaseContext());
			// Copiar os parametros da 1� linha
			linha1.setLayoutParams(linha.getLayoutParams());
			for (int j = 0; j < 4; j++) {

				// **********************************
				// Nome do professor

				final String proff = username[cont];
				final String fotoprof = fotoNome[cont];
				final int idPrf = idProf[cont];
				// ***********************************

				// novo bot�o
				Button bt1 = new Button(bt.getContext());
				// copiar os parametros do bot�o original
				bt1.setLayoutParams(bt.getLayoutParams());

				// se a professor tiver foto, vou busca-la
				if (fotoNome[cont] != null) {
					String imageInSD = Environment
							.getExternalStorageDirectory().getAbsolutePath()
							+ "/School-Data/Professors/" + fotoNome[cont];
					Bitmap bitmap = BitmapFactory.decodeFile(imageInSD);
					ImageView imageView = new ImageView(this);

					// ajustar o tamanho da imagem
					imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap,
							240, 240, false));
					// enviar para o bot�o
					bt1.setCompoundDrawablesWithIntrinsicBounds(null,
							imageView.getDrawable(), null, null);
				} else {
					// sen�o copia a imagem do bot�o original
					bt1.setCompoundDrawables(null,
							bt.getCompoundDrawablesRelative()[1], null, null);
				}

				// addicionar o nome
				bt1.setText(username[cont]);
				// Defenir o que faz o bot�o ao clicar
				bt1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						// Entrar na activity
						Bundle wrap = new Bundle();
						wrap.putString("Escola", Escola);
						wrap.putInt("Escola_ID", idEscola);
						wrap.putString("Professor", proff);
						wrap.putString("foto_Professor", fotoprof);
						wrap.putInt("Professor_ID", idPrf);

						Intent it = new Intent(getApplicationContext(),
								EscolheTurma.class);
						it.putExtras(wrap);

						startActivity(it);
					}
				});
				// inserir o bot�o na linha
				linha1.addView(bt1);
				// incrementar o contador de controlo
				cont++;
			}
			// inserir a linha criada
			tabela.addView(linha1);
		}

		// resto
		if (nProfs % 4 != 0) {
			TableRow linha1 = new TableRow(getBaseContext());
			linha1.setLayoutParams(linha.getLayoutParams());
			for (int j = 0; j < nProfs % 4; j++) {
				// **********************************
				// Nome do professor

				final String proff = username[cont];
				final String fotoprof = fotoNome[cont];
				final int idPrf = idProf[cont];
				// ***********************************

				// novo bot�o
				Button bt1 = new Button(bt.getContext());
				// copiar os parametros do bot�o original
				bt1.setLayoutParams(bt.getLayoutParams());

				// se a professor tiver foto, vou busca-la
				if (fotoNome[cont] != null) {
					String imageInSD = Environment
							.getExternalStorageDirectory().getAbsolutePath()
							+ "/School-Data/Professors/" + fotoNome[cont];
					Bitmap bitmap = BitmapFactory.decodeFile(imageInSD);
					ImageView imageView = new ImageView(this);

					// ajustar o tamanho da imagem
					imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap,
							240, 240, false));
					// enviar para o bot�o
					bt1.setCompoundDrawablesWithIntrinsicBounds(null,
							imageView.getDrawable(), null, null);
				} else {
					// sen�o copia a imagem do bot�o original
					bt1.setCompoundDrawables(null,
							bt.getCompoundDrawablesRelative()[1], null, null);
				}

				// addicionar o nome
				bt1.setText(username[cont]);
				// Defenir o que faz o bot�o ao clicar
				bt1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						// Entrar na activity
						Bundle wrap = new Bundle();
						wrap.putString("Escola", Escola);
						wrap.putInt("Escola_ID", idEscola);
						wrap.putString("Professor", proff);
						wrap.putString("foto_Professor", fotoprof);
						wrap.putInt("Professor_ID", idPrf);

						Intent it = new Intent(getApplicationContext(),
								EscolheTurma.class);
						it.putExtras(wrap);

						startActivity(it);
					}
				});
				// inserir o bot�o na linha
				linha1.addView(bt1);
				// incrementar o contador de controlo
				cont++;
			}
			// inserir a linha criada com o resto dos bot�es
			tabela.addView(linha1);
		}

		// por fim escondo a 1� linha
		tabela.removeView(linha);
	}

	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(1000);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}

}
