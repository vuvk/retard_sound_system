

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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vuvk
 */
public class Main {     
    /* from folder */
    final static Sound birdsMono     = new Sound("birds_mono.wav");
    final static Sound thunderMono   = new Sound("thunder_mono.wav", true);
    final static Sound raindropsMono = new Sound("rain_mono.wav");

    final static Sound birdsStereo     = new Sound("birds_stereo.wav", true);
    final static Sound thunderStereo   = new Sound("thunder_stereo.wav");
    final static SoundBuffer raindropsStereoBuffer = new SoundBuffer("rain_stereo.wav");
    final static Sound raindropsStereo = new Sound(raindropsStereoBuffer);
    
    final static Music ogg = new Music("music.ogg");  
    final static Music mp3 = new Music("music.mp3");
    
    /* from jar */
    final static Sound resSound = new Sound(Main.class.getResource("/test_sounds/birds_mono.wav"));
    final static SoundBuffer resSoundBuffer = new SoundBuffer(Main.class.getResource("/test_sounds/birds_mono.wav"));
    final static Sound resSoundBuffered = new Sound(resSoundBuffer);
    final static Music resMusic = new Music(Main.class.getResource("/test_sounds/music.mp3"));   
    
    /* from INET */
    static Sound urlSound;
    static SoundBuffer urlBuffer;
    static Sound urlSoundBuffered;
    static Music urlMusic;
    
    final static String URL_SOUND = "http://csfiles.maniapc.org/cs/sound/weapons/bobi_throw.wav";
    final static String URL_MUSIC = "http://www.markerbeacon.org/wp-content/uploads/audio/15_Megahertz.mp3";
    
    static {
        try {
            urlSound  = new Sound(new URL(URL_SOUND));
            urlBuffer = new SoundBuffer(new URL(URL_SOUND));
            urlSoundBuffered = new Sound(urlBuffer);
            urlMusic  = new Music(new URL(URL_MUSIC));
        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public static void main(String[] args) throws InterruptedException {
        
        System.out.println("start sound system");
        SoundSystem.start();     
        
        System.out.println("test mono line");  
        birdsMono.loop();
        Thread.sleep(3000);
        
        System.out.println("stop sound and retry play");
        birdsMono.stop();
        birdsMono.loop();
        Thread.sleep(3000);
        System.out.println("stop sound and retry play once again");
        birdsMono.stop();
        birdsMono.loop();
        Thread.sleep(3000);
        System.out.println("only retry play");
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
        mp3.stop();       
        
        System.out.println("try play wav from jar");  
        resSound.play();
        Thread.sleep(3000);
        System.out.println("try play buffered wav from jar");    
        resSound.stop();
        resSoundBuffered.play();
        Thread.sleep(3000);
        System.out.println("try play mp3 from jar");  
        resSoundBuffered.stop();
        resMusic.play();
        Thread.sleep(5000);
        resMusic.stop();        
               
        System.out.println("try play wav from url");        
        urlSound.play();
        Thread.sleep(3000); 
        System.out.println("try play buffered wav from url");        
        urlSoundBuffered.play();
        Thread.sleep(3000); 
        System.out.println("try play mp3 from url");     
        urlMusic.play();
        Thread.sleep(10000);
        urlMusic.stop();    
                
        System.out.println("stop sound system");
        SoundSystem.stop();
    }
}
