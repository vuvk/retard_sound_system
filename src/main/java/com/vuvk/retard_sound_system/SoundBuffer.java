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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

/**
 * load sound to memory buffer
 * @author vuvk
 */
public class SoundBuffer {
    private AudioFormat format;
    private byte[] buffer;
    
    public SoundBuffer(String path) {
        this(new File(path));
    }
    
    public SoundBuffer(File file) {      
        AudioInputStream stream = SoundSystem.getEncodedAudioInputStream(file);
        if (stream != null) {
            try {
                format = stream.getFormat();
                
                List<Byte> buf = new ArrayList<>((int)file.length());
                int n;
                int bufferSize = 0;
                byte[] readed = new byte[65535];
                while ((n = stream.read(readed)) != -1) {
                    bufferSize += n;
                    for (int i = 0; i < n; ++i) {
                        buf.add(readed[i]);
                    }
                }
                
                buffer = new byte[bufferSize];
                for (n = 0; n < bufferSize; ++n) {
                    buffer[n] = buf.get(n);
                }
                buf.clear();                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public AudioInputStream getAudioInputStream() {
        if (buffer == null || format == null) {
            return null;
        }
        
        return new AudioInputStream(new ByteArrayInputStream(buffer), format, buffer.length);
    }
}
