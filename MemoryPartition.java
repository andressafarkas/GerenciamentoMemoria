public class MemoryPartition {
    private char processId;
    private boolean isOccupied;

    public MemoryPartition() {
        this.processId = '-';  // '-' indica que nenhuma alocação foi feita ainda.
        this.isOccupied = false;
    }

    public void allocate(char processId) {
        this.processId = processId;
        this.isOccupied = true;
    }

    public void free() {
        this.processId = '-';
        this.isOccupied = false;
    }

    public boolean isOccupied() {
        return isOccupied;
    }
}
