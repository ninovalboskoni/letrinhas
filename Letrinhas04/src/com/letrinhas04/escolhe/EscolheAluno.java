package com.letrinhas04.escolhe;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.letrinhas04.R;
import com.letrinhas04.BaseDados.LetrinhasDB;
import com.letrinhas04.ClassesObjs.Escola;
import com.letrinhas04.ClassesObjs.Professor;
import com.letrinhas04.util.Custom;
import com.letrinhas04.util.SystemUiHider;

public class EscolheAluno extends Activity {

	ImageButton volt, exect;
	public int nAlunos;
	ListView list;
	LetrinhasDB db;
	Escola escola;
	Professor prof;

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
		setContentView(R.layout.activity_escolhe_aluno);
		
		
		
		//Cria o objecto da base de dados
		/*db = new LetrinhasDB(this);
		estudantes = db.getAllStudents();
		nAlunos = estudantes.size();
		//Bundle b = getIntent().getExtras();
		//int idEscola = b.getInt("IdEscola");
		//Para ser adapatado\\
				profs = db.getAllProfesorsBySchool(idEscola);
				nProfs = profs.size();
				username = new String[profs.size()];
				password = new String[profs.size()];
				telefone = new String[profs.size()];
				email = new String[profs.size()];
				fotoNome = new String[profs.size()];;
				estado = new int[profs.size()];
				idProf = new int[profs.size()];
				for (Professor cn : profs) {
						String storage = cn.getEmail()+","+cn.getFotoNome()+","+cn.getId()+","+cn.getNome()+","+cn.getPassword()+","+cn.getTelefone()+","+cn.getUsername();
			            Log.d("letrinhas-Escola", storage.toString());
			            password[numero] = cn.getPassword();
			            Log.d("letrinhas-Tipo",String.valueOf(password[0]));
			            username[numero] = cn.getNome();
			            Log.d("letrinhas-Titulo", username[0].toString());
			            idProf[numero] = cn.getId();
			            Log.d("letrinhas-ID", String.valueOf(idProf[0]));
			            fotoNome[numero] = cn.getFotoNome();
			            Log.d("letrinhas-IMG", fotoNome[0]);
			            setUp(username, fotoNome, idProf,username,password);
			            numero++;
		}*/

		// esconder o title************************************************+
		final View contentView = findViewById(R.id.escAluno);

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

		volt = (ImageButton) findViewById(R.id.escTVoltar_aluno);
		exect = (ImageButton) findViewById(R.id.ibComecar_aluno);

		escutaBotoes();
	}
	
 /*public void setUp(final String[] nome, String[] imgNome, final int[] id, final String[] userName, final String[] pass){
		Custom adapter = new Custom(EscolheAluno.this, nome, imgNome,"professores");
		list=(ListView)findViewById(R.id.lista);
				list.setAdapter(adapter);
				list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		            @Override
		            public void onItemClick(AdapterView<?> parent, View view,int position, long idd) {
		                Toast.makeText(EscolheAluno.this, "You Clicked at " +nome[+ position], Toast.LENGTH_SHORT).show();
		              /*  Bundle wrap = new Bundle();
		    			wrap.putInt("IdProf", id[position]);
		    			wrap.putString("pass", pass[position]);
		    			wrap.putString("user", userName[position]);
		    			Intent itp = new Intent(getApplicationContext(), Autenticacao.class);
						itp.putExtras(wrap);
						startActivity(itp);
		          }
		 });
	}
*/
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

	/**
	 * Procedimento para veirficar os bot�es
	 * 
	 * @author Thiago
	 */
	private void escutaBotoes() {
		exect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//executarTestes();
				Toast.makeText(getApplicationContext(), " - Tipo n�o defenido",
				Toast.LENGTH_SHORT).show();
			}
		});

		volt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {// sair da aplica��o
				finish();
			}
		});
	}
}
