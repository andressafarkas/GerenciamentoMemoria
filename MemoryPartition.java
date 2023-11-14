public class MemoryPartition {
    private char processId;
    private boolean isOccupied;
    private int size;


    public MemoryPartition(String processId, int size) {
        this.processId = '-';  // '-' indica que nenhuma alocação foi feita ainda.
        this.isOccupied = false;
        this.size = size;
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
