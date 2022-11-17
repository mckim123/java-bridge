package bridge.domain;

import bridge.BridgeMaker;
import bridge.BridgeRandomNumberGenerator;
import bridge.constant.GameCommand;
import bridge.constant.Moving;
import bridge.constant.PrintMessage;
import java.util.ArrayList;
import java.util.List;

public class BridgeGame {
    private List<String> bridge;
    private int trials;
    private int currentPosition;
    private int bridgeSize;
    private boolean isCorrect;
    private List<String> fullUpperMap;
    private List<String> fullLowerMap;

    public BridgeGame(int bridgeSize) {
        BridgeRandomNumberGenerator bridgeRandomNumberGenerator = new BridgeRandomNumberGenerator();
        BridgeMaker bridgeMaker = new BridgeMaker(bridgeRandomNumberGenerator);
        this.bridge = bridgeMaker.makeBridge(bridgeSize);
        this.trials = 1;
        this.bridgeSize = bridgeSize;
        this.currentPosition = 0;
        fullUpperMap = new ArrayList<>();
        fullLowerMap = new ArrayList<>();
        makeFullMap();
    }

    private void makeFullMap() {
        for (String answer : bridge) {
            if (answer.equals(Moving.U.toString())) {
                fullUpperMap.add(PrintMessage.MAP_RIGHT.getString());
                fullLowerMap.add(PrintMessage.MAP_BLANK.getString());
                continue;
            }
            fullUpperMap.add(PrintMessage.MAP_BLANK.getString());
            fullLowerMap.add(PrintMessage.MAP_RIGHT.getString());
        }
    }

    public boolean move(String moving) {
        isCorrect = bridge.get(currentPosition++).equals(moving);
        return isCorrect;
    }

    public boolean retry(String gameCommand) {
        if (gameCommand.equals(GameCommand.R.toString())) {
            trials++;
            currentPosition = 0;
            return true;
        }
        return false;
    }

    public boolean isPlaying() {
        return (currentPosition < bridgeSize);
    }

    public List<String> getMap() {
        List<String> totalMap = new ArrayList<>(fullUpperMap.subList(0, currentPosition));
        totalMap.addAll(fullLowerMap.subList(0, currentPosition));
        if (!isCorrect) {
            totalMap.set(currentPosition - 1, changeSymbol(totalMap.get(currentPosition - 1)));
            totalMap.set(2 * currentPosition - 1, changeSymbol(totalMap.get(2 * currentPosition - 1)));
        }
        return totalMap;
    }

    private String changeSymbol(String s) {
        if (s.equals(PrintMessage.MAP_RIGHT.getString())) {
            return PrintMessage.MAP_BLANK.getString();
        }
        return PrintMessage.MAP_WRONG.getString();
    }

    public int getTrials() {
        return trials;
    }
}
