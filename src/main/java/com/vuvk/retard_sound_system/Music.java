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

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Control;

/**
 *
 * @author vuvk
 */
public class Music {
    private static final Logger LOG = Logger.getLogger(Music.class.getName());    
    private static final int BUFFER_SIZE = 10240;
    
    private File inputFile;
    private AudioInputStream inputFileStream;
    private AudioInputStream inputAudioStream;
    private AudioFormat format;
    private SourceDataLine line;
    private double volume = 1.0;
    private boolean looping = false;
    private boolean playing = false;
    
    public Music(String path) {
        this(new File(path));
    }
    
    public Music(File file) {
        inputFile = file;        
        SoundSystem.MUSICS.add(this);
    }   
    
    public Music play() {
        return play(false);
    }
    
    public Music play(final boolean looping) {
        this.looping = looping;
        
        if (playing) {
            stop();
        }
        
        prepareStream();
            
        playing = true;
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                final byte[] buffer = new byte[BUFFER_SIZE];

                while (inputAudioStream != null && 
                       line             != null && 
                       isPlaying()) {
                    
                    int cnt = 0;                    
                    Arrays.fill(buffer, (byte)0);
                    
                    try {
                        if ((cnt = inputAudioStream.read(buffer, 0, buffer.length)) != -1) {
                            // apply volume
                            for (int i = 0; i < buffer.length; i += 2) {                                
                                byte low  = buffer[i];
                                byte high = buffer[i + 1];

                                int value = (short)(((high & 0xFF) << 8) | (low & 0xFF));
                                value *= volume;                                
                                
                                buffer[i]     = (byte) value;
                                buffer[i + 1] = (byte) (value >> 8);
                            }
                            
                            line.write(buffer, 0, cnt); 
                        } else {
                            break;
                        }
                    } catch (IOException ex) {
                        LOG.log(Level.SEVERE, null, ex);
                    }
                }

                if (looping) {
                    play();
                } else {
                    stop();
                }
            }
        }).start();
        
        return this;
    }
    
    public Music loop() {
        return play(true);
    }
    
    public Music stop() {
        looping = false;
        playing = false;
        
        line = null;
        
        if (inputAudioStream != null) {
            try {
                inputAudioStream.close();
                inputAudioStream = null;
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        } 
        
        if (inputFileStream != null) {
            try {
                inputFileStream.close();
                inputFileStream = null;
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
        
        return this;
    }

    public boolean isLooping() {
        return looping;
    }

    public final boolean isPlaying() {
        return playing;
    }   
    
    public AudioFormat getFormat() {
        return format;
    }     
        
    public Music setVolume(double value) {
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
        
    private void prepareStream() {
        inputFileStream = null;
        try {
            inputFileStream = AudioSystem.getAudioInputStream(inputFile);
        } catch (UnsupportedAudioFileException | IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        
        if (inputFileStream.getFormat().getChannels() == 1) {
            format = SoundSystem.getAudioMonoFormat();
        } else {
            format = SoundSystem.getAudioStereoFormat();
        }
        final DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        inputAudioStream = AudioSystem.getAudioInputStream(format, inputFileStream);
            
        try {
            line = (SourceDataLine) AudioSystem.getLine(info);
            Control[] controls = line.getControls();
            for (Control control : controls) {
                System.out.println(control);
            }
            line.open(format);
            line.start();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Music.class.getName()).log(Level.SEVERE, null, ex);
        }            
    }
}
