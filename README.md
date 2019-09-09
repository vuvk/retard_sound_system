About
-----
RetardSoundSystem is a simple sound system that wraps the standard Java Sound API.

Features
-----
- Mixing several sounds in a separate thread. 
- Playing, buffering sounds from file, memory, stream, URL or as resource from jar.
- Playing music from file, memory, stream, URL or as resource from jar.

Intentionally it used old Java 7 standard.

Demo
-----
https://www.youtube.com/watch?v=r5YJZWikRUc

Build
-----
`$ mvn clean package`

Releases
--------
If you would just like to download the jar files, see the releases page:

https://github.com/vuvk/retard_sound_system/releases

License
-------
RSS is licensed under the Apache 2.0 license.  A copy of the license can
be found in the header of every source file as well as in the LICENSE file.

Audio Formats
-------------
RSS stores all sounds as 16-bit, 44.1kHz, 2-channel, linear PCM data
internally (WAV-files). It makes an effort to play other formats, but will not be able
to handle all formats (OGG, MP3).
You need use class `Sound` for wav-files (or `SoundBuffer`), and class `Music` for ogg and mp3 files.

Using
---------------
See example in `tests`.
