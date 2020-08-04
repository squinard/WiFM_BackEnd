import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

import javax.sound.sampled.LineUnavailableException;

public class MainActivity {

    public static void main(String[] args) throws LineUnavailableException {
        int SAMPLE_RATE = 44100;
        int BUFFER_SIZE = 1024;
        // Listens to line in
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(SAMPLE_RATE, BUFFER_SIZE, 0);


        // Finds resultant pitch
        PitchDetectionHandler printPitch = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
                double result = pitchDetectionResult.getPitch();
                if (result > 300 && result < 44000) {
                    // Displays frequency
                    //System.out.println(Math.round((result) * 256 / SAMPLE_RATE));
                    // Converts sound frequencies back into character.
                    System.out.println((char) ((Math.round((result) * 256 / SAMPLE_RATE))));
                }
            }
        };
        // Use pitch estimate
        PitchProcessor.PitchEstimationAlgorithm algorithm = PitchProcessor.PitchEstimationAlgorithm.AMDF;
        AudioProcessor pitchEstimator = new PitchProcessor(algorithm, SAMPLE_RATE, BUFFER_SIZE, printPitch);


        dispatcher.addAudioProcessor(pitchEstimator);
        //dispatcher.addAudioProcessor(new AudioPlayer(new AudioFormat(SAMPLE_RATE, 16, 1, true, true)));

        dispatcher.run();







        /*
        boolean stopped;
        AudioFormat format = new AudioFormat(44100, 8, 1, false, true);
        TargetDataLine line;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class,
                format); // format is an AudioFormat object

        try {
            // Gets recording device
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            // Creates a stream for audio data
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] data = new byte[line.getBufferSize() / 5];
            int numBytesRead;
            // Opens line to receive data
            line.start();
            stopped = false;
            int i = 0;
            // Fast fourier transform to detect frequency of sine wave
            while (!stopped) {
                numBytesRead = line.read(data, 0, 10);


                /*if ((data[numBytesRead - 1] & 0xFF) > 140
                        && (data[numBytesRead - 2] & 0xFF) > 140
                        && (data[numBytesRead - 3] & 0xFF) > 140
                        && (data[numBytesRead - 4] & 0xFF) > 140
                        && (data[numBytesRead - 5] & 0xFF) > 140) {
                    data[numBytesRead - 1] = (byte) ((data[numBytesRead - 1] & 0xFF) + 128);
                    data[numBytesRead - 2] = (byte) ((data[numBytesRead - 2] & 0xFF) + 128);
                    data[numBytesRead - 3] = (byte) ((data[numBytesRead - 3] & 0xFF) + 128);
                    data[numBytesRead - 4] = (byte) ((data[numBytesRead - 4] & 0xFF) + 128);
                    data[numBytesRead - 5] = (byte) ((data[numBytesRead - 5] & 0xFF) + 128);
                    int average = Math.round((data[numBytesRead - 1] + data[numBytesRead - 2] + data[numBytesRead - 3] + data[numBytesRead - 4] + data[numBytesRead - 5]) / 5);
                    //System.out.println(data[numBytesRead - 1] + data[numBytesRead - 1] + 3);
                    //System.out.print(i + " ");

                    System.out.println(average);
                    //System.out.println((line.getLevel()));
                    System.out.println((char) (average + 50));
            }
        }

    } catch(
    LineUnavailableException ex)

    {
        System.out.println("Error processing audio: " + ex);
    }
*/
        /*
            InputStream in =
            encoding = con.getContentEncoding();  // ** WRONG: should use "con.getContentType()" instead but it returns something like "text/html; charset=UTF-8" so this value must be parsed to extract the actual encoding
            encoding = encoding == null ? "UTF-8" : encoding;
            String body = IOUtils.toString(in, encoding);
            System.out.println(body);

         */
    }
}
