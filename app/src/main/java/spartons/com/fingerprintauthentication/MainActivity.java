package spartons.com.fingerprintauthentication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;

import java.util.concurrent.Executor;

import static androidx.biometric.BiometricConstants.ERROR_NEGATIVE_BUTTON;

public class MainActivity extends AppCompatActivity {

    private BiometricPrompt biometricPrompt = null;
    private Executor executor = new MainThreadExecutor();

    private BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            if (errorCode == ERROR_NEGATIVE_BUTTON && biometricPrompt != null)
                biometricPrompt.cancelAuthentication();
            toast(errString.toString());
        }

        @Override
        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            toast("Authentication succeed");
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            toast("Application did not recognize the placed finger print. Please try again!");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (biometricPrompt == null)
            biometricPrompt = new BiometricPrompt(this, executor, callback);

        findViewById(R.id.selectFingerPrintButton).setOnClickListener(view -> {
            BiometricPrompt.PromptInfo promptInfo = buildBiometricPrompt();
            biometricPrompt.authenticate(promptInfo);
        });
    }

    private BiometricPrompt.PromptInfo buildBiometricPrompt() {
        return new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setSubtitle("Login into your account")
                .setDescription("Touch your finger on the finger print sensor to authorise your account.")
                .setNegativeButtonText("Cancel")
                .build();
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
