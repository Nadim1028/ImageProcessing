import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SkinMainClass
{
    public static void main(String[] args) throws IOException
    {
        File maskFolder = new File("data\\mask");
        File normalFolder = new File("data\\normal");

        File[] maskFiles = maskFolder.listFiles(),normalFiles = normalFolder.listFiles();
        BufferedImage normalImage=null,muskImage=null;
        int width,height;double ArraySumSkin=0,ArraySumNonSkin=0;
        double[][][] skin = new double[256][256][256],nonSkin = new double[256][256][256],probOfSkin = new double[256][256][256];

        BufferedWriter output1 = new BufferedWriter(new FileWriter("data\\output1.txt"));
        BufferedWriter output2 = new BufferedWriter(new FileWriter("data\\output2.txt"));

        for (int i=0;i<256;i++)
        {
            for (int j=0;j<256;j++)
            {
                for (int k=0;k<256;k++)
                {
                    skin[i][j][k]=0;
                    nonSkin[i][j][k]=0;

                }
            }
        }

        //for (File f  : maskFiles &&  f2:normalFiles)
        for(int l=0; l<maskFiles.length && l<normalFiles.length;l++)
        {

            File normalFile =normalFiles[l];
            File muskFile = maskFiles[l];


               try
               {
                   muskImage=ImageIO.read(muskFile);
                   normalImage=ImageIO.read(normalFile);

                   width=muskImage.getWidth();
                   height=muskImage.getHeight();

                   for (int i = 0; i < width; i++)
                   {

                       for (int j = 0; j < height; j++)
                       {



                           Color muskColor = new Color(muskImage.getRGB(i, j));

                           Color normalColor = new Color(normalImage.getRGB(i, j));



                           if (muskColor.getRed() > 240 && muskColor.getBlue() > 240 && muskColor.getGreen() > 240)
                           {


                               nonSkin[normalColor.getRed()][normalColor.getGreen()][normalColor.getBlue()]++;

                           }

                           else {

                               skin[normalColor.getRed()][normalColor.getGreen()][normalColor.getBlue()]++;
                           }


                       }

                   }

               }

               catch (Exception e) {
                   e.printStackTrace();
               }

        }


        for (int i=0;i<256;i++)
        {
            for (int j=0;j<256;j++)
            {
                for (int k=0;k<256;k++)
                {
                    ArraySumSkin += skin[i][j][k];
                    ArraySumNonSkin += nonSkin[i][j][k];

                }
            }
        }

        for (int i=0;i<256;i++)
        {
            for (int j=0;j<256;j++)
            {
                for (int k=0;k<256;k++)
                {
                    skin[i][j][k] = skin[i][j][k]/ArraySumSkin;
                    nonSkin[i][j][k] = nonSkin[i][j][k]/ArraySumNonSkin;

                }
            }
        }

        for (int i=0;i<256;i++)
        {
            for (int j=0;j<256;j++)
            {
                for (int k=0;k<256;k++)
                {
                    if(skin[i][j][k]==0 && nonSkin[i][j][k]==0)
                        probOfSkin[i][j][k]=0;

                    else if(nonSkin[i][j][k]==0)
                        probOfSkin[i][j][k]=100;

                    else
                        probOfSkin[i][j][k]=skin[i][j][k]/nonSkin[i][j][k];



                }
            }
        }

        for (int i=0;i<256;i++)
        {
            for (int j=0;j<256;j++)
            {
                for (int k=0;k<256;k++)
                {
                    output1.write( probOfSkin[i][j][k]+"\n");

                }
            }
        }

        File input = new File("data\\test.jpg");
        BufferedImage testImg=null;
        try {
            testImg=ImageIO.read(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(testImg);
        int newHeight=testImg.getHeight();
        int newWidth=testImg.getWidth();



        for (int i = 0; i < newWidth; i++)
        {

            for (int j = 0; j < newHeight; j++) {

                Color newColor = new Color(testImg.getRGB(i, j));
                if (probOfSkin[newColor.getRed()][newColor.getGreen()][newColor.getBlue()] > 0.4) {
                    testImg.setRGB(i, j, Color.white.getRGB());
                }

            }

        }

        ImageIO.write(testImg,"jpg",new File("data\\output.jpg"));

    }

}

