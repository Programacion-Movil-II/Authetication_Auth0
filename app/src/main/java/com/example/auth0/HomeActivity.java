
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

/**
 * Pantalla principal que se muestra
 * después de un inicio de sesión exitoso.
 *
 * Contiene una opción para cerrar sesión
 * y finalizar la sesión activa en Auth0.
 */
public class HomeActivity extends AppCompatActivity {

    private Auth0 auth0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inicializamos Auth0 para el cierre de sesión
        auth0 = new Auth0(this);

        // Configuración del botón de cerrar sesión
        Button btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(
                v -> cerrarSesionConAuth0()
        );
    }

    /**
     * Cierra la sesión activa del usuario
     * mediante Auth0.
     */
    private void cerrarSesionConAuth0() {

        WebAuthProvider.logout(auth0)
                .withScheme("demo")
                .start(this, new Callback<Void, AuthenticationException>() {

                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(
                                HomeActivity.this,
                                "Sesión cerrada correctamente",
                                Toast.LENGTH_SHORT
                        ).show();

                        // Regresamos a MainActivity
                        Intent intent = new Intent(
                                HomeActivity.this,
                                MainActivity.class
                        );

                        // Limpiar la pila de actividades
                        intent.setFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK
                        );

                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(AuthenticationException error) {

                        Toast.makeText(
                                HomeActivity.this,
                                "Error al cerrar sesión: "
                                        + error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}
