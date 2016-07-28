package ph.edu.dlsu;

import org.opencv.contrib.Contrib;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.*;

public class Main {

    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.loadLibrary("opencv_ffmpeg2413");  // This is necessary for WINDOWS System
                                                  // if the path is not set
    }


    private JFrame frame;
    private JLabel imageLabel;

    public static void main(String[] args) throws InterruptedException {
        Main app = new Main();
        app.initGUI();
        app.runMainLoop(args);
    }

    private void initGUI() {
        frame = new JFrame("Video Playback Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        imageLabel = new JLabel();
        frame.add(imageLabel);
        frame.setVisible(true);
    }

    private void runMainLoop(String[] args) throws InterruptedException {
        ImageViewer imageProcessor = new ImageViewer();
        Mat frame = new Mat();
        Mat processed = new Mat();
        Image tempImage;
        VideoCapture capture = new VideoCapture("res/Wildlife.wmv");
        if( capture.isOpened()){
            while (true){
                capture.read(frame);
                if( !frame.empty() ){

                    processFrame(frame, processed);

                    tempImage= imageProcessor.toBufferedImage(processed);
                    ImageIcon imageIcon = new ImageIcon(tempImage, "Video playback");
                    imageLabel.setIcon(imageIcon);
                    this.frame.pack();  //this will resize the window to fit the image
                    Thread.sleep(50);
                }
                else{
                    System.out.println(" Frame not captured or video has finished");
                    break;
                }
            }
        }
        else{
            System.out.println("Couldn't open video file.");
        }

    }

    private void processFrame(Mat srcImage, Mat outImage){
        Imgproc.cvtColor(srcImage, outImage, Imgproc.COLOR_BGR2GRAY);
        Contrib.applyColorMap(outImage, outImage, Contrib.COLORMAP_JET);
    }
}
