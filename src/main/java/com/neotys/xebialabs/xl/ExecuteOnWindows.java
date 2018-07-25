package com.neotys.xebialabs.xl;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.xebialabs.overthere.CmdLine;
import com.xebialabs.overthere.ConnectionOptions;
import com.xebialabs.overthere.Overthere;
import com.xebialabs.overthere.OverthereConnection;
import com.xebialabs.overthere.util.CapturingOverthereExecutionOutputHandler;

import static com.xebialabs.overthere.ConnectionOptions.ADDRESS;
import static com.xebialabs.overthere.ConnectionOptions.OPERATING_SYSTEM;
import static com.xebialabs.overthere.ConnectionOptions.PASSWORD;
import static com.xebialabs.overthere.ConnectionOptions.USERNAME;
import static com.xebialabs.overthere.OperatingSystemFamily.WINDOWS;
import static com.xebialabs.overthere.cifs.CifsConnectionBuilder.CONNECTION_TYPE;
import static com.xebialabs.overthere.cifs.CifsConnectionType.TELNET;
import static com.xebialabs.overthere.util.CapturingOverthereExecutionOutputHandler.capturingHandler;

public class ExecuteOnWindows {

    public static void main(String[] args) {

        ConnectionOptions options = new ConnectionOptions();
        options.set(ADDRESS, "mlasram");
        options.set(USERNAME, "intranet\\nnguyen");
        options.set(PASSWORD, "mo,.5duc");
        options.set(OPERATING_SYSTEM, WINDOWS);
        options.set(CONNECTION_TYPE, TELNET);
        OverthereConnection connection = Overthere.getConnection("cifs", options);
//        connection.setWorkingDirectory(connection.getFile("C:\\"));

        try {
            CapturingOverthereExecutionOutputHandler stdout = capturingHandler();
            CapturingOverthereExecutionOutputHandler stderr = capturingHandler();

            System.out.println("Avant  execute");
            final CmdLine cmdLine = CmdLine.build("echo", "%HOMEDRIVE%%HOMEPATH%");
            connection.execute(stdout, stderr, cmdLine);
            System.out.println("After  execute");

            final String userPath = Iterables.getLast(Splitter.on("\n").split(stdout.getOutput()));

            System.out.println(userPath);

            connection.setWorkingDirectory(connection.getFile(userPath));
            connection.execute(stdout, stderr, CmdLine.build("dir"));

            System.out.println(stdout.getOutput());
        } finally {
            connection.close();
        }
    }
}