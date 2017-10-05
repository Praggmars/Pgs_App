package Program.ImageProgram;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public enum ImageEffect {
    NORMAL{
        @Override
        public void ApplyEffect(Mat src, Mat dst) {
            src.copyTo(dst);
        }

        @Override
        public String toString() {
            return "No effect";
        }
    },
    BLACKANDWHITE{
        @Override
        public void ApplyEffect(Mat src, Mat dst) {
            Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
        }

        @Override
        public String toString() {
            return "Black and white";
        }
    },
    THRESHOLD{
        @Override
        public void ApplyEffect(Mat src, Mat dst) {
            Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
            Imgproc.threshold(dst, dst, 127, 255, Imgproc.THRESH_BINARY);
        }

        @Override
        public String toString() {
            return "Threshold";
        }
    },
    ADAPTIVE_THRESHOLD{
        @Override
        public void ApplyEffect(Mat src, Mat dst) {
            Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
            Imgproc.adaptiveThreshold(dst, dst, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 55, 1);
        }

        @Override
        public String toString() {
            return "Adatptive threshold";
        }
    },
    EDGE_DETECTION{
        @Override
        public void ApplyEffect(Mat src, Mat dst) {
            Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
            Imgproc.Canny(dst, dst, 60, 200);
        }

        @Override
        public String toString() {
            return "Edge detection";
        }
    };

    public abstract void ApplyEffect(Mat src, Mat dst);
}
