package esgi.yvox;

/**
 * Created by Benoit on 06/05/2016.
 */
public class Sphinx_Controller implements Sphinx_Request{
    private static Sphinx_Controller mInstance;
    private static Sphinx_Thread sph_thread;
    //state == true Recognition is running, if its false, recognition is stopped
    private static boolean state;

    private static Sphinx_Thread.SphinxEvent sph_callback  =new Sphinx_Thread.SphinxEvent() {
        @Override
        public void onStop() {
            state = false;
        }
    };

    public static Sphinx_Controller getInstance(){
        if (mInstance == null) {
            mInstance = new Sphinx_Controller();
        }
        if (sph_thread == null) {
            sph_thread = new Sphinx_Thread(sph_callback,"Sphinx Thread");
            sph_thread.start();
            sph_thread.Initialization();
        }
        return mInstance;
    }

    private Sphinx_Controller() {
        state = false;
    }

    @Override
    public void onRecognitionRequest() {
        if (state){
            state=false;
            sph_thread.stop_recognition();
        }else {
            state=true;
            sph_thread.start_recognition();
        }
    }
}
