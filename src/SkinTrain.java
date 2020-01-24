import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class SkinTrain {


    public static void main(String[] args) throws IOException {



        BufferedImage mainImage=null,muskImage=null;
        int height;
        int width;
        double ArraySumS=0;
        double ArraySumNS=0;
        double[][][] skin = new double[256][256][256];
        double[][][] nonSkin = new double[256][256][256];
        double[][][] probSkin = new double[256][256][256];

        BufferedWriter out = new BufferedWriter(new FileWriter("D:\\Ifti\\DBMS 02\\SkinDetector-20200119T075207Z-001\\SkinDetector\\ouput.txt"));
        BufferedWriter outTest = new BufferedWriter(new FileWriter("D:\\Ifti\\DBMS 02\\SkinDetector-20200119T075207Z-001\\SkinDetector\\ouputTest.txt"));




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

        try {

            int count = 0;
            //String countS = String.valueOf(count);



            while (count<10)
            {

                System.out.println("Loop1");
                String countS = String.valueOf(count);
                String pathMusk = "C:\\IIT Files\\5th Semester\\DBMS-2\\ibtd-20200118T175313Z-001\\ibtd\\000" + countS + ".bmp";
                String pathMain = "C:\\IIT Files\\5th Semester\\DBMS-2\\ibtd-20200118T175313Z-001\\ibtd\\000" + countS + ".jpg";


                File musk = new File(pathMusk);
                File main = new File(pathMain);

                muskImage = ImageIO.read(musk);
                mainImage = ImageIO.read(main);

                height = muskImage.getHeight();
                width = mainImage.getWidth();



                for (int i = 0; i < width; i++) {

                    for (int j = 0; j < height; j++)
                    {



                        Color muskColor = new Color(muskImage.getRGB(i, j));

                        Color mainColor = new Color(mainImage.getRGB(i, j));



                        if (muskColor.getRed() > 240 && muskColor.getBlue() > 240 && muskColor.getGreen() > 240) {


                            nonSkin[mainColor.getRed()][mainColor.getGreen()][mainColor.getBlue()]++;

                        } else {


                            skin[mainColor.getRed()][mainColor.getGreen()][mainColor.getBlue()]++;

                        }


                    }

                }

                count++;
                //if (count>9) break;

            }





            while (count>9 && count<100)
            {
                System.out.println("Loop2");
                System.out.println(count);
                String countS = String.valueOf(count);

                String pathMusk = "C:\\IIT Files\\5th Semester\\DBMS-2\\ibtd-20200118T175313Z-001\\ibtd\\00" + countS + ".bmp";
                String pathMain = "C:\\IIT Files\\5th Semester\\DBMS-2\\ibtd-20200118T175313Z-001\\ibtd\\00" + countS + ".jpg";


                File musk = new File(pathMusk);
                File main = new File(pathMain);
                muskImage = ImageIO.read(musk);
                mainImage = ImageIO.read(main);

                height = muskImage.getHeight();
                width = mainImage.getWidth();


                for (int i = 0; i < width; i++) {

                    for (int j = 0; j < height; j++) {


                        Color muskColor = new Color(muskImage.getRGB(i, j));
                        Color mainColor = new Color(mainImage.getRGB(i, j));


                        if (muskColor.getRed() > 240 && muskColor.getBlue() > 240 && muskColor.getGreen() > 240) {

                            nonSkin[mainColor.getRed()][mainColor.getGreen()][mainColor.getBlue()]++;

                        } else {

                            skin[mainColor.getRed()][mainColor.getGreen()][mainColor.getBlue()]++;

                        }


                    }

                }


                count++;

                //if (count>99) break;
            }





            while (count>99 && count<444)
            {
                System.out.println("Loop3");
                String countS = String.valueOf(count);
                String pathMusk = "C:\\IIT Files\\5th Semester\\DBMS-2\\ibtd-20200118T175313Z-001\\ibtd\\0" + countS + ".bmp";
                String pathMain = "C:\\IIT Files\\5th Semester\\DBMS-2\\ibtd-20200118T175313Z-001\\ibtd\\0" + countS + ".jpg";


                File musk = new File(pathMusk);
                File main = new File(pathMain);

                muskImage = ImageIO.read(musk);
                mainImage = ImageIO.read(main);

                height = muskImage.getHeight();
                width = mainImage.getWidth();


                for (int i = 0; i < width; i++) {

                    for (int j = 0; j < height; j++) {


                        Color muskColor = new Color(muskImage.getRGB(i, j));
                        Color mainColor = new Color(mainImage.getRGB(i, j));


                        if (muskColor.getRed() > 240 && muskColor.getBlue() > 240 && muskColor.getGreen() > 240) {

                            nonSkin[mainColor.getRed()][mainColor.getGreen()][mainColor.getBlue()]++;

                        } else {

                            skin[mainColor.getRed()][mainColor.getGreen()][mainColor.getBlue()]++;

                        }


                    }

                }


                count++;


            }

            for (int i=0;i<256;i++)
            {
                for (int j=0;j<256;j++)
                {
                    for (int k=0;k<256;k++)
                    {
                        ArraySumS=ArraySumS+skin[i][j][k];
                        ArraySumNS=ArraySumNS+nonSkin[i][j][k];

                    }
                }
            }

            for (int i=0;i<256;i++)
            {
                for (int j=0;j<256;j++)
                {
                    for (int k=0;k<256;k++)
                    {
                        skin[i][j][k]=skin[i][j][k]/ArraySumS;
                        nonSkin[i][j][k]=nonSkin[i][j][k]/ArraySumNS;

                    }
                }
            }

            for (int i=0;i<256;i++)
            {
                for (int j=0;j<256;j++)
                {
                    for (int k=0;k<256;k++)
                    {
                        if(skin[i][j][k]==0 && nonSkin[i][j][k]==0) probSkin[i][j][k]=0;

                        else if(nonSkin[i][j][k]==0) probSkin[i][j][k]=100;


                        else probSkin[i][j][k]=skin[i][j][k]/nonSkin[i][j][k];



                    }
                }
            }

            for (int i=0;i<256;i++)
            {
                for (int j=0;j<256;j++)
                {
                    for (int k=0;k<256;k++)
                    {
                        out.write( probSkin[i][j][k]+"\n");

                    }
                }
            }

            File input=new File("C:\\IIT Files\\5th Semester\\DBMS-2\\ibtd-20200118T175313Z-001\\ibtd\\0542.jpg");
            BufferedImage testImg=null;
            try
            {
                testImg=ImageIO.read(input);
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }
            System.out.println(testImg);
            int newHeight=testImg.getHeight();
            int newWidth=testImg.getWidth();



            for (int i = 0; i < newWidth; i++) {

                for (int j = 0; j < newHeight; j++) {

                    Color newColor=new Color(testImg.getRGB(i,j));
                    if (probSkin[newColor.getRed()][newColor.getGreen()][newColor.getBlue()]>0.4)
                    {
                        testImg.setRGB(i,j,Color.white.getRGB());
                    }




                }

            }

            ImageIO.write(testImg,"jpg",new File("Photos/output.jpg"));











        }
        catch (Exception e) {

            e.printStackTrace();

        }


    }

}

