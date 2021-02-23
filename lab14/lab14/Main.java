package lab14;

public class Main
{

	public static void main(String[] args)
	{
		/** Your code here. */
		Generator generator = new SineWaveGenerator(200);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
		gav.drawAndPlay(4096, 1000000);
	}

} 