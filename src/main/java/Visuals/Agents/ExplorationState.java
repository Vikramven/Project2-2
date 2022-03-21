package Visuals.Agents;

public class ExplorationState {

    private boolean isInRegion;
    private boolean goRight;
    private boolean needsToGoUp;
    private boolean needsToChangeRow;

    public ExplorationState() {
        this.isInRegion = false;
        this.needsToChangeRow = false;
        this.needsToGoUp = false;
    }

    public boolean isInRegion() {
        return isInRegion;
    }

    public boolean goesRight() {
        return goRight;
    }

    public void setInRegion(boolean inRegion) {
        isInRegion = inRegion;
    }

    public void setGoRight(boolean goRight) {
        this.goRight = goRight;
    }

    public void setNeedsToChangeRow(boolean needsToChangeRow) {
        this.needsToChangeRow = needsToChangeRow;
    }

    public boolean needsToChangeRow() {
        return needsToChangeRow;
    }

    public boolean needsToGoUp() {
        return needsToGoUp;
    }

    public void setNeedsToGoUp(boolean needsToGoUp) {
        this.needsToGoUp = needsToGoUp;
    }

    public void determineIfNeedsToMoveUpOrDown(double ang){
        int angle = (int)Math.toDegrees(ang);
        setNeedsToGoUp(0 <= angle && angle < 180);
    }
}
