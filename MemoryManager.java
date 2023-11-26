import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MemoryManager {
    private List<MemoryPartition> memory;
    private int lastCheckedIndex = 0;

    public MemoryManager(int size) {
        memory = new LinkedList<>();
        memory.add(new MemoryPartition(null, false, size)); // Inicializa a memória como livre
    }

    public void executeInstructions(List<String> instructions, int strategy) {
        for (String instruction : instructions) {
            if (instruction.startsWith("IN")) {
                String[] parts = instruction.split("[(), ]+");
                String name = parts[1];
                int size = Integer.parseInt(parts[2]);
                Process process = new Process(name, size);
                allocateMemory(process, strategy);
                printMemoryState(instruction);
            } else if (instruction.startsWith("OUT")) {
                String[] parts = instruction.split("[(), ]+");
                String name = parts[1];
                Process process = new Process(name, 0);
                deallocateMemory(process);
                defragmentMemory();
                printMemoryState(instruction);
            }
        }
    }

    public void allocateMemory(Process process, int strategy) {
        if (strategy == 1) {
            if (!firstFit(process)) {
                System.out.println("ESPAÇO INSUFICIENTE DE MEMÓRIA");
            }
        } else if (strategy == 2) {
            if (!worstFit(process)) {
                System.out.println("ESPAÇO INSUFICIENTE DE MEMÓRIA");
            }
        } else if (strategy == 3) {
            if (!bestFit(process)) {
                System.out.println("ESPAÇO INSUFICIENTE DE MEMÓRIA");
            }
        } else if (strategy == 4) {
            if (!circularFit(process)) {
                System.out.println("ESPAÇO INSUFICIENTE DE MEMÓRIA");
            }
        }
    }

    public void deallocateMemory(Process process) {
        for (MemoryPartition partition : memory) {
            if (partition.isOccupied() && partition.getProcess().name.equals(process.name)) {
                partition.deallocate();
                break;
            }
        }
    }

    public void defragmentMemory() {
        List<MemoryPartition> contiguousFreeSpace = new ArrayList<>();
        boolean contiguousSpaceFound = false;
        boolean finished = false;

        while (!finished) {
            for (MemoryPartition partition : memory) {
                if (!partition.isOccupied()) {
                    contiguousFreeSpace.add(partition);
                    contiguousSpaceFound = true;
                } else {
                    if (contiguousSpaceFound) {
                        mergeContiguousFreeSpace(contiguousFreeSpace);
                        contiguousFreeSpace.clear();
                        contiguousSpaceFound = false;
                        break;
                    }
                }
                finished = true;
            }
        }

        if (contiguousSpaceFound) {
            mergeContiguousFreeSpace(contiguousFreeSpace);
        }
    }

    private void mergeContiguousFreeSpace(List<MemoryPartition> contiguousFreeSpace) {
        if (contiguousFreeSpace.size() > 1) {
            int totalSize = contiguousFreeSpace.stream().mapToInt(MemoryPartition::getSize).sum();
            int startIndex = memory.indexOf(contiguousFreeSpace.get(0));
            memory.subList(startIndex, startIndex + contiguousFreeSpace.size()).clear();
            memory.add(startIndex, new MemoryPartition(null, false, totalSize));
        }
    }

    public boolean firstFit(Process process) {
        for (MemoryPartition partition : memory) {
            if (!partition.isOccupied() && partition.getSize() >= process.size) {
                if (partition.getSize() > process.size) {
                    int remainingSize = partition.getSize() - process.size;
                    int index = memory.indexOf(partition);
                    memory.remove(partition);
                    memory.add(index, new MemoryPartition(process, true, process.size));
                    memory.add(index + 1, new MemoryPartition(null, false, remainingSize));
                    return true;
                } else {
                    partition.allocate(process);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean worstFit(Process process) {
        int worstFitIndex = -1;
        int maxSize = -1;

        for (int i = 0; i < memory.size(); i++) {
            MemoryPartition partition = memory.get(i);
            if (!partition.isOccupied() && partition.getSize() >= process.size) {
                int currentSize = partition.getSize();
                if (currentSize > maxSize) {
                    maxSize = currentSize;
                    worstFitIndex = i;
                }
            }
        }

        if (worstFitIndex != -1) {
            MemoryPartition worstFitPartition = memory.get(worstFitIndex);
            if (worstFitPartition.getSize() > process.size) {
                int remainingSize = worstFitPartition.getSize() - process.size;
                memory.remove(worstFitPartition);
                memory.add(worstFitIndex, new MemoryPartition(process, true, process.size));
                memory.add(worstFitIndex + 1, new MemoryPartition(null, false, remainingSize));
                return true;
            } else {
                worstFitPartition.allocate(process);
                return true;
            }
        }
        return false;
    }

    public boolean bestFit(Process process) {
        int bestFitIndex = -1;
        int minSizeDiff = Integer.MAX_VALUE;

        for (int i = 0; i < memory.size(); i++) {
            MemoryPartition partition = memory.get(i);
            if (!partition.isOccupied() && partition.getSize() >= process.size) {
                int currentSize = partition.getSize();
                int sizeDiff = currentSize - process.size;
                if (sizeDiff < minSizeDiff) {
                    minSizeDiff = sizeDiff;
                    bestFitIndex = i;
                }
            }
        }

        if (bestFitIndex != -1) {
            MemoryPartition bestFitPartition = memory.get(bestFitIndex);
            if (bestFitPartition.getSize() > process.size) {
                int remainingSize = bestFitPartition.getSize() - process.size;
                memory.remove(bestFitPartition);
                memory.add(bestFitIndex, new MemoryPartition(process, true, process.size)); 
                memory.add(bestFitIndex + 1, new MemoryPartition(null, false, remainingSize)); 
                return true; 
            } else {
                bestFitPartition.allocate(process);
                return true; 
            }
        }
        return false; 
    }

    public boolean circularFit(Process process) {
        int startIndex = lastCheckedIndex;
        int i = startIndex;
        do {
            MemoryPartition partition = memory.get(i);
            if (!partition.isOccupied() && partition.getSize() >= process.size) {
                if (partition.getSize() > process.size) {
                    int remainingSize = partition.getSize() - process.size;
                    memory.remove(partition);
                    memory.add(i, new MemoryPartition(process, true, process.size));
                    memory.add(i + 1, new MemoryPartition(null, false, remainingSize));
                } else {
                    partition.allocate(process);
                }
                lastCheckedIndex = (i + 1) % memory.size();
                return true;
            }
            i = (i + 1) % memory.size();
        } while (i != startIndex);
        return false;
    }

    public void printMemoryState(String instruction) {
        if (instruction.startsWith("IN")) {
            System.out.print(instruction + "\t |");
        } else {
            System.out.print(instruction + "\t\t |");

        }
        for (MemoryPartition partition : memory) {
            if (!partition.isOccupied()) {
                System.out.print(partition.getSize() + "|");
            }
        }
        System.out.println();
    }

}