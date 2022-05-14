package com.github.abhinavgunwant.qr;

import java.io.IOException;
import java.util.Scanner;

import com.github.abhinavgunwant.qr.gui.MainWindow;
import com.google.zxing.WriterException;

/**
 * Main Class!
 */
public class App {
    private static Generator g = Generator.getInstance();

    private static void cli() {
        Scanner s = new Scanner(System.in);

        System.out.println("Welcome to qr code generator!");
        System.out.println("Enter the text/url to generate qr code for: ");

        String text = s.nextLine();

        System.out.print("Enter output file name (with extension): ");

        String filename = s.nextLine();

        System.out.println("Generating qr code...");

        try {
            g.generateCodeWithLogo(text, filename, 200);
        } catch (WriterException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }
    }

    public static void main( String[] args ) {
        if (args.length > 0 && args[0].matches("-c")) {
            cli();
        } else {
            MainWindow mw = new MainWindow();
            mw.show();
        }
    }
}
