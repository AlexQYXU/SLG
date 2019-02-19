import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.*;

public class changeSize {
    public static void main(String[] args) {
        changeSize cs = new changeSize();
        String path = System.getProperty("user.dir") + "/src/main/resources/map/Source/Chapter04_Source.png";
        cs.changeSize(17*32, 12*32, path);
    }

    public boolean changeSize(int newWidth, int newHeight, String path) {
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(path));

            //字节流转图片对象
            Image bi = ImageIO.read(in);
            System.out.print(((BufferedImage) bi).getWidth() + "  " + ((BufferedImage) bi).getHeight());
            //构建图片流
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            System.out.print(tag.getWidth() + " " + tag.getHeight());
            //绘制改变尺寸后的图
            tag.getGraphics().drawImage(bi, 0, 0, 17*32, 12*32, null);
            //输出流
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(System.getProperty("user.dir") + "/src/main/resources/map/Chapter04.png"));
            //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            //encoder.encode(tag);
            ImageIO.write(tag, "PNG", out);
            in.close();
            out.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static BufferedImage sub_Image(BufferedImage map) {

        BufferedImage sub_map = new BufferedImage(map.getWidth()/4 , map.getHeight() /4, BufferedImage.TYPE_INT_RGB);
        sub_map.getGraphics().drawImage(map,0,0,map.getWidth()/4 , map.getHeight() /4,null);
        return sub_map;
    }

    public static BufferedImage map(BufferedImage map,int width,int height){

        BufferedImage sub_map = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        sub_map.getGraphics().drawImage(map,0,0,width, height,null);
        return sub_map;
    }

}
