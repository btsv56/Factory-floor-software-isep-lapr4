package eapli.base.app.scm.console.JavaTCPCommunication;

import java.io.*;

import javax.net.ssl.*;
import javax.security.cert.X509Certificate;

/**
 *
 * @author asc@isep.ipp.pt
 */
public class CheckServerTLS {
    static private SSLSocket sock;

    public static void main(final String args[]) throws Exception {

        final SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();

        try {
            sock = (SSLSocket) sf.createSocket("10.8.208.198", 9999);
            for (final String s : sf.getSupportedCipherSuites()) {
                System.out.println(s);
            }
        } catch (final IOException ex) {
            System.out.println("Failed to connect to: " + "10.8.208.198" + ":" + 9999);
            System.out.println("Application aborted.");
            System.exit(1);
        }

        System.out.println("Connected to server: " + "10.8.208.198" + ":" + 9999);

        try {
            sock.startHandshake();
            final SSLSession ssl = sock.getSession();
            System.out.println("------------------------------------------------------");
            System.out.println(
                    "SSL/TLS version: " + ssl.getProtocol() + "         Cypher suite: " + ssl.getCipherSuite());

            final X509Certificate[] chain = ssl.getPeerCertificateChain();
            System.out.println("------------------------------------------------------");
            System.out.println("Certificate subject: " + chain[0].getSubjectDN());
            System.out.println("------------------------------------------------------");
            System.out.println("Certificate issuer: " + chain[0].getIssuerDN());
            System.out.println("------------------------------------------------------");
            System.out.println("Not before: " + chain[0].getNotBefore());
            System.out.println("------------------------------------------------------");
            System.out.println("Not after:  " + chain[0].getNotAfter());
            System.out.println("------------------------------------------------------");

        } catch (final SSLException tlsE) {
            System.out.println("SSL/TLS handshake has failed:\r\n" + tlsE.getCause());
            try {
                sock.close();
            } catch (final IOException ex2) {
                System.out.println("Error closing socket.");
            }
            System.exit(1);
        }
        try {
            sock.close();
        } catch (final IOException ex2) {
            System.out.println("Error closing socket.");
        }

    } // MAIN METHOD
} // CLASS

