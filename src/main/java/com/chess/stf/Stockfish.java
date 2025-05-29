package com.chess.stf;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Stockfish {
    private Process engineProcess;
    private BufferedReader processReader;
    private BufferedWriter processWriter;

    public boolean startEngine() {
        try {
            ProcessBuilder pb = new ProcessBuilder("/usr/games/stockfish");
            engineProcess = pb.start();
            processReader = new BufferedReader(new InputStreamReader(engineProcess.getInputStream()));
            processWriter = new BufferedWriter(new OutputStreamWriter(engineProcess.getOutputStream()));

            sendCommand("setoption name Skill Level value 20");
            sendCommand("setoption name Contempt value 24");

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void sendCommand(String command) throws IOException {
        processWriter.write(command + "\n");
        processWriter.flush();
    }

    public String readOutput() throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = processReader.readLine()) != null) {
            sb.append(line).append("\n");
            if (line.equals("readyok") || line.startsWith("bestmove")) break;
        }
        return sb.toString();
    }
    public boolean isAlive() {
        return engineProcess != null && engineProcess.isAlive();
    }

    public void stopEngine() throws IOException {
        sendCommand("quit");
        processReader.close();
        processWriter.close();
        engineProcess.destroy();
    }

    public String getBestMove(String fen) throws IOException {
        int numberOfMoves = 5;
        sendCommand("uci");
        sendCommand("setoption name MultiPV value " + numberOfMoves);
        sendCommand("isready");
        readOutput();

        sendCommand("position fen " + fen);
        sendCommand("go depth 20");
        String output = readOutput();

        List<String> possibleMoves = getStrings(output);

        if (!possibleMoves.isEmpty()) {
            Random random = new Random();
            return possibleMoves.get(random.nextInt(possibleMoves.size()));
        }

        for (String line : output.split("\n")) {
            if (line.startsWith("bestmove")) {
                return line.split(" ")[1];
            }
        }

        return null;
    }

    private static List<String> getStrings(String output) {
        List<String> possibleMoves = new ArrayList<>();
        for (String line : output.split("\n")) {
            if (line.startsWith("info") && line.contains(" pv ")) {
                String[] parts = line.split(" pv ");
                if (parts.length > 1) {
                    String move = parts[1].split(" ")[0];
                    if (!possibleMoves.contains(move)) {
                        possibleMoves.add(move);
                    }
                }
            }
        }
        return possibleMoves;
    }

}
