public class MemoryPartition {
    private Process process;
    private boolean isOccupied;
    private int size;

    public MemoryPartition(Process process, boolean isOccupied, int size) {
        this.process = process; 
        this.isOccupied = isOccupied;
        this.size = size;
    }

    public void allocate(Process process) {
        this.process = process;
        this.isOccupied = true;
    }

    public void deallocate() {
        this.process = null;
        this.isOccupied = false;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public Process getProcess() {
        return this.process;
    }

     public int getSize() {
        return this.size;
    }
}
