package com.manichord.mgit.transport;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;


public class MGitSSLSocketFactory extends SSLSocketFactory {

    private SSLSocketFactory wrappedSSLSocketFactory;
    public static String[] enabledProtocols = new String[] {"TLSv1.2", "TLSv1.1", "TLSv1"};

    public MGitSSLSocketFactory(final SSLSocketFactory wrapped) {
        wrappedSSLSocketFactory = wrapped;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return wrappedSSLSocketFactory.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return wrappedSSLSocketFactory.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket() throws IOException {
        return modifySocket(wrappedSSLSocketFactory.createSocket());
    }

    @Override
    public Socket createSocket(final Socket s, final String host, final int port, final boolean autoClose) throws IOException {
        return modifySocket(wrappedSSLSocketFactory.createSocket(s, host, port, autoClose));
    }

    @Override
    public Socket createSocket(final String host, final int port) throws IOException, UnknownHostException {
        return modifySocket(wrappedSSLSocketFactory.createSocket(host, port));
    }

    @Override
    public Socket createSocket(final String host, final int port, final InetAddress localHost, final int localPort) throws IOException, UnknownHostException {
        return modifySocket(wrappedSSLSocketFactory.createSocket(host, port, localHost, localPort));
    }

    @Override
    public Socket createSocket(final InetAddress host, final int port) throws IOException {
        return modifySocket(wrappedSSLSocketFactory.createSocket(host, port));
    }

    @Override
    public Socket createSocket(final InetAddress address, final int port, final InetAddress localAddress, final int localPort) throws IOException {
        return modifySocket(wrappedSSLSocketFactory.createSocket(address, port, localAddress, localPort));
    }


    private Socket modifySocket(final Socket socket) {
        if (null != socket && (socket instanceof SSLSocket)) {
            SSLSocket sslSocket = (SSLSocket) socket;
            sslSocket.setEnabledProtocols(enabledProtocols);
        }
        return socket;
    }
}
