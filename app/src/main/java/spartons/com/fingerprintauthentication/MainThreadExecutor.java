package spartons.com.fingerprintauthentication;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 8/23/19}
 */

public class MainThreadExecutor implements Executor {

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable r) {
        handler.post(r);
    }
}
