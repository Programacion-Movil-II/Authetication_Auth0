
package com.example.auth0;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.Callback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;

/**
 * Activity de Login que inicia el flujo
 * de autenticación segura con Auth0.
 */
public class MainActivity extends AppCompatActivity {

    private Auth0 auth0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos el cliente Auth0.
        // Lee automáticamente com_auth0_domain
        // y com_auth0_client_id desde strings.xml
        auth0 = new Auth0(this);

        // Configuración del botón de iniciar sesión
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(
                v -> iniciarSesionConAuth0()
        );
    }

    /**
     * Abre Auth0 Universal Login
     * en el navegador del dispositivo.
     */
    private void iniciarSesionConAuth0() {

        WebAuthProvider.login(auth0)
                .withScheme("demo")
                .start(this, new Callback<Credentials,
                        AuthenticationException>() {

                    @Override
                    public void onSuccess(Credentials credentials) {

                        // Acción al autenticar con éxito
                        Toast.makeText(
                                MainActivity.this,
                                "¡Login exitoso con Auth0!",
                                Toast.LENGTH_SHORT
                        ).show();

                        // Redirección a HomeActivity
                        Intent intent = new Intent(
                                MainActivity.this,
                                HomeActivity.class
                        );

                        // Pasamos el token de acceso obtenido
                        intent.putExtra(
                                "access_token",
                                credentials.getAccessToken()
                        );

                        startActivity(intent);

                        // Evita volver al Login con el botón Atrás
                        finish();
                    }

                    @Override
                    public void onFailure(AuthenticationException error) {

                        // Error al iniciar sesión
                        Toast.makeText(
                                MainActivity.this,
                                "Error al iniciar sesión: "
                                        + error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}
