package compiler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FrameStack {

    private Map<String, Frame> frames = new HashMap<>();
    private int frameCounter = 0;
    private int closureCounter = 0;

    private Frame oldFrame = new Frame("java/lang/Object");

    public Frame getOldFrame() {
        return oldFrame;
    }

    public Frame newFrame() {
        Frame frame = new Frame(String.format("f%s", frameCounter++), oldFrame);
        frames.put(frame.getFrameId(), frame);
        oldFrame = frame;
        return frame;
    }

    public Closure newClosureFrame(ClosureInterface closureInterface) {
        Closure frame = new Closure(String.format("closure%s", closureCounter++), closureInterface, oldFrame);
        // Frame frame = new Frame(String.format("fclosure%s", closureCounter++), oldFrame);
        frames.put(frame.getFrameId(), frame);
        Frame closureFrame = frame.getClosureFrame();
        frames.put(closureFrame.frameId, closureFrame);
        oldFrame = closureFrame;
        return frame;
    }

    public void dumpStack() throws IOException {
        for (Map.Entry<String, Frame> entry : frames.entrySet()) {
            entry.getValue().dump();
        }
    }
}
