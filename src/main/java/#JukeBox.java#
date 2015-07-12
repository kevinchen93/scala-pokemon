import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class JukeBox {

	private BackgroundSound bs;
	private HashMap availableClips;
	private HashMap playingClips;
	private int nextClip = 0;
	private boolean debug;
	
	public JukeBox () {
		availableClips = new HashMap();
		playingClips = new HashMap();
	}
	
	public void setDebug (boolean b) {
		debug = b;
	}

	public boolean loadClip (String resourcePath, 
			String soundName, 
			int howMany) {
		for (int x = 0; x < howMany; x++) {
			try {
				InputStream is = getClass().getResourceAsStream(resourcePath);
				if (is == null) {
					System.out.println("Can't find sound");
					return false;
				}
				boolean loaded = loadClip(is, soundName);
				if (!loaded) {
					System.out.println("Can't load sound");
					return false;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	private boolean loadClip (InputStream is, 
			String name) {
		
		name = trimExtension(name);

		if (!availableClips.containsKey(name)) {
			availableClips.put(name, new LinkedList());
		}
		
		List list = (List) availableClips.get(name);

		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(is);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if (audioInputStream == null) {
			return false;
		}
		
		AudioFormat	format = audioInputStream.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		Sound clip = null;
		try {
			clip = new Sound((Clip) AudioSystem.getLine(info),
					audioInputStream,
					name,
					nextClip++,
					this);
			list.add(clip);
			
			audioInputStream.close();
		}
		catch (LineUnavailableException e) {
			e.printStackTrace();
			return false;
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		if (clip == null) {
			return false;
		}
		
		return true;
	}
	
	private static String trimExtension (String name) {
		int last = name.lastIndexOf(".");
		if (last == 0) {
			throw new IllegalArgumentException (
					"can't start a name with a dot");
		}
		if (last > -1) {
			return name.substring(0, last);
		}
		else {
			return name;
		}
	}
	
	public int playClip (String name) {
		return playClip(name, 1);
	}
	public synchronized int playClip (String name, 
			int numberOfLoops) {
		name = trimExtension(name);
		if (!availableClips.containsKey(name)) {
			return -1;
		}
		List clips = (List) availableClips.get(name);
		print("gonna playClip " + name + "" +
				" from the " + clips.size() + " available copies");
		
		print(Clip.LOOP_CONTINUOUSLY + "");
		
		if (clips.isEmpty()) {
			return -1;
		}
		
		Sound clip = (Sound) clips.remove(0);
		playingClips.put(new Integer(clip.getID()), clip);
		if (numberOfLoops == 1) {
			clip.play();
		}
		else {
			print("continuous looping call");
			clip.loop(numberOfLoops);
		}
		
		return clip.getID();		
	}
	
	public static int getSoundLength(InputStream is) {
		AudioInputStream ais = null;
		try {
			ais = AudioSystem.getAudioInputStream(is);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (ais == null) {
			return -1;
		}
		
		long frames = ais.getFrameLength();
		
		AudioFormat	format = ais.getFormat();
		
		float framesPerSecond = format.getFrameRate();
		
		int seconds = (int) (frames / framesPerSecond);
		
		return seconds;
	}
	
	public synchronized BackgroundSound playBackground (InputStream is) {
		if (bs != null) {
			bs.stopBackgroundSound(false);
		}
		
		AudioInputStream ais = null;
		try {
			ais = AudioSystem.getAudioInputStream(is);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (ais == null) {
			return null;
		}
		
		AudioFormat	format = ais.getFormat();
		DataLine.Info info = new DataLine.Info(
				SourceDataLine.class, format);
		
		SourceDataLine background = null;
		
		try {
			background = (SourceDataLine) AudioSystem.getLine(info);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
		try {
			background.open();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		background.start();
		
		bs = new BackgroundSound(ais, background);
		return bs;
	}
	
	public void stopCurrentBackgroundSound(boolean noMoreSounds) {
		if (bs != null) {
			bs.stopBackgroundSound(noMoreSounds);
		}
	}

	public synchronized void makeAvailable(Sound sound) {
		if (availableClips.containsKey(sound.getName())) {
			playingClips.remove(new Integer(sound.getID()));
			((List) availableClips.get(sound.getName())).add(sound);
		}
	}
	
	public synchronized void stopClip (int id) {
		Integer ID = new Integer(id);
		if (playingClips.containsKey(ID)) {
			Sound s = (Sound) playingClips.get(ID);
			s.stop();
		}
	}
	
    public synchronized void stopAllClips () {
        Iterator it = playingClips.keySet().iterator();
        while (it.hasNext()) {
            Sound s = (Sound) playingClips.get((Integer)it.next());
            s.stop();
        }
    }
    
    private void print (String message) {
		if (debug) {
			print(message);
		}
	}

	public synchronized void close() {
		stopAllClips();
		stopCurrentBackgroundSound(true);
	}

	public class Sound implements LineListener {
		
		private Clip m_clip;
		private String name;
		private int id;
		private boolean looping = false;
		JukeBox jukeBox;
		public Sound (Clip clip, AudioInputStream ais, 
				String name,
				int id,
				JukeBox jukeBox) 
				throws LineUnavailableException, IOException {
			this.name = name;
			this.id = id;
			m_clip = clip;
			m_clip.addLineListener(this);
			m_clip.open(ais);
			this.jukeBox = jukeBox;
		}

		public String getName () {
			return name;
		}
		
		public void update(LineEvent event)
		{
			if (event.getType().equals(LineEvent.Type.STOP))
			{
				if (looping) {
					return;
				}

				m_clip.stop();
				m_clip.setFramePosition(0);
				jukeBox.makeAvailable(this);
			}
		}
		
		public void play() {
			m_clip.start();
		}
		
		public void loop(int numberOfLoops) {
			m_clip.setLoopPoints(0, -1);
			looping = true;
			m_clip.loop(numberOfLoops);
		}

		public int getID() {
			return id;
		}

		public void stop() {
			looping = false;
			m_clip.stop();
		}
	}	
	
	public class BackgroundSound extends Thread {
		
		private AudioInputStream ais;
		private SourceDataLine sdl;
		private boolean done;
		private boolean kill;
		BackgroundSoundObserver bso;
		
		public BackgroundSound (AudioInputStream ais,
				SourceDataLine sdl) {
			this(ais, sdl, null);
		}
		
		public BackgroundSound (AudioInputStream ais,
				SourceDataLine  sdl,
				BackgroundSoundObserver bso) {
			this.ais = ais;
			this.sdl = sdl;
			this.bso = bso;
			done = false;
			start();
		}
		
		public void run() {
			boolean allBytesRead = false;
			while (!done) {
				int	nBytesRead = 0;
				byte[]	abData = new byte[2048];
				while (nBytesRead != -1 && !done) {
					try {
						nBytesRead = ais.read(abData, 0, abData.length);
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					if (nBytesRead >= 0) {
						sdl.write(abData, 0, nBytesRead);
					}
					else {
						done = true;
						allBytesRead = true;
					}
				}
			}
			sdl.drain();
			sdl.stop();
			sdl.close();
			if (!kill && bso != null) {
				bso.soundDone(this, allBytesRead);
			}
		}

		public void registerObserver (BackgroundSoundObserver bso) {
			this.bso = bso;
		}
		
		public void stopBackgroundSound(boolean noMoreSounds) {
			if (done) {
				return;
			}
			done = true;
			kill = noMoreSounds;
		}
	}
	
	public interface BackgroundSoundObserver {
		public void soundDone(BackgroundSound bs,
				boolean allBytesRead);
		
	}

}
