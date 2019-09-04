/**
    Copyright 2019 Anton "Vuvk" Shcherbatykh

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package com.vuvk.retard_sound_system;

/**
 *
 * @author vuvk
 */
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;

import javax.sound.sampled.AudioInputStream;

public class Sound implements AutoCloseable {  
    private static final Logger LOG = Logger.getLogger(Sound.class.getName());
    
    private AudioInputStream stream = null;
    private File inputFile = null;
    private boolean playing = false;
    private boolean looping = false;
    private double volume = 1.0;
    
    public Sound(SoundBuffer buffer) {
        prepareStream(buffer);
    }
    
    public Sound(String path) {
        this(new File(path));
    }
    
    public Sound(File file) {
        this(file, false);
    }

    public Sound(String path, boolean precached) {
        this(new File(path), precached);
    }
    
    public Sound(File file, boolean precached) {                     
        if (precached) {
            prepareStream(new SoundBuffer(file));
        } else {
            prepareStream(file);
        }            
    }
    
    private void prepareStream(File file) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
        
        stream = SoundSystem.getEncodedAudioInputStream(file);
        inputFile = file;
        markStream();        
    }
    
    private void prepareStream(SoundBuffer buffer) {        
        stream = buffer.getAudioInputStream();
        markStream();
    }
    
    private void markStream() {    
        if (stream != null && stream.markSupported()) {
            stream.mark(0);
        }
    }
    
    public AudioFormat getFormat() {
        if (stream != null) {
            return stream.getFormat();
        }
        
        return null;
    }
    
    public Sound setVolume(double value) {
        if (value < 0.0) {
            value = 0.0;
        } else if (value > 1.0) {
            value = 1.0;
        }
        volume = value;
        
        return this;
    }
    
    public double getVolume() {
        return volume;
    }
	
    // проигрывается ли звук в данный момент
    public boolean isPlaying() {
        return playing;
    }

    /** 
     * Запуск
     * @param breakOld определяет поведение, если звук уже играется
      Если breakOld==true, о звук будет прерван и запущен заново
      Иначе ничего не произойдёт
    */
    public Sound play(boolean breakOld) {                
        if (stream != null) {
            if (playing && breakOld) {
                rewind();
            }
            
            if (!playing) {
                SoundSystem.playSound(this);
                playing = true;
            }
        } 
        
        return this;
    }
	
    /** 
     * То же самое, что и play(true)
     */
    public Sound play() {
        return play(true);
    }
    
    public Sound loop() {
        setLooping(true);
        return play();
    }
    
    public Sound rewind() {        
        if (inputFile != null) {
            prepareStream(inputFile);
        } else if (stream != null && stream.markSupported()) {
            try {
                stream.reset();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }        
        
        return this;
    }
	
    // Останавливает воспроизведение
    public Sound stop() {
        SoundSystem.stopSound(this);
        playing = false;
        
        return this;
    }

    public Sound setLooping(boolean looping) {
        this.looping = looping;
        return this;
    }

    public boolean isLooping() {
        return looping;
    }
	
    @Override
    public void close() {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
    }
    
    int read(byte[] buffer) {        
        try {
            return stream.read(buffer, 0, buffer.length);            
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
}