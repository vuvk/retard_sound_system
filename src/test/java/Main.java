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


import com.vuvk.retard_sound_system.Music;
import com.vuvk.retard_sound_system.Sound;
import com.vuvk.retard_sound_system.SoundBuffer;
import com.vuvk.retard_sound_system.SoundSystem;

/**
 *
 * @author vuvk
 */
public class Main {    
    final static Sound birdsMono     = new Sound("birds_mono.wav");
    final static Sound thunderMono   = new Sound("thunder_mono.wav", true);
    final static Sound raindropsMono = new Sound("rain_mono.wav");

    final static Sound birdsStereo     = new Sound("birds_stereo.wav", true);
    final static Sound thunderStereo   = new Sound("thunder_stereo.wav");
    final static SoundBuffer raindropsStereoBuffer = new SoundBuffer("rain_stereo.wav");
    final static Sound raindropsStereo = new Sound(raindropsStereoBuffer);
    
    final static Music ogg = new Music("music.ogg");  
    final static Music mp3 = new Music("music.mp3");
    
    public static void main(String[] args) throws InterruptedException {
        
        System.out.println("start sound system");
        SoundSystem.start();          
        
        System.out.println("test mono line");
        birdsMono.loop();   
        
        Thread.sleep(3000); 
        
        System.out.println("add rain - volume 0.3");
        raindropsMono.setVolume(0.3);
        raindropsMono.loop();
        
        Thread.sleep(3000);
        
        System.out.println("set rain volume to 1.0");
        raindropsMono.setVolume(1);
        
        Thread.sleep(3000);
        
        System.out.println("add thunder");
        thunderMono.play();
        
        Thread.sleep(6000);
        
        System.out.println("stop mono line");
        birdsMono.stop();
        thunderMono.stop();
        raindropsMono.stop();    
        
       
        System.out.println("test stereo line");
        birdsStereo.loop();
        
        Thread.sleep(3000);
        
        System.out.println("add rain - volume 0.3");
        raindropsStereo.setVolume(0.3);
        raindropsStereo.loop();
        
        Thread.sleep(3000);
        
        System.out.println("set rain volume to 1.0");
        raindropsStereo.setVolume(1);
        
        Thread.sleep(3000);
        
        System.out.println("add thunder");
        thunderStereo.play();
        
        Thread.sleep(6000);
        
        System.out.println("stop stereo line");
        birdsStereo.stop();
        thunderStereo.stop();
        raindropsStereo.stop();  
        
        
        System.out.print("try play ogg (volume 0.7)...");
        ogg.setVolume(0.7);  
        ogg.play();       
        Thread.sleep(3000); 
        ogg.stop();
        System.out.println("stop!");
        
        System.out.println("try play mp3 in loop (volume 0.3)...");
        mp3.loop();
        mp3.setVolume(0.3);        
        Thread.sleep(5000);
        
        System.out.println("set mp3 volume to 1.0");
        mp3.setVolume(1.0);
        Thread.sleep(5000);     
                
        System.out.println("stop sound system");
        SoundSystem.stop();
    }
}
