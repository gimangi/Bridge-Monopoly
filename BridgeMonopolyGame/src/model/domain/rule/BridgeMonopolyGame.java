package model.domain.rule;

import model.domain.map.MapDecoder;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public abstract class BridgeMonopolyGame {

    private @Nullable MapDecoder mapDecoder;


    /*
        Decide whether to use the default map.
     */
    protected abstract boolean useDefaultMap();
    /*
        Select map file name.
     */
    protected abstract String selectMapFile();

    /*
        It outputs that the input file does not exist and proceeds to the default map.
     */
    protected abstract String displayNotFoundMap();

    private void loadMap() {

    }

    public void run() {

        while (mapDecoder == null) {
            try {
                if (useDefaultMap())
                    mapDecoder = new MapDecoder(selectMapFile());
                else
                    mapDecoder = new MapDecoder();

            } catch (IOException e) {
                displayNotFoundMap();
            }
        }

        

    }

}
