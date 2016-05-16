package esgi.yvox;

/**
 * Created by Benoit on 06/05/2016.
 */
public class Sphinx_Controller implements Sphinx_Request{
    private static Sphinx_Controller mInstance;
    private static Thread sph_thread;
    //state == true Recognition is running, if its false, recognition is stopped
    private static boolean state;
    public boolean AskToStop = false;

    private static Sphinx_Thread.SphinxEvent sph_callback  =new Sphinx_Thread.SphinxEvent() {
        @Override
        public void onStop() {
            state = false;
            System.out.println("Destroying sph_thread");
            sph_thread = null;
        }
    };

    public static Sphinx_Controller getInstance(){
        if (mInstance == null) {
            mInstance = new Sphinx_Controller();
        }
        if (sph_thread == null) {
            sph_thread = new Thread(new Sphinx_Thread(mInstance,sph_callback));
        }
        return mInstance;
    }

    private Sphinx_Controller() {
        state = false;
    }

    @Override
    public void onRecognitionRequest() {
        System.out.println("state : "+state);
        if (state){
            AskToStop = true;
            System.out.println("Ask to stop");
        }else {
            state=true;
            AskToStop = false;
            sph_thread.start();

        }
    }
}
