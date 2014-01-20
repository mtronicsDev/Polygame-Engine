package mEngine.audio;

import com.sun.jndi.url.corbaname.corbanameURLContextFactory;
import mEngine.interactive.gameObjects.GameObject;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.util.WaveData;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.openal.AL10.*;

public class AudioController {

    public static AudioListener listener;
    public static List<AudioSource> sources = new ArrayList<AudioSource>();

    public static void initializeOpenAL() {

        try {

            AL.create();

        } catch (LWJGLException e) {

            e.printStackTrace();
            System.exit(1);

        }

    }

    public static void setListener(GameObject obj) {

        listener = new AudioListener(obj);

    }

    static int loadALData(AudioSource src, String  fileName) {

        WaveData waveData = null;

        try {

            FileInputStream stream = new FileInputStream(new File(fileName));
            waveData = WaveData.create(new BufferedInputStream(stream));

        }
        catch (FileNotFoundException e) {

            e.printStackTrace();
            System.exit(1);

        }
        src.buffer = alGenBuffers();
        if(alGetError() != AL_NO_ERROR) return AL_FALSE;
        alBufferData(src.buffer, waveData.format, waveData.data, waveData.samplerate);
        waveData.dispose();

        src.source = alGenSources();
        if(alGetError() != AL_NO_ERROR) return  AL_FALSE;

        alSourcei(src.source, AL_BUFFER, src.buffer);
        alSourcef(src.source, AL_PITCH, 1.0f);
        alSourcef(src.source, AL_GAIN, 1.0f);
        alSource(src.source, AL_POSITION, src.sourcePos);
        alSource(src.source, AL_VELOCITY, src.sourceVel);

        if(alGetError() == AL_NO_ERROR) return  AL_TRUE;
        else return AL_FALSE;

    }

    public static void killALData() {

        for(AudioSource source : sources){

            alDeleteSources(source.source);
            alDeleteBuffers(source.buffer);

        }

        AL.destroy();

    }

}
