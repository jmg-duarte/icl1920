package compiler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FrameStack {

    private Map<String, Frame> frames = new HashMap<>();
    private int frameCounter = 0;
    private Frame oldFrame;

    public FrameStack() {
        oldFrame = new Frame("java/lang/Object");
    }

    public int frameDepth() {
        return frameCounter;
    }

    public Frame getOldFrame() {
        return oldFrame;
    }

    public Frame newFrame() {
        Frame frame = new Frame(frameCounter++, oldFrame);
        frames.put(frame.getFrameID(), frame);
        oldFrame = frame;
        return frame;
    }

    public void dumpStack() throws IOException {
        for (Map.Entry<String, Frame> entry : frames.entrySet()) {
            entry.getValue().dump();
        }
    }
}
